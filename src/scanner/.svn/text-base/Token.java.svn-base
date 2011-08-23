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
package scanner;

/** Tokens are objects created by a <TT>scanner</TT> while scanning a stream of
 * characters. */
public class Token implements PositionableInCharStream
{
    private String m_value;
    private TokenType m_type;
    private CharStreamPosition m_position;

    /** Creates a new token with the specified values.
     * @param val The token value.
     * @param type The type of this token
     * @param position The position of this token : equals to the position
     * of the first character of this token. */
    public Token (String val, TokenType type, CharStreamPosition position)
    {
        m_value = val;
        m_type = type;
        m_position = position;
    }

    /** Returns the type of this token.
     * @return The type of this token. */
    public TokenType getType()
    { return m_type; }

    /** Returns the string of this token.
     * @return the string of this token. */
    public String getValue()
    { return m_value; }

    public int getLength()
    { return m_value.length(); }

    /** Returns the position of this token.
     * @return The position of this token, this is equals to the position
     * of the first character of this token. */
    public CharStreamPosition getPosition()
    { return m_position; }

    /** Returns a string representation of this token.
     * @return A string representation of this token. */
    public String toString()
    { return ("[\"" + m_value + "\", " + m_type + "]" + m_position); }
}

