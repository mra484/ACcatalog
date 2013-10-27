/*@ Mark Andrews
 * 10/27/2014
 * 
 * This class handles the gui placement and the action handlers for each of the components
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class DisplayWindow extends JFrame{

	//must be odd and >= 3
	public static final int listHeight = 15;
	private DisplayField listField = new DisplayField();
	private JTextField textEntry = new JTextField(15);
	private JButton search = new JButton("Search");
	private JButton add = new JButton ("Add");
	private JButton remove = new JButton ("Remove");
	private ActionClass action = new ActionClass();
	private KeyClass key = new KeyClass();
	private filer listReader = new filer();
	private JPanel bottomPanel = new JPanel();
	private JPanel buttonPanel = new JPanel();

	public DisplayWindow(){
		super("Animal Crossing Item Catalog");
		this.setLayout(new BorderLayout());

		bottomPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 20, 10));
		buttonPanel.setLayout(new FlowLayout());

		search.setSize(100,50);
		search.setVisible(false);
		search.addActionListener(action);

		add.setSize(100,50);
		add.addActionListener(action);

		remove.setSize(100,50);
		remove.addActionListener(action);

		textEntry.addKeyListener(key);
		bottomPanel.add(textEntry);
		
		buttonPanel.add(search);
		buttonPanel.add(add);
		buttonPanel.add(remove);
		bottomPanel.add(buttonPanel);

		this.add(bottomPanel, BorderLayout.SOUTH);
		this.add(listField, BorderLayout.CENTER);
		this.setVisible( true );
		this.setSize( 500, 500 );
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public static void main(String [] args){
		new DisplayWindow();
	}


	private class KeyClass implements KeyListener{
		public void keyPressed(KeyEvent e){
			//perform "add word" function on enter, refresh search result and highlight text
			if(e.getKeyCode() == KeyEvent.VK_ENTER){
				listReader.addWord(new Entry(textEntry.getText(), null));
				listReader.saveFiles();
				listReader.searchList(new Entry(textEntry.getText(), null), listField);
				
				textEntry.setSelectionStart(0);
				textEntry.setSelectionEnd(textEntry.getText().length());
				
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {
			//dynamic search didn't work under this method, after the first letter, it was always 1 letter behind
		}

		@Override
		public void keyReleased(KeyEvent e) {
			//perform search as user types
			if(e.getKeyCode() != KeyEvent.VK_ENTER && textEntry.getText() != null){
				listReader.searchList(new Entry(textEntry.getText(), null), listField);
			}
			
		}
	}

	private class ActionClass implements ActionListener{
		public void actionPerformed(ActionEvent e){
			Entry searchWord = null;
			
			if( textEntry.getText() == null)
				return;
			
			searchWord = new Entry(textEntry.getText(), null);
			

			if( e.getSource() == search){
				listReader.searchList(searchWord, listField);
			}

			if( e.getSource() == add ){
					listReader.addWord(searchWord);
					listReader.saveFiles();
					listReader.searchList(searchWord, listField);
			}

			if( e.getSource() == remove){
					listReader.removeWord(searchWord);
					listReader.saveFiles();
					listReader.searchList(searchWord, listField);
			}
		}
	}

}
