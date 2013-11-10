/**@author Mark Andrews
 * 10/27/2014
 * 
 * This class manages the file system.  Search, add, remove, open, and save functions are carried out here.  
 */

import java.io.*;
import java.util.*;

public class filer {
	private int totalItems = 3488;
	private File input;
	private File input2;
	private Entry head;
	private Entry last;
	private int userSize = 0;
	private FileInputStream fis;
	private InputStreamReader isr;
	private BufferedReader unicodeReader;
	private FileOutputStream fos;
	private OutputStreamWriter osw;
	private BufferedWriter unicodeWriter;
	
	private Scanner fileReader;
	private PrintStream fileWriter;
	private TreeMap<String, Entry> itemList = new TreeMap<String, Entry>();
	private TreeSet<Entry> itemIndex = new TreeSet<Entry>();

	public filer() {

		try {
			
			openFileRead("masterIndex.txt");
			readReferenceList();
			unicodeReader.close();
			openFileRead("userIndex.txt");
			readUserList();
			unicodeReader.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		saveFiles();
	}

	private void openFileRead(String fileName) {

		try
		{
			fis = new FileInputStream(fileName);
//			input = new File(fileName);
			unicodeReader = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
//			fileReader = new Scanner(input);
		}
		catch (FileNotFoundException ex)
		{
//			try 
//			{
//				if(input.createNewFile())
//					fileReader = new Scanner(input);
//			} 
//			catch (IOException e) 
//			{
//				System.out.println("Error opening file");
//				System.exit(-1);				
//			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void openFileWrite(String fileName) {
		try
		{
			fos = new FileOutputStream(fileName);
			unicodeWriter = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));
//			input = new File(fileName);
//			fileWriter = new PrintStream(input);
		}
		catch (FileNotFoundException ex)
		{
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void readReferenceList(){

		Entry newEntry;
		String a, b, c, d, e, f, g, h;
		String[] splitArray, intArray;
		byte type, series, set, theme, clothes, style, furniture;
		
		while(true){
			try {
				a = unicodeReader.readLine();
				if(a == null)
					break;
				splitArray = a.split("\"");
				intArray = splitArray[0].split(" ");
				type = Byte.parseByte(intArray[0]);
				series = Byte.parseByte(intArray[1]);
				set = Byte.parseByte(intArray[2]);
				theme = Byte.parseByte(intArray[3]);
				clothes = Byte.parseByte(intArray[4]);
				style = Byte.parseByte(intArray[5]);
				furniture = Byte.parseByte(intArray[6]);
				a = splitArray[1];
				b = splitArray[3];
				c = splitArray[5];
				d = splitArray[7];
				e = splitArray[9];
				f = splitArray[11];
				g = splitArray[13];
				h = splitArray[15];
//				type = readValue(a);
				itemIndex.add( (newEntry = new Entry(a, b, c, d, e, f, g, h, type, series, set, theme, clothes, style, null) ) );
				itemList.put(newEntry.searchName, newEntry);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
//			a = readName();
		}
		//for now only the reference file will only contain proper names for display
//		while(fileReader.hasNext()){
//			type = fileReader.nextByte();
//			series = fileReader.nextByte();
//			set = fileReader.nextByte();
//			theme = fileReader.nextByte();
//			clothes = fileReader.nextByte();
//			style = fileReader.nextByte();
//			name = readName();
////			name = fileReader.next();
//			if( normalizeText(name).compareTo("") == 0)
//				continue;
//			itemList.put(normalizeText(name), new Entry(name, type, series, set, theme, clothes, style, null));
//			totalItems++;
////			itemList.put(normalizeText(name), new Entry(name, null));
//		}
	}
	
	private String readName(){
		String name;
		name = fileReader.next();
		while(name.charAt(name.length()-1) != '"'){
			name = name +' ' + fileReader.next();
		}
		return name.substring(1, name.length()-1);
	}
	
//	private byte readValue(String a){
//		
//	}
	
	private void readUserList(){
		String name;
		Entry prev = null;
		Entry current = null;

		
			try {
				while(true){
				name = unicodeReader.readLine();
				if( name == null )
					break;
				name = normalizeText(name);
				if(itemList.containsKey(name)){
					current = itemList.get(name);
				} else
					continue;
				current.addPrev(prev);
				current.setOwned(true);
				userSize++;
				if( prev != null )
					prev.addNext(current);
				else{
					head = current;
					current.setHead(true);
				}
				prev = current;
				
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
//		while(fileReader.hasNext()){
//			name = fileReader.nextLine();
//
//			if( normalizeText(name).compareTo("") == 0)
//				continue;
//
//			//take entry from main list if it exists
//			if(itemList.containsKey(normalizeText(name))){
//				current = itemList.get(normalizeText(name));
//			}
//
//			//add to main list if it didn't exist
//			else{
//				current = new Entry(name, prev);
//				itemList.put(current.searchName, current);
//			}
//			current.addPrev(prev);
//			current.setOwned(true);
//			userSize++;
//
//			//add to the userlist and update references
//			if( prev != null )
//				prev.addNext(current);
//			else{
//				head = current;
//				current.setHead(true);
//			}
//			prev = current;
//		}
		last = current;
		if( current != null)
			current.setLast(true);
		
	}

	//saves files after every operation
	public void saveFiles(){
		openFileWrite("masterIndex.txt.temp");
		try {
			for(Entry a: itemList.values()){
				unicodeWriter.write(a.toString(), 0, a.toString().length());
				unicodeWriter.newLine();
			}
			unicodeWriter.flush();
			unicodeWriter.close();
			fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		input2 = new File("masterIndex.txt");
		input2.delete();
		input = new File("masterIndex.txt.temp");
		input.renameTo(new File("masterIndex.txt"));

		openFileWrite("userIndex.txt.temp");
		try {
			for(Entry a: itemList.values()){
				if(a.getOwned()){
					unicodeWriter.write(a.toString(), 0, a.toString().length());
					unicodeWriter.newLine();					
				}
			}
			unicodeWriter.flush();
			unicodeWriter.close();
			fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		input2 = new File("userIndex.txt");
		input2.delete();
		input = new File("userIndex.txt.temp");
		input.renameTo(new File("userIndex.txt"));
//		openFileWrite("masterIndex.txt.temp");
//		for(Entry a: itemList.values()){
//			fileWriter.println(a);
//		}
//		fileWriter.close();
//		input2 = new File("masterIndex.txt");
//		input2.delete();
//		input.renameTo(new File("masterIndex.txt"));
//
//		openFileWrite("userIndex.txt.temp");
//		for(Entry a: itemList.values()){
//			if(a.getOwned())
//				fileWriter.println(a.displayName);
//		}
//		fileWriter.close();
//		input2 = new File("userIndex.txt");
//		input2.delete();
//		input.renameTo(new File("userIndex.txt"));
	}

	public Entry searchList(Entry item, DisplayField field){
		Entry result = null, currentEntry = null;
		int state;
		
		//the resulting item from the searchList method can be the item itself, or the item before or after it
		result = searchList(item);
		
		//if the result was the item, that means there were items before and after it so the list can be updated normally
//		if( result == item ){
//			field.updateList(item, 2);
//			return result;
//		}
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
			itemList.put(word.searchName, word);
			linkWord(word);
			return true;
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

}
