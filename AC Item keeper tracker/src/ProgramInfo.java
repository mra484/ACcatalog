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
 *   11/23/2013
 */
import java.awt.BorderLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.text.StyledDocument;


public class ProgramInfo extends JPanel{
	private static final long serialVersionUID = 1L;
	
	private JTextPane text2 = new JTextPane();

	public ProgramInfo(){
		setLayout(new BorderLayout());
		setBorder(javax.swing.BorderFactory.createEtchedBorder());
		text2.setEditable(false);
		text2.setBackground(new JFrame().getBackground());
		text2.setContentType("text/html");
		text2.setText("<font size=\"5\"><b>Animal Crossing Item Cataloger</b></font><br> (C) 2013 Mark Andrews<br>" + "github.com/mra484<br><hr size=1>" +
				"AC Item Cataloger was created using information from the following websites:<br><br>" +
				"<b>thonky.com</b><br>" + "Contains tons of helpful information about every aspect of Animal Crossing.  " +
				"Used information for furniture series, sets, themes<br><br>" +
				"<b>animalcrossingnl.mooo.com</b><br>" + "An online catalog complete with images.  " +
				"Used their list of item names to make the main list complete in various languages<br><br>" +
				"<b>gamefaqs.com : Animal Crossing Item List by Liquefy</b><br>" +
				"Contains all information you could want to know about items in Animal Crossing.  " +
				"Used their list of clothing styles<br><hr size =1>" + 
				
				"<font size=\"3\"><p>This program is free software: you can redistribute it and/or modify" +
				"it under the terms of the GNU General Public License as published by"+
				"the Free Software Foundation, either version 3 of the License, or"+
				"(at your option) any later version.</p>"+

    			"<p>This program is distributed in the hope that it will be useful,"+
    			"but WITHOUT ANY WARRANTY; without even the implied warranty of"+
    			"MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the"+
    			"GNU General Public License for more details.</p>"+

    			"<p>You should have received a copy of the GNU General Public License"+
				"along with this program.  If not, see http://www.gnu.org/licenses/.</p></font>");
		add(text2, BorderLayout.CENTER);
	}
}
