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

import java.beans.FeatureDescriptor;
import java.util.Vector;

import javax.sound.midi.Instrument;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.ShortMessage;

import abc.notation.KeySignature;
import abc.notation.MultiNote;
import abc.notation.Note;
import abc.notation.Tempo;
import abc.notation.Tuplet;
import abc.parser.PositionableMultiNote;
import abc.parser.PositionableNote;

/** A basic midi converter that just plays melody, ignores ornaments and chords. */
public class OldBasicMidiConverter extends OldMidiConverterAbstract
{
  public MidiEvent[] getMidiEventsFor(Note note, KeySignature key, long elapsedTime) throws InvalidMidiDataException
  {
    long noteLength = (long)getNoteLengthInTicks(note);//, defaultLength);
    MidiEvent[] events = null;
    if (!note.isRest())
    {
      events = new MidiEvent[2];
      ShortMessage myNoteOn = new ShortMessage();
      myNoteOn.setMessage(ShortMessage.NOTE_ON, getMidiNoteNumber(note, key), 50);
      events[0] = new MidiEvent(myNoteOn,elapsedTime);
      //events[1] = new MidiEvent(new NotationMarkerMessage((PositionableNote)note), elapsedTime);
      ShortMessage myNoteOff = new ShortMessage();
      myNoteOff.setMessage(ShortMessage.NOTE_OFF , getMidiNoteNumber(note, key), 50);
      events[1] = new MidiEvent(myNoteOff,elapsedTime+noteLength);
    }
    return events;
  }

  /** Returns the corresponding midi events for a tuplet. */
  public MidiEvent[] getMidiEventsFor(Tuplet tuplet, KeySignature key, long elapsedTime) throws InvalidMidiDataException
  {
    float totalTupletLength = tuplet.getTotalRelativeLength();
    Vector tupletAsVector = tuplet.getNotesAsVector();
    int notesNb = tupletAsVector.size();
    MidiEvent[] events = new MidiEvent[3*notesNb];
    for (int j=0; j<tupletAsVector.size(); j++)
    {
      Note note = null;
      // to be fixed  : this can be a note or a multi note.
      //if (tupletAsVector.elementAt(j) instanceof Note)
        note = (Note)(tupletAsVector.elementAt(j));
      //else
      //  note = (Note)((MultiNote)tupletAsVector.elementAt(j)).getNotesAsVector().elementAt(0);
      long noteLength = getNoteLengthInTicks(note);//, defaultLength);
      noteLength = (long)(noteLength * totalTupletLength / notesNb);
      if (!note.isRest())
      {
        ShortMessage myNoteOn = new ShortMessage();
        myNoteOn.setMessage(ShortMessage.NOTE_ON, getMidiNoteNumber(note, key), 50);
        events[3*j] = new MidiEvent(myNoteOn,elapsedTime);

        events[3*j+1] = new MidiEvent(new NotationMarkerMessage((PositionableNote)note), elapsedTime);

        ShortMessage myNoteOff = new ShortMessage();
        myNoteOff.setMessage(ShortMessage.NOTE_OFF , getMidiNoteNumber(note, key), 50);
        elapsedTime+=noteLength;
        events[3*j+2] = new MidiEvent(myNoteOff,elapsedTime);
      }
    /*      else
      if (tupletAsVector.elementAt(j) instanceof MultiNote)
      {
        events = getMidiEventsFor((MultiNote)tupletAsVector.elementAt(j), key, elapsedTime);
        long noteLength = events[1+(events.length-1)/2].getTick()-events[0].getTick();
        noteLength = (long)(noteLength * totalTupletLength / tuplet.countNotes());
        for (int i=0; i<(events.length-1)/2-1; i++)
        {
          events[i].setTick(elapsedTime);
          events[2*i+1].setTick(elapsedTime+noteLength);
        }
      }*/
    }
    return events;
  }

  /** Returns the corresponding midi events for a tempo change. */
  public MidiEvent[] getMidiEventsFor(Tempo tempo, long lastPosInTicks) throws InvalidMidiDataException
  {
    TempoMessage mt = new TempoMessage(tempo);
    MidiEvent me = new MidiEvent(mt,lastPosInTicks);
    MidiEvent[] events = null;//{me, new MidiEvent(new TempoMessageWA(), lastPosInTicks)};
    return events;
  }

  /** Returns the corresponding midi events for a multi note. */
  public MidiEvent[] getMidiEventsFor(MultiNote notes, KeySignature key, long elapsedTime) throws InvalidMidiDataException
  {
    Vector notesVector = notes.getNotesAsVector();
    MidiEvent[] events = new MidiEvent[2*notesVector.size()+1];
    for (int j=0; j<notesVector.size(); j++)
    {
      Note note = (Note)(notesVector.elementAt(j));
      float noteLength = getNoteLengthInTicks(note);//, defaultLength);
      if (!note.isRest())
      {
        ShortMessage myNoteOn = new ShortMessage();
        myNoteOn.setMessage(ShortMessage.NOTE_ON, getMidiNoteNumber(note, key), 50);
        events[j] = new MidiEvent(myNoteOn,elapsedTime);
      }
    }

    events[notesVector.size()] = new MidiEvent(new NotationMarkerMessage((PositionableMultiNote)notes), elapsedTime);

    for (int j=0; j<notesVector.size(); j++)
    {
      Note note = (Note)(notesVector.elementAt(j));
      long noteLength = getNoteLengthInTicks(note);//, defaultLength);
      if (!note.isRest())
      {
        ShortMessage myNoteOff = new ShortMessage();
        myNoteOff.setMessage(ShortMessage.NOTE_OFF , getMidiNoteNumber(note, key), 50);
        events[notesVector.size()+j+1] = new MidiEvent(myNoteOff,elapsedTime+noteLength);
      }
    }
    return events;
  }
  
	public Instrument getInstrument() {
		return null;
	}

	/** Sets the instrument to be used for sequence playback. This implicitly loads the 
	 * given instrument. 
	 * @param instr The instrument to be used for sequence playback. */
	public void setInstrument(Instrument instr) {
		throw new RuntimeException("Method not implemented");
	}

}
