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

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaMessage;

public class NoteIndexMessage extends MetaMessage
{
  //private PositionableInCharStream m_pos = null;

  public NoteIndexMessage(int indexInScore)
  {
    //System.out.println("new NoteIndexMessage(" + indexInScore +")");
    //FF 7F <len> <id> <data>  Sequencer-Specific Meta-event
    //m_pos = pos;
    byte[] buffer = new byte[3];

    buffer[0] = (byte)(( indexInScore & 0x00ff0000 ) >> 16 );
    buffer[1] = (byte)(( indexInScore & 0x0000ff00 ) >> 8 );
    buffer[2] = (byte)( indexInScore &  0x000000ff );

    try
    { setMessage(MidiMessageType.NOTE_INDEX_MARKER ,buffer, buffer.length); }
    catch (InvalidMidiDataException e)
    { e.printStackTrace(); }
  }

  public static int getIndex(byte[] bytes)
  {
	int a = ((int)bytes[0]&0xFF)<<16;
    int b = ((int)bytes[1]&0xFF)<<8;
    int c = ((int)bytes[2]&0xFF);
    if (a+b+c<0)
      System.err.println("ca va péter !");
    return a+b+c;

  }
}
