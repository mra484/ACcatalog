import javax.swing.*;
import java.awt.*;
public class DisplayField extends JPanel{
	private ItemLabel prev1 = new ItemLabel("");
	private ItemLabel prev2 = new ItemLabel("");
	private ItemLabel prev3 = new ItemLabel("");
	private ItemLabel curr = new ItemLabel("");
	private ItemLabel next1 = new ItemLabel("");
	private ItemLabel next2 = new ItemLabel("");
	private ItemLabel next3 = new ItemLabel("");
	
	public DisplayField(){
		this.setSize(300, 300);
		this.setLayout(new GridLayout(1, 0, 10, 15));
		this.add(prev3);
		this.add(prev2);
		this.add(prev1);
		this.add(curr);
		this.add(next1);
		this.add(next2);
		this.add(next3);
	}
}
