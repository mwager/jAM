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

/**
 * This class contains constants for clef
 */
public class Clef extends MusicElement implements Cloneable {
	
	private static final long serialVersionUID = 548811480906259853L;
	public static final Clef TREBLE = new Clef("G", 2, 0, 0, 5);
	public static final Clef SOPRANO = new Clef("C", 1, 0, 0, 5);
	public static final Clef MEZZOSOPRANO = new Clef("C", 2, 0, 0, 5);
	public static final Clef ALTO = new Clef("C", 3, 0, 0, 5);
	public static final Clef TENOR = new Clef("C", 4, 0, 0, 5);
	public static final Clef BARITONE = new Clef("F", 3, 0, 0, 5);
	public static final Clef BASS = new Clef("F", 4, 0, 0, 5);
	
	/** No clef */
	public static final Clef NONE = new Clef("", 2, 0, 0, 5);
	/** The standard F (bass) clef */
	public static final Clef F = BASS;
	/** The standard G (treble) clef */
	public static final Clef G = TREBLE;
	/** The standard C (alto) clef */
	public static final Clef C = ALTO;
	/** the drum clef <TT>||</TT> */
	public static final Clef PERC = new Clef("Perc", 3, 0, 0, 1);
	/** G clef one octave up */
	public static final Clef G_8va = new Clef("G", 1);
	/** G clef two octaves up */
	public static final Clef G_15va = new Clef("G", 2);
	/** G clef one octave down */
	public static final Clef G_8vb = new Clef("G", -1);
	/** G clef two octaves up */
	public static final Clef G_15vb = new Clef("G", -2);
	/** F clef one octave up */
	public static final Clef F_8va = new Clef("F", 1);
	/** F clef two octaves up */
	public static final Clef F_15va = new Clef("F", 2);
	/** F clef one octave down */
	public static final Clef F_8vb = new Clef("F", -1);
	/** F clef two octaves down */
	public static final Clef F_15vb = new Clef("F", -2);
	/** C clef one octave up */
	public static final Clef C_8va = new Clef("C", 1);
	/** C clef two octaves up */
	public static final Clef C_15va = new Clef("C", 2);
	/** C clef one octave down */
	public static final Clef C_8vb = new Clef("C", -1);
	/** C clef two octaves down */
	public static final Clef C_15vb = new Clef("C", -2);
	
	//TODO G first line, F third line, various C...

	/** Ottava 8va +1 octave */
	public static final Clef ottava_8va = new Clef(null, 1);
	/** Ottava 16ma +2 octave */
	public static final Clef ottava_15ma = new Clef(null, 2);
	/** Ottava 8vb -1 octave */
	public static final Clef ottava_8vb = new Clef(null, -1);
	/** Ottava 16mb -2 octave */
	public static final Clef ottava_15mb = new Clef(null, -2);
	
