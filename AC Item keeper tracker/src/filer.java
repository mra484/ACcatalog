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
 * 10/27/2013
 * 
 * This class manages the file system.  Search, add, and remove functions are carried out here.  
 */

import java.util.*;

import javax.swing.JOptionPane;

public class filer {
	
	private int totalItems = 0;
	private Entry head;
	private Entry last;
	private Entry empty = new Entry("--------------------------", null);
	private int userSize = 0;
	private TreeMap<String, Entry> itemList = new TreeMap<String, Entry>();
	private Queue<String> notFoundList = new ArrayDeque<String>();
	
	private FileHandler fileManager = null;
	private DisplayField sResults = null;
	private ItemSorter lister = null;
	private DisplayWindow mainWindow;
	
	public filer(DisplayWindow a){
		mainWindow = a;
		fileManager = new FileHandler(this, a);
		System.out.println(userSize);
//		printUnicode();
	}

	public Entry searchListControl(Entry item){
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

		sResults.updateList(result, state);
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

	public int addWord(Entry word){
		boolean containsKey = itemList.containsKey(word.searchName);
		Entry listEntry = null;
		if( containsKey )
			listEntry = itemList.get(word.searchName);
		
		//do nothing if the word is already in the user list
		if( containsKey ){
			if( listEntry.getOwned() ){
				return 0;
			}
			linkWord(listEntry);
			return 2;
		} else {

			//add to user and master list if not in both
			if(!DisplayWindow.readOnly){
				itemList.put(word.searchName, word);
				linkWord(word);
				return 2;
			}
			return 1;
		}
	}

	public void linkWord(Entry word){
		boolean valueFound = false;
		Entry next = null;

		word.setOwned(true);
		userSize++;
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
			//item added to the end of the list
			last.next = word;
			word.prev = last;
			last.setLast(false);
			last = word;			
			word.setLast(true);
			
		} else if ( next.getHead() ){
			//item added to the start of the list
			next.prev = word;
			word.next = next;
			head.setHead(false);
			head = word;
			word.setHead(true);
			
		} else {
			//item added within the list
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

					//behavior for removing from start, end, and inside the list
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
	public Queue<String> getMissingList(){
		return notFoundList;
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
	
	public void saveFiles(int a){
		if( itemList.size() < 3609)
			JOptionPane.showMessageDialog(mainWindow, "Item Removed from master list");
		switch (a){
		case 0:
			fileManager.saveSettings();
			break;
		case 1:
			fileManager.saveUser();
			fileManager.saveSettings();
			break;
		case 2:
			fileManager.saveReference();
			fileManager.saveSettings();
			break;
		case 3:
			fileManager.saveSettings();
			fileManager.saveUser();
			fileManager.saveReference();
			break;
		default:
			break;
				
		}
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
		relinkUserList();
		searchListControl(empty);
	}
	
	public void setDisplayField(DisplayField a){
		sResults = a;
	}
	
//	public void setItemPane(itemPane a ){
//		itemInfo = a;
//	}
	
	//goes through the list and relinks the user's items in order
	public void relinkUserList(){
		int i = 0;
		Entry temp1 = null;
		for(Entry a: itemList.values()){
			if(!a.getOwned())
				continue;
			if( temp1 != null){
				temp1.next = a;
			}
			if( i == 0){
				head = a;
				a.setHead(true);
				a.prev = null;
			} else if ( i == userSize){
				last = a;
				a.setHead(false);
				a.setLast(true);
				a.next = null;
				a.prev = temp1;
			} else {
				a.setHead(false);
				a.setLast(false);
				a.prev = temp1;
			}
			temp1 = a;
			i++;
		}
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