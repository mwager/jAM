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
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import abc.notation.Annotation;
import abc.notation.Chord;
import abc.notation.DecorableElement;
import abc.notation.Decoration;
import abc.notation.MusicElement;
import abc.notation.Tune;
import abc.ui.fonts.MusicalFont;
import abc.ui.scoretemplates.HorizontalPosition;
import abc.ui.scoretemplates.ScoreAttribute;
import abc.ui.scoretemplates.ScoreElements;

/** This class defines a score rendition element. Rendition scores elements
 * are graphical representations of tune score elements objects retrieved
 * from a tune object.
 * A <TT>JScoreElement</TT> can itself contain <TT>JScoreElement</TT> instances.
 * You should figure out the graphical score representation as a tree of
 * <TT>JScoreElement</TT>. (Composite)
 * @see Tune#getMusic()
 */
abstract class JScoreElementAbstract implements JScoreElement {
	/** The metrics to be used to calculate this rendition element */
	private ScoreMetrics m_metrics = null;
	/**
	 * The reference point (bottom, left) that should be used when rendering
	 * this element.
	 *
	 * Use {@link #getBase()} which is sometimes extended
	 */
	private Point2D m_base = null;
	
	/** vector of JDecoration instances for this element. */
	protected Vector m_jDecorations = null;
	
	/** Dynamic for this element */
	protected JDynamic m_jDynamic = null;
	
	/**
	 * Array of anchors (Point2D) for decorations
	 * <TT>m_decorationAnchors[JDecoration.ABOVE_STAFF]</TT>...
	 */
	protected Point2D[] m_decorationAnchors = new Point2D[JDecoration.POSITIONS_COUNT];

	/** Teh bounding box (more or less humm....) that encapsulates
	 * this graphical score element. */
	protected Rectangle2D m_boundingBox = null;

	/** The staff line that contains this score element. */
	protected JStaffLine staffLine = null;
	
	private boolean m_isRendered = false;
	
	private Color m_color = null;
	
	private transient Graphics2D g2d = null;

	/** Constructor
	 * @param base The base location
	 * @param mtrx The score metrics needed
	 */
	protected JScoreElementAbstract(Point2D base, ScoreMetrics mtrx) {
		m_base = base;
		m_metrics = mtrx;
	}

	/** Constructor
	 * @param mtrx The score metrics needed
	 */
	protected JScoreElementAbstract(ScoreMetrics mtrx) {
		m_metrics = mtrx;
	}

	/** Returns the width of this score element.
	 * @return The width of this score element. */
	public abstract double getWidth();
	
	protected ScoreMetrics getMetrics() {
		return m_metrics;
	}
	/** Returns the musical font mapping class */
	protected MusicalFont getMusicalFont() {
		return getTemplate().getMusicalFont();
	}
	protected ScoreTemplate getTemplate() {
		return getMetrics().getTemplate();
	}

	/** Returns the staff line containing this score element.
	 * @return The staff line containing this score element. */
	public JStaffLine getStaffLine() {
		return staffLine;
	}

	public void setStaffLine(JStaffLine staffLine) {
		this.staffLine = staffLine;
	}

	/** Returns the tune's music element represented by this graphical score element.
	 * @return The tune's music element represented by this graphical score element. <TT>null</TT>
	 * if this graphical score element is not related to any music element.
	 * @see MusicElement  */
	public abstract MusicElement getMusicElement();

	/** Returns the bounding box for this score element.
	 * @return the bounding box for this score element. */
	public Rectangle2D getBoundingBox() {
		Rectangle2D bb = new Rectangle2D.Double(
				m_base.getX(),
				m_base.getY()-50,
				getWidth(),
				50);
		return bb;
	}

	/** Returns the score element whose bouding box contains the
	 * given location.
	 * @param location A location.
	 * @return The score element whose bouding box contains the
	 * given location. <TT>this</TT> can be returned or one of the
	 * sub <TT>JScoreElement</TT> contained in this one. <TT>null</TT>
	 * is returned if no matching element has been found. */
	public JScoreElement getScoreElementAt(Point location) {
		if (location == null)
			return null;
		if (getBoundingBox().contains(location))
			return this;
		else
			return null;
	}

	/** Return the base of this element.
	 * @return The base of this element. The base is the point that is used
	 * as a reference to draw the element at this location.
	 * @see #setBase(Point2D) */
	public Point2D getBase() {
		return m_base;
	}

	/** Sets the base of this element.
	 * @param base The new bas that should be used to draw this element.
	 * @see #getBase() */
	public void setBase(Point2D base) {
		m_base = (Point2D)base.clone();
		onBaseChanged();
	}
	
