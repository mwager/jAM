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

import java.util.EventListener;

import abc.notation.NoteAbstract;

/** This interface defines methods that should be implemented by any object that
 * wants to listen to a tune player. */
public interface TunePlayerListenerInterface extends EventListener
{
  /** Invoked when the playing of a tune has started. */
  public void playBegin(PlayerStateChangeEvent e);

  /** Invoked when the playing of a tune is ended. */
  public void playEnd(PlayerStateChangeEvent e);

  /** Invoked when the playing tempo has changed. */
  public void tempoChanged(TempoChangeEvent e);

  /** Invoked when a part of the tune notation is played.
   * @deprecated use notePlayed() instead. */
  public void partPlayed(int begin, int end);

  public void notePlayed(NoteAbstract note);
}
