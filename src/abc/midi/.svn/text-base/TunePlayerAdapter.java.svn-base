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
package abc.midi;

import abc.notation.NoteAbstract;

/** A convenient class to listen to TunePlayer. This listener does nothing. Just
* extend from it to override your behaviours. */
public class TunePlayerAdapter implements TunePlayerListenerInterface
{
  /** Invoked when the playing of a tune has started. */
  public void playBegin(PlayerStateChangeEvent e)
  {}

  /** Invoked when the playing of a tune is ended. */
  public void playEnd(PlayerStateChangeEvent e)
  {}

  /** Invoked when the playing tempo has changed. */
  public void tempoChanged(TempoChangeEvent e)
  {}

  /** Invoked when a part of the tune notation is played. */
  public void partPlayed(int begin, int end)
  {}

  public void notePlayed(NoteAbstract note)
  {}
}
