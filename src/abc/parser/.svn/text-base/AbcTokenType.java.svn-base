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
package abc.parser;

import scanner.TokenType;
/** This class defines all types of token that can be encountered while parsing
 * a tune written using abc notation. */
public class AbcTokenType implements TokenType
{
    /** The <TT>ALPHA<TT> token type : characters from a to z and A to Z. */
    public static AbcTokenType ALPHA = new AbcTokenType("ALPHA");
    /** The  aread field token type : <TT>A:</TT>. */
    public static AbcTokenType FIELD_AREA = new AbcTokenType("FIELD AREA");
    /** The  book field token type : <TT>B:</TT>. */
    public static AbcTokenType FIELD_BOOK = new AbcTokenType("FIELD BOOK");
    /** The  composer field token type : <TT>C:</TT>. */
    public static AbcTokenType FIELD_COMPOSER = new AbcTokenType("FIELD COMPOSER");
    /** The  discography field token type : <TT>D:</TT>. */
    public static AbcTokenType FIELD_DISCOGRAPHY = new AbcTokenType("FIELD DISCOGRAPHY");
    /** The  elemskip field token type : <TT>E:</TT>. */
    public static AbcTokenType FIELD_ELEMSKIP = new AbcTokenType("FIELD ELEMSKIP");
    /** The  elemskip field token type : <TT>F:</TT>. */
    public static AbcTokenType FIELD_FILEURL = new AbcTokenType("FIELD FILE");
    /** The  group field token type : <TT>G:</TT>. */
    public static AbcTokenType FIELD_GROUP = new AbcTokenType("FIELD GROUP");
    /** The  history field token type : <TT>H:</TT>. */
    public static AbcTokenType FIELD_HISTORY = new AbcTokenType("FIELD HISTORY");
    /** The  information field token type : <TT>I:</TT>. */
    public static AbcTokenType FIELD_INFORMATION = new AbcTokenType("FIELD INFORMATION");
    /** The  default length field token type : <TT>L:</TT>. */
    public static AbcTokenType FIELD_DEFAULT_LENGTH = new AbcTokenType("FIELD DEFAULT LENGTH");
    /** The  key field token type : <TT>K:</TT>. */
    public static AbcTokenType FIELD_KEY = new AbcTokenType("FIELD KEY");
    /** The  meter field token type : <TT>M:</TT>. */
    public static AbcTokenType FIELD_METER = new AbcTokenType("FIELD METER");
    /** The  notes field token type : <TT>N:</TT>. */
    public static AbcTokenType FIELD_NOTES = new AbcTokenType("FIELD NOTES");
    /** The  origin field token type : <TT>O:</TT>. */
    public static AbcTokenType FIELD_ORIGIN = new AbcTokenType("FIELD ORIGIN");
    /** The  parts field token type : <TT>P:</TT>. */
    public static AbcTokenType FIELD_PARTS = new AbcTokenType("FIELD PARTS");
    /** The tempo field token type : <TT>Q:</TT>. */
    public static AbcTokenType FIELD_TEMPO = new AbcTokenType("FIELD TEMPO");
    /** The  rhythm field token type : <TT>R:</TT>. */
    public static AbcTokenType FIELD_RHYTHM = new AbcTokenType("FIELD RHYTHM");
    /** The source field token type : <TT>S:</TT>. */
    public static AbcTokenType FIELD_SOURCE = new AbcTokenType("FIELD SOURCE");
    /** The title field token type : <TT>T:</TT>. */
    public static AbcTokenType FIELD_TITLE = new AbcTokenType("FIELD TITLE");
    /** The reference number field token type : <TT>X:</TT>. */
    public static AbcTokenType FIELD_NUMBER = new AbcTokenType("FIELD NUMBER");
    /** The transcription notes field token type : <TT>Z:</TT>. */
    public static AbcTokenType FIELD_TRANSCRNOTES = new AbcTokenType("FIELD TRANSCRNOTES");
    /** The words field token type : <TT>W:</TT>. */
    public static AbcTokenType FIELD_WORDS = new AbcTokenType("FIELD WORDS");

    /** The text token type : all characters, excluding '%'. */
    public static AbcTokenType TEXT = new AbcTokenType("TEXT");
    /** The chord name token type : character '"'. */
    public static AbcTokenType CHORD_NAME = new AbcTokenType("CHORD NAME");
    /** The comment token type : character '%'. */
    public static AbcTokenType COMMENT = new AbcTokenType("COMMENT");
    /** The number token type. */
    public static AbcTokenType NUMBER = new AbcTokenType("NUMBER");
    /** The digit token type : from 1 to 9. */
    public static AbcTokenType DIGIT = new AbcTokenType("DIGIT");
    /** The fraction token type : character '/'. */
    public static AbcTokenType FRACTION = new AbcTokenType("FRACTION");
    public static AbcTokenType PLUS = new AbcTokenType("PLUS");
    /** The parenthesis open token type : character '('. */
    public static AbcTokenType PARENTHESIS_OPEN = new AbcTokenType("PARENTHESIS OPEN");
    /** The parenthesis close token type : character ')'. */
    public static AbcTokenType PARENTHESIS_CLOSE = new AbcTokenType("PARENTHESIS CLOSE");
    /** The line feed token type : character '\n'. */
    public static AbcTokenType LINE_FEED = new AbcTokenType("LINE FEED");
    /** The line break token type : character '!'. */
    public static AbcTokenType LINE_BREAK = new AbcTokenType("LINE BREAK");
    /** The no line break token type : character '\'. */
    public static AbcTokenType NO_LINE_BREAK = new AbcTokenType("NO LINE BREAK");

