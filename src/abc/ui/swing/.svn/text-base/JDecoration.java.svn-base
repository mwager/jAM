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
package abc.ui.swing;

import java.awt.Graphics2D;
//import java.awt.Shape;
//import java.awt.font.GlyphVector;
//import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import abc.notation.Decoration;
import abc.notation.MusicElement;
import abc.notation.Tune;
import abc.ui.fonts.MissingGlyphException;


/** This class defines a score rendition element. Rendition scores elements
 * are graphical representations of tune score elements objects retrieved
 * from a tune object.
 * A <TT>JScoreElement</TT> can itself contain <TT>JScoreElement</TT> instances.
 * You should figure out the graphical score representation as a tree of
 * <TT>JScoreElement</TT>. (Composite)
 * @see Tune#getMusic()
 */
class JDecoration extends JScoreElementAbstract {

	
	/** Above the staff, above the note if note is higher than staff */
	protected static final byte ABOVE_STAFF = 0;
	/** Under the staff, under the note if note is lower than staff */
	protected static final byte UNDER_STAFF = 1;
	/** Above the note head and stem, can be in the staff */
	protected static final byte ABOVE_NOTE = 2;
	/** Under the note head and stem, can be in the staff */
	protected static final byte UNDER_NOTE = 3;
	/** above note head if stem up, under note head else */
	protected static final byte VERTICAL_NEAR_STEM = 4;
	/** under note head if stem up, above note head else */
	protected static final byte VERTICAL_AWAY_STEM = 5;
	/** right of note head if stem up, left of note head else */
	protected static final byte HORIZONTAL_NEAR_STEM = 6;
	/** left of note head if stem up, right of note head else */
	protected static final byte HORIZONTAL_AWAY_STEM = 7;
	/** at stem end, may be in the staff */
	protected static final byte STEM_END = 8;
	/** at stem end, always under/above the staff */
	protected static final byte STEM_END_OUT_OF_STAFF = 9;
	/** on the middle of the stem */
	protected static final byte STEM_MIDDLE = 10;
	/** above the staff, after the note (e.g. pause) */
	protected static final byte ABOVE_STAFF_AFTER_NOTE = 11;
	/** above note head if stem up, under note head else */
	protected static final byte VERTICAL_NEAR_STEM_OUT_STAFF = 12;
	/** under note head if stem up, above note head else */
	protected static final byte VERTICAL_AWAY_STEM_OUT_STAFF = 13;
	/** vertically in the middle of the staff */
	protected static final byte MIDDLE_STAFF = 14;
	/** horizontally on the left of the note/element */
	protected static final byte LEFT_NOTE = 15;
	/** horizontally on the right of the note/element (e.g. bend) */
	protected static final byte RIGHT_NOTE = 16;
	
	protected static final int POSITIONS_COUNT = 17;

	protected static byte getPosition(Decoration deco) {
		switch (deco.getType()) {
		case Decoration.STACCATO:
			return VERTICAL_AWAY_STEM;
		case Decoration.TENUTO:
		case Decoration.ACCENT://marcato
		case Decoration.STACCATISSIMO:
		case Decoration.SFORZANDO:
		case Decoration.MARCATO_STACCATO:
		case Decoration.MARTELATO_STACCATO:
		case Decoration.MEZZO_STACCATO:
			return VERTICAL_AWAY_STEM_OUT_STAFF;
		case Decoration.ROLL:
		case Decoration.FERMATA:
		case Decoration.UPBOW:
		case Decoration.DOWNBOW:
			//return STEM_END_OUT_OF_STAFF;
		case Decoration.GENERAL_GRACING:
		case Decoration.TRILL:
		case Decoration.LOWERMORDENT:
		case Decoration.UPPERMORDENT:
		case Decoration.SEGNO:
		case Decoration.CODA:
			return ABOVE_STAFF;
		case Decoration.BREATH:
		case Decoration.BREATH_LONGER:
			return ABOVE_STAFF_AFTER_NOTE;
		case Decoration.PEDAL_DOWN:
		case Decoration.PEDAL_UP:
		case Decoration.FERMATA_INVERTED:
			return UNDER_STAFF;
		case Decoration.STEM_COMBINE_UP_SINGLE:
		case Decoration.STEM_COMBINE_UP_DOUBLE:
		case Decoration.STEM_COMBINE_UP_TRIPLE:
			return STEM_MIDDLE;
		case Decoration.REPEAT_LAST_BAR:
		//	return LEFT_NOTE;
		case Decoration.REPEAT_LAST_TWO_BARS:
			return MIDDLE_STAFF;
		default:
			return ABOVE_STAFF;
		}
	}
	
