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
package abc.ui.scoretemplates;

import java.awt.Color;
import java.io.Serializable;

import abc.ui.fonts.MusicalFont;
import abc.ui.fonts.SonoraFont;
import abc.ui.swing.Engraver;
import abc.ui.swing.JTune;

/**
 * List of score attributes for score template.
 * 
 * @see abc.ui.swing.ScoreTemplate#setAttribute(ScoreAttribute, Object)
 * @see abc.ui.swing.ScoreTemplate#setAttributeSize(ScoreAttribute, float, SizeUnit)
 * @see abc.ui.swing.ScoreTemplate#getAttributeSize(ScoreAttribute)
 */
public class ScoreAttribute implements Cloneable, Serializable {

	static public class Size implements Cloneable, Serializable {
		private static final long serialVersionUID = 242654766042695139L;
		private float m_size = -1;
		private SizeUnit m_unit = null;
		public Size(float size, SizeUnit u) {
			m_size = size;
			m_unit = u;
		}
		public Object clone() { return new Size(m_size, (SizeUnit) m_unit.clone()); }
		public float getSize() { return m_size; }
		public SizeUnit getUnit() { return m_unit; }
		public int hashCode() { return super.hashCode(); }
		public String toString() {
			return m_size + m_unit.toString();
		}
	}
	
	/** Draws acciaccaturas after the associated note.<br>
	 * This can be useful in folkloric music
	 * Default value: false<br>
	 * Type: boolean */
	public static final ScoreAttribute ACCIACCATURA_AFTER_NOTE
		= new ScoreAttribute("AcciaccaturaAfterNote",
				new Boolean(false));
	
	/** Add a slash to acciaccaturas.<br>
	 * Default value: true<br>
	 * Type: boolean */
	public static final ScoreAttribute ACCIACCATURA_SLASH
		= new ScoreAttribute("AcciaccaturaSlash",
				new Boolean(true));

	/** Add a slur between note and acciaccaturas.<br>
	 * Default value: false<br>
	 * Type: boolean */
	public static final ScoreAttribute ACCIACCATURA_SLUR
		= new ScoreAttribute("AcciaccaturaSlur",
				new Boolean(false));

	/** Space after an accidental, in px or note width.<br>
	 * Default value: 0.3 * note width<br>
	 * Type: size */
	public static final ScoreAttribute ACCIDENTAL_SPACE
		= new ScoreAttribute("AccidentalSpace",
				new Size(.3f, SizeUnit.NOTE_WIDTH));
	
	/** Add a slash to appoggiaturas.<br>
	 * Default value: true (in theory, should be false)<br>
	 * Type: boolean */
	public static final ScoreAttribute APPOGGIATURA_SLASH
		= new ScoreAttribute("AppoggiaturaSlash",
				new Boolean(true));

	/** Add a slur between note and appoggiaturas.<br>
	 * Default value: false<br>
	 * Type: boolean */
	public static final ScoreAttribute APPOGGIATURA_SLUR
		= new ScoreAttribute("AppoggiaturaSlur",
				new Boolean(false));

	/** Space between the staff top and chord line,
	 * expressed in px or {@link SizeUnit#STAFF_HEIGHT}.<br>
	 * Default value: 1.25 * staff height<br>
	 * Type: size */
	public static final ScoreAttribute CHORD_LINE_SPACING
		= new ScoreAttribute("ChordLineSpacing",
				new Size(1.25f, SizeUnit.STAFF_HEIGHT));
	
	/** Horizontal position for dynamics, from the associated element
	 * expressed in {@link SizeUnit#NOTE_WIDTH}.<br>
	 * Default value: -1 * note width (a bit on the left)<br>
	 * Type: size */
	public static final ScoreAttribute DYNAMIC_HORIZONTAL_OFFSET
		= new ScoreAttribute("DynamicHoriontalOffset",
				new Size(-1f, SizeUnit.NOTE_WIDTH));

	/** Vertical position for dynamics, from the bottom of the
	 * staff line, expressed in {@link SizeUnit#NOTE_HEIGHT}.<br>
	 * Default value: -6 * note height (above the staff)<br>
	 * Type: size */
	public static final ScoreAttribute DYNAMIC_VERTICAL_OFFSET
		= new ScoreAttribute("DynamicVerticalOffset",
				new Size(-6f, SizeUnit.NOTE_HEIGHT));
	
	/** Engraver mode, possible values are {@link Engraver#DEFAULT}
	 * and {@link Engraver#NONE}.<br>
	 * Default value: Engraver.DEFAULT<br>
	 * Type: number (integer) */
	public static final ScoreAttribute ENGRAVER_MODE
		= new ScoreAttribute("EngraverMode",
				new Integer(Engraver.DEFAULT));
	
