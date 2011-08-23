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
import abc.parser.AbcTokenType;

/** This class defines states used in finale state automata. */
public class State implements Cloneable
{
    /** Transitions from this state. */
    private Vector transitions;
    /** The array representation of transitions from this state. */
    private Transition[] m_transitionsArrayCache=null;
    /** <TT>true</TT> if this state is a token state. */
    private boolean isTokenState;
    /** The type of this state. */
    private TokenType m_type;

    /** Constructs a new state.
     * @param type The type of the state.
     * @param isTokenStateValue <TT>true</TT> if the state is a token state,
     * <TT>false</TT> otherwise. */
    public State(TokenType type, boolean isTokenStateValue)
    {
        m_type = type;
        isTokenState = isTokenStateValue;
        transitions = new Vector();
    }

    /** Returns the type of this state.
     * @return The type of this state. */
    public TokenType getType()
    { return m_type; }

    /** Returns an array containing transitions from this state.
     * @return an array containing transitions from this state. */
    public Transition[]  getTransitions()
    {
      if (m_transitionsArrayCache==null)
      {
        int size = transitions.size();
        m_transitionsArrayCache = new Transition[size];
        for (int i=0; i<size;i++)
          m_transitionsArrayCache[i] = (Transition)transitions.elementAt(i);
      }
      return m_transitionsArrayCache;

      //return (Transition[])(transitions.toArray(new Transition[transitions.size()]));
    }

    /** Returns the transition that can be activated for the specified character.
     * @param character A character.
     * @return The transition that can be activated for the specified character.
     * <TT>null</TT> is returned if no transition is specified from this state
     * for the given character. */
    public Transition getTransitionFor(char character)
    {
      Transition matchingTransition = null;
      int m_transitionsNb = transitions.size();
      int transitionIndex = 0;
      while (matchingTransition==null && transitionIndex<m_transitionsNb)
      {
        if (((Transition)transitions.elementAt(transitionIndex)).isPossible(character))
          matchingTransition = (Transition)transitions.elementAt(transitionIndex);
        else
          transitionIndex++;
      }
      return matchingTransition;
    }

    /** Returns <TT>true</TT> if this state has self transitions. A self
     * transition is a transition whose target state is equals to the source state.
     * @return <TT>true</TT> if this state has self transitions, <TT>false</TT>
     * otherwise. */
    public boolean hasSelfTransitions()
    {
      //boolean hasSelfTransitions = false;
      for (int i=0; i<transitions.size(); i++)
      {
        if (((Transition)transitions.elementAt(i)).isSelfTransition())
          return true;
      }
      return false;
    }

    /** Returns the number of transitions from this state.
     * @return the number of transitions from this state. */
    public int countTransitions()
    { return transitions.size();  }

    /** Sets the type of this state.
     * @param type The new type of this state. */
    public void setType(TokenType type)
    { m_type = type; }

    /** Sets if this state is a token state or not.
     * @param isToken <TT>true</TT> if this state is a token state,
     * <TT>false</TT> otherwise. */
    public void setTokenState(boolean isToken)
    { isTokenState = isToken; }

    /** Returns <TT>true</TT> if this state is a token state.
     * @return <TT>true</TT> if this state is a token state,
     * <TT>false</TT> otherwise. */
    public boolean isTokenState()
    { return isTokenState; }

    /** Adds a transition to this state.
     * @param transition The transition to be added to this state.
     * @see #removeTransition(scanner.Transition) */
    public void addTransition(Transition transition)
    {
      transitions.addElement(transition);
      transition.setSourceState(this);
      m_transitionsArrayCache=null;
    }

    /**Removes the given transition.
     * @param transition The transition to be removed.
     * @return <TT>true</TT> if the given transition was one of this state's
     * transition, <TT>false</TT> otherwise.
     * @see #addTransition(scanner.Transition) */
    public boolean removeTransition(Transition transition)
    {
      if(transitions.removeElement(transition))
      {
        transition.setSourceState(null);
        m_transitionsArrayCache=null;
        return true;
      }
      else
        return false;
    }

