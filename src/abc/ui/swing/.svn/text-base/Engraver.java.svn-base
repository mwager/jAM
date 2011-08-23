/**
 * 
 */
package abc.ui.swing;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeSet;

import abc.notation.MultiNote;
import abc.notation.Music;
import abc.notation.Note;
import abc.notation.NoteAbstract;
import abc.notation.Tune;

/**
 * @author Sylvain Machefert
 *
 */
public class Engraver implements Serializable {
	
	public static final int DEFAULT = 0;

	public static final int NONE = -1;
	
	private static final long serialVersionUID = 2254019163765767727L;
	
	public static final int VARIATION_DEFAULT = 0;
	public static final int VARIATION_MAX = 100;
	public static final int VARIATION_MIN = -100;
	
	private int m_mode = DEFAULT;
	private int m_variation = VARIATION_DEFAULT;
	private HashMap spacesAfter;
	
	protected Engraver() {
		this(DEFAULT);
	}
	
	protected Engraver(int mode) {
		spacesAfter = new HashMap();
		setMode(mode);
	}

	protected Engraver(int mode, int variation) {
		spacesAfter = new HashMap();
		setMode(mode, variation);
	}
	
	/**
	 * Adapt the engraving to the tune, i.e. search for the
	 * shortest note in the tune. If shortest note is a quarter,
	 * we can reduce the space between quarter.
	 * @param tune
	 */
	protected void adaptToTune(Tune tune, ScoreMetrics metrics) {
		//reinit the values of the engraving mode
		setMode(m_mode, m_variation);
		
		if (m_mode != NONE) {
			//11=notes spacing at 45px (default font size)
			double ratio = 100 * (11 / metrics.getNotesSpacing()) - 100;
			int oldVariation = m_variation;
			setMode(m_mode, m_variation - (int)ratio);
			m_variation = oldVariation;
			
			Music music = tune.getMusic();
			Note shortestNote = music.getShortestNoteInAllVoices();
			int shortestDuration = Note.SIXTEENTH;
			if (shortestNote != null)
				shortestDuration = shortestNote.getDuration();
			//System.out.println("shortest note duration = "+shortest.getDuration());
			short min = /*(shortestDuration >= Note.QUARTER)
				? Note.SIXTEENTH
				: */Note.SIXTEENTH;
			if (shortestDuration > min) {
				TreeSet set = new TreeSet(spacesAfter.keySet());
				Object[] durations = set.toArray();
				int iMin = 0, iShortest = 0;
				for (int i = 0; i < durations.length; i++) {
					//System.out.println("durations["+i+"] = "+durations[i]);
					int j = ((Integer) durations[i]).intValue();
					if (j == min)
						iMin = i;
					if (j == shortestDuration)
						iShortest = i;
				}
				int offset = (iShortest>iMin)?iShortest - iMin:0;
				if (offset != 0) {
					for (int i = durations.length-1; i >= iShortest; i--) {
						//DEBUG
						/*String s = "";
						for (Iterator itSet = set.iterator(); itSet.hasNext();) {
							Integer i2 = (Integer) itSet.next();
							System.out.print(i2+"\t");
							s += getSpaceAfter(i2.intValue())+"\t";
						}
						System.out.println("\n"+s);*/
						setSpaceAfter(((Integer) durations[i]).intValue(),
							getSpaceAfter( ((Integer)durations[i-offset]).intValue())
							);
					}
				}
				//System.out.println(spaceAfter.keySet().toArray());
				//System.out.println(spaceAfter.entrySet().toArray());
			}
		}
	}
	
	public int getMode() {
		return m_mode;
	}

	private int[] getNearestDurations(int unknownDuration) {
		int[] ret = {Note.DOTTED_WHOLE, Note.SIXTY_FOURTH};
		for (Iterator it = spacesAfter.keySet().iterator(); it.hasNext();) {
			int i = ((Integer) it.next()).intValue();
			if (i > unknownDuration && i < ret[0])
				ret[0] = i;
			else if (i < unknownDuration && i > ret[1])
				ret[1] = i;
		}
		return ret;
	}
	
	protected double getNoteSpacing(JScoreElement e) {
		Note n1 = null;
		if (e instanceof JGroupOfNotes) {
			JGroupOfNotes jgon = (JGroupOfNotes) e;
			NoteAbstract na = jgon.m_notes[jgon.m_notes.length-1];
			if (na instanceof MultiNote)
				n1 = ((MultiNote) na).getShortestNote();
			else
				n1 = (Note) na;
		} else if (e instanceof JChord) {
			n1 = ((JChord) e).multiNote.getShortestNote();
		} else if (e instanceof JNote) {
			n1 = ((JNote) e).note;
		}
		return (n1==null)
			? 0
			: getSpaceAfter(n1.getDuration())/* * spaceFactor*/;
	}

