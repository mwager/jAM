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

/** A special bar line that enables you to repeat part of music from a tune. */
public class RepeatBarLine extends BarLine implements Cloneable {

	private static final long serialVersionUID = 6130499407941371335L;

	private static boolean containsOne(byte[] byteArray) {
		if (byteArray != null) {
			for (int i = 0; i < byteArray.length; i++) {
				if (byteArray[i] == 1)
					return true;
			}
		}
		return false;
	}

	private byte[] m_repeatNumbers = null;

	/**
	 * Creates a new repeat bar line.
	 * 
	 * @param repeatsNumber
	 *            The number of times the repeat should occur.
	 */
	public RepeatBarLine(byte[] repeatNumbers) {
		super(containsOne(repeatNumbers) ? BarLine.SIMPLE
				: BarLine.REPEAT_CLOSE);
		m_repeatNumbers = repeatNumbers;
	}

	/**
	 * Returns the numbers of times the repeat should occur.
	 * 
	 * @return the numbers of times the repeat should occur.
	 */
	public byte[] getRepeatNumbers() {
		return m_repeatNumbers;
	}

	/**
	 * Returns true if this repeat is the first (and maybe 1st and 3th and so
	 * on...). Return false if this repeat is not the first (e.g. 2nd and 3th)
	 * 
	 * A first repeat has a simple bar line, not first has a end repeat bar line
	 * (:|)
	 */
	public boolean isFirstRepeat() {
		return containsOne(m_repeatNumbers);
	}

	/**
	 * Returns a string representation of this repeat barline.
	 * 
	 * @return A string representation of this repeat barline.
	 */
	public String toString() {
		String ret = isFirstRepeat() ? "|" : ":|";
		for (int i = 0; i < m_repeatNumbers.length; i++) {
			if (i > 0)
				ret += ",";
			ret += m_repeatNumbers[i];
		}
		return ret;
	}

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
