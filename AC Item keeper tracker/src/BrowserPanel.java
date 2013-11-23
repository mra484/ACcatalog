/**Animal Crossing Item Cataloger
 * Copyright(C) 2013 Mark Andrews
 * 
 *   Animal Crossing Item Cataloger is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   Animal Crossing Item Cataloger is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *   
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *   
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


public class BrowserPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private itemPane itemInfo = null;
	private itemPane searchInfo = new itemPane(itemPane.SEARCHPANEL);
	private ItemSorter itemList;
	private searchPanel search;
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
	
	public BrowserPanel(filer listManager, itemPane a, searchPanel b){
		setLayout(new BorderLayout());
		itemInfo = a;
		search = b;
		itemList = new ItemSorter(listManager, searchInfo, itemInfo, new JList<String>(), this);
		itemInfo.setFiler(listManager);
		searchInfo.setSorter(itemList);
		searchInfo.setFiler(listManager);
		search.setBrowser(this);

		//arrange the counter text for the left panel
		itemList.setBackground(Color.WHITE);
		text.setText(String.format("  Items Collected: %d / %d", listManager.getUserSize(), listManager.getTotalItems()));
		text2.setText(String.format("  Collection Percentage: %.2f%%",100*(double)listManager.getUserSize()/listManager.getTotalItems()));
		textPanel.setLayout(new BorderLayout());
		textPanel.add(text, BorderLayout.NORTH);
		textPanel.add(text2, BorderLayout.SOUTH);
		
		//add items to the left panel
		leftPanel.setLayout(new BorderLayout());
		leftPanel.add(itemList, BorderLayout.CENTER);
		leftPanel.add(textPanel, BorderLayout.SOUTH);
		
		//arrange text and search information to bottom panel
		southPanel.setBorder(BorderFactory.createEtchedBorder(1));
		southPanel.setLayout(new GridBagLayout());
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.NORTH;
		southPanel.add(searchText, c);
		
		c.gridy = 1;
		southPanel.add(searchInfo, c);
		
		add(topText, BorderLayout.NORTH);
		add(leftPanel, BorderLayout.CENTER);
		add(itemInfo, BorderLayout.EAST);
		add(southPanel, BorderLayout.SOUTH);
	}
	
	public void update(){
		if(DisplayWindow.quickAdd){
			topText.setForeground(Color.RED);
			topText.setText("Caution: quick add and remove is currently enabled in options");
		} else {
			topText.setForeground(Color.BLACK);
			topText.setText("Select an item from the list to display more detailed information on the right");
		}
		text.setText(String.format("  Items Collected: %d / %d", owned, total));
		text2.setText(String.format("  Collection Percentage: %%%.2f",100*(double)owned/(double)total));
	}
}
