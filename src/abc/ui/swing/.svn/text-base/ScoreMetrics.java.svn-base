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

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Map;

import abc.notation.Note;
import abc.ui.fonts.MusicalFont;
import abc.ui.scoretemplates.DefaultScoreTemplate;
import abc.ui.scoretemplates.ScoreAttribute;
import abc.ui.scoretemplates.SizeUnit;

/**
 * This class encapsulates all requiered dimensions calculations
 * needed to draw correctly a score (notes spacing, glyph bounds...).
 * All those values are calculated from score template settings.
 */
public class ScoreMetrics {

	protected static final int NOTATION_CONTEXT_GRACENOTE = 200;
	protected static final int NOTATION_CONTEXT_NOTE = 100;
	protected static final int NOTATION_CONTEXT_TEMPO = 150;

	/**
	 * Map to store the calculated bounds of various glyphes
	 * 
	 * Avoid numerous <TT>*Bounds</TT> members for this class
	 */
	private Map bounds = new HashMap();

	private Graphics2D g2 = null;

	private ScoreTemplate m_template = null;

	/**
	 * Build a ScoreMetrics from a {@link ScoreTemplate}
	 * @param g2d
	 * @param template
	 */
	protected ScoreMetrics(Graphics2D g2d, ScoreTemplate template) {
		g2 = g2d;
		m_template = template;
		m_template.setMetrics(this);
	}

	/**
	 * Get the bounds of a glyph in the default notation context.
	 * 
	 * @param glyph
	 */
	protected Rectangle2D getBounds(char glyph) {
		return getBounds(new char[] { glyph }, NOTATION_CONTEXT_NOTE);
	}
	
	/**
	 * Get the bounds of a glyph in the default notation
	 * context.
	 * @param glyph
	 * @param notationContext {@link #NOTATION_CONTEXT_NOTE} or {@link #NOTATION_CONTEXT_GRACENOTE}
	 */
	protected Rectangle2D getBounds(char glyph, int notationContext) {
		return getBounds(new char[]{glyph}, notationContext);
	}
	
	/**
	 * Get the bounds of a glyph in the default notation
	 * context.
	 * @param glyph {@link #SHARP}, {@link #FLAT}...
	 */
	protected Rectangle2D getBounds(char[] glyph) {
		return getBounds(glyph, NOTATION_CONTEXT_NOTE);
	}
	
	/**
	 * Get the bounds of a glyph in the given notation context
	 * @param glyph {@link #SHARP}, {@link #FLAT}...
	 * @param notationContext {@link #NOTATION_CONTEXT_NOTE} or {@link #NOTATION_CONTEXT_GRACENOTE}
	 */
	protected Rectangle2D getBounds(char[] glyph, int notationContext) {
		String key = String.valueOf(notationContext)
			+ "-" + String.valueOf(glyph)
			+ "-" + getMusicalFont().getName();
		if (bounds.get(key) == null) {
			FontRenderContext frc = g2.getFontRenderContext();
			bounds.put(key, new TextLayout(
					String.valueOf(glyph),//new Character(glyph[0]).toString(),
					getNotationFontForContext(notationContext),
					frc).getBounds());
		}
		return (Rectangle2D) (bounds.get(key));
	}
	
	/**
	 *
	 * @param notationContext {@link #NOTATION_CONTEXT_NOTE}, {@link #NOTATION_CONTEXT_GRACENOTE}
	 * @return
	 */
	protected Dimension getGlyphDimension(int notationContext) {
		Rectangle2D bounds = getBounds(
				new char[] {getMusicalFont().getNoteWithoutStem(Note.QUARTER)},
				notationContext);
		return new Dimension((int)(bounds.getHeight()),
				(int)(bounds.getWidth()));
	}

	/* *** graceNotes support *** */
	protected double getGraceNoteHeight() {
		return getBounds(new char[] {getMusicalFont().getNoteWithoutStem(Note.QUARTER)},
				NOTATION_CONTEXT_GRACENOTE).getHeight();
	}

	protected BasicStroke getGraceNotesLinkStroke() {
		return getNotesLinkStrokeForContext(NOTATION_CONTEXT_GRACENOTE);
	}

