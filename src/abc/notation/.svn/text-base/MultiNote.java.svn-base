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

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

/** A multi note is a group of notes that should be played together. */
public class MultiNote extends NoteAbstract implements Cloneable
{
  private static final long serialVersionUID = 8194309683281786117L;
  
  /** Notes contained in this multinote. */
  private Vector m_notes;

	/** Creates a new <TT>MultiNote</TT> from given notes.
	 * @param notes A Vector containing the <TT>NoteAbstract</TT> of this
	 * <TT>MultiNote</TT>. */
  	public MultiNote (Vector notes) {
		super();
		m_notes = fromLowestToHighest(notes);
		byte y = 1;
		for (Iterator it = m_notes.iterator(); it.hasNext();) {
			((Note) it.next()).getReference().setY(y++);
		}
  	}
  	
  	/** Returns <TT>true</TT> if this chord contains the specified note.
  	 * @param aNote A note instance.
  	 * @return <TT>true</TT> if this chord contains the specified note.
  	 * <TT>false</TT> otherwise. */
  	public boolean contains(Note aNote) {
  		return (m_notes.indexOf(aNote)!=-1);
  	}

  /** Returns the longest note of this multi note, based on its duration.
   * @return The longest note of this multi note. If several notes have the
   * same longest length, the first one is returned.
   * @see Note#getDuration() */
  public Note getLongestNote() {
	  /*float length = 0;
	  float currentNoteLength = 0;
	  Note maxNote = null;
	  for (int i=0; i<m_notes.size() && maxNote==null; i++) {
		  currentNoteLength = ((Note)(m_notes.elementAt(i))).getDuration();
		  if (currentNoteLength > length)
			  maxNote = (Note)m_notes.elementAt(i);
	  }
	  return maxNote;*/return getLongestNote(this.toArray());
  }
  
  /** Returns the note with the biggest duration from the given array of notes.
   * @param notes An array of notes.
   * @return The note with the biggest duration from the given array of notes.
   * @see Note#getDuration() */
  public static Note getLongestNote(Note[] notes) {
	  float length = 0;
	  float currentNoteLength = 0;
	  Note maxNote = null;
	  for (int i=0; i<notes.length && maxNote==null; i++) {
		  currentNoteLength = notes[i].getDuration();
		  if (currentNoteLength > length)
			  maxNote = notes[i];
	  }
	  return maxNote;
  }
  
  /** Returns all notes strictly shorter than the given strict duration.
   * @param notes An arry of notes.
   * @param aStrictDuration A strict duration (use constants defined class Note)
   * @return All notes strictly shorter than the given strict duration. <TT>null</TT>
   * is returned is no note if shorter than the given strict duration.
   * @see Note#getStrictDuration() */
  public static Note[] getNotesShorterThan(Note[] notes, int aStrictDuration) {
	  Vector shorterNotes = new Vector();
	  Note[] notesArray =null;
	  for (int i=0; i<notes.length; i++) {
		  if (notes[i].getStrictDuration()<aStrictDuration)
			  shorterNotes.addElement(notes[i]);
	  }
	  if (shorterNotes.size()>0) {
		  notesArray = new Note[shorterNotes.size()];
		  shorterNotes.toArray(notesArray);
	  }
	  return notesArray;
  }
  
  /** Returns the lowest note from the given array of notes.
   * @param notes An array of notes.
   * @return The lowest note from the given array of notes.
   * @see Note#isLowerThan(Note) */
  public static Note getLowestNote(Note[] notes) {
	  Note lowestNote = notes[0];
	  for (int i=1; i<notes.length; i++)
		  if (notes[i].isLowerThan(lowestNote))
			  lowestNote = notes[i];
	  return lowestNote;
  }
  
  /** Returns the highest note from the given array of notes.
   * @param notes An array of notes.
   * @return The highes note from the given array of notes.
   * @see Note#isHigherThan(Note) */
  public static Note getHighestNote(Note[] notes) {
	  Note highestNote = notes[0];
	  for (int i=1; i<notes.length; i++)
		  if (notes[i].isHigherThan(highestNote))
			  highestNote = notes[i];
	  return highestNote;
  }
  
  /** Returns the shortest note of this multi note, based on its duration.
   * @return The shortest note of this multi note. If several notes have the
   * same shortest length, the first one is returned.
   * @see Note#getDuration() */
  public Note getShortestNote() {
	  Note shortNote = (Note)m_notes.elementAt(0);
	  float length = shortNote.getDuration();
	  float currentNoteLength = 0;
	  for (int i=1; i<m_notes.size(); i++) {
		  currentNoteLength = ((Note)(m_notes.elementAt(i))).getDuration();
		  if (currentNoteLength < length)
			  shortNote = (Note)m_notes.elementAt(i);
	  }
	  return shortNote;
  }
  
