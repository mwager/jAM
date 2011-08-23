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

import abc.notation.Clef;
import abc.notation.Note;

class JChordNote extends JNotePartOfGroup {
	
	private boolean m_isLowest = false;
	private boolean m_isHighest = false;

	public JChordNote(Note noteValue, Clef clef, Point2D base, ScoreMetrics c) {
		super(noteValue, clef, base, c);
		//onBaseChanged();
		//System.err.println(toString() + ": note="+noteValue.toString());
	}
	
	public void setAutoStem(boolean auto) {
		autoStem = auto;
	}

	protected void valuateNoteChars() {
		if (!isAnchor()) {
			noteChars = new char[] { getMusicalFont().getNoteWithoutStem(note.getStrictDuration()) };
		} else {//isAnchor
			//from JNote.valuateNoteChars()
			if (isStemUp())
				noteChars = new char[] { getMusicalFont().getNoteStemUpChar(((Note) getMusicElement()).getStrictDuration()) };
			else
				noteChars = new char[] { getMusicalFont().getNoteStemDownChar(((Note) getMusicElement()).getStrictDuration()) };
		}
	}

	public double render(Graphics2D context){
		super.render(context);

		// visual debug
/*
		java.awt.Color previousColor = context.getColor();
		context.setColor(java.awt.Color.GREEN);
		context.drawLine((int)getStemBeginPosition().getX(), (int)getStemBeginPosition().getY(),
				(int)getStemEndPosition().getX(), (int)getStemEndPosition().getY());
		context.setColor(previousColor);
*/
		return getWidth();
	}

	/**
	 * return <TT>true</TT> if this note is the highest in the chord
	 */
	protected boolean isHighest() {
		return m_isHighest;
	}

	/**
	 * Sets to <TT>true</TT> to tells that this is note is the
	 * highest in the chord
	 */
	protected void setIsHighest(boolean highest) {
		m_isHighest = highest;
	}

	/**
	 * return <TT>true</TT> if this note is the lowest in the chord
	 */
	protected boolean isLowest() {
		return m_isLowest;
	}

	/**
	 * Sets to <TT>true</TT> to tells that this is note is the
	 * lowest in the chord
	 */
	protected void setIsLowest(boolean lowest) {
		m_isLowest = lowest;
	}
}
