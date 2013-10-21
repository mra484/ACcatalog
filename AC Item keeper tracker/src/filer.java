import java.io.*;
import java.util.*;

public class filer {
	private File input;
	private Scanner fileReader;
	private HashSet<String> itemList = new HashSet<String>();
	
	public filer() throws IOException{
		
		try
		{
			input = new File("list.txt");
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
	}
	
	public void searchList(String item){
		
	}
}
