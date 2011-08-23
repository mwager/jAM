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

import abc.notation.Clef;
import abc.notation.MultiNote;
import abc.notation.Note;

class JChordPartOfGroup extends JChord implements JGroupableNote {
	
	public JChordPartOfGroup(MultiNote multiNote, Clef clef, ScoreMetrics metrics, Point2D base){
		super(multiNote, clef, metrics, base);
	}

	protected JNote createAnchorNote(Note note, Point2D base, ScoreMetrics metrics) {
		return new JNotePartOfGroup(note, getClef(), base, getMetrics());
	}

	protected JChord createNormalizedChord(MultiNote mNote, ScoreMetrics mtrx, Point2D base) {
		//Is this the fastest chord resulting from the decomposition of the original chord ?
		if (multiNote.getStrictDurations()[0]==mNote.getStrictDurations()[0])
			return new JChordPartOfGroup(mNote, getClef(), mtrx, base);
		else
			return new JChord(mNote, getClef(), mtrx, base);
	}

	public void setStemYEnd(int value) {
		if (m_normalizedChords!=null)
			((JChordPartOfGroup)m_normalizedChords[0]).setStemYEnd(value);
		else {
			m_stemYEndForChord = value;
			((JNotePartOfGroup)anchor).setStemYEnd(value);
		}
	}

	public int getStemYEnd() {
		if (m_normalizedChords!=null)
			return ((JChordPartOfGroup)m_normalizedChords[0]).getStemYEnd();
		else
			return ((JNotePartOfGroup)anchor).getStemYEnd();
	}

	public Point2D getStemBeginPosition() {
		if (m_normalizedChords!=null)
			return ((JChordPartOfGroup)m_normalizedChords[0]).getStemBeginPosition();
		else
			return ((JNotePartOfGroup)anchor).getStemBeginPosition();
	}

	public JNotePartOfGroup getReferenceNoteForGroup () {
		if (m_normalizedChords!=null)
			return ((JChordPartOfGroup)m_normalizedChords[0]).getReferenceNoteForGroup();
		else
			return (JNotePartOfGroup)anchor;
	}
}
