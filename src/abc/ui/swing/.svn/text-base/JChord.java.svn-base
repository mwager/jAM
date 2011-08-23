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
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import abc.notation.Clef;
import abc.notation.GracingType;
import abc.notation.Interval;
import abc.notation.MultiNote;
import abc.notation.MusicElement;
import abc.notation.Note;
import abc.ui.scoretemplates.ScoreAttribute;

/** This class is in charge of rendering a chord {@link abc.notation.MultiNote}. */
class JChord extends JNoteElementAbstract {
	/** the multi this JChord is the graphic representation of. */
	protected MultiNote multiNote = null;
	/** All the notes composing the chord. */
	protected Note[] m_notes = null;
	/** All the notes rendition elements that are part of the group. */
	protected JNote[] m_sNoteInstances = null;

	protected JNote anchor = null;
	
	/**
	 * A flag that tells that the chord is part of a normalized chord
	 * then accidental and other positions computed for normalized chord
	 * are applied to "childs".
	 */
	private boolean isPartOfANormalizedChord = false;
	
	/** When multi notes are made of notes with different durations, such chord
	 * is decomposed into chords with same strict duration for normalization,
	 * and to ease the rendition : the rendition is made on set of notes with the same
	 * strict duration.
	 * Such normalized chords are ordered with ascend strict durations in this array. */
	protected JChord[] m_normalizedChords = null;

	// the width of the chord group without grace notes
	private double c_width = 0;
	private double m_width = -1;
	
	private boolean followStemmingPolicy = true;

	protected int m_stemYEndForChord = -1;
	
	/** Horizontal positifion of note head, after graces and accidentals */
	private double m_noteHeadX = -1;
	
	public JChord(MultiNote multiNote, Clef clef, ScoreMetrics metrics, Point2D base){
		super(multiNote, clef, base, metrics);
		this.multiNote = multiNote;
		
		m_notes = multiNote.toArray();
		//create JNotePartOfGroup instances. Those instances stay the same when the base is changed.
		//The width of the chord is the width of the largest note.
		if (multiNote.hasUniqueStrictDuration()) {
			m_sNoteInstances = new JNote[m_notes.length];
			boolean inverted = false;
			for (int i=0; i<m_notes.length; i++) {
				m_sNoteInstances[i] = new JChordNote(m_notes[i], getClef(), base, getMetrics());
				((JChordNote) m_sNoteInstances[i]).setIsLowest(i == 0);
				((JChordNote) m_sNoteInstances[i]).setIsHighest(i == m_notes.length - 1);
				if (i > 0) {
					Interval interval = new Interval(m_notes[i-1], m_notes[i], null);
					if ((interval.getLabel() == Interval.UNISON)
						|| (interval.getLabel() == Interval.SECOND)) {
						inverted = !inverted;
						m_sNoteInstances[i].setHeadInverted(inverted);
					} else {
						//interval greater or equal third, note head
						//goes back to normal position
						inverted = false;
					}
				}
				if(m_sNoteInstances[i].getWidth()>c_width)
					c_width = m_sNoteInstances[i].getWidth();
			}
			setAutoStem(true);
			isPartOfANormalizedChord = false;
		}
		else {
			m_sNoteInstances = new JNote[1];
//			m_sNoteInstances[0] = new JNote(multiNote.getHighestNote(), base, m_metrics);
			m_sNoteInstances[0] = new JChordNote(multiNote.getHighestNote(), getClef(), base, getMetrics());

			c_width = m_sNoteInstances[0].getWidth();
			MultiNote[] h = multiNote.normalize();
			short[] durations = multiNote.getStrictDurations();
			if (durations.length>2)
				System.err.println("abc4j - warning : chords with more than 2 differents strict duration aren't supported : only 2 smaller durations are taken into account");
			m_normalizedChords = new JChord[2];
			MultiNote shortest = h[0];
			JChord jChord = createNormalizedChord(shortest, metrics, base);
			m_normalizedChords[0] = jChord;
			//m_normalizedChords[0].setStemUp(true);
			MultiNote longest = h[1];
			jChord = createNormalizedChord(longest, metrics, base);
			m_normalizedChords[1] = jChord;
			//m_normalizedChords[1].setStemUp(false);
			if (longest.getLowestNote().isLowerThan(
					shortest.getLowestNote())) {
				m_normalizedChords[1].setStemUp(false);
				m_normalizedChords[0].setStemUp(true);
			} else {
				m_normalizedChords[1].setStemUp(true);
				m_normalizedChords[0].setStemUp(false);
			}
			setAutoStem(false);
			followStemmingPolicy = false;
			m_normalizedChords[0].isPartOfANormalizedChord = true;
			m_normalizedChords[1].isPartOfANormalizedChord = true;
		}

		if (m_jGracenotes != null)
			m_width = c_width + m_jGracenotes.getWidth();
		else
			m_width = c_width;

		setBase(base);
	}