	protected void addDecorations(DecorableElement decorable) {
		if (decorable.hasDecorations()) {
			Decoration[] decorations = decorable.getDecorations();
			for (int i = 0; i < decorations.length; i++) {
				if (decorations[i] != null) {
					addDecoration(
						new JDecoration(
							decorations[i], getMetrics()));
				}
			}
		}
	}
	
	/** Add decoration if it hasn't been added previously */
	protected void addDecoration (JDecoration decoration) {
		if (m_jDecorations == null) {
			m_jDecorations = new Vector();
		}
		if (!m_jDecorations.contains(decoration)) {
			m_jDecorations.add(decoration);
		}
	}
	
	protected void calcDecorationPosition() {
		if (m_jDecorations != null && m_jDecorations.size() > 0){
			double noteHeight = getMetrics().getNoteHeight();
			double x = getBase().getX() + getWidth() / 2;
			double bottom = getStaffLine().get1stLineY() + noteHeight;
			double top = getStaffLine().get5thLineY() - noteHeight;
			double middle = bottom + (top-bottom)/2;
			double left = x - getWidth();
			double right = x + getWidth();
			m_decorationAnchors[JDecoration.ABOVE_NOTE]
				= m_decorationAnchors[JDecoration.ABOVE_STAFF]
				= m_decorationAnchors[JDecoration.ABOVE_STAFF_AFTER_NOTE]
				= m_decorationAnchors[JDecoration.STEM_END]
				= m_decorationAnchors[JDecoration.STEM_END_OUT_OF_STAFF]
				= m_decorationAnchors[JDecoration.VERTICAL_NEAR_STEM]
				= m_decorationAnchors[JDecoration.VERTICAL_NEAR_STEM_OUT_STAFF]
				= new Point2D.Double(x, top);
			m_decorationAnchors[JDecoration.UNDER_NOTE]
				= m_decorationAnchors[JDecoration.UNDER_STAFF]
				= m_decorationAnchors[JDecoration.VERTICAL_AWAY_STEM]
				= m_decorationAnchors[JDecoration.VERTICAL_AWAY_STEM_OUT_STAFF]
				= new Point2D.Double(x, bottom);
			m_decorationAnchors[JDecoration.HORIZONTAL_AWAY_STEM]
				= m_decorationAnchors[JDecoration.HORIZONTAL_NEAR_STEM]
				= m_decorationAnchors[JDecoration.STEM_MIDDLE]
				= m_decorationAnchors[JDecoration.MIDDLE_STAFF]
				= new Point2D.Double(x, middle);
			m_decorationAnchors[JDecoration.LEFT_NOTE]
				= new Point2D.Double(left, middle);
			m_decorationAnchors[JDecoration.RIGHT_NOTE]
				= new Point2D.Double(right, middle);
		}
	}

	/** Sets the color used for the rendition of this score element.
	 * @param color The color used for the rendition of this score element. */
	/*public void setColor(Color color) {
		m_color = color;
	}*/

	/** Callback invoked when the base has changed for this object. */
	protected abstract void onBaseChanged();

	/**
	 * Set the color for renderer, get color value in the score
	 * template, or apply the specified color for the current
	 * element.
	 * @param g2
	 * @param scoreElement
	 */
	protected void setColor(Graphics2D g2, byte scoreElement) {
		if (m_color != null)
			g2.setColor(m_color);
		else {
			Color c = getTemplate().getElementColor(scoreElement);
			Color d = getTemplate().getElementColor(ScoreElements._DEFAULT);
			if ((c != null) && !c.equals(d))
				g2.setColor(c);
		}
	}
	
	/** Renders this Score element to the given graphic context.
	 * @param g2 */
	public double render(Graphics2D g2) {
		setRendered(true);
		g2d = g2;
		return getWidth();
	}
	
	protected boolean isRendered() {
		return m_isRendered && (g2d != null);
	}
	protected void setRendered(boolean b) {
		m_isRendered = b;
	}
	/**
	 * Sets the color of the score element.<br>
	 * If element is already rendered, it's refreshed.
	 * @param c <code>null</code> to use the default color
	 */
	public void setColor(Color c) {
		boolean renderAgain = (c != m_color) && isRendered();
		m_color = c;
		if (renderAgain)
			render(g2d);
	}
	protected Color getColor() {
		return m_color;
	}
	
