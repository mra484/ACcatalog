import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DisplayWindow extends JFrame{
	private DisplayField listField = new DisplayField();
	private JTextField textEntry = new JTextField();
	private JButton search = new JButton("Search");
	private ActionClass action = new ActionClass();
	private filer listReader = new filer();
	
	public DisplayWindow(){
		this.setLayout(new BorderLayout());
		search.setSize(100,50);
		search.addActionListener(action);
		textEntry.setSize(300,100);
		this.add(listField, BorderLayout.WEST);
		this.add(textEntry, BorderLayout.SOUTH);
		this.add(search, BorderLayout.NORTH);
		this.setVisible( true );
		this.setSize( 300, 400 );
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	public static void main(String [] args){
		new DisplayWindow();
	}
	
	

private class ActionClass implements ActionListener{
	public void actionPerformed(ActionEvent e){
		Entry result = null;
		boolean match = false;
		String searchText = textEntry.getText();
		if( searchText != null ){
			searchText = listReader.normalizeText(searchText);
			result = listReader.searchList(textEntry.getText());
		}
		else
			return;
		if( searchText.compareTo(result.searchName) == 0){
			match = true;
			result = result.prev;
		}
		
		listField.updateList(result, match);
		
		
		System.out.println();
	}
}

}
