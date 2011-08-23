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
package abc.audio;

import java.util.Hashtable;
import java.util.Iterator;

import abc.notation.KeySignature;
import abc.notation.Music;
import abc.notation.MusicElement;

/**
 * Utility class to prepare a {@link abc.notation.Tune.Music} object
 * to be rendered in audio output (MIDI or other)
 */
public class BeforeAudioRendition {

	public static Music transformAll(Music source) {
		Music dest = transformDecorations(source);
		dest = applyDynamics(dest);
		dest = transformRepeatsAndBreaks(dest);
		dest = generateBassAndChords(dest);
		return dest;
	}
	
	/**
	 * Transform tills, mordants, grupetto, arpeggio, staccato
	 * into multiple notes (and rests).
	 * 
	 * e.g. +trill+C2 will be transformed into DCDC
	 * @param source
	 * @return the Music object which contents has been changed
	 */
	public static Music transformDecorations(Music music) {
		Music ret = new Music();
		KeySignature tuneKey = null;
		KeySignature currentKey = null;
		Hashtable partsKey = new Hashtable();
		MusicElement element;
		Iterator itMusic = music.iterator();
		while (itMusic.hasNext()) {
			element = (MusicElement) itMusic.next();
			
		}
		return music;
	}
	
	/**
	 * Transforms <code>|: ... :|</code> into <code>2 * (...)</code>,
	 * repeat bars.
	 * @param music
	 * @return
	 */
	public static Music transformRepeatsAndBreaks(Music music) {
		return music;
	}
	
	/**
	 * Apply dynamics by modifying notes' velocities by a
	 * percentage, e.g. +ff+ apply 120%.
	 * @param music
	 * @return
	 */
	public static Music applyDynamics(Music music) {
		return music;
	}
	
	/**
	 * If <code>%%MIDI gchord ...</code> instruction(s) is(are)
	 * declared, create new voices for bass and chord
	 * accompaniments
	 * @param music
	 * @return
	 */
	public static Music generateBassAndChords(Music music) {
		return music;
	}
}
