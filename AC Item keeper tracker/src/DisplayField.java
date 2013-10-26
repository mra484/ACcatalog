import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
public class DisplayField extends JPanel{
	private ArrayList<ItemLabel> labelList = new ArrayList<ItemLabel>();
	
	public DisplayField(){
		this.setSize(300, 300);
		this.setLayout(new GridLayout(1, 0, 10, 15));
		for( int i = 0 ; i < 7 ; i++ ){
			labelList.add(new ItemLabel(""));
		}
	}
	
	public void updateList(Entry current, boolean match){
		for( int i = 0 ; i < 7 && current != null; i++ ){
			
			//if the item was in the list, skip the third iteration
			if( i == 3 && match )
				continue;
			labelList.get(i).setText(current.displayName);
			current = current.next;
		}
	}
}
