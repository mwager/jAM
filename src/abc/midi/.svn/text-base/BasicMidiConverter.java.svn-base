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
import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;

import abc.notation.KeySignature;
import abc.notation.Note;
import abc.notation.Tempo;

/** A basic midi converter that just plays melody, ignores ornaments and chords. */
public class BasicMidiConverter extends MidiConverterAbstract {
  public MidiMessage[] getNoteOneMessageFor(Note note, KeySignature key) throws InvalidMidiDataException
  {
    MidiMessage[] events = new MidiMessage[1];
    ShortMessage myNoteOn = new ShortMessage();
    myNoteOn.setMessage(ShortMessage.NOTE_ON, getMidiNoteNumber(note, key), 50);
    events[0] = myNoteOn;
    return events;
  }

  public MidiMessage[] getNoteOffMessageFor(Note note, KeySignature key) throws InvalidMidiDataException
  {
    ShortMessage[] events = new ShortMessage[1];
    ShortMessage myNoteOff = new ShortMessage();
    myNoteOff.setMessage(ShortMessage.NOTE_OFF , getMidiNoteNumber(note, key), 50);
    events[0]=myNoteOff;
    return events;
  }

  public MidiMessage[] getMidiMessagesFor(Tempo tempo) throws InvalidMidiDataException {
	  TempoMessage tempoM = new TempoMessage(tempo);
	  MidiMessage[] messages = {tempoM, new MetaMessageWA(tempoM)};
	  return messages;
  }

}
