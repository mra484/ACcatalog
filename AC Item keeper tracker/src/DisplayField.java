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
 * 10/27/2013
 * 
 * This class manages the search result frame, currently made up of an array of JLabels
 */

import javax.swing.*;

import java.awt.*;
import java.util.ArrayList;

public class DisplayField extends JPanel{
	private static final long serialVersionUID = 1L;
	private ArrayList<JLabel> labelList = new ArrayList<JLabel>();
	private Entry empty = new Entry("--------------------------", null);

	//list that makes up the search results
	public DisplayField(){
		this.setBackground(Color.WHITE);
		this.setLayout(new GridLayout(DisplayWindow.listHeight, 0, 10, 15));
		for( int i = 0 ; i < DisplayWindow.listHeight ; i++ ){
			labelList.add(new JLabel("--------------------------"));
			this.add(labelList.get(i));
		}
	}	

	public void updateList(Entry current, int state){
		Entry head = current;

		//move up from the center item
		for( int i = (DisplayWindow.listHeight / 2) ; i >= 0 ; i-- ){
			if( state <= -1){
				labelList.get(i).setText("--------------------------");
				continue;
			}
			labelList.get(i).setText(current.displayName);
			if( current.prev != null )
				current = current.prev;
			else
				current = empty;
		}

		//center item
		current = head;
		if( state == 0 || state == -2 ){
			if( state == 0 )
				current = current.next;
			labelList.get(DisplayWindow.listHeight / 2 + 1).setText(current.displayName);
			labelList.get(DisplayWindow.listHeight / 2 + 1).setBorder(BorderFactory.createBevelBorder(1));
		}
		else{
			labelList.get(DisplayWindow.listHeight / 2 + 1).setText("--------------------------");
			labelList.get(DisplayWindow.listHeight / 2 + 1).setBorder(BorderFactory.createBevelBorder(1));
		}
		if ( state == 1 || state == 0 || state == -2){
			if( current.next != null)
				current = current.next;
			else
				current = empty;
		}
		
		//move down from center item
		for( int i = (DisplayWindow.listHeight / 2 + 2 ) ; i < DisplayWindow.listHeight ; i++ ){
			labelList.get(i).setText(current.displayName);
			if( current.next != null )
				current = current.next;
			else
				current = empty;
		}
	}
}
