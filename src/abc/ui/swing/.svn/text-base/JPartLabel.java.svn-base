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

import abc.notation.PartLabel;
import abc.ui.scoretemplates.ScoreElements;
import abc.ui.scoretemplates.TextJustification;

public class JPartLabel extends JText {
	
	/** Constructor
	 * @param mtrx The score metrics needed
	 */
	protected JPartLabel(ScoreMetrics mtrx, Point2D base, PartLabel partLabel) {
		super(mtrx, String.valueOf(partLabel.getLabel()),
				ScoreElements.PART_LABEL);
		setTextJustification(TextJustification.CENTER);
	}

	/** Returns the height of this score element.
	 * @return The height of this score element. */
	public double getHeight() {
		return getFrameSize();
	}

	/**
	 * Returns zero, no need to add a width to a label
	 * @return 0
	 */
	public double getWidth() {
		return 0;
	}
	
	/** Draw always a square frame around the label, get the largest glyph "M" */
	private double getFrameSize() {
		return 4 + (double)getMetrics().getTextFontWidth(ScoreElements.PART_LABEL, "M");
	}
	
	public Rectangle2D getBoundingBox() {
		double framesize = getFrameSize();
		double x = getBase().getX() + framesize/6;
		if (getTextJustification() == TextJustification.RIGHT)
			x -= framesize;
		else
			x -= framesize/2;
		double y = getBase().getY()
				- getMetrics().getStaffCharBounds().getHeight()
				- getMetrics().getNoteHeight()*2
				- framesize;
		return new Rectangle2D.Double(x, y, framesize, framesize);
	}
	
	/* (non-Javadoc)
	 * @see abc.ui.swing.JText#render(java.awt.Graphics2D)
	 */
	public double render(Graphics2D g2) {
		double labelSize = (double)getMetrics().getTextFontWidth(ScoreElements.PART_LABEL, getText());
		Font previousFont = g2.getFont();
		Color previousColor = g2.getColor();
		setColor(g2, ScoreElements.PART_LABEL);
		Rectangle2D bb = getBoundingBox();
		g2.setFont(getTemplate().getTextFont(ScoreElements.PART_LABEL));
		float descent = g2.getFont().getLineMetrics(getText(), g2.getFontRenderContext())
			.getDescent();
		g2.drawString(getText(), (int)(bb.getCenterX() - labelSize/2),
				(int)(bb.getY() + bb.getHeight() - descent));
		g2.setFont(previousFont);
		g2.draw(bb);
		g2.setColor(previousColor);
		return getWidth();
	}

}
