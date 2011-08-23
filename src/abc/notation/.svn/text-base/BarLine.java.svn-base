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

/** This class defines bar lines. */
public class BarLine extends DecorableElement implements Cloneable
{
  private static final long serialVersionUID = -1007382315266780584L;
  
  /** The simple bar line type. Ex: | */
  public static final byte SIMPLE = 0;
  /** The repeat open bar line type. Ex: |: */
  public static final byte REPEAT_OPEN = 1;
  /** The repeat close bar line type. Ex: :| */
  public static final byte REPEAT_CLOSE = 2;
  /** The beginning of the tune. Ex: [| */
  public static final byte BEGIN = 3;
  /** The end of the tune. Ex: |] */
  public static final byte END = 4;
  /** The double bar. Ex: || */
  public static final byte DOUBLE = 6;
  /** Invisible bar. Ex: [|] (in ABC v2 format) */
  public static final byte INVISIBLE = 7;
  /** Simple thin dotted bar line. Ex .| (in ABC v2 format) */
  public static final byte DOTTED = 8;
  /** end and begin repeat. Ex ::, :|: */
  public static final byte BEGIN_AND_END_REPEAT = 9;
  
  
  /** The type of this bar line. */
  private byte m_type = SIMPLE;
  
  /** Default constructor.
   * Constructs a simple bar line. */
  public BarLine()
  {}

  /** Creates a new bar line with the corresponding type.
   * @param type The type of bar line to be created : <TT>SIMPLE</TT>,
   * <TT>REPEAT_OPEN</TT> or <TT>REPEAT_CLOSE</TT>. */
  public BarLine(byte type)
  { m_type = type; }

  /** Returns the type of this bar line.
   * @return The type of this bar line. */
  public byte getType()
  { return m_type; }

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
	/**
	 * Converts the specified string to a bar line type.
	 * 
	 * @param barLine
	 *            The string to be converted as a bar line. Possible values are
	 *            <TT>|</TT>, <TT>||</TT>, <TT>[|</TT>, <TT>|]</TT>,
	 *            <TT>:|</TT>, <TT>|:</TT>, <TT>::</TT>...
	 * @return The bar line type corresponding to the given string. <TT>null</TT>
	 *         is returned if no type matches the string.
	 */
	public static byte[] convertToBarLine(String barLine) {
		byte[] barlineTypes = null;
		if (barLine.equals("::")) {
			barlineTypes = new byte[2];
			barlineTypes[0] = BarLine.REPEAT_CLOSE;
			barlineTypes[1] = BarLine.REPEAT_OPEN;
			//barlineTypes[0] = BarLine.BEGIN_AND_END_REPEAT;
		} else if (barLine.equals("|")) {
			barlineTypes = new byte[] { BarLine.SIMPLE };
		} else if (barLine.equals("||")) {
			barlineTypes = new byte[] { BarLine.DOUBLE };
		} else if (barLine.equals("[|")) {
			barlineTypes = new byte[] { BarLine.BEGIN };
		} else if (barLine.equals("|]")) {
			barlineTypes = new byte[] { BarLine.END };
		} else if (barLine.equals(":|")) {
			barlineTypes = new byte[] { BarLine.REPEAT_CLOSE };
		} else if (barLine.equals("|:")) {
			barlineTypes = new byte[] { BarLine.REPEAT_OPEN };
		} else if (barLine.equals(".|")
				|| barLine.equals(".|.")
				|| barLine.equals(":")) {
			barlineTypes = new byte[] { BarLine.DOTTED };
		} else if (barLine.equals("[|]")
				|| barLine.equals("[]")) {
			barlineTypes = new byte[] { BarLine.INVISIBLE };
		}
		return barlineTypes;
	}

  /**
	 * Returns a string representation of this object.
	 * 
	 * @return A string representation of this object.
	 */
  public String toString()
  {
	  switch(m_type) {
	  case SIMPLE: return "|";
	  case REPEAT_OPEN: return "|:";
	  case REPEAT_CLOSE: return ":|";
	  case BEGIN: return "[|";
	  case END: return "|]";
	  case DOUBLE: return "||";
	  case INVISIBLE: return "[|]";
	  case DOTTED: return ".|";
	  case BEGIN_AND_END_REPEAT: return "::";
	  default: return null;
	  }
  }
}
