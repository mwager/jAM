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

/** This class defines key signatures using modes definition like E major, G minor etc etc...
 * <PRE>
 *                           1   2   3   4   5   6   7
 * Major (Ionian, mode 1)    D   E   F#  G   A   B   C#
 * Dorian (mode 2)               E   F#  G   A   B   C#   D
 * Mixolydian (mode 5)                       A   B   C#   D   E   F#  G
 * Aeolian (mode 6)                              B   C#   D   E   F#  G   A
 * </PRE>
 * If we consider the key namned "Ab aeolian", "A" is the note of this
 * key, "b" is the key accidental and "aeolian" is the mode. */
public class KeySignature extends MusicElement implements Cloneable
{
	private static final long serialVersionUID = -8079964017180751158L;

	private static final Accidental[][] accidentalsRules = {
//	Flyd C Cmaj Cion Gmix Ddor Amin Am Aeol  Ephr Bloc
//						C    					D    					E    					F    					G    					A    					B
/*C*/ {Accidental.NATURAL, 	Accidental.NATURAL, Accidental.NATURAL, Accidental.NATURAL, Accidental.NATURAL, Accidental.NATURAL, Accidental.NATURAL },	// C
/*Db*/{Accidental.NATURAL, 	Accidental.FLAT,	Accidental.FLAT,	Accidental.NATURAL,	Accidental.FLAT,	Accidental.FLAT,	Accidental.FLAT},
/*D*/ {Accidental.SHARP,	Accidental.NATURAL, Accidental.NATURAL, Accidental.SHARP,	Accidental.NATURAL, Accidental.NATURAL, Accidental.NATURAL },	// D
/*Eb*/{Accidental.NATURAL, 	Accidental.NATURAL, Accidental.FLAT, 	Accidental.NATURAL, Accidental.NATURAL, Accidental.FLAT, 	Accidental.FLAT },
/*E*/ {Accidental.SHARP,	Accidental.SHARP,	Accidental.NATURAL, Accidental.SHARP,	Accidental.SHARP,	Accidental.NATURAL, Accidental.NATURAL },	// E
/*F*/ {Accidental.NATURAL, 	Accidental.NATURAL, Accidental.NATURAL, Accidental.NATURAL, Accidental.NATURAL, Accidental.NATURAL, Accidental.FLAT },		// F
      {},	// This one would contain E#(=F) Not really OK 
/*G*/ {Accidental.NATURAL, 	Accidental.NATURAL, Accidental.NATURAL, Accidental.SHARP,	Accidental.NATURAL, Accidental.NATURAL, Accidental.NATURAL },	// G
/*Ab*/{Accidental.NATURAL, 	Accidental.FLAT, 	Accidental.FLAT, 	Accidental.NATURAL, Accidental.NATURAL, Accidental.FLAT, 	Accidental.FLAT },
/*A*/ {Accidental.SHARP,	Accidental.NATURAL, Accidental.NATURAL, Accidental.SHARP,	Accidental.SHARP,	Accidental.NATURAL, Accidental.NATURAL },	// A
/*Bb*/{Accidental.NATURAL, 	Accidental.NATURAL, Accidental.FLAT, 	Accidental.NATURAL, Accidental.NATURAL, Accidental.NATURAL, Accidental.FLAT },
/*B*/ {Accidental.SHARP,	Accidental.SHARP,	Accidental.NATURAL, Accidental.SHARP,	Accidental.SHARP,	Accidental.SHARP,	Accidental.NATURAL }	// B
    };
    
    private static final Accidental[][] accidentalRulesFlat = {
/*C*/ {},
/* */ {},
/*D*/ {},
/* */ {},
/*E*/ {},
/*F*/ {},
/*Gb*/{Accidental.FLAT, 		Accidental.FLAT, 	Accidental.FLAT, 	Accidental.NATURAL, Accidental.FLAT, 	Accidental.FLAT, 	Accidental.FLAT },
/*G*/ {},
/* */ {},
/*A*/ {},
/* */ {},
/*Cb*/{Accidental.FLAT, 		Accidental.FLAT, 	Accidental.FLAT, 	Accidental.FLAT, 	Accidental.FLAT, 	Accidental.FLAT, 	Accidental.FLAT }
    };
    
