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
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import abc.notation.Clef;
import abc.notation.GracingType;
import abc.notation.MusicElement;
import abc.notation.Note;
import abc.ui.scoretemplates.ScoreAttribute;
import abc.ui.scoretemplates.ScoreElements;

/**
 * This class is in charge of rendering a single note and it's associated
 * accidentals, dynamics, and lyric syllables.
 */
class JNote extends JNoteElementAbstract {

	/** This position is independant from the note type (height or rhythm)
	 * and, as a way of consequence, can be used to render elements such as sharp,
	 * flats etc around the note.
	 * This is the base to compute the accidentalsPosition, the slurs positions etc. */
	protected Point2D notePosition = null;
	/** This position is used to render the note character in the score.
	 * This position may (relatively) differ from one note to another and cannot be
	 * used as a stable reference position to draw elements around the note. */
	protected Point2D displayPosition = null;
	/** Position to draw accidentals. <TT>null</TT> if no accidental for this note. */
	protected Point2D accidentalsPosition = null;
	/** Position to draw the graces notes, if graces are
	 * before the note, this position will be getBase */
	protected Point2D gracesPosition = null;

	protected Point2D articulationPosition = null;

	protected Point2D[] dotsPosition = null;
	/** The chars from the font that represent the note to be displayed. */
	protected char[] noteChars = null;
	/** The chars from the font that represent the accidentals to be displayed.
	 * <TT>null</TT> if the note has no accidental. */
	protected char[] accidentalsChars = null;
	/** For notes in a chord, if two (or more) notes too close have accidentals
	 * they are moved to the left.
	 */
	private float accidentalPositionOffset = 1;

	protected Point2D stemUpBeginPosition = null;
	protected Point2D stemDownBeginPosition = null;

	private double m_width = -1;
	private double boundingBox_width = -1;
	
	public JNote(Note noteValue, Clef clef, Point2D base, ScoreMetrics c) {
		super(noteValue, clef, base, c);
		note = noteValue;
		valuateNoteChars();

		if (!note.isRest()) {
			//if (noteValue.getHeight()<Note.B) {
			if ((getClef() == null) || noteValue.isLowerThan(getClef().getMiddleNote())) {
				setStemUp(true);
			} else {
				setStemUp(false);
			}
		}
		setBase(base);
	}

	public MusicElement getMusicElement() {
		return note;
	}
	
	public double getWidth() {
		return m_width; //suppose it has been calculated
	}

	/** Sets the Unicode value of the note as a char array.
	 * @see #noteChars
 	 */
	protected void valuateNoteChars() {
		short noteDuration = note.getStrictDuration();
		if (note.isRest()){
			noteChars = new char[] { getMusicalFont().getRestChar(noteDuration) };
			//System.out.println("duration of the rest is " + noteDuration);
		}
		else {
			if (isStemUp())
				noteChars = new char[] { getMusicalFont().getNoteStemUpChar(noteDuration) };
			else
				noteChars = new char[] { getMusicalFont().getNoteStemDownChar(noteDuration) };
		}
	}
	
	/** Sets the Unicode values of the stem [0] and the
	 * note head [1] for head inverted note
	 * @return char[2][]
 	 */
	protected char[][] valuateInvertedNoteChars() {
		short noteDuration = note.getStrictDuration();
		char[] c1 = null, c2 = null;
		if (note.isRest()){
			c1 = new char[] { getMusicalFont().getRestChar(noteDuration) };
		}
		else {
			if (noteDuration <= Note.HALF) {
				if (isStemUp())
					c1 = new char[] { getMusicalFont().getStemWithoutNoteUpChar(noteDuration) };
				else
					c1 = new char[] { getMusicalFont().getStemWithoutNoteDownChar(noteDuration) };
			}
			c2 = new char[] { getMusicalFont().getNoteWithoutStem(noteDuration) };
			//if the note in valuateNoteChar is without stem
			//remove the stem char (c1)
			//e.g.: JChordNote, JNotePartOfGroup
			if (c2[0] == noteChars[0]) {
				c1 = null;
			}
		}
		return new char[][] { c1, c2 };
	}

