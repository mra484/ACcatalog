/**@author Mark Andrews
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
	public static final int listHeight = 13;
	public static int language = 1;
	
	private searchPanel search = new searchPanel();
	private BrowserPanel browse = new BrowserPanel(search.getFiler());
	private JTabbedPane tabs = new JTabbedPane();
	
	public DisplayWindow(){
		super("Animal Crossing Item Catalogger");
		tabs.add("Add", search);
		tabs.add("Browse", browse);
		
		
		add(tabs);
		setVisible(true);
		setSize(480,600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String [] args){
		new DisplayWindow();
	}

}
