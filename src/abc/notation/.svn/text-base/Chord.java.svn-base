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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Chord class handle chord names and probably later some useful
 * functions for chord.
 * <B>Do not confuse</B> with {@link abc.notation.MultiNote} which is
 * a collection of {@link abc.notation.Note}s played at the same time,
 * what we call a "chord" in music!
 * <BR>
 * Chord names are texts added to a {@link abc.notation.NoteAbstract}
 * object, which are represented on the top of the staff.
 * <BR>
 * In ABC, chord names are between quotes
 * e.g.: "C"EEE2 EEE2 | "G"EGC>DE4 | "F"FFF>F "C"EEE>E... (Jingle bells)
 * <BR>
 * Complex chords can be entered, and sometimes it's not a chord!
 * So before gettings chord properties, check {@link #isChord()}.
 */
public class Chord extends MusicPresentationElement implements Cloneable {
	
	//TODO getFrettedFingerings(EADGBE, DADGAD...)
	//TODO getNotes - generate a MultiNote
	
	private static final long serialVersionUID = 7349845779154689148L;
	/** The unicode sharp char \u266F (&#9839;) */
	public static final char UNICODE_SHARP = '\u266F';
	/** The unicode flat char \u266D (&#9837;) */
	public static final char UNICODE_FLAT = '\u266D';
	/** The unicode natural char \u266E (&#9838;) */
	public static final char UNICODE_NATURAL = '\u266E';
	
	//for " (EbMaj7/Bb) "	| for "(/Bb)" :
	//groupe 0 :(EbMaj7/Bb)	| groupe 0 :(/Bb)
	//groupe 1 :(			| groupe 1 :(/
	//groupe 2 :Eb			| groupe 2 :Bb
	//groupe 3 :E			| groupe 3 :B
	//groupe 4 :b			| groupe 4 :b
	//groupe 5 :Maj7		| groupe 5 :
	//groupe 6 :/Bb			| groupe 6 :null
	//groupe 7 :/			| groupe 7 :null
	//groupe 8 :Bb			| groupe 8 :null
	//groupe 9 :B			| groupe 9 :null
	//groupe 10 :b			| groupe 10 :null
	//groupe 11 :)			| groupe 11 :)
	private static final String REGEXP =
		"^([\\s ]*[\\(\\/]{0,2})" //spaces, opening parenthesis, /
		+"(([ABCDEFG])([b\u266D#\u266F])?)" //note name + accidental
		//\u266D = flat, \u266E = natural, \u266F = sharp
		+"([mM1234567890abdijnsuøØo°\u00D8\u00F8\u00B0\u0394\u2206\\-\\+]*)"
			//handles min(or), Maj/maj(or), dim, sus, Maj7, mb5...
			// but not #11 (may be ok for Eb7#11,
			// but F#11 will disturb...)
			//\u00F8 = ø, \u00D8 = Ø, \u00B0 = °
			//delta = Maj7, maths=\u2206, greek=\u0394
		+"((\\/)(([ABCDEFG])([b\u266D#\u266F])?))?" // /bass
		+"(\\)?[ \\s]*)$" //closing parenthesis, spaces
	;
	private static Pattern pattern = Pattern.compile(REGEXP);

	public static boolean isChord(String text) {
		if (text != null)
			text = text.trim();
		if ((text == null) || (text.length() == 0))
			return false;
//		Matcher m = pattern.matcher(text);
//		if (m.matches()) {
//		for (int i = 0; i <= m.groupCount(); i++) {
//			System.out.println("groupe "+i+" :"+m.group(i));
//		}
//		}
		return pattern.matcher(text).matches();
	}
	
	private String m_chordName = "";
	private boolean m_isChord = false;
	private Note m_note = null;
	private Note m_bass = null;
	private String m_quality = "";
	private boolean m_isOptional = false;
	
	public Chord(String chordName) {
		m_chordName = chordName;
		m_isChord = isChord(m_chordName);
		if (m_isChord) {
			try {
				Matcher m = pattern.matcher(m_chordName.trim());
				m.matches();
				String[] group = new String[m.groupCount() + 1];
				for (int i = 0; i <= m.groupCount(); i++) {
					group[i] = m.group(i);
					//System.out.println("\tgroup["+i+"] = "+group[i]);
				}
				boolean hasNote = (!group[1].contains("/")) && (group[3].length() > 0);
				boolean hasBass = ((group[1].contains("/") && (group[3].length() > 0))
								|| ((group[7] != null) && group[7].contains("/")
										&& (group[9] != null) && (group[9].length() > 0)));
				if (hasNote || hasBass) {
					byte height = Note.convertToNoteType(group[3]);
					Accidental accidental = 
						Accidental.convertToAccidental(group[4]);
					if (hasNote)
						m_note = new Note(height, accidental);
					else //this is a bass without note
						m_bass = new Note(height, accidental);
					if (hasNote && hasBass) {
						height = Note.convertToNoteType(group[9]);
						accidental = 
							Accidental.convertToAccidental(group[10]);
						m_bass = new Note(height, accidental);
					}
					m_isOptional = (group[1].contains("(")
							&& group[11].contains(")"));//==
					if (hasNote)
						m_quality = group[5];
				}
			} catch (Exception e) {
				System.err.println("Error analysing chord "+m_chordName+" : "+e.getMessage());
			}
		}
	}
	
	/**
	 * Returns the note of the chord, <TT>null</TT> if no
	 * note (e.g. only one bass in the sequence <TT>E7, /G#</TT>)
	 */
	public Note getNote() {
		return m_note;
	}
	
	/**
	 * Sets the note of the chord, <TT>null</TT> accepted.
	 * @param note
	 */
	public void setNote(Note note) {
		m_note = note;
	}
	
	public boolean hasNote() {
		return getNote() != null;
	}
	
	/**
	 * If any, returns the bass (Note object) of the chord
	 */
	public Note getBass() {
		return m_bass;
	}
	
	/**
	 * Sets the bass of the chord, <TT>null</TT> accepted
	 * @param bass
	 */
	public void setBass(Note bass) {
		m_bass = bass;
	}
	
	public boolean hasBass() {
		return getBass() != null;
	}
	
	/** Returns the quality of chord (min, Maj7...) */
	public String getQuality() {
		return m_quality;
	}
	
	public String getText() {
		return getText(false);
	}
	
	/** Returns the text of the chord.
	 * 
	 * Returns the original text if it's not a real chord
	 * @param unicode If any, accidentals are not # and b, but
	 * \u266F (&#9839;) = sharp and \u266D (&#9837;) = flat
	 */
	public String getText(boolean unicode) {
		if (!isChord())
			return m_chordName;
		else {
			StringBuffer ret = new StringBuffer();
			if (isOptional())
				ret.append("(");
			ret.append(note2string(getNote(), unicode));
			ret.append(getQuality());
			if (hasBass()) {
				ret.append("/");
				ret.append(note2string(getBass(), unicode));
			}
			if (isOptional())
				ret.append(")");
			return ret.toString();
		}
	}
	
	private String note2string(Note note, boolean unicode) {
		if (note == null)
			return "";
		else {
			String ret = "";
			switch(note.getStrictHeight()) {
			case Note.A: ret = "A"; break;
			case Note.B: ret = "B"; break;
			case Note.C: ret = "C"; break;
			case Note.D: ret = "D"; break;
			case Note.E: ret = "E"; break;
			case Note.F: ret = "F"; break;
			case Note.G: ret = "G"; break;
			}
			if (note.getAccidental().isFlat())
				ret += unicode?"\u266D":"b";
			else if (note.getAccidental().isSharp())
				ret += unicode?"\u266F":"#";
			return ret;
		}
	}
	
	/**
	 * Returns <TT>true</TT> if the chord text is understood has
	 * a real chord, <TT>false</TT> elsewhere.
	 */
	public boolean isChord() {
		return m_isChord && (hasNote() || hasBass());
	}
	
	/** A chord is optional if it's between parenthesis */
	public boolean isOptional() {
		return m_isOptional;
	}
	
	public boolean equals(Object o) {
		if (o instanceof Chord)
			return ((Chord)o).getText() == getText();
		else
			return super.equals(o);
	}
	
	public String toString() {
		return super.toString() + ": " + getText();
	}
	
	public Object clone() throws CloneNotSupportedException {
		Object o = super.clone();
		if (m_bass != null)
		((Chord) o).m_bass = (Note) m_bass.clone();
		if (m_note != null)
		((Chord) o).m_note = (Note) m_note.clone();
		return o;
	}
}