    /**
     * Performs an union between this state and the given one.
     * Union between a token state and a non token state will give a token state.
     * Union between 2 non token states will give a non token state.
     * Union between 2 token states is possible only if only one of them has
     * self transitions.
     * Union between 2 token states that both have self transitions will throw
     * a runtime exception.
     * Union between 2 token states that both haven't any self transition
     * will return the state given as paremeter
     * @param state a State
     * @return State The resulting union from this state and the given one.
     */
    public State union(State state)
    {
      if (isTokenState() && state.isTokenState())
      {
        //System.out.println(getType()+" " + isTokenState() + " union("
        //  + state.getType() + " " + state.isTokenState() + ")");
        //Should be there's but this is due to conflicts between token types.
        if (getType().equals(AbcTokenType.TEXT))
          return state;
        if (state.getType().equals(AbcTokenType.TEXT))
          return this;
        /*if (getType().equals(AbcTokenType.KEY_ACCIDENTAL))
          return this;
        if (state.getType().equals(AbcTokenType.KEY_ACCIDENTAL))
          return state;*/
      }
      if (isTokenState() && state.isTokenState())
       if (hasSelfTransitions() && state.hasSelfTransitions())
         throw new RuntimeException("BOTH ARE TOKENS ! : " + this.getType() + " and " + state.getType());
        else
        if (!hasSelfTransitions() && !state.hasSelfTransitions())
        {
          //System.err.println("warning : ambiguity between " + this.getType() + " and "
          //                   + state.getType() + " : returning " + state);
          return state;
        }
/*          (
        (hasSelfTransitions() && state.hasSelfTransitions())
        ||(!hasSelfTransitions() && !state.hasSelfTransitions())
          )
          )
        throw new RuntimeException("BOTH ARE TOKENS ! : " + this.getType() + " and " + state.getType());*/
      State returnedState = (State)clone();
      boolean hasSelfTransition = hasSelfTransitions();
      //boolean parameterSelfTransition = state.hasSelfTransitions();

      Transition[] targetTransitions = state.getTransitions();
      for (int i = 0; i < targetTransitions.length; i++)
      {
        Transition currentTargetTransition = (Transition)targetTransitions[i];
        currentTargetTransition = new Transition(currentTargetTransition.getTargetState(), currentTargetTransition.getChars());
        Transition[] returnedStateTransitions = returnedState.getTransitions();
        for (int j = 0; j < returnedStateTransitions.length; j++)
        {
          Transition currentTransition = (Transition)returnedStateTransitions[j];
          // Any self transition ?
          if (currentTransition.isSelfTransition() && currentTargetTransition.isSelfTransition())
            //are they all self transitions ?
            currentTransition.add(currentTargetTransition.getChars());
          else
          if (currentTransition.isSelfTransition())
          {
            returnedState.convertSelfTransition(currentTransition);
            returnedState = returnedState.union(state);
          }
          else
          if (currentTargetTransition.isSelfTransition())
          {
            state.convertSelfTransition(currentTargetTransition);
            returnedState = returnedState.union(state);
          }
          else
          {
            //NO self transition => manage intersections....
            char[] intersection = currentTransition.intersect(currentTargetTransition.getChars());
            // We have to manage an intersection....
            if (intersection.length != 0)
            {
              currentTransition.substract(intersection);
              if (currentTransition.getChars().length == 0)
                returnedState.removeTransition(currentTransition);
              currentTargetTransition.substract(intersection);

              State unionState = currentTransition.getTargetState().union(currentTargetTransition.getTargetState());
              Transition newtransition = new Transition(unionState, intersection);
              returnedState.addTransition(newtransition);
            }
          }
        } // end for
        if (currentTargetTransition.getChars().length != 0)
          returnedState.addTransition(currentTargetTransition);
      }
      //sets the name
      if (isTokenState())
      {
        if (state.isTokenState())
        {
          //both are tokens.
          //System.out.println("============================TWO TOKENS :" + this + "  " + state);
          if (hasSelfTransition)
            returnedState.setType(state.getType());
          // we cannot have both with self transitions.
        }
        else
        {
          //this is the only token =>return should have is name=> already the case with this .clone()
        }
      }
      else
      {
        if (state.isTokenState())
        {
          //this is not token but state is.
          returnedState.setTokenState(true);
          returnedState.setType(state.getType());
        }
        else
        {
          if (getType().equals(state.getType()))
            returnedState.setType(getType());
          else
          returnedState.setType(TokenType.UNKNOWN);
        }
      }
      return returnedState;
    }

    /** Convert the given self transition to a transition to a new state (a
     * clone of this one).
     * @param selfTransition The self transition to be converted. */
    private void convertSelfTransition(Transition selfTransition)
    {
      if (selfTransition.isSelfTransition() && selfTransition.getSourceState().equals(this))
      {
        removeTransition(selfTransition);
        State tempState = (State)clone();
        Transition transition = new Transition(tempState, selfTransition.getChars());
        addTransition(transition);
      }
    }

    public Object clone()
    {
      State state = new State(getType(), isTokenState());
      int size = transitions.size();
      for (int i=0; i<size; i++)
      {
        Transition currentTransition = (Transition) transitions.elementAt(i);
        //if (currentTransition.getTargetState().equals(this))
        if (currentTransition.isSelfTransition())
        {
          Transition trans = new Transition(state, currentTransition.getChars());
          state.addTransition(trans);
        }
        else
        {
          Transition trans = new Transition(currentTransition.getTargetState(), currentTransition.getChars());
          state.addTransition(trans);
        }
      }
      return state;
    }

    public String toString()
    {
      String string2return = null;
      if (isTokenState)
        string2return = "*"+m_type+"*";
      else
        string2return = m_type.toString();
      return string2return;
    }
}

