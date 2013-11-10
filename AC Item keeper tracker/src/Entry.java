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

	public String displayNameEU;
	public String displayNameUS;
	public String displayNameFR;
	public String displayNameIT;
	public String displayNameDS;
	public String displayNameES;
	public String displayNameJP;
	public String imageLocation;
	
	private byte type = 0;
	private byte series = 0;
	private byte set = 0;
	private byte theme = 0;
	private byte clothes = 0;
	private byte style = 0;
	private byte furniture = 0;
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
	
	public Entry(String EU, String US, String FR, String IT, String DS, String ES, String JP,
			String File, byte type, byte series, byte set, byte theme, byte clothes, 
			byte style,  Entry prev){
		
		this.type = type;
		this.series = series;
		this.set = set;
		this.theme = theme;
		this.clothes = clothes;
		this.style = style;
		this.furniture = furniture;
		
		displayNameEU = EU.trim();
		displayNameUS = US.trim();
		displayNameFR = FR.trim();
		displayNameIT = IT.trim();
		displayNameDS = DS.trim();
		displayNameES = ES.trim();
		displayNameJP = JP.trim();
		imageLocation = File.trim();
		
		setLanguage();

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
		return String.format("%d %d %d %d %d %d %d \"%s\" \"%s\" \"%s\" \"%s\" \"%s\" \"%s\" \"%s\" \"%s\"",
				type, series, set, theme, clothes, style, furniture, displayNameEU, displayNameUS, displayNameFR,
				displayNameIT, displayNameDS, displayNameES, displayNameJP, imageLocation);
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
	
	public boolean match(int type, int series, int set, int theme, int clothes, int clothesStyle, boolean owned){
		if( !((byte) type == this.type) && type > 0 )
			return false;
		if( !((byte) series == this.series) && series > 0 )
			return false;
		if( !((byte) set == this.set) && set > 0)
			return false;
		if( !((byte) theme == this.theme) && theme > 0)
			return false;
		if( !((byte) clothes == this.clothes) && clothes > 0)
			return false;
		if( !((byte) clothesStyle == this.style) && clothesStyle > 0)
			return false;
		if( owned && !isOwned )
			return false;
		return true;
	}
	
	public void setLanguage(){

		switch(DisplayWindow.language){
		case 0:
			displayName = displayNameEU;
			break;
		case 1:
			displayName = displayNameUS;
			break;
		case 2:
			displayName = displayNameFR;
			break;
		case 3:
			displayName = displayNameIT;
			break;
		case 4:
			displayName = displayNameDS;
			break;
		case 5:
			displayName = displayNameES;
			break;
		case 6:
			displayName = displayNameJP;
			break;
			default:
				break;
		}
		//item names will be forced lower case with spaces, periods and hyphens removed 
		//to cut down on the chance of duplicate items, name displayed will be different
		//than what is searched and sorted
		sortName = displayName.toLowerCase();
		searchName = sortName.replace(".", "");
		searchName = searchName.replace(" ", "");
		searchName = searchName.replace("'", "");
		searchName = searchName.replace("`", "");
		searchName = searchName.replace("&", "");
		searchName = searchName.replace("-", "");
	}
		
}