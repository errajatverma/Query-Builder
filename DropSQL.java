package QueryBuilder;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

import java.util.*;
public class DropSQL implements ActionListener
{
	String query;
	JFrame f;
	Set<String> tablename,columnname;
	ArrayList<String> st;
	ResultSet rs;
	Statement s;
	Connection create;
	JTextField tn;
	JComboBox combo,column,col;
	JButton dt,dc,tc,sdt,sdc,stc,ok;
	JLabel drop,truncat,delete;
	public DropSQL(String p,Set<String> tablename,Set<String> selected,Set<String> columnname) throws Exception
	{
		this.columnname=selected;
		this.tablename=tablename;
		st=new ArrayList<String>();
		Class.forName("oracle.jdbc.driver.OracleDriver");
		create=DriverManager.getConnection("JDBC:Oracle:thin:@localhost:1521:xe","system",p);
		s=create.createStatement();
		f=new JFrame();
		f=new JFrame("Drop");
		f.setLayout(null);
		f.setVisible(true);
		f.setBounds(540,190, 355, 420);
		f.getContentPane().setBackground(new Color(0,255,0));
		//ff.setResizable(false);
		f.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		drop=new JLabel();
		drop.setBounds(20, 20,150, 160);
		drop.setBorder(BorderFactory.createTitledBorder("DropTable"));
		f.add(drop);
		combo=new JComboBox(tablename.toArray());
		combo.setBounds(40, 50, 115,20);
		combo.addActionListener(this);
		f.add(combo);
		dt=new JButton("Drop");
		dt.setBounds(40,100 , 115, 20);
		dt.addActionListener(this);
		f.add(dt);
		sdt=new JButton("SQL");
		sdt.setBounds(40,130 , 115, 20);
		sdt.addActionListener(this);
		f.add(sdt);
		delete=new JLabel();
		delete.setBounds(20,190,305,160);
		delete.setBorder(BorderFactory.createTitledBorder("Drop Column"));
		f.add(delete);
		column=new JComboBox(columnname.toArray());
		column.setBounds(40,220,265,20);
		column.addActionListener(this);
		f.add(column);
		tn=new JTextField();
		tn.setBounds(40,250,265,20);
		tn.setText((String)combo.getSelectedItem());
		tn.setEditable(false);
		f.add(tn);
		dc=new JButton("Drop");
		dc.setBounds(40,280,265,20);
		dc.addActionListener(this);
		f.add(dc);
		sdc=new JButton("SQL");
		sdc.setBounds(40,310, 265,20);
		sdc.addActionListener(this);
		f.add(sdc);
		truncat=new JLabel();
		truncat.setBounds(170, 20,150, 160);
		truncat.setBorder(BorderFactory.createTitledBorder("Truncat Table"));
		f.add(truncat);
		col=new JComboBox(tablename.toArray());
		col.setBounds(190, 50, 115,20);
		f.add(col);
		tc=new JButton("Truncat");
		tc.setBounds(190,100 , 115, 20);
		tc.addActionListener(this);
		f.add(tc);
		stc=new JButton("SQL");
		stc.setBounds(190,130 , 115, 20);
		stc.addActionListener(this);
		f.add(stc);
		ok=new JButton("OK");
		ok.setBounds(140,350,60,20);
		ok.addActionListener(this);
		f.add(ok);
	}
	@Override
	public void actionPerformed(ActionEvent e)
	{	
		try
		{
			if(e.getSource()==combo)
			{
				tn.setEditable(false);
				tn.setText((String)combo.getSelectedItem());
				st.clear();
				String size=(String) combo.getSelectedItem();
				for(String loop:columnname)
				{
					if(loop.startsWith(size))
					{
						st.add(loop.substring(size.length()+1));
					}
				}
				column.removeAllItems();
				for(String loop:st)
				{
					column.addItem((Object)loop);
				}
			}
			else if(e.getSource()==dt)
			{
				query="drop TABLE "+combo.getSelectedItem();
				rs=s.executeQuery(query);
				String size=(String) combo.getSelectedItem();
				for(String loop:columnname)
				{
					if(loop.startsWith(size))
					{
						column.removeItem((Object)loop.substring(size.length()+1));
						columnname.remove(loop);
					}
				}
				col.removeItem(combo.getSelectedItem());
				JOptionPane.showMessageDialog(f, "Table Deleted", "Info.",JOptionPane.OK_OPTION,new ImageIcon("ok.jpg"));
				combo.removeItem(combo.getSelectedItem());
				
			}
			else if(e.getSource()==sdt)
			{
				query="drop TABLE "+combo.getSelectedItem()+";";
				new SeeSQL(query);
			}
			else if(e.getSource()==dc)
			{
				query="alter table "+tn.getText()+" drop column "+column.getSelectedItem();
				rs=s.executeQuery(query);
				JOptionPane.showMessageDialog(f, "Column Deleted", "Info.",JOptionPane.OK_OPTION,new ImageIcon("m.png"));
				columnname.remove(tn.getText()+"."+column.getSelectedItem());
				column.removeItem(column.getSelectedItem());
			}
			else if(e.getSource()==sdc)
			{
				query="alter table "+tn.getText()+" drop column "+column.getSelectedItem()+";";
				new SeeSQL(query);	
			}
			else if(e.getSource()==tc)
			{
				query="truncate table "+col.getSelectedItem();
				rs=s.executeQuery(query);
				String size=(String) col.getSelectedItem();
				for(String loop:st)
				{
					if(loop.startsWith(size))
					{
						column.removeItem((Object)loop.substring(size.length()+1));
					}
				}
				JOptionPane.showMessageDialog(f, "Table Truncated", "Info.",JOptionPane.OK_OPTION,new ImageIcon("m.png"));
				col.removeItem(col.getSelectedItem());
			}
			else if(e.getSource()==stc)
			{
				query="truncate table "+col.getSelectedItem()+";";
				new SeeSQL(query);
			}
			else if(e.getSource()==ok)
			{
				QuerySQL.f.setEnabled(true);
				f.dispose();
			}
			else if(e.getSource()==col)
			{
				tn.setEditable(false);
				tn.setText((String)combo.getSelectedItem());
				st.clear();
				String size=(String) combo.getSelectedItem();
				for(String loop:columnname)
				{
					if(loop.startsWith(size))
					{
						st.add(loop.substring(size.length()+1));
					}
				}
				column.removeAllItems();
				for(String loop:st)
				{
					column.addItem((Object)loop);
				}
			}
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(f, ex.getMessage(), "Error",JOptionPane.ERROR_MESSAGE,new ImageIcon("m.png"));
		}
	}
}
