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

import javax.swing.JPanel;
import javax.swing.JTextArea;


public class ProgramInfo extends JPanel{
	private static final long serialVersionUID = 1L;
	
	private JTextArea text = new JTextArea();

	public ProgramInfo(){
		setLayout(new BorderLayout());
		setBorder(javax.swing.BorderFactory.createEtchedBorder());
		text.setEditable(false);
		text.setLineWrap(true);
		text.setMargin(new Insets(10, 10, 10, 10));
		text.append("Animal Crossing Item Cataloger (C) 2013 Mark Andrews\n" + "github.com/mra484\n\n" +
				"AC Item Cataloger was created using information from the following websites:\n\n\n" +
				"thonky.com\n\n" + "Contains tons of helpful information about every aspect of Animal Crossing\n" +
				"Used information for furniture series, sets, themes\n\n\n" +
				"animalcrossingnl.mooo.com\n\n" + "An online catalog complete with images\n" +
				"Used their list of item names to make the main list complete in various languages\n\n\n" +
				"gamefaqs.com : Animal Crossing Item List by Liquefy\n\n" +
				"Contains all information you could want to know about items in Animal Crossing\n" +
				"Used their list of clothing styles\n\n");
		add(text, BorderLayout.CENTER);
	}
}
