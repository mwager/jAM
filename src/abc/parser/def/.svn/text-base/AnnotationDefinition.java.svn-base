// Copyright 2006-2008 Lionel Gueganton
// This file is part of abc4j.
//
// abc4j is free software: you can redistribute it and/or modify
// it under the terms of the GNU Lesser General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// abc4j is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public License
// along with abc4j.  If not, see <http://www.gnu.org/licenses/>.
package abc.parser.def;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import scanner.AutomataDefinition;
import scanner.State;
import scanner.Transition;
import abc.parser.AbcTextReplacements;
import abc.parser.AbcTokenType;

/** **/
public class AnnotationDefinition extends AutomataDefinition {

  private static char[] chars = {
    '0','1','2','3','4','5','6','7','8','9',
    ' ',
    '\t','!','#','$','&','\'','(',')','*','+',',','-','.','/',':',';','<','=','>','?','@','[','\\',']','^','_',
    '`','{','|','}','~','¿','¡',
    'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
    'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z', 
    //==== not really part of v1.6
    //http://jrgraphix.net/research/unicode_blocks.php?block=0
    //check http://en.wikipedia.org/wiki/Basic_Latin for values.
    //all this char are added from the AbcTextReplacements class
    //'à',    'á',      'â',      'ã',      'é',      'ê',      'í',      'ó',      'ô', 
//    '\u00E0', '\u00E1', '\u00E2', '\u00E3', '\u00E9', '\u00EA', '\u00ED', '\u00F3', '\u00F4', // as requiered by Hugo
//    //'õ',    'ú',      'ç',
//    '\u00F5', '\u00FA', '\u00E7',													// as requiered by Hugo 
//    //'À',    'Á',      'Â',      'Ã',      'É',      'Ê',      'Í',      'Ó',      'Ô', 
//    '\u00C0', '\u00C1', '\u00C2', '\u00C3', '\u00C9', '\u00CA', '\u00CD', '\u00D3', '\u00D4',	// as requiered by Hugo
//    //'Õ',    'Ú',      'Ç',
//    '\u00D5', '\u00DA', '\u00C7',													// as requiered by Hugo
//    //'è',    'ù'
//    '\u00E8', '\u00F9',																// French characters
    //thorn 'þ' and 'Þ'
    '\u00DE', '\u00FE',
    };
  
  private static char[] acceptedChars = null;

    public AnnotationDefinition() {
    	if (acceptedChars == null) {
    		//Fills the char array with all latin extended
    		//chars defined in the AbcTextReplacements class.
    		Collection allChars = new ArrayList(chars.length+100);
    		for (int i = 0; i < chars.length; i++) {
				allChars.add(String.valueOf(chars[i]));
			}
    		allChars.addAll(AbcTextReplacements.getInstance().values());
    		acceptedChars = new char[allChars.size()];
        	Iterator it = allChars.iterator();
        	int i = 0;
        	while (it.hasNext()) {
        		acceptedChars[i] = ((String) it.next()).charAt(0);
        		i++;
        	}
        }
    	
    	//===================== FIELD
        State stateANNOTATION_CHAR = new State(AbcTokenType.ANNOTATION, true);
        Transition trans = new Transition(stateANNOTATION_CHAR, acceptedChars);
        getStartingState().addTransition(trans);
        stateANNOTATION_CHAR.addTransition(new Transition(stateANNOTATION_CHAR, acceptedChars));
    }

}

