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
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import abc.notation.Dynamic;
import abc.notation.MusicElement;
import abc.ui.scoretemplates.ScoreAttribute;
import abc.ui.scoretemplates.ScoreElements;

public class JDynamic extends JScoreElementAbstract {

	private Dynamic m_dynamic = null;

	private double m_height = -1;

	private JScoreElementAbstract m_scoreElement = null;

	private double m_width = -1;

	private double m_x = -1;

	private double m_y = -1;

	protected JDynamic(Dynamic dynamic, ScoreMetrics mtrx) {
		super(mtrx);
		m_dynamic = dynamic;
		Rectangle2D bounds = getMetrics().getBounds(
				getMusicalFont().getDynamic(m_dynamic.getType()));
		m_height = bounds.getHeight();
		m_width = bounds.getWidth();
	}

	public Rectangle2D getBoundingBox() {
		Rectangle2D bb = new Rectangle2D.Double(
				m_x, m_y-m_height, m_width, m_height);
		return bb;
	}

	public double getHeight() {
		return m_height;
	}

	public MusicElement getMusicElement() {
		return m_dynamic;
	}

	public double getWidth() {
		return m_width;
	}

	protected void onBaseChanged() {
		m_x = getBase().getX()
				- (m_width/2)
				+ getTemplate().getAttributeSize(
						ScoreAttribute.DYNAMIC_HORIZONTAL_OFFSET);
		m_y = m_scoreElement.getStaffLine().get1stLineY()
				+ (m_height/2)
				+ getTemplate().getAttributeSize(
						ScoreAttribute.DYNAMIC_VERTICAL_OFFSET);
	}

	public double render(Graphics2D g2) {
		super.render(g2);
		if (m_dynamic.getType() != Dynamic.UNKNOWN) {
			Color previousColor = g2.getColor();
			setColor(g2, ScoreElements.DYNAMIC);
			char[] chars = { getMusicalFont().getDynamic(m_dynamic.getType()) };
			g2.drawChars(chars, 0, chars.length, (int) m_x, (int) m_y);
			g2.setColor(previousColor);
		}
		//renderDebugBoundingBox(g2);
		return getWidth();
	}

	protected void setAttachedTo(JScoreElementAbstract scoreEl) {
		m_scoreElement = scoreEl;
		setBase(scoreEl.getBase());
	}
}
