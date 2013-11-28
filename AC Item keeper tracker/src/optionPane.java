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

	private JCheckBox noPopup = new JCheckBox();
	private JLabel noPopupLabel = new JLabel("Disable autofinish");
	
	private JCheckBox noWarning = new JCheckBox();
	private JLabel noWarningLabel = new JLabel("Disable list size warning");
	
	private JCheckBox setRO = new JCheckBox();
	private JLabel setROLabel = new JLabel("Make master list read only");

	private JCheckBox winSize = new JCheckBox();
//	private JLabel winSizeLabel = new JLabel("Compact window");
	
	private JCheckBox quickAdd = new JCheckBox();
	private JLabel quickAddLabel = new JLabel("Quick add and remove in browser");
	
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
		contentPanel.setLayout(new GridLayout(5,2,10,10));
		
		String languageHelp = "Change the language that the item names appear";
		
		String readOnlyHelp = "Choose whether or not the main list can be edited.  " +
				"Information panels will be replaced with combo boxes if disabled.  " +
				"Should only need to be used if the entire main list is to be changed.  " +
				"Be sure to check \"Disable list warning\"";
		
		String quickAddHelp = "When enabled, clicking on an item in the browser list will toggle whether or not it is on the user list.  " + 
				"Can click and drag to toggle several at once.";
		
		String popupHelp = "When enabled, the auto complete will not be displayed in the \"Add\" tab.";
		
		String warningHelp = "When enabled, no warning message will be displayed when the main list item count drops below 3609.";
				
		language.addActionListener(action);
		setRO.addItemListener(actions);
		winSize.addItemListener(actions);
		quickAdd.addItemListener(actions);
		noPopup.addItemListener(actions);
		noWarning.addItemListener(actions);
		
		language.setToolTipText(languageHelp);
		languageLabel.setToolTipText(languageHelp);

		setRO.setToolTipText(readOnlyHelp);		
		setROLabel.setToolTipText(readOnlyHelp);
		
		quickAdd.setToolTipText(quickAddHelp);
		quickAddLabel.setToolTipText(quickAddHelp);
		
		noPopup.setToolTipText(popupHelp);
		noPopupLabel.setToolTipText(popupHelp);
		
		noWarning.setToolTipText(warningHelp);
		noWarningLabel.setToolTipText(warningHelp);
		
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
		noPopup.setSelected(DisplayWindow.popup);
		noWarning.setSelected(DisplayWindow.listWarning);
		
		contentPanel.add(languageLabel);
		contentPanel.add(language);
		contentPanel.add(setROLabel);
		contentPanel.add(setRO);
		contentPanel.add(quickAddLabel);
		contentPanel.add(quickAdd);
		contentPanel.add(noPopupLabel);
		contentPanel.add(noPopup);
		contentPanel.add(noWarningLabel);
		contentPanel.add(noWarning);
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
			
			if(e.getSource() == noPopup)
				DisplayWindow.popup = noPopup.isSelected();
			
			if(e.getSource() == noWarning)
				DisplayWindow.listWarning = noWarning.isSelected();
			
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
