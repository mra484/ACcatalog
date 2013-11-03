/*@ Mark Andrews
 * 10/27/2014
 * 
 * This class manages the file system.  Search, add, remove, open, and save functions are carried out here.  
 * - itemList treeMap uses the searchName for a key
 * - userSearchList hashSet uses the searchName of the item
 */

import java.io.*;
import java.util.*;

public class filer {
	private File input;
	private File input2;
	private Entry head;
	private Entry last;
	private Scanner fileReader;
	private PrintStream fileWriter;
	private TreeMap<String, Entry> itemList = new TreeMap<String, Entry>();
	private TreeSet<Entry> userList = new TreeSet<Entry>();
	private HashSet<String> userSearchList = new HashSet<String>();

	public filer(){

		openFileRead("masterIndex.txt");
		readReferenceList();
		fileReader.close();

		openFileRead("userIndex.txt");
		readUserList();
		fileReader.close();
		saveFiles();
	}

	private void openFileRead(String fileName){

		try
		{
			input = new File(fileName);
			fileReader = new Scanner(input);
		}
		catch (FileNotFoundException ex)
		{
			try 
			{
				if(input.createNewFile())
					fileReader = new Scanner(input);
			} 
			catch (IOException e) 
			{
				System.out.println("Error opening file");
				System.exit(-1);				
			}
		}
	}

	private void openFileWrite(String fileName){
		try
		{
			input = new File(fileName);
			fileWriter = new PrintStream(input);
		}
		catch (FileNotFoundException ex)
		{
			try 
			{
				if(input.createNewFile())
					fileWriter = new PrintStream(input);	
			} 
			catch (IOException e) {
				System.out.println("Error opening file for writing");
				System.exit(-1);
			}
		}
	}
	private void readReferenceList(){

		String name;
		byte type, series, set, theme;

		//for now only the reference file will only contain proper names for display
		while(fileReader.hasNext()){
			type = fileReader.nextByte();
			series = fileReader.nextByte();
			set = fileReader.nextByte();
			theme = fileReader.nextByte();
			name = fileReader.nextLine();
			if( normalizeText(name).compareTo("") == 0)
				continue;
			itemList.put(normalizeText(name), new Entry(name, type, series, set, theme, null));
//			itemList.put(normalizeText(name), new Entry(name, null));
		}
	}
	
	private void readUserList(){
		String name;
		Entry prev = null;
		Entry current = null;

		while(fileReader.hasNext()){
			name = fileReader.nextLine();

			if( normalizeText(name).compareTo("") == 0)
				continue;

			//take entry from main list if it exists
			if(itemList.containsKey(normalizeText(name))){
				current = itemList.get(normalizeText(name));
				current.addPrev(prev);
			}

			//add to main list if it didn't exist
			else{
				current = new Entry(name, prev);
				itemList.put(current.searchName, current);
			}

			//add to the userlist and update references
			userList.add(current);
			userSearchList.add(current.searchName);
			if( prev != null )
				prev.addNext(current);
			else{
				head = current;
				current.setHead(true);
			}
			prev = current;
		}
		last = current;
		if( current != null)
			current.setLast(true);
		
	}

	//saves files after every operation
	public void saveFiles(){
		openFileWrite("masterIndex.txt.temp");
		for(Entry a: itemList.values()){
			fileWriter.println(a);
		}
		fileWriter.close();
		input2 = new File("masterIndex.txt");
		input2.delete();
		input.renameTo(new File("masterIndex.txt"));

		openFileWrite("userIndex.txt.temp");
		for(Entry a: userList){
			fileWriter.println(a.displayName);
		}
		fileWriter.close();
		input2 = new File("userIndex.txt");
		input2.delete();
		input.renameTo(new File("userIndex.txt"));
	}

	public Entry searchList(Entry item, DisplayField field){
		Entry result = null, currentEntry = null;
		int state;
		
		result = searchList(item);
		if( result == item ){
			field.updateList(item, 2);
			return result;
		}
		state = item.compareTo(result);
		
		if( state == 0 ){
			currentEntry = result;
		} else {
			currentEntry = item;
			state = ( state < 0 ? -1 : 1 );
		}
		
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

		//return the node for the item if it exists
		if( userSearchList.contains(item.searchName) )
			return itemList.get(item.searchName);

		
		//if it does not exist, temporarily enter it into the list and find the node that precedes it
		userList.add(item);
		for(Entry a: userList){
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
		userList.remove(item);
		return prev;

	}

	public String normalizeText(String a){
		String searchName;
		searchName = a.toLowerCase();
		searchName = searchName.replace(".", "");
		searchName = searchName.replace(" ", "");
		searchName = searchName.replace("'", "");
		searchName = searchName.replace("`", "");
		searchName = searchName.replace("&", "");
		return searchName.replace("-", "");
	}

	public boolean addWord(Entry word){

		//do nothing if the word is already in the user list
		if( userSearchList.contains(word.searchName) )
			return false;

		else if( itemList.containsKey(word.searchName) == true ){
			linkWord(itemList.get(word.searchName));
			return true;
		} else{

			//add to user and master list if not in both
			itemList.put(word.searchName, word);
			linkWord(word);
			return true;
		}
	}

	public void linkWord(Entry word){
		boolean valueFound = false;
		Entry next = null;

		userSearchList.add(word.searchName);
		userList.add(word);
		if( userSearchList.size() == 1 ){
			last = word;
			head = word;
			word.setHead(true);
			word.setLast(true);
			return;
		}

		for(Entry a: userList){
			if( valueFound ){
				next = a;
				break;
			}
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
		//if the item is not in the list ignore the action
		if( !userSearchList.contains(word.searchName) || userSearchList.size() == 0)
			return false;
		else{
			//find entry containing the item and remove it
			for(Entry a : userList){
				if( a.searchName.compareTo(word.searchName) == 0){
					if( userSearchList.size() == 1 ){

					}else if( a == head ){
						a.next.prev = null;
						head = a.next;
					} else if( a == last ){
						a.prev.next = null;
						last = a.prev;
					} else {
						a.prev.next = a.next;
						a.next.prev = a.prev;
					}
					userList.remove(a);
					userSearchList.remove(word.searchName);
					return true;
				}
			}
		}
		return false;
	}

}
