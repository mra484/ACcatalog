/** @author Mark Andrews
 * 11/4/2013
 * This class assembles the gui and handles the action listeners for the main
 * search and add tab.
 */

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.*;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class searchPanel extends JPanel{

	private DisplayField listField = new DisplayField();
	private itemPane itemInfo = null;
	private DisplayWindow mainWindow;
	private Entry currentEntry = new Entry("", null);
	
	private JTextField textEntry = new JTextField(15);
	private JButton add = new JButton ("Add");
	private JButton remove = new JButton ("Remove");
	private JLabel text = new JLabel("Enter an item below, use the add button or return key to add it to your list.");
	private JLabel text2 = new JLabel(" ");
	
	private ActionClass action = new ActionClass();
	private KeyClass key = new KeyClass();
	private filer listManager;
	private JPanel centerPanel = new JPanel();
	private JPanel bottomPanel = new JPanel();
	private JPanel buttonPanel = new JPanel();
	private GridBagConstraints c = new GridBagConstraints();
	

	public searchPanel(filer a, DisplayWindow mainWindow) {
		this.setLayout(new BorderLayout());
		this.mainWindow = mainWindow;
		itemInfo = new itemPane();
		listManager = a;
		itemInfo.setFiler(a);
		
		GridBagLayout layout = new GridBagLayout();
		layout = createLayout(layout);

//		bottomPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 20, 10));
//		buttonPanel.setLayout(new FlowLayout());

		add.addActionListener(action);
		remove.addActionListener(action);
		textEntry.addKeyListener(key);
		text2.setFont(new Font("Italics", Font.ITALIC, 12));
//		bottomPanel.add(textEntry);
//
//		buttonPanel.add(add);
//		buttonPanel.add(remove);
//		bottomPanel.add(buttonPanel);
		
//		bottomPanel.setLayout(new BorderLayout());
		buttonPanel.setLayout(layout);
		buttonPanel.add(text);
		buttonPanel.add(textEntry);
		buttonPanel.add(add);
		buttonPanel.add(remove);
		buttonPanel.add(text2);
//		bottomPanel.add(buttonPanel, BorderLayout.CENTER);
		

		listField.setBorder(BorderFactory.createEtchedBorder(1));
		centerPanel.setLayout(new BorderLayout());
		centerPanel.add(listField, BorderLayout.CENTER);
		centerPanel.add(itemInfo, BorderLayout.EAST);

		this.add(buttonPanel, BorderLayout.SOUTH);
		this.add(centerPanel, BorderLayout.CENTER);

	}
	
	public GridBagLayout createLayout(GridBagLayout layout){
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.weightx = 1;
		c.weighty = 1;
		c.anchor = GridBagConstraints.NORTHWEST;
		layout.setConstraints(text, c);
		
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = 1;
		c.gridy = 1;
		c.weightx = 5;
//		c.ipadx = 10;
		layout.setConstraints(textEntry, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.weightx = 1;
		c.ipadx = 0;
		layout.setConstraints(add, c);
		
		c.gridx = 2;
		layout.setConstraints(remove, c);
		
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = GridBagConstraints.REMAINDER;
		layout.setConstraints(text2, c);
		
		return layout;
	}

	public filer getFiler(){
		return listManager;
	}
	
	public void saveFiles(){
		if(DisplayWindow.readOnly)
			listManager.saveFiles(1);
		else
			listManager.saveFiles(3);

	textEntry.setSelectionStart(0);
	textEntry.setSelectionEnd(textEntry.getText().length());
		
	}


	private class KeyClass implements KeyListener{
		public void keyPressed(KeyEvent e){
			String item = "";
			//perform "add word" function on enter, refresh search result and highlight text
			if(e.getKeyCode() == KeyEvent.VK_ENTER){
				if(textEntry.getText() != null)
					item = textEntry.getText();
				
				//check for "washbasin" handle accordingly
				if(DisplayWindow.language < 2 && (item.startsWith("w") || item.startsWith("W"))){
					if(	item.toLowerCase().compareTo("washbasin") == 0){
						new ItemCheckDialog(listManager, mainWindow, listField, itemInfo);
						return;
					}

				}
				if (listManager.addWord(new Entry(item, null)) ) {
					text2.setForeground(new Color(5, 128, 15));
					text2.setText(item + " successfully added to the list");
					listManager.searchList(new Entry(item, null), listField);
					saveFiles();
				} else{
					text2.setForeground(Color.RED);
					text2.setText(item + " not found in the list");
					saveFiles();
				}
					

			}
		}

		@Override
		public void keyTyped(KeyEvent e) {
			//dynamic search didn't work well under this method, after the first letter, it was always 1 letter behind
		}

		@Override
		public void keyReleased(KeyEvent e) {
			//perform search as user types
			text2.setText(" ");
			if(listManager.getUserSize() == 0 )
				return;
			if(/*e.getKeyCode() != KeyEvent.VK_ENTER &&*/ textEntry.getText() != null){
				currentEntry = listManager.searchList(new Entry(textEntry.getText(), null), listField);
				itemInfo.update(currentEntry);
			}

		}
	}

	private class ActionClass implements ActionListener{
		public void actionPerformed(ActionEvent e){
			Entry searchWord = null;

			if( textEntry.getText() == null)
				return;

			searchWord = new Entry(textEntry.getText(), null);

			//same function as pressing return
			if( e.getSource() == add ){
				if(DisplayWindow.language < 2 && (searchWord.searchName.startsWith("w"))){
					if(	searchWord.searchName.compareTo("washbasin") == 0){
						new ItemCheckDialog(listManager, mainWindow, listField, itemInfo);
						return;
					}
				}
			
				if( listManager.addWord(searchWord) ){
					text2.setForeground(new Color(5, 128, 15));
					text2.setText(searchWord.displayName + " successfully added to the list");
					saveFiles();
					currentEntry = listManager.searchList(searchWord, listField);
			} else {
				text2.setForeground(Color.RED);
				text2.setText(searchWord.displayName + " not found in the list");
				saveFiles();
				
			}
				itemInfo.update(currentEntry);
			}

			//removes word from masterIndex if not readOnly, switches to notOwned if it is readOnly
			if( e.getSource() == remove){

				if( listManager.removeWord(searchWord) ){
					text2.setForeground(new Color(5, 128, 15));
					text2.setText(searchWord.displayName + " successfully removed from the list");
					saveFiles();
				} else {
					text2.setForeground(Color.RED);
					text2.setText(searchWord.displayName + " not found in the list");
				}
				if(listManager.getUserSize() == 0 )
					return;
				currentEntry = listManager.searchList(searchWord, listField);
				itemInfo.update(currentEntry);
			}
		}
	}

}
