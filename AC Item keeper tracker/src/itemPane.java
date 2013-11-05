import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
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
	
	private JLabel seriesLabel = new JLabel("Furniture Series");
	private JComboBox<String> series = new JComboBox<String>();
	private JLabel setLabel = new JLabel("Furniture Set");
	private JComboBox<String> set = new JComboBox<String>();
	private JLabel themeLabel = new JLabel("Furniture Theme");
	private JComboBox<String> theme = new JComboBox<String>();
	
	private ItemUpdater updater = new ItemUpdater();
	private filer files = null;
	
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
		
		setLayout(new FlowLayout(FlowLayout.LEADING));
		add(typeLabel);
		add(type);
		add(seriesLabel);
		add(series);
		add(setLabel);
		add(set);
		add(themeLabel);
		add(theme);
//		setMaximumSize(new Dimension(500, 200));
	}
	
	public void populateLists(){
		//add types to choice
				type.addItem("Unknown");
				type.addItem("Furniture");
				type.addItem("Wallpaper");
				type.addItem("Carpet");
				type.addItem("Shirts");
				type.addItem("Pants / Skirts");
				type.addItem("Dresses");
				type.addItem("Hats");
				type.addItem("Accessories");
				type.addItem("Shoes and Socks");
				type.addItem("Umbrellas");
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
	
	public void setFiler(filer file){		
		files = file;
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
			if(type.getSelectedIndex() < 4 && type.getSelectedIndex() > 0){
				series.setEnabled(true);
				set.setEnabled(true);
				theme.setEnabled(true);
			} else {
				series.setEnabled(false);
				set.setEnabled(false);
				theme.setEnabled(false);
			}
		}
	}
}
