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

/** This interface exposes the types of midi messages 
 * created by the <TT>abc.midi</TT> package */ 
public interface MidiMessageType {
	
  //public static final byte TEXT_EVENT = 0x01;
	/** The message type for an end of a tune playback. */
	public static final byte END_OF_TRACK = 0x2F;
	/** The message type for a tempo change. */ 
	public static final byte TEMPO_CHANGE = 0x51;
	/** The message type to flag which part of the abc notation
	 * is played during a tune playback.
	 * @deprecated use <TT>NOTE_INDEX_MARKER</TT>
	 * @see #NOTE_INDEX_MARKER */
	public static final byte NOTATION_MARKER = 0x30;
	/** The message type to give a reference to the note index in the
	 * score that is being played during a tune playback. */
	public static final byte NOTE_INDEX_MARKER = 0x40;
	
	public static final byte MARKER = 0x06;

}