    private static final Accidental[][] accidentalRulesSharp = {
/*C*/ {},
/*C#*/{Accidental.SHARP,		Accidental.SHARP,	Accidental.SHARP, 	Accidental.SHARP,	Accidental.SHARP,	Accidental.SHARP,	Accidental.SHARP },
/*D*/ {},
/* */ {},
/*E*/ {},
/*F*/ {},
/*F#*/{Accidental.SHARP,		Accidental.SHARP,	Accidental.SHARP, 	Accidental.SHARP,	Accidental.SHARP,	Accidental.SHARP,	Accidental.NATURAL },
/*G*/ {},
/* */ {},
/*A*/ {},
/* */ {},
/*B*/ {}
    };

    /** The aeolian mode type. */
    public static final byte AEOLIAN	= 0;
    /** The dorian mode type. */
    public static final byte DORIAN	= 1;
    /** The ionian mode type. */
    public static final byte IONIAN	= 2;
    /** The locrian mode type. */
    public static final byte LOCRIAN	= 3;
    /** The lydian mode type. */
    public static final byte LYDIAN	= 4;
    /** The major mode type. */
    public static final byte MAJOR	= 5;
    /** The minor mode type. */
    public static final byte MINOR	= 6;
    /** The mixolydian mode type. */
    public static final byte MIXOLYDIAN= 7;
    /** The phrygian mode type. */
    public static final byte PHRYGIAN	= 8;
    /** The "not standard" mode type. */
    public static final byte OTHER	= -1;

    /** Highland Bagpipe notation is also catered for :
     * K:HP
     * puts no key signature on the music but implies the bagpipe scale, while
     * K:Hp
     * puts F sharp, C sharp and G natural. */
    private byte m_keyNote = Note.C;
    private Accidental m_keyAccidental = Accidental.NATURAL;
    private byte mode = OTHER;
    private Accidental[] accidentals = accidentalsRules[0];
    private byte keyIndex = 0;
    private Clef m_clef = Clef.TREBLE;

    /** Creates a new signature with the specified parameters.
     * @param keyNoteType The note of the mode. Possible values are
     * <TT>Note.A</TT>, <TT>Note.B</TT>, <TT>Note.C</TT>, <TT>Note.D</TT>,
     * <TT>Note.E</TT>, <TT>Note.F</TT> or <TT>Note.G</TT>.
     * @param modeType The type of the mode. Possible values are
     * <TT>AEOLIAN</TT>, <TT>DORIAN</TT>, <TT>IONIAN</TT>, <TT>LOCRIAN</TT>, <TT>LYDIAN</TT>
     * <TT>MAJOR</TT>, <TT>MINOR</TT>, <TT>MIXOLYDIAN</TT>, <TT>PHRYGIAN</TT> or <TT>OTHER</TT>. */
    public KeySignature (byte keyNoteType, byte modeType)
    {
      this(keyNoteType, new Accidental(), modeType);
    }
    
    /** @deprecated use {@link #KeySignature(byte, Accidental, byte)} */
	public KeySignature(byte keyNoteType, float keyAccidentalValue, byte modeType) {
		this(keyNoteType, new Accidental(keyAccidentalValue), modeType);
	}

