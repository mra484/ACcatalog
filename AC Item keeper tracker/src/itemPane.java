/**@author Mark Andrews
 * 11/4/2013
 * This class mostly handles the item information panels.  Two different layouts can be created
 * depending on which constructor is used. 
 * 
 */

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.EtchedBorder;
public class itemPane extends JPanel{
	private static final long serialVersionUID = 1532607835222537991L;
	
	private Entry currentEntry = new Entry(" ", null);
	private Entry blankEntry = new Entry("--------------------------", null);
	private JPanel centerPlate = new JPanel();
	private GroupLayout layout = new GroupLayout(centerPlate);
	
	private JPanel namePanel = new JPanel();
	private Font nameFont = new Font("Large", Font.BOLD, 18);
	private JLabel name = new JLabel("--------------------------");
	
	private JLabel typeLabel = new JLabel("Item Type");
	private JComboBox<String> type = new JComboBox<String>();
	
	private JLabel furnitureLabel = new JLabel("Furniture");
	
	private JLabel seriesLabel = new JLabel("Furniture Series");
	private JComboBox<String> series = new JComboBox<String>();
	
	private JLabel setLabel = new JLabel("Furniture Set");
	private JComboBox<String> set = new JComboBox<String>();

	private JLabel themeLabel = new JLabel("Furniture Theme");
	private JComboBox<String> theme = new JComboBox<String>();

	private JLabel clothesLabel = new JLabel("Clothes");

	private JLabel clothesTypeLabel = new JLabel("Clothes Type");
	private JComboBox<String> clothes = new JComboBox<String>();

	private JLabel clothesStyleLabel = new JLabel("Clothes Style");
	private JComboBox<String> clothesStyle = new JComboBox<String>();

	private JButton searchButton = new JButton("Search");
	private JTextField searchField = new JTextField();
	private JLabel owned = new JLabel("Owned Items");
	private JCheckBox ownedCheck = new JCheckBox();
	
	
	private ItemUpdater updater = new ItemUpdater();
	private ActionHandler actions = new ActionHandler();
	private filer files = null;
	private ItemSorter sorter = null;
	
	public static enum itemType {ADD, BROWSE};
	
	public itemPane(){
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createEtchedBorder(1));
		namePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		name.setFont(nameFont);
		namePanel.add(name);
		this.add(namePanel, BorderLayout.NORTH);		
		populateLists();
		
