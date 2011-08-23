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
package abc.parser.def;

import java.util.Vector;

import scanner.AutomataDefinition;
import scanner.TokenType;
import abc.parser.AbcTokenType;
import abc.parser.AbcVersion;

public class DefinitionFactory
{

  public static Vector m_allPreviouslyCreatedDefinitions = new Vector();

  public static AutomataDefinition getDefinition(TokenType abcTokenType, AbcVersion abcVersion)
  {
    TokenType[] tokenTypes = {abcTokenType};
    //System.out.println("getAutomataFor(" + toString(tokenTypes));
    AutomataDefinition automataDef = null;
    automataDef = getAlreadyCreatedDefinition(tokenTypes);

    if (automataDef==null)
    {
    if (abcTokenType==(AbcTokenType.FIELD_NUMBER)) automataDef = new FieldNumberDefinition();
    else if (abcTokenType==(AbcTokenType.FIELD_TITLE)) automataDef = new FieldTitleDefinition();
    else if (abcTokenType==(AbcTokenType.FIELD_AREA)) automataDef = new FieldAreaDefinition();
    else if (abcTokenType==(AbcTokenType.FIELD_BOOK)) automataDef = new FieldBookDefinition();
    else if (abcTokenType==(AbcTokenType.FIELD_COMPOSER)) automataDef = new FieldComposerDefinition();
    else if (abcTokenType==(AbcTokenType.FIELD_DISCOGRAPHY)) automataDef = new FieldDiscographyDefinition();
    else if (abcTokenType==(AbcTokenType.FIELD_ELEMSKIP)) automataDef = new FieldElemskipDefinition();
    else if (abcTokenType==(AbcTokenType.FIELD_FILEURL)) automataDef = new FieldFileDefinition();
    else if (abcTokenType==(AbcTokenType.FIELD_GROUP)) automataDef = new FieldGroupDefinition();
    else if (abcTokenType==(AbcTokenType.FIELD_HISTORY)) automataDef = new FieldHistoryDefinition();
    else if (abcTokenType==(AbcTokenType.FIELD_INFORMATION)) automataDef = new FieldInformationDefinition();
    else if (abcTokenType==(AbcTokenType.FIELD_DEFAULT_LENGTH)) automataDef = new FieldDefaultLengthDefinition();
    else if (abcTokenType==(AbcTokenType.FIELD_METER)) automataDef = new FieldMeterDefinition();
    else if (abcTokenType==(AbcTokenType.FIELD_NOTES)) automataDef = new FieldNotesDefinition();
    else if (abcTokenType==(AbcTokenType.FIELD_ORIGIN)) automataDef = new FieldOriginDefinition();
    else if (abcTokenType==(AbcTokenType.FIELD_PARTS)) automataDef = new FieldPartsDefinition();
    else if (abcTokenType==(AbcTokenType.FIELD_RHYTHM)) automataDef = new FieldRhythmDefinition();
    else if (abcTokenType==(AbcTokenType.FIELD_SOURCE)) automataDef = new FieldSourceDefinition();
    else if (abcTokenType==(AbcTokenType.FIELD_TEMPO)) automataDef = new FieldTempoDefinition();
    else if (abcTokenType==(AbcTokenType.FIELD_TRANSCRNOTES)) automataDef = new FieldTranscriptionNotesDefinition();
    else if (abcTokenType==(AbcTokenType.FIELD_KEY)) automataDef = new FieldKeyDefinition();
    else if (abcTokenType==(AbcTokenType.FIELD_WORDS)) automataDef = new FieldWordsDefinition();
    else if (abcTokenType==(AbcTokenType.KEY_HP)) automataDef = new KeyHPDefinition();
    else if (abcTokenType==(AbcTokenType.C_METER)) automataDef = new MeterCDefinition();
    else if (abcTokenType==(AbcTokenType.TEXT)) automataDef = new TextDefinition();
    else if (abcTokenType==(AbcTokenType.NUMBER)) automataDef = new NumberDefinition();
    else if (abcTokenType==(AbcTokenType.DIGIT)) automataDef = new DigitDefinition();
    else if (abcTokenType==(AbcTokenType.FRACTION)) automataDef = new FractionDefinition();
    else if (abcTokenType==(AbcTokenType.PART)) automataDef = new PartDefinition();
    else if (abcTokenType==(AbcTokenType.PARENTHESIS_OPEN)) automataDef = new ParenthesisOpenDefinition();
    else if (abcTokenType==(AbcTokenType.PARENTHESIS_CLOSE)) automataDef = new ParenthesisCloseDefinition();
    else if (abcTokenType==(AbcTokenType.PLUS)) automataDef = new PlusDefinition();
    else if (abcTokenType==(AbcTokenType.SPACE)) automataDef = new SpaceDefinition();
    else if (abcTokenType==(AbcTokenType.LINE_FEED)) automataDef = new LineFeedDefinition();
    else if (abcTokenType==(AbcTokenType.LINE_BREAK)) automataDef = new LineBreakDefinition();
    else if (abcTokenType==(AbcTokenType.NO_LINE_BREAK)) automataDef = new NoLineBreakDefinition();
    else if (abcTokenType==(AbcTokenType.BASE_NOTE))
      automataDef = new BaseNoteDefinition();
    else if (abcTokenType==(AbcTokenType.KEY_ACCIDENTAL)) automataDef = new KeyAccidentalDefinition();
    else if (abcTokenType==(AbcTokenType.ACCIDENTAL)) automataDef = new AccidentalDefinition(abcVersion);
    else if (abcTokenType==(AbcTokenType.MODE))automataDef = new ModeDefinition(abcVersion);
    else if (abcTokenType==(AbcTokenType.CLEF))automataDef = new ClefTextDefinition();
    else if (abcTokenType==(AbcTokenType.COMMENT)) automataDef = new CommentDefinition();
    else if (abcTokenType==(AbcTokenType.GUITAR_CHORD)) automataDef = new GuitarChordDefinition();
    else if (abcTokenType==(AbcTokenType.GRACING_BEGIN)) automataDef = new GracingBeginDefinition();
    else if (abcTokenType==(AbcTokenType.ACCIACCATURA)) automataDef = new AcciaccaturaDefinition(abcVersion);
    else if (abcTokenType==(AbcTokenType.GRACING_END)) automataDef = new GracingEndDefinition();
    else if (abcTokenType==(AbcTokenType.GRACING)) automataDef = new GracingDefinition(abcVersion);
    else if (abcTokenType==(AbcTokenType.SYMBOL_BEGIN)) automataDef = new SymbolBeginDefinition(abcVersion);
    else if (abcTokenType==(AbcTokenType.SYMBOL)) automataDef = new SymbolDefinition(abcVersion);
    else if (abcTokenType==(AbcTokenType.SYMBOL_END)) automataDef = new SymbolEndDefinition(abcVersion);
    else if (abcTokenType==(AbcTokenType.ANNOTATION_BEGIN)) automataDef = new AnnotationBeginDefinition();
    else if (abcTokenType==(AbcTokenType.ANNOTATION)) automataDef = new AnnotationDefinition();
    else if (abcTokenType==(AbcTokenType.ANNOTATION_END)) automataDef = new AnnotationEndDefinition();
    else if (abcTokenType==(AbcTokenType.SPACER)) automataDef = new SpacerDefinition(abcVersion);
    else if (abcTokenType==(AbcTokenType.REST)) automataDef = new RestDefinition(abcVersion);
    else if (abcTokenType==(AbcTokenType.BARLINE)) automataDef = new BarlineDefinition(abcVersion);
    else if (abcTokenType==(AbcTokenType.NTH_REPEAT)) automataDef = new NthRepeatDefinition();
    else if (abcTokenType==(AbcTokenType.BEGIN_SLUR)) automataDef = new SlurBeginDefinition();
    else if (abcTokenType==(AbcTokenType.END_SLUR)) automataDef = new SlurEndDefinition();
    else if (abcTokenType==(AbcTokenType.USER_DEFINED)) automataDef = new UserDefinedDefinition();
    else if (abcTokenType==(AbcTokenType.OCTAVE)) automataDef = new OctaveDefinition();
    else if (abcTokenType==(AbcTokenType.BROKEN_RHYTHM)) automataDef = new BrokenRhythmDefinition();
    else if (abcTokenType==(AbcTokenType.TIE)) automataDef = new TieDefinition();
    else if (abcTokenType==(AbcTokenType.TUPLET_SPEC)) automataDef = new TupletSpecDefinition();
    else if (abcTokenType==(AbcTokenType.MULTI_NOTE_BEGIN)) automataDef = new MultiNoteBeginDefinition();
    else if (abcTokenType==(AbcTokenType.MULTI_NOTE_END)) automataDef = new MultiNoteEndDefinition();
//    else if (abcTokenType==(AbcTokenType.TEX_COMMAND)) automataDef = new TexCommandDefinition();
    else if (abcTokenType==(AbcTokenType.CHORD_NAME)) automataDef = new ChordNameDefinition();
    else if (abcTokenType==(AbcTokenType.EQUALS)) automataDef = new EqualsDefinition();
    else if (abcTokenType==(AbcTokenType.C_TEMPO)) automataDef = new TempoCDefinition();
    else if (abcTokenType==(AbcTokenType.COMA)) automataDef = new ComaDefinition();
    else throw new RuntimeException("NO AUTOMATA FOR " + abcTokenType);
    m_allPreviouslyCreatedDefinitions.add(new DefinitionCache(tokenTypes, automataDef));
    //System.out.println("Creating item in cache for  " + toString(tokenTypes));

    }
//    else
//      System.out.println("Using cache for  " + toString(tokenTypes));

    return automataDef;
  }

