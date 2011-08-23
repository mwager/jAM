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
package abc.ui.scoretemplates;

/**
 * List of score elements, texts (title, composer...), notes,
 * graces, clef, accidental, time signature, decoration, slurs...
 *
 */
public abstract class ScoreElements {
	
	public static final byte _DEFAULT = 0;
	
	public static final byte STAFF_LINES = -1;
	public static final byte NOTE = -2;
	public static final byte CLEF = -3;
	public static final byte KEY_SIGNATURE = -4;
	public static final byte TIME_SIGNATURE = -5;
	public static final byte BAR_LINES = -6;
	public static final byte DECORATION = -7;
	public static final byte SLUR = -8;
	public static final byte TIE = -9;
	public static final byte GRACENOTE = -10;
	public static final byte ACCIDENTAL = -11;
	public static final byte DYNAMIC = -12;
	
	/** @see #TEXT_ORIGIN */
	public static final byte TEXT_AREA = 9;
	/** Book (e.g. B:O'Neills) */
	public static final byte TEXT_BOOK = 2;
	/** Composer (e.g. C:Robert Jones, C:Trad.) */
	public static final byte TEXT_COMPOSER = 3;
	/** Discography (e.g. D:Chieftains IV) */
	public static final byte TEXT_DISCOGRAPHY = 4;
	//public static final byte ELEMSKIP = 5;
	/** Group (e.g. G:flute) */
	public static final byte TEXT_GROUP = 5;
	/** History (e.g. H:This tune said to...) */
	public static final byte TEXT_HISTORY = 6;
	/** Informations or instructions */
	public static final byte TEXT_INFORMATIONS = 7; //or instruction
	/** Notes (e.g. N:see also O'Neills - 234) */
	public static final byte TEXT_NOTES = 8;
	/** Origin and area (e.g. O:UK, Yorkshire, Bradford) */
	public static final byte TEXT_ORIGIN = 9;
	/** Rhythm (e.g. R:R, R:reel) */
	public static final byte TEXT_RHYTHM = 10;
	/** Source (e.g. S:collected in Brittany) */
	public static final byte TEXT_SOURCE = 11;
	/** Title(s) (e.g. T:Paddy O'Rafferty) */
	public static final byte TEXT_TITLE = 12;
	public static final byte TEXT_SUBTITLE = 13;
	/** Transcriber and notes (e.g. Z:John Smith, j.s@aol.com) */
	public static final byte TEXT_TRANSCRNOTES = 14;
	/** Words (lyrics after the tune) */
	public static final byte TEXT_WORDS = 15;
	/** File URL (e.g. F:http://a.b.c/file.abc) */
	public static final byte TEXT_FILEURL = 16;
	/** Lyrics' author (e.g. A:Walter Raleigh) */
	public static final byte TEXT_LYRICIST = 17; //v2.0 A: field
	/** Chord names over the staff line */
	public static final byte TEXT_CHORDS = 18;
	/** Lyrics under the staff line, aligned on notes */
	public static final byte TEXT_LYRICS = 19;
	/** Part labels in frames over staff line */
	public static final byte PART_LABEL = 20;
	/** Part order in header */
	public static final byte TEXT_PARTS_ORDER = 21;
	/** Tempo */
	public static final byte TEMPO = 22;
	/** Annotations arround elements */
	public static final byte TEXT_ANNOTATIONS = 23;
	
	public static String toString(byte b) {
		switch (b) {
		case TEXT_ANNOTATIONS: return "annotations";
		//case AREA: cf ORIGIN
		case TEXT_BOOK: return "book";
		case TEXT_CHORDS: return "chords";
		case TEXT_COMPOSER: return "composer";
		case TEXT_DISCOGRAPHY: return "discography";
		case TEXT_FILEURL: return "file url";
		case TEXT_GROUP: return "group";
		case TEXT_HISTORY: return "history";
		case TEXT_INFORMATIONS: return "informations";
		case TEXT_LYRICIST: return "lyricist";
		case TEXT_LYRICS: return "lyrics";
		//case NOTES: cf ANNOTATIONS
		case TEXT_ORIGIN: return "origin";
		case PART_LABEL: return "part label";
		case TEXT_PARTS_ORDER: return "parts order";
		case TEXT_RHYTHM: return "rhythm";
		case TEXT_SOURCE: return "source";
		case TEXT_SUBTITLE: return "subtitle";
		case TEMPO: return "tempo";
		case TEXT_TITLE: return "title";
		case TEXT_TRANSCRNOTES: return "transcriber notes";
		case TEXT_WORDS: return "words";
		default: return "<unknown text field>";
		}
	}
}
