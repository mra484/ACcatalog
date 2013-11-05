import java.awt.BorderLayout;

import javax.swing.JList;
import javax.swing.JPanel;


public class BrowserPanel extends JPanel{
	private itemPane itemInfo = new itemPane();
	private itemPane searchInfo = new itemPane(3);
	private JList itemList = new JList();
	
	public BrowserPanel(filer listReader){
		
		setLayout(new BorderLayout());
		add(itemList, BorderLayout.WEST);
		add(itemInfo, BorderLayout.EAST);
		add(searchInfo, BorderLayout.SOUTH);
	}
}