    /** The mode token type : <TT>m</TT> or <TT>M</TT> or <TT></TT> or <TT></TT>
     * or <TT></TT> or <TT></TT> or <TT></TT> or <TT></TT> or <TT></TT>  . */
    public static AbcTokenType MODE = new AbcTokenType("MODE");

    /** The HP key specification token type : "HP" or "Hp". */
    public static AbcTokenType KEY_HP = new AbcTokenType("KEY HP");
    /** The key accidental token type : '#' or 'b'. */
    public static AbcTokenType KEY_ACCIDENTAL = new AbcTokenType("KEY ACCIDENTAL");
    /** The meter C token type : "C" for 4/4, "C|" for 2/2. */
    public static AbcTokenType C_METER = new AbcTokenType("C METER");
    /** The equals token type : character '='. */
    public static AbcTokenType EQUALS = new AbcTokenType("EQUALS");
    /** The C tempo token type : character 'C'. */
    public static AbcTokenType C_TEMPO = new AbcTokenType("C TEMPO");
    /** The base note token type : from 'A' to 'G' and from 'a' to 'g'. */
    public static AbcTokenType BASE_NOTE = new AbcTokenType("BASE NOTE");
    public static AbcTokenType CLEF = new AbcTokenType("CLEF");

    public static AbcTokenType PART = new AbcTokenType("PART");
    public static AbcTokenType ACCIDENTAL = new AbcTokenType("ACCIDENTAL");
    public static AbcTokenType REST = new AbcTokenType("REST");
    public static AbcTokenType BARLINE = new AbcTokenType("BARLINE");
    public static AbcTokenType REPEAT_OPEN = new AbcTokenType("REPEAT OPEN");
    public static AbcTokenType REPEAT_CLOSE = new AbcTokenType("REPEAT CLOSE");
    public static AbcTokenType SPACE = new AbcTokenType("SPACE");
    public static AbcTokenType SPACER = new AbcTokenType("SPACER");
    public static AbcTokenType SYMBOL = new AbcTokenType("SYMBOL");
    public static AbcTokenType SYMBOL_BEGIN = new AbcTokenType("SYMBOL BEGIN");
    public static AbcTokenType SYMBOL_END = new AbcTokenType("SYMBOL END");
    public static AbcTokenType ANNOTATION = new AbcTokenType("ANNOTATION");
    public static AbcTokenType ANNOTATION_BEGIN = new AbcTokenType("ANNOTATION BEGIN");
    public static AbcTokenType ANNOTATION_END = new AbcTokenType("ANNOTATION END");
    public static AbcTokenType GUITAR_CHORD = new AbcTokenType("GUITAR CHORD");
    public static AbcTokenType BEGIN_SLUR = new AbcTokenType("BEGIN SLUR");
    public static AbcTokenType END_SLUR = new AbcTokenType("END SLUR");
    public static AbcTokenType GRACING_BEGIN = new AbcTokenType("GRACE BEGIN");
    public static AbcTokenType ACCIACCATURA = new AbcTokenType("ACCIACCATURA");
    public static AbcTokenType GRACING_END = new AbcTokenType("GRACE END");
    public static AbcTokenType GRACING = new AbcTokenType("GRACING");
    public static AbcTokenType OCTAVE = new AbcTokenType("OCTAVE");
    public static AbcTokenType TIE = new AbcTokenType("TIE");
    public static AbcTokenType BROKEN_RHYTHM = new AbcTokenType("BROKEN RHYTHM");
    public static AbcTokenType MULTI_NOTE_BEGIN = new AbcTokenType("MULTI NOTE BEGIN");
    public static AbcTokenType MULTI_NOTE_END = new AbcTokenType("MULTI NOTE END");
    public static AbcTokenType TUPLET_SPEC = new AbcTokenType("TUPLET SPEC");

    //public static AbcTokenType TEX_COMMAND = new AbcTokenType("TEX COMMAND");
    public static AbcTokenType USER_DEFINED = new AbcTokenType("USER DEFINED");
    public static AbcTokenType NTH_REPEAT = new AbcTokenType("NTH REPEAT");
    public static AbcTokenType CHORD_TYPE = new AbcTokenType("CHORD TYPE");

    public static AbcTokenType COMA = new AbcTokenType("COMA");


    private String m_name = null;

    public AbcTokenType(String typeName)
    { m_name = typeName; }

    public boolean isField()
    {
      return (
        equals(AbcTokenType.FIELD_AREA) ||
        equals(AbcTokenType.FIELD_BOOK) ||
        equals(AbcTokenType.FIELD_COMPOSER) ||
        equals(AbcTokenType.FIELD_DEFAULT_LENGTH) ||
        equals(AbcTokenType.FIELD_DISCOGRAPHY) ||
        equals(AbcTokenType.FIELD_FILEURL) ||
        equals(AbcTokenType.FIELD_GROUP) ||
        equals(AbcTokenType.FIELD_HISTORY) ||
        equals(AbcTokenType.FIELD_INFORMATION) ||
        equals(AbcTokenType.FIELD_KEY) ||
        equals(AbcTokenType.FIELD_METER) ||
        equals(AbcTokenType.FIELD_NOTES) ||
        equals(AbcTokenType.FIELD_NUMBER) ||
        equals(AbcTokenType.FIELD_ORIGIN) ||
        equals(AbcTokenType.FIELD_PARTS) ||
        equals(AbcTokenType.FIELD_RHYTHM) ||
        equals(AbcTokenType.FIELD_SOURCE) ||
        equals(AbcTokenType.FIELD_TEMPO) ||
        equals(AbcTokenType.FIELD_TITLE) ||
        equals(AbcTokenType.FIELD_TRANSCRNOTES) ||
        equals(AbcTokenType.FIELD_WORDS)
        );
    }

    public String toString()
    { return m_name; }
}

