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

import javax.sound.midi.MetaMessage;
import javax.sound.midi.InvalidMidiDataException;
import abc.notation.Tempo;
import abc.notation.Note;

/** A midi message to set tempo. */
public class TempoMessage extends MetaMessage {
	private static final int MICRO_SECOND_NB_IN_ONE_MINUTE = 60 * 1000000;
	
	/** Creates a midi message to change tempo to the specified tempo.
	 * @param tempo A tempo coming from an abc notation. */
	public TempoMessage(Tempo tempo) {
		super();
		int nbOfQuarterNotesPerMinute = tempo.getNotesNumberPerMinute(Note.QUARTER);
		int microsecondsPerQuarterNote = MICRO_SECOND_NB_IN_ONE_MINUTE / nbOfQuarterNotesPerMinute;
		//System.out.println("encoding " + microsecondsPerQuarterNote);
		byte[] buffer = new byte[3];
		buffer[0] = (byte)(( microsecondsPerQuarterNote & 0x00ff0000 ) >> 16 );
		buffer[1] = (byte)(( microsecondsPerQuarterNote & 0x0000ff00 ) >> 8 );
		buffer[2] = (byte)( microsecondsPerQuarterNote &  0x000000ff );
		try	{
			setMessage(MidiMessageType.TEMPO_CHANGE ,buffer, buffer.length);
		}
		catch (InvalidMidiDataException e) {
			e.printStackTrace();
		}
	}
	
	public static Tempo getTempo(byte[] bytes) {
		Tempo tempo = null;
		try {
			//System.out.println("converting" + bytes.length);
			int a = ((int)bytes[0]&0xFF)<<16;
			int b = ((int)bytes[1]&0xFF)<<8;
			int c = ((int)bytes[2]&0xFF);
			int microsecondsPerQuarterNote = a+b+c;
			short nbOfQuarterNotesPerMinute = (short)(MICRO_SECOND_NB_IN_ONE_MINUTE / microsecondsPerQuarterNote);
			tempo = new Tempo(nbOfQuarterNotesPerMinute);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return tempo;
	}
}