	public static Clef parseClef(String clefText, Clef previousClef) {
		if ((clefText == null) || (clefText.trim().equals("")))
			return null;
		String[] args = clefText.split(" ");
		
		String clefName = null;
		byte lineNumber = -1;
		byte octaveTransp = -1;
		byte semitonesTransp = -1;
		byte staffLines = -1;
		
		//looks for "clef=..." or "..."
		//... = treble, alto, tenor
		for (int i = 0; i < args.length; i++) {
			if (args[i].length() > 5)
				if (args[i].substring(0, 5).equals("clef="))
					args[i] = args[i].substring(5);
			String a = args[i];
			if (a.equals("treble") || a.equals("G")) {
				clefName = TREBLE.m_clefName;
				lineNumber = TREBLE.m_lineNumber;
			} else if (a.equals("bass") || a.equals("F")) {
				clefName = BASS.m_clefName;
				lineNumber = BASS.m_lineNumber;
			} else if (a.equals("bass3") || a.equals("baritone")) {
				clefName = BARITONE.m_clefName;
				lineNumber = BARITONE.m_lineNumber;
			} else if (a.equals("tenor")) {
				clefName = TENOR.m_clefName;
				lineNumber = TENOR.m_lineNumber;
			} else if (a.equals("alto") || a.equals("C")) {
				clefName = ALTO.m_clefName;
				lineNumber = ALTO.m_lineNumber;
			} else if (a.equals("mezzosoprano") || a.equals("mezzo")
					|| a.equals("alto2")) {
				clefName = MEZZOSOPRANO.m_clefName;
				lineNumber = MEZZOSOPRANO.m_lineNumber;
			} else if (a.equals("soprano") || a.equals("alto1")) {
				clefName = SOPRANO.m_clefName;
				lineNumber = SOPRANO.m_lineNumber;
			} else if (a.equals("perc") || a.equals("P")) {
				clefName = PERC.m_clefName;
				lineNumber = PERC.m_lineNumber;
			} else if (a.equals("none")) {
				clefName = NONE.m_clefName;
			} else if (a.startsWith("bass") || a.startsWith("F")) {
				//more exotic, like bass2, treble1, alto5, why not?
				clefName = BASS.m_clefName;
				lineNumber = BASS.m_lineNumber;
			} else if (a.startsWith("alto") || a.startsWith("C")) {
				clefName = ALTO.m_clefName;
				lineNumber = ALTO.m_lineNumber;
			} else if (a.startsWith("treble") || a.startsWith("G")) {
				clefName = TREBLE.m_clefName;
				lineNumber = TREBLE.m_lineNumber;
			}
			if (clefName != null) {
				String digit = a.substring(a.length()-1);
				try {
					lineNumber = Byte.parseByte(digit);
				} catch (Exception ignore) {}
				args[i] = null;
				break;//clef is found
			}
		}
		
		//+8 / -8
		for (int i = 0; i < args.length; i++) {
			if (args[i] == null)
				continue;
			if (args[i].equals("+8")) {
				octaveTransp = 1;
				args[i] = null;
				break;
			} else if (args[i].equals("-8")) {
				octaveTransp = -1;
				args[i] = null;
				break;
			}
		}
		
		//t[ranspose]=x / s[tafflines]=x / m[iddle]=B|C|A,|D,|B
		for (int i = 0; i < args.length; i++) {
			if (args[i] == null)
				continue;
			if (args[i].startsWith("s=")
					|| args[i].startsWith("stafflines=")) {
				try {
					staffLines = Byte.parseByte(args[i].split("=")[1]);
					args[i] = null;
					break;
				} catch (Exception e) {}
			} else if (args[i].startsWith("t=")
					|| args[i].startsWith("transpose=")) {
				try {
					semitonesTransp = Byte.parseByte(args[i].split("=")[1]);
					args[i] = null;
					break;
				} catch (Exception e) {}
			} else if (args[i].startsWith("m=")
					|| args[i].startsWith("middle=")) {
				String a = args[i].split("=")[1];
				//could be use with clef=bass to avoid to ","
				//for each notes. Need to see how it works
				//For now, determinate the clef if not set before
				if (clefName == null) {
					if (a.equals("B"))
						clefName = TREBLE.m_clefName;
					else if (a.equals("C")) {
						clefName = ALTO.m_clefName;
						lineNumber = ALTO.m_lineNumber;
					}
					else if (a.equals("A,")) {
						clefName = TENOR.m_clefName;
						lineNumber = TENOR.m_lineNumber;
					}
					else if (a.equals("D,"))
						clefName = BASS.m_clefName;
				}
			}
		}
		
		if (octaveTransp == -1)
			octaveTransp =(byte)(previousClef!=null?previousClef.getOctaveTransposition():0);
		if (semitonesTransp == -1)
			semitonesTransp = (byte)(previousClef!=null?previousClef.getSemitoneTransposition():0);
		if (clefName == null) {
			clefName = previousClef!=null?previousClef.getName():"G";
			lineNumber = (byte)(previousClef!=null?previousClef.getLineNumber():2);
		}
		if (lineNumber == -1) {
			if (previousClef != null)
				lineNumber = (byte)previousClef.getLineNumber();
			else if (clefName.equals(G.m_clefName)) lineNumber = G.m_lineNumber;
			else if (clefName.equals(F.m_clefName)) lineNumber = C.m_lineNumber;
			else if (clefName.equals(C.m_clefName)) lineNumber = F.m_lineNumber;
			else if (clefName.equals(PERC.m_clefName)) lineNumber = PERC.m_lineNumber;
			else {
				System.err.println("Can't determinate clef's line number, set to 2 (treble default)");
				lineNumber = G.m_lineNumber;
			}
		}
		if (staffLines == -1)
			staffLines = (byte)(previousClef!=null?previousClef.getStaffLines():5);
		
		return new Clef(clefName, lineNumber, octaveTransp,
				semitonesTransp, staffLines);
	}
	
