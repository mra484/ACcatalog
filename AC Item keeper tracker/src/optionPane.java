/**@author Mark Andrews
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
	private JPanel contentPanel = new JPanel();
	
	private JComboBox<String> language = new JComboBox<String>();
	private JLabel languageLabel = new JLabel("Language");
	
	private JCheckBox setRO = new JCheckBox();
	private JLabel setROLabel = new JLabel("Make master list read only");
	
	private JCheckBox winSize = new JCheckBox();
	private JLabel winSizeLabel = new JLabel("Compact window");
	
	private filer listManager = null;
	private itemPane itemInfo = null;
	private ItemHandler actions = new ItemHandler();
	private ActionHandler action = new ActionHandler();
	
	public optionPane(filer a, itemPane b){
		listManager = a;
		itemInfo = b;
		setLayout(new FlowLayout(FlowLayout.LEADING));
		contentPanel.setLayout(new GridLayout(3,2,10,10));
		
		language.addActionListener(action);
		setRO.addItemListener(actions);
		winSize.addItemListener(actions);
		
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
		
		contentPanel.add(languageLabel);
		contentPanel.add(language);
		contentPanel.add(setROLabel);
		contentPanel.add(setRO);
		contentPanel.add(winSizeLabel);
		contentPanel.add(winSize);
		add(contentPanel);
	}
	
	private class ItemHandler implements ItemListener{
		public void itemStateChanged(ItemEvent e){
			//changes should only be made if the program has moved past initialization stages
			if(!DisplayWindow.programStarted)
				return;
			
			if(e.getSource() == setRO)
				DisplayWindow.readOnly = setRO.isSelected();
			if(e.getSource() == winSize)
				DisplayWindow.smallWindow = winSize.isSelected();
			itemInfo.updateComboBoxes();
			listManager.saveFiles(0);
				
			
		}
	}
	private class ActionHandler implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(!DisplayWindow.programStarted)
				return;

			DisplayWindow.language = language.getSelectedIndex();
			listManager.updateLanguage();
			listManager.saveFiles(3);
		}
	}
}