	public double getSpaceAfter(int noteDuration) {
		if (m_mode == NONE)
			return 0;
		Integer i = new Integer(noteDuration);
		if (spacesAfter.containsKey(i))
			return ((Double) spacesAfter.get(i)).doubleValue();
		else {
			int longest = Note.SIXTY_FOURTH, shortest = Note.LONG;
			for (Iterator itDur = spacesAfter.keySet().iterator(); itDur.hasNext();) {
				int dur = ((Integer) itDur.next()).intValue();
				if (dur < shortest) shortest = dur;
				if (dur > longest) longest = dur;
			}
			if (noteDuration < shortest)
				//return getSpaceAfter(shortest);
				return ((Double) spacesAfter.get(new Integer(shortest))).doubleValue();
			else if (noteDuration > longest)
				//return getSpaceAfter(longest);
				return ((Double) spacesAfter.get(new Integer(longest))).doubleValue();
		}
/*		else if (noteDuration <= Note.SIXTY_FOURTH) {
			System.out.println("getSpaceAfter("+noteDuration+") <= "+Note.SIXTY_FOURTH);
			return getSpaceAfter(Note.SIXTY_FOURTH);
		}
		else if (noteDuration >= Note.DOTTED_WHOLE) {
			System.out.println("getSpaceAfter("+noteDuration+") >= "+Note.DOTTED_WHOLE);
			return getSpaceAfter(Note.DOTTED_WHOLE);
		}*/
		try {
			//System.out.println("Unknown note duration : "+noteDuration);
			int[] nearests = getNearestDurations(noteDuration);
			int topD = nearests[0], bottomD = nearests[1];
			//System.out.println(" --> nearests : top="+topD+", bottom="+bottomD);
			if (topD == bottomD)
				return getSpaceAfter(topD);
			float percent = (float)(noteDuration-bottomD)/(float)(topD-bottomD);
			//System.out.println(" --> % = "+(noteDuration-bottomD)+"/"+(topD-bottomD)+"="+percent);
			double topL = getSpaceAfter(topD);
			double bottomL = getSpaceAfter(bottomD);
			double ret = (double) ((percent*(topL-bottomL)) + bottomL);
			//System.out.println(" --> topL="+topL+", bottomL="+bottomL+" = "+ret);
			return ret;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return 0;
		}
	}
	
	public int getVariation() {
		return m_variation;
	}
	
	/**
	 * Sets the engraving mode
	 * <ul><li>{@link #NONE} : equal space between each note
	 * <li>{@link #DEFAULT} : smaller space for short note, bigger space for long notes, something that look nice ;)
	 * </ul>
	 * @param mode
	 */
	public void setMode(int mode) {
		setMode(mode, VARIATION_DEFAULT);
	}
	
	/**
	 * Sets the engraving mode
	 * <ul><li>{@link #NONE} : equal space between each note
	 * <li>{@link #DEFAULT} : smaller space for short note, bigger space for long notes, something that look nice ;)
	 * </ul>
	 * @param mode {@link #DEFAULT} or {@link #NONE}
	 * @param variation, in % negative to reduce, positive
	 * to improve space between notes.
	 */
	public void setMode(int mode, int variation) {
		//only reset if needed
		if (m_mode == mode && m_variation == variation)
			return;
		
		spacesAfter.clear();
		m_mode = mode;
		//bounds variation %
		//variation = Math.max(variation, -50);
		m_variation = Math.max(VARIATION_MIN,
						Math.min(VARIATION_MAX, variation));
		if (m_mode == DEFAULT) {
			double factor = 1 + variation/100f;
			setSpaceAfter(Note.DOTTED_WHOLE, 30*factor);
			setSpaceAfter(Note.WHOLE, 25*factor);
			setSpaceAfter(Note.DOTTED_HALF, 20*factor);
			setSpaceAfter(Note.HALF, 15*factor);
			setSpaceAfter(Note.DOTTED_QUARTER, 12*factor);
			setSpaceAfter(Note.QUARTER, 10*factor);
			setSpaceAfter(Note.DOTTED_EIGHTH, 7*factor);
			setSpaceAfter(Note.EIGHTH, 5*factor);
			setSpaceAfter(Note.DOTTED_SIXTEENTH, 2*factor);
			//invert factor
			factor = 1 - variation/100;
			setSpaceAfter(Note.SIXTEENTH, -1*factor);
			setSpaceAfter(Note.DOTTED_THIRTY_SECOND, -2*factor);
			setSpaceAfter(Note.THIRTY_SECOND, -3*factor);
			setSpaceAfter(Note.DOTTED_SIXTY_FOURTH, -4*factor);
			setSpaceAfter(Note.SIXTY_FOURTH, -5*factor);
		}
		else { //mode==NONE
			//do nothing, will always return 0
		}
	}
	
	public void setSpaceAfter(int noteLength, double space) {
		spacesAfter.put(new Integer(noteLength), new Double(space));
	}
	
}
