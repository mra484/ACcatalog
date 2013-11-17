/**@author Mark Andrews
 * 11/4/2013
 * This class assembles the gui for the browser tab, action listeners are handled by the panels themselves
 * 
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;


public class BrowserPanel extends JPanel{
	private itemPane itemInfo = null;
	private itemPane searchInfo = new itemPane(1);
	private ItemSorter itemList;
	public static int total = 1;
	public static int owned = 1;
	
	private JLabel text = new JLabel();
	private JLabel text2 = new JLabel();
	private JPanel leftPanel = new JPanel();
	private JPanel textPanel = new JPanel();
	
	public BrowserPanel(filer listManager, itemPane a){
		setLayout(new BorderLayout());
		itemInfo = a;
		itemList = new ItemSorter(listManager, searchInfo, itemInfo, new JList<String>(), this);
		itemInfo.setFiler(listManager);
		searchInfo.setSorter(itemList);
		searchInfo.setFiler(listManager);

		itemList.setBackground(Color.WHITE);
		text.setText(String.format("  Items Collected: %d / %d", listManager.getUserSize(), listManager.getTotalItems()));
		text2.setText(String.format("  Collection Percentage: %%%.2f",100*(double)listManager.getUserSize()/listManager.getTotalItems()));
		textPanel.setLayout(new BorderLayout());
		textPanel.add(text, BorderLayout.NORTH);
		textPanel.add(text2, BorderLayout.SOUTH);
		
		leftPanel.setLayout(new BorderLayout());
		leftPanel.add(itemList, BorderLayout.CENTER);
		leftPanel.add(textPanel, BorderLayout.SOUTH);
		add(leftPanel, BorderLayout.CENTER);
		add(itemInfo, BorderLayout.EAST);
		add(searchInfo, BorderLayout.SOUTH);
	}
	
	public void update(){
		text.setText(String.format("  Items Collected: %d / %d", owned, total));
		text2.setText(String.format("  Collection Percentage: %%%.2f",100*(double)owned/(double)total));
	}
}