  /*
	 * public static AutomataDefinition getDefinition(Set aSet) { aSet. return
	 * null; }
	 */

	public static AutomataDefinition getDefinition(TokenType[] tokenTypes,
			AbcVersion abcVersion) {
		// System.out.println("getAutomataFor(" + toString(tokenTypes));
		AutomataDefinition alreadyCreated = getAlreadyCreatedDefinition(tokenTypes);
		if (alreadyCreated != null) {
			// System.out.println("Cache used for " + toString(tokenTypes));
			return alreadyCreated;
		} else {
			AutomataDefinition definition = getDefinition(tokenTypes[0],
					abcVersion);
			if (tokenTypes.length >= 1) {
				for (int i = 1; i < tokenTypes.length; i++)
					definition = definition.union(getDefinition(tokenTypes[i],
							abcVersion));
			}
			// System.out.println("Adding automataDef in cache for : " +
			// toString(tokenTypes));
			m_allPreviouslyCreatedDefinitions.addElement(new DefinitionCache(
					tokenTypes, definition));
			return definition;
		}
	}

  private static AutomataDefinition getAlreadyCreatedDefinition(TokenType[] tokenTypes)
  {
    AutomataDefinition alreadyCreatedAutomata = null;
    for (int i=0; i<m_allPreviouslyCreatedDefinitions.size() && (alreadyCreatedAutomata== null); i++)
    {
      if (((DefinitionCache)m_allPreviouslyCreatedDefinitions.elementAt(i)).isValidFor(tokenTypes))
        alreadyCreatedAutomata = ((DefinitionCache)m_allPreviouslyCreatedDefinitions.elementAt(i)).getAutomataResult();
    }
    return alreadyCreatedAutomata;
  }