	/** The anchor of the chord is the one that present the rhytm (the highest one
	 * in case of stem up, the lowest one in case of stem down)
	 * @param note
	 * @param base
	 * @param metrics
	 * @return
	 */
	protected JNote createAnchorNote(Note note, Point2D base, ScoreMetrics metrics) {
//		JNote jNote = new JNote(note, base, metrics);
		JNote jNote = new JChordNote(note, getClef(), base, metrics);
		//note.set
		return jNote;
	}

	/** Invoked when a multi note is decomposed into multi notes with same strict
	 * duration. */
	protected JChord createNormalizedChord(MultiNote mNote, ScoreMetrics mtrx, Point2D base) {
		return new JChord(mNote, getClef(), mtrx, base);
	}

	public MusicElement getMusicElement() {
		return multiNote;
	}
	
	public Point2D getNotePosition() {
		return getLowestNote().getNotePosition();
	}
	
	public double getWidth() {
		return m_width; //suppose it has been calculated
	}

	public JNote[] getScoreElements() {
		return m_sNoteInstances;
	}

	/** Sets the staff line this chord belongs to. */
	public void setStaffLine(JStaffLine staffLine) {
		for (int i=0; i<m_sNoteInstances.length; i++)
			m_sNoteInstances[i].setStaffLine(staffLine);
		super.setStaffLine(staffLine);
	}

	/*JNotePartOfGroup[] getRenditionElements() {
		return m_sNoteInstances;
	}*/

	/** Sets the base of this chord. */
	public void setBase(Point2D base) {
		m_stemYEndForChord = -1;
		if (m_normalizedChords!=null) {
			for (int i=0; i<m_normalizedChords.length; i++) {
				m_normalizedChords[i].setBase(base);
			}
		} else {
			for (int i = 0; i < m_sNoteInstances.length; i++) {
				m_sNoteInstances[i].setBase(base);
			}
		}
		if (m_jGracenotes != null) {
			m_jGracenotes.setBase(base);
		}
		super.setBase(base);
	}
	
	protected JNote getHighestNote() {
		JNote highest = null;
		JNote current;
		if (m_normalizedChords != null) {
			for (int i=0; i<m_normalizedChords.length; i++) {
				current = m_normalizedChords[i].getHighestNote();
				if (highest == null)
					highest = current;
				else if (((Note) current.getMusicElement()).isHigherThan(
						(Note) highest.getMusicElement()))
					highest = current;
			}
		} else {
			for (int i=0; i<m_sNoteInstances.length; i++) {
				current = ((JNote)m_sNoteInstances[i]);
				if (highest == null)
					highest = current;
				else if (((Note) current.getMusicElement()).isHigherThan(
						(Note) highest.getMusicElement()))
					highest = current;
			}
		}
		return highest;
	}
	
	protected JNote getLowestNote() {
		JNote lowest = null;
		JNote current;
		if (m_normalizedChords != null) {
			for (int i=0; i<m_normalizedChords.length; i++) {
				current = m_normalizedChords[i].getLowestNote();
				if (lowest == null)
					lowest = current;
				else if (((Note) current.getMusicElement()).isLowerThan(
						(Note) lowest.getMusicElement()))
					lowest = current;
			}
		}
		else {
			for (int i=0; i<m_sNoteInstances.length; i++) {
				current = ((JNote)m_sNoteInstances[i]);
				if (lowest == null)
					lowest = current;
				else if (((Note) current.getMusicElement()).isLowerThan(
						(Note) lowest.getMusicElement()))
					lowest = current;
			}
		}
		return lowest;
	}
	
	public boolean isFollowingStemmingPolicy() {
		return followStemmingPolicy;
	}

	public boolean isAutoStem() {
		if (autoStem) {
			if (m_normalizedChords != null)
				return false;
			for (int i = 0; i < m_sNoteInstances.length; i++) {
				if (m_sNoteInstances[i].isAutoStem() == false)
					return false;
			}
		}
		return autoStem;
	}
	
