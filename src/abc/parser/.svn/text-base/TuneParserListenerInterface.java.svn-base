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

import java.util.EventListener;

import abc.notation.Tune;
import scanner.InvalidCharacterEvent;
import scanner.TokenEvent;

/** Interface that should be implemented by any object that listens to tunes
 * parsing. */
public interface TuneParserListenerInterface extends EventListener
{
  /** Invoked when the parsing of the tune begins. */
  public void tuneBegin();

  /** Invoked when an invalid token has been encountered.
   * @param event An event describing the problem encountered by the parser. */
  public void invalidToken(InvalidTokenEvent event);

  /** Invoked when a valid token has been encountered by the parser.
   * @param event An event describing the valid token parsed. */
  public void validToken(TokenEvent event);

  /** Invoked when an invalid character has been found by the parser.
   * @param event An event describing the invalid character found by the parser. */
  public void invalidCharacter(InvalidCharacterEvent event);

  /** Invoked when the parsing of a tune has ended.
   * @param tune The tune that has just been parsed. */
  public void tuneEnd(Tune tune);
}
