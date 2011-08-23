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

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import javax.sound.midi.Instrument;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Track;

import abc.notation.Accidental;
import abc.notation.BarLine;
import abc.notation.Decoration;
import abc.notation.Interval;
import abc.notation.KeySignature;
import abc.notation.MultiNote;
import abc.notation.Music;
import abc.notation.Note;
import abc.notation.RepeatBarLine;
import abc.notation.Tempo;
import abc.notation.Tune;
import abc.notation.Voice;

/** MidiConverter class defines various static methods to convert abc related stuff
 * to midi : notes, tunes etc... */
public abstract class MidiConverterAbstract implements MidiConverterInterface {
	/** The resolution of the sequence : this will correspond to a quarter note. */
	private static final int SEQUENCE_RESOLUTION = Note.QUARTER;
	/** The instrument to use for the playback sequence. */
	protected Instrument instrument = null;

  	/** Converts the given tune to a midi sequence.
  	 * @param tune The tune to be converted.
  	 * @return The midi sequence of the tune. */
  	public Sequence toMidiSequence(Tune tune) {
  		Sequence sequence = null;
  		try {
  			if (instrument==null) {
  				Synthesizer synth = MidiSystem.getSynthesizer();
  				synth.open();
  				try {
  					setInstrument(synth.getAvailableInstruments()[0]);
  				} finally {
  					synth.close();
  				}
  			}
  			// Sequence in ticks per quarter note : PPQ = Pulse Per Quarter Note
  			// Resolution is expressed in ticks per beat.
  			// Last parameter "1" is the number of tracks.
  			sequence = new Sequence (Sequence.PPQ, SEQUENCE_RESOLUTION, 1);
  			// Set the instrument on channel 0
  	        ShortMessage sm = new ShortMessage( );
  	        sm.setMessage(ShortMessage.PROGRAM_CHANGE, 0, instrument.getPatch().getProgram(), 0);

  			Track track = sequence.createTrack();
  			track.add(new MidiEvent(sm, 0));
  			//long trackLengthInTicks = track.ticks();
  			int lastRepeatOpen = -1;
  			int repeatNumber = 1;
  			boolean inWrongEnding = false;
  			KeySignature tuneKey = null;
  			KeySignature currentKey = null;
  			Hashtable partsKey = new Hashtable();
  			
			long elapsedTime = 0;
			Note[] graceNotes = null;
			Music staff = tune.getMusic();
			Iterator it = staff.getVoices().iterator();
			while (it.hasNext()) {
				Voice voice = (Voice) it.next();
				int i = 0;// StaffItem iterator
	  		while (i < voice.size()) {
				if (!inWrongEnding) {
					//==================================================================== TEMPO
					if (voice.elementAt(i) instanceof abc.notation.Tempo) {
						addTempoEventsFor(track, elapsedTime, getMidiMessagesFor((Tempo)voice.elementAt(i)));//, trackLengthInTicks));
					}
					else
					if (voice.elementAt(i) instanceof abc.notation.PartLabel) {
						//Imagine... part A in Gmaj, B in Amin
						//in tune you have K:G, P:A, ... P:B, K:Am
						//if you have part order ABA, when you return to A
						//you stay in Amin. This stores the tuneKey when a
						//new part appear, and restitute it when part is played again
						abc.notation.PartLabel pl = (abc.notation.PartLabel) voice.elementAt(i);
						if (partsKey.get(pl.getLabel()+"") == null) {
							partsKey.put(pl.getLabel()+"", tuneKey);
						} else {
							tuneKey = (KeySignature) partsKey.get(pl.getLabel()+"");
						}
					}
					else
						//==================================================================== KEY SIGNATURE
					if (voice.elementAt(i) instanceof abc.notation.KeySignature) {
							tuneKey = (KeySignature)(voice.elementAt(i));
							currentKey = new KeySignature(tuneKey.getAccidentals());
					}
					else
					//==================================================================== NOTE
					// Notes ending ties should be ignored. Already taken into
					// account in getNoteLengthInTicks(Note)
					if (voice.elementAt(i) instanceof abc.notation.Note
						&& !((abc.notation.Note)voice.elementAt(i)).isEndingTie()) {

						Note note = (Note)voice.elementAt(i);
						long noteDuration;
						boolean fermata = false;
						Vector decorationNotes = new Vector();
						if (note.hasGeneralGracing() || note.hasDecorations()) {
							Decoration[] d = note.getDecorations();
							for (int j = 0; j < d.length; j++) {
								switch (d[j].getType()) {
								case Decoration.FERMATA:
								case Decoration.FERMATA_INVERTED:
									fermata = true; break;
								case Decoration.LOWERMORDENT:
								case Decoration.UPPERMORDENT:
								case Decoration.DOUBLE_LOWER_MORDANT:
								case Decoration.DOUBLE_UPPER_MORDANT:
								case Decoration.TRILL:
								case Decoration.TURN: //GRUPETTO_UP
								case Decoration.TURN_INVERTED: //GRUPETTO_DOWN
								case Decoration.TURNX:
								case Decoration.TURNX_INVERTED:
									Note n = new Note(note.getHeight());
									n.setAccidental(note.getAccidental(currentKey));
									Note o = new Interval(Interval.SECOND, Interval.MAJOR,
												Interval.UPWARD)
											.calculateSecondNote(n);
									Note m = new Interval(Interval.SECOND, Interval.MAJOR,
											Interval.DOWNWARD)
										.calculateSecondNote(n);
									//TODO ornament templates: regular, musette, balkan...
									//n.setStrictDuration(Note.SIXTEENTH);
									//o.setDuration((short)(Note.EIGHTH+Note.SIXTEENTH));
									o.setAccidental(Accidental.NONE);
									m.setAccidental(Accidental.NONE);
									n.setStrictDuration(Note.THIRTY_SECOND);
									m.setStrictDuration(Note.THIRTY_SECOND);
									o.setStrictDuration(Note.THIRTY_SECOND);
									switch (d[j].getType()) {
									case Decoration.DOUBLE_LOWER_MORDANT:
										decorationNotes.add(n);
										decorationNotes.add(m);
									case Decoration.LOWERMORDENT:
										decorationNotes.add(n);
										decorationNotes.add(m);
										break;
									case Decoration.DOUBLE_UPPER_MORDANT:
									case Decoration.TRILL:
										decorationNotes.add(n);
										decorationNotes.add(o);
									case Decoration.UPPERMORDENT:
										decorationNotes.add(n);
										decorationNotes.add(o);
										break;
									case Decoration.TURNX_INVERTED:
									case Decoration.TURN:
										decorationNotes.add(o);
										decorationNotes.add(n);
										decorationNotes.add(m);
										break;
									case Decoration.TURNX:
									case Decoration.TURN_INVERTED:
										decorationNotes.add(m);
										decorationNotes.add(n);
										decorationNotes.add(o);
									}
									break;
								}
							}
							// currently not used
							// future use: playing rolls, slides, etc.
						}
						long graceNotesDuration = 0;
						if (note.hasGracingNotes() || (decorationNotes.size() > 0)) {
							graceNotes = note.getGracingNotes();
							//gracing are eighth note for graphical rendition
							//and because that's it in the parser
							//adapt duration to note length
							int divisor = 1;
							if (note.getStrictDuration() >= Note.HALF)
								divisor = 1; //grace is an eighth
							else if (note.getStrictDuration() >= Note.QUARTER)
								divisor = 2; //16th
							else if (note.getStrictDuration() >= Note.EIGHTH)
								divisor = 4; //32nd
							else
								divisor = 8; //64th
							if (note.hasGracingNotes()) {
								for (int j=0;j<graceNotes.length;j++) {
									noteDuration = getNoteLengthInTicks(graceNotes[j], staff)/divisor;
									graceNotesDuration += noteDuration;
									playNote(graceNotes[j], i, currentKey, elapsedTime, noteDuration, track);
									elapsedTime+=noteDuration;
								}
							}
							for (int j=0;j<decorationNotes.size();j++) {
								noteDuration = getNoteLengthInTicks((Note)decorationNotes.elementAt(j), staff);
								graceNotesDuration += noteDuration;
								playNote((Note)decorationNotes.elementAt(j), i, currentKey, elapsedTime, noteDuration, track);
								elapsedTime+=noteDuration;
							}
						}
						//The note duration if the note isn't part of a tuplet.
						noteDuration = getNoteLengthInTicks(note, staff) - graceNotesDuration;
						if (noteDuration <= 0) //in case of too much grace notes
							noteDuration = getNoteLengthInTicks(note, staff);
						if (fermata) noteDuration *= 2;
						playNote(note, i, currentKey, elapsedTime, noteDuration, track);
						elapsedTime+=noteDuration;
					}
					else
					//==================================================================== MULTI NOTE
					if ((voice.elementAt(i) instanceof abc.notation.MultiNote)) {
						MultiNote multiNote = (MultiNote)voice.elementAt(i);
						playMultiNote(multiNote, i, currentKey, elapsedTime, track, staff);
						elapsedTime+=getNoteLengthInTicks(multiNote, staff);
					}
				} //endif (!inWrongEnding)
    			//====================================================================== REPEAT BAR LINE
  				if (voice.elementAt(i) instanceof abc.notation.RepeatBarLine) {
  					RepeatBarLine bar = (RepeatBarLine)voice.elementAt(i);
  					if (repeatNumber<bar.getRepeatNumbers()[0] && lastRepeatOpen!=-1) {
  						repeatNumber++;
  						i=lastRepeatOpen;
  					}
  					else
					if (repeatNumber>bar.getRepeatNumbers()[0])
						inWrongEnding = true;
					else
						inWrongEnding = false;
  				}
  				else
				//====================================================================== BAR LINE OPEN / CLOSE
				if (voice.elementAt(i) instanceof abc.notation.BarLine) {
					//currentKey = new KeySignature(tuneKey.getAccidentals());
					switch ( ((BarLine)(voice.elementAt(i))).getType()) {
						case BarLine.SIMPLE : break;
						case BarLine.REPEAT_OPEN : lastRepeatOpen=i; repeatNumber=1; break;
						case BarLine.REPEAT_CLOSE :
  								if (repeatNumber<2 && lastRepeatOpen!=-1) {
  									repeatNumber++; i=lastRepeatOpen;
  								}
  								else {
  									repeatNumber=1; lastRepeatOpen=-1;
  								}
  								break;
  						//TODO case BarLine.BEGIN_AND_END_REPEAT
					}
				}
  				//Whatever kind of bar line it is
  				if (voice.elementAt(i) instanceof abc.notation.BarLine) {
					currentKey = new KeySignature(tuneKey.getAccidentals());
				}
  				i++;
  			}//end while each element in voice
			}//end while each voice in music
  		}
  		catch (InvalidMidiDataException e) {
  			e.printStackTrace();
  		}
  		catch (Exception e) {
  			e.printStackTrace();
  		}
  		return sequence;
  	}