  public static class DefinitionCache
  {
    private TokenType[] m_tokenTypes=null;
    private AutomataDefinition m_automataDefResult=null;

    public DefinitionCache(TokenType[] tokenTypes, AutomataDefinition automataDef)
    {
      m_tokenTypes = tokenTypes;
      m_automataDefResult = automataDef;
    }

    public boolean isValidFor(TokenType[] tokenTypes)
    {
      boolean isValidFor = true;
      if (m_tokenTypes.length==tokenTypes.length)
      {
        for (int i=0; i<m_tokenTypes.length && isValidFor; i++)
          if (!contains(m_tokenTypes[i], tokenTypes))
            isValidFor=false;
      }
      else
        isValidFor = false;
      return isValidFor;
    }

    public AutomataDefinition getAutomataResult()
    { return m_automataDefResult; }

    public TokenType[] getTokenTypes()
    { return m_tokenTypes; }

  }

  private static boolean contains(TokenType type, TokenType[] types)
  {
    boolean contains = false;
    int index = 0;
    while (!contains && index<types.length)
      if (type.equals(types[index]))
        contains = true;
      else
        index++;
    return contains;
  }

  public static String toString(TokenType[] types)
  {
    String s = "[";
    for (int i=0; i<types.length; i++)
      s = s.concat("," + types[i].toString());
    s = s.concat("]");
    return s;

  }
}