  /** Returns the highest note among the notes composing this multi note.
   * @return The highest note among the notes composing this multi note.
   * @see #getLowestNote()
   * @see Note#isHigherThan(Note) */
  public Note getHighestNote() {
	  Note highestNote = (Note)m_notes.elementAt(0);
	  //short highestHeight = highestNote.getHeight();
	  //short currentNoteLength = 0;
	  for (int i=1; i<m_notes.size(); i++)
		  if (((Note)(m_notes.elementAt(i))).isHigherThan(highestNote))
			  highestNote = (Note)m_notes.elementAt(i);
	  return highestNote;
  }
  
  /** Returns the notes from this multinote that begin a tie.
   * @return Te notes from this multinote that begin a tie. <TT>null</TT> is
   * returned if no note begins a tie.
   * @see Note#isBeginningTie() */
  public Note[] getNotesBeginningTie() {
	  Vector notesBT = new Vector(m_notes.size());
	  for (int i=0; i<m_notes.size(); i++)
		  if (((Note)m_notes.elementAt(i)).isBeginningTie())
			  notesBT.addElement(m_notes.elementAt(i));
	  if (notesBT.size()>0) {
		  Note[] notesBTasArray = new Note[notesBT.size()];
		  notesBT.toArray(notesBTasArray);
		  return notesBTasArray;
	  }
	  else
		  return null;
  }
  
  public static Note[] excludeTiesEndings(Note[] notes) {
	  Vector withoutTiesEnding = new Vector();
	  Note[] withoutTiesEndingArray = null;
	  for (int i=0; i<notes.length; i++) {
		  if (!notes[i].isEndingTie())
			  withoutTiesEnding.addElement(notes[i]);
	  }
	  if (withoutTiesEnding.size()>0) {
		  withoutTiesEndingArray = new Note[withoutTiesEnding.size()];
		  withoutTiesEnding.toArray(withoutTiesEndingArray);
	  }
	  return withoutTiesEndingArray;
  }
  
  /** Returns the lowest note among the notes composing this multi note.
   * @return The lowest note among the notes composing this multi note.
   * @see Note#isLowerThan(Note)
   * @see #getHighestNote() */
  public Note getLowestNote() {
	  /*Note lowestNote = (Note)m_notes.elementAt(0);
	  for (int i=1; i<m_notes.size(); i++)
		  if (((Note)(m_notes.elementAt(i))).isLowerThan(lowestNote))
			  lowestNote = (Note)m_notes.elementAt(i);
	  return lowestNote;*/
	  return getLowestNote(this.toArray());
  }
  
  /** Returns <TT>true</TT> if the strict durations of all notes composing this
   * multi note have the same value.
   * @return <TT>true</TT> if the strict durations of all notes composing this
   * multi note have the same value, <TT>false</TT> otherwise.
   * @see Note#getStrictDuration() */
  public boolean hasUniqueStrictDuration() {
	  /*short strictDuration = ((Note)m_notes.elementAt(0)).getStrictDuration();
	  short currentDuration = 0;
	  for (int i=1; i<m_notes.size(); i++) {
		  currentDuration = ((Note)(m_notes.elementAt(i))).getStrictDuration();
		  if (currentDuration!=strictDuration)
			  return false;
	  }
	  return true;*/
	  return getStrictDurations().length==1;
  }
  
  /** Returns the strict durations composing this multi notes.
   * @return The strict durations composing this multi notes. The array is sorted
   * from shortest durations to longest ones. */
  public short[] getStrictDurations() {
	  Vector durations = new Vector();
	  short currentDuration = 0;
	  for (int i=0; i<m_notes.size(); i++) {
		  currentDuration = ((Note)(m_notes.elementAt(i))).getStrictDuration();
		  if (durations.indexOf(new Short(currentDuration))==-1)
			  durations.addElement(new Short(currentDuration));
	  }
	  if (durations.size()==0)
		  return null;
	  else {
		  //sort the durations
		  Vector sortedDurations = new Vector();
		  for (int i=0; i<durations.size(); i++) {
			  int j=0;
			  while (j<sortedDurations.size()
					  && ((Short)sortedDurations.elementAt(j)).shortValue()<
					  (((Short)durations.elementAt(i)).shortValue())
					  )
				  j++;
			  sortedDurations.insertElementAt(durations.elementAt(i),j);
		  }
		  short[] durationsAsArray = new short[sortedDurations.size()];
		  for (int i=0; i<sortedDurations.size(); i++)
			  durationsAsArray [i] = ((Short)sortedDurations.elementAt(i)).shortValue();
		  return durationsAsArray;
	  }
  }
  
  /** Normalizes this multi note by decomposing it into multinotes with
   * same strict duration.
   * @return An array of multinotes with unique strict duration. Such strict
   * durations are sorted by ascent order.
   * @see #hasUniqueStrictDuration() */
  public MultiNote[] normalize() {
	  Hashtable splitter = new Hashtable();
	  for (int i=0; i<m_notes.size(); i++) {
		  Note note = (Note)m_notes.elementAt(i);
		  Short key = new Short(note.getStrictDuration());
		  if (splitter.containsKey(key))
			  ((Vector)splitter.get(key)).addElement(note);
		  else {
			  Vector v = new Vector();
			  v.addElement(note);
			  splitter.put(key, v);
		  }
	  }
	  short[] strictDurations = getStrictDurations();
	  MultiNote[] normalizedChords = new MultiNote[strictDurations.length];
	  for (int i=0; i<strictDurations.length;i++) {
		  normalizedChords[i] = new MultiNote((Vector)splitter.get(new Short(strictDurations[i])));
	  }
	  return normalizedChords;
  }
  
