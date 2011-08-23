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

import scanner.Transition;
import scanner.AutomataDefinition;
import scanner.State;
import abc.parser.AbcTokenType;
/*
// header - edit "Data/yourJavaHeader" to customize
// contents - edit "EventHandlers/Java file/onCreate" to customize
//
*/

public class BaseNoteDefinition extends AutomataDefinition
{
    private static char[] chars = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'a', 'b', 'c', 'd', 'e', 'f', 'g'};

    public BaseNoteDefinition()
    { buildDefinition(); }

    protected void buildDefinition()
    {
        State state = new State(AbcTokenType.BASE_NOTE, true);
        Transition trans = new Transition(state,chars);
        getStartingState().addTransition(trans);
    }
}