	/** Engraver variation, value is a number between
	 * {@link Engraver#VARIATION_MIN} and
	 * {@link Engraver#VARIATION_MAX}.<br>
	 * Default value: {@link Engraver#VARIATION_DEFAULT}<br>
	 * Type: number (integer) */
	public static final ScoreAttribute ENGRAVER_VARIATION
		= new ScoreAttribute("EngraverMode",
				new Integer(Engraver.VARIATION_DEFAULT));
	
	/** Left margin for first staff, expressed px or
	 * {@link SizeUnit#STAFF_WIDTH}.<br>
	 * Default value: 0 (* staff width)<br>
	 * Type: size */
	public static final ScoreAttribute FIRST_STAFF_LEFT_MARGIN
		= new ScoreAttribute("FirstStaffLeftMargin",
				new Size(0, SizeUnit.STAFF_WIDTH));
	
	/** Top margin for first staff, just below the headers,
	 * expressed in px or {@link SizeUnit#STAFF_HEIGHT}.<br>
	 * Default value: 0.75 * staff height<br>
	 * Type: size */
	public static final ScoreAttribute FIRST_STAFF_TOP_MARGIN
		= new ScoreAttribute("FirstStaffTopMargin",
				new Size(.75f, SizeUnit.STAFF_HEIGHT));
	
	/** Thickness of gracenote link.<br>
	 * Default value: 1/2 * gracenote height<br>
	 * Type: size */
	public static final ScoreAttribute GRACENOTE_LINK_STROKE
		= new ScoreAttribute("GracenoteLinkStroke",
				new Size(1f/2f, SizeUnit.GRACENOTE_HEIGHT));
	
	/** Default note spacing, in gracenote width, or px.<br>
	 * Default value: 1 * gracenote width<br>
	 * Type: size */
	public static final ScoreAttribute GRACENOTE_SPACING
		= new ScoreAttribute("GracenoteSpacing",
				new Size(1f, SizeUnit.GRACENOTE_WIDTH));
	
	/** Gracenote stem length<br>
	 * Default value: 3 * gracenote height<br>
	 * Type: size */
	public static final ScoreAttribute GRACENOTE_STEM_LENGTH
		= new ScoreAttribute("GracenoteStemLength",
				new Size(3f, SizeUnit.GRACENOTE_HEIGHT));
	
	/** Gracenotes stem policy, possible value : {@link JTune#STEMS_AUTO},
	 * {@link JTune#STEMS_UP} and {@link JTune#STEMS_DOWN}.<br>
	 * Default value: STEM UP<br>
	 * Type: number (byte) */
	public static final ScoreAttribute GRACENOTE_STEM_POLICY
		= new ScoreAttribute("GracenoteStemPolicy",
				new Byte(JTune.STEMS_UP));
	
	/** Justify staff lines<br>
	 * Default value: true<br>
	 * Type: boolean */
	public static final ScoreAttribute JUSTIFY
		= new ScoreAttribute("Justify",
				new Boolean(true));
	
	/** Bottom margin.<br>
	 * Default value: 0px<br>
	 * Type: size */
	public static final ScoreAttribute MARGIN_BOTTOM
		= new ScoreAttribute("MarginBottom",
				new Size(0f, SizeUnit.PX));
	
	/** Left margin.<br>
	 * Default value: 0px<br>
	 * Type: size */
	public static final ScoreAttribute MARGIN_LEFT
		= new ScoreAttribute("MarginLeft",
				new Size(0f, SizeUnit.PX));
	
	/** Right margin.<br>
	 * Default value: 0px<br>
	 * Type: size */
	public static final ScoreAttribute MARGIN_RIGHT
		= new ScoreAttribute("MarginRight",
				new Size(0f, SizeUnit.PX));
	
	/** Top margin.<br>
	 * Default value: 0px<br>
	 * Type: size */
	public static final ScoreAttribute MARGIN_TOP
		= new ScoreAttribute("MarginTop",
				new Size(0f, SizeUnit.PX));
	
	/** Musical font, value is a {@link MusicalFont} object.<br>
	 * Default value: {@link SonoraFont}<br>
	 * Type: object (MusicalFont) */
	public static final ScoreAttribute NOTATION_FONT
		= new ScoreAttribute("MusicalFont", new SonoraFont());

	/** Gracenotes font size, expressed in pt or percent
	 * of notation size.<br>
	 * Default value: 60% (of notation font size)<br>
	 * Type: size */
	public static final ScoreAttribute NOTATION_GRACENOTE_SIZE
		= new ScoreAttribute("GracenoteSize",
				new Size(60, SizeUnit.PERCENT));
	
	/** Notation font size, expressed in pt.<br>
	 * Default value: 45pt<br>
	 * Type: size */
	public static final ScoreAttribute NOTATION_SIZE
		= new ScoreAttribute("NotationSize",
				new Size(39, SizeUnit.PT));
	
	/** Tempo indicator font size, expressed in pt or percent
	 * of notation size.<br>
	 * Default value: 75% (of notation font size)<br>
	 * Type: size */
	public static final ScoreAttribute NOTATION_TEMPO_SIZE
		= new ScoreAttribute("TempoSize",
				new Size(75, SizeUnit.PERCENT));

