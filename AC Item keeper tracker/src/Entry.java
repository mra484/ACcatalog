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
 * 10/27/2014
 * 
 * Container class for item entries to be stored in a treeMap
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
	public boolean changed = false;

	public Entry(String a, Entry prev){
		
		//item name displayed by the program will be different than what is searched
		displayName = a.trim();
		displayNameEU = displayName;
		displayNameUS = displayName;
		displayNameFR = displayName;
		displayNameIT = displayName;
		displayNameDS = displayName;
		displayNameES = displayName;
		displayNameJP = displayName;
		imageLocation = "none";
		
		//item names will be forced lower case with spaces, periods and hyphens removed to cut down on the chance of duplicate items
		sortName = normalizeText(displayName);
		searchName = sortName;
		

//		sortName = convertUnicode(displayName.toLowerCase());
//		searchName = normalizeText(sortName);
////		
		isOwned = true;
		this.prev = prev;
	}
	
	public Entry(String EU, String US, String FR, String IT, String DS, String ES, String JP,
			String File, byte type, byte series, byte set, byte theme, byte clothes, 
			byte style, byte furniture, Entry prev){
		
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
	
	public byte getClothes(){
		return clothes;
	}
	
	public byte getStyle(){
		return style;
	}
	
	public byte getFurniture(){
		return furniture;
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
	public void setClothes(byte a){
		clothes = a;
	}
	public void setStyle(byte a){
		style = a;
	}
	public void setFurniture(byte a){
		furniture = a;
	}
	public void setOwned(boolean a){
		isOwned = a;
	}
	public boolean getOwned(){
		return isOwned;
	}
	
	//determines if an item is a match for the current search properies in the browser tab
	public boolean match(int type, int series, int set, int theme, int clothes, int clothesStyle, int furniture, boolean owned){
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
		if( !((byte) furniture == this.furniture) && furniture > 0)
			return false;
		
		//an item that reaches here will be in the effective list, increment total
		BrowserPanel.total++;
		if( owned && !isOwned )
			return false;
		
		//if it is owned increment owned
		if( isOwned )
			BrowserPanel.owned++;
		
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
		sortName = convertUnicode(displayName.toLowerCase());
//		searchName = normalizeText(sortName);
//		sortName = displayName.toLowerCase().replace("\u00ba", "").replace("\u0093", "").replace("\u0084", "");
		searchName = normalizeText(sortName);
	}
	public String normalizeText(String a){

		a = a.toLowerCase();
		
//		a = convertUnicode(a);
		a = a.replace("\u00c2", "a").replace("\u00e0", "a").replace("\u00e2", "a").replace("\u00e3", "a").replace("\u00e3", "a").replace("\u00c1", "a").replace("\u00e1", "a");
		a = a.replace("\u00c9", "e").replace("\u00e9", "e").replace("\u00e8", "e").replace("\u00ea", "e").replace("\u00eb", "e");
		a = a.replace("\u00ed", "i").replace("\u00ee", "i").replace("\u00ef", "i").replace("\u00ec", "i");
		a = a.replace("\u00f4", "o").replace("\u00f2", "o").replace("\u00f6", "o").replace("\u00d6", "o").replace("\u00d3", "o").replace("\u00f3", "o");
		a = a.replace("\u00fb", "u").replace("\u00f9", "u").replace("\u00fc", "u").replace("\u00dc", "u").replace("\u00fa", "u");
		a = a.replace("\u00f1", "n");
		
		a = a.replace("\u00df", "ss");
		a = a.replace("\u00e7", "c");
		a = a.replace("\u0153", "oe");
		a = a.replace("\u00ba", "").replace("\u0093", "").replace("\u0084", "");
		
		a = a.replace(".", "");
		a = a.replace(" ", "");
		a = a.replace("'", "");
		a = a.replace("`", "");
		a = a.replace("&", "");
		a = a.replace("(", "");
		a = a.replace(")", "");
		return a.replace("-", "");
		
	}
	public String convertUnicode(String a){

		//Unicode characters
		a = a.replace("\u00c2", "a").replace("\u00e0", "a").replace("\u00e2", "a").replace("\u00e3", "a").replace("\u00e3", "a").replace("\u00c1", "a").replace("\u00e1", "a");
		a = a.replace("\u00c9", "e").replace("\u00e9", "e").replace("\u00e8", "e").replace("\u00ea", "e").replace("\u00eb", "e");
		a = a.replace("\u00ed", "i").replace("\u00ee", "i").replace("\u00ef", "i").replace("\u00ec", "i");
		a = a.replace("\u00f4", "o").replace("\u00f2", "o").replace("\u00f6", "o").replace("\u00d6", "o").replace("\u00d3", "o").replace("\u00f3", "o");
		a = a.replace("\u00fb", "u").replace("\u00f9", "u").replace("\u00fc", "u").replace("\u00dc", "u").replace("\u00fa", "u");
		a = a.replace("\u00f1", "n");
		
		a = a.replace("\u00df", "ss");
		a = a.replace("\u00e7", "c");
		a = a.replace("\u0153", "oe");
		a = a.replace("\u00ba", "").replace("\u0093", "").replace("\u0084", "");
		
		return a;
	}
		
}