    /**
	 * Creates a new signature with the specified parameters.
	 * 
	 * @param keyNoteType
	 *            The note of the mode. Possible values are <TT>Note.A</TT>,
	 *            <TT>Note.B</TT>, <TT>Note.C</TT>, <TT>Note.D</TT>,
	 *            <TT>Note.E</TT>, <TT>Note.F</TT> or <TT>Note.G</TT>.
	 * @param keyAccidental
	 *            Accidental for the note mode. Possible values are <TT>Accidental.SHARP</TT>,
	 *            <TT>Accidental.NATURAL</TT>? <TT>Accidental.NONE</TT>,
	 *            or <TT>Accidental.FLAT</TT>.
	 * @param modeType
	 *            The type of the mode. Possible values are <TT>AEOLIAN</TT>,
	 *            <TT>DORIAN</TT>, <TT>IONIAN</TT>, <TT>LOCRIAN</TT>,
	 *            <TT>LYDIAN</TT>, <TT>MAJOR</TT>, <TT>MINOR</TT>, <TT>MIXOLYDIAN</TT>,
	 *            <TT>PHRYGIAN</TT> or <TT>OTHER</TT>.
	 * @exception IllegalArgumentException
	 *                Thrown if keyAccidental or modeType are out of the allowed
	 *                values.
	 */
	public KeySignature(byte keyNoteType, Accidental keyAccidental, byte modeType) {
		if (keyAccidental.isMicrotonal())
			throw new IllegalArgumentException(
					"Key accidental can't be microtonal");
		if (!(modeType == AEOLIAN || modeType == DORIAN || modeType == IONIAN
				|| modeType == LOCRIAN || modeType == LYDIAN
				|| modeType == MAJOR || modeType == MINOR
				|| modeType == MIXOLYDIAN || modeType == PHRYGIAN || modeType == OTHER))
			throw new IllegalArgumentException(
					"Mode type must be choose among AEOLIAN, DORIAN, IONIAN, LOCRIAN, LYDIAN, MAJOR, MINOR, MIXOLYDIAN, PHRYGIAN or OTHER");
		m_keyNote = keyNoteType;
		mode = modeType;
		m_keyAccidental = keyAccidental;
		accidentals = accidentalsRules[0];
		keyIndex = 0;
		if (m_keyNote == Note.D) keyIndex = 2;
		else if (m_keyNote == Note.E) keyIndex = 4;
		else if (m_keyNote == Note.F) keyIndex = 5;
		else if (m_keyNote == Note.G) keyIndex = 7;
		else if (m_keyNote == Note.A) keyIndex = 9;
		else if (m_keyNote == Note.B) keyIndex = 11;
		if (m_keyAccidental.isSharp()) keyIndex += 1;
		else if (m_keyAccidental.isFlat()) keyIndex -= 1;
		if (modeType == MINOR) keyIndex += 3;
		else if (modeType == LYDIAN) keyIndex += 7;
		else if (modeType == MIXOLYDIAN) keyIndex += 5;
		else if (modeType == DORIAN) keyIndex += 10;
		else if (modeType == AEOLIAN) keyIndex += 3;
		else if (modeType == PHRYGIAN) keyIndex += 8;
		else if (modeType == LOCRIAN) keyIndex += 1;
		//mode = other (or EXP = Explicit), all natural
		else if (modeType == OTHER) keyIndex = 0;//all natural
		// this + 12 % 12 is a workaound to express key signature
		// that are expressed with notes such as Cb (=B) or E# (=F)
		// Before this fix, keys such as Cb was causing crash (because
		// keyIndex was equals to -1 in that case.
		keyIndex = (byte)((keyIndex + 12) % 12);
		if (keyIndex == 6 && keyAccidental.isFlat()) // Gb (The C is flat)
			setAccidentals(accidentalRulesFlat[keyIndex]);
		else if (keyIndex == 6 && keyAccidental.isSharp()) // F# (The E is sharp)
			setAccidentals(accidentalRulesSharp[keyIndex]);
		else if (keyIndex == 6 && keyAccidental.isNatural())
			throw new RuntimeException("Cannot map " + keyNoteType + "/"
					+ keyAccidental + "/" + modeType + " to a key signature");
		else if (keyIndex == 11 && keyAccidental.isFlat()) // for Cb
			setAccidentals(accidentalRulesFlat[keyIndex]);
		else if (keyIndex == 1 && keyAccidental.isSharp()) // C#
			setAccidentals(accidentalRulesSharp[keyIndex]);
		else
			setAccidentals(accidentalsRules[keyIndex]); // apply normal rule.
		
		//D# is Eb, so for really clean object, set key to E and accidental to flat
		if (m_keyAccidental.isSharp() && hasOnlyFlats()) {
			Note enh = Note.createEnharmonic(new Note(m_keyNote,
					m_keyAccidental),
					new Accidental[] {Accidental.FLAT});
			m_keyNote = enh.getStrictHeight();
			m_keyAccidental = enh.getAccidental();
		}
		else if (m_keyAccidental.isFlat() && hasOnlySharps()) {
			Note enh = Note.createEnharmonic(new Note(m_keyNote,
					m_keyAccidental),
					new Accidental[] {Accidental.SHARP});
			m_keyNote = enh.getStrictHeight();
			m_keyAccidental = enh.getAccidental();
		}
	}

