/**@author Mark Andrews
 * 11/4/2013
 * A list for the browser page will be created here depending on the search options selected 
 */

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.List;
import java.util.ArrayList;
import java.util.TreeSet;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ItemSorter extends JScrollPane {
	private filer listReader;
	private itemPane itemInfo;
	private itemPane searchInfo;
	private BrowserPanel browser;
	private Entry blank = new Entry("", null);
	private JList<String> lister;
	private ListSelectionHandler actions = new ListSelectionHandler();
	
	//JList is contained within the JScrollPane of ItemSorter.  Filer and itemPane references are needed for the browser functions
	public ItemSorter(filer a, itemPane b, itemPane c, JList<String> d, BrowserPanel e){
		super(d);
		lister = d;
		listReader = a;
		searchInfo = b;
		itemInfo = c;
		browser = e;
		listReader.setLister(this);
		lister.setCellRenderer(new CellRenderer());
		lister.setListData(getList(searchInfo.getType(), searchInfo.getSeries(), searchInfo.getSet(),
				searchInfo.getTheme(), searchInfo.getClothes(), searchInfo.getStyle(), searchInfo.getFurniture(), searchInfo.getOwned()));
		lister.addListSelectionListener(actions);
	}
	public ItemSorter(filer a, JList<String> d){
		super(d);
		lister = d;
		listReader = a;
		listReader.setLister(this);
		lister.setListData(getList(0, 0, 0, 0, 0, 0, 0, false));
	}
	
	//will use the arguments from the browser section to reduce the list down to items that satisfy those conditions
	public String[] getList(int type, int series, int set, int theme, int clothes, int clothesStyle, int furniture, boolean owned){
		ArrayList<String> list = new ArrayList<String>();
		TreeSet<Entry> sortList = new TreeSet<Entry>();
		int i = 0;
		BrowserPanel.total = 0;
		BrowserPanel.owned = 0;
		for(Entry a: listReader.getList().values()){
			if( a.match(type, series, set, theme, clothes, clothesStyle, furniture, owned) ){
				sortList.add(a);
				i++;
			}
		}
		for(Entry a: sortList){
			list.add(a.displayName);
		}
		return list.toArray(new String[i]);
	
	}
	
	public String getEntry(){
		return lister.getSelectedValue();
	}
	
	public void update(){
		lister.setListData(getList(searchInfo.getType(), searchInfo.getSeries(), searchInfo.getSet(),
				searchInfo.getTheme(), searchInfo.getClothes(), searchInfo.getStyle(), searchInfo.getFurniture(), searchInfo.getOwned()));
		browser.update();
	}

	public class ListSelectionHandler implements ListSelectionListener{
		
		//updates the item information panel whenever an item is clicked on
		public void valueChanged(ListSelectionEvent e) {
			if(lister.isSelectionEmpty()){
				itemInfo.update(new Entry("", null));
				return;
			}
			itemInfo.update(listReader.getList().get(blank.normalizeText(lister.getSelectedValue())));
			browser.update();
		}
	}
	
	private class CellRenderer extends DefaultListCellRenderer{
		
		public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus){
			Component comp = super.getListCellRendererComponent(list, value, index, false, false);
			Font unOwned = new Font("unOwned", Font.ITALIC, 12);

			if( !listReader.getList().get(blank.normalizeText(value.toString())).getOwned() ){
				comp.setFont(unOwned);
				comp.setForeground(Color.GRAY);
			}
			if( isSelected ){
				comp.setBackground(Color.CYAN);
			}
			return this;
		}
	}	
}
