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

import java.awt.geom.Point2D;

import abc.notation.MusicElement;
import abc.notation.TwoNotesLink;

public class JSlurOrTie extends JScoreElementAbstract {
	
	/** Link position will be automatically computed */
	public static final short POSITION_AUTO = 0;
	/** Link position is forced down */
	public static final short POSITION_UNDER = 1;
	/** Link position is forced up */
	public static final short POSITION_ABOVE = 2;
	
	private short position = POSITION_AUTO;
	/** Does it care about stems to draw the link out of them ? */
	private boolean outOfStems = false;
	/** Is it a slur for a tuplet ? */
	private boolean isTuplet = false;
	
	/** Yes I know, we should create a JNotesLink, but for
	 * now, here is the easiest place to store the control point
	 * for a tuplet, just above the tuplet number.
	 */
	private Point2D tupletControlPoint = null;
	
	private TwoNotesLink m_slurDef = null;
	
	/**
	 * @param tnl
	 * @param c
	 */
	protected JSlurOrTie(TwoNotesLink tnl, ScoreMetrics c) {
		super(c);
		m_slurDef = tnl;
	}
	
	/** Returns the position of the link */
	public short getPosition() {
		return position;
	}
	
	public TwoNotesLink getSlurDefinition() {
		return m_slurDef;
	}
	
	/** Sets the position of the link
	 * @param pos The position :
	 * <ul><li>{@link #POSITION_AUTO} by default, position will
	 * be calculated
	 * <li>{@link #POSITION_UNDER}
	 * <li>{@link #POSITION_ABOVE}</ul> 
	 */
	public void setPosition(short pos) {
		position = pos;
	}
	
	public boolean isAbove() {
		return getPosition() == POSITION_ABOVE;
	}
	public boolean isUnder() {
		return getPosition() == POSITION_UNDER;
	}

	/** Does it care about stems to draw the link out of them ? */
	public boolean isOutOfStems() {
		return outOfStems;
	}

	/**
	 * Does it care about stems to draw the link out of them ?
	 * <br>false by default
	 * @param b The outOfStems to set.
	 */
	public void setOutOfStems(boolean b) {
		outOfStems = b;
	}

	/**
	 * Is it a slur for a tuplet
	 */
	public boolean isTuplet() {
		return isTuplet;
	}

	/**
	 * @param b
	 */
	public void setTuplet(boolean b) {
		isTuplet = b;
	}
	
	public void setTupletControlPoint(Point2D p) {
		tupletControlPoint = p;
	}
	public Point2D getTupletControlPoint() {
		return tupletControlPoint;
	}
	
	/* (non-Javadoc)
	 * @see abc.ui.swing.JScoreElementAbstract#getMusicElement()
	 */
	public MusicElement getMusicElement() {
		return null;
	}
	
	/** Not used, returns -1 */
	public double getWidth() {
		return -1; //not used
	}

	/* (non-Javadoc)
	 * @see abc.ui.swing.JScoreElementAbstract#onBaseChanged()
	 */
	protected void onBaseChanged() {
		//No need here, the JNote and childs .onBaseChange
		//do the job
	}

}
