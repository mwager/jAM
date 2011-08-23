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
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import abc.notation.Clef;
import abc.notation.MusicElement;
import abc.notation.Note;
import abc.ui.scoretemplates.ScoreElements;

/** This class is in charge of rendering a staff clef. */
class JClef extends JScoreElementAbstract {
	
	protected static double getOffset(Note note, Clef clef) {
		double positionOffset = 0;
		//TODO if (clef.isPerc())
		byte noteHeight = note.getStrictHeight();
		//position for G clef, line 2
		switch (noteHeight) {
			case Note.C : positionOffset = -1.5; break;
			case Note.D : positionOffset = -1;break;
			case Note.E : positionOffset = -0.5;break;
			case Note.F : positionOffset = 0;break;
			case Note.G : positionOffset = 0.5;break;
			case Note.A : positionOffset = 1;break;
			case Note.B : positionOffset = 1.5;break;
		}
		if (clef.isG()) {
			positionOffset += clef.getLineNumber() - 2;
		}
		else if (clef.isC()) { //C clef, line 3
			positionOffset += 3;
			positionOffset += clef.getLineNumber() - 3;
		}
		else { //F clef, line 4
			positionOffset += 2.5 + 3.5;
			positionOffset += clef.getLineNumber() - 4;
		}
		//clef octave +8 | -8
		positionOffset -= clef.getOctaveTransposition() * 3.5;
		//note octave
		positionOffset += note.getOctaveTransposition()*3.5;
		return positionOffset;
	}
	
	protected static boolean isOnStaffLine(Note note, Clef clef) {
		double offset = getOffset(note, clef);
		return offset != Math.round(offset);
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
	
	protected Clef m_clef = Clef.G;
	
	public JClef(Point2D base, Clef clef, ScoreMetrics c) {
		super(c);
		m_clef = clef;
		setBase(base);
	}
	
	public double getWidth() {
		if (m_clef.equals(Clef.NONE))
			return 0;
		return getMetrics().getBounds(
				getMusicalFont().getClef(m_clef)
			).getWidth()
			+getMetrics().getNoteWidth()/2;
		//return 3*getMetrics().getNoteWidth();
	}
	
	protected void onBaseChanged() {
	}
	
	public MusicElement getMusicElement() {
		return m_clef;
	}
	
	public double render(Graphics2D context){
		super.render(context);
		/*char[] chars2 = {ScoreMetrics.STAFF_SIX_LINES};
		context.drawChars(chars2, 0, chars2.length, 
				(int)m_base.getX(), (int)(m_base.getY()));*/
		if (!m_clef.equals(Clef.NONE)) {
			Color previousColor = context.getColor();
			setColor(context, ScoreElements.CLEF);
			char[] chars = {getMusicalFont().getClef(m_clef)};
			context.drawChars(chars, 0, chars.length, 
					(int)(getBase().getX()+getMetrics().getNoteWidth()/4),
					(int)(getBase().getY()
						- getMetrics().getNoteHeight()
							*(m_clef.getLineNumber()-1)));
			context.setColor(previousColor);
		}
		return getWidth();
	}
}