    /**
	 * Creates a key signature with the specified accidentals.
	 * 
	 * @param accidentalsDefinition
	 *            Accidental definition from note C to B. Possible values for
	 *            accidentals are : <TT>Accidental.SHARP</TT>, <TT>Accidental.NATURAL</TT>
	 *            or <TT>Accidental.FLAT</TT>.
	 */
    public KeySignature (Accidental[] accidentalsDefinition) {
    	setAccidentals(accidentalsDefinition);
    }
    
    /** Returns the note of the mode.
     * @return The note of the mode. Possible values are
     * <TT>Note.A</TT>, <TT>Note.B</TT>, <TT>Note.C</TT>, <TT>Note.D</TT>,
     * <TT>Note.E</TT>, <TT>Note.F</TT> or <TT>Note.G</TT>. */
    public byte getNote()
    { return m_keyNote; }
    
    /**
	 * Returns the note of the degree of the mode.
	 * 
	 * @param degree
	 *            between 1 and 7, included. getDegree(1) is equivalent to
	 *            {@link #getNote()}.
	 * @return Possible values are <TT>Note.A</TT>, <TT>Note.B</TT>, <TT>Note.C</TT>,
	 *         <TT>Note.D</TT>, <TT>Note.E</TT>, <TT>Note.F</TT> or <TT>Note.G</TT>.
	 * @throws IllegalArgumentException
	 *             if degree is <1 or >7
	 */
	public byte getDegree(int degree) throws IllegalArgumentException {
		if (degree < 1 || degree > 7)
			throw new IllegalArgumentException(
					"Degree must be between 1 and 7 (included)");
		if (degree == 1)
			return getNote();
		else {
			byte[] notes = new byte[] { Note.C, Note.D, Note.E, Note.F, Note.G,
					Note.A, Note.B };
			int index = 0;
			for (int i = 0; i < notes.length; i++) {
				if (notes[i] == m_keyNote) {
					index = i;
					break;
				}
			}
			return notes[(index + degree - 1) % notes.length];
		}
	}

    /**
	 * Returns the mode of this key.
	 * 
	 * @return The mode of this key. Possible values are <TT>AEOLIAN</TT>,
	 *         <TT>DORIAN</TT>, <TT>IONIAN</TT>, <TT>LOCRIAN</TT>, <TT>LYDIAN</TT>,
	 *         <TT>MAJOR</TT>, <TT>MINOR</TT>, <TT>MIXOLYDIAN</TT>,
	 *         <TT>PHRYGIAN</TT> or <TT>OTHER</TT>.
	 */
    public byte getMode()
    { return mode; }

    /** Returns key accidental for this Key.
     * @return This key's key accidental. Ex: for "Ab aeolian", the key
     * accidental is "b" (flat) */
    public Accidental getAccidental ()
    { return m_keyAccidental; }

    /** Returns accidentals values of this key signature.
     * @return accidentals of this key signature. Index 0 correspond to 
     * accidental for C, 1 to accidental for D and so on up to B.*/
    public Accidental[] getAccidentals ()
    { return accidentals; }

    /**
     * Set the accidentals, ensure that the byte array is copied
     * <TT>accidentals = accidentalRules...[...]</TT> keeps the reference
     * to accidentalRules... so if you set an accidental with {@link #setAccidental(byte, byte)}
     * the static accidentalRule...[] is altered.
     * @param accidentalsDefinition
     */
    private void setAccidentals(Accidental[] accidentalsDefinition) {
    	Accidental[] newArray = new Accidental[7];
        System.arraycopy(accidentalsDefinition, 0, newArray, 0, 7);
        accidentals = newArray;
    }