		//create a layout that has the type on the left and the furniture attributes on the right with a gap between choices
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		layout.setHorizontalGroup(
				layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(typeLabel).addComponent(type))
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(seriesLabel).addComponent(series)
						.addComponent(setLabel).addComponent(set)
						.addComponent(themeLabel).addComponent(theme)));
		
		layout.setVerticalGroup(
				layout.createSequentialGroup()
					.addGap(30)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(typeLabel).addComponent(seriesLabel))
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(type).addComponent(series))
					.addGap(25)
					.addComponent(setLabel).addComponent(set)
					.addGap(25)
					.addComponent(themeLabel).addComponent(theme));
		
		centerPlate.setLayout(layout);
		
		
		this.add(centerPlate, BorderLayout.CENTER);
	}
	
	public itemPane(int a){
		populateLists();
//		searchField.setColumns(6);
		searchButton.addActionListener(actions);
		
		clothes.setMaximumSize(new Dimension(120, 25));
		clothes.addItemListener(updater);
		
		clothesStyle.setMaximumSize(new Dimension(120, 25));
		clothesStyle.addItemListener(updater);
		
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		layout.setHorizontalGroup(
			layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(typeLabel).addComponent(type))
//					.addComponent(searchField))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(furnitureLabel).addComponent(seriesLabel).addComponent(series)
					.addComponent(setLabel).addComponent(set).addComponent(themeLabel).addComponent(theme))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(clothesLabel).addComponent(clothesTypeLabel)
					.addComponent(clothes).addComponent(clothesStyleLabel).addComponent(clothesStyle))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(searchButton).addComponent(owned))
				.addComponent(ownedCheck));
		
		layout.setVerticalGroup(
			layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(furnitureLabel)
					.addComponent(clothesLabel)
					.addComponent(searchButton))				
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(typeLabel).addComponent(seriesLabel).addComponent(clothesTypeLabel))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(type).addComponent(series).addComponent(clothes))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
//						.addComponent(searchField)
					.addComponent(setLabel).addComponent(clothesStyleLabel))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(set).addComponent(clothesStyle))
				.addComponent(themeLabel)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(theme).addComponent(owned).addComponent(ownedCheck)));
					
		centerPlate.setLayout(layout);
		centerPlate.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		add(centerPlate);

	}
	
	public void populateLists(){
				//add types to choice
				type.addItem("Unknown");
				type.addItem("Furniture");
				type.addItem("Wallpaper");
				type.addItem("Carpet");
				type.addItem("Clothes");
				type.addItem("Paper");
				type.addItem("Songs");
				type.addItem("Fossils");
				type.addItem("Wet Suits");
				type.addItem("Streetpass");
				type.addItemListener(updater);
				type.setMaximumSize(type.getMinimumSize());
						
				//add lists to furniture parameters
				series.addItem("None");
				series.addItem("Balloon");
				series.addItem("Blue");
				series.addItem("Cabana");
				series.addItem("Cabin");
				series.addItem("Classic");
				series.addItem("Egg");
				series.addItem("Exotic");
				series.addItem("Golden");
				series.addItem("Gorgeous");
				series.addItem("Gracie");
				series.addItem("Green");
				series.addItem("Harvest");
				series.addItem("Ice");
				series.addItem("Jingle");
				series.addItem("Kiddie");
				series.addItem("Lovely");
				series.addItem("Mermaid");
				series.addItem("Modern Wood");
				series.addItem("Modern");
				series.addItem("Mushroom");
				series.addItem("Near Future");
				series.addItem("Pave");
				series.addItem("Pipe");
				series.addItem("Polka-Dot");
				series.addItem("Princess");
				series.addItem("Ranch");
				series.addItem("Regal");
				series.addItem("Robo");
				series.addItem("Rococo");
				series.addItem("Scandinavia");
				series.addItem("Simple");
				series.addItem("Sloppy");
				series.addItem("Snowman");
				series.addItem("Spooky");
				series.addItem("Stripe");
				series.addItem("Sweets");
				series.addItem("trump");
				series.addItemListener(updater);
				series.setMaximumSize(new Dimension(120, 25));
				
				set.addItem("None");
				set.addItem("Apple");
				set.addItem("Bear");
				set.addItem("Bonsai");
				set.addItem("Cactus");
				set.addItem("Cafe");
				set.addItem("Chess");
				set.addItem("Citrus");
				set.addItem("Classroom");
				set.addItem("Dharma");
				set.addItem("Drum");
				set.addItem("Fish");
				set.addItem("Frog");
				set.addItem("Guitar");
				set.addItem("Hospital");
				set.addItem("Insect");
				set.addItem("Japanese-Style");
				set.addItem("Lucky Cat");
				set.addItem("Mossy Garden");
				set.addItem("Museum");
				set.addItem("Nintendo");
				set.addItem("Office");
				set.addItem("Panda");
				set.addItem("Pear");
				set.addItem("Pine Bonsai");
				set.addItem("Pine");
				set.addItem("Plant");
				set.addItem("String");
				set.addItem("Study");
				set.addItem("Study");
				set.addItem("Totem Pole");
				set.addItem("Tulip");
				set.addItem("Vase");
				set.addItem("Watermelon");
				set.addItemListener(updater);
				set.setMaximumSize(new Dimension(120, 25));
				
				theme.addItem("None");
				theme.addItem("Backyard");
				theme.addItem("Boxing");
				theme.addItem("Construction");
				theme.addItem("Creepy");
				theme.addItem("Mad Scientist");
				theme.addItem("Mario");
				theme.addItem("Nursery");
				theme.addItem("Pirate");
				theme.addItem("SF");
				theme.addItem("Spa");
				theme.addItem("Space");
				theme.addItem("Western");
				theme.addItemListener(updater);
				theme.setMaximumSize(new Dimension(120, 25));
				
				clothes.addItem("All");
				clothes.addItem("Shirts");
				clothes.addItem("Pants / Skirts");
				clothes.addItem("Dresses");
				clothes.addItem("Hats");
				clothes.addItem("Accessories");
				clothes.addItem("Shoes and Socks");
				clothes.addItem("Umbrellas");
	}
	
	public void update(Entry search){
		if(search.searchName.length() == 0)
			search = blankEntry;
		
		currentEntry = search;
		type.setSelectedIndex(currentEntry.getType());
		series.setSelectedIndex(currentEntry.getSeries());
		set.setSelectedIndex(currentEntry.getSet());
		theme.setSelectedIndex(currentEntry.getTheme());
		name.setText(search.displayName);
	}
	
	public int getType(){
		return  type.getSelectedIndex();
	}
	public int getSeries(){
		return  series.getSelectedIndex();
	}
	public int getSet(){
		return  set.getSelectedIndex();
	}
	public int getTheme(){
		return  theme.getSelectedIndex();
	}
	public int getClothes(){
		return 0;
//		return clothes.getSelectedIndex();
	}
	public int getStyle(){
		return 0;
//		return  clothesStyle.getSelectedIndex();
	}
	
	public boolean getOwned(){
		return ownedCheck.isSelected();
	}
	public void setFiler(filer file){		
		files = file;
	}
	
	public void setSorter(ItemSorter a){
		sorter = a;
	}
	
	public class ItemUpdater implements ItemListener{
		public void itemStateChanged(ItemEvent e){
			
			currentEntry.setType((byte)type.getSelectedIndex());
			currentEntry.setSeries((byte)series.getSelectedIndex());
			currentEntry.setSet((byte)set.getSelectedIndex());
			currentEntry.setTheme((byte)theme.getSelectedIndex());
			if( files != null)
				files.saveFiles();
			
			//only allow furniture attributes to be changed if item is a piece of furniture
			if( type.getSelectedIndex() == 0){
				series.setEnabled(true);
				set.setEnabled(true);
				theme.setEnabled(true);
				clothes.setEnabled(true);
				clothesStyle.setEnabled(true);
			}else if(type.getSelectedIndex() < 4 && type.getSelectedIndex() > 0){
				series.setEnabled(true);
				set.setEnabled(true);
				theme.setEnabled(true);
				clothes.setEnabled(false);
				clothesStyle.setEnabled(false);
			} else if( type.getSelectedIndex() < 10) {
				series.setEnabled(false);
				set.setEnabled(false);
				theme.setEnabled(false);
				clothes.setEnabled(true);
				clothesStyle.setEnabled(true);
			} else {
				series.setEnabled(false);
				set.setEnabled(false);
				theme.setEnabled(false);
				clothes.setEnabled(false);
				clothesStyle.setEnabled(false);
			}
		}
	}
	
	public class ActionHandler implements ActionListener{
		public void actionPerformed(ActionEvent e){
			sorter.update();
		}
	}
}
