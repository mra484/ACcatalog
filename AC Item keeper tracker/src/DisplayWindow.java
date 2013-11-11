/**@author Mark Andrews
 * 10/27/2014
 * 
 * This class handles the gui placement and the action handlers for each of the components
 */

import javax.swing.*;
import javax.swing.text.Position;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class DisplayWindow extends JFrame{

	//must be odd and >= 3
	public static final int listHeight = 13;
	public static int language = 1;
	public static boolean readOnly = true;
	public static boolean defaultOwned = false;
	public static boolean smallWindow = true;
	public static Point windowPos = null;
	public static boolean programStarted = false;
	private filer listManager = new filer(this);
	private searchPanel search;
	private BrowserPanel browse;
	private optionPane option;
	private JTabbedPane tabs = new JTabbedPane();
	
	
	
	public DisplayWindow(){
		super("Animal Crossing Item Catalogger");
		if( windowPos != null)
			setLocation(windowPos);
		
		search = new searchPanel(listManager);
		browse = new BrowserPanel(listManager);
		option = new optionPane(listManager);
		
		tabs.add("Add", search);
		tabs.add("Browse", browse);
		tabs.add("Option", option);
		
		
		add(tabs);
		setVisible(true);
		setSize(480,600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		programStarted = true;
	}
	
	public static void main(String [] args){
		new DisplayWindow();
	}
	
//	public void getWinLocation(){
//		windowPos = getLocation();
//	}
//	public double getXPos(){
//		return windowPos.getX();
//	}
//	
//	public double getYPos(){
//		return windowPos.getY();
//	}
	
}
