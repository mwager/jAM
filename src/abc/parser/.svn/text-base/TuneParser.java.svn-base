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

import java.io.Reader;
import java.io.StringReader;

import scanner.NoSuchTokenException;
import scanner.Set;

import abc.notation.Tune;
import abc.parser.def.DefinitionFactory;

/** A convenient class to ease the parsing of ONE tune.
 * The result of the parsing is directly returned as a Tune object
 * synchronously. You don't have to attach yourself as a listener or 
 * whatever to get the parsing result. */
public class TuneParser extends AbcParserAbstract {
	
	/** Constructs a new tune parser.
	 *  
	 * Use {@link #TuneParser(AbcVersion)} to specify which version
	 * to use
	 */
	public TuneParser() {
		this(AbcVersion.v1_6);
	}
	
	/**
	 * Constructs a new tune parser.
	 * 
	 * @param abcVersion
	 *            Specify which ABC version to use for parsing
	 *            {@link AbcVersion#v1_6} or {@link AbcVersion#v2_0}
	 * 
	 * @param abcVersion
	 */
	public TuneParser(AbcVersion abcVersion) {
		super(abcVersion);
	}
  
	/** Parse the given string and creates a <TT>Tune</TT> object as parsing result.
	 * @param tune The abc tune, as a String, to be parsed.
	 * @return An object representation of the abc notation string. */
	public Tune parse(String tune) { 
		return parse(new StringReader(tune));
	}

	/** Parses the specified stream in ABC notation.
	 * @param charStream Tune stream in ABC notation.
	 * @return A tune representing the ABC notation stream. */
	public Tune parse(Reader charStream) {
		try {
			Set current = null;
			init();
			m_scanner.init(charStream);
			
			current = new Set().union(FIRST_ABCHEADER).union(FIRST_FIELD_KEY);
			//m_scanner.setFinaleStateAutomata(getAutomataFor(current.getTypes()));
			m_automata.setDefinition(DefinitionFactory.getDefinition(current
					.getTypes(), m_abcVersion));
			m_scanner.setFinaleStateAutomata(m_automata);
			notifyListenersForTuneBegin();
			try {
				m_token = m_scanner.nextToken();
				m_tokenType = m_token.getType();
			} catch (NoSuchTokenException e) {
				//notifyListenersForInvalidToken(null, new CharStreamPosition(0,0,0), AbcTokenType.FIELD_NUMBER);
				//return new Tune();
			}
			parseAbcTune(current);
		} catch (NoSuchTokenException e) {
			//System.out.println("CATCHING NO SUCH ELEMENT EXCEPTION");
			//e.printStackTrace();
			//Occurs when the last parts of the tune is just invalid characters.
		}
		notifyListenersForTuneEnd(m_tune);
		return m_tune;
	}

	/** Parses the header of the specified tune notation.
	 * @param tune A tune notation in ABC.
	 * @return A tune representing the ABC notation with header values only. */
	public Tune parseHeader(String tune) {
		return parseHeader(new StringReader(tune));
	}

	/** Parse the given string and creates a <TT>Tune</TT> object with no music
	 * as parsing result. This purpose of this method method is to provide a
	 * faster parsing when just abc header fields are needed.
	 * @param charStream The stream to be parsed.
	 * @return An object representation with no score of the abc notation
	 * string. */
	public Tune parseHeader(Reader charStream) {
		notifyListenersForTuneBegin();
		try {
			init();
			m_scanner.init(charStream);
			Set current = new Set().union(FIRST_ABCHEADER).union(
					FIRST_FIELD_KEY);
			m_automata.setDefinition(DefinitionFactory.getDefinition(current
					.getTypes(), m_abcVersion));
			m_scanner.setFinaleStateAutomata(m_automata);
			//m_scanner.setFinaleStateAutomata(getAutomataFor(current.getTypes()));
			try {
				m_token = m_scanner.nextToken();
				m_tokenType = m_token.getType();

			} catch (NoSuchTokenException e) {
				//notifyListenersForInvalidToken(null, new CharStreamPosition(0,0,0), AbcTokenType.FIELD_NUMBER);
				//return new Tune();
			}
			parseAbcHeader(current);
		} catch (NoSuchTokenException e) {
			//Occurs when the last parts of the tune is just invalid characters.
			//System.out.println("CATCHING NO SUCH ELEMENT EXCEPTION");
		}
		notifyListenersForTuneEnd(m_tune);
		return m_tune;
	}

	/** Adds a listener to catch events thrwon by the parser durin tune parsing.
	 * @param listener Object that implements the TuneParserListenerInterface.
	 * @see #removeListener(abc.parser.TuneParserListenerInterface) */
	public void addListener(TuneParserListenerInterface listener) {
		super.addListener(listener);
	}

	/** Removes a listener from this parser.
	 * @param listener The listener to be removed.
	 * @see #addListener(abc.parser.TuneParserListenerInterface) */
	public void removeListener(TuneParserListenerInterface listener) {
		super.removeListener(listener);
	}
}