	/** The name of the clef: G, C, F, Perc, "" or null */
	private String m_clefName = null;
	/** Number of the line staff where clef is printed.
	 * 2 for treble, 3 for alto, 4 for tenor, 4 for bass... */
	private byte m_lineNumber = -1;
	/** Transposition in octave, +1 for *va, -1 for *vb... */
	private byte m_octaveTransposition = 0;
	/** Transposition in semitones, doesn't affect
	 * printed output, only audio rendition. */
	private byte m_semitoneTransposition = 0;
	/** Number of lines in the staff (1 for perc, 5 for all others) */
	private byte m_staffLines = 5;
	
	private transient Note m_referenceNote = null;
	private transient Note m_middleNote = null;
	private transient Note m_lowNote = null;
	private transient Note m_highNote = null;
	
	/** Creates a clef with the specified parameters.
	 * @param name The name of the clef
	 * @param octaveTranspo Transposition in octave, +1 for *va, -1 for *vb...
	 */
	private Clef(String name, int octaveTranspo) {
		m_clefName = name;
		m_octaveTransposition = (byte) octaveTranspo;
	}

	/** Creates a clef with the specified parameters.
	 * @param name The name of the clef
	 * @param lineNumber Number of the line staff where clef is printed
	 * @param octaveTranspo Transposition in octave, +1 for *va, -1 for *vb...
	 * @param semitoneTranspo Transposition in semitones, doesn't affect
	 * printed output, only audio rendition.
	 * @param staffLines Number of lines in the staff (1 for perc, 5 for all others)
	 */
	private Clef(String name, int lineNumber, int octaveTranspo,
			int semitoneTranspo, int staffLines) {
		m_clefName = name;
		setLineNumber((byte)lineNumber);
		setOctaveTransposition((byte) octaveTranspo);
		m_semitoneTransposition = (byte) semitoneTranspo;
		setStaffLines((byte)staffLines);
	}
	
	/**
	 * Returns the reference note, <TT>G</TT> for G clef,
	 * <TT>G'</TT> for G + 1 octave, <TT>F,</TT> for bass clef.
	 * @return
	 */
	public Note getReferenceNote() {
		if (m_referenceNote == null) {
			if (getName() != null) {
				byte octav = 0;
				if (getName().equals(G.getName()))
					m_referenceNote = new Note(Note.G);
				else if (getName().equals(F.getName())) {
					m_referenceNote = new Note(Note.F);
					octav = -1;
				}
				else //C and Perc
					m_referenceNote = new Note(Note.C);
				m_referenceNote.setOctaveTransposition((byte)(octav + getOctaveTransposition()));
			}
		}
		return m_referenceNote;
	}
	
	/**
	 * Return the high first note which needs an extended staff line.
	 * <BR>For a 5 lines staff, it's a seventh over the middle note.
	 * For standard treble G clef, returns <TT>a</TT>.
	 */
	public Note getNoteFirstExtendedLineHigh() {
		if (m_highNote == null) {
			//A 7th (label=6) for 5 lines,
			//A 5th (label=4) for 3 lines,
			//A 3rd (label=2) for 1 line
			byte label = (byte) (getStaffLines() + 1);
			Interval interv = new Interval(label,
				label==5?Interval.PERFECT:Interval.MAJOR,
				Interval.UPWARD);
			m_highNote = interv.calculateSecondNote(getMiddleNote());
			m_highNote.setAccidental(new Accidental());//Accidental.NONE
		}
		return m_highNote;
	}

	/**
	 * Return the low first note which needs an extended staff line.
	 * <BR>For a 5 lines staff, it's a seventh under the middle note.
	 * For standard treble G clef, returns <TT>C</TT>.
	 */
	public Note getNoteFirstExtendedLineLow() {
		if (m_lowNote == null) {
			//A 7th (label=6) for 5 lines,
			//A 5th (label=4) for 3 lines,
			//A 3rd (label=2) for 1 line
			byte label = (byte) (getStaffLines() + 1);
			Interval interv = new Interval(label,
				label==5?Interval.PERFECT:Interval.MAJOR,
				Interval.DOWNWARD);
			m_lowNote = interv.calculateSecondNote(getMiddleNote());
			m_lowNote.setAccidental(new Accidental());//Accidental.NONE
		}
		return m_lowNote;
	}
	
