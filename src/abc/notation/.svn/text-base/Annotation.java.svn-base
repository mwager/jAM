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

/**
 * A annotation is a text placed near an element such as
 * notes, bar line...
 * 
 * In ABC format, the placement is controlled by following symbols :
 * <ul><li>^ above the staff
 * <li>_ under the staff
 * <li>&lt; left to the element
 * <li>&gt; right to the element
 * <li>@ controlled by rendition software
 * </ul>
 * 
 * An element can have multiple annotations
 */
public class Annotation extends MusicElement implements Cloneable {
	
	private static final long serialVersionUID = -5229829009603741539L;
	
	private String m_identifier = null;
	
	/** the content of the annotation. */
	private String m_text = null;
	
	/** Creates a new annotation.
	 * @param theContent */
	public Annotation(String theContent){
		m_text = theContent;
	}

	/** Creates a new annotation.
	 * @param theContent
	 * @param identifier Not displayed string, but useful if you want to
	 * remove/update some kind of annotation
	 */
	public Annotation(String theContent, String identifier){
		m_text = theContent;
		m_identifier = identifier;
	}

	/** Returns the text of the annotation. */
	public String getText() {
		return m_text;
	}
	
	public String getIdentifier() {
		return m_identifier;
	}
	
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
	public String toString() {
		return getText();
	}
	
}