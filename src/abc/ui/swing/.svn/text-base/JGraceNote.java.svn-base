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
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Point2D;

import abc.notation.Clef;
import abc.notation.Note;
import abc.ui.scoretemplates.ScoreElements;
import abc.ui.swing.JScoreElement.JGraceElement;

class JGraceNote extends JNote implements JGraceElement {

	protected boolean renderSlash = true;
	private Point2D slashStart = null;
	private Point2D slashEnd = null;
	
	private double m_width = -1;

	public JGraceNote(Note noteValue, Clef clef, Point2D base, ScoreMetrics c) {
		super(noteValue, clef, base, c);

//		super.setAutoStem(false);
//		super.setStemUp(true);

		slashStart = new Point2D.Double(0,0);
		slashEnd = new Point2D.Double(0,0);
	}


//	// no-op: gracenotes should always be stemmed up
//	public void autoStem(boolean auto) {
//	}
//
//	// no-op: gracenotes should always be stemmed up
//	public void setStemUp(boolean isUp) {
//	}

	public void setRenderSlash(boolean render) {
		renderSlash = render;
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
	
	public double getWidth() {
		return m_width; //suppose it has been calculated
	}

	protected void onBaseChanged() {
		super.onBaseChanged();
		this.m_width = super.getWidth();
		
		//grace note glyph dimension
		Dimension glyphDimension = getMetrics()
			.getGlyphDimension(getNotationContext());
		double width = glyphDimension.getWidth();
		
		// calculate slash position
		int startX = 0;
		int endX = 0;
		int startY = 0;
		int endY = 0;
		if (isStemUp()) {
			startX = (int) (displayPosition.getX() + width*0.5);
			endX = (int) (displayPosition.getX() + width*2.5);
			startY = (int) (displayPosition.getY() - width*2.5);
			endY = (int) (displayPosition.getY() - width*3.5);
		} else {
			startX = (int) (displayPosition.getX() - width*0.5);
			endX = (int) (displayPosition.getX() + width*1.5);
			startY = (int) (displayPosition.getY() + width*1.5);
			endY = (int) (displayPosition.getY() + width*2.5);
		}
		slashStart = new Point2D.Double(startX, startY);
		slashEnd = new Point2D.Double(endX, endY);

	}

	public double render(Graphics2D context){
		Font previousFont = context.getFont();
		try {
			context.setFont(
					getMetrics().getNotationFontForContext(ScoreMetrics.NOTATION_CONTEXT_GRACENOTE)
				);
			super.render(context);

// visual testing of base/offset/note positions
/*
Color previousColor = context.getColor();
context.setColor(Color.RED);
double nh = m_metrics.getStaffLineHeight();
double offset = getOffset(note);
context.drawRect((int)(m_base.getX()), (int)(m_base.getY()),1, 1);
context.setColor(Color.GREEN);
context.drawRect((int)(m_base.getX()), (int)(m_base.getY()+offset),1, 1);
context.setColor(previousColor);
*/

		} finally {
			context.setFont(previousFont);
		}

		if (renderSlash) {
			Color previousColor = context.getColor();
			setColor(context, ScoreElements.GRACENOTE);
			Stroke dfs = context.getStroke();
			context.setStroke(getMetrics().getStemStroke());
			context.drawLine((int)slashStart.getX(), (int)slashStart.getY(), (int)slashEnd.getX(), (int)slashEnd.getY());
			context.setStroke(dfs);
			context.setColor(previousColor);
		}
		
		//renderDebugBoundingBox(context);

		return m_width;
	}
}
