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

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;

import abc.notation.Tune;

/** This interface defines methods that should be implemented for any "tune to
 * midi" converter.
 * Improved converters could generate midi sequences depending on the type of tune
 * rhythm, have nice ornaments features etc etc... */
public interface MidiConverterInterface
{
  /** Returns the midi sequence corresponding to the given tune.
   * @param tune A tune with a score.
   * @return A midi sequence corresponding to the given tune. */
  public Sequence toMidiSequence(Tune tune);
  
  	/** Returns the instrument currently used for sequence playback.  
	 * @return The instrument currently used for sequence playback. Returns <TT>null</TT>
	 * if not set. */
	public Instrument getInstrument();

	/** Sets the instrument to be used for sequence playback. This implicitly loads the 
	 * given instrument. 
	 * @param instr The instrument to be used for sequence playback. */
	public void setInstrument(Instrument instr) throws MidiUnavailableException;
}

