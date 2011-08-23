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

import abc.parser.AbcTokenType;
import abc.parser.AbcVersion;
import scanner.AutomataDefinition;
import scanner.State;
import scanner.Transition;
/** **/
public class SymbolDefinition extends AutomataDefinition
{
  private static char[] chars = {
    '0','1','2','3','4','5',//'6','7','8','9',
    '%','<','>','.','(',')','-',
    'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
    'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};

    public SymbolDefinition(AbcVersion abcVersion)
    {   //===================== FIELD
    	if (abcVersion.isGreaterOrEqual(AbcVersion.v2_0)) {
	        State stateTEXT_CHAR = new State(AbcTokenType.SYMBOL, true);
	        Transition trans = new Transition(stateTEXT_CHAR, chars);
	        getStartingState().addTransition(trans);
	        stateTEXT_CHAR.addTransition(new Transition(stateTEXT_CHAR, chars));
    	}
    }

}

