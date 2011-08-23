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

import java.io.Serializable;
import java.lang.Cloneable;

/** This class defines positions in a stream of characters. */
public class CharStreamPosition implements Cloneable, Serializable
{

  private static final long serialVersionUID = 9202215234974140708L;
  /** The column of the character. (first char has column 1).*/
  private int m_column = 1;
  /** The line of the character. (first char has line 1).*/
  private int m_line = 1;
  /** The character offset from the beginning of the stream (first char has
   * offset 0).*/
  private int m_charactersOffset = 0;

  /** Creates a new position with default values (column=1, line=1, offset=0). */
  public CharStreamPosition()
  {}

  /** Creates a new position a the same place as the given one.
   * @param pos The position to be take as reference to create this new position. */
  public CharStreamPosition(CharStreamPosition pos)
  { setPosition(pos); }

  /** Creates a new position at the specified place.
   * @param column This position's column.
   * @param line This position's line.
   * @param charactersOffset This position's offset. */
  public CharStreamPosition(int column , int line, int charactersOffset)
  {
    m_column = column;
    m_line = line;
    m_charactersOffset = charactersOffset;
  }

  /** Sets this position to the same place as the specified one.
   * @param pos The position where this one has to be set. */
  public void setPosition(CharStreamPosition pos)
  {
    m_column=pos.m_column;
    m_line=pos.m_line;
    m_charactersOffset=pos.m_charactersOffset;
  }

  /** Sets this position to the specified place.
   * @param column This position's new colum.
   * @param line This position's new line.
   * @param offset This position's new offset. */
  public void setPosition(int column, int line, int offset)
  {
    m_column=column;
    m_line=line;
    m_charactersOffset=offset;
  }

  /** Sets the column of this position.
   * @param column This position's new colum. */
  public void setColumn(int column)
  { m_column = column; }

  /** Sets the line of this position.
   * @param line This position's new line. */
  public void setLine(int line)
  { m_line = line; }

  /** Sets the offset of this position.
   * @param charactersOffset The character offset to be applied to this position. */
  public void setCharactersOffset(int charactersOffset)
  { m_charactersOffset = charactersOffset; }

  /** Returns the column of this position.
   * @return The column of this position. */
  public int getColumn()
  {return m_column; }

  /** Returns the line of this position.
   * @return The line of this position. */
  public int getLine()
  {return m_line; }

  /** Returns the character offset.
   * @return The character offset. Character offset is equal to zero for the
   * first character. */
  public int getCharactersOffset()
  { return m_charactersOffset; }

  /** Returns a new position at the same place as this one.
   * @return A new position at the same place as this one. */
  public Object clone()
  { return new CharStreamPosition(m_column, m_line, m_charactersOffset); }

  /** Returns a string representation this object.
   * @return A string representation this object. */
  public String toString()
  { return "@(L:"+m_line+", C:"+m_column+"; "+m_charactersOffset+")"; }
}