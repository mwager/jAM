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
package abc.notation;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeMap;

import scanner.PositionableInCharStream;

/**
 * A Music is a collection of {@link abc.notation.Voice}s
 * containing {@link MusicElement music elements} (notes,
 * bars...), for a {@link abc.notation.Part} of a
 * {@link abc.notation.Tune} (part "A", "B",... or at least
 * the default part).
 */
public class Music implements Cloneable, Serializable {

	private static final long serialVersionUID = 5411161761359626571L;

	protected transient NoteAbstract lastNote = null;

	private TreeMap m_bars = new TreeMap();

	private short m_firstBarNumber = 1;
	
	private TreeMap m_voices = new TreeMap();
	
	private char m_partLabel = ' ';
	
	protected void setPartLabel(char c) {
		m_partLabel = c;
	}

	public Music() {
		this((short) 1);
	}

	public Music(short firstBarNo) {
		super();
		m_firstBarNumber = firstBarNo;
		m_bars.put(new Short((short) m_firstBarNumber), new Bar(
				(short) m_firstBarNumber, 0));
	}
	
	/**
	 * Returns a Map of <code>Byte(voiceNumber) => Voice</code>
	 */
	public Collection getVoices() {
		return m_voices.values();
	}
	
	/**
	 * Concatene the given music object to current one
	 * @param music
	 */
	public void append(Music music) {
		//if appending a music from a part
		//add a PartLabel to all voices
		if (music.m_partLabel != ' ') {
			Iterator it = m_voices.values().iterator();
			while (it.hasNext()) {
				((Voice)it.next()).addElement(new PartLabel(music.m_partLabel));
			}
		}
		Iterator it = music.getVoices().iterator();
		while (it.hasNext()) {
			Voice v = (Voice) it.next();
			getVoice(v.getVoiceNumber()).addAll(v);
		}
	}
	
	/**
	 * Returns the asked voice, create it if needed.
	 * @param voiceNumber
	 */
	public Voice getVoice(int voiceNumber) {
		Byte B = new Byte((byte)voiceNumber);
		if (m_voices.get(B) == null) {
			Voice v = new Voice((byte)voiceNumber, m_firstBarNumber);
			v.setPartLabel(m_partLabel);
			m_voices.put(B, v);
		}
		return (Voice) m_voices.get(B);
	}

	/** For compatibility, return iterator on first voice
	 * @deprecated use {@link #getVoice(byte)}.iterator() 
	 */
	public Iterator iterator() {
		return getVoice((byte)1).iterator();
	}
	
	/** @deprecated use {@link #getVoice(byte)} */
	public void addElement(MusicElement element) {
		getVoice((byte)1).addElement(element);
	}

	/**
	 * Add an element to the specified voice.
	 * 
	 * This is just a shorten way to call
	 * <code>myMusic.getVoice(voiceNumber).addElement(element)</code>
	 * @param voiceNumber
	 * @param element
	 */
	public void addElement(byte voiceNumber, MusicElement element) {
		getVoice(voiceNumber).addElement(element);
	}
	
	/**
	 * Maybe could be used...? add an element to all voices
	 * be sure to add it first in the voice, are last when
	 * all elements have been added...
	 * @param element
	 */
	public void addToAllVoices(MusicElement element) {
		Iterator it = m_voices.values().iterator();
		while (it.hasNext()) {
			Voice v = (Voice) it.next();
			v.addElement(element);
		}
	}

	/**
	 * Return true if the bar is empty or contains only barline and spacer(s).
	 * False if barline contain other kind of music element
	 * 
	 * @param bar
	 */
	public boolean barIsEmptyForAllVoices(Bar bar) {
		Iterator it = m_voices.values().iterator();
		while (it.hasNext()) {
			Voice v = (Voice) it.next();
			if (!v.barIsEmpty(bar))
				return false;
		}
		return true;
	}

	public Object clone() throws CloneNotSupportedException {
		Object o = super.clone();
		((Music) o).m_bars = (TreeMap) m_bars.clone();
		((Music) o).m_voices = (TreeMap) m_voices.clone();
		return o;
	}

	/**
	 * Returns the score element location at the specified offset.
	 * 
	 * @param offset
	 *            An offset in a char stream.
	 * @return The score element location at the specified offset.
	 * @deprecated use {@link #getElementAtStreamPosition(int)}
	 * FIXME this is specific to ABC format... should be elsewhere
	 * than in Music object
	 */
	public MusicElement getElementAt(int offset) {
		return getElementAtStreamPosition(offset);
	}
	/**
	 * Returns the score element location at the specified offset.
	 * 
	 * @param offset
	 *            An offset in a char stream.
	 * @return The score element location at the specified offset.
	 * FIXME this is specific to ABC format... should be elsewhere
	 * than in Music object
	 */
	public MusicElement getElementAtStreamPosition(int offset) {
		Iterator it = m_voices.values().iterator();
		while (it.hasNext()) {
			Voice v = (Voice) it.next();
			int size = v.size();
			MusicElement current = null;
			for (int i = 0; i < size; i++) {
				current = (MusicElement) v.elementAt(i);
				if (current instanceof PositionableInCharStream) {
					PositionableInCharStream pos = (PositionableInCharStream) current;
					if (pos.getPosition().getCharactersOffset() <= offset
							&& (pos.getPosition().getCharactersOffset() + pos
								.getLength()) >= offset)
						return current;
				}
			}
		}
		return null;
	}
	
