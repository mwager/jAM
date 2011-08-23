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

/** Accidentals */
public class Accidental implements Cloneable, Serializable
{
	private static final long serialVersionUID = -50238873169452062L;
	
	/** The <TT>DOUBLE FLAT</TT> ccidental type : bb */
	private static final float _DOUBLE_FLAT = -2.0f;
	/** The <TT>DOUBLE SHARP</TT> accidental type : x */
	private static final float _DOUBLE_SHARP = 2.0f;
	/** The <TT>FLAT</TT> accidental type : b. */
	private static final float _FLAT = -1.0f;
	/** The <TT>NATURAL</TT> accidental type. */
	private static final float _NATURAL = 0.0f;
	/** The <TT>NONE</TT> accidental type. */
	private static final float _NONE = 10.0f;
	/** The <TT>SHARP</TT> accidental type : # */
	private static final float _SHARP = 1.0f;

	public static final Accidental NONE = new Accidental();
	public static final Accidental DOUBLE_FLAT = new Accidental(_DOUBLE_FLAT);
	public static final Accidental FLAT_AND_A_HALF = new Accidental(-1.5f);
	public static final Accidental FLAT = new Accidental(_FLAT); //-1f
	public static final Accidental HALF_FLAT = new Accidental(-0.5f);
	public static final Accidental NATURAL = new Accidental(_NATURAL);
	public static final Accidental HALF_SHARP = new Accidental(0.5f);
	public static final Accidental SHARP = new Accidental(_SHARP);
	public static final Accidental SHARP_AND_A_HALF = new Accidental(1.5f);
	public static final Accidental DOUBLE_SHARP = new Accidental(_DOUBLE_SHARP);

	/**
	 * Convert a string to an accidental. Understand ABC ^ and _, chord names #,
	 * b and unicode char
	 */
	public static Accidental convertToAccidental(String accidental) throws IllegalArgumentException
	{
		// TODO double and half flat and sharp
		if (accidental==null) return NONE;
		else if (accidental.equals("^")
			|| accidental.equals("#")
			|| accidental.equals("\u266F")) return SHARP;
		else if (accidental.equals("_")
			|| accidental.equals("b")
			|| accidental.equals("\u266D")) return FLAT;
		else if (accidental.equals("=")
			|| accidental.equals("\u266E")) return NATURAL;
		else if (accidental.equals("^^")
			|| accidental.equals("##")
			|| accidental.equals("\u266F\u266F")) return DOUBLE_SHARP;
		else if (accidental.equals("__")
			|| accidental.equals("bb")
			|| accidental.equals("\u266D\u266D")) return DOUBLE_FLAT;
		else if (accidental.equals("^/")) return HALF_SHARP;
		else if (accidental.equals("_/")) return HALF_FLAT;
		else if (accidental.equals("^3/")
				|| accidental.equals("^3/2")) return SHARP_AND_A_HALF;
		else if (accidental.equals("_3/")
				|| accidental.equals("_3/2")) return FLAT_AND_A_HALF;
		else throw new IllegalArgumentException(accidental + " is not a valid accidental");
	}

	private Fraction m_fraction = null;
	private float m_value = _NONE;
	
	public Accidental() {
	}
	
	public Accidental(float value) {
		setValue(value);
	}
	
	public Accidental(Fraction fraction) {
		m_fraction = fraction;
		setValue(fraction.floatValue());
	}
	
	public boolean equals(Object o) {
		if (o instanceof Accidental) {
			return ((Accidental)o).getValue()==getValue();
		}
		else return super.equals(o);
	}
	
	/** Returns the microtonal offset between this (microtonal)
	 * accidental and the nearest occidental semitone.
	 * 
	 * e.g. returns -0.5 for flat-and-half (-1.5), this is the offset
	 * from -1 (flat). Returns 0.5 for half-flat (-0.5), this is
	 * the offset from -1 (flat).
	 */
	public float getMicrotonalOffset() {
		if (isMicrotonal())
			return m_value - getNearestOccidentalValue();
		else
			return 0;
	}
	
	/**
	 * Return the nearest non microtonal semitone
	 * 
	 * e.g. flat (-1) for a half-flat or flat-and-half
	 */
	public byte getNearestOccidentalValue() {
		if (m_value == _NONE || m_value == _NATURAL)
			return 0;
		if ((m_value == _DOUBLE_FLAT) || (m_value == _DOUBLE_SHARP))
			return (byte)m_value;
		if (m_value < 0)
			return -1;//flat
		if (m_value > 0)
			return 1;//sharp
		return 0;
	}
	public float getValue() {
		if (m_value == _NONE)
			return 0;
		else
			return m_value;
	}
	
	/** Value is not equals to {@link #_NONE} */
	public boolean isDefined() {
		return m_value != _NONE;
	}
	
	public boolean isDoubleFlat() {
		return m_value == _DOUBLE_FLAT;
	}
	
	public boolean isDoubleSharp() {
		return m_value == _DOUBLE_SHARP;
	}
	
	public boolean isFlat() {
		return m_value == _FLAT;
	}
	
	/** Value is not defined, use the key accidental */
	public boolean isInTheKey() {
		return m_value == _NONE;
	}
	/** Value is not defined, use the key accidental */
	public boolean isNotDefined() {
		return isInTheKey();
	}
	
	public boolean isMicrotonal() {
		return isDefined() && !isDoubleFlat() && !isFlat()
			&& !isNatural() && !isSharp() && !isDoubleSharp();
	}
	
	public boolean isNatural() {
		return m_value == _NATURAL;
	}
	
	public boolean isSharp() {
		return m_value == _SHARP;
	}
	
	private void setValue(float value) {
		if (((value >= _DOUBLE_FLAT) && (value <= _DOUBLE_SHARP))
				|| (value == _NONE))
			m_value = value;
		else
			m_value = _NONE;
	}
	
	public String toString() {
		if (m_value == _NONE) return "";
		if (m_value == _NATURAL) return "=";
		if (m_value == _FLAT) return "b";
		if (m_value == _SHARP) return "#";
		if (m_value == _DOUBLE_FLAT) return "bb";
		if (m_value == _DOUBLE_SHARP) return "##";
		if (isMicrotonal()) {
			String ret = "";
			if (m_fraction != null)
				ret = m_fraction.toString();
			else
				ret = Float.toString(m_value);
			return ret
				+ (getNearestOccidentalValue()<0?"b":"#");
		}
		return "";
	}
}