	/**
	 * Returns the note at the middle of the staff.
	 * <BR>
	 * Generally, notes lower or equal will have stems up, and notes
	 * higher will have stems down.
	 * <BR>
	 * Returns <TT>B</TT> for treble clef, <TT>D,</TT> for bass clef...
	 */
	public Note getMiddleNote() {
		if (m_middleNote == null) {
			Note ref = getReferenceNote();
			if ((ref != null) && (getLineNumber() != -1)) {
				if (m_lineNumber == 3 || isPerc()) {
					try {
						m_middleNote = (Note) ref.clone();
					} catch (CloneNotSupportedException never) {
						never.printStackTrace();
					}
				}
				else {
					Interval interval = null;
					switch(m_lineNumber) {
					case 1: interval = Interval.PERFECT_FIFTH; break;
					case 2: interval = Interval.MAJOR_THIRD; break;
					//case 3: already handled
					case 4: interval = Interval.reverseOrder(Interval.MAJOR_THIRD); break;
					case 5: interval = Interval.reverseOrder(Interval.PERFECT_FIFTH); break;
					}
					m_middleNote = interval.calculateSecondNote(ref);
					m_middleNote.setAccidental(new Accidental());//Accidental.NONE);
				}
			}
		}
		return m_middleNote;
	}

	public boolean equals(Clef otherClef) {
		return ((m_clefName!=null && m_clefName.equals(otherClef.getName()))
			|| ((m_clefName==null) && (otherClef.m_clefName == null)))
			&& (m_octaveTransposition == otherClef.m_octaveTransposition)
			&& (m_semitoneTransposition == otherClef.m_semitoneTransposition)
			&& (getLineNumber() == otherClef.getLineNumber())
			&& (m_staffLines == otherClef.m_staffLines);
	}

	/** Returns the name of the clef: G, C, F, Perc, "" or null */
	public String getName() {
		return m_clefName;
	}
	
	/** Returns the transposition in octave, +1 for *va, -1 for *vb... */
	public int getOctaveTransposition() {
		return m_octaveTransposition;
	}

	/** Returns the transposition in octave, +1 for *va, -1 for *vb... */
	public void setOctaveTransposition(byte i) {
		if (i < -2) i = -2;
		else if (i > 2) i = 2;
		m_octaveTransposition = i;
		reset();
	}
	
	/** Returns the transposition in semitones, doesn't affect
	 * printed output, only audio rendition. */
	public int getSemitoneTransposition() {
		return m_semitoneTransposition;
	}
	
	/** Sets the transposition in semitones, doesn't affect
	 * printed output, only audio rendition. */
	public void setSemitoneTransposition(byte i) {
		m_semitoneTransposition = i;
		reset();
	}
	
	/** Returns the number of the line staff where clef is printed.
	 * 2 for treble, 3 for alto, 4 for tenor, 4 for bass... */
	public int getLineNumber() {
		if ((m_lineNumber == -1) && (getName() != null)) {
			if (getName().equals(G.getName()))
				m_lineNumber = 2;
			else if (getName().equals(F.getName()))
				m_lineNumber = 4;
			else if (getName().equals(C.getName()))
				m_lineNumber = 3;
		}
		return m_lineNumber;
	}
	
	/** Sets the number of the line staff where clef is printed.
	 * 2 for treble, 3 for alto, 4 for tenor, 4 for bass... */
	public void setLineNumber(byte i) {
		if (i <= 0) i = 1;
		else if (i > 5) i = 5;
		m_lineNumber = i;
		reset();
	}
	
	/** Returns the number of lines in the staff
	 * (1 for perc, 5 for all others) */
	public int getStaffLines() {
		return m_staffLines;
	}

	/** Returns the number of lines in the staff
	 * (1 for perc, 5 for all others) */
	public void setStaffLines(byte i) {
		if (i < 0) i = 0;
		else if (i > 5) i = 5;
		m_staffLines = i;
		reset();
	}
	
	private void reset() {
		m_referenceNote = null;
		m_middleNote = null;
		m_lowNote = null;
		m_highNote = null;
	}
	
	public boolean isG() {
		return G.getName().equals(getName());
	}
	public boolean isF() {
		return F.getName().equals(getName());
	}
	public boolean isC() {
		return C.getName().equals(getName());
	}
	public boolean isPerc() {
		return PERC.getName().equals(getName());
	}

	public String toString() {
		return getName();
	}
	
	public Object clone() throws CloneNotSupportedException {
		Object o = super.clone();
		if (m_referenceNote != null)
		((Clef) o).m_referenceNote = (Note) m_referenceNote.clone();
		if (m_middleNote != null)
		((Clef) o).m_middleNote = (Note) m_referenceNote.clone();
		if (m_lowNote != null)
		((Clef) o).m_lowNote = (Note) m_referenceNote.clone();
		if (m_highNote != null)
		((Clef) o).m_highNote = (Note) m_referenceNote.clone();
		return o;
	}
	
}