	private boolean voiceExists(byte voiceNumber) {
		return m_voices.get(new Byte(voiceNumber)) != null;
	}

	/**
	 * Returns an element for the given reference, <TT>null</TT> if not found
	 * 
	 * @param ref
	 * @return
	 */
	public MusicElement getElementByReference(MusicElementReference ref) {
		if (voiceExists(ref.getVoice())) {
			Iterator it = getVoice(ref.getVoice()).iterator();
			while (it.hasNext()) {
				MusicElement element = (MusicElement) it.next();
				if (element.getReference().equals(ref))
					return element;
			}
		}
		return null;
	}

	/**
	 * Returns the highest note between two music elements. <TT>MultiNote</TT>
	 * instances are ignored.
	 * 
	 * @param elmtBegin
	 *            The music element where to start (included) the search of the
	 *            highest note.
	 * @param elmtEnd
	 *            The music element where to end (included) the search of the
	 *            highest note.
	 * @return The highest note or multinote between two music elements. <TT>null</TT>
	 *         if no note has been found between the two music elements.
	 * @throws IllegalArgumentException
	 *             Thrown if one of the music elements hasn't been found in the
	 *             music or if the <TT>elmtEnd</TT> param is located before
	 *             the <TT>elmntBegin</TT> param in the music.
	 * @deprecated use {@link #getVoice(byte)}.{@link Voice#getHighestNoteBewteen(MusicElement, MusicElement) getHighest...}
	 */
	public NoteAbstract getHighestNoteBewteen(MusicElement elmtBegin,
			MusicElement elmtEnd) throws IllegalArgumentException {
		return getVoice((byte)1).getHighestNoteBewteen(elmtBegin, elmtEnd);
	}

	/**
	 * Returns the key signature of this tune.
	 * 
	 * @return The key signature of this tune.
	 */
	public KeySignature getKey() {
		return getVoice((byte)1).getKey();
	}

	/**
	 * Returns the last note that has been added to this score.
	 * 
	 * @return The last note that has been added to this score. <TT>null</TT>
	 *         if no note in this score.
	 * @deprecated use {@link #getVoice(byte)}.getLastNote()
	 */
	public NoteAbstract getLastNote() {
		return getVoice((byte)1).getLastNote();
	}

	/**
	 * @param elmtBegin
	 *            (included)
	 * @param elmtEnd
	 *            (included)
	 * @return The lowest note or multinote between the two given score elements
	 *         if found. <TT>null</TT> if no note has been found between the
	 *         two music elements.
	 * @throws IllegalArgumentException
	 * @deprecated use {@link #getVoice(byte)}.{@link Voice#getLowestNoteBewteen(MusicElement, MusicElement) getLowest...}
	 */
	public NoteAbstract getLowestNoteBewteen(MusicElement elmtBegin,
			MusicElement elmtEnd) throws IllegalArgumentException {
		return getVoice((byte)1).getLowestNoteBewteen(elmtBegin, elmtEnd);
	}

	/**
	 * Returns a collection of Note between begin and end included
	 * 
	 * @param elmtBegin
	 * @param elmtEnd
	 * @return a Collection of NoteAbstract (Note or MultiNote)
	 * @throws IllegalArgumentException
	 * @deprecated use {@link #getVoice(byte)}.{@link Voice#getNotesBetween(MusicElement, MusicElement) getNotesBetween...}
	 */
	public Collection getNotesBetween(MusicElement elmtBegin,
			MusicElement elmtEnd) throws IllegalArgumentException {
		return getVoice((byte)1).getNotesBetween(elmtBegin, elmtEnd);
	}

	/**
	 * @return The shortest note in the tune.
	 */
	public Note getShortestNoteInAllVoices() throws IllegalArgumentException {
		Note shortestNote = null;
		Iterator it = m_voices.values().iterator();
		while (it.hasNext()) {
			Voice v = (Voice) it.next();
			Note shortInVoice = v.getShortestNote();
			if (shortInVoice != null) {
				if (shortestNote == null)
					shortestNote = shortInVoice;
				else if (shortInVoice.isShorterThan(shortestNote))
					shortestNote = shortInVoice;
			}
		}
		return shortestNote;
	}

	/**
	 * Returns <TT>true</TT> if this tune music has chord names, <TT>false</TT>
	 * otherwise.
	 * @deprecated use {@link #getVoice(byte)}.hasChordNames()
	 */
	public boolean hasChordNames() {
		return getVoice((byte)1).hasChordNames();
	}

	private boolean hasObject(Class musicElementClass) {
		Iterator it = m_voices.values().iterator();
		while (it.hasNext()) {
			Voice v = (Voice) it.next();
			if (v.hasObject(musicElementClass))
				return true;
		}
		return false;
	}

	/**
	 * Returns <TT>true</TT> if this tune music has part label(s), <TT>false</TT>
	 * otherwise.
	 */
	public boolean hasPartLabel() {
		return hasObject(PartLabel.class);
	}

	/**
	 * Returns <TT>true</TT> if this tune music has tempo, <TT>false</TT>
	 * otherwise.
	 */
	public boolean hasTempo() {
		return hasObject(Tempo.class);
	}

	/** @deprecated use {@link #getVoice(byte)}.indexOf() */
	public int indexOf(MusicElement elmnt) {
		return getVoice((byte)1).indexOf(elmnt);
	}

}
