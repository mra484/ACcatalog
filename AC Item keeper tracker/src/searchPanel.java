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

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class searchPanel extends JPanel{

	private DisplayField listField = new DisplayField();
	private itemPane itemInfo = null;
	private DisplayWindow mainWindow;
	private JTextField textEntry = new JTextField(15);
	private Entry currentEntry = new Entry("", null);
	private JButton add = new JButton ("Add");
	private JButton remove = new JButton ("Remove");
	private ActionClass action = new ActionClass();
	private KeyClass key = new KeyClass();
	private filer listManager;
	private JPanel centerPanel = new JPanel();
	private JPanel bottomPanel = new JPanel();
	private JPanel buttonPanel = new JPanel();

	public searchPanel(filer a, DisplayWindow mainWindow) {
		this.setLayout(new BorderLayout());
		this.mainWindow = mainWindow;
		itemInfo = new itemPane();
		listManager = a;
		itemInfo.setFiler(a);

		bottomPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 20, 10));
		buttonPanel.setLayout(new FlowLayout());

		add.addActionListener(action);
		remove.addActionListener(action);

		textEntry.addKeyListener(key);
		bottomPanel.add(textEntry);

		buttonPanel.add(add);
		buttonPanel.add(remove);
		bottomPanel.add(buttonPanel);

		listField.setBorder(BorderFactory.createEtchedBorder(1));
		centerPanel.setLayout(new BorderLayout());
		centerPanel.add(listField, BorderLayout.CENTER);
		centerPanel.add(itemInfo, BorderLayout.EAST);

		this.add(bottomPanel, BorderLayout.SOUTH);
		this.add(centerPanel, BorderLayout.CENTER);

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
					listManager.searchList(new Entry(item, null), listField);
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
					saveFiles();
					currentEntry = listManager.searchList(searchWord, listField);
			}
				itemInfo.update(currentEntry);
			}

			//removes word from masterIndex if not readOnly, switches to notOwned if it is readOnly
			if( e.getSource() == remove){

				listManager.removeWord(searchWord);
				saveFiles();
				if(listManager.getUserSize() == 0 )
					return;
				currentEntry = listManager.searchList(searchWord, listField);
				itemInfo.update(currentEntry);
			}
		}
	}

}
