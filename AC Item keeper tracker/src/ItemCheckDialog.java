/**@author Mark Andrews
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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
	private JButton add = new JButton("Add");
	private JButton cancel = new JButton("Cancel");
	private JButton exit = new JButton ("Exit");
	private JButton b1 = new JButton ("Washbasin (1)");
	private JButton b2 = new JButton ("Washbasin (2)");
	
	private filer itemManager = null;
	private itemPane itemInfo = null;
	private ItemSorter itemList = null;
	private DisplayField listField;
	private Queue<String> missingItems;
	private itemHandler items = new itemHandler();
	private Entry blank = new Entry("", null);
	private boolean checkWashbasin = false;

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
	public ItemCheckDialog(filer listManager, DisplayWindow mainWindow, DisplayField sResults, itemPane info ){
		super(mainWindow, "Item clarification");
		setSize(275, 245);
		setVisible(true);
		setLocation(new Point(mainWindow.getX()+100, mainWindow.getY()+100));
		setModal(true);
		setModalityType(ModalityType.APPLICATION_MODAL);
		
		itemManager = listManager;
		checkWashbasin = true;
		listField = sResults;
		itemInfo = info;
		
		text.setText("Which washbasin matches your item?");
		text2.setText("(A bucket)");
		text3.setText("(A faucet)");
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
			if(!checkWashbasin){
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
				item = itemManager.getList().get("washbasin1");
			
			if (e.getSource() == b2)
				item = itemManager.getList().get("washbasin2");
			
			itemManager.addWord(item);
			itemManager.searchList(item, listField);
			itemInfo.update(item);
			itemManager.saveFiles(1);

			dispose();
		}
	}
}
