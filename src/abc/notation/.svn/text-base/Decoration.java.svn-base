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

/** This class defines decorations. Decoration definitions include:
 * <pre>
 * .		staccato
 * ~		general gracing (abc v1.6 and older)
 * ~		irish roll (abc v2.0)
 * u		upbow
 * v		downbow
 * T       trill
 * H       fermata
 * L       accent or emphasis
 * M       lowermordent
 * P       uppermordent
 * S       segno
 * O       coda
 * </pre>
 * <b>NOTE:</b>The symbols T,H,L,M,P,S,O and ABC2.0 features and are not supported
 * by the abc4j 1.6 parser.
 */
public class Decoration extends SymbolElement implements Cloneable
{

  private static final long serialVersionUID = 8903942534378714085L;

  public static final byte UNKNOWN = 0;

  /** Staccato. Ex: <TT>.</TT> */
  public static final byte STACCATO = 1;

  /** Roll. Ex: <TT>~</TT> or <TT>+roll+</TT> */
  public static final byte ROLL = 2;

  /** Upbow. Ex: <TT>u</TT> or <TT>+upbow+</TT> */
  public static final byte UPBOW = 3;

  /** Downbow. Ex: <TT>v</TT> or <TT>+downbow+</TT> */
  public static final byte DOWNBOW = 4;
  
  /** Trill. Ex: <TT>T</TT> or <TT>+trill+</TT> */
  public static final byte TRILL = 5;

  /** Fermata. Ex: <TT>H</TT> or <TT>+fermata+</TT> */
  public static final byte FERMATA = 6;

  /** The <TT>></TT> accent type, also named "marcato" */
  public static final byte ACCENT = 7;
  /** The <TT>></TT> marcato (accent) type */
  public static final byte MARCATO = 7;

  /** Lower mordent. Ex: <TT>M</TT> */
  public static final byte LOWERMORDENT = 8;

  /** Upper mordent. Ex: <TT>P</TT> */
  public static final byte UPPERMORDENT = 9;

  //TODO move SEGNO and CODA to RepeatBarLine
  /** The  type. Ex:  */
  public static final byte SEGNO = 10;

  /** The  type. Ex:  */
  public static final byte CODA = 11;
  
  public static final byte STACCATISSIMO = 12;
  
  /** The <TT>,</TT> symbol, breath */
  public static final byte BREATH = 13;

  /** The <TT>//</TT> symbol, longer breath */
  public static final byte BREATH_LONGER = 14;
  
  /** The tenuto <TT>-</TT> symbol */
  public static final byte TENUTO = 15;

  /** The sforzando <TT>/\</TT> symbol */
  public static final byte SFORZANDO = 16;
  
  /** The marcato <TT>></TT> with staccato <TT>.</TT> under */
  public static final byte MARCATO_STACCATO = 17;

  /** The tenuto <TT>-</TT> with staccato <TT>.</TT> under */
  public static final byte MEZZO_STACCATO = 18;

  /** The <TT>/\</TT> with a staccato <TT>.</TT> under */
  public static final byte MARTELATO_STACCATO = 19;
  
  /** The <I>Ped.</I> symbol */
  public static final byte PEDAL_DOWN = 20;
  /** The <I>*</I> symbol, when pedal is released */
  public static final byte PEDAL_UP = 21;
  
  public static final byte STEM_COMBINE_UP_SINGLE = 22;
  public static final byte STEM_COMBINE_UP_DOUBLE = 23;
  public static final byte STEM_COMBINE_UP_TRIPLE = 24;
  
  public static final byte FERMATA_INVERTED = 25;
  
  public static final byte FINGERING_0 = 26;
  public static final byte FINGERING_1 = 27;
  public static final byte FINGERING_2 = 28;
  public static final byte FINGERING_3 = 29;
  public static final byte FINGERING_4 = 30;
  public static final byte FINGERING_5 = 31;
  
  /** left-hand pizzicato, or rasp for French horns */
  public static final byte PLUS = 32;
  
  /** small filled-in wedge mark */
  public static final byte WEDGE = 33;
  
