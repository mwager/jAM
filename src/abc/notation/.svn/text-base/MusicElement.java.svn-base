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

/**
 * A tagging abstract class (was: interface) for all elements that are part of a
 * tune's music.
 */
public abstract class MusicElement implements Cloneable, Serializable {

	private MusicElementReference _reference = new MusicElementReference();

	/**
	 * Returns the reference of the element in the Tune.Music collection.<br>
	 */
	public MusicElementReference getReference() {
		return _reference;
	}
	
	public Object clone() throws CloneNotSupportedException {
		Object o = super.clone();
		((MusicElement) o)._reference = (MusicElementReference) _reference.clone();
		return o;
	}
}
