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
 * 11/10/2013
 * Class for managing program options.  The three variables that makes up the options are static
 * variables of DisplayWindow. 
 */
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.*;

public class optionPane extends JPanel{
	private static final long serialVersionUID = 1L;

	private JPanel contentPanel = new JPanel();
	
	private JComboBox<String> language = new JComboBox<String>();
	private JLabel languageLabel = new JLabel("Language");
	
	private JCheckBox setRO = new JCheckBox();
	private JLabel setROLabel = new JLabel("Make master list read only");

	private JCheckBox winSize = new JCheckBox();
//	private JLabel winSizeLabel = new JLabel("Compact window");
	
	private JCheckBox quickAdd = new JCheckBox();
	private JLabel quickAddLabel = new JLabel("Quick Add and Remove in Browser");
	
	private filer listManager = null;
	private itemPane itemInfo = null;
	private BrowserPanel browser = null;
	private DisplayWindow window = null;
	private ItemHandler actions = new ItemHandler();
	private ActionHandler action = new ActionHandler();
	
	public optionPane(filer a, itemPane b, BrowserPanel c, DisplayWindow d){
		listManager = a;
		itemInfo = b;
		browser = c;
		window = d;
		setLayout(new FlowLayout(FlowLayout.LEADING));
		contentPanel.setLayout(new GridLayout(3,2,10,10));
		
		language.addActionListener(action);
		setRO.addItemListener(actions);
		winSize.addItemListener(actions);
		quickAdd.addItemListener(actions);
		
		//fields for language selector
		language.addItem("English (EU)");
		language.addItem("English (US)");
		language.addItem("French");
		language.addItem("Italian");
		language.addItem("German");
		language.addItem("Spanish");
		language.addItem("Japanese");
		language.setMaximumSize(new Dimension(language.getMinimumSize()));
		
		//determine initial values for options
		language.setSelectedIndex(DisplayWindow.language);		
		setRO.setSelected(DisplayWindow.readOnly);
		winSize.setSelected(DisplayWindow.smallWindow);
		quickAdd.setSelected(DisplayWindow.quickAdd);
		
		contentPanel.add(languageLabel);
		contentPanel.add(language);
		contentPanel.add(setROLabel);
		contentPanel.add(setRO);
		contentPanel.add(quickAddLabel);
		contentPanel.add(quickAdd);
//		contentPanel.add(winSizeLabel);
//		contentPanel.add(winSize);
		add(contentPanel);
	}
	
	private class ItemHandler implements ItemListener{
		public void itemStateChanged(ItemEvent e){
			
			//changes should only be made if the program has moved past initialization stages
			if(!DisplayWindow.programStarted)
				return;
			
			if(e.getSource() == setRO){
				DisplayWindow.readOnly = setRO.isSelected();
				window.updateInfo();
			}
			if(e.getSource() == winSize)
				DisplayWindow.smallWindow = winSize.isSelected();
			if(e.getSource() == quickAdd){
				DisplayWindow.quickAdd = quickAdd.isSelected();
				browser.update();
			itemInfo.updateComboBoxes();
			listManager.saveFiles(0);
				

			}
		}
	}
	private class ActionHandler implements ActionListener{
		public void actionPerformed(ActionEvent e){
			//language can be changed when program is starting up, ignore actions from it
			if(!DisplayWindow.programStarted)
				return;

			DisplayWindow.language = language.getSelectedIndex();
			listManager.updateLanguage();
			listManager.saveFiles(3);
		}
	}
}
