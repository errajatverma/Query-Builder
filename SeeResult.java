package QueryBuilder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;
import java.sql.*;
public class SeeResult
{
	String query;
	Set<String> columnname;
	JFrame ff;
	JScrollPane jsp;
	JPanel list;
	JTextArea area;
	ResultSet rs;
	Statement s;
	public SeeResult(String query,Set<String> columnname,String p) 
	{	
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection create=DriverManager.getConnection("JDBC:Oracle:thin:@localhost:1521:xe","system",p);
			s=create.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			this.query=query;
			this.columnname=columnname;
			//f.setEnabled(false);
			ff=new JFrame("Result");
			ff.setLayout(null);
			ff.setVisible(true);
			ff.setSize(460,550);
			ff.setBounds(510,190, 400, 400);
			ff.getContentPane().setBackground(new Color(0,255,0));
			//ff.setResizable(false);
			ff.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
			JPanel list=new JPanel();
			list.setBounds(40, 40, 305, 290);
			list.setLayout(new BorderLayout());
			JTextArea area=new JTextArea();
			area.setBackground(Color.RED);
			area.setEditable(false);
			area.setBounds(0, 0, 305, 290);
			JPanel line=new JPanel();
			line.setBounds(0, 30, 305, 2);
			line.setBorder(BorderFactory.createMatteBorder(2,0, 2,0, Color.black));
			area.add(line);
			Iterator<String> i=columnname.iterator();
			int len=0;
			int col=0;
			while(i.hasNext())
			{
				JLabel label=new JLabel((String) i.next());
				label.setFont(new Font("ROMAN_BASELINE",Font.BOLD+Font.ITALIC,12));
				label.setBounds(5+len, 5, 180, 30);
				area.setColumns(col);
				len+=200;
				col=col+35;
				area.add(label);
			}
			rs=s.executeQuery(query);
			int width=0;
			for(int w=1;w<=columnname.size();w++)
			{	 
				int height=0;
				int row=0;
				while(rs.next())
				{
					JLabel jl=new JLabel(rs.getString(w));
					jl.setFont(new Font("ROMAN_BASELINE",Font.ITALIC,10));
					jl.setBounds(5+width, 35+height, 180, 30);
					area.add(jl);
					area.setRows(row);
					height+=30;
					row+=3;
				}
				width+=200;
				rs.beforeFirst();
			}
			create.close();
			jsp=new JScrollPane(area,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			list.add(jsp);
			ff.add(list);
			JButton ok=new JButton("OK");
			ok.setBounds(152, 335, 80, 20);
			ff.add(ok);
			ok.addActionListener
			(
					new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
						{
							QuerySQL.f.setEnabled(true);
							ff.dispose();
						}
					}	
			);
		}
		catch(Exception e)
		{
			ff.dispose();
			JOptionPane.showMessageDialog(ff,e.getMessage(), "Error",JOptionPane.ERROR_MESSAGE,new ImageIcon("m.png"));
			
		}
	}
}
