/**Animal Crossing Item Cataloger
 * Copyright(C) 2013 Mark Andrews
 * 
 *   Animal Crossing Item Cataloger is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   Animal Crossing Item Cataloger is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *   
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * 10/27/2013
 * 
 * This class handles the gui placement and the action handlers for each of the components
 */

import javax.swing.*;

import java.awt.*;

public class DisplayWindow extends JFrame{

	private static final long serialVersionUID = 1L;
	
	//must be odd and >= 3
	public static final int listHeight = 13;
	public static int language = 1;
	public static boolean readOnly = true;
	public static boolean defaultOwned = false;
	public static boolean smallWindow = true;
	public static boolean quickAdd = false;
	public static Point windowPos = null;
	public static Dimension windowDim;
	public static boolean programStarted = false;
	
	private searchPanel search;
	private BrowserPanel browse;
	private optionPane option;
	private ProgramInfo about;
	private JTabbedPane tabs = new JTabbedPane();

	private filer listManager = new filer(this);
	private itemPane itemInfo;
	private itemPane itemInfo2;
	
	
	public DisplayWindow(){
		super("Animal Crossing Item Cataloger");
		
		String os = System.getProperty("os.name");
		if ( os.contains("Windows") )
			windowDim = new Dimension(530,600);
		else
			windowDim = new Dimension(600,650);

		if( readOnly ){
			itemInfo = new itemPane(itemPane.DISPLAYPANELRO);
			itemInfo2 = new itemPane(itemPane.DISPLAYPANELRO);
			
		}
		else{
			itemInfo = new itemPane(itemPane.DISPLAYPANEL);
			itemInfo2 = new itemPane(itemPane.DISPLAYPANEL);
			
		}
		
		if( windowPos != null)
			setLocation(windowPos);
//		itemInfo.setReadOnly(readOnly);
		search = new searchPanel(listManager, this, itemInfo2);
		browse = new BrowserPanel(listManager, itemInfo, search);
		option = new optionPane(listManager, itemInfo, browse, this);
		about = new ProgramInfo();
		
		tabs.add("Add", search);
		tabs.add("Browse", browse);
		tabs.add("Options", option);
		tabs.add("About", about);
		
		
		add(tabs);
		setVisible(true);
		setSize(windowDim);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		programStarted = true;
		
		//reconcile items not found in the list if there are any
		if(listManager.getMissingList().size() > 0)
			if( listManager.getMissingList().size() != 1 || !listManager.getMissingList().peek().contains("@"))
				new ItemCheckDialog(listManager, listManager.getMissingList(), this);
	}
	public void updateInfo(){
		itemInfo.setReadOnly(readOnly);
		itemInfo2.setReadOnly(readOnly);
	}
	
	public static void main(String [] args){
		new DisplayWindow();
	}

}