    /** Sets the accidental for the specified note.
     * @param noteHeigth The note heigth. Possible values are,
     * <TT>Note.A</TT>, <TT>Note.B</TT>, <TT>Note.C</TT>, <TT>Note.D</TT>,
     * <TT>Note.E</TT>, <TT>Note.F</TT> or <TT>Note.G</TT>.
     * @param accidental The accidental to be set to the note. Possible values are :
     * <TT>Accidental.SHARP</TT>, <TT>Accidental.NATURAL</TT>
     * or <TT>Accidental.FLAT</TT>.
     * @exception IllegalArgumentException Thrown if an invalid note heigth or
     * accidental has been given. */
    public void setAccidental(byte noteHeigth, Accidental accidental) throws IllegalArgumentException
    {
      int index = 0;
      noteHeigth = (byte)(noteHeigth%12);
      if (noteHeigth==Note.C) index=0;
      else if (noteHeigth==Note.D) index=1;
      else if (noteHeigth==Note.E) index=2;
      else if (noteHeigth==Note.F) index=3;
      else if (noteHeigth==Note.G) index=4;
      else if (noteHeigth==Note.A) index=5;
      else if (noteHeigth==Note.B) index=6;
      else
        throw new IllegalArgumentException("Invalid note heigth : " + noteHeigth);

      if (accidental.isNotDefined())
    	  accidentals[index] = Accidental.NATURAL;
      //accept it, because in midi, the key is changed when there
      //is an accidental in a bar, using this method
      //else if (accidental.isDoubleFlat() || accidental.isDoubleSharp())
      //    throw new IllegalArgumentException("Accidental can't be DOUBLE_SHARP or DOUBLE_FLAT");
      else
    	  accidentals[index] = accidental;
    }

    /** Returns accidental for the specified note heigth for this key.
     * @param noteHeigth A note heigth among <TT>Note.C</TT>, <TT>Note.D</TT>,
     * <TT>Note.E</TT>, <TT>Note.F</TT>, <TT>Note.G</TT>, <TT>Note.A</TT>,
     * <TT>Note.B</TT>.
     * @return Accidental value for the specified note heigth. This value can be
     * <TT>NATURAL</TT>, <TT>FLAT</TT> or <TT>SHARP</TT>.
     * @exception IllegalArgumentException Thrown if the specified note heigth
     * is invalid. */
    public Accidental getAccidentalFor (byte noteHeigth)
    {
      int index = 0;
      if (noteHeigth==Note.C) index=0;
      else if (noteHeigth==Note.D) index=1;
      else if (noteHeigth==Note.E) index=2;
      else if (noteHeigth==Note.F) index=3;
      else if (noteHeigth==Note.G) index=4;
      else if (noteHeigth==Note.A) index=5;
      else if (noteHeigth==Note.B) index=6;
        else throw new IllegalArgumentException("Invalid note heigth : " + noteHeigth);
      return accidentals[index];
    }
    
    /**
     * Returns the clef associated to this key signature
     */
    public Clef getClef() {
    	if (m_clef == null)
    		m_clef = Clef.G;
    	return m_clef;
    }
    
    /**
     * Sets the clef associated to this key signature
     * @param clef
     */
    public void setClef(Clef clef) {
    	m_clef = clef;
    }
    
	/**
	 * Returns <TT>true</TT> if the key has only flats or naturals.
	 * Takes into account the additionnals accidentals.
	 * 
	 * e.g.:
	 * <ul>
	 * <li>Am _d (sabah mode) has one flat (Db), returns true
	 * <li>Dm ^c has flat (Bb) and sharp (C#), returns false
	 * </ul>
	 * 
	 * @see #isFlatDominant()
	 * @see #hasSharpsAndFlats()
	 */
	public boolean hasOnlyFlats() {
		return isFlatDominant() && !hasSharpsAndFlats();
	}
	
	/**
	 * Returns <TT>true</TT> if the key has only sharps or naturals.
	 * Takes into account the additionnals accidentals.
	 * 
	 * e.g.:
	 * <ul>
	 * <li>D ^g has only sharps (F# G# C#), returns true
	 * <li>Dm ^c has flat (Bb) and sharp (C#), returns false
	 * </ul>
	 * 
	 * @see #isSharpDominant()
	 * @see #hasSharpsAndFlats()
	 */
	public boolean hasOnlySharps() {
		return isSharpDominant() && !hasSharpsAndFlats();
	}
	
