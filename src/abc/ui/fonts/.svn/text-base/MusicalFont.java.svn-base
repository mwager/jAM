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
package abc.ui.fonts;

import java.awt.Font;
import java.io.Serializable;

import abc.notation.Accidental;
import abc.notation.Clef;
import abc.notation.Decoration;
import abc.notation.Note;
import abc.notation.TimeSignature;

/**
 * Interface for musical fonts definitions
 */
public interface MusicalFont extends Cloneable, Serializable {

	/** Returns the Font object */
	public Font getFont() throws Exception;
	
	/** Returns the glyph for an {@link abc.notation.Accidental} */
	public char getAccidental(Accidental acc);

	/** Returns the glyph for {@link abc.notation.BarLine} */
	public char getBarLine(byte barLine);

	/** Returns the glyph for a {@link abc.notation.Clef} */
	public char getClef(Clef clef);

	/** Returns the decoration char */
	public char getDecoration(Decoration decoration);

	/**
	 * Returns the decoration char, depending on the position
	 * 
	 * @param decoration
	 * @param inverted
	 */
	public char getDecoration(Decoration decoration, boolean inverted);

	/** Returns the decoration char */
	public char getDecoration(int decoration);
	
	/**
	 * Returns the decoration char, depending on the position
	 * 
	 * @param decoration
	 * @param inverted
	 */
	public char getDecoration(int decoration, boolean inverted);

	/** Returns the dot of a note */
	public char getDot();
	
	/** Returns the dynamic char */
	public char getDynamic(byte dynamic);

	public String getName();

	/**
	 * Return the char for a given note with a stem down
	 * 
	 * @param strictDuration
	 *            {@link Note#getStrictDuration()}, for example
	 *            {@link Note#QUARTER}
	 */
	public char getNoteStemDownChar(short strictDuration);

	/**
	 * Return the char for a given note with a stem up
	 * 
	 * @param strictDuration
	 *            {@link Note#getStrictDuration()}, for example
	 *            {@link Note#QUARTER}
	 */
	public char getNoteStemUpChar(short strictDuration);

	/** Returns the head of a quarter or shorter note, without any stem */
	public char getNoteWithoutStem();

	/**
	 * Returns the head of a note, without any stem
	 * 
	 * @param strictDuration
	 *            if >= {@link Note#HALF} returns the glyph for half and whole,
	 *            else return the plain glyph for all shorter notes.
	 */
	public char getNoteWithoutStem(short strictDuration);

	/**
	 * Return the char for a given rest.
	 * 
	 * @param strictDuration
	 *            {@link Note#getStrictDuration()}, for example
	 *            {@link Note#QUARTER}
	 */
	public char getRestChar(short strictDuration);

	/** Returns the char which draw the 5 lines of the staff */
	public char getStaffFiveLines();

	/**
	 * Returns the char which draw a stem without the note head
	 * 
	 * @param strictDuration
	 *            {@link Note#getStrictDuration()}, for example
	 *            {@link Note#QUARTER}
	 */
	public char getStemWithoutNoteDownChar(short strictDuration);
	
	/**
	 * Returns the char which draw a stem without the note head
	 * 
	 * @param strictDuration
	 *            {@link Note#getStrictDuration()}, for example
	 *            {@link Note#QUARTER}
	 */
	public char getStemWithoutNoteUpChar(short strictDuration);
	
	/**
	 * Returns the digits for the numerator or the denominator of time signature
	 */
	public char[] getTimeSignatureDigits(int number);
	
	/**
	 * Return abbreviated symbol for 2/2 (C|) and 4/4 (C)
	 * time signatures
	 * @param ts
	 * @throws IllegalArgumentException if time signature is not
	 * 2/2 nor 4/4
	 */
	public char getTimeSignatureAbbreviated(TimeSignature ts)
	throws IllegalArgumentException;

	/** Returns the tuplet digit(s) */
	public char[] getTupletDigits(int nuplet);

}