	/** Invoked when this chord base has changed. */
	protected void onBaseChanged() {
		//System.out.println("JChord.onBaseChanged : "+multiNote);
		byte h = multiNote.getHighestNote().getHeight();
		byte l = multiNote.getLowestNote().getHeight();

		//if normalizedChords!=null [0] stems down, [1] stems up
		//autoStemming is disabled
		byte midHeight = getClef().getMiddleNote().getHeight();
		if ((m_normalizedChords == null) && isAutoStem()) {
			boolean stemUp = false;
			if (h <= midHeight) {
				stemUp = true;
			} else if (l > midHeight) {
				stemUp = false;
			} else {
				if ( (h - midHeight) < (midHeight - l) ) {
					// lowest note is further away from center line than highest note
					stemUp = true;
				} else {
					stemUp = false;
				}
			}
			//recalculate anchor and valuate its note char
			setStemUp(stemUp);
		}

		if (m_normalizedChords == null) {
			// set stemYEnd
			//   get highest/lowest in group
			//   set stems appropriately
			int stemYEnd;
			if (m_stemYEndForChord == -1) {
				//auto calculated, chord is not part of group
				int stemLength = getMetrics().getStemLengthForContext(ScoreMetrics.NOTATION_CONTEXT_NOTE);
				int halfNoteHeight = (int) (getMetrics().getNoteHeight() / 2);
				int highestNoteY = (int) getHighestNote().getStemUpBeginPosition().getY() - stemLength - halfNoteHeight;
				int lowestNoteY = (int) getLowestNote().getStemDownBeginPosition().getY() + stemLength + halfNoteHeight;
				stemYEnd = isStemUp()?highestNoteY:lowestNoteY;
			} else {
				stemYEnd = m_stemYEndForChord;
			}
			
			for (int i=0; i<m_sNoteInstances.length; i++) {
				((JNotePartOfGroup)m_sNoteInstances[i]).setStemYEnd(stemYEnd);
			}
		}
		
		double graceNotesWidth = 0;
		boolean graceNotesAfter = false;
		if (m_jGracenotes != null) {
			if (multiNote.getGracingType() == GracingType.ACCIACCATURA)
				graceNotesAfter = getTemplate().getAttributeBoolean(ScoreAttribute.ACCIACCATURA_AFTER_NOTE);
			graceNotesWidth = m_jGracenotes.getWidth()
				+ getMetrics().getGraceNotesSpacing();
			if (graceNotesAfter)
				graceNotesWidth += getMetrics().getGraceNotesSpacing();
//			m_jGracenotes.onBaseChanged();
//			graceNotesWidth = m_jGracenotes.getWidth()
//				+ getMetrics().getGraceNotesSpacing();
		}
		
		double accidentalsWidth = 0;
		if (!isPartOfANormalizedChord) { //not already calculated
			//calculate accidantal position for all notes
			Note[] notes = multiNote.toArray();
			boolean chordHasStemDownInverted = false;
			for (int i = notes.length - 1; i >= 0; i--) {
				JNote jnote = getJNote(notes[i]);
				if (!jnote.isStemUp() && jnote.isHeadInverted()) {
					chordHasStemDownInverted = true;
					break;
				}
			}
			int accOffset = 1;
			int refAccidentedNote = -1;
			for (int i = notes.length - 1; i >= 0; i--) {
				//from highest to lowest
				if (notes[i].hasAccidental()) {
					JNote jnote = getJNote(notes[i]);
					if (refAccidentedNote == -1) {
						refAccidentedNote = i;
					} else {
						//if interval between reference and this note is smaller than
						//a sixth, accidentals are moved on the left
						//else accidental is at normal position, set note as referenec
						Interval interv = new Interval(notes[refAccidentedNote], notes[i], null);
						if (interv.getLabel() < Interval.SIXTH) {
							accOffset++;
						} else {
							accOffset = 1;
							refAccidentedNote = i;
						}
					}
					
					//For stem down chord with inverted heads, add a .5 (a note head)
					//offset to the accidental position
					float w_accOffset = accOffset;
					if (!jnote.isStemUp()
							&& !jnote.isHeadInverted()
							&& chordHasStemDownInverted)
						w_accOffset += .5f;
					if (w_accOffset != 1)
						jnote.setAccidentalPositionOffset(w_accOffset);
					
					double accWidth = jnote.getNotePosition().getX() - jnote.getBase().getX();
					accidentalsWidth = Math.max(accidentalsWidth, accWidth);
				}
			}
		}
		
		m_noteHeadX = getBase().getX()
			+ (graceNotesAfter?0:graceNotesWidth)
			;//+ accidentalsWidth;
		
		Point2D chordBase = new Point2D.Double(m_noteHeadX, getBase().getY());
		
		double chordWidth = 0;
		
		// TODO: setBase for decorations

		if (m_normalizedChords==null) {
			
			// calculate slur/tie position last because slurs/ties must
			//  go over any decorations
			//if (note.getSlurDefinition()!=null)
			/*for (int i=0; i<m_sNoteInstances.length; i++) {
				((JNote)m_sNoteInstances[i]).calcSlursAndTiesPosition();
			}*/

			double biggestStemX = -1;
			for (int i=0; i<m_sNoteInstances.length; i++) {
				m_sNoteInstances[i].setBase(chordBase);
				if (m_sNoteInstances[i].getStemBeginPosition().getX()>biggestStemX)
					biggestStemX = m_sNoteInstances[i].getStemBeginPosition().getX();
				if (m_sNoteInstances[i].getWidth() > chordWidth)
					chordWidth = m_sNoteInstances[i].getWidth();
			}
			//realign all stems
			for (int i=0; i<m_sNoteInstances.length; i++) {
				Point2D stemBegin = m_sNoteInstances[i].getStemBeginPosition();
				Point2D newStemBegin = new Point2D.Double (biggestStemX, stemBegin.getY());
				m_sNoteInstances[i].setStemBeginPosition(newStemBegin);
			}
		}
		else
		{
			for (int i=0; i<m_normalizedChords.length; i++) {
				Point2D newBase = (Point2D) chordBase.clone();
				if (accidentalsWidth > 0) {
					double tmpAccWidth = 0;
					//determinate accidental width of the chord
					for (int j = 0; j < m_normalizedChords[i].m_sNoteInstances.length; j++) {
						JNote jnote = m_normalizedChords[i].m_sNoteInstances[j];
						double accWidth = jnote.getNotePosition().getX() - jnote.getBase().getX();
						tmpAccWidth = Math.max(tmpAccWidth, accWidth);
					}
					newBase.setLocation(newBase.getX() + accidentalsWidth - tmpAccWidth,
										newBase.getY());
					if (newBase.getX() == chordBase.getX())
						newBase = chordBase;
				}
				m_normalizedChords[i].setBase(newBase);
				if (m_normalizedChords[i].getWidth() > chordWidth)
					chordWidth = m_normalizedChords[i].getWidth();
				//m_normalizedChords[i].onBaseChanged();
			}
		}

		//slur anchors
		if (m_normalizedChords == null) {
			ScoreMetrics metrics = getMetrics();
			slurUnderAnchor = m_sNoteInstances[0].getSlurUnderAnchor();
			slurUnderAnchorOutOfStem = stemUp
				?slurUnderAnchor
				:new Point2D.Double(getBoundingBox().getMinX(),
									getBoundingBox().getMaxY()+metrics.getSlurAnchorYOffset());
			slurAboveAnchor = m_sNoteInstances[m_sNoteInstances.length-1].getSlurAboveAnchor();
			slurAboveAnchorOutOfStem = stemUp
				?new Point2D.Double(getBoundingBox().getMaxX(),
									getBoundingBox().getMinY()-metrics.getSlurAnchorYOffset())
				:slurAboveAnchor;
				
			//calcDecorationPosition();
		}
		
		if (m_jGracenotes != null) {
			Point2D gracesPosition;
			if (!graceNotesAfter)
				gracesPosition = (Point2D) getBase().clone();
			else {
				gracesPosition = new Point2D.Double(
					m_noteHeadX+chordWidth
						+getMetrics().getGraceNotesSpacing()*2,
					getBase().getY()
				);
			}
			m_jGracenotes.setBase(gracesPosition);
		}

		m_width = (int)(graceNotesWidth/*+accidentalsWidth*/+chordWidth);
		//m_width = /*c_width + */graceNotesWidth + accidentalsWidth + chordWidth;
		//m_width = getBoundingBox().getWidth();
	}
	
