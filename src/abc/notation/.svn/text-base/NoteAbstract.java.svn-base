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

import java.util.Vector;

/** This is the abstract class to define notes or multi notes. */
public class NoteAbstract extends DecorableElement implements Cloneable
{

  private static final long serialVersionUID = 7275657390476103339L;
  
  private Note[] m_gracingNotes = null;
  private boolean generalGracing		= false;
  private boolean staccato			= false;
  private byte m_gracingType = GracingType.APPOGGIATURA;

  /** The number of dots for this note. */
  private byte m_dotted = 0;

  private Vector slurDefinitions = null;// new Vector(2);
  //protected SlurDefinition slurDefinition = null;
  /** <TT>true</TT> if this note is part of a slur, <TT>false</TT>
   * otherwise. */
  private boolean m_isPartOfSlur = false;

  //private boolean m_isTied = false;
  private TieDefinition tieDefinition = null;

  /** The tuplet this note may belongs to. <TT>null</TT>
   * if this note does not belong to any tuplet. */
  private Tuplet m_tuplet = null;

  /** Returns the gracing notes to be played with this note.
   * @return The gracing notes to be played with this note. <TT>null</TT> if
   * this note has no gracing notes.
   * @see #hasGracingNotes() */
  public Note[] getGracingNotes()
  { return m_gracingNotes; }
  
  /**
   * Returns the gracing type
   * @return {@link GracingType#APPOGGIATURA} or
   * {@link GracingType#ACCIACCATURA}
   */
  public byte getGracingType() {
	  return m_gracingType;
  }

  public void setGracingNotes(Note[] notes)
  { m_gracingNotes=notes;}
  
  public void setGracingType(byte b) {
	m_gracingType = b;  
  }

  /** Returns <TT>true</TT> if this note has gracings, <TT>false</TT> otherwise.
   * @return <TT>true</TT> if this note has gracings, <TT>false</TT> otherwise. */
  public boolean hasGracingNotes()
  { return (m_gracingNotes!=null && m_gracingNotes.length > 0);}

  /** Sets the number of dots for this note.
   * @param dotsNumber The number of dots for this note. */
  public void setDotted(byte dotsNumber)
  { m_dotted = dotsNumber; }

  /** Returns the dotted value of this note.
   * @return The dotted value of this note. Default is 0.
   * @deprecated replaced by countDots()
   * @see #countDots() */
  public byte getDotted()
  { return m_dotted; }

  /** Returns the number of dots for this note.
   * @return The number of dots for this note. Default is 0. */
  public byte countDots() {
	  return m_dotted;
  }

  /** Returns <TT>true</TT> if this note has a general gracing, <TT>false</TT> otherwise.
   * @return <TT>true</TT> if this note has a general gracing, <TT>false</TT> otherwise. */
  public boolean hasGeneralGracing()
  { return generalGracing;}

  /** Specifies if this note should be played with a general gracing or not.
   * @param hasGeneralGracing <TT>true</TT> if this note should be played with
   * a general gracing, <TT>false</TT> otherwise. */
  public void setGeneralGracing(boolean hasGeneralGracing)
  { generalGracing = hasGeneralGracing; }

  /** Returns <TT>true</TT> if this note should be played with staccato.
   * @return <TT>true</TT> if this note should be played with staccato,
   * <TT>false</TT> otherwise.
   * @see #setStaccato(boolean) */
  public boolean hasStaccato()
  { return staccato; }

  /** Sets the staccato playing style of this note.
   * @param staccatoValue <TT>true</TT> if this note should be played with staccato,
   * <TT>false</TT> otherwise.
   * @see #hasStaccato() */
  public void setStaccato (boolean staccatoValue)
  { staccato = staccatoValue; }

  /** Returns <TT>true</TT> if this Note is part of a slur.
   * @return <TT>true</TT> if this Note is part of a slur, <TT>false</TT>
   * otherwise. */
  public boolean isPartOfSlur()
  { return m_isPartOfSlur; }

  /** Return <TT>true</TT> if this note is part of a tuplet.
   * @return <TT>true</TT> if this note is part of a tuplet, <TT>false</TT>
   * otherwise. */
  public boolean isPartOfTuplet()
  { return m_tuplet!=null; }

  /** Returns the tuplet this note is part of.
   * @return The tuplet this note is part of. <TT>null</TT> is returned if
   * this note isn't part of a tuplet.
   * @see #isPartOfTuplet() */
  public Tuplet getTuplet()
  { return m_tuplet; }

  /** Sets if this note is part of a slur or not.
   * @param isPartOfSlur <TT>true</TT> if this note is part of a slur,
   * <TT>false</TT> otherwise. */
  public void setPartOfSlur(boolean isPartOfSlur)
  { m_isPartOfSlur = isPartOfSlur; }


  /** Sets the tuplet this note belongs to.
   * @param tuplet The tuplet this note belongs to. */
  void setTuplet(Tuplet tuplet)
  { m_tuplet = tuplet; }

