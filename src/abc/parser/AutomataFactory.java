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
package abc.parser;

import java.util.Vector;
import scanner.TokenType;
import scanner.FinaleStateAutomata;
import abc.parser.def.DefinitionFactory;

class AutomataFactory {

	public static Vector m_allPreviouslyCreatedAutomatas = new Vector();

	public static FinaleStateAutomata getAutomata(TokenType abcTokenType,
			AbcVersion abcVersion) {
		return new FinaleStateAutomata(DefinitionFactory.getDefinition(
				abcTokenType, abcVersion));
	}

	public static FinaleStateAutomata getAutomata(TokenType[] tokenTypes,
			AbcVersion abcVersion) {
		return new FinaleStateAutomata(DefinitionFactory.getDefinition(
				tokenTypes, abcVersion));
	}

}