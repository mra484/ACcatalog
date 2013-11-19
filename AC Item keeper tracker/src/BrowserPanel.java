/**@author Mark Andrews
 * 11/4/2013
 * This class assembles the gui for the browser tab, action listeners are handled by the panels themselves
 * 
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;


public class BrowserPanel extends JPanel{
	private itemPane itemInfo = null;
	private itemPane searchInfo = new itemPane(1);
	private ItemSorter itemList;
	public static int total = 1;
	public static int owned = 1;
	
	private JLabel text = new JLabel();
	private JLabel text2 = new JLabel();
	private JLabel topText = new JLabel("Select an item from the list to display more detailed information on the right");
	private JLabel searchText = new JLabel("Narrow down the list by selecting categories to filter by below");
	private JPanel leftPanel = new JPanel();
	private JPanel textPanel = new JPanel();
	private JPanel southPanel = new JPanel();
	private GridBagConstraints c = new GridBagConstraints();
	
	public BrowserPanel(filer listManager, itemPane a){
		setLayout(new BorderLayout());
		itemInfo = a;
		itemList = new ItemSorter(listManager, searchInfo, itemInfo, new JList<String>(), this);
		itemInfo.setFiler(listManager);
		searchInfo.setSorter(itemList);
		searchInfo.setFiler(listManager);

		itemList.setBackground(Color.WHITE);
		text.setText(String.format("  Items Collected: %d / %d", listManager.getUserSize(), listManager.getTotalItems()));
		text2.setText(String.format("  Collection Percentage: %.2f%%",100*(double)listManager.getUserSize()/listManager.getTotalItems()));
		textPanel.setLayout(new BorderLayout());
		textPanel.add(text, BorderLayout.NORTH);
		textPanel.add(text2, BorderLayout.SOUTH);
		
		leftPanel.setLayout(new BorderLayout());
		leftPanel.add(itemList, BorderLayout.CENTER);
		leftPanel.add(textPanel, BorderLayout.SOUTH);
		
		southPanel.setBorder(BorderFactory.createEtchedBorder(1));
		southPanel.setLayout(new GridBagLayout());
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.NORTH;
		southPanel.add(searchText, c);
		
		c.gridy = 1;
		southPanel.add(searchInfo, c);
//		southPanel.add(searchText, BorderLayout.NORTH);
//		southPanel.add(searchInfo, BorderLayout.CENTER);
//		
		add(topText, BorderLayout.NORTH);
		add(leftPanel, BorderLayout.CENTER);
		add(itemInfo, BorderLayout.EAST);
		add(southPanel, BorderLayout.SOUTH);
	}
	
	public void update(){
		text.setText(String.format("  Items Collected: %d / %d", owned, total));
		text2.setText(String.format("  Collection Percentage: %%%.2f",100*(double)owned/(double)total));
	}
}