  private static Vector fromLowestToHighest(Vector original){
	  Vector orderedNotes = new Vector();
	  for (int i=0; i<original.size(); i++) {
		  int j=0;
		  while (j<orderedNotes.size()
				  && ((Note)orderedNotes.elementAt(j)).isLowerThan((Note)original.elementAt(i)))
			  j++;
		  orderedNotes.insertElementAt(original.elementAt(i),j);
	  }
	  return orderedNotes;
  }
  
  /** Returns <TT>true</TT> if this multi note some accidentals (for at least one
   * of its note).
   * @return <TT>true</TT> if this multi note some accidentals (for at least one
   * of its note), <TT>false</TT> otherwise.
   * @see Note#hasAccidental() */
  public boolean hasAccidental() {
	  for (int i=1; i<m_notes.size(); i++)
		  if (((Note)(m_notes.elementAt(i))).hasAccidental())
			  return true;
	  return false;
  }
  
  /** Returns a new vector containing <B>clones</B> of all <TT>Note</TT> objects contained in
   * this multi note.
   * @return a new vector containing all <TT>Note</TT> objects contained in
   * this multi note.
   * @see #toArray() to get an array of notes not cloned */
  public Vector getNotesAsVector()
  { return (Vector)m_notes.clone(); }
  
  /** Returns notes composing this multinote as an array of notes, sorted
   * from the lowest note to the highest one.
   * @return Notes composing this multinote as an array of notes, sorted
   * from the lowest note to the highest one. */
  public Note[] toArray() {
	  if (m_notes.size()>0) {
		  Note[] notes = new Note[m_notes.size()];
		  return (Note[])m_notes.toArray(notes);
	  }
	  else
		  return null;
  }
  
	protected void setNotes(Note[] notes) {
		Vector v = new Vector(notes.length);
		for (int i = 0; i < notes.length; i++) {
			v.addElement(notes[i]);
		}
		m_notes = fromLowestToHighest(v);
		byte y = 1;
		for (Iterator it = m_notes.iterator(); it.hasNext();) {
			((Note) it.next()).getReference().setY(y++);
		}
	}


  public String toString()
  {
		String string2Return = super.toString();
	  for (int i = 0; i < m_notes.size(); i++) {
		  if (i == 0) string2Return = string2Return.concat("[");
		  else string2Return = string2Return.concat(":");
		Note n = (Note) m_notes.elementAt(i);
		if (n.getStrictHeight() == Note.REST) 	string2Return = string2Return.concat("z"); else
		if (n.getStrictHeight() == Note.C) 	string2Return = string2Return.concat("C"); else
		if (n.getStrictHeight() == Note.D) 	string2Return = string2Return.concat("D"); else
		if (n.getStrictHeight() == Note.E) 	string2Return = string2Return.concat("E"); else
		if (n.getStrictHeight() == Note.F) 	string2Return = string2Return.concat("F"); else
		if (n.getStrictHeight() == Note.G) 	string2Return = string2Return.concat("G"); else
		if (n.getStrictHeight() == Note.A) 	string2Return = string2Return.concat("A"); else
		if (n.getStrictHeight() == Note.B) 	string2Return = string2Return.concat("B"); /*else
		if (n.getStrictHeight() == Note.c) 	string2Return = string2Return.concat("c"); else
		if (n.getStrictHeight() == Note.d) 	string2Return = string2Return.concat("d"); else
		if (n.getStrictHeight() == Note.e) 	string2Return = string2Return.concat("e"); else
		if (n.getStrictHeight() == Note.f) 	string2Return = string2Return.concat("f"); else
		if (n.getStrictHeight() == Note.g) 	string2Return = string2Return.concat("g"); else
		if (n.getStrictHeight() == Note.a) 	string2Return = string2Return.concat("a"); else
		if (n.getStrictHeight() == Note.b) 	string2Return = string2Return.concat("b");*/
		if (n.getOctaveTransposition() == 1) 	string2Return = string2Return.concat("'"); else
		if (n.getOctaveTransposition() == -1) 	string2Return = string2Return.concat(",");
		if (n.getAccidental().isFlat())		string2Return = string2Return.concat("b");
		if (n.getAccidental().isSharp())	string2Return = string2Return.concat("#");
		//string2Return = string2Return.concat(relativeLength.toString());
	  }
	  string2Return = string2Return.concat("]");
	return string2Return;
  }

	public Object clone() throws CloneNotSupportedException {
		Object o = super.clone();
		if (m_notes != null)
		((MultiNote) o).m_notes = (Vector) m_notes.clone();
		return o;
	}
}