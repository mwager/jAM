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

/** This class enables the representation of a fraction. */
public class Fraction extends MusicElement implements Cloneable, Serializable
{
  private static final long serialVersionUID = 2832587773244232648L;
  private int numerator = 0;
  private int denominator = 1;

  /** Creates a new fraction with the specified numerator and denominator values.
   * @param numeratorValue The value of the numerator.
   * @param denominatorValue The value of the denominator.
   * @exception IllegalArgumentException Thrown if the denominator is equal
   * to 0. */
  public Fraction(int numeratorValue, int denominatorValue) throws IllegalArgumentException
  {
    setNumerator(numeratorValue);
    setDenominator(denominatorValue);
  }

  /** Sets the numerator of this fraction.
   * @param numeratorValue The numerator of this fraction. */
  public void setNumerator(int numeratorValue)
  { numerator = numeratorValue; }

  /** Returns the numerator of this fraction.
   * @return The numerator value of this fraction. */
    public int getNumerator()
  { return numerator; }

  /**
   * @param denominatorValue The denominator of this fraction.
   * @exception IllegalArgumentException Thrown if the denominator is equal
   * to 0. */
  public void setDenominator(int denominatorValue) throws IllegalArgumentException
  {
    if (denominator!=0)
      denominator = denominatorValue;
    else
      throw new IllegalArgumentException("Denominator can't be equal to 0 !");
  }

  public int getDenominator()
  { return denominator; }

  /** Returns the float value represented by this fraction
   * @return The fload value represented by this fraction. */
  public float floatValue()
  { return (numerator/denominator); }

  /** Multiplies this fraction by the specified fraction and returns
   * the result as a float. This fraction remains unchanged.
   * @param fraction A Fraction.
   * @return The result of the multiplication of this fraction by the specified
   * one. */
  public float multipliedBy (Fraction fraction)
  {
    int newNumerator = numerator * fraction.getNumerator();
    int newDenominator = denominator * fraction.getDenominator();
    return (newNumerator/newDenominator);
  }

  /** Returns a String representation of this fraction.
   * @return a String representation of this fraction. */

  public String toString()
  {
    if (denominator==1)
      return String.valueOf(numerator);
    else
    	return String.valueOf(numerator)+"/"
    		 + String.valueOf(denominator);
  }
  
  public Object clone() throws CloneNotSupportedException {
	  return super.clone();
  }
}