	/** Thickness of note link.<br>
	 * Default value: 1/3 * note height<br>
	 * Type: size */
	public static final ScoreAttribute NOTE_LINK_STROKE
		= new ScoreAttribute("NoteLinkStroke",
				new Size(1f/2f, SizeUnit.NOTE_HEIGHT));

	/** Default note spacing, in note width, or px.<br>
	 * Default value: 1.5 * note width<br>
	 * Type: size */
	public static final ScoreAttribute NOTE_SPACING
		= new ScoreAttribute("NoteSpacing",
				new Size(1.5f, SizeUnit.NOTE_WIDTH));
	
	/** Note stem length<br>
	 * Default value: 3 * note height<br>
	 * Type: size */
	public static final ScoreAttribute NOTE_STEM_LENGTH
		= new ScoreAttribute("NoteStemLength",
				new Size(3f, SizeUnit.NOTE_HEIGHT));

	/** Notes stem policy, possible value : {@link JTune#STEMS_AUTO},
	 * {@link JTune#STEMS_UP} and {@link JTune#STEMS_DOWN}.<br>
	 * Default value: STEM AUTO<br>
	 * Type: number (byte) */
	public static final ScoreAttribute NOTE_STEM_POLICY
		= new ScoreAttribute("NoteStemPolicy",
				new Byte(JTune.STEMS_AUTO));

	private static final long serialVersionUID = 4720580138163902559L;

	/** Slur anchor vertical offset, in note height or px.<br>
	 * Default value: 1/3 * note height<br>
	 * Type: size */
	public static final ScoreAttribute SLUR_ANCHOR_Y_OFFSET
		= new ScoreAttribute("SlurAnchorYOffset",
				new Size(1f/3f, SizeUnit.NOTE_HEIGHT));
	
	/** Slur thickness, in note height or px.<br>
	 * Default value: 1/7 * note height<br>
	 * Type: size */
	public static final ScoreAttribute SLUR_THICKNESS
		= new ScoreAttribute("SlurThickness",
				new Size(1f/7f, SizeUnit.NOTE_HEIGHT));
	
	/** Space between two staves, expressed in px or
	 * {@link SizeUnit#STAFF_HEIGHT}.<br>
	 * Default value: 1.5 * staff height<br>
	 * Type: size */
	public static final ScoreAttribute STAFF_LINES_SPACING
		= new ScoreAttribute("StaffLinesSpacing",
				new Size(1.5f, SizeUnit.STAFF_HEIGHT));
	
	/** Stem thickness.
	 * Default value: 1/12 * note width<br>
	 * Type: size */
	public static final ScoreAttribute STEM_STROKE
		= new ScoreAttribute("StemStroke",
				new Size(1f/12f, SizeUnit.NOTE_WIDTH));
	
	/** Default text size, from which all text elements sizes
	 * are derived, expressed in pt or percent of notation size.<br>
	 * Default value: 25% of notation size<br>
	 * Type: size */
	public static final ScoreAttribute TEXT_DEFAULT_SIZE
		= new ScoreAttribute("TextDefaultSize",
				new Size(33, SizeUnit.PERCENT));
	
	/** Transposition, in semitones<br>
	 * Default value: 0 semitones<br>
	 * Type: number (integer) */
	public static final ScoreAttribute TRANSPOSITION
		= new ScoreAttribute("Transposition", new Integer(0));
	
	
	/** Tuplet number vertical offset, in note height or px.<br>
	 * Default value: 1/4 * note height<br>
	 * Type: size */
	public static final ScoreAttribute TUPLET_NUMBER_Y_OFFSET
		= new ScoreAttribute("TupletNumberYOffset",
				new Size(1f/4f, SizeUnit.NOTE_HEIGHT));
	
	private Object m_defaultValue;
	private String m_name;
	private ScoreAttribute(String s, Boolean defaultValue) {
		m_name = s;
		m_defaultValue = defaultValue;
	}
	protected ScoreAttribute(String s, Color defaultValue) {
		m_name = s;
		m_defaultValue = defaultValue;
	}
	private ScoreAttribute(String s, MusicalFont defaultValue) {
		m_name = s;
		m_defaultValue = defaultValue;
	}
	private ScoreAttribute(String s, Number defaultValue) {
		m_name = s;
		m_defaultValue = defaultValue;
	}
	private ScoreAttribute(String s, Size defaultValue) {
		m_name = s;
		m_defaultValue = defaultValue;
	}
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	public boolean equals(Object o) {
		if (o instanceof ScoreAttribute)
			return ((ScoreAttribute)o).m_name.equals(m_name);
		else if (o instanceof String)
			return ((String)o).equals(m_name);
		else
			return super.equals(o);
	}
	public Object getDefaultValue() {
		return m_defaultValue;
	}
	public String getName() {
		return m_name;
	}
	public int hashCode() {
		return super.hashCode();
	}
	public String toString() {
		return m_name;
	}
}
