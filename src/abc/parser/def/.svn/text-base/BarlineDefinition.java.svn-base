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

/** **/
public class BarlineDefinition extends AutomataDefinition
{
    public BarlineDefinition(AbcVersion abcVersion)
    {
        State state = new State(AbcTokenType.BARLINE, true);
        getStartingState().addTransition(new Transition(state,'|'));

        State state1 = new State(AbcTokenType.BARLINE, true);
        char[] chars = {'|', ']', ':' };
        Transition trans = new Transition(state1,chars);
        state.addTransition(trans);

        State state2 = new State(AbcTokenType.UNKNOWN, false);
        getStartingState().addTransition(new Transition(state2, ':'));
        State state3 = new State(AbcTokenType.BARLINE, true);
        char[] chars1 = {':', '|'};
        trans = new Transition(state3,chars1);
        state2.addTransition(trans);

        //ABC v2 [|] invisible bar line
        //XXX doesn't work, why?
        if (abcVersion.equals(AbcVersion.v2_0)) {
        	State stateI1 = new State(AbcTokenType.UNKNOWN, false);
        	getStartingState().addTransition(new Transition(stateI1, '['));
        	State stateI2 = new State(AbcTokenType.BARLINE, true);
        	trans = new Transition(stateI2, '|');
        	State stateI3 = new State(AbcTokenType.BARLINE, true);
        	stateI2.addTransition(new Transition(stateI3, ']'));
        	stateI1.addTransition(trans);
        }

        State state4 = new State(AbcTokenType.UNKNOWN, false);
        getStartingState().addTransition(new Transition(state4,'['));
        State state5 = new State(AbcTokenType.BARLINE, true);
        trans = new Transition(state5, '|');
        state4.addTransition(trans);

        //TODO |:: ::|
        //ABC v2 dotted bar .| or .|. or :
        if (abcVersion.equals(AbcVersion.v2_0)) {
	        State state6 = new State(AbcTokenType.UNKNOWN, false);
	        getStartingState().addTransition(new Transition(state6,'.'));
	        State state7 = new State(AbcTokenType.BARLINE, true);
	        trans = new Transition(state7, '|');
	        state6.addTransition(trans);

	        State state8 = new State(AbcTokenType.UNKNOWN, false);
	        getStartingState().addTransition(new Transition(state8,'.'));
	        State state9 = new State(AbcTokenType.BARLINE, true);
	        trans = new Transition(state9, '|');
	        State stateA = new State(AbcTokenType.BARLINE, true);
	        state9.addTransition(new Transition(stateA, '.'));
	        state8.addTransition(trans);

	        State stateB = new State(AbcTokenType.BARLINE, true);
	        getStartingState().addTransition(new Transition(stateB, ':'));
	        
        }
    }
}

