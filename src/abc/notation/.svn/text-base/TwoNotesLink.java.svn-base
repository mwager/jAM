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

/** This class abstracts any kind of relationship  between two notes. */
public class TwoNotesLink extends MusicElement implements Cloneable, Serializable {
	
	private static final long serialVersionUID = 4174972765782118146L;
	
	/** The note starting the link between the two notes. */	
	private MusicElementReference start = null;
	/** The ending the link between the two notes. */
	private MusicElementReference end = null;
	
	/** Default constructor. */
	protected TwoNotesLink(){
	}
	
	/** Returns the end of the two notes link.
	 * @return Returns the end of the two notes link. 
	 * <TT>null</TT> if not specified.
	 * @see #getStart() */
	public MusicElementReference getEnd() {
		return end;
	}
	
	/** Sets the end of the two notes link.
	 * @param end The note ending the relation between the two notes. 
	 * @see #getEnd() 
	 * @see #setStart(NoteAbstract) */
	public void setEnd(MusicElementReference end) {
		this.end = end;
	}

	public void setEnd(NoteAbstract end) {
		setEnd(end.getReference());
	}

	/** Returns the start of the two notes link.
	 * @return Returns the start of the two notes link. 
	 * <TT>null</TT> if not specified. 
	 * @see #getEnd() */
	public MusicElementReference getStart() {
		return start;
	}
	
	/** Sets the start of the two notes link.
	 * @param start The note starting the relation between the two notes. 
	 * @see #getStart() 
	 * @see #setEnd(NoteAbstract) */
	public void setStart(MusicElementReference start) {
		this.start = start;
	}
	
	public void setStart(NoteAbstract start) {
		setStart(start.getReference());
	}
	
	public String toString() {
		return "TwoNotesLink("+start+" ; "+end+")";
	}
	
	public Object clone() throws CloneNotSupportedException {
		//this is a shallow copy
		Object o = super.clone();
//			if (start != null)
//			((TwoNotesLink) o).start = (NoteAbstract) start.clone();
//			if (end != null)
//			((TwoNotesLink) o).end = (NoteAbstract) end.clone();
		return o;
	}
}