  /**
   * @return Returns the slurDefinitions vector
   */
  public Vector getSlurDefinitions() {
	  if (slurDefinitions == null)
		  slurDefinitions = new Vector(2);
	  return slurDefinitions;
  }
  /**
   * @deprecated use {@link #getSlurDefinitions()} to get
   * the Vector of all slurs of the note, and test if
   * size equals zero, then note has no slurs.
   * @return a SlurDefinition (the last added) or
   * <TT>null</TT> if no slur has been added.
   */
  public SlurDefinition getSlurDefinition() {
	  if (slurDefinitions != null) {
		  if (slurDefinitions.size() > 0) {
			  return (SlurDefinition) slurDefinitions
			  	.elementAt(slurDefinitions.size() - 1);
		  }
	  }
	  return null;
  }

	public boolean isBeginingSlur() {
		if (slurDefinitions == null)
			return false;
		int i = 0;
		while (i < slurDefinitions.size()) {
			SlurDefinition slur = (SlurDefinition) slurDefinitions.elementAt(i);
			if (slur.getStart() != null) {
				if (slur.getStart().equals(getReference()))
					return true;
			}
			i++;
		}
		return false;
	}

	public boolean isEndingSlur() {
		if (slurDefinitions == null)
			return false;
		int i = 0;
		while (i < slurDefinitions.size()) {
			SlurDefinition slur = (SlurDefinition) slurDefinitions.elementAt(i);
			if (slur.getEnd() != null) {
				if (slur.getEnd().equals(getReference()))
					return true;
			}
			i++;
		}
		return false;
	}

  /**
   * @param slurDefinition The slurDefinition to set.
   */
  public void addSlurDefinition(SlurDefinition slurDefinition) {
  	getSlurDefinitions().addElement(slurDefinition);
  }
  /**
   * @deprecated see {@link #addSlurDefinition(SlurDefinition)}
   * because one Note can have multiple slurs definitions
   * @param slurDefinition
   */
  public void setSlurDefinition(SlurDefinition slurDefinition) {
	  addSlurDefinition(slurDefinition);
  }

  /** Sets the tie definition for this note.
   * @param tieDef The definition of the tie if this note is tied. <TT>NULL</TT> if the
   * note should not be tied.
   * @see #isTied() */
  public void setTieDefinition(TieDefinition tieDef) {
	  //m_isTied = isTied;
	  this.tieDefinition = tieDef;
  }

  public TieDefinition getTieDefinition() {
	  //m_isTied = isTied;
	  return tieDefinition;
  }

  /** Returns <TT>true</TT> if this note is beginning a tie.
   * @return <TT>true</TT> if this note is beginning a tie, <TT>false</TT>
   * otherwise. */
  public boolean isBeginningTie() {
	  return tieDefinition!=null && getReference().equals(tieDefinition.getStart());
  }

  /** Returns <TT>true</TT> if this note is ending a tie.
   * @return <TT>true</TT> if this note is ending a tie, <TT>false</TT>
   * otherwise. */
  public boolean isEndingTie() {
	  return tieDefinition!=null && getReference().equals(tieDefinition.getEnd());
  }


  /** Returns <TT>true</TT> if this note is tied.
   * @return <TT>true</TT> if this note is tied, <TT>false</TT> otherwise.
   * @see #setTieDefinition(TieDefinition) */
  public boolean isTied() {
	  return tieDefinition!=null;//isPartOfSlur() && (getSlurDefinition()==null || !getSlurDefinition().getEnd().equals(this));
  }



  /** Returns a String representation of this Object.
   * @return a String representation of this Object. */
  public String toString()
  {
    String string2Return = "";
    if (m_chord!=null) 				string2Return = string2Return.concat("\""+m_chord.getText()+"\"");
    if (generalGracing)
      string2Return = string2Return.concat("~");
    if (m_gracingNotes!=null)	string2Return = string2Return.concat("{"+m_gracingNotes.toString()+"}");
    if (staccato)					string2Return = string2Return.concat(".");
    if (m_decorations!=null)	string2Return = string2Return.concat("{"+m_decorations.toString()+"}");
    //string2Return = string2Return.concat(notes.toString());
    return string2Return;
  }

	public Object clone() throws CloneNotSupportedException {
		Object o = super.clone();
		NoteAbstract clone = (NoteAbstract) o;
		if (m_chord != null)
		clone.m_chord = (Chord) m_chord.clone();
		if (m_dynamic != null)
		clone.m_dynamic = (Dynamic) m_dynamic.clone();
		if (m_decorations != null)
		clone.m_decorations = (Decoration[]) m_decorations.clone();
		if (m_gracingNotes != null)
		clone.m_gracingNotes = (Note[]) m_gracingNotes.clone();
		//do not clone tuplet
		//if (m_tuplet != null)
		//clone.m_tuplet = (Tuplet) m_tuplet.clone();
		return o;
	}
}

