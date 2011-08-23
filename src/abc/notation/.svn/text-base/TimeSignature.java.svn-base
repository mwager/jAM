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

/** This class enables you to describe any time signatures like 4/4, 6/8 ... */
public class TimeSignature extends Fraction implements Cloneable {

	private static final long serialVersionUID = -2501211100528653135L;

	/** The 2/2 time signature constant. */
	public static final TimeSignature SIGNATURE_2_2 = new TimeSignature(2, 2);

	/** The 4/4 time signature constant. */
	public static final TimeSignature SIGNATURE_4_4 = new TimeSignature(4, 4);

	/** The 3/4 time signature constant. */
	public static final TimeSignature SIGNATURE_3_4 = new TimeSignature(3, 4);

	/** The 6/8 time signature constant. */
	public static final TimeSignature SIGNATURE_6_8 = new TimeSignature(6, 8);

	private int[] m_sumOfNumerators = null;

	/**
	 * Creates a new time signature with the specified parameters.
	 * 
	 * @param num
	 *            The number of beat in a bar.
	 * @param den
	 *            The type of those beats.
	 */
	public TimeSignature(int num, int den) {
		super(num, den);
	}

	/**
	 * Creates a new time signature with sum of numerators.
	 * 
	 * @param sumOfNumerators
	 *            The number of beat in a bar.
	 * @param den
	 *            The type of those beats.
	 */
	public TimeSignature(int[] sumOfNumerators, int den) {
		super(1, den);
		if (sumOfNumerators.length == 1) {
			setNumerator(sumOfNumerators[0]);
		} else {
			m_sumOfNumerators = sumOfNumerators;
			int num = 0;
			for (int i = 0; i < m_sumOfNumerators.length; i++) {
				num += m_sumOfNumerators[i];
			}
			setNumerator(num);
		}
	}

	/**
	 * Returns the default note length for this time signature.
	 * 
	 * @return The default note length for this time signature. The default note
	 *         length is equals to <TT>Note.SIXTEENTH</TT> when the time
	 *         signature decimal conversion value is strictly less than 0.75. If
	 *         it's higher, the default is <TT>Note.EIGHTH</TT>.
	 */
	public short getDefaultNoteLength() {
		short currentNoteLength;
		if (this.floatValue() < 0.75)
			currentNoteLength = Note.SIXTEENTH;
		else
			currentNoteLength = Note.EIGHTH;
		return currentNoteLength;
	}

	/**
	 * Return <TT>true</TT> if this time signature if compound, <TT>false</TT>
	 * otherwise.
	 * 
	 * @return <TT>true</TT> if this time signature if compound, <TT>false</TT>
	 *         otherwise. A time signature is considered as compound if its top
	 *         number can be divided by 3. As a way of consequence, compound
	 *         time signatures are 3/4, 3/8, 9/8 etc... simple time signatures
	 *         are C, 4/4, 2/4 etc...
	 */
	public boolean isCoumpound() {
		// a time signature is compound if the top number can be divised by 3.
		// http://www.musictheory.halifax.ns.ca/14tsmc.html
		return (getNumerator() % 3 == 0);
	}

	public int getNumberOfDefaultNotesPerBeat(short defaultLength) {
		// The reference length for meter
		short meterDefLength = Note.convertToNoteLengthStrict(1,
				getDenominator());
		return meterDefLength / defaultLength;
	}

	public boolean equals(Object o) {
		if (o instanceof TimeSignature) {
			return (((TimeSignature) o).getDenominator() == this
					.getDenominator() && ((TimeSignature) o).getNumerator() == this
					.getNumerator());
		} else
			return super.equals(o);
	}

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	/**
	 * Returns an array of int if time signature is made by
	 * a sum of numerators.
	 * 
	 * e.g. 7+9/8, returns new int[] {7,9}
	 * 
	 * @return <code>null</code> if not a sum
	 */
	public int[] getSumOfNumerators() {
		return m_sumOfNumerators;
	}

	public boolean isSumOfNumerators() {
		return (m_sumOfNumerators != null) && (m_sumOfNumerators.length > 1);
	}

}
