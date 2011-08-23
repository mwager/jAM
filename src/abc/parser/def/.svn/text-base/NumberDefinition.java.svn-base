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
import scanner.IsDigitTransition;
import scanner.State;
import scanner.Transition;
import abc.parser.AbcTokenType;

/** **/
public class NumberDefinition extends AutomataDefinition
{
    public NumberDefinition()
    { buildDefinition(); }

    protected void buildDefinition()
    {
        //===================== FIELD
        State stateNUMBER = new State(AbcTokenType.NUMBER, true);
        Transition trans = new IsDigitTransition(stateNUMBER);
        getStartingState().addTransition(trans);
        trans = new IsDigitTransition(stateNUMBER);
        stateNUMBER.addTransition(trans);
    }
}

