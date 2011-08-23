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

import java.lang.Character;

/** This class defines transitions between states that are used for defining
 * finale state automatas.
 * A transition can then be added to one and only one state.*/
public class Transition
{
   /** Characters that will activate this transition. */
   private char[] chars = null;
   /** The target state of this transition. */
   private State targetState = null;
   /** The source state of this transition. (automatically set
    * when adding this transition to a state. */
   private State sourceState = null;

    /** Constructs a new transition.
     * @param state The new state reached if this transition is activated.
     * @param characters Characters that will activate this transition. */
    public Transition(State state, char[] characters)
    {
        targetState = state;
        chars = characters;
    }

    public Transition(State state, char character)
    {
        targetState = state;
        chars = new char[1];
        chars[0] = character;
    }

    /** Checks if this transition would be activated with the given character.
     * @param character The character that may activate this transition.
     * @return <TT>true</TT> if the given character would activate the transition,
     * <TT>false</TT> otherwise. */
    public boolean isPossible(char character)
    {
      boolean isPossible = false;
      int index=0;
      int length = chars.length;
      while (!isPossible && index<length)
      {
        if (chars[index] == character)
          isPossible = true;
        else
          index++;
      }
      return isPossible;
    }

    /** Returns characters that activate this transition.
     * @return Characters that activate this transition. */
    public char[] getChars()
    { return chars; }

    /** Returns the target state of this transition.
     * @return The target state of this transition : the state that is reached
     * if this transition is activated. */
    public State getTargetState()
    { return targetState; }

    /** Returns the source state of this transition.
     * @return The source state of this transition : the state to which this
     * transition has been added. */
    public State getSourceState()
    { return sourceState; }

    /** Sets the target state of this transition.
     * @param state the target state of this transition. */
    public void setTargetState(State state)
    { targetState = state; }

	/** Returns <TT>true</TT> if this transition is a self transition. A
	 * transition is a self transition when its source state is equal to
	 * its target state.
	 * @return <TT>true</TT> if this transition is a self transition, <TT>false</TT>
	 * otherwise. */
    public boolean isSelfTransition()
    { return targetState.equals(sourceState); }

    /** Sets the source state of this transition.
     * This code is called by a state object when adding a transition to it. */
    void setSourceState(State state)
    {
      if (state==null)
        sourceState=null;
      else
      if (sourceState==null)
        sourceState = state;
      else
        throw new RuntimeException("Already assigned transition for " + this.toString());
    }

    /*public Transition intersect2(Transition transition)
    {
      char[] parameter = transition.getChars();
      char[] commonTemp = null;
      char[] thisOnlyTemp = new char[chars.length];
      char[] parameterOnlyTemp = new char[parameter.length];
      int commonLength = 0;
      int thisOnlyLength = 0;
      int parameterOnlyLength = 0;

      Transition returnedTransition = null;

      if (chars.length<parameter.length)
        commonTemp = new char[chars.length];
      else
        commonTemp = new char[parameter.length];

      for (int i=0; i<chars.length; i++)
      {
        if (contains(chars[i], parameter))
        {
           commonTemp[commonLength]=chars[i];
           commonLength++;
        }
        else
        {
          thisOnlyTemp[thisOnlyLength] = chars[i];
          thisOnlyLength++;
        }
      }

      for (int i=0; i<parameter.length; i++)
      {
        if (!contains(parameter[i], chars))
        {
           parameterOnlyTemp[parameterOnlyLength] = parameter[i];
           parameterOnlyLength++;
        }
      }

      char[] common = new char[commonLength];
      char[] thisOnly = new char[thisOnlyLength];
      char[] parameterOnly = new char[parameterOnlyLength];

      System.arraycopy(commonTemp, 0, common, 0, commonLength);
      System.arraycopy(thisOnlyTemp, 0, thisOnly, 0, thisOnlyLength);
      System.arraycopy(parameterOnlyTemp, 0, parameterOnly, 0, parameterOnlyLength);

      Transition[] returnedArray = null;
      if (commonLength!=0)
      {

        returnedTransition = new Transition(
                getTargetState().union(transition.getTargetState()), common);
      }
      return returnedTransition;
    }*/

    /** Adds new characters to this transition to activate it. */
    public void add(char[] characters)
    {
      //Transition returnedTransition =null;
      char[] allContainedCharactersTemp = new char[characters.length + chars.length];
      System.arraycopy(chars, 0, allContainedCharactersTemp, 0, chars.length);
      int index = chars.length;

      for (int i=0; i<characters.length;i++)
      {
        if (!contains(characters[i]))
        {
          allContainedCharactersTemp[index]=characters[i];
          index++;
        }
      }
      char[] allContainedCharacters = new char[index];
      System.arraycopy(allContainedCharactersTemp, 0, allContainedCharacters, 0, index);
      chars = allContainedCharacters;
    }

