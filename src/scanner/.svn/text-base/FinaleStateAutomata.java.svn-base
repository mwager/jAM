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

/** This class defines finale state automatas.
 * @see AutomataDefinition */
public class FinaleStateAutomata
{
  /** The current state. */
  private State m_currentState = null;
  /** Characters sent to this automata. */
  private StringBuffer receivedCharacters = null;
  /** The definition of this automata. */
  private AutomataDefinition m_definition = null;

  /** Constructs a new automata. */
  public FinaleStateAutomata()
  { this(new AutomataDefinition()); }

  /** Creates a new automata from the specified definition.
   * @param def The definition of states and transitions of this automata. */
  public FinaleStateAutomata(AutomataDefinition def)
  { setDefinition(def); }

  /** Returns the definition of this automata.
   * @return the definition of this automata. */
  public AutomataDefinition getDefinition()
  { return m_definition;  }

  /** Sets the definition of this automata. Changing the definition of this
   * automata will reinitialize it.
   * @param definition The definition of states and transitions of this automata.
   * @see #initialize() */
  public void setDefinition(AutomataDefinition definition)
  {
    m_definition = definition;
    initialize();
  }

  /** Sends a character to this automata.
   * @param character The character to be sent to this automata. */
  public void sendChar(char character) throws NoTransitionFoundException
  {
    Transition matchingTransition = getTransitionFor(character);
    if (matchingTransition==null)
      throw new NoTransitionFoundException();
    else
    {
      m_currentState = matchingTransition.getTargetState();
      receivedCharacters.append(character);
    }
  }

  /** Returns the transition that can be activated from this automata's
   * current state with the specified character.
   * @param character A character.
   * @return The transition that can be activated from this automata's current
   * state. <TT>null</TT> is returned if no transition can be activated with
   * this character from the current state.
   * @see #getCurrentState() */
  public Transition getTransitionFor(char character)
  {
    return m_currentState.getTransitionFor(character);
 }

  /** Returns the starting state of this automata.
   * @return The starting state of this automata. */
  public State getStartingState()
  { return m_definition.getStartingState(); }

  /** Returns the current state of this automata.
   * @return The current state of this automata. */
  public State getCurrentState()
  { return m_currentState; }

  /** Returns a string that representes the list of all characters
   * received by this finale state automata. */
  public String getReceivedCharacters()
  {
    return new String(receivedCharacters);
  }

  /** Initializes this state machine. The current state is back to the
   * starting state and the received characters are initialized to none. */
  public void initialize()
  {
    receivedCharacters = new StringBuffer();
    m_currentState = m_definition.getStartingState();
  }

  /** Returns a string representation of this object.
   * @return A string representation of this object. */
  public String toString()
  { return "FSA : [" + getStartingState().toString()+"]"; }

}

