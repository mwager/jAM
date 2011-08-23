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
import abc.notation.BarLine;
import abc.notation.Clef;
import abc.notation.Decoration;
import abc.notation.Dynamic;
import abc.notation.Note;
import abc.notation.TimeSignature;

/**
 * Font definition for SONORA.TTF
 */
public class SonoraFont extends MusicalFontAbstract implements MusicalFont, Serializable {
	
	private static final long serialVersionUID = 659612792568835733L;

	/** Digits for time signature */
	private static final char[] DIGITS = {
		'\uF030', //0
		'\uF031', //1
		'\uF032', //2
		'\uF033', //3
		'\uF034', //4
		'\uF035', //5
		'\uF036', //6
		'\uF037', //7
		'\uF038', //8
		'\uF039'}; //9
	
	/** Digits for fingerings */
	private static final char[] FINGERINGS_DIGITS = {
		'\uF102', //0
		'\uF103', //1
		'\uF104', //2
		'\uF105', //3
		'\uF106', //4
		'\uF107', //5
		'\uF108', //6
		'\uF109', //7
		'\uF10A', //8
		'\uF10B'}; //9
	
	private static transient Font font = null;
	
	/** Tuplet digits, in italics */
	private static final char[] ITALICS_DIGITS = {
		'\uF0BC', //0
		'\uF0C1', //1
		'\uF0AA', //2
		'\uF0A3', //3
		'\uF0A2', //4
		'\uF0B0', //5
		'\uF0A4', //6
		'\uF0A6', //7
		'\uF0A5', //8
		'\uF0BB'}; //9
	
	/** Unknown note length */
	private static final char UNKNWON_NOTE = '\uF0AD';

	public SonoraFont() {
		//voided
	}
	
	public Font getFont() throws Exception {
		if (font == null) {
			font = Font.createFont(
					Font.TRUETYPE_FONT,
					getClass().getResourceAsStream("SONORA3.TTF"));
		}
		return font;
	}

	public char getAccidental(Accidental acc)
	throws MissingGlyphException, IllegalArgumentException {
		if (acc.isInTheKey())
			throw new IllegalAccessError("No glyph for NONE accidental type");
		if (acc.isFlat()) return '\uF062';
		if (acc.isNatural()) return '\uF06E';
		if (acc.isSharp()) return '\uF023';
		if (acc.isDoubleFlat()) return '\uF0BA';
		if (acc.isDoubleSharp()) return '\uF099';//F0DC
		if (acc.equals(Accidental.HALF_FLAT)) return '\uF0F5';
		if (acc.equals(Accidental.FLAT_AND_A_HALF)) return '\uF0F6';
		if (acc.equals(Accidental.HALF_SHARP)) return '\uF0F7';
		if (acc.equals(Accidental.SHARP_AND_A_HALF)) return '\uF0F8';
		
		//case Accidental.COMMA_SHARP_ONE: return '\uF08E'; //Koma Diyezi
		//case Accidental.COMMA_SHARP_FIVE: return '\uF08F'; //Küçük Mücennep Diyezi
		//case Accidental.COMMA_SHARP_EIGHT: return '\uF090'; //Buyuk Mücennep Diyezi
		//case Accidental.COMMA_FLAT_ONE: return '\uF091'; //Koma Bemolü
		//case Accidental.COMMA_FLAT_FOUR: return '\uF092'; //Bakiyye Bemolü
		//case Accidental.COMMA_FLAT_EIGHT: return '\uF093'; //Tanini Bemolü
		
		//TODO HALF_SHARP derived from F023
		//TODO HALF_FLAT derived from F062
		//TODO HALF_SHARP derived from F023
		//TODO HALF_FLAT derived from F062
		//also exists "sharp and half", "flat and half" but
		//don't know how to write them in ABC
		throw new MissingGlyphException("Accidental type "+acc.toString(), this);
	}
	
	/** Returns the simple bar line glyph */
	public char getBarLine(byte barLine) {
		switch(barLine) {
		case BarLine.SIMPLE:
		//all other are drawn in JBarLine
		default:
			return '\uF05C';
		}
	}
	
	public char getClef(Clef clef) throws MissingGlyphException {
		if (clef.isG()) {
			switch(clef.getOctaveTransposition()) {
			case 0: return '\uF026'; //G
			case 1: return '\uF0A0'; //G 8va
			case -1: return '\uF056'; //G 8vb
			}
		}
		else if (clef.isF()) {
			switch(clef.getOctaveTransposition()) {
			case 0: return '\uF03F'; //F
			case 1: return '\uF10E'; //F 8va
			case -1: return '\uF074'; //F 8vb
			}
		}
		else if (clef.isC()) {
			switch(clef.getOctaveTransposition()) {
			case 0: return '\uF042'; //C
			}
		}
		else if (clef.isPerc()) return '\uF03A';
		else if (clef.equals(Clef.ottava_8va)) return '\uF0C3';
		else if (clef.equals(Clef.ottava_8vb)) return '\uF0D7';
		else if (clef.equals(Clef.ottava_15ma)) return '\uF0DB';
		else if (clef.equals(Clef.ottava_15mb)) return '\uF100';
		else if (clef.equals(Clef.NONE)) return ' ';
		throw new MissingGlyphException(clef, this);
	}
	
