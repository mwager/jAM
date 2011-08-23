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

/** This scanner extends the capabilities of the default scanner to implement
 *  abc tokens scannig.
 *               \n
 *  start -----------------------> LINE_FEED
 *    |                              ^
 *    |  \r                    \n    |
 *    |-------> LINE_FEED-------------
 **/
public class LineFeedDefinition extends AutomataDefinition
{

    public LineFeedDefinition()
    {
      State state = new State(AbcTokenType.LINE_FEED, true);
      Transition trans = new Transition(state,'\n');
      getStartingState().addTransition(trans);

      State state1 = new State(AbcTokenType.LINE_FEED, true);
      trans = new Transition(state1,'\r');
      getStartingState().addTransition(trans);

      trans = new Transition(state,'\n');
      state1.addTransition(trans);
    }
}

