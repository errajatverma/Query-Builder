package QueryBuilder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;

public class SeeSQL extends Query
{
	public SeeSQL(String query)
	{
		final JFrame f1=new JFrame("Query");
		f1.setLayout(null);
		f1.setVisible(true);
		f1.setBounds(350,290, 760, 190);
		f1.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		f1.getContentPane().setBackground(new Color(0,255,0));
		//f.setResizable(false);
		JPanel list=new JPanel();
		list.setBounds(20, 20, 700, 80);
		list.setLayout(new BorderLayout());
		JTextArea area=new JTextArea();
		area.setBackground(Color.YELLOW);
		area.setEditable(false);
		area.setBounds(0, 0, 700, 80);
		JScrollPane jsp=new JScrollPane(area,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		list.add(jsp);
		f1.add(list);
		////////////////////////////////////////
		JLabel index=new JLabel(query);
		index.setBounds(20, 12, 925, 30);
		area.add(index);
		String splt[]=query.split(" ");
		int h=0;
		for(@SuppressWarnings("unused") String sp:splt)
		{
			area.setColumns(h);
			h=h+25;
		}
		JButton ok=new JButton("OK");
		ok.setBounds(330,120,80,20);
		f1.add(ok);
		ok.addActionListener
		(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					f1.dispose();
				}
			}
		);
	}
}