  /** small circle above note indicating open string or harmonic */
  public static final byte OPEN = 34;
  
  /** cello thumb symbol, or snap-pizzicato mark */
  public static final byte THUMB = 35;
  
  /** Grupetto */
  public static final byte TURN = 36;
  public static final byte GRUPETTO_UP = TURN;
  
  /** vertical line on the upper part of the staff */
  public static final byte SHORT_PHRASE = 37;
  
  /** vertical line on the upper part of the staff,
   * extending down to the centre line */
  public static final byte MEDIUM_PHRASE = 38;
  
  /** vertical line on the upper part of the staff,
   * extending 3/4 of the way down */
  public static final byte LONG_PHRASE = 39;
  
  public static final byte DA_SEGNO = 40;
  public static final byte DA_CAPO = 41;
  public static final byte DA_CODA = 42;
  public static final byte FINE = 43;
  public static final byte SLIDE = 44;
  public static final byte TURNX = 45;
  /** inverted grupetto */
  public static final byte TURN_INVERTED = 46;
  public static final byte GRUPETTO_DOWN = TURN_INVERTED;
  public static final byte TURNX_INVERTED = 47;
  public static final byte ARPEGGIO = 48;
  public static final byte TRILL_START = 49;
  public static final byte TRILL_END = 50;
  
  /** Guitar bend up (tiré in French) */
  public static final byte BEND_PULL = 51;
  /** Guitar bend down (relâché in French) */
  public static final byte BEND_RELEASE = 52;
  /** Guitar bend up and down (tiré-relâché in French) */
  public static final byte BEND_PULL_RELEASE = 53;
  
  public static final byte DOUBLE_UPPER_MORDANT = 54;
  public static final byte DOUBLE_LOWER_MORDANT = 55;
  
  public static final byte GROUP_64TH = 56;
  public static final byte GROUP_32ND = 57;
  public static final byte GROUP_16TH = 58;
  public static final byte GROUP_8TH = 59;
  
  public static final byte REPEAT_LAST_BAR = 60;
  public static final byte REPEAT_LAST_TWO_BARS = 61;
  
  /** The v1.6 ~ roll */
  public static final byte GENERAL_GRACING = 99;

	/** The type of this Decoration. */
	private byte m_type = UNKNOWN;

	/**
	 * Creates a new decoration of the given type.
	 * 
	 * @param type
	 *            {@link #TRILL}, {@link #ACCENT}, {@link #CODA}...
	 */
	public Decoration(byte type) {
		m_type = type;
	}

	/** Returns the type of this decoration. */
	public byte getType() {
		return m_type;
	}

	/**
	 * Checks if the decoration's type is the same as the give type.
	 * 
	 * @return true if decoration is of type
	 */
	public boolean isType(byte type) {
		return (m_type == type);
	}

	public boolean equals(Object o) {
		if (o instanceof Decoration) {
			return isType(((Decoration) o).getType());
		} else
			return super.equals(o);
	}

	/**
	 * Checks if this decoration is a dynamic. mezzo forte, crescendo, etc.
	 * 
	 * @return true if decoration is dynamic
	 * @deprecated Dynamic and Decoration are distinct
	 */
	public boolean isDynamic(byte decoration) {
		return false;
	}


