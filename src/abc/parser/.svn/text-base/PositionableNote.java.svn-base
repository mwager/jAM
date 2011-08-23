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

import abc.notation.Accidental;
import abc.notation.Note;
import scanner.CharStreamPosition;
import scanner.PositionableInCharStream;

/**
 * A note that encapsulates the information needed to locate where the abc 
 * information describing this note was positioned in the parsed stream.
 */
public class PositionableNote extends Note implements PositionableInCharStream
{
	private static final long serialVersionUID = 1828128323496336180L;
  private CharStreamPosition m_position = null;
  private int m_length = -1;

  public PositionableNote(byte heigthValue, Accidental accidental)
  { super(heigthValue, accidental); }

  public PositionableNote(byte heigthValue, Accidental accidental, byte octaveTranspositionValue)
  { super(heigthValue, accidental, octaveTranspositionValue); }

  public CharStreamPosition getPosition()
  { return m_position; }

  public void setBeginPosition(CharStreamPosition position)
  { m_position = position; }

  public void setLength(int length)
  { m_length = length; }

  public int getLength()
  { return m_length; }

  public String toString()
  { return super.toString()+" "+m_position+"(l=" + m_length+")"; }
}
