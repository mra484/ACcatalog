/**@author Mark Andrews
 *11/10/2013
 * Class handles all file related operations.  masterIndex.txt and userIndex.txt use the
 * UTF-8 character set, settings.ini uses ANSI
 */

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class FileHandler{

	private FileInputStream fis;
	private BufferedReader unicodeReader;
	private FileOutputStream fos;
	private BufferedWriter unicodeWriter;
	private File input;
	private File input2;
	private PrintStream fileWriter;
	private Scanner fileReader;
	
	private filer listManager = null;
	private DisplayWindow mainWindow = null;
	private Entry selected = null;
	
	public FileHandler(filer a, DisplayWindow b) {

			listManager = a;
			mainWindow = b;
			
			readSettings();
			
			openFileRead("masterIndex.txt");
			readReferenceList();
			
			openFileRead("userIndex.txt");
			readUserList();
			
//			saveFiles();
	}
	
	private void readSettings(){
		String read;
		String[] readValues;
		double x, y;
		openPlainFileRead("settings.ini");
		if(!fileReader.hasNext()){
			fileReader.close();
			return;
		}
		try{
		//read from settings file, if anything doesn't fit, ignore the rest of the file and continue
		read = fileReader.nextLine();
		readValues = read.split("=");
		DisplayWindow.language = Integer.parseInt(readValues[1]);

		read = fileReader.nextLine();
		readValues = read.split("=");
		DisplayWindow.readOnly = Boolean.parseBoolean(readValues[1]);

		read = fileReader.nextLine();
		readValues = read.split("=");
		DisplayWindow.defaultOwned = Boolean.parseBoolean(readValues[1]);
		
		read = fileReader.nextLine();
		readValues = read.split("=");
		x = Double.parseDouble(readValues[1]);
	
		read = fileReader.nextLine();
		readValues = read.split("=");
		y = Double.parseDouble(readValues[1]);
		
		DisplayWindow.windowPos = new Point();
		DisplayWindow.windowPos.setLocation(x,y);
		} catch (Exception e){
			System.out.println("Error reading from settings.ini");
		}
		
		openFileRead("userIndex.txt");
		try {
			
			//check the start of the userIndex for a language value
			read = unicodeReader.readLine();
			if( read.contains("@") ){
				readValues = read.split(" ");
				DisplayWindow.language = Integer.parseInt(readValues[1]);
			}
			unicodeReader.close();
		} catch (IOException e) {
			System.out.println("Problem reading from userIndex.txt in readSettings().");
		}
				
		fileReader.close();
		
	}
	
	private void openFileRead(String fileName) {

		try
		{
			fis = new FileInputStream(fileName);
			unicodeReader = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
		} catch (FileNotFoundException e) {
			System.out.println("Unable to open " + fileName + " for reading");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void openPlainFileRead(String fileName){
		input = new File(fileName);
		try {
			fileReader = new Scanner(input);
		} catch (FileNotFoundException e) {
			try {
				input.createNewFile();
				fileReader = new Scanner(input);
			} catch (IOException e1) {
				System.exit(-1);
			}
		}
		
	}
	private void openFileWrite(String fileName) {
		try
		{
			fos = new FileOutputStream(fileName);
			unicodeWriter = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));
		}
		catch (FileNotFoundException ex)
		{
			System.out.println("Unable to open " + " for writing");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void openPlainFileWrite(String fileName){
		input = new File(fileName);
		try {
			fileWriter = new PrintStream(input);
		} catch (FileNotFoundException e) {
			System.out.println("Unable to open " + fileName + " for writing");
		}
		
	}

	private void readReferenceList(){

		Entry newEntry;
		String a, b, c, d, e, f, g, h;
		String[] splitArray, intArray;
		byte type, series, set, theme, clothes, style, furniture;

		try {
			while(true){
				a = unicodeReader.readLine();
				if(a == null)
					break;

				//split array into clusters containing attribute values, and names
				//split the values array into individual values
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
				newEntry = new Entry(a, b, c, d, e, f, g, h, type, series, set, theme, clothes, style, furniture, null);
				listManager.getList().put(newEntry.searchName, newEntry);
				listManager.incTotalItems();
			}
			unicodeReader.close();
		} catch (IOException e1) {
			System.out.println("Unable to read from masterIndex.txt in readReferenceList()");
		}
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

				//check for the current item in the index, if it isn't listed, print it to the console and move to the next item
				current = new Entry(name, null);
				if(listManager.getList().containsKey(current.searchName)){
					current = listManager.getList().get(current.searchName);
				} else{
					listManager.getMissingList().add(current.displayName);
					System.out.println(current.displayName);
					continue;
				}

				//use with specific lists to edit item properties
				//current.setStyle((byte)9);
				
				//set link for searches, owned for browsing, and inc user size for itemSorter
				current.addPrev(prev);
				current.setOwned(true);
				listManager.incUserSize();
				
				//take separate action if this is the first item to be read
				if( prev != null )
					prev.addNext(current);
				else{
					listManager.setHead(current);
					current.setHead(true);
				}
				prev = current;

			}
			unicodeReader.close();
			
		} catch (IOException e) {
			System.out.println("Unable to read from userIndex.txt in readUserList()");
		}
	
		listManager.setLast(current);
		if( current != null)
			current.setLast(true);
	}
	
	public void saveReference(){
		
		//masterIndex will save all information except whether it is owned or not
		openFileWrite("masterIndex.txt.temp");
		try {
			for(Entry a: listManager.getList().values()){
				unicodeWriter.write(a.toString(), 0, a.toString().length());
				unicodeWriter.newLine();
			}
			unicodeWriter.flush();
			unicodeWriter.close();
			fos.close();
			} catch (IOException e) {
				System.out.println("Unable to save to masterIndex.txt");
			}
		input2 = new File("masterIndex.txt");
		input2.delete();
		input = new File("masterIndex.txt.temp");
		input.renameTo(new File("masterIndex.txt"));
		
	}
	
	public void saveUser(){

		//userIndex will save the name of owned items only in the current language of the program
		openFileWrite("userIndex.txt.temp");
		try {
			unicodeWriter.write(String.format("%s %d", "@", DisplayWindow.language));
			unicodeWriter.newLine();
			for(Entry a: listManager.getList().values()){
				if(a.getOwned()){
					unicodeWriter.write(a.displayName);
					unicodeWriter.newLine();					
				}
			}
			unicodeWriter.flush();
			unicodeWriter.close();
			fos.close();
			} catch (IOException e) {
				System.out.println("Unable to save to userIndex.txt");
			}
		input2 = new File("userIndex.txt");
		input2.delete();
		input = new File("userIndex.txt.temp");
		input.renameTo(new File("userIndex.txt"));
		
		
	}
	public void saveSettings(){
		
		//System settings saved are the language, read only, display owned items checkbox status, and the x y coordinates of the main window
		openPlainFileWrite("settings.ini.temp");
		fileWriter.printf("language=%d\nreadOnly=%s\ndefaultOwned=%s\nmainWindow.X=%d\nmainWindow.Y=%d", DisplayWindow.language, DisplayWindow.readOnly,
				DisplayWindow.defaultOwned, mainWindow.getX(), mainWindow.getY());
		fileWriter.flush();
		fileWriter.close();
		input2 = new File("settings.ini");
		System.out.println(input2.delete());
		input = new File("settings.ini.temp");
		input.renameTo(new File("settings.ini"));
	}
}