    /** Returns an array containing characters that are activating this transition
     * and that are also contained in the given array. Characters activating this
     * transition are left unchanged.
     * @param parameter An array of char.
     * @return An array containing characters that are activating this transition
     * and that are also contained in the given array. */
    public char[] intersect(char[] parameter)
    {
      int charsLength = chars.length;
      int parameterLength = parameter.length;
      char[] commonTemp = null;
      //char[] thisOnlyTemp = new char[charsLength];
      //char[] parameterOnlyTemp = new char[parameterLength];
      int commonLength = 0;
      //int thisOnlyLength = 0;
      //int parameterOnlyLength = 0;

      //Transition returnedTransition = null;

      if (charsLength<parameterLength)
        commonTemp = new char[charsLength];
      else
        commonTemp = new char[parameterLength];

      for (int i=0; i<charsLength; i++)
      {
        if (contains(chars[i], parameter))
        {
           commonTemp[commonLength]=chars[i];
           commonLength++;
        }
        /*else
        {
          thisOnlyTemp[thisOnlyLength] = chars[i];
          thisOnlyLength++;
        }*/
      }

      /*for (int i=0; i<parameterLength; i++)
      {
        if (!contains(parameter[i], chars))
        {
           parameterOnlyTemp[parameterOnlyLength] = parameter[i];
           parameterOnlyLength++;
        }
      }*/

      char[] common = new char[commonLength];
      //char[] thisOnly = new char[thisOnlyLength];
      //char[] parameterOnly = new char[parameterOnlyLength];

      System.arraycopy(commonTemp, 0, common, 0, commonLength);
      //System.arraycopy(thisOnlyTemp, 0, thisOnly, 0, thisOnlyLength);
      //System.arraycopy(parameterOnlyTemp, 0, parameterOnly, 0, parameterOnlyLength);

      //Transition[] returnedArray = null;
      return common;
    }

    /** Returns <TT>true<TT> if the given characters activate this transition.
     * @return <TT>true<TT> if the given characters activate this transition,
     * <TT>false</TT> otherwise.
     * @param characters An array of char.*/
    public boolean contains(char[] characters)
    {
      boolean contains = true;
      int index  = 0;
      while (contains && index<characters.length)
        if (!contains(chars[index], characters))
          contains=false;
        else
          index++;
      return contains;
    }

    /** Returns <TT>true<TT> if the given character activate this transition.
     * @param character A character.
     * @return <TT>true<TT> if the given character activate this transition,
     * <TT>false</TT> otherwise. */
    public boolean contains(char character)
    { return contains(character, chars); }

    /** Removes the given characters to the ones activating this transition.
     * @param characters Characters to be removed from the ones activating this
     * transition. */
    public void substract(char[] characters)
    {
      char[] thisOnlyTemp = new char[chars.length];
      int thisOnlyLength = 0;
      for (int i=0; i<chars.length; i++)
      {
        if (!contains(chars[i], characters))
        {
          thisOnlyTemp[thisOnlyLength] = chars[i];
          thisOnlyLength++;
        }
      }
      char[] thisOnly = new char[thisOnlyLength];
      System.arraycopy(thisOnlyTemp, 0, thisOnly, 0, thisOnlyLength);
      chars = thisOnly;
    }

    /** Returns <TT>true</TT> if the given array contains the given character. */
    private static boolean contains(char character, char[] characters)
    {
      boolean contains = false;
      int index = 0;
      while (!contains && index<characters.length)
        if (characters[index]==character)
          contains = true;
        else
          index++;
      return contains;
    }

    public String toString()
    {
      String stringReturned = "[";
      for (int i=0; i<chars.length; i++)
        stringReturned = stringReturned.concat((new Character(chars[i])).toString() + " ");
      stringReturned = stringReturned.concat("]-> " + getTargetState());
      return stringReturned;
    }

            /*if (getTargetState().isTokenState())
        {
          if (transition.getTargetState().isTokenState())
            System.err.println("INCONSISTANCE DANS LA FUSION !!!!!!");
          else
          {
              Transition transition1 = this;
              Transition transition2 = new Transition(
                  transition.getTargetState(), parameterOnly);
              returnedArray = new Transition[2];
              returnedArray[0] = transition1;
              returnedArray[1] = transition2;
          }
        }
        else
        {
          if (transition.getTargetState().isTokenState())
          {
              Transition transition1 = transition;
              Transition transition2 = new Transition(
                  getTargetState(), thisOnly);
              returnedArray = new Transition[2];
              returnedArray[0] = transition1;
              returnedArray[1] = transition2;
          }
          else
          {
            Transition transition1 = new Transition(
                getTargetState(), thisOnly);
            //String newStateName = getTargetState().getName() + " union " + transition.getTargetState().getName();
            Transition transition2 = new Transition(
                getTargetState().union(transition.getTargetState()), common);
            Transition transition3 = new Transition(
                transition.getTargetState(), parameterOnly);
            returnedArray = new Transition[3];
            returnedArray[0] = transition1;
            returnedArray[1] = transition2;
            returnedArray[2] = transition3;
          }
        }*/
}

