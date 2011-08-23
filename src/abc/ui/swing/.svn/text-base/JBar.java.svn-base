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

//import java.awt.Graphics;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import abc.notation.BarLine;
import abc.notation.MusicElement;
import abc.ui.scoretemplates.ScoreElements;

/** This class renders a simple bar line. */
class JBar extends JScoreElementAbstract {
	
	// dots in case of repeat only.
	private double m_barDotsSpacing = -1;

	/** The encapsulated abc notation bar element */
	protected BarLine m_barLine = null;

	private int m_bottomDotY = -1;

	private int m_dotsRadius = -1;

	// space between double bars [|, |:
	private double m_doubleBarSpacing = -1;

	private int m_thickBarWidth = -1;

	private int m_thinBarWidth = -1;

	private int m_topDotY = -1;

	public JBar(BarLine barLine, Point2D base, ScoreMetrics mtrx) {
		super(mtrx);
		m_barLine = barLine;
		addDecorations(barLine);
		setBase(base);
	}

	/**
	 * Returns the bounding box for this score element.
	 * 
	 * @return the bounding box for this score element.
	 */
	public Rectangle2D getBoundingBox() {
		Rectangle2D bb = new Rectangle2D.Double(getBase().getX(), getBase()
				.getY()
				- getHeight(), getWidth(), getHeight());
		return bb;
	}

	private int getHeight() {
		return (int) getMetrics().getStaffCharBounds().getHeight();
	}

	public MusicElement getMusicElement() {
		return m_barLine;
	}
	
	public double getWidth() {
		double width = 0;
		switch (m_barLine.getType()) {
		case BarLine.BEGIN_AND_END_REPEAT:
			width = m_thickBarWidth + 2 * (m_doubleBarSpacing + m_thinBarWidth - 1
					+ m_barDotsSpacing + Math.ceil(m_dotsRadius) - 1);
			break;
		case BarLine.REPEAT_OPEN:
		case BarLine.REPEAT_CLOSE:
			width = m_thickBarWidth + m_doubleBarSpacing + m_thinBarWidth - 1
					+ m_barDotsSpacing + Math.ceil(m_dotsRadius) - 1;
			break;
		case BarLine.BEGIN:
		case BarLine.END:
			width = m_thickBarWidth + m_doubleBarSpacing + m_thinBarWidth - 1;
			break;
		case BarLine.DOUBLE:
			width = m_doubleBarSpacing + 2 * m_thinBarWidth - 2;
			break;
		case BarLine.SIMPLE:
		case BarLine.INVISIBLE:
		case BarLine.DOTTED:
		default:
			width = m_thinBarWidth - 1;
			break;
		}
		return width;
	}

	protected void onBaseChanged() {
		int height = getHeight();
		m_topDotY = (int) (getBase().getY() - height * 0.61);
		m_bottomDotY = (int) (getBase().getY() - height * 0.4);
		
		double noteWidth = getMetrics().getNoteWidth();
		m_dotsRadius = (int) (noteWidth * 0.3);
		m_thickBarWidth = Math.max(2, (int) (noteWidth * 0.5));
		m_thinBarWidth = Math.max(1, (int) getMetrics().getBounds(
						getMusicalFont().getBarLine(BarLine.SIMPLE)
					).getWidth());
		m_barDotsSpacing = Math.max(1, noteWidth * 0.2);
		m_doubleBarSpacing = Math.max(2, noteWidth * 0.3);
	}

	public double render(Graphics2D context) {
		super.render(context);
		Color previousColor = context.getColor();
		setColor(context, ScoreElements.BAR_LINES);
		double x = getBase().getX();
		switch (m_barLine.getType()) {
		case BarLine.BEGIN_AND_END_REPEAT:
			renderDots(context, x);
			x += m_dotsRadius + m_barDotsSpacing;
			renderThinLine(context, x);
			x += m_thinBarWidth + m_doubleBarSpacing;
			renderThickLine(context, x);
			x += m_thickBarWidth + m_doubleBarSpacing;
			renderThinLine(context, x);
			x += m_thinBarWidth + m_barDotsSpacing;
			renderDots(context, x);
			break;
		case BarLine.REPEAT_OPEN:
			renderThickLine(context, x);
			renderThinLine(context, x + m_thickBarWidth + m_doubleBarSpacing);
			renderDots(context, x + m_thickBarWidth + m_doubleBarSpacing
					+ m_thinBarWidth + m_barDotsSpacing);
			break;
		case BarLine.REPEAT_CLOSE:
			renderDots(context, x);
			renderThinLine(context, x + m_dotsRadius + m_barDotsSpacing);
			renderThickLine(context, x + m_dotsRadius + m_barDotsSpacing
					+ m_thinBarWidth + m_doubleBarSpacing);
			break;
		case BarLine.BEGIN:
			renderThickLine(context, x);
			renderThinLine(context, x + m_thickBarWidth + m_doubleBarSpacing);
			break;
		case BarLine.END:
			renderThinLine(context, x);
			renderThickLine(context, x + m_thinBarWidth + m_doubleBarSpacing);
			break;
		case BarLine.DOUBLE:
			renderThinLine(context, x);
			renderThinLine(context, x + m_doubleBarSpacing);
			break;
		case BarLine.INVISIBLE: break;
		case BarLine.DOTTED:
			renderDottedLine(context, x);
			break;
		case BarLine.SIMPLE:
		default:
			renderThinLine(context, x);
			break;
		}
		context.setColor(previousColor);
		renderDecorations(context);
		renderDynamic(context);
		renderAnnotations(context);
		//renderDebugBoundingBoxOuter(context);
		return getWidth();
	}

	private void renderDots(Graphics2D context, double x) {
		context.fillOval((int) x, m_topDotY, m_dotsRadius, m_dotsRadius);
		context.fillOval((int) x, m_bottomDotY, m_dotsRadius, m_dotsRadius);
	}

	private void renderThickLine(Graphics2D context, double x) {
		int height = getHeight();
		context.fillRect((int) x, (int) (getBase().getY() - height),
				m_thickBarWidth, height);
	}

	private void renderDottedLine(Graphics2D context, double x) {
		int height = getHeight();
		int yStart = (int) (getBase().getY() - height);
		int yEnd = (int) getBase().getY();
		double yUnit = height / 23;
		if (yUnit < 1) yUnit = 1;
		int y = yStart;
		while (y <= yEnd - (yUnit * 3)) {
			context.fillRect((int) x, y,
					m_thinBarWidth, (int) (yUnit * 3));
			y += (int) (yUnit * 5);
		}
	}
	
	private void renderThinLine(Graphics2D context, double x) {
//		context.drawChars(new char[] {
//				getMusicalFont().getBarLine(BarLine.SIMPLE)
//			}, 0, 1, (int) x, (int) (getBase().getY()));

		int height = getHeight();
		context.fillRect((int) x, (int) (getBase().getY() - height),
				m_thinBarWidth, height);
	}
}
