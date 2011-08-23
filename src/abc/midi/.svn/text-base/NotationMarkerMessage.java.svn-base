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

import scanner.PositionableInCharStream;

/** A midi meta event to flag a positionable object in a midi stream. */
public class NotationMarkerMessage extends MetaMessage
{
  //private PositionableInCharStream m_pos = null;

  public NotationMarkerMessage(PositionableInCharStream pos)
  {
    //FF 7F <len> <id> <data>  Sequencer-Specific Meta-event
    //m_pos = pos;
    int offsetBegin = pos.getPosition().getCharactersOffset();
    //int offsetEnd = pos.getEndPosition().getCharactersOffset()-1;
    // replaced by when migrating ot getSize() in positionableInStream:
    int offsetEnd = offsetBegin+pos.getLength()-1;


    byte[] buffer = new byte[6];

    buffer[0] = (byte)(( offsetBegin & 0x00ff0000 ) >> 16 );
    buffer[1] = (byte)(( offsetBegin & 0x0000ff00 ) >> 8 );
    buffer[2] = (byte)( offsetBegin &  0x000000ff );

    buffer[3] = (byte)(( offsetEnd & 0x00ff0000 ) >> 16 );
    buffer[4] = (byte)(( offsetEnd & 0x0000ff00 ) >> 8 );
    buffer[5] = (byte)( offsetEnd &  0x000000ff );
    try
    { setMessage(MidiMessageType.NOTATION_MARKER ,buffer, buffer.length); }
    catch (InvalidMidiDataException e)
    { e.printStackTrace(); }
  }

  public static int getBeginOffset(byte[] bytes)
  {
    int a = ((int)bytes[0]&0xFF)<<16;
    int b = ((int)bytes[1]&0xFF)<<8;
    int c = ((int)bytes[2]&0xFF);
    if (a+b+c<0)
      System.out.println("ca va péter !");
    return a+b+c;

  }

  public static int getEndOffset(byte[] bytes)
  {
    int a = ((int)bytes[3]&0xFF)<<16;
    int b = ((int)bytes[4]&0xFF)<<8;
    int c = ((int)bytes[5]&0xFF);
    return a+b+c;
  }
}
