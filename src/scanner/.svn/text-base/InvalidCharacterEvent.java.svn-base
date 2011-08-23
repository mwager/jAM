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
import scanner.CharStreamPosition;

/** This event is thrown when an unexpected character is encountered. */
public class InvalidCharacterEvent extends EventObject implements PositionableInCharStream
{
  private static final long serialVersionUID = -7063895754307596335L;
  /** The invalid character. */
  private char m_character;
  /** The position of the invalid character. */
  private CharStreamPosition m_position = null;

  /** Creates a new event with the following parameters.
   * @param source The source that has detected the invalid character.
   * @param character The invalid character.
   * @param position The position where the invalid character has been detected. */
  public InvalidCharacterEvent(Object source, char character, CharStreamPosition position)
  {
    super(source);
    m_position = position;
    m_character = character;
  }

  /** Returns the invalid character.
   * @return The invalid character. */
  public char getCharacter()
  { return m_character; }

  public int getLength()
  { return 1; }

  /** Returns the position where the invalid character has been detected.
   * @return The position where the invalid character has been detected. */
  public CharStreamPosition getPosition()
  { return m_position; }

  public String toString()
  { return ("'" + m_character + "'" + m_position); }
}