	private Decoration m_decoration = null;
	
	private boolean m_isInverted = false;
	
	private JScoreElementAbstract m_scoreElement = null;

	/** The width of this rendition element */
	protected double m_width = -1;

	/** The height of this rendition element */
	protected double m_height = -1;

	/** Constructor
	 * @param decoration The decoration
	 * @param mtrx The score metrics needed
	 */
	protected JDecoration(Decoration decoration, ScoreMetrics mtrx) {
		this(decoration, null, mtrx);
	}

	/** Constructor
	 * @param decoration The decoration
	 * @param base The base
	 * @param mtrx The score metrics needed
	 */
	protected JDecoration(Decoration decoration, Point2D base, ScoreMetrics mtrx) {
		super(mtrx);
		m_decoration = decoration;
		if (base != null) setBase(base);
		Rectangle2D bounds = null;
		try {
			bounds = getMetrics().getBounds(
				getMusicalFont().getDecoration(m_decoration)
			);
		} catch (MissingGlyphException mge) {
			bounds = new Rectangle2D.Double(0, 0, 0, 0);
		}
		m_height = bounds.getHeight();
		m_width = bounds.getWidth();
	}

	/** Returns the height of this score element.
	 * @return The height of this score element. */
	public double getHeight() {
		return m_height;
	}
	
	/**
	 * in a genric way that enables positioning, sizing,
	 * rendering to be done generically
	 * <p>subclasses should override this method. 
	 * @return {@link ScoreMetrics#NOTATION_CONTEXT_NOTE}
	 */
	public int getNotationContext() {
		return ScoreMetrics.NOTATION_CONTEXT_NOTE;
	}

	/** Returns the width of this score element.
	 * @return The width of this score element. */
	public double getWidth() {
		return m_width;
	}

	/** Returns the tune's music element represented by this graphical score element.
	 * @return The tune's music element represented by this graphical score element. <TT>null</TT>
	 * if this graphical score element is not related to any music element.
	 * @see MusicElement  */
	public MusicElement getMusicElement() {
		return m_decoration;
	}

	protected byte getPosition() {
		return getPosition(m_decoration);
	}
	
	private boolean isInvertable() {
		byte pos = getPosition();
		if ((pos != ABOVE_STAFF) && (pos != UNDER_STAFF)
				&& (pos != ABOVE_NOTE) && (pos != UNDER_NOTE)) {
			char charDeco = getMusicalFont().getDecoration(
					m_decoration, false);
			char charDeco2 = getMusicalFont().getDecoration(
					m_decoration, true);
			return charDeco == charDeco2;
		} else {
			return false;
		}
	}
	
	protected boolean isInverted() {
		return m_isInverted;
	}
	
	protected void setInverted(boolean b) {
		m_isInverted = isInvertable()?b:false;
	}
	
	protected void setAttachedTo(JScoreElementAbstract scoreEl) {
		m_scoreElement = scoreEl;
	}

	/** Callback invoked when the base has changed for this object. */
	protected void onBaseChanged() {
		// does nothing
	}
	
	/** Renders this Score element to the given graphic context.
	 * @param g2 */
	public double render(Graphics2D g2) {
		try {
			char charDeco = getMusicalFont().getDecoration(
					m_decoration, m_isInverted);
			Rectangle2D bounds = getMetrics().getBounds(charDeco);
			double x = getBase().getX() - bounds.getWidth()/2;
			double y = getBase().getY();
			if (getPosition() == MIDDLE_STAFF)
				y = getBase().getY() + bounds.getHeight()/2;
			if (m_scoreElement != null) {
				boolean isUnderNote = y > m_scoreElement.getBase().getY();
				if (isUnderNote)
					y += bounds.getHeight();
				//avoid collisions
				double offset = getMetrics().getNoteHeight() / 2;
				
				//first decoration is on top
				for (int i = m_scoreElement.m_jDecorations.size()-1; i >= 0; i--) {
					//JDecoration jdec = (JDecoration) it.next();
					JDecoration jdec = (JDecoration) m_scoreElement.m_jDecorations.elementAt(i);
					if (jdec == this)
						break;
					if (jdec.getBase().equals(getBase())) {
						if (isUnderNote)
							y = y + offset + jdec.getHeight();
						else
							y = y - offset - jdec.getHeight();
					}
				}
			}
			//if (m_isInverted) y += bounds.getHeight();
			g2.drawChars(new char[] { charDeco }, 0, 1,
					(int)x, (int)y);
			return bounds.getWidth();
		} catch (MissingGlyphException mge) {
			return 0;
		}
	}

}
