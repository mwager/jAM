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
import scanner.CharStreamPosition;
import scanner.PositionableInCharStream;
import abc.notation.MultiNote;

/**
 * A multinote that encapsulates the information needed to locate where the abc 
 * information describing this multinote was positioned in the parsed stream.
 */
public class PositionableMultiNote extends MultiNote implements PositionableInCharStream
{
	private static final long serialVersionUID = 4066435012161183112L;
  private CharStreamPosition m_position = null;
  private int m_length = -1;

  public PositionableMultiNote(Vector notes)
  {
    super(notes);
    PositionableNote firstNote = (PositionableNote)notes.elementAt(0);
    PositionableNote lastNote = (PositionableNote)notes.elementAt(notes.size()-1);
    m_position = firstNote.getPosition();
    m_length = lastNote.getPosition().getCharactersOffset() - firstNote.getPosition().getCharactersOffset()
        + lastNote.getLength();
  }

  public CharStreamPosition getPosition()
  { return m_position; }

  public void setBeginPosition(CharStreamPosition position)
  { m_position = position; }

  public void setLength(int length)
  { m_length = length; }

  public int getLength()
  { return m_length; }

  public String toString()
  { return super.toString()+" "+m_position+"(l="+m_length+")"; }
}