	/** Sets note stem direction.
	 * @param isUp true or false
	 */
	public void setStemUp(boolean isUp) {
		super.setStemUp(isUp);
		valuateNoteChars();
	}

	/**
	 * Moves the note to a new position in order to set the new stem
	 * begin position to the new location .
	 *
	 */
	public void setStemBeginPosition(Point2D newStemBeginPos) {
		double xDelta = newStemBeginPos.getX() - getStemBeginPosition().getX();
		double yDelta = newStemBeginPos.getY() - getStemBeginPosition().getY();
		//System.out.println("translating " + this + " with " + xDelta + "/" + yDelta);
		Point2D newBase = new Point2D.Double(getBase().getX()+xDelta, getBase().getY()+yDelta);
		setBase(newBase);
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

	// all sizing/positioning is derived in this method!!
	//
	protected void onBaseChanged() {
		//System.out.println("JNote.onBaseChanged : "+note);
		ScoreMetrics metrics = getMetrics();
		//can be note or grace note
		Dimension glyphDimension = metrics.getGlyphDimension(getNotationContext());
		//note glyph is used for vertical position of graces notes
		Dimension noteGlyphDimension = metrics.getGlyphDimension(ScoreMetrics.NOTATION_CONTEXT_NOTE);

		Point2D base = getBase();
		int noteY = 0;
		if (note.isRest())
			noteY = (int)(base.getY()-2*noteGlyphDimension.getHeight());
		else
			noteY = (int)(base.getY()-getOffset(note)*noteGlyphDimension.getHeight()
				- noteGlyphDimension.getHeight()/2 + glyphDimension.getHeight()/2);
		double graceNotesWidth = 0;
		boolean graceNotesAfter = false;
		if (m_jGracenotes != null) {
			if (note.getGracingType() == GracingType.ACCIACCATURA)
				graceNotesAfter = getTemplate().getAttributeBoolean(ScoreAttribute.ACCIACCATURA_AFTER_NOTE);
			graceNotesWidth = m_jGracenotes.getWidth()
				+ getMetrics().getGraceNotesSpacing();
			if (graceNotesAfter)
				graceNotesWidth += getMetrics().getGraceNotesSpacing();
			// + (getWidth()*SPACE_RATIO_FOR_GRACENOTES);
		}
		double accidentalsWidth = 0;
		try {
			if (note.hasAccidental()) {
				accidentalsChars = new char[] {getMusicalFont().getAccidental(note.getAccidental())};
				accidentalsWidth = 
					getMetrics().getBounds(accidentalsChars, getNotationContext()).getWidth() +
					Math.max(3,
						getTemplate().getAttributeSize(ScoreAttribute.ACCIDENTAL_SPACE)
						);
				accidentalsWidth *= Math.floor(getAccidentalPositionOffset());
				if (getAccidentalPositionOffset()
						!= Math.floor(getAccidentalPositionOffset())) {
					accidentalsWidth += glyphDimension.getWidth();
				}
			}
		} catch (IllegalArgumentException iae) {
			throw new IllegalArgumentException("Incorrect accidental for " + note + " : " + note.getAccidental());
		}

		//System.out.println("note chars " + noteChars[0]);
		//double noteY =(int)(base.getY()-getOffset(note)*c.getNoteHeigth());
		//double noteY =(int)(base.getY()-getOffset(note)*glyphDimension.getHeigth());
		double noteX = base.getX()
			+ (graceNotesAfter?0:graceNotesWidth)
			+ accidentalsWidth;
		if (note.hasAccidental())
			accidentalsPosition = new Point2D.Double(
					base.getX()+(graceNotesAfter?0:graceNotesWidth),
					noteY-(glyphDimension.getHeight()/2));
		if (isHeadInverted() && !isStemUp())
			noteX += glyphDimension.getWidth();
		displayPosition = new Point2D.Double(noteX, noteY);
		notePosition = (Point2D)displayPosition.clone();
		if (isHeadInverted()) {
			if (isStemUp()) {
				notePosition.setLocation(notePosition.getX() + glyphDimension.getWidth(),
					notePosition.getY());
			} else {
				notePosition.setLocation(notePosition.getX() - glyphDimension.getWidth(),
					notePosition.getY());
			}
		}
		if (!isHeadInverted()) {
			stemUpBeginPosition = new Point2D.Double(notePosition.getX()
				+ glyphDimension.getWidth()*0.93,
				notePosition.getY()-glyphDimension.getHeight()/2);
			stemDownBeginPosition = new Point2D.Double(notePosition.getX(),
				notePosition.getY()-glyphDimension.getHeight()/2);
		} else {
			stemUpBeginPosition = new Point2D.Double(notePosition.getX(),
				notePosition.getY()-glyphDimension.getHeight()/2);
			stemDownBeginPosition = new Point2D.Double(notePosition.getX()
				+ glyphDimension.getWidth()*0.93,
				notePosition.getY()-glyphDimension.getHeight()/2);
		}
		//if (note.getHeight()>=Note.c && note.getStrictDuration() < Note.EIGHTH) {
		if (note.isHigherThan(getClef().getMiddleNote())
				&& note.getStrictDuration() < Note.EIGHTH) {
			stemUpBeginPosition.setLocation(stemUpBeginPosition.getX(), stemUpBeginPosition.getY()-glyphDimension.getHeight()/2);
			stemDownBeginPosition.setLocation(stemDownBeginPosition.getX(), stemDownBeginPosition.getY()-glyphDimension.getHeight()/2);
		}
		
		//reinit stem position
		setStemUp(isStemUp());
		//if stem is up and {TODO !isHeadInverted), and < eight
		//add an extra width.
		double extraWidth = 0;
		//if (!(this instanceof JNotePartOfGroup)
		if (((this instanceof JNote) || (this instanceof JChordNote))
				&& !(this instanceof JGraceNote)
				&& !note.isRest()
				&& ((isStemUp()
						&& ((note.getStrictDuration() <= Note.EIGHTH)
						|| isHeadInverted()))
					|| (!isStemUp() && isHeadInverted()))
				&& ((noteChars[0] != getMusicalFont().getNoteWithoutStem())
					|| isHeadInverted())
				) {
			extraWidth = glyphDimension.getWidth();
		}
		//calc dots needed extra space
		calcDotsPosition();
		if (dotsPosition != null) {
			double extraWidthDots = dotsPosition[dotsPosition.length - 1].getX()
				- notePosition.getX() - glyphDimension.getWidth()
				+ metrics.getBounds(getMusicalFont().getDot(), getNotationContext()).getWidth();
			extraWidth = Math.max(extraWidth, extraWidthDots);
		}
		
		if (m_jGracenotes != null) {
			if (!graceNotesAfter)
				gracesPosition = (Point2D) getBase().clone();
			else {
				gracesPosition = new Point2D.Double(
					noteX+glyphDimension.getWidth()+extraWidth
						+getMetrics().getGraceNotesSpacing()*2,
					getBase().getY()
				);
			}
		}
		
		m_width = (int)(graceNotesWidth+accidentalsWidth+glyphDimension.getWidth()+extraWidth);
		boundingBox_width = (int)(glyphDimension.getWidth()+extraWidth);

		onNotePositionChanged();
	}

	protected void onNotePositionChanged() {
		if (m_jGracenotes != null)
			m_jGracenotes.setBase(gracesPosition);
		calcDotsPosition();
		//calcDecorationPosition();
		calcSlursAndTiesPosition();
	}

	protected void calcDotsPosition() {
		if (note.countDots()!=0) {
			dotsPosition = new Point2D[note.countDots()];
			Dimension glyphDimension = getMetrics().getGlyphDimension(getNotationContext());
			//!isOnStaffLine() => the dot is between the lines
			double y = notePosition.getY()+glyphDimension.getHeight()*0.05; //-...*0.05
			if (!note.isRest() && isOnStaffLine()) {
				//the dot is over the staff line
				y = notePosition.getY()-glyphDimension.getHeight()*0.1;
			}
			double x = notePosition.getX() + glyphDimension.getWidth()*1.2; //1.2
			for (int i = 0; i < note.countDots(); i++) {
				x += Math.max(2.0, glyphDimension.getWidth()*.5);
				dotsPosition[i] = new Point2D.Double(x, y);
			}
		}
	}

	protected void calcSlursAndTiesPosition() {
		// calculate slur/tie position last because slurs/ties must
		//  go over any decorations
		//if (note.getSlurDefinition()!=null)
		ScoreMetrics metrics = getMetrics();
		Dimension glyphDimension = metrics.getGlyphDimension(getNotationContext());
		slurUnderAnchor = new Point2D.Double(displayPosition.getX() + glyphDimension.getWidth()/2,
				notePosition.getY()+metrics.getSlurAnchorYOffset());
		slurAboveAnchor = new Point2D.Double(displayPosition.getX() + glyphDimension.getWidth()/2,
				notePosition.getY()-glyphDimension.getHeight()-metrics.getSlurAnchorYOffset());
		//TODO improve position for rests
		slurUnderAnchorOutOfStem = (isStemUp() && !note.isRest())
			?slurUnderAnchor
			:new Point2D.Double(stemDownBeginPosition.getX(),//getBoundingBox().getMinX(),
					getBoundingBox().getMaxY()+metrics.getSlurAnchorYOffset());
		slurAboveAnchorOutOfStem = (isStemUp() && !note.isRest())
			?new Point2D.Double(stemUpBeginPosition.getX(),//getBoundingBox().getMaxX(),
								getBoundingBox().getMinY()-metrics.getSlurAnchorYOffset())
			:slurAboveAnchor;
		Rectangle2D tieAnchors = new Rectangle2D.Double(
				notePosition.getX(),
				notePosition.getY()-glyphDimension.getHeight(),
				glyphDimension.getWidth(),
				glyphDimension.getHeight());
		//ties start and end in a corner of the glyph rectangle
		tieStartAboveAnchor = new Point2D.Double(tieAnchors.getMaxX(), tieAnchors.getMinY());
		tieStartUnderAnchor = new Point2D.Double(tieAnchors.getMaxX(), tieAnchors.getMaxY());
		tieEndAboveAnchor = new Point2D.Double(tieAnchors.getMinX(), tieAnchors.getMinY());
		tieEndUnderAnchor = new Point2D.Double(tieAnchors.getMinX(), tieAnchors.getMaxY());
	}

	protected double getOffset(Note note) {
		return JClef.getOffset(note, getClef());
	}

	public Point2D getNotePosition(){
		return notePosition;
	}

	protected Point2D getDisplayPosition(){
		return displayPosition;
	}

	public Rectangle2D getBoundingBox() {
		Dimension glyphDimension = getMetrics().getGlyphDimension(getNotationContext());
		double noteGlyphHeight = glyphDimension.getHeight()*4;
		if (note.getStrictDuration() == Note.THIRTY_SECOND)
			noteGlyphHeight = glyphDimension.getHeight()*5;
		else if (note.getStrictDuration() == Note.SIXTY_FOURTH)
			noteGlyphHeight = glyphDimension.getHeight()*6;
		else if (note.getStrictDuration() == Note.WHOLE)
			noteGlyphHeight = glyphDimension.getHeight();
		if (note.isRest()) {
			return new Rectangle2D.Double(
					getBase().getX(),
					getBase().getY()-getMetrics().getStaffCharBounds().getHeight(),
					m_width,
					getMetrics().getStaffCharBounds().getHeight()
				);
		}
		else if (isStemUp()) {
			return new Rectangle2D.Double(
					(int)getBase().getX(),//,
					(int)(displayPosition.getY()-noteGlyphHeight),
					(int)m_width,//boundingBox_width,
					noteGlyphHeight);
		}
		else {
			return new Rectangle2D.Double(
					(int)getBase().getX(),//(int)(displayPosition.getX()),
					(int)(displayPosition.getY()-glyphDimension.getHeight()),
					(int)m_width,//boundingBox_width,
					noteGlyphHeight);
		}
	}

	public double render(Graphics2D g){
		super.render(g);
		renderExtendedStaffLines(g, getMetrics(), getBase());
		Color previousColor = g.getColor();
		if (this instanceof JGraceElement)
			setColor(g, ScoreElements.GRACENOTE);
		else
			setColor(g, ScoreElements.NOTE);
		renderAccidentals(g);
		renderGraceNotes(g);
		renderDots(g);
		renderNoteChars(g);
		g.setColor(previousColor);
		renderDecorations(g);
		renderDynamic(g);
		renderChordName(g);
		renderAnnotations(g);
		
		//renderDebugBoundingBox(g);
		//renderDebugSlurAnchors(g);
		//renderDebugDecorationAnchors(g);
		
		return getWidth();
	}

	protected void renderNoteChars(Graphics2D gfx) {
		if (!(note.isRest() && note.isRestInvisible())) {
			if (note.isRest() || !isHeadInverted()) {
				gfx.drawChars(noteChars, 0, 1, (int)displayPosition.getX(), (int)displayPosition.getY());
			} else {
				char[][] chars = valuateInvertedNoteChars();
				if (chars[0] != null) // stem
					gfx.drawChars(chars[0], 0, 1, (int)displayPosition.getX(), (int)displayPosition.getY());
				if (chars[1] != null) // head
					gfx.drawChars(chars[1], 0, 1, (int)notePosition.getX(), (int)notePosition.getY());
			}
		}
	}

	protected void renderAccidentals(Graphics2D gfx) {
		if (accidentalsPosition!=null) {
			Color previousColor = gfx.getColor();
			setColor(gfx, ScoreElements.ACCIDENTAL);
			gfx.drawChars(accidentalsChars, 0, 1,
					(int)accidentalsPosition.getX(),
					(int)accidentalsPosition.getY());
			gfx.setColor(previousColor);
		}
	}
	
	/**
	 * Returns true if the note is on staff line (or extended
	 * line), e.g. in G clef, C E G B d f a c'...
	 * 
	 * Returns false if the note is between 2 lines
	 */
	private boolean isOnStaffLine() {
		//TODO if (staffLine.getClef() == Clef.G) {
		return JClef.isOnStaffLine(note, getClef());
		/*switch(note.getStrictHeight()) {
		case Note.C:
		case Note.E:
		case Note.G:
		case Note.B:
			return (note.getOctaveTransposition() % 2) == 0;
		default: //D F A
			return (note.getOctaveTransposition() % 2) == 1;
		}
		//}*/
	}

	protected void renderExtendedStaffLines(Graphics2D context, ScoreMetrics metrics, Point2D base){
		Color previousColor = context.getColor();
		setColor(context, ScoreElements.STAFF_LINES);
		
		//FIXME: "Gracing" branch changes not integrated here
		//used for width which vary from normal to grace note
		Dimension glyphDimension = metrics.getGlyphDimension(getNotationContext());
		//FIXME Breve and long are longer, so draws a longer extended staff line
		if (note.getStrictDuration() == Note.BREVE
				|| note.getStrictDuration() == Note.LONG) {
			Rectangle2D bounds = metrics.getBounds(
				new char[] {getMusicalFont().getNoteWithoutStem(note.getStrictDuration())},
				getNotationContext());
			glyphDimension = new Dimension((int)bounds.getHeight(),
					(int)bounds.getWidth());
		}
		//used for height
		Dimension noteGlyphDimension = metrics.getGlyphDimension(ScoreMetrics.NOTATION_CONTEXT_NOTE);
		int extSize = (int)glyphDimension.getWidth()/3;
		Note low = getClef().getNoteFirstExtendedLineLow();
		Note high = getClef().getNoteFirstExtendedLineHigh();
		if (note.getHeight()<=low.getHeight()){
			double currentOffset = getOffset(low);
			int currentPosition = (int)(base.getY()-1-currentOffset*noteGlyphDimension.getHeight()/1.5);
			double offset = getOffset(note);
			Stroke dfs = context.getStroke();
			context.setStroke(metrics.getStemStroke());
			while (currentOffset>=offset) {
				context.drawLine(
						(int)(displayPosition.getX()-extSize), currentPosition,
						(int)(displayPosition.getX()+glyphDimension.getWidth()+extSize), currentPosition);
				currentOffset--;
				currentPosition = (int)(currentPosition + noteGlyphDimension.getHeight());
				//System.out.println("current offset : " + currentOffset + " " + currentPosition);
			}
			context.setStroke(dfs);
		}
		else if (note.getHeight()>=high.getHeight()){
			double currentOffset = getOffset(high);
			int currentPosition = (int)(base.getY()-1-currentOffset*noteGlyphDimension.getHeight()-noteGlyphDimension.getHeight()/2);
			double offset = getOffset(note);
			Stroke dfs = context.getStroke();
			context.setStroke(metrics.getStemStroke());
			while (currentOffset<=offset) {
				context.drawLine(
						(int)(displayPosition.getX()-extSize), currentPosition,
						(int)(displayPosition.getX()+glyphDimension.getWidth()+extSize), currentPosition);
				currentOffset++;
				currentPosition = (int)(currentPosition - noteGlyphDimension.getHeight());
				//System.out.println("current offset : " + currentOffset + " " + currentPosition);
			}
			context.setStroke(dfs);
		}
		
		context.setColor(previousColor);
	}

	protected void renderDots(Graphics2D context){
		if (dotsPosition!=null) {
			char[] glyph = new char[] {
				getMusicalFont().getDot()
			};
			for (int i = 0; i < dotsPosition.length; i++) {
				context.drawChars(glyph, 0, 1,
						(int)dotsPosition[i].getX(), (int)dotsPosition[i].getY());
			}
		}
	}


	/**
	 * @return Returns the stemBeginPosition.
	 */
	public Point2D getStemBeginPosition() {
		if (isStemUp())
			return stemUpBeginPosition;
		else
			return stemDownBeginPosition;
		//return stemBeginPosition;
		/*new Point2D.Double(notePosition.getX()+ m_metrics.getNoteWidth()*0.93,
		notePosition.getY()-m_metrics.getNoteHeigth()/2);*/
	}

	/**
	 * @return Returns the stemDownBeginPosition.
	 */
	protected Point2D getStemDownBeginPosition() {
		return stemDownBeginPosition;
	}

	/**
	 * @param stemDownBeginPosition The stemDownBeginPosition to set.
	 */
	protected void setStemDownBeginPosition(Point2D stemDownBeginPosition) {
		this.stemDownBeginPosition = stemDownBeginPosition;
	}

	/**
	 * @return Returns the stemUpBeginPosition.
	 */
	protected Point2D getStemUpBeginPosition() {
		return stemUpBeginPosition;
	}

	/**
	 * @param stemUpBeginPosition The stemUpBeginPosition to set.
	 */
	protected void setStemUpBeginPosition(Point2D stemUpBeginPosition) {
		this.stemUpBeginPosition = stemUpBeginPosition;
	}
	
	/**
	 * For notes in a chord, if two (or more) notes too close have accidentals
	 * they are moved to the left. 
	 * 
	 * @return the offset expressed in accidental width (default 1)
	 */
	protected float getAccidentalPositionOffset() {
		return accidentalPositionOffset;
	}
	
	/**
	 * For notes in a chord, if two (or more) notes too close have accidentals
	 * they are moved to the left. 
	 * 
	 * @param i the offset expressed in accidental width (default 1)
	 */
	protected void setAccidentalPositionOffset(float i) {
		this.accidentalPositionOffset = i;
		onBaseChanged();
	}

}
