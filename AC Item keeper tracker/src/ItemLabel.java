import javax.swing.JLabel;
public class ItemLabel extends JLabel{
	
	public ItemLabel(String a){
		super(a);
	}
	
	public void updateLabel(String a){
		super.setName(a);
	}
}