  	/** Returns the instrument currently used for sequence playback.
  	 * @return The instrument currently used for sequence playback. Returns <TT>null</TT>
  	 * if not set. */
  	public Instrument getInstrument(){
  		return instrument;
  	}

  	/** Sets the instrument to be used for sequence playback. This implicitly loads the
  	 * given instrument.
  	 * @param instr The instrument to be used for sequence playback. */
  	public void setInstrument(Instrument instr) throws MidiUnavailableException {
  		MidiSystem.getSynthesizer().loadInstrument(instr);
  		instrument = instr;
  	}

  	/** Generates the midi events required to play the given note in the context
  	 * described by the others parameters.
  	 * @param note The note to be played.
  	 * @param indexInScore The index of the note in the score.
  	 * @param currentKey The current key this note is referring to.
  	 * @param timeReference The time reference expressed in ticks when the note should be played.
  	 * @param duration The duration of the note expressed in ticks.
  	 * @param track The track where the note should be played.
  	 * @throws InvalidMidiDataException */
  	 protected void playNote(Note note, int indexInScore, KeySignature currentKey, long timeReference,
		  long duration, Track track) throws InvalidMidiDataException {
  		 if (!note.isRest() && !note.isEndingTie()) {
  			 addNoteOnEventsFor(track, timeReference, getNoteOneMessageFor(note, currentKey));
  			 addNoteOffEventsFor(track, timeReference + duration, getNoteOffMessageFor(note, currentKey));
  			 // In case the note to be played had an accidental, the current key needs
  			 // to be updated.
  			 updateKey(currentKey, note);
  		 }
  	 }