	/**
	 * Returns the spacing between graceNotes.
	 * 
	 * @return The spacing between graceNotes, expressed in pixels.
	 */
	protected double getGraceNotesSpacing() {
		return getNotesSpacingForContext(NOTATION_CONTEXT_GRACENOTE);
	}
	
	protected double getGraceNoteWidth() {
		return getBounds(new char[]{getMusicalFont().getNoteWithoutStem(Note.QUARTER)},
				NOTATION_CONTEXT_GRACENOTE).getWidth();
	}

	/** Returns the musical font definition */
	private MusicalFont getMusicalFont() {
		return getTemplate().getMusicalFont();
	}
	
	/**
	 * Return the notation for from the given notation context
	 * 
	 * @param notationContext
	 *            {@link #NOTATION_CONTEXT_NOTE},
	 *            {@link #NOTATION_CONTEXT_GRACENOTE}...
	 */
	public Font getNotationFontForContext(int notationContext) {
		try {
			Font baseNotationFont = ((MusicalFont) getTemplate().getAttributeObject(
					ScoreAttribute.NOTATION_FONT)).getFont();

			switch (notationContext) {
			case NOTATION_CONTEXT_GRACENOTE:
				return baseNotationFont.deriveFont(getTemplate()
						.getAttributeSize(
								ScoreAttribute.NOTATION_GRACENOTE_SIZE));
			case NOTATION_CONTEXT_TEMPO:
				return baseNotationFont.deriveFont(getTemplate()
						.getAttributeSize(ScoreAttribute.NOTATION_TEMPO_SIZE));
			case NOTATION_CONTEXT_NOTE:
			default:
				return baseNotationFont.deriveFont(getTemplate()
						.getAttributeSize(ScoreAttribute.NOTATION_SIZE));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public float getNotationFontSize() {
		return getTemplate().getAttributeSize(ScoreAttribute.NOTATION_SIZE);
	}

	public double getNoteHeight() {
		return getBounds(new char[]{getMusicalFont().getNoteWithoutStem(Note.QUARTER)},
				NOTATION_CONTEXT_NOTE).getHeight();
	}

	/** @deprecated {@link getNoteHeight()} */
	public double getNoteHeigth() {
		return getNoteHeight();
	}

	protected BasicStroke getNotesLinkStroke() {
		return getNotesLinkStrokeForContext(NOTATION_CONTEXT_NOTE);
	}
	
	protected BasicStroke getNotesLinkStrokeForContext(int context) {
		switch (context) {
		case NOTATION_CONTEXT_GRACENOTE:
			return new BasicStroke(getTemplate().getAttributeSize(
					ScoreAttribute.GRACENOTE_LINK_STROKE), 0, 0);
		case NOTATION_CONTEXT_TEMPO:
		case NOTATION_CONTEXT_NOTE:
		default:
			return new BasicStroke(getTemplate().getAttributeSize(
					ScoreAttribute.NOTE_LINK_STROKE), 0, 0);
		}
	}

	/**
	 * Returns the spacing between notes. Note that the engraver can change the
	 * spacings, this one is the default spacing, used as reference for
	 * engraver.
	 * 
	 * @return The spacing between notes, expressed in pixels.
	 */
	protected double getNotesSpacing() {
		return getNotesSpacingForContext(NOTATION_CONTEXT_NOTE);
	}
	
	protected double getNotesSpacingForContext(int context) {
		switch(context) {
		case NOTATION_CONTEXT_GRACENOTE:
			return getTemplate().getAttributeSize(ScoreAttribute.GRACENOTE_SPACING);
		case NOTATION_CONTEXT_TEMPO:
		case NOTATION_CONTEXT_NOTE:
		default:
			return getTemplate().getAttributeSize(ScoreAttribute.NOTE_SPACING);
		}
	}

	public double getNoteWidth() {
		return getBounds(
				new char[] { getMusicalFont().getNoteWithoutStem(Note.QUARTER) },
				NOTATION_CONTEXT_NOTE).getWidth();
	}
	
	/**
	 * Returns the vertical offset between note head and slur point
	 */
	protected double getSlurAnchorYOffset() {
		return getTemplate().getAttributeSize(
				ScoreAttribute.SLUR_ANCHOR_Y_OFFSET);
	}

	/**
	 * Returns the bounding box of a staff line character.
	 * 
	 * @return Returns the bounding box of a staff line character.
	 */
	protected Rectangle2D getStaffCharBounds() {
		return getBounds(new char[] { getMusicalFont().getStaffFiveLines() },
				NOTATION_CONTEXT_NOTE);
	}
	
	/**
	 * 
	 * @param notationContext
	 *            {@link #NOTATION_CONTEXT_NOTE},
	 *            {@link #NOTATION_CONTEXT_GRACENOTE}
	 * @return
	 */
	protected int getStemLengthForContext(int notationContext) {
		switch (notationContext) {
		case NOTATION_CONTEXT_NOTE:
			return (int) getTemplate().getAttributeSize(
					ScoreAttribute.NOTE_STEM_LENGTH);
		case NOTATION_CONTEXT_GRACENOTE:
			return (int) getTemplate().getAttributeSize(
					ScoreAttribute.GRACENOTE_STEM_LENGTH);
		default:
			return 0;
		}
	}
	
	protected BasicStroke getStemStroke() {
		return new BasicStroke(Math.max(1f, getTemplate().getAttributeSize(
				ScoreAttribute.STEM_STROKE)));
	}
	
	protected ScoreTemplate getTemplate() {
		if (m_template == null)
			m_template = new DefaultScoreTemplate();
		return m_template;
	}
	
	/**
	 * Returns a text font (for lyrics, chord names, title...)
	 * 
	 * @param textType
	 *            one of {@link abc.ui.scoretemplates.ScoreElements} constants
	 * @return a Font object
	 */
	protected Font getTextFont(byte textType) {
		try {
			return getTemplate().getTextFont(textType);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Returns height of one line rendered in the textType font.
	 * 
	 * @param textType
	 *            one of {@link abc.ui.scoretemplates.ScoreElements} constants
	 * @return the height of a string in this font used for this score metrics
	 */
	protected int getTextFontHeight(byte textType) {
		FontMetrics fontMetrics = g2.getFontMetrics(getTextFont(textType));
		return fontMetrics.getHeight();
	}

	/**
	 * Return the base text font size<br>
	 * Titles, subtitles... sizes are derived from it.
	 */
	public float getTextFontSize() {
		return getTemplate().getDefaultTextSize();
	}

	/**
	 * Returns width of string rendered in the textType font.
	 * 
	 * @param textType
	 *            one of {@link abc.ui.scoretemplates.ScoreElements} constants
	 * @param text
	 *            The text we want to know its width
	 * @return the width of a string in this font used for this score metrics
	 */
	protected int getTextFontWidth(byte textType, String text) {
		if (text == null)
			return 0;
		FontMetrics fontMetrics = g2.getFontMetrics(getTextFont(textType));
		return fontMetrics.stringWidth(text);
	}
	
	/**
	 * Clear the stored bounds and other things that may have
	 * changed when template options are changed.<br>
	 * Call this method on {@link ScoreTemplateChangeListener#onTemplateChange()}
	 * implementation.
	 */
	protected void reload() {
		bounds.clear();
	}

	/**
	 * Define the font size, then all (or most of) elements of the score.
	 * 
	 * It's the same than
	 * {@link ScoreTemplate#setAttributeSize(ScoreAttribute, float)} for
	 * {@link ScoreAttribute#NOTATION_SIZE}.
	 * 
	 * @param size
	 */
	public void setNotationFontSize(float notationSize) {
		getTemplate().setAttributeSize(ScoreAttribute.NOTATION_SIZE,
				notationSize, SizeUnit.PT);
	}

	void setTemplate(ScoreTemplate st) {
		m_template = st;
	}
	
	/**
	 * Sets the base text font size<br>
	 * Titles, subtitles... sizes are derived from it.
	 * 
	 * It's the same than
	 * {@link ScoreTemplate#setAttributeSize(ScoreAttribute, float)} for
	 * {@link ScoreAttribute#TEXT_DEFAULT_SIZE}.
	 * 
	 * @param textSize
	 */
	public void setTextFontSize(float textSize) {
		getTemplate().setDefaultTextSize(textSize);
	}

}