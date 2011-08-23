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

import java.util.Vector;

/** This class defines sets used to group token types while parsing. */
public class Set implements Cloneable
{
  /** A vector containing token types as <TT>Integer</TT>. */
  private Vector m_types = null;

  /** Creates a new set.
   * @param tokenTypes Token types to be put in the created set. */
  public Set(TokenType[] tokenTypes)
  {
    this();
    for (int i =0; i<tokenTypes.length ; i++)
      m_types.addElement(tokenTypes[i]);
  }

  private Set(int initialCapacity)
  {
    m_types = new Vector(initialCapacity);
  }

  /** Default constructor.
   * Constructs an empty set. */
  public Set()
  { m_types = new Vector(); }


  /** Creates a new set from the specified one.
   * @param set The set from which this set has to be initialized. */
  public Set(Set set)
  {
    this((Vector)set.m_types.clone());
  }

  /** Creates a new set containing the specified tokenType.
   * @param tokenType Token type to be put in the created set. */
  public Set(TokenType tokenType)
  {
    this();
    m_types.addElement(tokenType);
  }

  private Set(Vector types)
  { m_types = types; }

  /** Returns an array representation of the types contained in this set.
   * @return An array representation of the types contained in this set. An
   * array with size 0 is returned if the set is empty. */
  public TokenType[] getTypes()
  {
    TokenType[] types = new TokenType[m_types.size()];
    for (int i=0; i<m_types.size(); i++)
      types[i] = (TokenType)m_types.elementAt(i);
    return types;
  }

  /** Returns the number of elements in this set.
   * @return The number of elements in this set. */
  public int size()
  { return m_types.size(); }

  /** Adds the specified token type in the set. This token type is added even if
   * the set already contains it.
   * @param tokenType The token type added in the set. */
  public void add(TokenType tokenType)
  {
    if (m_types.contains(tokenType))
        throw new IllegalStateException("This token " + tokenType + " is already contained in " + this);
    m_types.addElement(tokenType);
  }

  /** Removes the spcified token type from this set.
   * @param tokenType The token type to be removed from this set.
   * @return <TT>true</TT> if the token type was contained in this set.
   * <TT>false</TT> otherwise. */
  public boolean remove(TokenType tokenType)
  {  return m_types.removeElement(tokenType); }

  /** Checks if a token type if contained in this set or not.
   * @param tokenType The type of token that may be contained in this set.
   * @return <TT>true</TT> if the token type is contained in this set.
   * <TT>false</TT> otherwise. */
  public boolean contains(TokenType tokenType)
  {
    for (int i=0; i<m_types.size(); i++)
    {
      if (m_types.elementAt(i).equals(tokenType))
        return true;
    }
    return false;
  }

  public boolean contains(Set aSet)
  {
    boolean contains = true;
    for (int i=0; i<aSet.m_types.size() && contains; i++)
      if (!contains((TokenType)aSet.m_types.elementAt(i)))
        contains=false;
    return contains;
  }

  /** Removes all elements of the given set from this set.
   * @param aSet Elements to be removed from this set. */
  public void remove(Set aSet)
  {
    Vector tokensFromSet = aSet.m_types;
    for (int i=0; i<tokensFromSet.size(); i++)
    {
      if (m_types.contains(tokensFromSet.elementAt(i)))
        remove((TokenType)tokensFromSet.elementAt(i));
    }
  }

  /** Creates a new set containing tokens types from this set AND tokens types
   * from the given set.
   * @param aSet The set to be unioned with this one.
   * @return A set containing tokens types from this set AND tokens types
   * from the given set. */
  public Set createUnion(Set aSet)
  {
    /*Set set2return = (Set)clone();
    Vector tokensFromSet = aSet.m_types;
    for (int i=0; i<tokensFromSet.size(); i++)
    {
      if (!m_types.contains(tokensFromSet.elementAt(i)))
        set2return.add((TokenType)tokensFromSet.elementAt(i));
    }
    return set2return;*/
    Set set2return = new Set(size()+aSet.size());
    Vector tokensFromSet = aSet.m_types;
    TokenType tk = null;
    for (int i=0; i<tokensFromSet.size(); i++)
    {
      tk = (TokenType)tokensFromSet.elementAt(i);
      if (!set2return.m_types.contains(tk))
        set2return.add(tk);
    }
    tokensFromSet=m_types;
    for (int i=0; i<tokensFromSet.size(); i++)
    {
      tk = (TokenType)tokensFromSet.elementAt(i);
      if (!set2return.m_types.contains(tk))
        set2return.add(tk);
    }
    return set2return;
  }

  /** Creates a new set containing tokens types from this set AND the specified
   * token type.
   * @param tokenType The token type to be added to this set to create the
   * union result.
   * @return A set containing tokens types from this set AND the specified
   * token type. */
  public Set createUnion(TokenType tokenType)
  {
    Set set2return = (Set)clone();
    if (!contains(tokenType))
      set2return.add(tokenType);
    return set2return;
  }

  /** Adds the tokens types from the specified set to this set (without
   * creating any new set).
   * @param aSet A set containing tokens types to be added to this set.
   * @return A reference on this. */
  public Set union(Set aSet)
  {
    Vector tokensFromSet = aSet.m_types;
    for (int i=0; i<tokensFromSet.size(); i++)
    {
      if (!m_types.contains(tokensFromSet.elementAt(i)))
        add((TokenType)tokensFromSet.elementAt(i));
    }
    return this;
  }

  /** Performs an union with the specified token type without creating any
   * new set.
   * @param tokenType The token type to be unioned with this set.
   * @return A reference on this. */
  public Set union(TokenType tokenType)
  {
    if (!contains(tokenType))
      add(tokenType);
    return this;
  }

  /** Returns a new set containing elements contained in this set AND in the
   * given one.
   * @param aSet The set to be intersected with this one.
   * @return A new set containing elements contained in this set AND in the
   * given one.*/
  public Set intersect(Set aSet)
  {
    Set set2return = (Set)clone();
    Vector tokensFromSet = aSet.m_types;
    //Vector thisTokens = this.toVector();

    for (int i=0; i<m_types.size(); i++)
    {
      if (!tokensFromSet.contains(m_types.elementAt(i)))
        set2return.remove((TokenType)m_types.elementAt(i));
    }
    return set2return;
  }

  public Object clone()
  { return new Set(this); }

  public boolean equals(Object o)
  {
    if (o instanceof Set)
    {
      Set aSet = (Set)o;
      int myTypesSize = this.m_types.size();
      if (myTypesSize==aSet.m_types.size())
      {
        boolean areEqual = true;
        for (int i=0; i<myTypesSize && areEqual; i++)
          if (!contains((TokenType)aSet.m_types.elementAt(i)))
            areEqual=false;
        return areEqual;
      }
      else
        return false;
    }
    else
      return super.equals(o);
  }

  /** Returns a string representation of this set.
   * @return A string representation of this set. */
  public String toString()
  {
    /*String st = "[";
    Iterator it = m_types.iterator();
    while (it.hasNext())
    {
      TokenType value = (TokenType)it.next();
      st = st.concat(value.toString());
      if (it.hasNext())
        st = st.concat(", ");
    }
    st = st.concat("]");*/
    return m_types.toString();
  }

}

