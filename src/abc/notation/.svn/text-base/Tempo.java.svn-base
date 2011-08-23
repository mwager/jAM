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
package abc.notation;

/** The tempo class enables you to define tempo values from a reference note
 * length. */
public class Tempo extends MusicElement implements Cloneable
{
  private static final long serialVersionUID = -9003671935699760878L;
  
  /** The length taken as reference to define the tempo value. */
  private short m_referenceLength = Note.QUARTER;
  /** The tempo value in notes per minutes. */
  private short m_value;

  /** Creates a tempo object with the specified tempo value and quarter as
   * length reference.
   * @param notesNbPerMinute The number of quarter notes per minutes. */
  public Tempo (short notesNbPerMinute)
  { m_value = notesNbPerMinute; }

  /** Creates a tempo object with the specified tempo value and the specified
   * length reference.
   * @param referenceLength The reference length.
   * @param value The number of reference lengths per minutes. */
  public Tempo (short referenceLength, short value)
  {
    m_referenceLength = referenceLength;
    m_value = value;
  }

  /** Returns the reference length used to express this tempo.
   * @return The reference length used to express this tempo. Possible
   * values are <TT>Note.SIXTY_FOURTH</TT>, <TT>Note.THIRTY_SECOND</TT> ...
   * or <TT>Note.WHOLE</TT>. */
  public short getReferenceLength()
  { return m_referenceLength; }

  /** Returns the number of note per minutes considering that those notes' length
   * is the reference length.
   * @return the number of note per minutes considering that those notes' length
   * is the reference length.  */
  public short getNotesNumberPerMinute()
  { return m_value; }

  /** Returns the tempo for the given length as reference.
   * @param refLength The note length in which this tempo should be expressed.
   * @return The number of notes of the specified length per minutes. */
  public short getNotesNumberPerMinute(short refLength)
  { return (short)(m_value * (((float)m_referenceLength)/((float)refLength))); }
  
  public Object clone() throws CloneNotSupportedException {
	  return super.clone();
  }
}