  protected void playMultiNote(MultiNote multiNote, int indexInScore, KeySignature currentKey, long reference, Track track, Music staff) throws InvalidMidiDataException
  {
    Vector notesVector = multiNote.getNotesAsVector();
    for (int j=0; j<notesVector.size(); j++)
    {
      Note note = (Note)(notesVector.elementAt(j));
      if (!note.isRest() && !note.isEndingTie())
        addNoteOnEventsFor(track, reference, getNoteOneMessageFor(note, currentKey));
    }
    for (int j=0; j<notesVector.size(); j++)
    {
      Note note = (Note)(notesVector.elementAt(j));
      //TODO needs to be improved to take into account multi notes with different notes length
      long noteDuration = getNoteLengthInTicks(multiNote, staff);
      if (!note.isRest() && !note.isEndingTie())
        addNoteOffEventsFor(track, reference+noteDuration, getNoteOffMessageFor(note, currentKey));
    }
    for (int j=0; j<notesVector.size(); j++)
      updateKey(currentKey, (Note)notesVector.elementAt(j));
  }

  private static void addNoteOnEventsFor(Track track, long timeReference, MidiMessage[] messages)
  {
    MidiMessage myNoteOn = messages[0];
    MidiEvent[] events = new MidiEvent[1];
    events[0] = new MidiEvent(myNoteOn,timeReference);
    addEventsToTrack(track, events);
  }

