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
import java.util.ArrayList;

import abc.notation.Accidental;
import abc.notation.Clef;
import abc.notation.KeySignature;
import abc.notation.Note;
import abc.notation.MusicElement;
import abc.ui.scoretemplates.ScoreElements;

/** This class is in charge of rendering a key signature. */
class JKeySignature extends JScoreElementAbstract {
/* TODO: test every keys, TJM "Gracings" branch changes not included */
	private KeySignature key = null;
	private KeySignature previous_key = null;

	private double m_width = -1;
	private ArrayList chars = new ArrayList(); //ArrayList of char[]
	private ArrayList positions = new ArrayList(); //ArrayList of Point2D

	public JKeySignature(KeySignature keyV, Point2D base, ScoreMetrics c) {
		this(keyV, null, base, c);
	}
	
	public JKeySignature(KeySignature keyV, KeySignature keyPrevious,
			Point2D base, ScoreMetrics c) {
		super(c);
		key = keyV;
		previous_key = keyPrevious;
		setBase(base);
	}

	public MusicElement getMusicElement() {
		return key;
	}
	
	public double getWidth() {
		return m_width; //suppose it has been calculated
	}

	protected void onBaseChanged() {
		ScoreMetrics c = getMetrics();
		Point2D m_base = getBase();
		// easier to read, and may speed up a little :)
		double noteHeight = c.getNoteHeight();
		double baseX = m_base.getX();
		double baseY = m_base.getY() - noteHeight/2;
		
		Clef clef = key.getClef();
		
		//TODO key accidentals depending on the clef
		//octave offset = 3.5
		double octaveOffset = 0;
		if (clef.isC())
			octaveOffset = 3.5;
		else if (clef.isF())
			octaveOffset = 3.5*2;
		//Calculate vertical position for # and b
		double FsharpY = baseY - (JClef.getOffset(new Note(Note.f), clef)-octaveOffset) * noteHeight;
		double CsharpY = baseY - (JClef.getOffset(new Note(Note.c), clef)-octaveOffset) * noteHeight;
		double GsharpY = baseY - (JClef.getOffset(new Note(Note.g), clef)-octaveOffset) * noteHeight;
		double DsharpY = baseY - (JClef.getOffset(new Note(Note.d), clef)-octaveOffset) * noteHeight;
		double AsharpY = baseY - (JClef.getOffset(new Note(Note.A), clef)-octaveOffset) * noteHeight;
		double EsharpY = baseY - (JClef.getOffset(new Note(Note.e), clef)-octaveOffset) * noteHeight;
		double BsharpY = baseY - (JClef.getOffset(new Note(Note.B), clef)-octaveOffset) * noteHeight;
		
		double BflatY = BsharpY;//baseY - JNote.getOffset(new Note(Note.B)) * noteHeight;
		double EflatY = EsharpY;//baseY - JNote.getOffset(new Note(Note.e)) * noteHeight;
		double AflatY = AsharpY;//baseY - JNote.getOffset(new Note(Note.A)) * noteHeight;
		double DflatY = DsharpY;//baseY - JNote.getOffset(new Note(Note.d)) * noteHeight;
		double GflatY = baseY - (JClef.getOffset(new Note(Note.G), clef)-octaveOffset) * noteHeight;
		double CflatY = CsharpY;//baseY - JNote.getOffset(new Note(Note.c)) * noteHeight;
		double FflatY = baseY - (JClef.getOffset(new Note(Note.F), clef)-octaveOffset) * noteHeight;
		
		//0=C, 1=D..., 6=B
		int[] sharpOrder = new int[] {3,0,4,1,5,2,6};
		int[] flatOrder = new int[] {6,2,5,1,4,0,3};
		double[] sharpYs = new double[] {FsharpY,CsharpY,GsharpY,DsharpY,AsharpY,EsharpY,BsharpY};
		double[] flatYs = new double[] {BflatY,EflatY,AflatY,DflatY,GflatY,CflatY,FflatY};
		
		Accidental firstAccidental, secondAccidental;

		if (key.isSharpDominant()) {
			firstAccidental = Accidental.SHARP;
			secondAccidental = Accidental.FLAT;
		} else /*if (key.hasOnlyFlats())*/ {
			firstAccidental = Accidental.FLAT;
			secondAccidental = Accidental.SHARP;
		}
		
		Accidental[] accidentals = key.getAccidentals();
		int cpt = 0;
		
		if ((previous_key != null) && !previous_key.equals(key)) {
			//Before switching to a new key, maybe we need to
			//print naturals
			Accidental accidental = previous_key.isSharpDominant()
					?Accidental.SHARP:Accidental.FLAT;
			int[] order = (accidental == Accidental.FLAT)
					?flatOrder:sharpOrder;
			char glyph = getMusicalFont().getAccidental(Accidental.NATURAL);
			double glyphWidth = getMetrics().getBounds(glyph).getWidth();
			double[] Ys = (accidental==Accidental.FLAT)
					?flatYs:sharpYs;
			Accidental[] previous_accidentals = previous_key.getAccidentals();
			for (int i = 0; i < order.length; i++) {
				if (!previous_accidentals[order[i]].isNatural()
					&& !accidentals[order[i]].equals(previous_accidentals[order[i]])) {
					chars.add(cpt, new char[]{glyph});
					positions.add(cpt, new Point2D.Double(baseX, Ys[i]));
					baseX += glyphWidth;
					m_width += glyphWidth;
					cpt++;
				}
			}
			if (cpt > 0) { //there are some naturals, so a little space
				baseX += glyphWidth;
				m_width += glyphWidth;
			}
		}
		
		for (int twoPasses = 1; twoPasses <= 2; twoPasses++) {
			Accidental accidental = twoPasses==1?firstAccidental:secondAccidental;
			int[] order = (accidental.isFlat())?flatOrder:sharpOrder;
			double[] Ys = (accidental.isFlat())?flatYs:sharpYs;
			if ((twoPasses == 2) && key.hasSharpsAndFlats()) {
				//A little space when changing accidental
				double litSpace = getMetrics().getBounds(getMusicalFont().getAccidental(Accidental.NATURAL)).getWidth();
				baseX += litSpace;
				m_width += litSpace;
			}
			for (int i = 0; i < order.length; i++) {
				if (accidentals[order[i]].getNearestOccidentalValue()
						== accidental.getNearestOccidentalValue()) {
					char glyph = getMusicalFont().getAccidental(accidentals[order[i]]);
					double glyphWidth = getMetrics().getBounds(glyph).getWidth();
					chars.add(cpt, new char[]{glyph});
					positions.add(cpt, new Point2D.Double(baseX, Ys[i]));
					baseX += glyphWidth;
					m_width += glyphWidth;
					cpt++;
				}
			}
		}
	}

	public double render(Graphics2D context){
		super.render(context);
		Color previousColor = context.getColor();
		setColor(context, ScoreElements.KEY_SIGNATURE);
		for (int i = 0, j = chars.size(); i < j; i++) {
			if (chars.get(i)!=null) {
				Point2D p = (Point2D) positions.get(i);
				context.drawChars((char[]) chars.get(i), 0, 1,
						(int)p.getX(), (int)p.getY());
			}
		}
		context.setColor(previousColor);
		return getWidth();
	}


}
