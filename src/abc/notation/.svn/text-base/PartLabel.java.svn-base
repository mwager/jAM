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

/** A simple presentation element : the label of a Part */
public class PartLabel extends MusicPresentationElement implements Cloneable {

	private static final long serialVersionUID = -4712754516181803338L;
	
	private char m_label = ' ';
	
	protected PartLabel(char label) {
		this.m_label = label;
	}
	
	public char getLabel() {
		return m_label;
	}
	
	public boolean equals(Object o) {
		if (!(o instanceof PartLabel))
			return false;
		else
			return getLabel() == ((PartLabel)o).getLabel();
	}
	
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
