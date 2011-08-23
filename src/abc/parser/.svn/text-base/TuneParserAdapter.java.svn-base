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

import abc.notation.Tune;
import scanner.InvalidCharacterEvent;
import scanner.TokenEvent;

/** An empty implementation of a tune parser listener that does nothing. */
public class TuneParserAdapter implements TuneParserListenerInterface
{
  /** Invoked when the parser reaches the beginning of a tune. */
  public void tuneBegin()
  {}

  /** Invoked when an invalid token has been parsed.
   * @param evt An event describing the invalid token. */
  public void invalidToken(InvalidTokenEvent evt)
  {}

  /** Invoked when a valid token has been parsed.
   * @param evt An event describing the valid token. */
  public void validToken(TokenEvent evt)
  {}

  /** Invoked when an invalid character has been parsed.
   * @param evt An event describing the invalid character. */
  public void invalidCharacter(InvalidCharacterEvent evt)
  {}

  /** Invoked when the parser reaches the end of a tune.
   * @param tune The tune that has just been parsed. */
  public void tuneEnd(Tune tune)
  {}
}
