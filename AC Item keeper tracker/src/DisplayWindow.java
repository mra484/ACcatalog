import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DisplayWindow extends JFrame{
	
	//must be odd and >= 3
	public static final int listHeight = 15;
	private DisplayField listField = new DisplayField();
	private JTextField textEntry = new JTextField(15);
	private JButton search = new JButton("Search");
	private JButton add = new JButton ("Add");
	private JButton remove = new JButton ("Remove");
	private ActionClass action = new ActionClass();
	private filer listReader = new filer();
	private JPanel bottomPanel = new JPanel();
	private JPanel buttonPanel = new JPanel();
	
	public DisplayWindow(){
		this.setLayout(new BorderLayout());

		bottomPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 20, 10));
		buttonPanel.setLayout(new FlowLayout());
		search.setSize(100,50);
		search.addActionListener(action);
		add.setSize(100,50);
		add.addActionListener(action);
		remove.setSize(100,50);
		remove.addActionListener(action);
		bottomPanel.add(textEntry);
		buttonPanel.add(search);
		buttonPanel.add(add);
		buttonPanel.add(remove);
		bottomPanel.add(buttonPanel);
		
		this.add(bottomPanel, BorderLayout.SOUTH);
		this.add(listField, BorderLayout.CENTER);
		this.setVisible( true );
		this.setSize( 500, 400 );
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	public static void main(String [] args){
		new DisplayWindow();
	}
	
	
	

private class ActionClass implements ActionListener{
	public void actionPerformed(ActionEvent e){
		Entry result = null;
		int state = 0;
		String searchText = textEntry.getText();
		if( searchText != null ){
			searchText = listReader.normalizeText(searchText);
			result = listReader.searchList(textEntry.getText());
		}
		else
			return;
		state = searchText.compareTo(result.searchName);
		if( state == 0)
			result = result.prev;
		
		listField.updateList(result, state);
		listReader.saveFiles();
		
		
		System.out.println();
	}
}

}
