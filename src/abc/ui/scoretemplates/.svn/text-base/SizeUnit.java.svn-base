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
package abc.ui.scoretemplates;

import java.io.Serializable;

public class SizeUnit implements Cloneable, Serializable {

	private static final long serialVersionUID = -2224057024846190626L;

	/** Size expressed in <I>n</I> * gracenote height. n is float */
	public static final SizeUnit GRACENOTE_HEIGHT = new SizeUnit("gracenote height");
	
	/** Size expressed in <I>n</I> * gracenote width. n is float */
	public static final SizeUnit GRACENOTE_WIDTH = new SizeUnit("gracenote width");

	/** Size expressed in <I>n</I> * note height. n is float */
	public static final SizeUnit NOTE_HEIGHT = new SizeUnit("note height");
	
	/** Size expressed in <I>n</I> * note width. n is float */
	public static final SizeUnit NOTE_WIDTH = new SizeUnit("note width");

	/** Size expressed in percent of the default value */
	public static final SizeUnit PERCENT = new SizeUnit("%");
	
	/** Font size in pt */
	public static final SizeUnit PT = new SizeUnit("pt");
	
	/** Size in pixels */
	public static final SizeUnit PX = new SizeUnit("px");
	
	/** Size expressed in <I>n</I> * staff height. n is float */
	public static final SizeUnit STAFF_HEIGHT = new SizeUnit("staff height");
	
	/** Size expressed in <I>n</I> * staff char width. n is float */
	public static final SizeUnit STAFF_WIDTH = new SizeUnit("staff width");
	
	private String name;
	private SizeUnit(String s) {
		name = s;
	}
	public Object clone() { return new SizeUnit(name); }
	public boolean equals(Object o) {
		if (o instanceof SizeUnit)
			return ((SizeUnit)o).name.equals(name);
		else if (o instanceof String)
			return ((String)o).equals(name);
		else
			return super.equals(o);
	}
	public int hashCode() {
		return super.hashCode();
	}
	public String toString() {
		return name;
	}
}
