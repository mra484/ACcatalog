/**@author Mark Andrews
 *11/10/2013
 * Class handles all file related operations.  masterIndex.txt and userIndex.txt use the
 * UTF-8 character set, settings.ini uses ANSI
 */

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


public class FileHandler {

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
	
	public FileHandler(filer a, DisplayWindow b) {

		try {
			listManager = a;
			mainWindow = b;
			readSettings();
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
	
	private void readSettings(){
		openPlainFileRead("settings.ini");
		if(!fileReader.hasNext()){
			fileReader.close();
			return;
		}
		DisplayWindow.language = fileReader.nextInt();
		DisplayWindow.readOnly = fileReader.nextBoolean();
		DisplayWindow.windowPos = new Point();
		DisplayWindow.windowPos.setLocation(fileReader.nextDouble(), fileReader.nextDouble());
		fileReader.close();
		
	}
	
	private void openFileRead(String fileName) {

		try
		{
			fis = new FileInputStream(fileName);
			unicodeReader = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
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
				current = new Entry(name, null);
				if(listManager.getList().containsKey(current.searchName)){
					current = listManager.getList().get(current.searchName);
				} else
					continue;
				current.addPrev(prev);
				current.setOwned(true);
				listManager.incUserSize();
				if( prev != null )
					prev.addNext(current);
				else{
					listManager.setHead(current);
					current.setHead(true);
				}
				prev = current;
				
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		listManager.setLast(current);
		if( current != null)
			current.setLast(true);
		
	}

	//saves files after every operation
	public void saveFiles(){
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		input2 = new File("masterIndex.txt");
		input2.delete();
		input = new File("masterIndex.txt.temp");
		input.renameTo(new File("masterIndex.txt"));

		openFileWrite("userIndex.txt.temp");
		try {
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		input2 = new File("userIndex.txt");
		input2.delete();
		input = new File("userIndex.txt.temp");
		input.renameTo(new File("userIndex.txt"));
		
		openPlainFileWrite("settings.ini.temp");
		fileWriter.printf("%d %s %d %d", DisplayWindow.language, DisplayWindow.readOnly, mainWindow.getX(), mainWindow.getY());
		fileWriter.flush();
		fileWriter.close();
		input2 = new File("settings.ini");
		System.out.println(input2.delete());
		input = new File("settings.ini.temp");
		input.renameTo(new File("settings.ini"));
	}
}
