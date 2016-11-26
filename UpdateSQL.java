package QueryBuilder;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;
public class UpdateSQL implements ActionListener
{
	ArrayList<String> al;
	CopyOnWriteArraySet<String> coln;
	Set<String>tablename;
	String query;
	String[] typearr;
	Connection create;
	Statement s;
	ResultSet rs;
	JFrame f;
	JComboBox t,col,type;
	JButton sqlt,updatet,sqlc,updatec,ok;
	JLabel label,tl,tn,cn,sc,ac;
	JTextField tr,table,column,add;
	public UpdateSQL(Set<String> tablename,Set<String> columnname,Set<String> columnname2, String p)
	{
		try 
		{
			this.tablename=tablename;
			coln=new CopyOnWriteArraySet<String>(columnname2);
			al=new ArrayList<String>();
			typearr=new String[]{"NUMBER","VARCHAR2","DATE","CHAR","BLOB","CLOB","TIMESTAMP"};
			Class.forName("oracle.jdbc.driver.OracleDriver");
			create=DriverManager.getConnection("JDBC:Oracle:thin:@localhost:1521:xe","system",p);
			s=create.createStatement();
			f=new JFrame();
			f=new JFrame("Update");
			f.setLayout(null);
			f.setVisible(true);
			f.setBounds(600,180, 260, 450);
			f.getContentPane().setBackground(new Color(0,255,0));
			//ff.setResizable(false);
			f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);//DO_NOTHING_ON_CLOSE);
			tn=new JLabel("Select Table :");
			tn.setBounds(30, 20, 180, 20);
			f.add(tn);
			t=new JComboBox(tablename.toArray());
			t.setBounds(30, 50, 180,20);
			t.setEditable(false);
			t.addActionListener(this);
			f.add(t);
			tl=new JLabel("Update Table Name :");
			tl.setBounds(30, 200, 180, 20);
			f.add(tl);
			tr=new JTextField();
			tr.setBounds(30, 230, 180,20);
			tr.setText(tablename.iterator().next());
			f.add(tr);
			cn=new JLabel("Update Column Name :");
			cn.setBounds(30, 260, 180, 20);
			f.add(cn);
			column=new JTextField();
			column.setBounds(30, 290, 180,20);
			column.setText(columnname.iterator().next());
			f.add(column);
			sc=new JLabel("Select Column  :");
			sc.setBounds(30, 80, 180,20);
			f.add(sc);
			al.clear();
			String size=(String)t.getSelectedItem();
			for(String loop:coln)
			{
				if(loop.startsWith(size))
				{
					al.add(loop.substring(size.length()+1));
				}
			}
			col=new JComboBox(al.toArray());
			col.setBounds(30,110,180,20);
			col.addActionListener(this);
			f.add(col);
			sqlt=new JButton("SQL TABLE");
			sqlt.setBounds(30,320,80,20);
			sqlt.addActionListener(this);
			f.add(sqlt);
			updatet=new JButton("Update TABLE");
			updatet.setBounds(130,320,80,20);
			updatet.addActionListener(this);
			f.add(updatet);
			sqlc=new JButton("SQL Column");
			sqlc.setBounds(30,350,80,20);
			sqlc.addActionListener(this);
			f.add(sqlc);
			updatec=new JButton("Update Column");
			updatec.setBounds(130,350,80,20);
			updatec.addActionListener(this);
			f.add(updatec);
			ok=new JButton("OK");
			ok.setBounds(90,390,60,20);
			ok.addActionListener(this);
			f.add(ok);
			ac=new JLabel("Add Column  :");
			ac.setBounds(30, 140, 180, 20);
			f.add(ac);
			add=new JTextField();
			add.setBounds(30, 170, 100,20);
			f.add(add);
			type=new JComboBox(typearr);
			type.setBounds(140, 170, 70, 20);
			f.add(type);	
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==t)
		{
			tr.setEditable(true);
			tr.setText((String)t.getSelectedItem());
			al.clear();
			String size=(String) t.getSelectedItem();
			for(String loop:coln)
			{
				if(loop.startsWith(size))
				{
					al.add(loop.substring(size.length()+1));
				}
			}
			col.removeAllItems();
			for(String loop:al)
			{
				col.addItem((Object)loop);
			}
		}
		else if(e.getSource()==col)
		{
			column.setEditable(true);
			column.setText((String)col.getSelectedItem());	
		}
		else if(e.getSource()==sqlt)
		{
			query="alter table "+t.getSelectedItem()+" rename to '"+tr.getText()+"';";
			new SeeSQL(query);
		}
		else if(e.getSource()==updatet)
		{
			try
			{
				query="alter table "+t.getSelectedItem()+" rename to "+tr.getText();
				rs=s.executeQuery(query);
				JOptionPane.showMessageDialog(f, "Table Updated Succesfully", "Info.",JOptionPane.OK_OPTION,new ImageIcon("ok.jpg"));
				tablename.remove(t.getSelectedItem());
				tablename.add(tr.getText());
				for(String loop:coln)
				{
					if(loop.startsWith((String)t.getSelectedItem()))
					{
						coln.remove(loop);
						coln.add(loop.replace((String) t.getSelectedItem(),tr.getText()));
					}
				}
				t.addItem(tr.getText());
				t.removeItem(t.getSelectedItem());
			}
			catch(Exception e1)
			{
				JOptionPane.showMessageDialog(f, e1.getMessage(), "Error",JOptionPane.ERROR_MESSAGE,new ImageIcon("m.png"));
			}
		}
		else if(e.getSource()==sqlc)
		{
			if(add.getText().equals(""))
			{
				query="alter table "+t.getSelectedItem()+" rename "+col.getSelectedItem()+" to "+column.getText()+";";
				if(query!=null)
				{
					new SeeSQL(query);
				}
				else
				{
					JOptionPane.showMessageDialog(f, "Empty Value", "Info.",JOptionPane.ERROR_MESSAGE,new ImageIcon("m.png"));
				}
			}
			else
			{
				query="alter table "+t.getSelectedItem()+" add ('"+add.getText()+"'  "+type.getSelectedItem()+"(4000) NULL);";
				new SeeSQL(query);
			}
		}
		else if(e.getSource()==updatec)
		{
			if(add.getText().equals(""))
			{
				query="alter table "+t.getSelectedItem()+" rename column "+col.getSelectedItem()+" to "+column.getText();
				if(query!=null)
				{
					try
					{
						rs=s.executeQuery(query);
						JOptionPane.showMessageDialog(f, "Column Updated Succesfully", "Info.",JOptionPane.OK_OPTION,new ImageIcon("ok.jpg"));
						coln.remove(t.getSelectedItem()+"."+col.getSelectedItem());
						coln.add(t.getSelectedItem()+"."+column.getText());
						col.addItem(column.getText());
						col.removeItem(col.getSelectedItem());
					}
					catch (SQLException e1) 
					{
						JOptionPane.showMessageDialog(f, e1.getMessage(), "Error",JOptionPane.ERROR_MESSAGE,new ImageIcon("m.png"));
					}
				}
				else
				{
					JOptionPane.showMessageDialog(f, "Empty Value", "Info.",JOptionPane.ERROR_MESSAGE,new ImageIcon("m.png"));
				}
			}
			else
			{
				query="alter table "+t.getSelectedItem()+" add ("+add.getText()+" "+type.getSelectedItem()+"(38) NULL)";
				try
				{
					rs=s.executeQuery(query);
					JOptionPane.showMessageDialog(f, "Column Added Succesfully", "Info.",JOptionPane.OK_OPTION,new ImageIcon("ok.jpg"));
					col.addItem(add.getText());
					add.setText("");
				}
				catch (SQLException e1) 
				{
					JOptionPane.showMessageDialog(f, e1.getMessage(), "Error",JOptionPane.ERROR_MESSAGE,new ImageIcon("m.png"));
				}
			}
		}
		else if(e.getSource()==ok)
		{
			QuerySQL.f.setEnabled(true);
			f.dispose();
		}
	}
}
