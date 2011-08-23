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
package scanner;

import java.util.EventObject;
import scanner.PositionableInCharStream;
import scanner.CharStreamPosition;
import scanner.Token;

/** This event is thrown when a new token has been detected. */
public class TokenEvent extends EventObject implements PositionableInCharStream
{
  private static final long serialVersionUID = 1789076874936938551L;
  /** The token found. */
  protected Token m_token = null;

  /** Creates a new token event.
   * @param source The source that has detected the new token.
   * @param token The detected token. */
  public TokenEvent(Object source, Token token)
  {
    super(source);
    m_token = token;
  }

  public CharStreamPosition getPosition()
  { return m_token.getPosition();}

  public int getLength()
  { return m_token.getLength();}

  /** Returns the found token.
   * @return The found token. */
  public Token getToken()
  { return m_token; }

  /** Returns a string representation of this token event.
   * @return A string representation of this token event. */
  public String toString()
  { return (m_token + "(" + m_token.getType() + ")"); }
}