/**@author Mark Andrews
 * 10/27/2014
 * 
 * This class manages the file system.  Search, add, and remove functions are carried out here.  
 */

import java.io.*;
import java.util.*;

import javax.swing.tree.TreePath;

public class filer {
	private int totalItems = 0;
	private Entry head;
	private Entry last;
	private int userSize = 0;
	private TreeMap<String, Entry> itemList = new TreeMap<String, Entry>();
	
	private FileHandler fileManager = null;
	private ItemSorter lister = null;
	
	public filer(DisplayWindow a){
		fileManager = new FileHandler(this, a);
		System.out.println(userSize);
//		printUnicode();
	}

	public Entry searchList(Entry item, DisplayField field){
		Entry result = null, currentEntry = null;
		int state;
		
		//the resulting item from the searchList method can be the item itself, or the item before or after it
		result = searchList(item);
		
		state = item.compareTo(result);
		
		//the Entry returned will be used in the item information panel, determine if the item was in the list or not
		//and return the result Entry(which has complete information, item was found), or the item Entry(which is mostly empty, item not found)
		if( state == 0 ){
			currentEntry = result;
		} else {
			currentEntry = item;
			state = ( state < 0 ? -1 : 1 );
		}
		
		//If the search did return the item itself, and it still has
		if( state == 0 && !result.getHead())
			result = result.prev;
		
		else if( state == 0 && result.getHead())
			state = -2;		

		field.updateList(result, state);
		return currentEntry;
	}
	
	public Entry searchList(Entry item){
		Entry prev = null;
		boolean breakNext = false;
		boolean containsKey = itemList.containsKey(item.searchName);
		Entry listItem = null;
		if( containsKey )
			listItem = itemList.get(item.searchName);

		//return the node for the item if it exists
		if( containsKey )
			if( listItem.getOwned())
				return listItem;

		
		//if it does not exist, temporarily enter it into the list and find the node that precedes it
		if( !containsKey )
			itemList.put(item.searchName, item);
		else
			listItem.setOwned(true);
		
		for(Entry a: itemList.values()){
			if(!a.getOwned())
				continue;
			if( breakNext ){
				prev = a;
				break;
			}
			if( item.compareTo(a) == 0){
				if( prev == null)
					breakNext = true;
				else
					break;
			}
			prev = a;
		}
		if( !containsKey )
			itemList.remove(item.searchName);
		else
			listItem.setOwned(false);
		return prev;

	}

	public boolean addWord(Entry word){
		boolean containsKey = itemList.containsKey(word.searchName);
		Entry listEntry = null;
		if( containsKey )
			listEntry = itemList.get(word.searchName);
		
		//do nothing if the word is already in the user list
		if( containsKey ){
			if( listEntry.getOwned() ){
				return false;
			}
			linkWord(listEntry);
			return true;
		} else {

			//add to user and master list if not in both
			if(!DisplayWindow.readOnly){
				itemList.put(word.searchName, word);
				linkWord(word);
				return true;
			}
			return false;
		}
	}

	public void linkWord(Entry word){
		boolean valueFound = false;
		Entry next = null;

//		userSearchList.add(word.searchName);
		word.setOwned(true);
		userSize++;
//		userList.add(word);
		if( userSize == 1 ){
			last = word;
			head = word;
			word.setHead(true);
			word.setLast(true);
			return;
		}

		for(Entry a: itemList.values()){
			if( !a.getOwned())
				continue;
			if( valueFound ){
				next = a;
				break;
			}
			if( a.getOwned() )
				if( a.searchName.compareTo(word.searchName) == 0 ){
					valueFound = true;
			}
		}
		if( valueFound && next == null ){
			last.next = word;
			word.prev = last;
			last.setLast(false);
			last = word;			
			word.setLast(true);
		} else if ( next.getHead() ){
			next.prev = word;
			word.next = next;
			head.setHead(false);
			head = word;
			word.setHead(true);
		} else {
			word.next = next;
			word.prev = next.prev;
			next.prev.next = word;
			next.prev = word;
		}

	}

	public boolean removeWord(Entry word){
		Entry toRemove = null;
		//if the item is not in the list ignore the action
		if( !itemList.containsKey(new String(word.searchName)) || userSize == 0)
			return false;
		else if ( itemList.get(word.searchName).getOwned() ){
			//find entry containing the item and remove it
			toRemove = itemList.get(word.searchName);
					if( userSize == 1 ){

					}else if( toRemove == head ){
						toRemove.next.setHead(true);
						toRemove.next.prev = null;
						head = toRemove.next;
					} else if( toRemove == last ){
						toRemove.prev.next = null;
						toRemove.prev.setLast(true);
						last = toRemove.prev;
					} else {
						toRemove.prev.next = toRemove.next;
						toRemove.next.prev = toRemove.prev;
					}
					toRemove.setOwned(false);
					userSize--;
					if( !DisplayWindow.readOnly)
						itemList.remove(toRemove.searchName);
					return true;			
		}
		return false;
	}
	
	public TreeMap<String, Entry> getList(){
		return itemList;
	}
	public int getTotalItems(){
		return totalItems;
	}
	
	public void setHead(Entry a){
		head = a;
	}
	
	public void setLast(Entry a){
		last = a;
	}
	
	public void saveFiles(){
		fileManager.saveFiles();
	}
	public void incUserSize(){
		userSize++;
	}
	public void incTotalItems(){
		totalItems++;
	}
	public int getUserSize(){
		return userSize;
	}
	
	public void setLister(ItemSorter a){
		lister = a;
	}
	
	public void updateLanguage(){
		TreeMap<String, Entry> newList = new TreeMap<String, Entry>();
		for(Entry a: itemList.values()){
			a.setLanguage();
			newList.put(a.searchName, a);
		}
		itemList.clear();
		itemList = newList;
		lister.update();
	}
	
	//method for finding and saving all the unique unicode special characters in the item list
//	public void printUnicode(){
//		TreeSet<Character> unicodeList = new TreeSet<Character>();
//			for(Entry a: itemList.values()){
//				char[] list = a.displayName.toCharArray();
//				for(int i = 0; i < a.displayName.length(); i++ )
//					if(list[i] > 'z' )
//						unicodeList.add(list[i]);
//			}
//		try {
//			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("output.txt"), "UTF-8"));
//			for(Character a: unicodeList){
//				try {
//					writer.write(a);
//					writer.newLine();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//
//			try {
//				writer.flush();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			try {
//				writer.close();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
}


