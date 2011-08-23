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

import java.io.Serializable;

/** Abstract class that defines the number of times a part in a tune's music should
 * be repeated. */
public abstract class RepeatedPartAbstract implements Serializable
{
  private static final long serialVersionUID = -7080407911337277773L;
  
  private byte m_repeatNumber = 1;
  /** Creates a new repeated part. By default this part is repeated
   * only once. */
  public RepeatedPartAbstract()
  { super(); }

  /** Returns the number of times this part should be repeated.
   * @return The number of times this part should be repeated. */
  public byte getNumberOfRepeats()
  { return m_repeatNumber; }

  /** Sets the number of times this part should be repeated.
   * @param repeatNumber The number of times this part should be repeated. */
  public void setNumberOfRepeats(byte repeatNumber)
  { m_repeatNumber = repeatNumber; }

  /** Returns this repeated part as an array of singles parts. The playing of
   * this repeated part would sound the same as the playing of the array of parts.
   * @return An array of singles parts that would sound the same as the
   * playing of this repeated part. */
  public abstract Part[] toPartsArray();
  
  public abstract Object clone(Tune t);
}
