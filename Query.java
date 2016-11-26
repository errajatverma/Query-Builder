package QueryBuilder;
import java.awt.*;
import java.awt.List;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;
@SuppressWarnings("serial")
class QuerySQL extends JFrame implements ActionListener
{
	String query;
	int col;
	static JFrame f;
	String p;
	JComboBox c,c1,op;
	JTextField val;
	JLabel title,table,group,operator;
	JButton move,del,sql,result,update,drop,logout,refresh;
	Connection create;
	Statement s;
	ResultSet rs;
	JPanel list,view;
	List jl1,jl2;
	JScrollPane jsp;
	ArrayList<String> arr;
	Set<String> tablename,columnname,simpleColumn;
	JRadioButton jrb,and,or,none;
	public QuerySQL(String p) throws Exception
	{
		this.p=p;
		query="";
		String[] array={"TABLE"};
		String[] expression={"Select","<",">","<=",">=","="};
		arr=new ArrayList<String>();
		Class.forName("oracle.jdbc.driver.OracleDriver");
		create=DriverManager.getConnection("JDBC:Oracle:thin:@localhost:1521:xe","system",p);
		s=create.createStatement();
		DatabaseMetaData rsmd=create.getMetaData();
		rs=rsmd.getTables(null,"SYSTEM", null, array);
		while(rs.next())
		{
			//System.out.println(rs.getString(3)+" = "+rs.getString(4));
			arr.add(rs.getString(3));
		}
		f=new JFrame("Query Builder");
		f.setLayout(null);
		f.setVisible(true);
		f.setBounds(450,110, 525, 550);
		f.getContentPane().setBackground(new Color(0,255,255));
		//f.setResizable(false);
		f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		columnname=new HashSet<String>();
		simpleColumn=new HashSet<String>();
		tablename=new HashSet<String>();
		title=new JLabel("Welcome System");
		title.setBounds(210, 10, 120,30);
		f.add(title);
		table=new JLabel("Select Table");
		table.setBounds(20, 80, 100, 30);
		f.add(table);
		c=new JComboBox(arr.toArray());
		c.setBounds(20, 110, 200, 20);
		c.addActionListener
		(
			new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
						Object item=c.getSelectedItem();
						try
						{
							rs=s.executeQuery("select * from "+item);
							ResultSetMetaData rsmd=rs.getMetaData();
							col=rsmd.getColumnCount();
							for(int i=1;i<=col;i++)
							{
								if(i==col)
								{
									jl1.removeAll();
									for(int j=1;j<=col;j++)
									{
										jl1.add(rsmd.getColumnName(j));
									}
								}
								else
								{
									jl1.add(rsmd.getColumnName(i));
								}
							}
						}
						catch(Exception ex)
						{
							ex.printStackTrace();
						}
							
					}	
			}
		);
		f.add(c);
		jl1=new List(5);
		jl1.setBounds(20, 140, 200, 200);
		f.add(jl1);
		jl2=new List(5);
		jl2.setBounds(300, 140, 200, 200);
		f.add(jl2);
		move=new JButton("=>");
		move.setBounds(235,190 ,50, 20);
		f.add(move);
		del=new JButton("<=");
		del.setBounds(235,240 ,50, 20);
		f.add(del);
		move.addActionListener(this);
		del.addActionListener(this);
		group=new JLabel("Group By");
		group.setBounds(315,103,60,20);
		f.add(group);
		jrb=new JRadioButton();
		jrb.setBounds(293, 110, 22, 12);
		jrb.setBackground(new Color(0,255,255));
		f.add(jrb);
		sql=new JButton("SQL");
		sql.setBounds(418,370,80,20);
		f.add(sql);
		sql.addActionListener(this);
		logout=new JButton("LogOut");
		logout.setBounds(5, 10, 80, 20);
		logout.addActionListener(this);
		f.add(logout);
		refresh=new JButton("Refresh");
		refresh.setBounds(420, 10, 80, 20);
		f.add(refresh);
		refresh.addActionListener(this);
		result=new JButton("Result");
		result.setBounds(418,410,80,20);
		f.add(result);
		result.addActionListener(this);
		jrb.addItemListener
		(
			new ItemListener()
			{
				@Override
				public void itemStateChanged(ItemEvent e) 
				{
					 
				}
			}
		);
		and=new JRadioButton("And",false);
		and.setBounds(245,370,80,12);
		and.setBackground(new Color(0,255,255));
		f.add(and);
		or=new JRadioButton("Or",false);
		or.setBounds(245,410, 80, 12);
		or.setBackground(new Color(0,255,255));
		f.add(or);
		none=new JRadioButton("None",true);
		none.setBounds(245,450, 80, 12);
		none.setBackground(new Color(0,255,255));
		f.add(none);
		ButtonGroup bg=new ButtonGroup();
		bg.add(and);
		bg.add(or);
		bg.add(none);
		operator=new JLabel("Operators");
		operator.setBounds(20, 370, 100, 20);
		f.add(operator);
		op=new JComboBox(expression);
		op.setEnabled(false);
		op.setBounds(20, 395, 200, 20);
		op.addActionListener(this);
		f.add(op);
		val=new JTextField("Enter Value");
		val.setBounds(20, 445, 200, 20);
		val.setEditable(false);
		f.add(val);
		update=new JButton("Update");
		update.setBounds(418,450,80,20);
		update.addActionListener(this);
		f.add(update);
		drop=new JButton("Drop");
		drop.setBounds(420,103,80,20);
		drop.addActionListener(this);
		f.add(drop);
	}
	@Override
	public void actionPerformed(ActionEvent e)
	{
		try
		{
			if(e.getSource()==move)
			{
				if(jl1.getSelectedItem()==null)
				{
				}
				else
				{
				if(jl2.getItemCount()!=0)
				{
					String[] items=jl2.getItems();
					for(String itm:items)
					{
						if(itm.equals(jl1.getSelectedItem()))
						{
							jl2.remove(itm);
						}
					}
				}
				jl2.add(jl1.getSelectedItem());
				columnname.add(c.getSelectedItem()+"."+jl1.getSelectedItem());
				simpleColumn.add(jl1.getSelectedItem());
				tablename.add((String)c.getSelectedItem());
				op.setEnabled(true);
				}
			}
			else if(e.getSource()==del)
			{
				columnname.remove((String)c.getSelectedItem()+"."+jl2.getSelectedItem());
				simpleColumn.remove(jl2.getSelectedItem());
				jl2.remove(jl2.getSelectedItem());
			}
			else if(e.getSource()==sql)
			{
				try
				{
					if(c1.getSelectedItem()!=null&&op.getSelectedItem()!="Select")
					{
						if(!val.getText().equals("")&&!val.getText().equals("Enter Value"))
						{
							query="select "+message(columnname)+" from "+message(tablename)+" where "+(String)c1.getSelectedItem()+" "+(String)op.getSelectedItem()+" "+val.getText()+";";
							new SeeSQL(query);
						}
						else
						{
							JOptionPane.showMessageDialog(f, "Empty Value", "Info.",JOptionPane.ERROR_MESSAGE,new ImageIcon("m.png"));
						}
					}
					else
					{
						query="select "+message(columnname)+" from "+message(tablename)+";";
						new SeeSQL(query);
					}
				}
				catch(Exception ex)
				{
					if(op.getSelectedItem().equals("Select"))
					{
						query="select "+message(columnname)+" from "+message(tablename)+";";
						new SeeSQL(query);
					}
					else
					{
						JOptionPane.showMessageDialog(f, "Select Column", "Info.",JOptionPane.ERROR_MESSAGE,new ImageIcon("m.png"));
					}
				}
				
			}
			else if(e.getSource()==result)
			{
					try
					{
						if(c1.getSelectedItem()!=null&&op.getSelectedItem()!="Select")
						{
							if(!val.getText().equals("")&&!val.getText().equals("Enter Value"))
							{
								query="select "+message(columnname)+" from "+message(tablename)+" where "+(String)c1.getSelectedItem()+" "+(String)op.getSelectedItem()+" "+val.getText();
								f.setEnabled(false);
								new SeeResult(query,columnname,p);
							}
							else
							{
								JOptionPane.showMessageDialog(f, "Empty Value", "Info.",JOptionPane.ERROR_MESSAGE,new ImageIcon("m.png"));
							}
						}
						else
						{
							query="select "+message(columnname)+" from "+message(tablename);
							f.setEnabled(false);
							new SeeResult(query,columnname,p);
						}
					}
					catch(Exception ex)
					{
						if(op.getSelectedItem().equals("Select"))
						{
							query="select "+message(columnname)+" from "+message(tablename);
							if(query.equals("select  from "))
							{
								JOptionPane.showMessageDialog(f, "Query Empty", "Info.",JOptionPane.ERROR_MESSAGE,new ImageIcon("m.png"));	
							}
							else
							{
								f.setEnabled(false);
								new SeeResult(query,columnname,p);
							}
						}
						else
						{
							JOptionPane.showMessageDialog(f, "Select Column", "Info.",JOptionPane.ERROR_MESSAGE,new ImageIcon("m.png"));
						}
					}
			}
			else if(e.getSource()==update)
			{
				if(tablename.size()>0)
				{
					f.setEnabled(false);
					new UpdateSQL(tablename,simpleColumn,columnname,p);
				}
				else
				{
					JOptionPane.showMessageDialog(f, "Select Table", "Info.",JOptionPane.ERROR_MESSAGE,new ImageIcon("m.png"));	
				}
			}
			else if(e.getSource()==drop)
			{
				if(tablename.size()>0)
				{
					f.setEnabled(false);
					new DropSQL(p,tablename,columnname,simpleColumn);
				}
				else
				{
					JOptionPane.showMessageDialog(f, "Select Table", "Info.",JOptionPane.ERROR_MESSAGE,new ImageIcon("m.png"));	
				}
			}
			else if(e.getSource()==op)
			{
				if(columnname.size()>0)
				{
					Object get=op.getSelectedItem();
						if(get.equals("Select"))
						{
						
						}
						else
						{
							c1=new JComboBox(columnname.toArray());
							c1.setBounds(20, 420, 200, 20);
							f.add(c1);
							c1.addActionListener
							(
									new ActionListener()
									{
										public void actionPerformed(ActionEvent ae)
										{
											if(c1.getSelectedItem()==null)
											{
										
											}
											else
											{
												val.setEditable(true);
											}
										}
									}
							);
						}
					}
				}
				else if(e.getSource()==logout)
				{
					f.dispose();
					new MainFrame();
				}
				else if(e.getSource()==refresh)
				{
					f.dispose();
					new QuerySQL(p);
				}
			}	
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	public static String message(Set<String> name)
	{
		String tab="";
		int Size=name.size();
		if(Size>1)
		{
			for(String loop:name)
			{
				tab+=loop+" ,";
			}
			String[] space=tab.split(" ");
			tab="";
			for(int i=0;i<space.length-1;i++)
			{
				tab+=space[i];
			}
		}
		else
		{
			for(String loop:name)
			{
				tab=loop;
			}
		}
		return tab;
	}
}
public class Query 
{
	public static void main(String[] args) throws Exception
	{
		new QuerySQL("rv5064");
	}
}