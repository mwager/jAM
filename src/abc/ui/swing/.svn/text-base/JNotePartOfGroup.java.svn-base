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
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import abc.notation.Clef;
import abc.notation.Note;
import abc.ui.scoretemplates.ScoreElements;

class JNotePartOfGroup extends JNote implements JGroupableNote {

	/*protected int stemX = -1;
	protected int stemYBegin = -1;  */
	protected int stemYEnd = -1;
	
	private boolean anchor = false;

	public JNotePartOfGroup(Note noteValue, Clef clef, Point2D base, ScoreMetrics c) {
		super(noteValue, clef, base, c);
		super.setAutoStem(true);
	}

	protected void valuateNoteChars() {
		// beamed notes are always 1/8th notes or less
		// so just display a stemless note - stems and beams
		// are drawn programmatically
		noteChars = new char[] { getMusicalFont().getNoteWithoutStem() };
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
	
	protected void onBaseChanged() {
		super.onBaseChanged();
		ScoreMetrics metrics = getMetrics();
		//used for width can vary if note or grace note
		Dimension glyphDimension = metrics.getGlyphDimension(getNotationContext());
		//note glyph is used for vertical position of normal and graces notes
		Dimension noteGlyphDimension = metrics.getGlyphDimension(ScoreMetrics.NOTATION_CONTEXT_NOTE);

		//correct what differs from SNote...
		//The displayed character is not the same.
		//noteChars = ScoreMetrics.NOTE;
		//The Y offset needs to be updated.
		int noteY = (int)(getBase().getY()-getOffset(note)*noteGlyphDimension.getHeight()
				- noteGlyphDimension.getHeight()/2 + glyphDimension.getHeight()/2);

		//apply the new Y offset to the note location
		int noteX = (int)displayPosition.getX();

		displayPosition.setLocation(noteX, noteY);

		int stemYBegin = (int)(noteY - glyphDimension.getHeight()/2);

		if (stemYEnd < 0) {
			// if stemYEnd hasn't been set give it a default
			if (isStemUp()) {
				//stemYBegin = (int)(displayPosition.getY() - glyphDimension.getHeight()/6);
				stemYEnd = (int)(displayPosition.getY()
						+ glyphDimension.getHeight()/2
						- metrics.getStemLengthForContext(getNotationContext()));
			} else {
				//stemYBegin = (int)(displayPosition.getY() + glyphDimension.getHeight()/6);
				stemYEnd = (int)(displayPosition.getY()
						- glyphDimension.getHeight()/2
						+ metrics.getStemLengthForContext(getNotationContext()));
			}
		}

		setStemUpBeginPosition(new Point2D.Double(noteX + Math.ceil(glyphDimension.getWidth()), stemYBegin));
		setStemDownBeginPosition(new Point2D.Double(noteX, stemYBegin));

		notePosition = new Point2D.Double(displayPosition.getX(), displayPosition.getY());
		if (isHeadInverted()) {
			if (isStemUp()) {
				notePosition.setLocation(notePosition.getX() + glyphDimension.getWidth(),
					notePosition.getY());
			} else {
				notePosition.setLocation(notePosition.getX() - glyphDimension.getWidth(),
					notePosition.getY());
			}
		}
		onNotePositionChanged();

	}

	public void setStemYEnd(int value) {
		stemYEnd = value;
	}

	public int getStemYEnd() {
		return stemYEnd;
	}

	/*public Point2D getStemBegin() {
		return new Point2D.Double(stemX, stemYBegin);
	}*/

	public Rectangle2D getBoundingBox() {
		Dimension glyphDimension = getMetrics().getGlyphDimension(getNotationContext());
		if (isStemUp()) {
			return new Rectangle2D.Double(
				(int)(getBase().getX()),
				(int)(stemYEnd),
				getWidth(),
				getStemBeginPosition().getY()-stemYEnd+glyphDimension.getHeight()/2);
		}
		else {
			return new Rectangle2D.Double(
				(int)(getBase().getX()),
				getStemBeginPosition().getY()-glyphDimension.getHeight()/2,
				getWidth(),
				stemYEnd-getStemBeginPosition().getY()+1+glyphDimension.getHeight()/2);
		}
	}

	public Point2D getEndOfStemPosition() {
		if(stemYEnd!=-1)
			return new Point2D.Double(getStemBeginPosition().getX(), stemYEnd);
		else
			throw new IllegalStateException();
	}

	public double render(Graphics2D context){
		super.render(context);
		//context.drawChars(noteChars, 0, 1, (int)displayPosition.getX(), (int)displayPosition.getY());

		//draw stem, except for whole+ (in chord)
		if (!note.isRest() && (note.getStrictDuration() < Note.WHOLE)) {
			Color previousColor = context.getColor();
			if (this instanceof JGraceElement)
				setColor(context, ScoreElements.GRACENOTE);
			else
				setColor(context, ScoreElements.NOTE);
		//	Stroke defaultS = context.getStroke();
		//	context.setStroke(getMetrics().getStemStroke());
		//	context.drawLine((int)getStemBeginPosition().getX()+1, (int)getStemBeginPosition().getY(),
		//			(int)getStemBeginPosition().getX()+1, stemYEnd);
		//	context.setStroke(defaultS);
			context.fillRect((int)Math.ceil(getStemBeginPosition().getX()),
					Math.min((int)getStemBeginPosition().getY(),stemYEnd),
					1,
					(int)Math.abs(stemYEnd - getStemBeginPosition().getY()));
			context.setColor(previousColor);
		}
		
		/* * /java.awt.Color previousColor = context.getColor();
		context.setColor(java.awt.Color.RED);
		context.drawLine((int)getStemBeginPosition().getX(), (int)getStemBeginPosition().getY(),
				(int)getStemBeginPosition().getX(), (int)getStemBeginPosition().getY());
				//(int)getNotePosition().getX(), (int)getNotePosition().getY());
		context.setColor(java.awt.Color.GREEN);
		Point2D m_base = getBase();
		context.drawLine((int)m_base.getX(), (int)m_base.getY(),
				(int)m_base.getX(), (int)m_base.getY());
		context.setColor(previousColor);/* */
		
		//renderDebugBoundingBox(context);
		//renderDebugSlurAnchors(context);

		return getWidth();
	}

	public void setAutoStem(boolean auto) {
	  // always false, instances are stemmed by the aggregating class
	  autoStem = false;

	}

	/**
	 * @return Returns the anchor.
	 */
	protected boolean isAnchor() {
		return anchor;
	}

	/**
	 * @param anchor The anchor to set.
	 */
	protected void setAnchor(boolean anchor) {
		this.anchor = anchor;
	}

}
