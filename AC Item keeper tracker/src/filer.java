import java.io.*;
import java.util.*;

public class filer {
	private File input;
	private File input2;
	private Scanner fileReader;
	private PrintStream fileWriter;
	private TreeMap<String, Entry> itemList = new TreeMap<String, Entry>();
	private TreeSet<String> userList = new TreeSet<String>();

	public filer(){

		openFileRead("masterIndex.txt");
		readReferenceList();
		fileReader.close();

		openFileRead("userIndex.txt");
		readUserList();
		fileReader.close();
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

		//for now only the reference file will only contain proper names for display
		while(fileReader.hasNext()){
			name = fileReader.nextLine();
			if( normalizeText(name).compareTo("") == 0)
				continue;
			itemList.put(name, new Entry(name, null));
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
			if(itemList.containsKey(name)){
				current = itemList.get(name);
				current.addPrev(prev);
			}
			
			//add to main list if it didn't exist
			else{
				current = new Entry(name, prev);
				itemList.put(name, current);
			}
			
			//add to the userlist and update references
			userList.add(current.displayName);
			if( prev != null )
				prev.addNext(current);
			prev = current;
		}
	}
	
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
		for(String a: userList){
			fileWriter.println(a);
		}
		fileWriter.close();
		input2 = new File("userIndex.txt");
		input2.delete();
		input.renameTo(new File("userIndex.txt"));
	}


	public Entry searchList(String item){
		String prev = null;
		boolean breakNext = false;
		
		//return the node for the item if it exists
		if( itemList.containsKey(item) )
			return itemList.get(item);
		
		//if it does not exist, temporarily enter it into the list and find the node that precedes it
		userList.add(item);
		for(String a: userList){
			if( item.compareTo(a) == 0 || breakNext){
				if( prev == null)
					breakNext = true;
				else{
					prev = a;
					break;
				}
			}
			prev = a;
		}
		userList.remove(item);
		return itemList.get(prev);
		
		
	}
	
	public String normalizeText(String a){
		String searchName;
		searchName = a.toLowerCase();
		searchName = searchName.replace(".", "");
		searchName = searchName.replace(" ", "");
		return searchName.replace("-", "");
	}
	
}
