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
 * 11/17/2013
 * A class for handling pop up windows that require user interaction such as items not found in the master list
 * and items with duplicate names 
 */

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Queue;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;


public class ItemCheckDialog extends JDialog{
	private static final long serialVersionUID = 1L;
	private JPanel top = new JPanel();
	private JPanel right = new JPanel();
	private JPanel left = new JPanel();
	private JLabel text = new JLabel();
	private JLabel text2 = new JLabel();
	private JLabel text3 = new JLabel();
	private String item1;
	private String item2;
	private JButton add = new JButton("Add");
	private JButton cancel = new JButton("Cancel");
	private JButton exit = new JButton ("Exit");
	private JButton b1 = new JButton ();
	private JButton b2 = new JButton ();
	
	private filer itemManager = null;
	private itemPane itemInfo = null;
	private ItemSorter itemList = null;
	private DisplayField listField;
	private Queue<String> missingItems;
	private itemHandler items = new itemHandler();
	private Entry blank = new Entry("", null);
	private boolean checkDuplicate = false;

	//constructer for handling items not found in the master list
	public ItemCheckDialog(filer listManager, Queue<String> missingList, DisplayWindow mainWindow){
		super(mainWindow, "Items not found");
		setSize(275, 245);
		setVisible(true);
		setLocation(new Point(mainWindow.getX()+100, mainWindow.getY()+100));

		setModal(true);
		setModalityType(ModalityType.APPLICATION_MODAL);

		this.itemManager = listManager;
		missingItems = missingList;
		
		//check first entry for the language character, skip it if found
		if(missingItems.peek().contains("@"))
			missingItems.poll();
		text.setText((String.format("\"%s\" not found in main list, ", missingItems.poll())));
		text2.setText("please select it manually or cancel to remove");
		top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
		top.add(text);
		top.add(text2);

		//item selection list on the left side of the window
		itemList = new ItemSorter(listManager, new JList<String>());
		left.setLayout(new FlowLayout(FlowLayout.LEFT));
		left.add(itemList);

		//interaction buttons on the right side
		add.addActionListener(items);
		cancel.addActionListener(items);
		exit.addActionListener(items);
		right.setLayout(new GridLayout(3,1,0,0));
		right.add(add);
		right.add(cancel);
		right.add(exit);

		text3.setText(String.format("Unknown items remaining: %d", missingItems.size()));

		setLayout(new BorderLayout());
		add(top, BorderLayout.NORTH);
		add(left, BorderLayout.WEST);
		add(right, BorderLayout.EAST);	
		add(text3, BorderLayout.SOUTH);
	}

	//constructor for handling "washbucket" duplicate item in english
	public ItemCheckDialog(filer listManager, DisplayWindow mainWindow, DisplayField sResults, itemPane info, int item ){
		super(mainWindow, "Item clarification");
		setSize(350, 245);
		setVisible(true);
		setLocation(new Point(mainWindow.getX()+100, mainWindow.getY()+100));
		setModal(true);
		setModalityType(ModalityType.APPLICATION_MODAL);
		
		itemManager = listManager;
		checkDuplicate = true;
		listField = sResults;
		itemInfo = info;
		
		switch(item){
		
		//Washbasin duplicate, english
		case 0:
			item1 = "washbasin1";
			item2 = "washbasin2";
			b1.setText("Washbasin (1)");
			b2.setText("Washbasin (2)");
			text.setText("Which Washbasin matches your item?" );
			text2.setText("(A bucket)");
			text3.setText("(A faucet)");
			break;
		
		//festival lantern/ red-tassled lantern duplicate italian
		case 1:
			item1 = "lanternacinese1";
			item2 = "lanternacinese2";
			b1.setText("Lanterna cinese (1)");
			b2.setText("Lanterna cinese (2)");
			text.setText("Which Lanterna cinese matches your item?" );
			text2.setText("(white lantern)");
			text3.setText("(red lantern)");
			break;
			
		//plate armor / samurai shirt duplicate japanese
		case 2:
			item1 = "\u304b\u3063\u3061\u3085\u30461";
			item2 = "\u304b\u3063\u3061\u3085\u30462";
			b1.setText("\u304b\u3063\u3061\u3085\u3046 (1)");
			b2.setText("\u304b\u3063\u3061\u3085\u3046 (2)");
			text.setText("Which \u304b\u3063\u3061\u3085\u3046 matches your item?" );
			text2.setText("(plate)");
			text3.setText("(samurai)");
			break;
		default:
			break;
		}
		
		b1.addActionListener(items);
		b2.addActionListener(items);
		
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		layout.setConstraints(text, c);
		add(text);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		layout.setConstraints(text2, c);
		add(text2);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.gridx = 1;
		c.gridy = 1;
		layout.setConstraints(text3, c);
		add(text3);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.gridx = 0;
		c.gridy = 2;
		layout.setConstraints(b1, c);
		add(b1);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.gridx = 1;
		c.gridy = 2;
		add(b2, c);
		
	}
	
	public void update(){
		text.setText((String.format("\"%s\" not found in main list, ", missingItems.poll())));
		text3.setText((String.format("Unknown items remaining: %d", missingItems.size())));
	}
	
	
	private class itemHandler implements ActionListener{
		public void actionPerformed(ActionEvent e){
			Entry item = null;
			
			//actions for the missing item dialog, if nothing is selected when add is pressed, it will 
			//skip the current entry like cancel
			if(!checkDuplicate){
			if(e.getSource() == add)
				if( itemList.getEntry() != null )
					itemManager.getList().get(blank.normalizeText(itemList.getEntry())).setOwned(true);
			
			if(e.getSource() == exit)
				System.exit(2);
			if(e.getSource() == cancel ){}
			if( missingItems.size() == 0){
				dispose();
				itemManager.saveFiles(1);
			}
			update();
		}
			
			//actions for the duplicate item dialog, word selected will be added and updated in the main window
			if(e.getSource() == b1)
				item = itemManager.getList().get(item1);
			
			if (e.getSource() == b2)
				item = itemManager.getList().get(item2);
			
			itemManager.addWord(item);
			itemManager.searchList(item, listField);
			itemInfo.update(item);
			itemManager.saveFiles(1);

			dispose();
		}
	}
}
