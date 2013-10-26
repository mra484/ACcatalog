
public class Entry implements Comparable<Entry>{
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