/**@author Mark Andrews
 * 11/4/2013
 * This class assembles the gui for the browser tab, action listeners are handled by the panels themselves
 * 
 */

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JList;
import javax.swing.JPanel;


public class BrowserPanel extends JPanel{
	private itemPane itemInfo = new itemPane();
	private itemPane searchInfo = new itemPane(3);
	private ItemSorter itemList;
	
	public BrowserPanel(filer listReader){
		itemList = new ItemSorter(listReader, itemInfo);
		itemList.setBackground(Color.WHITE);
		setLayout(new BorderLayout());
		add(itemList, BorderLayout.CENTER);
		add(itemInfo, BorderLayout.EAST);
		add(searchInfo, BorderLayout.SOUTH);
	}
}