	public char getDecoration(int decoration, boolean inverted) {
		switch(decoration) {
		case Decoration.ACCENT:
		//case Decoration.MARCATO: marcato=accent
			return '\uF03E'; // ">"
		case Decoration.STACCATO: return '\uF02E'; // "."
		case Decoration.STACCATISSIMO:
			return !inverted?'\uF04E'//under note
							:'\uF054';//above note
		case Decoration.UPBOW: return '\uF076';
		case Decoration.DOWNBOW: return '\uF0B3';
		case Decoration.TRILL: return '\uF0D9'; // "tr"
		case Decoration.FERMATA: return '\uF055';
		case Decoration.FERMATA_INVERTED: return '\uF075';
		case Decoration.LOWERMORDENT: return '\uF101';
		case Decoration.UPPERMORDENT: return '\uF06D';
		case Decoration.SEGNO: return '\uF025';
		case Decoration.CODA: return '\uF0DE';
		case Decoration.BREATH: return '\uF02C'; // ","
		case Decoration.BREATH_LONGER: return '\uF022'; // "//"
		case Decoration.TENUTO: return '\uF05F';
		case Decoration.SFORZANDO:
			return !inverted?'\uF05E':'\uF076';
		case Decoration.MARCATO_STACCATO:
			return !inverted?'\uF0DF':'\uF091';
		case Decoration.MEZZO_STACCATO:
			return !inverted?'\uF090':'\uF03C';
		case Decoration.MARTELATO_STACCATO:
			return !inverted?'\uF0AC':'\uF0E8';
		case Decoration.PEDAL_DOWN: return '\uF0A1';
		case Decoration.PEDAL_UP: return '\uF02A';
		case Decoration.ROLL:
			return !inverted?'\uF017':'\uF016';
		case Decoration.STEM_COMBINE_UP_SINGLE: return '\uF021';
		case Decoration.STEM_COMBINE_UP_DOUBLE: return '\uF040';
		case Decoration.STEM_COMBINE_UP_TRIPLE: return '\uF0BE';
		case Decoration.FINGERING_0: return getFingeringsDigits(0)[0];
		case Decoration.FINGERING_1: return getFingeringsDigits(1)[0];
		case Decoration.FINGERING_2: return getFingeringsDigits(2)[0];
		case Decoration.FINGERING_3: return getFingeringsDigits(3)[0];
		case Decoration.FINGERING_4: return getFingeringsDigits(4)[0];
		case Decoration.FINGERING_5: return getFingeringsDigits(5)[0];
		case Decoration.PLUS: return '\uF08E';
		case Decoration.OPEN: return '\uF08F';
		case Decoration.THUMB: return '\uF098';//thumb, snap
		case Decoration.WEDGE: return '\uF0AE';
		case Decoration.TURN:
		case Decoration.TURNX:
			return '\uF10C';
		case Decoration.TURN_INVERTED:
		case Decoration.TURNX_INVERTED:
			return '\uF10D';
		case Decoration.REPEAT_LAST_BAR:
			return '\uF0D4';
		case Decoration.REPEAT_LAST_TWO_BARS:
			return '\uF0C7';
		case Decoration.GENERAL_GRACING:
			return '\uF015';
		default:
			throw new MissingGlyphException("Decoration "
					+ decoration, this);
		}
	}
	
	public char[] getDigits(int number) {
		char[] iChars = String.valueOf(number).toCharArray();
		for (int i = 0; i < iChars.length; i++) {
			iChars[i] = DIGITS[Integer.parseInt(""+iChars[i])];
		}
		return iChars;
	}
	
	public char getDot() {
		return '\uF06B';
	}
	
	public char getDynamic(byte dynamic) {
		switch(dynamic) {
		case Dynamic.PPPP: return '\uF0AF';
		case Dynamic.PPP: return '\uF0B8';
		case Dynamic.PP: return '\uF0B9';
		case Dynamic.P: return '\uF070';
		case Dynamic.MP: return '\uF050';
		case Dynamic.MF: return '\uF046';
		case Dynamic.F: return '\uF066';
		case Dynamic.FF: return '\uF0C4';
		case Dynamic.FFF: return '\uF0EC';
		case Dynamic.FFFF: return '\uF0EB';
		//case Dynamic.M: return '\uF0BD';
		//case Dynamic.S: return '\uF073';
		//case Dynamic.Z: return '\uF07A';
		case Dynamic.FP: return '\uF0EA';
		case Dynamic.SF: return '\uF053';
		case Dynamic.SFPP: return '\uF0B6';
		case Dynamic.SFP: return '\uF082';
		case Dynamic.SFZ: return '\uF0A7';
		case Dynamic.FZ: return '\uF05A';
		case Dynamic.SFFZ: return '\uF08D';
		default:
			throw new MissingGlyphException("Dynamic "+dynamic, this);
		}
	}

