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
import javax.sound.midi.Track;
import javax.sound.midi.MidiEvent;
import abc.notation.KeySignature;
import abc.notation.Note;
import abc.notation.MultiNote;
import abc.notation.Music;

/** A basic midi converter that just plays melody, ignores ornaments and chords. */
public class BasicPositionableMidiConverter extends BasicMidiConverter
{
  protected void playNote(Note note, int indexInScore, KeySignature currentKey, long reference, long duration, Track track) throws InvalidMidiDataException
  {
    //System.out.println("play note " + note);
    //MidiEvent[] array = {new MidiEvent(new NotationMarkerMessage((PositionableInCharStream)note), reference)};
    MidiEvent[] array = {new MidiEvent(new NoteIndexMessage(indexInScore), reference)};
    addEventsToTrack(track, array);
    super.playNote(note, indexInScore,  currentKey, reference, duration, track);
  }

  protected void playMultiNote(MultiNote multiNote, int indexInScore, KeySignature currentKey, long reference, Track track, Music staff) throws InvalidMidiDataException
  {
    //System.out.println("play multiNote " + multiNote);
    //MidiEvent[] array = {new MidiEvent(new NotationMarkerMessage((PositionableInCharStream)multiNote), reference)};
    MidiEvent[] array = {new MidiEvent(new NoteIndexMessage(indexInScore), reference)};
    addEventsToTrack(track, array);
    super.playMultiNote(multiNote, indexInScore, currentKey, reference, track, staff);

  }

}
