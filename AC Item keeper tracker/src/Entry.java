/*@ Mark Andrews
 * 10/27/2014
 * 
 * Container class for item entries to be stored in treehash/sets
 * 
 *  -displayName uses proper capitalization, spaces, and punctuation, used for outputting to the screen
 *  -sortName uses proper punctuation and spacing but is forced lowercase to be closer to alphabetization
 *  -searchName removes punctuation and forces lowercase used when searching to reduce the chances of duplicate entries
 */
public class Entry implements Comparable<Entry>{
	public String displayName;
	public String sortName;
	public String searchName;
	private byte type = 0;
	private byte series = 0;
	private byte set = 0;
	private byte theme = 0;
	private byte clothes = 0;
	private byte style = 0;
	private boolean isOwned = false;
	public Entry next;
	public Entry prev;
	private boolean isHead = false;
	private boolean isLast = false;

	public Entry(String a, Entry prev){
		
		//item name displayed by the program will be different than what is searched
		displayName = a.trim();
		

		
		//item names will be forced lower case with spaces, periods and hyphens removed to cut down on the chance of duplicate items
		sortName = displayName.toLowerCase();
		searchName = sortName.replace(".", "");
		searchName = searchName.replace(" ", "");
		searchName = searchName.replace("'", "");
		searchName = searchName.replace("`", "");
		searchName = searchName.replace("&", "");
		searchName = searchName.replace("-", "");
		isOwned = true;
		this.prev = prev;
	}
	
	public Entry(String a, byte type, byte series, byte set, byte theme, Entry prev){
		
		this.type = type;
		this.series = series;
		this.set = set;
		this.theme = theme;
		//item name displayed by the program will be different than what is searched
		displayName = a.trim();
				
		//item names will be forced lower case with spaces, periods and hyphens removed to cut down on the chance of duplicate items
		sortName = displayName.toLowerCase();
		searchName = sortName.replace(".", "");
		searchName = searchName.replace(" ", "");
		searchName = searchName.replace("'", "");
		searchName = searchName.replace("`", "");
		searchName = searchName.replace("&", "");
		searchName = searchName.replace("-", "");

		this.prev = prev;
	}

	public int compareTo(Entry a){
		if( searchName.compareTo(a.searchName) == 0)
			return 0;
		return sortName.compareTo(a.sortName);
	}

	public void addNext(Entry a){
		next = a;
	}

	public void addPrev(Entry a){
		prev = a;
	}
	
	public String toString(){
		return String.format("%d %d %d %d %d %d \"%s\"", type, series, set, theme, clothes, style, displayName);
	}
	
	public void setHead(boolean a){
		isHead = a;
	}
	public boolean getHead(){
		return isHead;
	}
	
	public void setLast(boolean a){
		isLast = a;
	}
	public boolean getLast(){
		return isLast;
	}
	
	public byte getType(){
		return type;
	}
	
	public byte getSeries(){
		return series;
	}
	
	public byte getSet(){
		return set;
	}
	
	public byte getTheme(){
		return theme;
	}
	
	public void setType(byte a){
		type = a;
	}
	
	public void setSeries(byte a){
		series = a;
	}
	
	public void setSet(byte a){
		set = a;
	}
	
	public void setTheme(byte a){
		theme = a;
	}
	public void setOwned(boolean a){
		isOwned = a;
	}
	public boolean getOwned(){
		return isOwned;
	}
}