	protected void renderAnnotations(Graphics2D gfx) {
		MusicElement me = getMusicElement();
		Collection annots = null;
		if (me instanceof DecorableElement)
			annots = ((DecorableElement) me).getAnnotations();
		if (annots != null) {
			Iterator it = annots.iterator();
			while (it.hasNext()) {
				Annotation annot = (Annotation) it.next();
				JAnnotation jannot = new JAnnotation(getMetrics(),
					annot.getText());
				jannot.setBase(getBase());
				jannot.setAttachedTo(this);
				jannot.render(gfx);
			}
		}
	}
	
	protected void renderChordName(Graphics2D gfx) {
		MusicElement me = getMusicElement();
		Chord chord = null;
		if (me instanceof DecorableElement)
			chord = ((DecorableElement) me).getChord();
		if (chord != null) {
			Point2D displayPos = null;
			if (this instanceof JNote)
				displayPos = ((JNote) this).getDisplayPosition();
			else if (this instanceof JChord)
				displayPos = ((JChord) this).getHighestNote().getDisplayPosition();
			else if (this instanceof JSpacer)
				displayPos = getBase();

			JChordName chordName = new JChordName(getMetrics(), chord);
			Dimension dimension = chordName.getDimension();
			double y = getStaffLine().getBase().getY()/* not yet defined*/
				//- (displayPosition.getY()%m_metrics.getStaffLinesSpacing())
				- getMetrics().getStaffCharBounds().getHeight()
				- getTemplate().getAttributeSize(ScoreAttribute.CHORD_LINE_SPACING)
				+ dimension.getHeight();
			double x = displayPos.getX();
			byte[] pos = getTemplate().getPosition(ScoreElements.TEXT_CHORDS);
			if (pos != null) {
				switch (pos[1]) {
				case HorizontalPosition.RIGHT:
					x -= dimension.getWidth(); break;
				case HorizontalPosition.CENTER:
					x -= dimension.getWidth()/2; break;
				case HorizontalPosition.LEFT:
				default:
					break;
				}
				chordName.setBase(new Point2D.Double(x, y));
				chordName.render(gfx);	
			}
		}
	}
	
	protected void renderDynamic(Graphics2D context) {
		if (m_jDynamic != null) {
			m_jDynamic.setAttachedTo(this);
			m_jDynamic.render(context);
		}
	}

	protected void renderDecorations(Graphics2D context){
		if (m_jDecorations != null && m_jDecorations.size() > 0) {
			Color previousColor = context.getColor();
			setColor(context, ScoreElements.DECORATION);
			//if (m_decorationAnchors[JDecoration.ABOVE_STAFF] == null)
				calcDecorationPosition();
			//first decoration is on top
			JDecoration jDeco = null;
			for (int i = m_jDecorations.size()-1; i >= 0; i--) {
				jDeco = (JDecoration)m_jDecorations.elementAt(i);
				boolean inverted = (this instanceof JStemmableElement)
					&& !((JStemmableElement) this).isStemUp();
				jDeco.setInverted(inverted);
				jDeco.setAttachedTo(this);
				jDeco.setBase(m_decorationAnchors[jDeco.getPosition()]);
				jDeco.render(context);
 			}
			context.setColor(previousColor);
 		}
	}
	
	/**
	 * For debugging purpose, draw the bounding box of
	 * the element
	 * @param context
	 */
	protected void renderDebugBoundingBox(Graphics2D context) {
		/* */java.awt.Color previousColor = context.getColor();
		context.setColor(java.awt.Color.RED);
		context.draw(getBoundingBox());
		context.setColor(previousColor);/* */
	}
	
	/**
	 * For debugging purpose, draw the outer of bounding box of
	 * the element
	 * @param context
	 */
	protected void renderDebugBoundingBoxOuter(Graphics2D context) {
		java.awt.Color previousColor = context.getColor();
		context.setColor(java.awt.Color.RED);
		Rectangle2D bb = getBoundingBox();
		bb.setRect(bb.getX()-1, bb.getY()-1, bb.getWidth()+2, bb.getHeight()+2);
		context.draw(bb);
		context.setColor(previousColor);
	}

	protected void renderDebugDecorationAnchors(Graphics2D context) {
		java.awt.Color previousColor = context.getColor();
		context.setColor(java.awt.Color.ORANGE);
		for (int i = 0; i < m_decorationAnchors.length; i++) {
			if (m_decorationAnchors[i] != null) {
				context.drawOval((int)m_decorationAnchors[i].getX(),
								(int)m_decorationAnchors[i].getY(),
								1, 1);
			}
		}
		context.setColor(previousColor);
	}

	public String toString() {
		String ret = super.toString();
		if (getMusicElement() != null)
			ret += " <" + getMusicElement().toString()
			+ " ref"
			+ getMusicElement().getReference().toString()
			+">";
		return ret;
	}
	
}
