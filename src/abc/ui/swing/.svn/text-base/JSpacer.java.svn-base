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
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import abc.notation.MusicElement;
import abc.notation.Spacer;

/** This class is in charge of rendering a time signature. */
class JSpacer extends JScoreElementAbstract {
	
	private Spacer m_spacer = null;
	
	public JSpacer(ScoreMetrics c, Point2D base, Spacer s) {
		super(c);
		m_spacer = s;
		addDecorations(s);
		setBase(base);
	}
	
	public Rectangle2D getBoundingBox() {
		return new Rectangle2D.Double(
				getBase().getX(), getStaffLine().get5thLineY(),
				getWidth(), getStaffLine().getHeight()
				);
	}
	
	public double getWidth() {
		return getMetrics().getNoteWidth();
	}
	
	public MusicElement getMusicElement() {
		return m_spacer;
	}
	
	protected void onBaseChanged() {
		//decorations positions are calculated by renderDecorations
		//calcDecorationPosition();
	}
	
	public double render(Graphics2D context){
		super.render(context);
		renderDecorations(context);
		renderDynamic(context);
		renderChordName(context);
		renderAnnotations(context);
		//renderDebugBoundingBox(context);
		return getWidth();
	}
}

