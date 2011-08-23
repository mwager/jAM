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
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.Rectangle2D;

import abc.notation.Accidental;
import abc.notation.Clef;
import abc.notation.Decoration;
import abc.notation.GracingType;
import abc.notation.Note;
import abc.notation.NoteAbstract;
import abc.ui.scoretemplates.ScoreAttribute;
import abc.ui.scoretemplates.ScoreElements;
import abc.ui.swing.JScoreElement.JStemmableElement;

/** This class defines a note rendition element.
 */
abstract class JNoteElementAbstract extends JScoreElementAbstract
								implements JStemmableElement {

	protected static final double SPACE_RATIO_FOR_GRACENOTES = 0.3;

	protected Note note = null;

	private boolean m_headInverted = false;

	// instance of JGraceNotes or JGroupOfGraceNotes
	protected JScoreElementAbstract m_jGracenotes = null;

	private JSlurOrTie jSlurDefinition = null;

	//TODO redundant with slur...AnchorOutOfStem ?
	protected Dimension slurStemOffset = null;

	protected Point2D slurUnderAnchor = null, slurAboveAnchor = null;
	protected Point2D slurUnderAnchorOutOfStem = null, slurAboveAnchorOutOfStem = null;
	protected Point2D tieStartAboveAnchor = null, tieStartUnderAnchor = null;
	protected Point2D tieEndAboveAnchor = null, tieEndUnderAnchor = null;

	/** <TT>true</TT> if the stem is up for this chord, <TT>false</TT> otherwise. */
	// private attribute so all classes are forced to accessor methods
	//  this ensures autoStemming is done correctly
	protected boolean stemUp = true;

	/** Stem direction will be determined automatically
	 * based on note value. Notes B or higher will be stemed down,
	 * Notes A or lower will be stemed up. True by default.
	 */
	// private attribute so all classes are forced to accessor methods
	//  this ensures autoStemming is done correctly
	protected boolean autoStem = true;
	
	private Clef m_clef;

	/** Callback invoked when the base has changed for this object. */
	protected abstract void onBaseChanged();

	public JNoteElementAbstract(NoteAbstract noteValue, Clef clef, Point2D base, ScoreMetrics metrics) {
		super(base, metrics);
		m_clef = clef;

		// add JGraceNotes
		if (noteValue.hasGracingNotes()) {
			Note[] graceNotes = noteValue.getGracingNotes();
			if (graceNotes.length == 1) {
				m_jGracenotes = new JGraceNote(graceNotes[0], clef, base, getMetrics());
				base.setLocation(base.getX()+m_jGracenotes.getWidth(),base.getY());
			} else if (graceNotes.length>1) {
				m_jGracenotes = new JGroupOfGraceNotes(getMetrics(), base, graceNotes, clef, null);
				//base.setLocation(base.getX()+m_jGracenotes.getWidth(),base.getY());
			}
			byte appogOrAcciac = noteValue.getGracingType();
			boolean renderSlash = true;
			switch (appogOrAcciac) {
			case GracingType.ACCIACCATURA:
				renderSlash = getTemplate().getAttributeBoolean(ScoreAttribute.ACCIACCATURA_SLASH);
				break;
			case GracingType.APPOGGIATURA:
			default:
				renderSlash = getTemplate().getAttributeBoolean(ScoreAttribute.APPOGGIATURA_SLASH);
				break;
			}
			((JGraceElement) m_jGracenotes).setRenderSlash(renderSlash);
		}

		// add JDecorations
		if (noteValue.hasDecorations()) {
			Decoration[] decorations = noteValue.getDecorations();
			for (int i=0; i<decorations.length;i++) {
				if ((decorations[i].getType() == Decoration.ROLL)
						&& noteValue.hasGeneralGracing()) {
					addDecoration(
						new JDecoration(
							new Decoration(Decoration.GENERAL_GRACING),
							getMetrics()
						));
				} else {
					addDecoration(
						new JDecoration(
							decorations[i], getMetrics()));
				}
			}
		}
		
		if (noteValue.hasDynamic()) {
			m_jDynamic = new JDynamic(noteValue.getDynamic(), getMetrics());
		}
	}
	
	protected Clef getClef() {
		if (m_clef == null)
			m_clef = Clef.G;
		return m_clef;
	}
	
	public abstract Point2D getNotePosition();

	public boolean isFollowingStemmingPolicy() {
		return true;
	}

	public boolean isAutoStem() {
		return autoStem;
	}

	public void setAutoStem(boolean auto) {
		autoStem = auto;
	}

	public void setStemUp(boolean isUp) {
		stemUp = isUp;
		/*
		valuate note char
		*/
	}

	public boolean isStemUp() {
	  boolean isup = stemUp;
	  if (autoStem && (note != null)) {
		//if (note.getHeight()<Note.B) {
		//now depends on the clef!
		//reset the note's accidental before checking
		Accidental acc = note.getAccidental();
		note.setAccidental(Accidental.NONE);
		if ((getClef() == null) || note.isLowerThan(getClef().getMiddleNote())) {
		  isup = true;
		} else {
		  isup = false;
		}
		note.setAccidental(acc);
	  }
	  return isup;
	}
	
	/**
	 * Sets if the note head is inverted (i.e. right to stem
	 * if stem is up, left to stem if stem is down)
	 * @param b
	 */
	protected void setHeadInverted(boolean b) {
		m_headInverted = b;
	}

	/**
	 * Returns if the note head is inverted (i.e. right to stem
	 * if stem is up, left to stem if stem is down)
	 */
	protected boolean isHeadInverted() {
		return m_headInverted;
	}

	protected void calcDecorationPosition() {
		/* ********************************************* */
		// TODO move this in JDecoration (or subclass) ?
		// GENERAL RULES implemented here:
		// Articulations:
		//		upbow          -> always above staff
		//		downbow        -> always above staff
		//		staccato       -> placed with note
		//		staccatissimo  -> placed with note
		//		tenuto         -> placed with note
		//		[the rest]     ->
		//
		// Decorations/Ornaments
		//		[all]          -> above staff
		//
		// Dynamics:
		//		single staff   -> place below
		//		double staff   -> between staffs
		//		vocal music    -> above staff
		//
		/* ********************************************* */
		//TODO getDecorations.size==0, return (if null, =new Vector)
		if (m_jDecorations != null && m_jDecorations.size() > 0){

			JNote lowest, highest;
			if (this instanceof JChord) {
				lowest = ((JChord) this).getLowestNote();
				highest = ((JChord) this).getHighestNote();
			}
			else {//if (this instanceof JNote) {
				lowest = (JNote) this; highest = (JNote) this;
			}
			
			ScoreMetrics metrics = getMetrics();
			Rectangle2D bb = getBoundingBox();
			boolean isStemUp = isStemUp();
			double noteHeight = metrics.getNoteHeight();
			double noteWidth = metrics.getNoteWidth();
			double noteHeadX = lowest.getNotePosition().getX() + noteWidth * 0.5;
			double aboveNoteHeadY = highest.getNotePosition().getY() - noteHeight*1.5;
			double underNoteHeadY = lowest.getNotePosition().getY() + noteHeight*0.5;
			double stemX = lowest.getStemBeginPosition().getX();
			double endStemY = isStemUp
							?(bb.getMinY()-noteHeight*0.5)
							:(bb.getMaxY()+noteHeight*0.5);
			double middleStemY = isStemUp
							?(endStemY + noteHeight*1.5)
							:(endStemY - noteHeight*1.5);
			double aboveStaffY = (getStaffLine()!=null
							?getStaffLine().get5thLineY()
							:aboveNoteHeadY) - noteHeight;
			double aboveStaffAfterNoteY = aboveStaffY - noteHeight;
			double aboveStaffAfterNoteX = noteHeadX + noteWidth*2;
			double underStaffY = (getStaffLine()!=null
							?getStaffLine().get1stLineY()
							:underNoteHeadY) + noteHeight;
			double middleStaffY = aboveStaffY + ((underStaffY-aboveStaffY)/2);
			double noteHeadY = lowest.getNotePosition().getY() - noteHeight*0.5;
			double leftNoteHeadX = (lowest.accidentalsPosition!=null
										?lowest.accidentalsPosition.getX()
										:lowest.notePosition.getX())
									- noteWidth*0.5;
			double rightNoteHeadX = (lowest.dotsPosition!=null
										?lowest.dotsPosition[lowest.dotsPosition.length-1].getX()
										:(lowest.notePosition.getX()+noteWidth))
									+ noteWidth*0.5;
			aboveStaffY = Math.min(aboveStaffY,
							Math.min(aboveNoteHeadY, endStemY));
			underStaffY = Math.max(underStaffY,
					Math.max(underNoteHeadY, endStemY));
			double aboveNoteOutOfStaffY = Math.min(aboveNoteHeadY, aboveStaffY);
			double underNoteOutOfStaffY = Math.max(underNoteHeadY, underStaffY);
			double endStemOutOfStaffY = isStemUp
							?Math.min(endStemY, aboveStaffY)
							:Math.max(endStemY, underStaffY);
			
			m_decorationAnchors[JDecoration.ABOVE_STAFF]
				= new Point2D.Double(noteHeadX, aboveStaffY);
			m_decorationAnchors[JDecoration.UNDER_STAFF]
				= new Point2D.Double(noteHeadX, underStaffY);
			m_decorationAnchors[JDecoration.ABOVE_NOTE]
				= new Point2D.Double(noteHeadX, aboveNoteHeadY);
			m_decorationAnchors[JDecoration.UNDER_NOTE]
				= new Point2D.Double(noteHeadX, underNoteHeadY);
			m_decorationAnchors[JDecoration.VERTICAL_NEAR_STEM]
				= new Point2D.Double(noteHeadX, isStemUp?aboveNoteHeadY:underNoteHeadY);
			m_decorationAnchors[JDecoration.VERTICAL_NEAR_STEM_OUT_STAFF]
			    				= new Point2D.Double(noteHeadX, isStemUp?aboveNoteOutOfStaffY:underNoteOutOfStaffY);
			m_decorationAnchors[JDecoration.VERTICAL_AWAY_STEM]
				= new Point2D.Double(noteHeadX, isStemUp?underNoteHeadY:aboveNoteHeadY);
			m_decorationAnchors[JDecoration.VERTICAL_AWAY_STEM_OUT_STAFF]
			    				= new Point2D.Double(noteHeadX, isStemUp?underNoteOutOfStaffY:aboveNoteOutOfStaffY);
			m_decorationAnchors[JDecoration.HORIZONTAL_NEAR_STEM]
				= new Point2D.Double(isStemUp()?rightNoteHeadX:leftNoteHeadX, noteHeadY);
			m_decorationAnchors[JDecoration.HORIZONTAL_AWAY_STEM]
				= new Point2D.Double(isStemUp()?leftNoteHeadX:rightNoteHeadX, noteHeadY);
			m_decorationAnchors[JDecoration.STEM_END]
				= new Point2D.Double(stemX, endStemY);
			m_decorationAnchors[JDecoration.STEM_END_OUT_OF_STAFF]
				= new Point2D.Double(stemX, endStemOutOfStaffY);
			m_decorationAnchors[JDecoration.STEM_MIDDLE]
				= new Point2D.Double(stemX, middleStemY);
			m_decorationAnchors[JDecoration.ABOVE_STAFF_AFTER_NOTE]
				= new Point2D.Double(aboveStaffAfterNoteX, aboveStaffAfterNoteY);
			m_decorationAnchors[JDecoration.LEFT_NOTE]
				= new Point2D.Double(leftNoteHeadX, noteHeadY);
			m_decorationAnchors[JDecoration.RIGHT_NOTE]
			    				= new Point2D.Double(rightNoteHeadX, noteHeadY);
			m_decorationAnchors[JDecoration.MIDDLE_STAFF]
			    				= new Point2D.Double(noteHeadX, middleStaffY);
		}
	}

	/**
	 * @return Returns the jSlurDefinition.
	 */
	public JSlurOrTie getJSlurDefinition() {
		if (jSlurDefinition == null)
			jSlurDefinition = new JSlurOrTie(null, getMetrics());
		return jSlurDefinition;
	}

	/**
	 * @param slurDefinition The jSlurDefinition to set.
	 */
	public void setJSlurDefinition(JSlurOrTie slurDefinition) {
		jSlurDefinition = slurDefinition;
	}
	
	public Point2D getTieStartAboveAnchor() {
		return tieStartAboveAnchor;
	}
	
	public Point2D getTieStartUnderAnchor() {
		return tieStartUnderAnchor;
	}
	
	public Point2D getTieEndAboveAnchor() {
		return tieEndAboveAnchor;
	}
	
	public Point2D getTieEndUnderAnchor() {
		return tieEndUnderAnchor;
	}

	public Point2D getSlurAboveAnchor() {
		return slurAboveAnchor;
	}

	public Point2D getSlurUnderAnchor() {
		return slurUnderAnchor;
	}

	public Point2D getSlurAboveAnchorOutOfStem() {
		return slurAboveAnchorOutOfStem;
	}

	public Point2D getSlurUnderAnchorOutOfStem() {
		return slurUnderAnchorOutOfStem;
	}

	/* Render each indiviual gracenote associated with this note. */
	protected void renderGraceNotes(Graphics2D context) {
		if (m_jGracenotes != null) {
			m_jGracenotes.render(context);
			
			boolean addSlur = false;
			if (getMusicElement() instanceof NoteAbstract) {
				byte graceType = ((NoteAbstract) getMusicElement())
					.getGracingType();
				addSlur = getTemplate().getAttributeBoolean(
					graceType == GracingType.ACCIACCATURA
						?ScoreAttribute.ACCIACCATURA_SLUR
						:ScoreAttribute.APPOGGIATURA_SLUR
				);
			}
			
			if (addSlur) {
				boolean graceAfterNote = false;
				if (m_jGracenotes.getBase().getX() > getBase().getX())
					graceAfterNote = true;
				boolean gracesUp = ((JStemmableElement) m_jGracenotes).isStemUp();
				Point2D p2dStart, p2dControl, p2dEnd;
				JNoteElementAbstract start, end;
				if (graceAfterNote) {
					if (this instanceof JChord) {
						start = gracesUp
							?((JChord) this).getLowestNote()
							:((JChord) this).getHighestNote();
					} else
						start = this;
					if (m_jGracenotes instanceof JGraceNote)
						end = (JGraceNote) m_jGracenotes;
					else //JGroupOfGrace
						end = (JGraceNotePartOfGroup)
							((JGroupOfGraceNotes) m_jGracenotes)
								.m_jNotes[((JGroupOfGraceNotes) m_jGracenotes).m_jNotes.length-1];
				} else {
					if (m_jGracenotes instanceof JGraceNote)
						start = (JGraceNote) m_jGracenotes;
					else //JGroupOfGrace
						start = (JGraceNotePartOfGroup)
							((JGroupOfGraceNotes) m_jGracenotes)
								.m_jNotes[0];
					if (this instanceof JChord) {
						end = gracesUp
							?((JChord) this).getLowestNote()
							:((JChord) this).getHighestNote();
					} else
						end = this;
				}
				p2dStart = gracesUp
						?start.getTieStartUnderAnchor()
						:start.getTieStartAboveAnchor();
				p2dEnd = gracesUp
						?end.getTieEndUnderAnchor()
						:end.getTieEndAboveAnchor();
				double yControl = gracesUp
						?Math.max(p2dStart.getY(), p2dEnd.getY())
						:Math.min(p2dStart.getY(), p2dEnd.getY());
				if (m_jGracenotes instanceof JGroupOfGraceNotes) {
					Rectangle2D bounds = m_jGracenotes.getBoundingBox();
					if (gracesUp)
						yControl = Math.max(yControl, bounds.getMaxY());
					else
						yControl = Math.min(yControl, bounds.getMinY());
				}
				p2dControl = new Point2D.Double(
					p2dStart.getX() + (p2dEnd.getX()-p2dStart.getX())/2,
					yControl + ((gracesUp?.5:-.5)*getMetrics().getSlurAnchorYOffset())
				);
				
				Color previousColor = context.getColor();
				setColor(context, ScoreElements.GRACENOTE);
				GeneralPath path = new GeneralPath();
				path.moveTo((float)p2dStart.getX(), (float)p2dStart.getY());
				QuadCurve2D q = new QuadCurve2D.Float();
				q.setCurve(p2dStart,
						JTune.newControl(p2dStart, p2dControl, p2dEnd),
						p2dEnd);
				path.append(q, true);
				p2dControl.setLocation(p2dControl.getX(),
					p2dControl.getY() + (gracesUp?1:-1));
				q.setCurve(p2dEnd,
						JTune.newControl(p2dStart, p2dControl, p2dEnd),
						p2dStart);
				path.append(q, true);
				context.fill(path);
				context.draw(path);
				context.setColor(previousColor);
			}
		}
	}
	
	protected void renderDebugSlurAnchors(Graphics2D context) {
		Point2D[] anchors = { getSlurAboveAnchor(),
				getSlurAboveAnchorOutOfStem(),
				getSlurUnderAnchor(),
				getSlurUnderAnchorOutOfStem() };
		java.awt.Color previousColor = context.getColor();
		context.setColor(java.awt.Color.BLUE);
		for (int i = 0; i < anchors.length; i++) {
			if (anchors[i] != null)
				context.drawOval((int)anchors[i].getX(), (int)anchors[i].getY(), 1, 1);
		}
		context.setColor(previousColor);
		System.out.println("renderDebugSlurAnchors : "+getMusicElement());
		System.out.println("  object : "+getClass().getSimpleName());
		System.out.println("  - base = "+getBase());
		System.out.println("  - above = "+getSlurAboveAnchor());
		System.out.println("  - above out = "+getSlurAboveAnchorOutOfStem());
		System.out.println("  - under = "+getSlurUnderAnchor());
		System.out.println("  - under out = "+getSlurUnderAnchorOutOfStem());
	}

}
