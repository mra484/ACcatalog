import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
public class DisplayField extends JPanel{
	private ArrayList<JLabel> labelList = new ArrayList<JLabel>();
	private Entry empty = new Entry("--------------------", null);
	
	public DisplayField(){
		this.setVisible(true);
		this.setSize(300, 300);
		this.setLayout(new GridLayout(DisplayWindow.listHeight, 0, 10, 15));
		for( int i = 0 ; i < DisplayWindow.listHeight ; i++ ){
			labelList.add(new JLabel("--------------------"));
			this.add(labelList.get(i));
		}
	}
	
	public void updateList(Entry current, int state){
		Entry head = current;


			for( int i = (DisplayWindow.listHeight / 2) ; i >= 0 ; i-- ){
				if( state < 0 ){
					labelList.get(i).setText("--------------");
					continue;
				}
				labelList.get(i).setText(current.displayName);
				if( current.prev != null )
					current = current.prev;
				else
					current = empty;
			}
		
		current = head;
		if( state == 0 ){
			current = current.next;
			labelList.get(DisplayWindow.listHeight / 2 + 1).setText(current.displayName);
		}
		else
			labelList.get(DisplayWindow.listHeight / 2 + 1).setText("-----------------");
		if( current.next != null )
			current = current.next;
		else
			current = empty;
		
		for( int i = (DisplayWindow.listHeight / 2 + 2 ) ; i < DisplayWindow.listHeight ; i++ ){
			if( state > 0 ){
				labelList.get(i).setText("--------------");
			}
			labelList.get(i).setText(current.displayName);
			if( current.next != null )
				current = current.next;
			else
				current = empty;
		}
	}
}
