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
package abc.parser;

import java.util.Enumeration;
import java.util.regex.Pattern;

class AbcTextField
{
  public static final byte AREA = 1;
  public static final byte BOOK = 2;
  public static final byte COMPOSER = 3;
  public static final byte DISCOGRAPHY = 4;
  public static final byte ELEMSKIP = 5;
  public static final byte GROUP = 6;
  public static final byte HISTORY = 7;
  public static final byte INFORMATION = 8; //or instruction
  public static final byte NOTES = 9;
  public static final byte ORIGIN = 10;
  public static final byte RHYTHM = 11;
  public static final byte SOURCE = 12;
  public static final byte TITLE = 15;
  public static final byte TRANSCRNOTES = 13;
  public static final byte WORDS = 14;
  public static final byte FILEURL = 15;
  public static final byte AUTHOR_OF_LYRICS = 16; //v2.0 A: field
  
  public static final AbcTextReplacements bundle = AbcTextReplacements.getInstance();

  private byte m_type = 0;
  private String m_text = null;
  private String m_comment = null;

  public AbcTextField(byte type, String text)
  {
    m_type = type;
    setText(text);
  }

  public AbcTextField(byte type, String text, String comment)
  {
    m_type = type;
    setText(text);
    m_comment = comment;
  }

  public String getText()
  {return m_text;}
  
  /**
   * Sets the text replacing all escaped combination to unicode
   * char, e.g. <TT>\^a</TT> is replace by <TT>â</TT>
   * @param text
   */
  private void setText(String text) {
	  Enumeration e = bundle.getKeys();
	  while (e.hasMoreElements()) {
		  String key = (String) e.nextElement();
		  if (text.indexOf(key) != -1)
			  text = stringReplace(text, key, bundle.getString(key));
	  }
	  m_text = text;
  }
  
  /** String.replace(String arg0, String arg1) in java 1.5 */
  private String stringReplace(String text, String target, String replacement) {
	  return Pattern.compile(target.toString(), Pattern.LITERAL).matcher(
			  text).replaceAll(replacement);
  }

  public byte getType()
  {return m_type;}

  public String getComment()
  {return m_comment;}

  public void display ()
  {
  }
}
