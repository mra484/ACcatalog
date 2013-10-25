import java.io.*;
import java.util.*;

public class filer {
	private File input;
	private Scanner fileReader;
	private HashSet<Entry> itemList = new HashSet<Entry>();
	
	public filer() throws IOException{
		
		try
		{
			input = new File("index.txt");
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
		while(fileReader.hasNext())
			itemList.add(fileReader.nextLine());
		populateList();
	}
	
	private void populateList(){
		Entry prev = null;
		Entry current = null;
		
		while(fileReader.hasNext()){
			current = new Entry(fileReader.nextLine(), prev);
			
			//insert entry it isn't a duplicate
			if(!itemList.contains(current)){
				itemList.add(current);
				if( prev != null )
					prev.addNext(current);
				prev = current;
			}
		}
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
		displayName = a;
		searchName = a.toLowerCase();
		this.prev = prev;
	}
	
	public int compareTo(Entry a){
		return searchName.compareTo(a.searchName);
	}
	
	public void addNext(Entry a){
		next = a;
	}
}