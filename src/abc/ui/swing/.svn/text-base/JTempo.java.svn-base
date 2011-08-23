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

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import abc.notation.MusicElement;
import abc.notation.Note;
import abc.notation.Tempo;
import abc.ui.scoretemplates.ScoreElements;

public class JTempo extends JText {
	
	private Tempo m_tempo = null;
	private char[] m_refNote;
	private boolean m_dotted;
	private String m_number;
	private Rectangle2D m_refNoteBounds;
	private Rectangle2D m_noteHeadBounds;
	private Rectangle2D m_numberBounds;
	private double m_y = -1;
	
	/** Constructor
	 * @param mtrx The score metrics needed
	 */
	protected JTempo(ScoreMetrics mtrx, Point2D base, Tempo tempo) {
		super(mtrx, "", ScoreElements.TEMPO);
		m_tempo = tempo;
		m_dotted = !Note.isStrictDuration(m_tempo.getReferenceLength());
		short refLength = m_tempo.getReferenceLength();
		if (m_dotted) {
			refLength = Note.getStrictDuration(refLength);
			m_refNote = new char[] {
				getMusicalFont().getNoteStemUpChar(refLength),
				getMusicalFont().getDot() };
		} else {
			m_refNote = new char[] { getMusicalFont().getNoteStemUpChar(refLength) };
		}
		m_refNoteBounds = getMetrics().getBounds(m_refNote,
				ScoreMetrics.NOTATION_CONTEXT_TEMPO);
		m_noteHeadBounds = getMetrics().getBounds(
				getMusicalFont().getNoteWithoutStem(Note.QUARTER),
				ScoreMetrics.NOTATION_CONTEXT_TEMPO);
		m_number = new String(getMusicalFont().getTimeSignatureDigits(m_tempo.getNotesNumberPerMinute()));
		m_numberBounds = getMetrics().getBounds(m_number.toCharArray(),
				ScoreMetrics.NOTATION_CONTEXT_TEMPO);
	}
	
	public MusicElement getMusicElement() {
		return m_tempo;
	}

	public Rectangle2D getBoundingBox() {
		return new Rectangle2D.Double(getBase().getX(),
				m_y,
				getTextWidth(),
				getHeight());
	}
	
	/** Returns the height of this score element.
	 * @return The height of this score element. */
	public double getHeight() {
		//if ref note is a whole, number are higher than note
		return
			m_noteHeadBounds.getHeight()
			+ Math.max(m_refNoteBounds.getHeight(),
					m_numberBounds.getHeight());
	}

	/**
	 * Returns zero, no need to add a width to a label
	 * @return 0
	 */
	public double getWidth() {
		return 0;
	}
	
	private double getTextWidth() {
		return m_refNoteBounds.getWidth()
				+ m_numberBounds.getWidth()
			+ (m_noteHeadBounds.getWidth() * 3);
	}
	
	public void onBaseChanged() {
		if (getStaffLine() != null) {
			m_y = getStaffLine().getTopY() - m_noteHeadBounds.getHeight();
			//TODO add more space on top of the staff line
		} else
			m_y = getBase().getY();
	}
		
	/* (non-Javadoc)
	 * @see abc.ui.swing.JText#render(java.awt.Graphics2D)
	 */
	public double render(Graphics2D g2) {
		double noteHeadWidth = m_noteHeadBounds.getWidth();
		double noteHeadHeight = m_noteHeadBounds.getHeight();
		Font previousFont = g2.getFont();
		Color previousColor = g2.getColor();
		g2.setFont(getMetrics()
				.getNotationFontForContext(ScoreMetrics.NOTATION_CONTEXT_TEMPO));
		setColor(g2, ScoreElements.TEMPO);
		g2.drawString(String.valueOf(m_refNote),
				(int)(getBase().getX()),
				(int)(m_y+noteHeadHeight));
		g2.drawString(m_number,
				(int)(getBase().getX() + m_refNoteBounds.getWidth() +
						3*noteHeadWidth),
				(int)(m_y + noteHeadHeight - m_numberBounds.getHeight()/2));
		//render the equals sign
		int xEquals = (int)(getBase().getX() + m_refNoteBounds.getWidth()
				+ noteHeadWidth);
		int yEquals = (int)(m_y + noteHeadHeight/2);
		g2.drawLine(xEquals, yEquals,
				(int)(xEquals+noteHeadWidth), yEquals);
		yEquals = (int)(yEquals - noteHeadHeight/2);
		g2.drawLine(xEquals, yEquals,
				(int)(xEquals+noteHeadWidth), yEquals);
		g2.setFont(previousFont);
		g2.setColor(previousColor);
		return getWidth();
	}

}
