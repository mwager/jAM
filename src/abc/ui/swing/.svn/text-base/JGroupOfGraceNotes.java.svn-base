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
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Point2D;

import abc.notation.Clef;
import abc.notation.Note;
import abc.notation.NoteAbstract;
import abc.ui.scoretemplates.ScoreElements;
import abc.ui.swing.JScoreElement.JGraceElement;

/** This class is in charge of rendering a group of grace notes whose stems should be linked. */
class JGroupOfGraceNotes extends JGroupOfNotes implements JGraceElement {

	protected boolean renderSlash = true;
	private Point2D slashStart = null;
	private Point2D slashEnd = null;

	public JGroupOfGraceNotes (ScoreMetrics metrics, Point2D base, NoteAbstract[] notes, Clef clef, Engraver engraver){
		super(metrics, base, notes, clef, engraver);

		JGraceNotePartOfGroup graceNote = null;
		for (int i=0; i<notes.length; i++) {
			graceNote = new JGraceNotePartOfGroup((Note)m_notes[i], clef, base, metrics);
			m_jNotes[i] = graceNote;
		}

		slashStart = new Point2D.Double(0,0);
		slashEnd = new Point2D.Double(0,0);

//		// force stem direction - gracenotes are always stemmed UP
//		setAutoStem(false);
//		setStemUp(true);
	}

	/**
	 * used to request glyph-specific metrics
	 * in a genric way that enables positioning, sizing, rendering
	 * to be done generically
	 * <p>subclasses should override this method.
	 * @return {@link ScoreMetrics#NOTATION_CONTEXT_GRACENOTE}
	 */
	protected int getNotationContext() {
		return ScoreMetrics.NOTATION_CONTEXT_GRACENOTE;
	}

	public void setRenderSlash(boolean render) {
		renderSlash = render;
	}

	protected void onBaseChanged() {
		super.onBaseChanged();

		// calculate slash position
		Dimension d = getMetrics().getGlyphDimension(getNotationContext());

		if (d == null) return;
		JGroupableNote firstNote = m_jNotes[0];
		Point2D displayPosition = ((JNote)firstNote).getDisplayPosition();
		boolean isUp = ((JNote)firstNote).isStemUp();
		int startX = 0;
		int endX = 0;
		int startY = 0;
		int endY = 0;
		if (isUp) {
			startX = (int) (displayPosition.getX() + d.getWidth()*0.5);
			endX = (int) (displayPosition.getX() + d.getWidth()*5.5);
			startY = (int) (firstNote.getStemYEnd() + d.getWidth()*2.5);
			endY = (int) (firstNote.getStemYEnd() - d.getWidth()*1.5);
		} else {
			startX = (int) (displayPosition.getX() - d.getWidth()*0.5);
			endX = (int) (displayPosition.getX() + d.getWidth()*4.5);
			startY = (int) (firstNote.getStemYEnd() - d.getWidth()*2.5);
			endY = (int) (firstNote.getStemYEnd() + d.getWidth()*1.5);
		}
		slashStart = new Point2D.Double(startX, startY);
		slashEnd = new Point2D.Double(endX, endY);
	}

	public double render(Graphics2D context) {
		Color previousColor = context.getColor();
		setColor(context, ScoreElements.GRACENOTE);
		super.render(context);

		if (renderSlash) {
			Stroke dfs = context.getStroke();
			context.setStroke(getMetrics().getStemStroke());
			context.drawLine((int)slashStart.getX(), (int)slashStart.getY(), (int)slashEnd.getX(), (int)slashEnd.getY());
			context.setStroke(dfs);
		}
		
		context.setColor(previousColor);
		//renderDebugBoundingBox(context);
		return getWidth();
	}
}
