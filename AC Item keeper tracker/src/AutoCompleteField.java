import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.BorderLayout;
import java.util.TreeSet;
import java.util.TreeMap;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class AutoCompleteField extends JPanel{
	private int listSize = 0;
	private TreeMap<String, Entry> itemList;
	private String[] remainingList = null;
	private JScrollPane scroll = null;
	private JList<String> list = null;
	private boolean skipSelection = false;
	private boolean selectionMade = false;
	private boolean skipSearch = false;
	private JTextField textEntry = new JTextField(15);
	private JPopupMenu pop = new JPopupMenu();
	private Entry currentEntry = new Entry(" ", null);
	private boolean listChange = false;

	private JLabel text = new JLabel("Enter an item below, use the add button or return key to add it to your list.");
	private JLabel text2 = new JLabel(" ");
	private KeyClass key = new KeyClass();
	private SelectionHandler select = new SelectionHandler();
	
	private searchPanel search;
	
	public AutoCompleteField(searchPanel a, TreeMap<String, Entry> b){

		//set up text and buttons for the bottom of the tab
		pop.setFocusable(false);
		pop.setPopupSize(new Dimension(300, 100));

		textEntry.addKeyListener(key);
		text2.setFont(new Font("Italics", Font.ITALIC, 12));
		setLayout(new BorderLayout());
		add(text);
		add(textEntry);
		add(text2);

	}

	public void popupSelected(){
		selectionMade = true;

		//add item to text entry, remove popupmenu and deselect
		textEntry.setText(list.getSelectedValue());
		pop.setVisible(false);
		list.setSelectedIndex(-1);

		//update item information
		search.update(textEntry.getText());
	}

	//creates a list of Strings that start with the text in the textEntry field and adds it to the popupmenu
	public void populatePopup(){
		if( DisplayWindow.popup )
			return;
		list = new JList<String>();
		TreeSet<String> sList = new TreeSet<String>();
		list.addListSelectionListener(select);

		if( scroll != null)
			pop.remove(scroll);
		pop.setVisible(false);
		scroll = new JScrollPane(list);
		scroll.setBorder(null);

		int i = 0;
		if( listSize == 0){
			for(Entry a: itemList.values()){
				if( a.searchName.startsWith(currentEntry.normalizeText(textEntry.getText()))){
					sList.add(a.displayName);
					i++;
				}
			}

		} else {
			for(String a: remainingList){
				if(currentEntry.normalizeText(a).startsWith(currentEntry.normalizeText(textEntry.getText()))){
					sList.add(a);
					i++;
				}
			}			
		}
		remainingList = sList.toArray(new String[i]);
		list.setListData(remainingList);
		listSize = i;
		pop.add(scroll);
		pop.show(textEntry, textEntry.getX(), textEntry.getY()+9 );
		pop.setPopupSize(textEntry.getWidth(), (listSize <= 5 ? 20*listSize : 100));
	}

	public void setSelectionStart(int a){
		textEntry.setSelectionStart(a);
	}
	
	public void setSelectionEnd(int a){
		textEntry.setSelectionEnd(a);
	}
	public String getText(){
		return textEntry.getText();
	}
	public void saveFiles(){
		listChange = true;
		textEntry.setSelectionStart(0);
		textEntry.setSelectionEnd(textEntry.getText().length());
		search.saveFiles();
	}
	private class KeyClass implements KeyListener{

		public void keyPressed(KeyEvent e){
			String item = "";
			Entry current;
			int result;
			//perform "add word" function on enter, refresh search result and highlight text
			if(e.getKeyCode() == KeyEvent.VK_ENTER){

				//if the enter key is pressed when something is selected and the window is visible
				//move to textEntry field and return;
				if(list.getSelectedIndex() != -1 && pop.isVisible()){
					popupSelected();
					return;
				}
				listChange = true;
				if(textEntry.getText() != null)
					item = textEntry.getText();

				//check duplicates, carry out the rest of the add operation from there
				if( checkDuplicates(item)){
					browser.update();
					return;
				}

				//get info from itemPane if saving attributes, only on readonly
				if(!DisplayWindow.readOnly && listManager.getUserSize() != 0){
					current = itemInfo.getEntry();
				} else
					current = new Entry(item, null);

				//try adding the word and remove the popup menu
				result = listManager.addWord(current);
				pop.setVisible(false);

				//report the result on screen
				if (result == 2 ) {
					text2.setForeground(new Color(5, 128, 15));
					text2.setText(item + " successfully added to the list");
					BrowserPanel.owned++;
					listManager.searchListControl(new Entry(item, null));
					saveFiles();

					//update the browser list if the list has been changed
					browser.update();

				} else{
					text2.setForeground(Color.RED);
					if(result == 1)
						text2.setText(item + " was not found in the main list");
					else
						text2.setText(item + " is already in the user list");

					saveFiles();
					skipSearch = true;
				}
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {
			//dynamic search didn't work well under this method, after the first letter, it was always 1 letter behind
		}

		//perform search as user types
		public void keyReleased(KeyEvent e) {
			if( skipSearch ){
				skipSearch = false;
				return;
			}

			//after add attempt, remove the result text on the next key stroke
			if( !listChange )
				text2.setText(" ");
			listChange = false;

			//behavior for arrow keys, only if the list isn't empty
			if(listSize != 0){
				if( e.getKeyCode() == KeyEvent.VK_UP ){
					skipSelection = true;
					if( list.getSelectedIndex() == -1)
						list.setSelectedIndex(0);
					else if( list.getSelectedIndex() == 0 )
						list.setSelectedIndex(listSize-1);
					else
						list.setSelectedIndex(list.getSelectedIndex()-1);
					list.ensureIndexIsVisible(list.getSelectedIndex());
					return;
				} else if( e.getKeyCode() == KeyEvent.VK_DOWN ){
					skipSelection = true;
					if( list.getSelectedIndex() == -1)
						list.setSelectedIndex(0);
					else if( list.getSelectedIndex() == listSize-1 )
						list.setSelectedIndex(0);
					else
						list.setSelectedIndex(list.getSelectedIndex()+1);
					list.ensureIndexIsVisible(list.getSelectedIndex());
					return;
				}
			}

			if( textEntry.getText().length() == 1){
				listSize = 0;
				selectionMade = false;
			}

			//skip search operations if there are no items in the user list, arrow keys still need to be available
			if(listManager.getUserSize() == 0 )
				return;

			if( e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
				listSize = 0;
				selectionMade = false;
			}

			//compile a list of items that starts with the current textfield string if the size has not been narrowed to 1
			if(listSize != 1 && !selectionMade)
				populatePopup();

			//perform a search to find the position the current string would fall in the display field
			//search still needs to be performed after autofinish selection has taken place
			if( textEntry.getText() != null ){

				//item information to be displayed will be returned
				currentEntry = listManager.searchListControl(new Entry(textEntry.getText(), null));
				itemInfo.update(currentEntry);

				//if the last keystroke was a backspace, and the textField is empty, reset the popup menu
				if( e.getKeyCode() == KeyEvent.VK_BACK_SPACE && textEntry.getText().compareTo("") == 0){
					pop.setVisible(false);
				}
			}
		}
	}

	//handles the popupmenu's jlist
	private class SelectionHandler implements ListSelectionListener{
		public void valueChanged(ListSelectionEvent e){

			//listener is skipped if the value change was due to an arrow key
			if( skipSelection){
				skipSelection = false;
				return;
			}
			popupSelected();
		}
	}
}