	private JNote getJNote(Note n) {
		if (m_normalizedChords == null) {
			for (int i = 0; i < m_sNoteInstances.length; i++) {
				if (m_sNoteInstances[i].getMusicElement() == n)
					return m_sNoteInstances[i];
			}
		}
		else {
			for (int i = 0; i < m_normalizedChords.length; i++) {
				for (int j = 0; j < m_normalizedChords[i].m_sNoteInstances.length; j++) {
					if (m_normalizedChords[i].m_sNoteInstances[j]
							.getMusicElement() == n)
						return m_normalizedChords[i].m_sNoteInstances[j];
				}
			}
		}
		return null;
	}

	public Rectangle2D getBoundingBox() {
		Rectangle2D bb = new Rectangle2D.Double(getBase().getX(), getBase().getY(), 0, 0);
		if (m_normalizedChords == null) {
			for (int i = 0; i < m_sNoteInstances.length; i++) {
				bb.add(m_sNoteInstances[i].getBoundingBox());
			}
		} else {
			for (int i=0; i<m_normalizedChords.length; i++) {
				bb.add(m_normalizedChords[i].getBoundingBox());
			}
		}
		//take into account the graces notes when after, or
		//other...
		bb.setRect(bb.getX(), bb.getY(),
			Math.max(bb.getWidth(), getWidth()),
			bb.getHeight());
		return bb;
	}
	