	public char[] getFingeringsDigits(int number) {
		char[] iChars = String.valueOf(number).toCharArray();
		for (int i = 0; i < iChars.length; i++) {
			iChars[i] = FINGERINGS_DIGITS[Integer.parseInt(""+iChars[i])];
		}
		return iChars;
	}
	
	public char[] getItalicDigits(int number) {
		char[] iChars = String.valueOf(number).toCharArray();
		for (int i = 0; i < iChars.length; i++) {
			iChars[i] = ITALICS_DIGITS[Integer.parseInt(""+iChars[i])];
		}
		return iChars;
	}
	
	public String getName() {
		return "SONORA";
	}
	
	public char getNoteStemDownChar(short strictDuration) {
		switch (strictDuration) {
		case Note.SIXTY_FOURTH: return '\uF01D';
		case Note.THIRTY_SECOND: return '\uF058';
		case Note.SIXTEENTH: return '\uF052';
		case Note.EIGHTH: return '\uF045';
		case Note.QUARTER: return '\uF051';
		case Note.HALF: return '\uF048';
		case Note.WHOLE: return '\uF092';
		case Note.BREVE: return '\uF057';
		case Note.LONG: return '\uF0DD';
		default: return UNKNWON_NOTE;
		}
	}
	
	public char getNoteStemUpChar(short strictDuration) {
		switch (strictDuration) {
		case Note.SIXTY_FOURTH: return '\uF01C';
		case Note.THIRTY_SECOND: return '\uF078';
		case Note.SIXTEENTH: return '\uF072';
		case Note.EIGHTH: return '\uF065';
		case Note.QUARTER: return '\uF071';
		case Note.HALF: return '\uF068';
		case Note.WHOLE: return '\uF092';
		case Note.BREVE: return '\uF057';
		case Note.LONG: return '\uF0DD';
		default: return UNKNWON_NOTE;
		}
	}
	
	public char getNoteWithoutStem() {
		return getNoteWithoutStem(Note.QUARTER);
	}
	
	public char getNoteWithoutStem(short strictDuration) {
		if (strictDuration >= Note.WHOLE) {//breve and long
			return getNoteStemUpChar(strictDuration);
		}
		if (strictDuration >= Note.HALF)
			return '\uF092';
		else
			return '\uF0CF';
	}
	
	public char getRestChar(short strictDuration) {
		switch (strictDuration) {
		case Note.SIXTY_FOURTH: return '\uF0F4';
		case Note.THIRTY_SECOND: return '\uF0A8';
		case Note.SIXTEENTH: return '\uF0C5';
		case Note.EIGHTH: return '\uF0E4';
		case Note.QUARTER: return '\uF0CE';
		case Note.HALF: return '\uF0EE';
		case Note.WHOLE: return '\uF0B7';
		case Note.BREVE: return '\uF0E3';
		default: return UNKNWON_NOTE;
		}
	}

	public char getStaffFiveLines() {
		return '\uF03D';
	}

	public char getStemWithoutNoteDownChar(short strictDuration) {
		switch (strictDuration) {
		case Note.SIXTY_FOURTH: return '\uF01B';
		case Note.THIRTY_SECOND: return '\uF041';
		case Note.SIXTEENTH: return '\uF049';
		case Note.EIGHTH: return '\uF050';
		case Note.QUARTER:
		case Note.HALF: return '\uF019';
		//whole, breve and long have no stem
		default: return UNKNWON_NOTE;
		}
	}
	
	public char getStemWithoutNoteUpChar(short strictDuration) {
		switch (strictDuration) {
		case Note.SIXTY_FOURTH: return '\uF01A';
		case Note.THIRTY_SECOND: return '\uF061';
		case Note.SIXTEENTH: return '\uF069';
		case Note.EIGHTH: return '\uF06A';
		case Note.QUARTER:
		case Note.HALF: return '\uF018';
		//whole, breve and long have no stem
		default: return UNKNWON_NOTE;
		}
	}
	
	public char getTimeSignatureAbbreviated(TimeSignature ts)
	throws IllegalArgumentException {
		if (ts.equals(TimeSignature.SIGNATURE_4_4)) {
			return '\uF063';
		} else if (ts.equals(TimeSignature.SIGNATURE_2_2)) {
			return '\uF043';
		} else
			throw new IllegalArgumentException("There is no " +
					"abbreviated symbol for time signature " +
					ts.toString());
	}
	
	public char[] getTimeSignatureDigits(int number) {
		return getDigits(number);
	}
	
	public char[] getTupletDigits(int nuplet) {
		return getItalicDigits(nuplet);
	}
}
