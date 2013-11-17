/**@author Mark Andrews
 * 11/4/2013
 * A list for the browser page will be created here depending on the search options selected 
 */

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.List;
import java.util.ArrayList;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ItemSorter extends JScrollPane {
	private filer listReader;
	private itemPane itemInfo;
	private itemPane searchInfo;
	private Entry blank = new Entry("", null);
	private static JList<String> lister = new JList<String>();
	private ListSelectionHandler actions = new ListSelectionHandler();
	
	//JList is contained within the JScrollPane of ItemSorter.  Filer and itemPane references are needed for the browser functions
	public ItemSorter(filer a, itemPane b, itemPane c){
		super(lister);
		listReader = a;
		searchInfo = b;
		itemInfo = c;
		listReader.setLister(this);
		lister.setCellRenderer(new CellRenderer());
		lister.setListData(getList(searchInfo.getType(), searchInfo.getSeries(), searchInfo.getSet(),
				searchInfo.getTheme(), searchInfo.getClothes(), searchInfo.getStyle(), searchInfo.getFurniture(), searchInfo.getOwned()));
		lister.addListSelectionListener(actions);
	}
	
	//will use the arguments from the browser section to reduce the list down to items that satisfy those conditions
	public String[] getList(int type, int series, int set, int theme, int clothes, int clothesStyle, int furniture, boolean owned){
		ArrayList<String> list = new ArrayList<String>();
		int i = 0;
		for(Entry a: listReader.getList().values()){
			if( a.match(type, series, set, theme, clothes, clothesStyle, furniture, owned) ){
				list.add(a.displayName);
				i++;
			}
		}
		return list.toArray(new String[i]);
	
	}
	
	public void update(){
		lister.setListData(getList(searchInfo.getType(), searchInfo.getSeries(), searchInfo.getSet(),
				searchInfo.getTheme(), searchInfo.getClothes(), searchInfo.getStyle(), searchInfo.getFurniture(), searchInfo.getOwned()));
	}

	public class ListSelectionHandler implements ListSelectionListener{
		
		//updates the item information panel whenever an item is clicked on
		public void valueChanged(ListSelectionEvent e) {
			if(lister.isSelectionEmpty()){
				itemInfo.update(new Entry("", null));
				return;
			}
			itemInfo.update(listReader.getList().get(blank.normalizeText(lister.getSelectedValue())));
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