	public double render(Graphics2D context){
		//super.render(context);
		//Stroke defaultStroke = context.getStroke();
		//JNote lowestElement = m_sNoteInstances[0];
		//JNote highestElement = m_sNoteInstances[m_sNoteInstances.length-1];
		if (m_normalizedChords==null) {
			for (int i=0; i<m_sNoteInstances.length; i++) {
				JNote n = m_sNoteInstances[i];
				n.render(context);
//System.out.println(n.getMusicElement().toString() +
//					":\tX=" + n.getStemBeginPosition().getX() +
//					",Y=" + n.getStemBeginPosition().getY() +
//					"\n\tisStemUp()=" + n.isStemUp() );
//				if (n instanceof JNotePartOfGroup) {
////System.out.println("\tstemYEnd="+((JNotePartOfGroup) n).getStemYEnd());
//				}

			}
		}
		else {
			for (int i=0; i<m_normalizedChords.length; i++)
				m_normalizedChords[i].render(context);
		}
		
		renderGraceNotes(context);
		renderChordName(context);
		renderDecorations(context);
		renderDynamic(context);
		renderAnnotations(context);

		//renderDebugBoundingBox(context);
		//renderDebugSlurAnchors(context);

		return m_width;
	}

	public void setStemUp(boolean isUp) {
		super.setStemUp(isUp);
		if (isUp) {
			//m_sNoteInstances[0] = new JChordNote(m_notes[0], m_sNoteInstances[0].getBase(), getMetrics());
			//JNote highestJNote = m_sNoteInstances[m_sNoteInstances.length-1];
			//Note highestNote = (Note)highestJNote.getMusicElement();
			//When the stem is up, the anchor is the highest note.
			//anchor = createAnchorNote(highestNote, highestJNote.getBase(), getMetrics());
			//m_sNoteInstances[m_sNoteInstances.length-1] = anchor;
			//Why recreate new JChordNotes???
			anchor = m_sNoteInstances[m_sNoteInstances.length-1];
		}
		else {
			//Replace the existing highest note
			//m_sNoteInstances[m_sNoteInstances.length-1] = new JChordNote(m_notes[m_notes.length-1],
			//		m_sNoteInstances[m_sNoteInstances.length-1].getBase(), getMetrics());
			//JNote lowestJNote = m_sNoteInstances[0];
			//Note lowestNote = (Note)lowestJNote.getMusicElement();
			// Replace the existing lowest note
			//When the stem is down, the anchor is the lowest note.
			//anchor = createAnchorNote(lowestNote, lowestJNote.getBase(), getMetrics());
			//m_sNoteInstances[0] = anchor;
			//Why recreate new JChordNotes???
			anchor = m_sNoteInstances[0];
		}
		//Apply the stem direction to the rest of the notes composing the chord.
		setAutoStem(false);
		for (int i=0; i<m_sNoteInstances.length; i++) {
			//m_sNoteInstances[i].setAutoStem(false);
			m_sNoteInstances[i].setStemUp(isUp);
			if (!(this instanceof JChordPartOfGroup)) {
				if (m_sNoteInstances[i] instanceof JChordNote) {
					((JChordNote) m_sNoteInstances[i]).setAnchor(m_sNoteInstances[i] == anchor);
				}
			}
		}
	}
	
	public void setAutoStem(boolean auto) {
		super.setAutoStem(auto);
		//Apply the stem direction to the rest of the notes composing the chord.
		if (m_normalizedChords!=null) {
			for (int i=0; i<m_normalizedChords.length; i++) {
				m_normalizedChords[i].setAutoStem(auto);
			}
		} else {
			for (int i=0; i<m_sNoteInstances.length; i++) {
				m_sNoteInstances[i].setAutoStem(auto);
			}
		}
	}

	public JScoreElement getScoreElementAt(Point point) {
		JScoreElement scoreEl = null;
		if(m_normalizedChords!=null)
			for (int i=0; i<m_normalizedChords.length && scoreEl==null; i++) {
				scoreEl = m_normalizedChords[i].getScoreElementAt(point);
			}
		else
		for (int i=0; i<m_sNoteInstances.length; i++) {
			scoreEl = m_sNoteInstances[i].getScoreElementAt(point);
			if (scoreEl!=null)
				return scoreEl;
		}
		return scoreEl;
	}

}
