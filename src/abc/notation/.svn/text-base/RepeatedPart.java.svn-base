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

/** A simple <TT>Part</TT> repeated several times. */
public class RepeatedPart extends RepeatedPartAbstract
{
  private static final long serialVersionUID = 3084581464382319491L;
  
  private Part m_part = null;

  /** Creates a new repeated part that will repeat the given part.
   * @param part A part of a tune. */
  public RepeatedPart (Part part)
  { m_part = part; }

  public Part[] toPartsArray()
  {
    int repeatNumber = getNumberOfRepeats();
    Part[] parts = new Part[repeatNumber];
    for (int i=0; i<repeatNumber; i++)
      parts[i] = m_part;
    return parts;
  }
  
  public Object clone(Tune t) {
	  //gets the instance of the clone()d part in Tune
	  //does not create a new clone
	  RepeatedPart rp = new RepeatedPart(t.getPart(m_part.getLabel()));
	  rp.setNumberOfRepeats(getNumberOfRepeats());
	  return rp;
  }
}
