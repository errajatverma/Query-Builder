package QueryBuilder;
import java.awt.*;
import java.sql.*;
import javax.swing.*;
import java.awt.event.*;
class MainFrame implements ActionListener
{
	JFrame f;
	JLabel db,user,pass;
	JPasswordField p;
	JButton b;
	public MainFrame()
	{
		f=new JFrame("Query Builder");
		f.setLayout(null);
		f.setVisible(true);
		f.setBounds(550,220, 300, 200);
		f.getContentPane().setBackground(new Color(0,255,255));
		f.setResizable(false);
		f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		db=new JLabel("DataBase  Oracle");
		db.setFont(new Font("Monotype_Corsiva",Font.ITALIC,15));
		db.setBounds(90, 2, 150,30);
		f.add(db);
		user=new JLabel("User            :    SYSTEM");
		user.setBounds(40, 60, 150, 30);
		f.add(user);
		pass=new JLabel("Password  : ");
		pass.setBounds(40, 95, 80,30);
		f.add(pass);
		p=new JPasswordField();
		p.setBounds(120, 100, 140, 20);
		f.add(p);
		b=new JButton("Submit");
		b.setBounds(100, 140, 100, 20);
		f.add(b);
		b.addActionListener(this);
	}
	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(p.getText().equals(""))
		{
			JOptionPane.showMessageDialog(f, "Password Empty", "Info.",JOptionPane.ERROR_MESSAGE,new ImageIcon("m.png"));
		}
		else
		{
			try 
			{
				Class.forName("oracle.jdbc.driver.OracleDriver");
				try
				{
					Connection c=DriverManager.getConnection("JDBC:Oracle:thin:@localhost:1521:xe","System",p.getText());
					f.dispose();
					new QuerySQL(p.getText());
					c.close();
				}
				catch(Exception e2)
				{
					JOptionPane.showMessageDialog(f, "Incorrect Password", "Info.",JOptionPane.ERROR_MESSAGE,new ImageIcon("m.png"));
				}
			}
			catch (Exception e1) 
			{
				e1.printStackTrace();
				JOptionPane.showMessageDialog(f, "Error in Connection", "Info.",JOptionPane.ERROR_MESSAGE,new ImageIcon("m.png"));
			}
			
		}
	}
}
public class Main_Query_Builder
{
	public static void main(String[] args)
	{
		new MainFrame();
	}
}