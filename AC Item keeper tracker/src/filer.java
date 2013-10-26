import java.io.*;
import java.util.*;

public class filer {
	private File input;
	private File input2;
	private Scanner fileReader;
	private PrintStream fileWriter;
	private HashMap<String, Entry> itemList = new HashMap<String, Entry>();
	private HashSet<Entry> userList = new HashSet<Entry>();

	public filer() throws IOException{

		openFileRead("index.txt");
		readReferenceList();
		fileReader.close();

		openFileRead("userIndex.txt");
		readUserList();
		fileReader.close();
	}

	private void openFileRead(String fileName) throws IOException{

		try
		{
			input = new File(fileName);
			fileReader = new Scanner(input);
		}
		catch (FileNotFoundException ex)
		{
			if(!input.createNewFile()){
				System.out.println("Error opening file");
				System.exit(-1);
			}
			fileReader = new Scanner(input);
		}
	}
	
	private void openFileWrite(String fileName) throws IOException{
		try
		{
			input = new File(fileName);
			fileWriter = new PrintStream(input);
		}
		catch (FileNotFoundException ex)
		{
			if(!input.createNewFile()){
				System.out.println("Error opening file");
				System.exit(-1);
			}
			fileWriter = new PrintStream(input);
		}
	}
	private void readReferenceList(){
		String name;

		//for now only the reference file will only contain proper names for display
		while(fileReader.hasNext()){
			name = fileReader.nextLine();
			itemList.put(name, new Entry(name, null));
		}
	}


	private void readUserList(){
		String name;
		Entry prev = null;
		Entry current = null;

		while(fileReader.hasNext()){
			name = fileReader.nextLine();

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
			userList.add(current);
			if( prev != null )
				prev.addNext(current);
			prev = current;
		}
	}
	
	public void saveFiles() throws IOException{
		openFileWrite("index.txt.temp");
		for(Entry a: itemList.values()){
			fileWriter.println(a);
		}
		input2 = new File("index.txt");
		input2.delete();
		input.renameTo(input2);
		
		openFileWrite("userIndex.txt.temp");
		for(Entry a: userList){
			fileWriter.println(a);
		}
		input2 = new File("userIndex.txt");
		input2.delete();
		input.renameTo(input2);
	}


	public void searchList(String item){

	}
}

class Entry implements Comparable<Entry>{
	public String displayName;
	public String searchName;
	private int set = 0;
	private int type = 0;
	public Entry next;
	public Entry prev;

	public Entry(String a, Entry prev){
		
		//item name displayed by the program will be different than what is searched
		displayName = a;
		
		//item names will be forced lower case with spaces, periods and hyphens removed to cut down on the chance of duplicate items
		searchName = a.toLowerCase();
		searchName = searchName.replace(".", "");
		searchName = searchName.replace(" ", "");
		searchName = searchName.replace("-", "");

		this.prev = prev;
	}

	public int compareTo(Entry a){
		return displayName.compareTo(a.displayName);
	}

	public void addNext(Entry a){
		next = a;
	}

	public void addPrev(Entry a){
		prev = a;
	}
	
	public String toString(){
		return displayName;
	}
}