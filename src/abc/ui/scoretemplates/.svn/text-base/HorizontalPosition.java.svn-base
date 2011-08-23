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

public abstract class HorizontalPosition {
	
	public static final byte LEFT = 1;
	public static final byte CENTER = 2;
	public static final byte RIGHT = 3;
	public static final byte STAFF_LEFT = 4;
	public static final byte STAFF_RIGHT = 5;
	public static final byte LEFT_TAB = 6;

	public static String toString(byte h) {
		switch (h) {
		case LEFT: return "left";
		case CENTER: return "center";
		case RIGHT: return "right";
		case STAFF_LEFT: return "staff left";
		case STAFF_RIGHT: return "staff right";
		case LEFT_TAB: return "left tab";
		default: return "<unknown horizontal align>";
		}
	}
	
	public static byte toHorizontalPosition(String s) {
		s = s.trim().toLowerCase();
		if (s.equals("left")) return LEFT;
		else if (s.equals("center")) return CENTER;
		else if (s.equals("right")) return RIGHT;
		else if (s.equals("staff left")) return STAFF_LEFT;
		else if (s.equals("staff right")) return STAFF_RIGHT;
		else if (s.equals("left tab")) return LEFT_TAB;
		else throw new RuntimeException("unknown horizontal align: "+s);
	}

}
