import javax.swing.*;
import java.awt.*;

public class DisplayWindow extends JFrame{
	private DisplayField listField = new DisplayField();
	private JTextField textEntry = new JTextField();
	
	public DisplayWindow(){
		this.setLayout(new BorderLayout());
		textEntry.setSize(300,100);
		this.add(listField, BorderLayout.WEST);
		this.add(textEntry, BorderLayout.SOUTH);
		this.setVisible( true );
		this.setSize( 300, 400 );
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	public static void main(String [] args){
		new DisplayWindow();
	}
	
}