	/**
	 * Returns <TT>true</TT> if the key contains sharps <B>and</B> flats
	 * (e.g.: in ABC format oriental scale <I>nawa atar</i> <TT>K:Cm ^f =b</TT>
	 * which scale is C D Eb F# G Ab B).
	 * 
	 * Returns <TT>false</TT> otherwise.
	 */
	public boolean hasSharpsAndFlats() {
		boolean hasSharp = false, hasFlat = false;
		for (int i = 0; i < accidentals.length; i++) {
			// handle microtonal (half flat and half sharp)
			// theorically, double sharp and double flat
			// are never at key, but half sharp and half flat can
			hasSharp = hasSharp || (accidentals[i].getValue() > 0);
			hasFlat = hasFlat || (accidentals[i].getValue() < 0);
		}
		return hasSharp && hasFlat;
	}
	
	/**
	 * Returns <TT>true</TT> if the key without exotic accidentals has only
	 * sharps.
	 * 
	 * e.g.:
	 * <ul>
	 * <li>E returns true
	 * <li>E =f returns true
	 * <li>G _a returns alse true (G major has F#)
	 * <li>F returns false (F major has Bb)
	 * </ul>
	 * 
	 * @see #hasOnlySharps()
	 * @see #hasSharpsAndFlats()
	 */
	public boolean isSharpDominant() {
		return ((keyIndex == 1 && m_keyAccidental.isSharp())
				|| keyIndex == 2 || keyIndex == 4
				|| (keyIndex == 6 && m_keyAccidental.isSharp())
				|| keyIndex == 7 || keyIndex == 9 || (keyIndex == 11 && m_keyAccidental.isNatural()));
	}
	
	/**
	 * Returns <TT>true</TT> if the key without exotic accidentals has only
	 * flats.
	 * 
	 * e.g.:
	 * <ul>
	 * <li>Dm returns true
	 * <li>Dm ^c returns also true
	 * <li>D returns false
	 * </ul>
	 * 
	 * @see #hasOnlyFlats()
	 * @see #hasSharpsAndFlats()
	 */
	public boolean isFlatDominant() {
		return ((keyIndex == 1 && m_keyAccidental.isFlat())
				|| keyIndex == 3 || keyIndex == 5
				|| (keyIndex == 6 && m_keyAccidental.isFlat())
				|| keyIndex == 8 || keyIndex == 10 || (keyIndex == 11 && m_keyAccidental.isFlat()));
	}
	
    public String toLitteralNotation()
    {
      String notation = "";
      if (m_keyNote==Note.A) notation = notation.concat("A");
      else if (m_keyNote==Note.B) notation = notation.concat("B");
      else if (m_keyNote==Note.C) notation = notation.concat("C");
      else if (m_keyNote==Note.D) notation = notation.concat("D");
      else if (m_keyNote==Note.E) notation = notation.concat("E");
      else if (m_keyNote==Note.F) notation = notation.concat("F");
      else if (m_keyNote==Note.G) notation = notation.concat("G");
      if (m_keyAccidental.isFlat()) notation = notation.concat("b");
      else if (m_keyAccidental.isSharp()) notation = notation.concat("#");
      if (mode==AEOLIAN) notation = notation.concat("aeo");
      else if (mode==DORIAN) notation = notation.concat("dor");
      else if (mode==IONIAN) notation = notation.concat("ion");
      else if (mode==LOCRIAN) notation = notation.concat("loc");
      else if (mode==LYDIAN) notation = notation.concat("lyd");
      else if (mode==MAJOR) notation = notation.concat("maj");
      else if (mode==MINOR) notation = notation.concat("min");
      else if (mode==MIXOLYDIAN) notation = notation.concat("mix");
      else if (mode==PHRYGIAN) notation = notation.concat("phr");
      return notation;
    }

