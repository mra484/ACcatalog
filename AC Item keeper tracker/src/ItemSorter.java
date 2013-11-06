/**@author Mark Andrews
 * 11/4/2013
 * A list for the browser page will be created here depending on the search options selected 
 */

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ItemSorter extends JScrollPane{
	private filer listReader;
	private itemPane itemInfo;
	private static JList<String> lister = new JList<String>();
	private ListSelectionHandler actions = new ListSelectionHandler();
	
	//JList is contained within the JScrollPane of ItemSorter.  Filer and itemPane references are needed for the browser functions
	public ItemSorter(filer a, itemPane b){
		super(lister);
		listReader = a;
		itemInfo = b;
		lister.setListData(getList(0,0,0,0,true));
		lister.addListSelectionListener(actions);
	}
	
	//will use the arguments from the browser section to reduce the list down to items that satisfy those conditions
	public String[] getList(int type, int series, int set, int theme, boolean owned){
		String[] list = new String[listReader.getTotalItems()];
		int i = 0;
		for(Entry a: listReader.getList().values()){
			
			list[i] = a.displayName;
			i++;
		}
			
		return list;
	
	}
	public class ListSelectionHandler implements ListSelectionListener{
		
		//updates the item information panel whenever an item is clicked on
		public void valueChanged(ListSelectionEvent e) {
			itemInfo.update(listReader.getList().get(listReader.normalizeText(lister.getSelectedValue())));
		}
	}
}
