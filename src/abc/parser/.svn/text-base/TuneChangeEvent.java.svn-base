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

import java.util.EventObject;

import abc.notation.Tune;

/** Event used when a state changed occured on a tune. */
public class TuneChangeEvent extends EventObject
{
  private static final long serialVersionUID = 8455472810254418509L;
  /** The tune updated event type. */
  public static final int TUNE_UPDATED = 0;
  /** The tune removed event type. */
  public static final int TUNE_REMOVED = 1;
  /** The tune added event type. */
  public static final int TUNE_ADDED = 2;

  /** The tune that changed. */
  private Tune m_tune = null;
  /** The notation of the tune that has changed. */
  private String m_tuneNotation = null;
  /** The type of event. */
  private int m_eventType = 0;

  /** Creates a new event describing a tune change.
   * @param source The source that generated this event.
   * @param eventType The type of this event.
   * @param newTune The tune that has changed (new, been removed or updated)
   * @param newTuneNotation The notation of the tune that has been changed, removed
   * or added. */
  public TuneChangeEvent(Object source, int eventType, Tune newTune, String newTuneNotation)
  {
    super(source);
    m_eventType = eventType;
    m_tune = newTune;
    m_tuneNotation = newTuneNotation;
  }

  /** Returns the tune notation in ABC format.
   * @return the tune notation in ABC format. */
  public String getTuneNotation()
  { return m_tuneNotation; }

  /** Returns the type of this event.
   * @return The type of this event. */
  public int getType()
  { return m_eventType; }

  /** Returns the tune that has changed.
   * @return The tune that has changed. */
  public Tune getTune()
  { return m_tune; }

  /** Returns a string representation of this event.
   * @return A  string representation of this event. */
  public String toString()
  {
    if (m_eventType==TUNE_REMOVED)
      return "Tune n°" + m_tune.getReferenceNumber() + " REMOVED";
    else
    if (m_eventType==TUNE_ADDED)
      return "Tune n°" + m_tune.getReferenceNumber() + " ADDED";
    else
      return "Tune n°" + m_tune.getReferenceNumber() + " UPDATED";
 }
}
