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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import abc.notation.Clef;
import abc.notation.MultiNote;
import abc.notation.MusicElement;
import abc.notation.Note;
import abc.notation.NoteAbstract;
import abc.notation.SlurDefinition;
import abc.ui.scoretemplates.ScoreAttribute;
import abc.ui.scoretemplates.ScoreElements;
import abc.ui.swing.JScoreElement.JStemmableElement;

/** This class is in charge of rendering a group of notes whose stems should be linked. */
class JGroupOfNotes extends JScoreElementAbstract
				implements JStemmableElement {

	/** All the {@link Note}s and {@link MultiNote}s that are part of the group. */
	protected NoteAbstract[] m_notes = null;
	/** notes that are grouped */
	//protected Note[] anchorNotes = null;
	/** All the notes rendition elements that are part of the group. chords and / or notes*/
	protected JGroupableNote[] m_jNotes = null;
	/** The Y coordinate where the line linking all the notes is put. */
	private int m_stemYend = -1;
	
	private double m_internalSpacingRatio = -1;

	private int nUpletSize = -1;

	private boolean autoStemming = true;
	private boolean isStemUp = true;
	
	private double m_width = -1;

	private Engraver m_engraver = null;
	
	private Clef m_clef;

	public JGroupOfNotes(ScoreMetrics metrics, Point2D base, NoteAbstract[] notes, Clef clef, Engraver engrav){
		super(metrics);
		m_clef = clef;
		if (notes.length<=1)
			throw new IllegalArgumentException(notes + " is not a group of notes, length = " + notes.length);
		m_engraver = engrav;
		m_notes = new NoteAbstract[notes.length];
		//create JNotePartOfGroup instances. Those instance should stay the same
		//when the base is changed.
		m_jNotes = new JGroupableNote[m_notes.length];
		for (int i=0; i<notes.length; i++)
			if (notes[i] instanceof Note) {
				m_notes[i] = (Note)notes[i];
				m_jNotes[i] = new JNotePartOfGroup((Note)m_notes[i], clef, base, getMetrics());
				//anchorNotes[i] = (Note)notes[i];
			}
			else {
				//This is a multiNote
				m_jNotes[i] = new JChordPartOfGroup((MultiNote)notes[i], clef, getMetrics(), new Point2D.Double());
				m_notes[i] = (MultiNote) notes[i];
					//(Note)((JChordPartOfGroup)m_jNotes[i]).getReferenceNoteForGroup().getMusicElement();
			}
		//m_jNotes[i]=n;
		if (notes[0].getTuplet()!=null) {
			nUpletSize = notes[0].getTuplet().getNumberOfNotes();
			SlurDefinition slurDef = new SlurDefinition();
			slurDef.setStart(notes[0].getReference());
			slurDef.setEnd(notes[notes.length-1].getReference());
			JSlurOrTie jSlurDef = new JSlurOrTie(slurDef, getMetrics());
			jSlurDef.setPosition(JSlurOrTie.POSITION_ABOVE);
			jSlurDef.setOutOfStems(true);
			jSlurDef.setTuplet(true);
			m_notes[0].addSlurDefinition(slurDef);
			((JNoteElementAbstract) m_jNotes[0]).setJSlurDefinition(jSlurDef);
		}
		setBase(base);
	}
	
	protected Clef getClef() {
		return m_clef;
	}

	public MusicElement getMusicElement() {
		return null;
	}
	
	/**
	 * in a genric way that enables positioning, sizing,
	 * rendering to be done generically
	 * <p>subclasses should override this method. 
	 * @return {@link ScoreMetrics#NOTATION_CONTEXT_NOTE}
	 */
	protected int getNotationContext() {
		return ScoreMetrics.NOTATION_CONTEXT_NOTE;
	}
	
	public double getWidth() {
		return m_width; //suppose that is has been calculated
	}

	public void setStaffLine(JStaffLine staffLine) {
		//If a group of notes if displayed on a staff line, all notes
		//composing the group are then part of this staff line as well.
		for (int i=0; i<m_jNotes.length; i++)
			((JScoreElementAbstract)m_jNotes[i]).setStaffLine(staffLine);
		super.setStaffLine(staffLine);
	}

	protected NoteAbstract[] getMusicElements() {
		return m_notes;
	}

	protected JScoreElementAbstract[] getRenditionElements() {
		JScoreElementAbstract[] array = new JScoreElementAbstract[m_jNotes.length];
		System.arraycopy(m_jNotes, 0, array, 0, m_jNotes.length);
		return array;
	}

	/**
	 * A JChordPartOfGroup in this group of note can have multi-length
	 * chords, then only one is part of group (the shortest duration' one)
	 * 
	 * So, for auto stemming, stem direction... functions, if the
	 * JNoteElementAbstract is a JChord(PartOfGroup), returns the
	 * really needed chord, itself if one-length, the shortest if
	 * multi-length
	 * @param chord
	 */
	private JChordPartOfGroup getChordReallyPartOfGroup(JChordPartOfGroup chord) {
		if (chord.m_normalizedChords != null) {
			//should be m_normalizedChords[0]
			for (int j=0; j<chord.m_normalizedChords.length; j++) {
				if (chord.m_normalizedChords[j] instanceof JChordPartOfGroup) {
					return (JChordPartOfGroup) chord.m_normalizedChords[j];
				}
			}
		}
		return chord;
	}
	
	public boolean isAutoStem() {
		if (autoStemming) {
			for (int i=0; i<m_jNotes.length; i++) {
				boolean autoS = true;
				JNoteElementAbstract jnea = (JNoteElementAbstract)m_jNotes[i];
				//If the chord is multi length, looks at the chord which
				//is *really* part of the group. In both chords in a
				//multi-length chord, there is only one part of a group (the shortest)
				if (jnea instanceof JChordPartOfGroup) {
					autoS = getChordReallyPartOfGroup((JChordPartOfGroup) jnea).autoStem;
				}
				else {
					autoS = jnea.isAutoStem();
				}
				if (autoS == false)
					return false;
			}
			return true;
		}
		return autoStemming;
	}

	public void setAutoStem(boolean auto) {
		autoStemming = auto;
		for (int i=0; i<m_jNotes.length; i++) {
			JNoteElementAbstract jnea = (JNoteElementAbstract)m_jNotes[i];
			if (jnea instanceof JChordPartOfGroup) {
				getChordReallyPartOfGroup((JChordPartOfGroup) jnea).setAutoStem(auto);
			} else {
				jnea.setAutoStem(auto);
			}
		}
		setStemUp(isStemUp);
	}
	
	public boolean isStemUp() {
		if (isAutoStem())
			return isStemUp;
		else {
			for (int i=0; i<m_jNotes.length; i++) {
				JNoteElementAbstract jnea = (JNoteElementAbstract)m_jNotes[i];
				if (jnea instanceof JChordPartOfGroup) {
					JChordPartOfGroup jcpog = getChordReallyPartOfGroup((JChordPartOfGroup) jnea);
					if (jcpog.isAutoStem() == false)
						return jcpog.isStemUp();
				} else {
					if (jnea.isAutoStem() == false)
						return jnea.isStemUp();
				}
			}
			//should never happen
			return isStemUp;
		}
	}
	
	private void setStemYEnd(int stemY) {
		m_stemYend = stemY;
		for (int i=0; i<m_jNotes.length; i++) {
			JNoteElementAbstract jnea = (JNoteElementAbstract)m_jNotes[i];
			if (jnea instanceof JChordPartOfGroup) {
				JChordPartOfGroup jcpog = getChordReallyPartOfGroup((JChordPartOfGroup) jnea);
				jcpog.setStemYEnd(stemY);
		//		jcpog.onBaseChanged();
			} else { //JNotePartOfGroup
				((JNotePartOfGroup) jnea).setStemYEnd(stemY);
		//		jnea.onBaseChanged();
			}
		}
	}

	public void setStemUp(boolean isUp) {
		isStemUp = isUp;
		for (int i=0; i<m_jNotes.length; i++) {
			JNoteElementAbstract jnea = (JNoteElementAbstract)m_jNotes[i];
			if (jnea instanceof JChordPartOfGroup) {
				JChordPartOfGroup jcpog = getChordReallyPartOfGroup((JChordPartOfGroup) jnea);
				jcpog.setAutoStem(false);
				jcpog.setStemUp(isStemUp);
				jcpog.onBaseChanged();
			} else {
				jnea.setAutoStem(false);
				jnea.setStemUp(isStemUp);
				jnea.onBaseChanged();
			}
		}
	}
	
	/**
	 * When the score is justified, avoid big gaps between
	 * group of notes, by enlarging the JGroupOfNote by adding
	 * a space between each note of the group.
	 * @param d <= 0 to cancel justification
	 */
	public void setInternalSpacingRatio(double d) {
		m_internalSpacingRatio = d;
	}

	protected void onBaseChanged() {
		Point2D currentBase =(Point2D)getBase().clone();
		int highIndex = 0;
		int lowIndex = 0;
		//Note highNote, lowNote;

		highIndex = Note.getHighestNoteIndex(m_notes);
		lowIndex = Note.getLowestNoteIndex(m_notes);

		JGroupableNote highestNote = m_jNotes[highIndex];//(m_notes); Notestsn = new JNotePartOfGroup(highestNote, getBase(), m_metrics);
		JGroupableNote lowestNote = m_jNotes[lowIndex];//(m_notes); Notestsn = new JNotePartOfGroup(lowestNote, getBase(), m_metrics);

		// assume every note in group has same auto stemming policy
		// can be indivudual beamed note or chord multinote

		if (isAutoStem()) {

			byte h, l;
			if (m_notes[highIndex] instanceof MultiNote)
				h = ((MultiNote) m_notes[highIndex]).getHighestNote().getHeight();
			else
				h = ((Note) m_notes[highIndex]).getHeight();
			if (m_notes[lowIndex] instanceof MultiNote)
				l = ((MultiNote) m_notes[lowIndex]).getLowestNote().getHeight();
			else
				l = ((Note) m_notes[lowIndex]).getHeight();

			byte midHeight = getClef().getMiddleNote().getHeight();
			if (h <= midHeight) {
				setStemUp(true);
			} else if (l > midHeight) {
				setStemUp(false);
			} else {
				if ( (h - midHeight) < (midHeight - l) ) {
					// lowest note is further away from center line than highest note
					setStemUp(true);
				} else {
					setStemUp(false);
				}
			}

		} else {
			setStemUp(isStemUp());
		}


		//FIXME compute average distance between note head and middle Note.c
//		int cptUp = 0, cptDown = 0;
//		for (int i = 0; i < m_jNotes.length; i++) {
//			if (((JNote) m_jNotes[i]).isStemUp())
//				cptUp++;
//			else
//				cptDown++;
//		}
//		//System.out.println("up="+cptUp+",down="+cptDown);
//		isStemUp = cptUp>=cptDown;
//		for (int i = 0; i < m_jNotes.length; i++) {
//			((JNote) m_jNotes[i]).setStemUp(isStemUp);
//			((JNote) m_jNotes[i]).onBaseChanged();
//		}

		Point2D updatedBase = null;
		setStemYEnd(-1); //reinit the stemYend stored in notes of the group
		if (isStemUp) {
			//update the highest note to calculate when the stem Y end should be after the base change.
			updatedBase = highestNote.getBase();
			updatedBase.setLocation(currentBase);
			((JScoreElementAbstract)highestNote).setBase(updatedBase);
			//based on this, calculate the new stem Y end.
			m_stemYend = (int)(highestNote.getStemBeginPosition().getY()
					-getMetrics().getNoteHeight()/2
					-getMetrics().getStemLengthForContext(getNotationContext()));
		} else {
			//update the lowest note to calculate when the stem Y end should be after the base change.
			updatedBase = lowestNote.getBase();
			updatedBase.setLocation(currentBase);
			((JScoreElementAbstract)lowestNote).setBase(updatedBase);
			//based on this, calculate the new stem Y end.
//			m_stemYend = (int)(lowestNote.getStemBeginPosition().getY()
//				+getMetrics().getStemLengthForContext(getNotationContext()));
			m_stemYend = lowestNote.getStemYEnd();
		}
		setStemYEnd(m_stemYend); //apply the same stemYEnd to all notes in group

		JGroupableNote firstNote = m_jNotes[0];
		JGroupableNote lastNote = m_jNotes[m_jNotes.length-1];
		double engraverSpacing = 0;
		// apply the new stem y end to the rest of the group of notes.
		for (int i=0; i<m_jNotes.length; i++) {
			updatedBase = m_jNotes[i].getBase();
			updatedBase.setLocation(currentBase);
			((JScoreElementAbstract)m_jNotes[i]).setBase(updatedBase);
			m_jNotes[i].setStemYEnd(m_stemYend);
			engraverSpacing = 0;

			// gracenote group have fixed spacing, so only use engraver spacing if engraver is not null
			// FIXME: fix this so gracenote groups use a proper engraver
			if (m_engraver != null)
				engraverSpacing = m_engraver.getNoteSpacing(m_jNotes[i]);
			double justifySpacingCorrection = engraverSpacing
				+ getMetrics().getNotesSpacingForContext(getNotationContext());
			if (m_internalSpacingRatio > 0)
				justifySpacingCorrection *= m_internalSpacingRatio;
			currentBase.setLocation(currentBase.getX()
							+ m_jNotes[i].getWidth()
							//+ getMetrics().getNotesSpacing()
							//+ engraverSpacing,
							+ justifySpacingCorrection,
						getBase().getY());
				//}
		}
		if (lastNote==null)
			lastNote=firstNote;
		//double firstNoteAccidentalWidth = (firstNote.getWidth()-getMetrics().getNoteWidth());

		/*if (isStemUp) {
			m_width = (int)(lastNote.getStemBeginPosition().getX()-(firstNote).getBase().getX() + firstNoteAccidentalWidth);
		}
		else {
			m_width = (int)(lastNote.getStemBeginPosition().getX()+getMetrics().getNoteWidth()
						-firstNote.getBase().getX() + firstNoteAccidentalWidth);
		}*/
		m_width = getBoundingBox().getWidth();
	}

	public double render(Graphics2D context){
		//super.render(context);
		Color previousColor = context.getColor();
		if (this instanceof JGraceElement)
			setColor(context, ScoreElements.GRACENOTE);
		else
			setColor(context, ScoreElements.NOTE);
		Stroke defaultStroke = context.getStroke();
		int factor = isStemUp?1:-1; //invert direction for noteLinkY
		for (int i=0; i<m_jNotes.length; i++) {
			JGroupableNote n = m_jNotes[i];
			((JScoreElementAbstract)n).render(context);
			BasicStroke notesLinkStroke =
				getMetrics().getNotesLinkStrokeForContext(getNotationContext());
			context.setStroke(notesLinkStroke);
			short[] longerRhythms = null;
			short noteStrictDuration =
				(m_notes[i] instanceof MultiNote)
					?((MultiNote) m_notes[i]).getShortestNote().getStrictDuration()
					:((Note) m_notes[i]).getStrictDuration();
			switch (noteStrictDuration) {
				case Note.EIGHTH: longerRhythms = new short[] { Note.EIGHTH }; break;
				case Note.SIXTEENTH: longerRhythms = new short[] { Note.EIGHTH, Note.SIXTEENTH }; break;
				case Note.THIRTY_SECOND: longerRhythms = new short[] { Note.EIGHTH, Note.SIXTEENTH, Note.THIRTY_SECOND }; break;
				case Note.SIXTY_FOURTH: longerRhythms = new short[] { Note.EIGHTH, Note.SIXTEENTH, Note.THIRTY_SECOND, Note.SIXTY_FOURTH }; break;
			}
			for (int j=0; j<longerRhythms.length; j++) {
				//decide where the end of the rhythm is.
				int noteLinkY = -1;
				if (longerRhythms[j]==Note.EIGHTH)
					noteLinkY = (int)(m_stemYend+factor*notesLinkStroke.getLineWidth()*0.5);
				else if (longerRhythms[j]==Note.SIXTEENTH)
					noteLinkY = (int)(m_stemYend+factor*notesLinkStroke.getLineWidth()*2);
				else if (longerRhythms[j]==Note.THIRTY_SECOND)
					noteLinkY = (int)(m_stemYend+factor*notesLinkStroke.getLineWidth()*3.5);
				else if (longerRhythms[j]==Note.SIXTY_FOURTH)
					noteLinkY = (int)(m_stemYend+factor*notesLinkStroke.getLineWidth()*5);
				//small graphical bug if stem down
				if (!isStemUp)
					noteLinkY += 1;//notesLinkStroke.getLineWidth();

				int noteLinkEnd = -1;
				// is there any note after ?
				boolean nextNoteIsShorterOrEquals = false;
				boolean previousNoteIsShorterOrEquals = false;
				boolean hasNext = i<m_jNotes.length-1;
				boolean hasPrevious = i>0;
				if (hasNext) {
					nextNoteIsShorterOrEquals =
						((m_notes[i+1] instanceof MultiNote)
							?((MultiNote) m_notes[i+1]).getShortestNote().getStrictDuration()
							:((Note) m_notes[i+1]).getStrictDuration())
						<=longerRhythms[j];
				}
				if (hasPrevious) {
					previousNoteIsShorterOrEquals =
						((m_notes[i-1] instanceof MultiNote)
							?((MultiNote) m_notes[i-1]).getShortestNote().getStrictDuration()
							:((Note) m_notes[i-1]).getStrictDuration())
					<=longerRhythms[j];
				}
				if (hasPrevious) {
					if (previousNoteIsShorterOrEquals)
						//the end is the stem of the previous note.
						noteLinkEnd = (int)((JGroupableNote)m_jNotes[i-1]).getStemBeginPosition().getX();//getE (int)(stemX-2*context.getNoteWidth());
					else
						if (!(hasNext && nextNoteIsShorterOrEquals))
							noteLinkEnd = (int)(m_jNotes[i].getStemBeginPosition().getX()-getMetrics().getNoteWidth()/1.2);
				}
				else
					if (!nextNoteIsShorterOrEquals)
						noteLinkEnd = (int)(m_jNotes[i].getStemBeginPosition().getX()+getMetrics().getNoteWidth()/1.2);
				if (noteLinkEnd!=-1)
					context.drawLine((int)m_jNotes[i].getStemBeginPosition().getX(),
							noteLinkY,
							noteLinkEnd,
							noteLinkY);
			}
//			restore defaut stroke.
			context.setStroke(defaultStroke);
		}

		if (nUpletSize!=-1) {
			char[] chars = getMusicalFont().getTupletDigits(nUpletSize);
			float tupletNumberYOffset = getTemplate().getAttributeSize(ScoreAttribute.TUPLET_NUMBER_Y_OFFSET);
			Rectangle2D numberBounds = getMetrics().getBounds(chars);
			//TODO replace with commented line but needs to be improved because of the get display position.
			//context.drawChars(chars, 0, 1, (int)(((JNote)m_jNotes[0]).getDisplayPosition().getX()+m_width/2), (int)(m_stemYend - m_metrics.getNoteHeigth()/4));
			Point2D ctrlP = null;
			if (isStemUp) {
				ctrlP = new Point2D.Double(
						m_jNotes[0].getBase().getX()+getWidth()/2,
						m_stemYend - getTemplate().getAttributeSize(ScoreAttribute.TUPLET_NUMBER_Y_OFFSET));
				context.drawChars(chars, 0, 1, (int)ctrlP.getX(), (int)ctrlP.getY());
			} else {
				//if group is ascending or descending
				//tuplet number Y = middle note minY-numberYOffset
				//straight line between start & end, if it doesn't
				//intersect notes
				Line2D line = new Line2D.Double(
					((JNoteElementAbstract) m_jNotes[0]).getSlurAboveAnchor(),
					((JNoteElementAbstract) m_jNotes[m_jNotes.length-1]).getSlurAboveAnchor()
					);
				boolean intersects = false;
				for (int i = 0; i < m_jNotes.length; i++) {
					if (line.intersects(m_jNotes[i].getBoundingBox())) {
						intersects = true;
						break;
					}
				}
				if (intersects) { //control point above bounds of the group
					ctrlP = new Point2D.Double(
						m_jNotes[0].getStemBeginPosition().getX()+(getWidth()-getMetrics().getNoteWidth())/2,
						getBoundingBox().getMinY() - tupletNumberYOffset);
				} else {
					ctrlP = new Point2D.Double(
						m_jNotes[0].getStemBeginPosition().getX()+(getWidth()-getMetrics().getNoteWidth())/2,
						line.getBounds2D().getCenterY() - tupletNumberYOffset);
				}
				context.drawChars(chars, 0, 1, (int)ctrlP.getX(), (int)ctrlP.getY());
			}
			ctrlP.setLocation(
				ctrlP.getX()+numberBounds.getWidth()/2,
				ctrlP.getY()-numberBounds.getHeight()-tupletNumberYOffset);
			((JNoteElementAbstract) m_jNotes[0]).getJSlurDefinition()
				.setTupletControlPoint(ctrlP);
		}
		context.setColor(previousColor);

		//renderDebugBoundingBox(context);
		
		return getWidth();
	}

	public Rectangle2D getBoundingBox() {
		Rectangle2D bb = new Rectangle2D.Double(getBase().getX(), getBase().getY(), 0, 0);
		for (int i = 0; i < m_jNotes.length; i++) {
			bb.add((/*(JNotePartOfGroup) */m_jNotes[i]).getBoundingBox());
		}
		return bb;
	}

	public JScoreElement getScoreElementAt(Point point) {
		JScoreElement scoreEl = null;
		for (int i=0; i<m_jNotes.length; i++) {
			scoreEl = ((JScoreElement)m_jNotes[i]).getScoreElementAt(point);
			if (scoreEl!=null)
				return scoreEl;
		}
		return scoreEl;
	}

	public boolean isFollowingStemmingPolicy() {
		return true;
	}
	
}
