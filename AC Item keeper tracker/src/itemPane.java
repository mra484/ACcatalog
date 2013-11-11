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
	
	private boolean isSearchPanel = false;
	private boolean skipListener = false;
	
	private Entry currentEntry = new Entry(" ", null);
	private Entry blankEntry = new Entry("--------------------------", null);
	private JPanel centerPlate = new JPanel();
	private GroupLayout layout = new GroupLayout(centerPlate);
	
	private JPanel namePanel = new JPanel();
	private Font nameFont = new Font("Large", Font.BOLD, 18);
	private JLabel name = new JLabel("--------------------------");
	
	private JLabel typeLabel = new JLabel("Item Type");
	private JComboBox<String> type = new JComboBox<String>();
	
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
	
	private JLabel furnitureLabel = new JLabel("Furniture Type");
	private JComboBox<String> furniture = new JComboBox<String>();

	private JButton searchButton = new JButton("Search");
	private JTextField searchField = new JTextField();
	
	private JPanel ownedPanel = new JPanel();
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
		layout = createLayout(1);

		centerPlate.setLayout(layout);
		
		
		this.add(centerPlate, BorderLayout.CENTER);
	}
	
	public itemPane(int a){
		isSearchPanel = true;
		populateLists();
//		searchField.setColumns(6);
		searchButton.addActionListener(actions);
		
		clothes.setMaximumSize(new Dimension(120, 25));
//		clothes.addItemListener(updater);
		clothes.addActionListener(actions);
		
		clothesStyle.setMaximumSize(new Dimension(120, 25));
//		clothesStyle.addItemListener(updater);
		clothesStyle.addActionListener(actions);
		
		ownedPanel.add(owned);
		ownedPanel.add(ownedCheck);
		
		layout = createLayout(2);	

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
				type.addItem("Wet Suits");
				type.addItem("Streetpass");
//				type.addItemListener(updater);
				type.addActionListener(actions);
				type.setMaximumSize(new Dimension(120, 25));
				
				furniture.addItem("Unknown");
				furniture.addItem("Gyroids");
				furniture.addItem("Fossils");
				furniture.addItem("Insects");
				furniture.addItem("Fish");
				furniture.addItem("Seafood");
				furniture.addItem("Art");
				furniture.addItem("Flowers");
				furniture.addItem("Villager Pictures");
				furniture.addItem("Fossil Models");
				furniture.addItem("Other");
//				furniture.addItemListener(updater);
				furniture.addActionListener(actions);
				furniture.setMaximumSize(new Dimension(120, 25));
						
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
//				series.addItemListener(updater);
				series.addActionListener(actions);
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
//				set.addItemListener(updater);
				set.addActionListener(actions);
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
//				theme.addItemListener(updater);
				theme.addActionListener(actions);
				theme.setMaximumSize(new Dimension(120, 25));
				
				clothes.addItem("All");
				clothes.addItem("Shirts");
				clothes.addItem("Pants / Skirts");
				clothes.addItem("Dresses");
				clothes.addItem("Hats");
				clothes.addItem("Accessories");
				clothes.addItem("Shoes and Socks");
				clothes.addItem("Umbrellas");
				clothes.setMaximumSize(new Dimension(120, 25));

				clothesStyle.addItem("Unknown");
				clothesStyle.addItem("Flashy");
				clothesStyle.addItem("Cute");
				clothesStyle.addItem("Official");
				clothesStyle.addItem("Ornate");
				clothesStyle.addItem("Modern");
				clothesStyle.addItem("Historical");
				clothesStyle.addItem("Rock");
				clothesStyle.addItem("Basic");
				clothesStyle.addItem("Sporty");
				clothesStyle.addItem("Iconic");
				clothesStyle.setMaximumSize(new Dimension(120, 25));
				
				
	}
	
	public GroupLayout createLayout(int a){
		switch(a){
		case 1:
			//create a layout that has the type on the left and the furniture attributes on the right with a gap between choices
			layout.setAutoCreateGaps(true);
			layout.setAutoCreateContainerGaps(true);
			layout.setHorizontalGroup(
					layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
							.addComponent(typeLabel).addComponent(type).addComponent(furnitureLabel)
							.addComponent(furniture).addComponent(clothesLabel).addComponent(clothes))
							.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
									.addComponent(seriesLabel).addComponent(series)
									.addComponent(setLabel).addComponent(set)
									.addComponent(themeLabel).addComponent(theme)
									.addComponent(clothesStyleLabel).addComponent(clothesStyle)));

			layout.setVerticalGroup(
					layout.createSequentialGroup()
					.addGap(30)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
							.addComponent(typeLabel).addComponent(seriesLabel))
							.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
									.addComponent(type).addComponent(series))
									.addGap(25)
							.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
									.addComponent(furnitureLabel).addComponent(setLabel))
							.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
									.addComponent(furniture).addComponent(set))
									.addGap(25)
									.addComponent(themeLabel).addComponent(theme)
									.addGap(25)
							.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
									.addComponent(clothesLabel).addComponent(clothesStyleLabel))
							.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
									.addComponent(clothes).addComponent(clothesStyle)));
		break;
		case 2:
			//create a layout for the bottom panel of the browse tab
			layout.setAutoCreateGaps(true);
			layout.setAutoCreateContainerGaps(true);
			layout.setHorizontalGroup(
					layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
							.addComponent(typeLabel).addComponent(type).addComponent(furnitureLabel)
							.addComponent(furniture).addComponent(ownedPanel))
							//						.addComponent(searchField))
							.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
									.addComponent(seriesLabel).addComponent(series)
									.addComponent(setLabel).addComponent(set).addComponent(themeLabel).addComponent(theme))
									.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
											.addComponent(clothesTypeLabel)
											.addComponent(clothes).addComponent(clothesStyleLabel).addComponent(clothesStyle)
											.addComponent(searchButton)));

			layout.setVerticalGroup(
					layout.createSequentialGroup()
							.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
									.addComponent(typeLabel).addComponent(seriesLabel).addComponent(clothesTypeLabel))
							.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
											.addComponent(type).addComponent(series).addComponent(clothes))
							.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
													.addComponent(furnitureLabel)
													.addComponent(setLabel).addComponent(clothesStyleLabel))
							.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
															.addComponent(furniture)
															.addComponent(set).addComponent(clothesStyle))
							.addComponent(themeLabel)
							.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
																	.addComponent(ownedPanel).addComponent(theme).addComponent(searchButton)));
						
			break;
			default:
				break;
		}
		return layout;
		
	}
	public void update(Entry search){
		if(search.searchName.length() == 0)
			search = blankEntry;
		
		currentEntry = search;
		skipListener = true;
		type.setSelectedIndex(currentEntry.getType());
		series.setSelectedIndex(currentEntry.getSeries());
		set.setSelectedIndex(currentEntry.getSet());
		theme.setSelectedIndex(currentEntry.getTheme());
		name.setText(search.displayName);
		skipListener = false;
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
		return clothes.getSelectedIndex();
	}
	public int getStyle(){
		return  clothesStyle.getSelectedIndex();
	}
	public int getFurniture(){
		return furniture.getSelectedIndex();
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
			
			//updates item's attributes on change
			currentEntry.setType((byte)type.getSelectedIndex());
			currentEntry.setSeries((byte)series.getSelectedIndex());
			currentEntry.setSet((byte)set.getSelectedIndex());
			currentEntry.setTheme((byte)theme.getSelectedIndex());
			currentEntry.setClothes((byte)clothes.getSelectedIndex());
			currentEntry.setStyle((byte)clothesStyle.getSelectedIndex());
			currentEntry.setFurniture((byte)furniture.getSelectedIndex());
			
			if( files != null)
				files.saveFiles();
			
			//only allow item attributes to be changed if that item should have that type of attribute
			if( DisplayWindow.readOnly && !isSearchPanel){
				type.setEnabled(false);
				series.setEnabled(false);
				set.setEnabled(false);
				theme.setEnabled(false);
				furniture.setEnabled(false);
				clothes.setEnabled(false);
				clothesStyle.setEnabled(false);
				
				//case for when "all" is selected in type
			}else if( type.getSelectedIndex() == 0){
				series.setEnabled(true);
				set.setEnabled(true);
				theme.setEnabled(true);
				furniture.setEnabled(true);
				clothes.setEnabled(true);
				clothesStyle.setEnabled(true);
				
				//case for when furniture, wallpaper, carpet are selected in type
			}else if(type.getSelectedIndex() < 4 && type.getSelectedIndex() > 0){
				series.setEnabled(true);
				set.setEnabled(true);
				theme.setEnabled(true);
				furniture.setEnabled(true);
				clothes.setEnabled(false);
				clothesStyle.setEnabled(false);
				
				//case for when clothes are selected as type
			} else if( type.getSelectedIndex() == 4) {
				series.setEnabled(false);
				set.setEnabled(false);
				theme.setEnabled(false);
				furniture.setEnabled(false);
				clothes.setEnabled(true);
				clothesStyle.setEnabled(true);
			} else {
				series.setEnabled(false);
				set.setEnabled(false);
				theme.setEnabled(false);
				furniture.setEnabled(false);
				clothes.setEnabled(false);
				clothesStyle.setEnabled(false);
			}
		}
	}
	
	public class ActionHandler implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(skipListener)
				return;
			if(e.getSource() == searchButton)	
				sorter.update();
			else {	
				//updates item's attributes on change
				currentEntry.setType((byte)type.getSelectedIndex());
				currentEntry.setSeries((byte)series.getSelectedIndex());
				currentEntry.setSet((byte)set.getSelectedIndex());
				currentEntry.setTheme((byte)theme.getSelectedIndex());
				currentEntry.setClothes((byte)clothes.getSelectedIndex());
				currentEntry.setStyle((byte)clothesStyle.getSelectedIndex());
				currentEntry.setFurniture((byte)furniture.getSelectedIndex());
				
				if( files != null)
					files.saveFiles();
				
				//only allow item attributes to be changed if that item should have that type of attribute
				if( DisplayWindow.readOnly && !isSearchPanel){
					type.setEnabled(false);
					series.setEnabled(false);
					set.setEnabled(false);
					theme.setEnabled(false);
					furniture.setEnabled(false);
					clothes.setEnabled(false);
					clothesStyle.setEnabled(false);
					
					//case for when "all" is selected in type
				}else if( type.getSelectedIndex() == 0){
					series.setEnabled(true);
					set.setEnabled(true);
					theme.setEnabled(true);
					furniture.setEnabled(true);
					clothes.setEnabled(true);
					clothesStyle.setEnabled(true);
					
					//case for when furniture, wallpaper, carpet are selected in type
				}else if(type.getSelectedIndex() < 4 && type.getSelectedIndex() > 0){
					series.setEnabled(true);
					set.setEnabled(true);
					theme.setEnabled(true);
					furniture.setEnabled(true);
					clothes.setEnabled(false);
					clothesStyle.setEnabled(false);
					
					//case for when clothes are selected as type
				} else if( type.getSelectedIndex() == 4) {
					series.setEnabled(false);
					set.setEnabled(false);
					theme.setEnabled(false);
					furniture.setEnabled(false);
					clothes.setEnabled(true);
					clothesStyle.setEnabled(true);
				} else {
					series.setEnabled(false);
					set.setEnabled(false);
					theme.setEnabled(false);
					furniture.setEnabled(false);
					clothes.setEnabled(false);
					clothesStyle.setEnabled(false);
				}
			}
				
			
		}
	}
}