    public static byte convertToModeType(String mode)
    {
    	mode = mode.trim().toUpperCase();
      if ("AEO".equals(mode)) return KeySignature.AEOLIAN;
      else if ("DOR".equals(mode)) return KeySignature.DORIAN;
      else if ("ION".equals(mode)) return KeySignature.IONIAN;
      else if ("LOC".equals(mode)) return KeySignature.LOCRIAN;
      else if ("LYD".equals(mode)) return KeySignature.LYDIAN;
      else if ("MAJ".equals(mode) || ("".equals(mode))) return KeySignature.MAJOR;
      else if ("MIN".equals(mode) || ("M".equals(mode))) return KeySignature.MINOR;
      else if ("MIX".equals(mode)) return KeySignature.MIXOLYDIAN;
      else if ("PHR".equals(mode)) return KeySignature.PHRYGIAN;
      //"EXP" = explicit -> return other
      else return KeySignature.OTHER;
    }

  public static Accidental convertToAccidental(String accidental) throws IllegalArgumentException
  {
    if (accidental==null) return Accidental.NATURAL;
    else if (accidental.equals("#")) return Accidental.SHARP;
    else if (accidental.equals("b")) return Accidental.FLAT;
    else throw new IllegalArgumentException(accidental + " is not a valid accidental");
  }

  /** Returns a String representation of this key.
   * @return A String representation of this key. */
  public String toString()
  {
    String string2Return = "KeySignature: "
    	+toLitteralNotation()+" (";
    for (int i= 0; i<7; i++)
    {
    	string2Return += accidentals[i].toString();
      if(i!=6)
        string2Return = string2Return.concat(", ");
    }
    string2Return = string2Return.concat("}");
    return string2Return;
  }
  

	public boolean equals(Object o) {
		if (o instanceof KeySignature){
			KeySignature oKey = (KeySignature) o;
			if ((this.m_keyNote != oKey.m_keyNote)
					|| (this.keyIndex != oKey.keyIndex)
					|| !(this.m_keyAccidental.equals(oKey.m_keyAccidental))
					|| (this.mode != oKey.mode)
				) {
				return false;
			}
			for (int i = 0; i < accidentals.length; i++) {
				if (!accidentals[i].equals(oKey.accidentals[i]))
					return false;
			}
			return true;
		}
		else
			return super.equals(o);
	}
	
	static public KeySignature transpose(KeySignature key, int semitones) {
		if ((semitones % 12) == 0) {
			try {
				return (KeySignature) key.clone();
			} catch (CloneNotSupportedException cnse) {
				cnse.printStackTrace();
			}
		}
		Note keyNote = new Note(key.getNote(), key.getAccidental());
		//looks for added accidentals, compare to new key with only mode
		KeySignature reference = new KeySignature(
									keyNote.getStrictHeight(),
									keyNote.getAccidental(),
									key.getMode());
		//addedAcc[degree] array of degree => accidental
		float[] addedAcc = new float[7];
		for (int i = Degree.I; i <= Degree.VII; i++) {
			float refAcc = reference.getAccidentalFor(reference.getDegree(i)).getValue();
			float acc = key.getAccidentalFor(key.getDegree(i)).getValue();
			addedAcc[i-1] = (acc-refAcc);
		}
		Note transposedKeyNote = Note.transpose(keyNote, semitones);
		KeySignature ret = new KeySignature(
				transposedKeyNote.getStrictHeight(),
				transposedKeyNote.getAccidental(),
				key.getMode()
			);
		for (int i = Degree.I; i <= Degree.VII; i++) {
			if (addedAcc[i-1] != 0) {
				Accidental acc = new Accidental(
						ret.getAccidentalFor(ret.getDegree(i)).getValue()
						+ addedAcc[i-1]);
				if ((acc.isDoubleFlat()) || (acc.isDoubleSharp()))
					acc = Accidental.NATURAL;
				ret.setAccidental(ret.getDegree(i), acc);
			}
		}
		return ret;
	}

  	public Object clone() throws CloneNotSupportedException {
  		Object k = super.clone();
  		((KeySignature) k).accidentals = (Accidental[]) accidentals.clone();
  		((KeySignature) k).m_clef = (Clef) m_clef.clone();
  		return k;
  	}

/*    public void display ()
    {
        System.out.println (key +  " " + m_keyAccidental + " " + mode);
    }*/
}