  private void addTempoEventsFor(Track track, long timeReference, MidiMessage[] messages) {
	  for (int i=0; i<messages.length; i++) {
		  MidiEvent me = new MidiEvent(messages[i], timeReference);
		  //MidiEvent[] events = {me, new MidiEvent(new TempoMessageWA(), timeReference)};
		  addEventsToTrack(track, me);
	  }
  }

  private void addNoteOffEventsFor(Track track, long timeReference, MidiMessage[] messages)
  {
    MidiMessage myNoteOn = messages[0];
    MidiEvent[] events = new MidiEvent[1];
    events[0] = new MidiEvent(myNoteOn,timeReference);
    addEventsToTrack(track, events);
  }


  private static void updateKey(KeySignature key, Note note)
  {
    if (!note.getAccidental().isInTheKey()) {
      key.setAccidental(note.getStrictHeight(), note.getAccidental());
    }
  }

  /**
   * @return The length of the track in ticks, once events have been added to it.
   */
  protected static long addEventsToTrack(Track track, MidiEvent[] events) {
    if (events!=null)
      for (int i=0; i<events.length; i++)
        track.add(events[i]);
    return track.ticks();
  }

  protected static long addEventsToTrack(Track track, MidiEvent event) {
	  track.add(event);
	  return track.ticks();
  }

  /** TODO rename to getNoteOnMessageFor (One->On)*/
  public abstract MidiMessage[] getNoteOneMessageFor(Note note, KeySignature key) throws InvalidMidiDataException;

  /** */
  public abstract MidiMessage[] getNoteOffMessageFor(Note note, KeySignature key) throws InvalidMidiDataException;

  /** Returns the corresponding midi events for a tempo change. */
  public abstract MidiMessage[] getMidiMessagesFor(Tempo tempo) throws InvalidMidiDataException;

  	/** Returns the length of the note in ticks, thanks to the sequence
  	 * resolution and the default note length. */
  	protected static long getNoteLengthInTicks(Note note, Music staff) {
  		short noteLength = note.getDuration();
  		if (note.isBeginningTie() && note.getTieDefinition().getEnd()!=null) {
  			noteLength +=
  				((Note)staff.getElementByReference(note.getTieDefinition().getEnd()))
  				.getDuration();
  		}
  		float numberOfQuarterNotesInThisNote = (float)noteLength / Note.QUARTER;
  		float lengthInTicks = (float)SEQUENCE_RESOLUTION * numberOfQuarterNotesInThisNote;
  		return (long)lengthInTicks;
  	}

  /** Returns the length of the multi note in ticks.
   * This length is calculated from the resolution of the midi sequence
   * manipulated internally.
   * @return The length of the multi note in ticks : this is equal to the length
   * of the longest note of the multi note. */
  public static long getNoteLengthInTicks(MultiNote note, Music staff) {
	  Note[] notes = note.toArray();
	  //if (notes!=null) {
		  notes = MultiNote.excludeTiesEndings(notes);
		  if (notes!=null)
			  return getNoteLengthInTicks(note.getShortestNote(), staff);
	  //}
	  else
		  return 0;
	  //return getNoteLengthInTicks(note.getLongestNote());
    /*float numberOfQuarterNotesInThisNote =  (float)longestLength / Note.QUARTER;
    float lengthInTicks = (float)SEQUENCE_RESOLUTION * numberOfQuarterNotesInThisNote;
    return (long)lengthInTicks;*/
  }

  /** Returns the midi note number corresponding a note in the given key.
   * @param note The note.
   * @param key The key this note should refer to for accidentals.
   * @return The midi height of the note in the given key. */
  public static byte getMidiNoteNumber (Note note, KeySignature key)
  {
    byte heigth = note.getStrictHeight();
    Accidental accidental = new Accidental(note.getAccidental(key).getNearestOccidentalValue());
    byte midiNoteNumber = (byte)(heigth+(69-Note.A));
    midiNoteNumber = (byte)(midiNoteNumber + note.getOctaveTransposition()*12);
   	midiNoteNumber += (byte) accidental.getValue(); //-2 dbl flat, -1 flat, 0 natural...
    return midiNoteNumber;
  }
}

