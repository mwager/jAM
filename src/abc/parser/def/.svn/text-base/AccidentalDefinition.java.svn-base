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
import abc.parser.AbcVersion;

/** * */
public class AccidentalDefinition extends AutomataDefinition {
	
	public AccidentalDefinition(AbcVersion abcVesion) {
		State state = new State(AbcTokenType.ACCIDENTAL, true);
		Transition trans = new Transition(state, '^');
		getStartingState().addTransition(trans);
		if (abcVesion.equals(AbcVersion.v1_6)) {
			State state1 = new State(AbcTokenType.ACCIDENTAL, true);
			trans = new Transition(state1, new char[] { '^' });
			state.addTransition(trans);
		} else {
			State state1 = new State(AbcTokenType.ACCIDENTAL, true);
			trans = new Transition(state1, new char[] { '^', '3' });
			state.addTransition(trans);
			State state2 = new State(AbcTokenType.ACCIDENTAL, true);
			state1.addTransition(new Transition(state2, '/'));
			State state3 = new State(AbcTokenType.ACCIDENTAL, true);
			state2.addTransition(new Transition(state3, '2'));
			state.addTransition(new Transition(state2, '/'));
		}

		state = new State(AbcTokenType.ACCIDENTAL, true);
		trans = new Transition(state, '_');
		getStartingState().addTransition(trans);
		if (abcVesion.equals(AbcVersion.v1_6)) {
			State state1 = new State(AbcTokenType.ACCIDENTAL, true);
			trans = new Transition(state1, new char[] { '_' });
			state.addTransition(trans);
		} else {
			State state1 = new State(AbcTokenType.ACCIDENTAL, true);
			trans = new Transition(state1, new char[] { '_', '3' });
			state.addTransition(trans);
			State state2 = new State(AbcTokenType.ACCIDENTAL, true);
			state1.addTransition(new Transition(state2, '/'));
			State state3 = new State(AbcTokenType.ACCIDENTAL, true);
			state2.addTransition(new Transition(state3, '2'));
			state.addTransition(new Transition(state2, '/'));
		}

		state = new State(AbcTokenType.ACCIDENTAL, true);
		trans = new Transition(state, '=');
		getStartingState().addTransition(trans);

	}
}
