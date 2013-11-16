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
	private itemPane itemInfo = new itemPane();
	private JTextField textEntry = new JTextField(15);
	private Entry currentEntry = new Entry("", null);
	private JButton option = new JButton("Options");
	private JButton add = new JButton ("Add");
	private JButton remove = new JButton ("Remove");
	private ActionClass action = new ActionClass();
	private KeyClass key = new KeyClass();
	private filer listManager;
	private JPanel centerPanel = new JPanel();
	private JPanel bottomPanel = new JPanel();
	private JPanel buttonPanel = new JPanel();

	public searchPanel(filer a) {
		this.setLayout(new BorderLayout());
		listManager = a;
		itemInfo.setFiler(a);

		bottomPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 20, 10));
		buttonPanel.setLayout(new FlowLayout());

		add.addActionListener(action);
		remove.addActionListener(action);
		option.addActionListener(action);

		textEntry.addKeyListener(key);
		bottomPanel.add(textEntry);

		buttonPanel.add(add);
		buttonPanel.add(remove);
		//		buttonPanel.add(option);
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


	private class KeyClass implements KeyListener{
		public void keyPressed(KeyEvent e){
			//perform "add word" function on enter, refresh search result and highlight text
			if(e.getKeyCode() == KeyEvent.VK_ENTER){
				if (listManager.addWord(new Entry(textEntry.getText(), null)) ) {
					listManager.saveFiles();
					listManager.searchList(new Entry(textEntry.getText(), null), listField);
				}

				textEntry.setSelectionStart(0);
				textEntry.setSelectionEnd(textEntry.getText().length());

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


			if( e.getSource() == option){
				listManager.searchList(searchWord, listField);
			}

			if( e.getSource() == add ){
				if( listManager.addWord(searchWord) ){
					listManager.saveFiles();
					currentEntry = listManager.searchList(searchWord, listField);
			}
				itemInfo.update(currentEntry);
			}

			if( e.getSource() == remove){

				listManager.removeWord(searchWord);
				listManager.saveFiles();
				if(listManager.getUserSize() == 0 )
					return;
				currentEntry = listManager.searchList(searchWord, listField);
				itemInfo.update(currentEntry);
			}
		}
	}

}