  /**
	 * Converts the specified string to a Decoration type.
	 * 
	 * @param str
	 *            The string to be converted as a decoration. Possible values
	 *            are:
	 * 
	 * <pre>
	 * .		staccato
	 * ~		general gracing (abc v1.6 and older)
	 * ~		irish roll (abc v2.0)
	 * u		upbow
	 * v		downbow
	 * T       trill
	 * H       fermata
	 * L       accent or emphasis
	 * M       lowermordent
	 * P       uppermordent
	 * S       segno
	 * O       coda
	 * </pre>
	 * 
	 * @return The decoration type corresponding to the given string. <TT>null</TT>
	 *         is returned if no type matches the string.
	 */
	public static byte convertToType(String str)
	{
		byte type = UNKNOWN;
		if (str.equals(".")) type = STACCATO;
		else if (str.equals("~")) type = ROLL;
		else if (str.equals("u")) type = UPBOW;
		else if (str.equals("v")) type = DOWNBOW;
		else if (str.equals("T")) type = TRILL;
		else if (str.equals("H")) type = FERMATA;
		else if (str.equals("L")) type = ACCENT;
		else if (str.equals("M")) type = LOWERMORDENT;
		else if (str.equals("P")) type = UPPERMORDENT;
		else if (str.equals("S")) type = SEGNO;
		else if (str.equals("O")) type = CODA;
		
		else if (str.equals("trill")) type = TRILL;
		else if (str.equals("lowermordent")
				|| str.equals("mordent")) type = LOWERMORDENT;
		else if (str.equals("uppermordent")
				|| str.equals("pralltriller")) type = UPPERMORDENT;
		else if (str.equals(">")
				|| str.equals("accent")
				|| str.equals("emphasis")) type = ACCENT;
		else if (str.equals("fermata")) type = FERMATA;
		else if (str.equals("invertedfermata")) type = FERMATA_INVERTED;
		else if (str.equals("tenuto")) type = TENUTO;
		else if (str.equals("0")) type = FINGERING_0;
		else if (str.equals("1")) type = FINGERING_1;
		else if (str.equals("2")) type = FINGERING_2;
		else if (str.equals("3")) type = FINGERING_3;
		else if (str.equals("4")) type = FINGERING_4;
		else if (str.equals("5")) type = FINGERING_5;
		else if (str.equals("plus")) type = PLUS;
		else if (str.equals("wedge")) type = WEDGE;
		else if (str.equals("open")) type = OPEN;
		else if (str.equals("thumb")
				|| str.equals("snap")) type = THUMB;
		else if (str.equals("turn")) type = TURN;
		else if (str.equals("roll")) type = ROLL;
		else if (str.equals("breath")) type = BREATH;
		else if (str.equals("shortphrase")) type = SHORT_PHRASE;
		else if (str.equals("mediumphrase")) type = MEDIUM_PHRASE;
		else if (str.equals("longphrase")) type = LONG_PHRASE;
		else if (str.equals("segno")) type = SEGNO;
		else if (str.equals("coda")) type = CODA;
		else if (str.equals("D.S.")) type = DA_SEGNO;
		else if (str.equals("D.C.")
				|| str.equals("dacapo")) type = DA_CAPO;
		else if (str.equals("dacoda")) type = DA_CODA;
		else if (str.equals("fine")) type = FINE;
		else if (str.equals("upbow")) type = UPBOW;
		else if (str.equals("downbow")) type = DOWNBOW;
		else if (str.equals("slide")) type = SLIDE;
		else if (str.equals("turnx")) type = TURNX;
		else if (str.equals("invertedturn")) type = TURN_INVERTED;
		else if (str.equals("invertedturnx")) type = TURNX_INVERTED;
		else if (str.equals("arpeggio")) type = ARPEGGIO;
		else if (str.equals("trill(")) type = TRILL_START;
		else if (str.equals("trill)")) type = TRILL_END;
		else if (str.equals("repeatbar")) type = REPEAT_LAST_BAR;
		else if (str.equals("repeatbar2")) type = REPEAT_LAST_TWO_BARS;
		return type;
	}

	/**
	 * Returns a string representation of this object.
	 * 
	 * @return A string representation of this object.
	 */
	public String toString() {
		if (m_type == STACCATO) return (".");
		else if (m_type == ROLL) return ("~");
		else if (m_type == UPBOW) return ("u");
		else if (m_type == DOWNBOW) return ("v");
		else if (m_type == TRILL) return ("T");
		else if (m_type == FERMATA) return ("H");
		else if (m_type == ACCENT) return ("L");
		else if (m_type == LOWERMORDENT) return ("M");
		else if (m_type == UPPERMORDENT) return ("P");
		else if (m_type == SEGNO) return ("S");
		else if (m_type == CODA) return ("O");
		return "";
	}
	
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
