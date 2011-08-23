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
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import abc.notation.MusicElement;

public interface JScoreElement {
	
	/** Returns the width of this score element.
	 * @return The width of this score element. */
	public double getWidth();
	
	/** Returns the staff line containing this score element. 
	 * @return The staff line containing this score element. */
	//public JStaffLine getStaffLine();
	
	/** Returns the music element represented by this graphical score element.
	 * @return The music element represented by this graphical score element. <TT>null</TT>
	 * if this graphical score element is not related to any music element. 
	 * @see MusicElement  */ 
	public abstract MusicElement getMusicElement();
	
	/** Returns the bounding box for this score element. 
	 * @return the bounding box for this score element. */
	public Rectangle2D getBoundingBox();
	
	/** Returns the score element whose bouding box contains the
	 * given location.
	 * @param location A location.
	 * @return The score element whose bouding box contains the
	 * given location. <TT>null</TT> is returned if no matching element 
	 * has been found. */ 
	public JScoreElement getScoreElementAt(Point location);
	
	/** Return the base of this element.
	 * @return The base of this element. The base is the point that is used 
	 * as a reference to draw the element at this location. */
	public Point2D getBase();
	
	/**
	 * Sets the color of the score element.<br>
	 * If element is already rendered, it's refreshed.
	 * @param c <code>null</code> to use the default color
	 */
	public void setColor(Color c);
	
	/**
	 * Stemmable element interface contains two methods :
	 * {@link #setAutoStem(boolean)} and
	 * {@link #setStemUp(boolean)}.
	 * 
	 * Known implementations are {@link JNoteElementAbstract}
	 * and {@link JGroupOfNotes}.
	 */
	public interface JStemmableElement {
		
		/**
		 * Returns if the stemmable element should follow
		 * stemming policy.
		 * 
		 * e.g. chord with multi-length should not follow policy
		 */
		public boolean isFollowingStemmingPolicy();
		
		/**
		 * Returns if the stem orientation is automatic or not
		 */
		public boolean isAutoStem();
		
		/**
		 * Sets if the stem orientation is automatic or not
		 * @param b
		 */
		public void setAutoStem(boolean b);
		
		/**
		 * Sets the stem orientation to up or down
		 * @param b
		 */
		public void setStemUp(boolean b);
		
		/**
		 * Returns <TT>true</TT> if stem is up, <TT>false</TT>
		 * if down.
		 */
		public boolean isStemUp();
		
	}
	
	/**
	 * Interface for {@link JGraceNote} and {@link JGroupOfGrace}
	 */
	public interface JGraceElement {
		
		public void setRenderSlash(boolean b);
		
	}

}
