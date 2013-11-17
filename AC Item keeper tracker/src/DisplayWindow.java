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
	
	private searchPanel search;
	private BrowserPanel browse;
	private optionPane option;
	private JTabbedPane tabs = new JTabbedPane();

	private filer listManager = new filer(this);
	private itemPane itemInfo = new itemPane();
	
	
	public DisplayWindow(){
		super("Animal Crossing Item Catalogger");
		if( windowPos != null)
			setLocation(windowPos);
		
		search = new searchPanel(listManager, this);
		browse = new BrowserPanel(listManager, itemInfo);
		option = new optionPane(listManager, itemInfo);
		
		tabs.add("Add", search);
		tabs.add("Browse", browse);
		tabs.add("Option", option);
		
		
		add(tabs);
		setVisible(true);
		setSize(480,600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		programStarted = true;
		
		//reconcile items not found in the list if there are any
		if(listManager.getMissingList().size() > 0)
			if( listManager.getMissingList().size() != 1 || !listManager.getMissingList().peek().contains("@"))
				new ItemCheckDialog(listManager, listManager.getMissingList(), this);
	}
	
	public static void main(String [] args){
		new DisplayWindow();
	}

}
