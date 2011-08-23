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

import scanner.AutomataDefinition;
import scanner.State;
import scanner.Transition;
import abc.parser.AbcTokenType;

/** * */
public class ClefTextDefinition extends AutomataDefinition {

	public ClefTextDefinition() {
		// starts with one of these words
		//String[] wordsSTART = { "clef=", "treble", "alto", "tenor", "bass", "baritone",
		//		"mezzo", "soprano", "+8", "-8", "s=", "staffline", "m=", "middle",
		//		"t=", "transpose", "perc", "none" };
		char[] chars0 = { 'a', 'b', 'c', 'm', 'n', 'p', 's', 't', '+', '-' };
		char[] chars1 = { 'a', 'e', 'i', 'l', 'o', 'r', 't', '8', '=' };
		char[] chars2 = { 'a', 'e', 'd', 'n', 'p', 'r', 's', 't', 'z', ' ',
				'1', '2', '3', '4', '5', '6', '7', '8', '9' };
		char[] charsFOLLOW = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', '=', '+', '-', ',', '\'', ' ', 'A', 'B', 'C', 'D',
			'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
			'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd',
			'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
			'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
		State stateCLEF = new State(AbcTokenType.CLEF, true);
		getStartingState().addTransition(new Transition(stateCLEF, chars0));
		State state1 = new State(AbcTokenType.CLEF, true);
		stateCLEF.addTransition(new Transition(state1, chars1));
		State state2 = new State(AbcTokenType.CLEF, true);
		state1.addTransition(new Transition(state2, chars2));
		//State state3 = new State(AbcTokenType.CLEF, true);
		state2.addTransition(new Transition(state2, charsFOLLOW));
		
//		for (int i = 0; i < wordsSTART.length; i++) {
//			State stateCLEF = new State(AbcTokenType.CLEF, true);
//			getStartingState().addTransition(new Transition(stateCLEF, wordsSTART[i].charAt(0)));
//			State state = null;
//			for (int j = 1; j < wordsSTART[i].length(); j++) {
//				state = new State(AbcTokenType.CLEF, true);
//				(j==1?stateCLEF:state).addTransition(new Transition(
//					state, wordsSTART[i].charAt(j)));
//			}
//			state.addTransition(new Transition(state, charsFOLLOW));
//		}
		//State stateCLEF = new State(AbcTokenType.CLEF, true);
		//getStartingState().addTransition(new Transition(stateCLEF, charsSTART));
		//stateCLEF.addTransition(new Transition(stateCLEF, charsFOLLOW));
	}
}
