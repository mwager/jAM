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

/** **/
public class NthRepeatDefinition extends AutomataDefinition
{
    public NthRepeatDefinition()
    {
    	char[] digit = {'1', '2', '3', '4', '5', '6', '7', '8', '9'};
    	char[] sep = {'-', ','};
    	
        State state = new State(AbcTokenType.UNKNOWN, false);
        State state1 = new State(AbcTokenType.NTH_REPEAT, true);
        State state1b = new State(AbcTokenType.NTH_REPEAT, true);
        State state1c = new State(AbcTokenType.NTH_REPEAT, true);
        State state1d = new State(AbcTokenType.NTH_REPEAT, true);
        State state1e = new State(AbcTokenType.NTH_REPEAT, true);
        State state1f = new State(AbcTokenType.NTH_REPEAT, true);
        State state1g = new State(AbcTokenType.NTH_REPEAT, true);
        getStartingState().addTransition(new Transition(state,'['));
        state.addTransition(new Transition(state1,digit));
        state1.addTransition(new Transition(state1b,sep));
        state1b.addTransition(new Transition(state1c,digit));
        state1c.addTransition(new Transition(state1d,sep));
        state1d.addTransition(new Transition(state1e,digit));
        state1e.addTransition(new Transition(state1f,sep));
        state1f.addTransition(new Transition(state1g,digit));

        State state2 = new State(AbcTokenType.UNKNOWN, false);
        State state3 = new State(AbcTokenType.NTH_REPEAT, true);
        state1b = new State(AbcTokenType.NTH_REPEAT, true);
        state1c = new State(AbcTokenType.NTH_REPEAT, true);
        state1d = new State(AbcTokenType.NTH_REPEAT, true);
        state1e = new State(AbcTokenType.NTH_REPEAT, true);
        state1f = new State(AbcTokenType.NTH_REPEAT, true);
        state1g = new State(AbcTokenType.NTH_REPEAT, true);
        getStartingState().addTransition(new Transition(state2, '|'));
        state2.addTransition(new Transition(state3,'1'));
        state3.addTransition(new Transition(state1b,sep));
        state1b.addTransition(new Transition(state1c,digit));
        state1c.addTransition(new Transition(state1d,sep));
        state1d.addTransition(new Transition(state1e,digit));
        state1e.addTransition(new Transition(state1f,sep));
        state1f.addTransition(new Transition(state1g,digit));

        State state4 = new State(AbcTokenType.UNKNOWN, false);
        State state5 = new State(AbcTokenType.UNKNOWN, false);
        State state6 = new State(AbcTokenType.NTH_REPEAT, true);
        state1b = new State(AbcTokenType.NTH_REPEAT, true);
        state1c = new State(AbcTokenType.NTH_REPEAT, true);
        state1d = new State(AbcTokenType.NTH_REPEAT, true);
        state1e = new State(AbcTokenType.NTH_REPEAT, true);
        state1f = new State(AbcTokenType.NTH_REPEAT, true);
        state1g = new State(AbcTokenType.NTH_REPEAT, true);
        getStartingState().addTransition(new Transition(state4,':'));
        state4.addTransition(new Transition(state5,'|'));
        state5.addTransition(new Transition(state6,new char[] {'2','3','4','5','6','7','8','9'}));
        state6.addTransition(new Transition(state1b,sep));
        state1b.addTransition(new Transition(state1c,digit));
        state1c.addTransition(new Transition(state1d,sep));
        state1d.addTransition(new Transition(state1e,digit));
        state1e.addTransition(new Transition(state1f,sep));
        state1f.addTransition(new Transition(state1g,digit));

    }
}

