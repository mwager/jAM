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

import java.awt.Color;
import java.awt.Font;
import java.awt.font.TextAttribute;
import java.util.Hashtable;

import abc.ui.swing.ScoreTemplate;

/**
 * Default score template.
 * 
 * Texts:
 * <ul><li>At top center: title and subtitles
 * <li>At top right: composer and origin (area)
 * <li>At top left: lyricist, rhythm, group, parts order
 * <li>At bottom: source, transcriber notes, annotations and file url
 * </ul>
 * 
 * Texts styles (bold, italic) and sizes are defined.<br>
 * File url is blue (not underline).<br>
 * Main font family is Palatino Linotype, which is a bit wider,
 * easier to read (especially for lyrics). Arial and Dialog are
 * used in case of lack of Palatino.
 * 
 * Source field is prefixed by <TT>"Source: "</TT>
 * 
 * Stave lines are in dark gray, all elements are in black.
 */
public class DefaultScoreTemplate extends ScoreTemplate implements Cloneable {

	private static final byte[] FOOTNOTES = new byte[] {
		ScoreElements.TEXT_NOTES, //=ANNOTATIONS
		ScoreElements.TEXT_SOURCE,
		ScoreElements.TEXT_TRANSCRNOTES,
		ScoreElements.TEXT_FILEURL
	};

	private static final long serialVersionUID = -556559340776215872L;

	public DefaultScoreTemplate() {
		super();
		color();
		positions();
		fonts();
		texts();
	}
	
	private void color() {
		setElementColor(ScoreElements._DEFAULT, Color.black);
		setElementColor(ScoreElements.STAFF_LINES, Color.darkGray);
	}
	
	private void fonts() {
		//All fonts are Palatino, then Arial, then Dialog, in
		//order of preference
		setDefaultTextFontFamilyName(
			new String[] {"Palatino Linotype", "Arial", "Dialog"}
		);
		//Part Labels
		setTextFontFamilyName(ScoreElements.PART_LABEL,
			new String[] {"Georgia", "Verdana", "Dialog"});
		//foot notes
		setTextFontFamilyName(FOOTNOTES,
			new String[] {"Arial", "Dialog"}
		);
		
		setTextStyle(
			new byte[] {ScoreElements.TEXT_TITLE,
						ScoreElements.TEXT_SUBTITLE,
						ScoreElements.TEXT_COMPOSER,
						ScoreElements.TEXT_LYRICIST,
						ScoreElements.TEXT_RHYTHM,
						ScoreElements.TEXT_ORIGIN},
			Font.BOLD
		);
		setTextStyle(ScoreElements.TEXT_PARTS_ORDER, Font.ITALIC);
		setTextStyle(ScoreElements.PART_LABEL, Font.BOLD);
		
		setTextSize(ScoreElements.TEXT_TITLE, 200, SizeUnit.PERCENT);
		setTextSize(ScoreElements.TEXT_SUBTITLE, 150, SizeUnit.PERCENT);
		setTextSize(ScoreElements.TEXT_COMPOSER, 125, SizeUnit.PERCENT);
		setTextSize(ScoreElements.PART_LABEL, 150, SizeUnit.PERCENT);
		setTextSize(ScoreElements.TEXT_ANNOTATIONS, 100, SizeUnit.PERCENT);
		setTextSize(FOOTNOTES, 10, SizeUnit.PT);
		
		Hashtable urlLink = new Hashtable();
		//urlLink.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_LOW_ONE_PIXEL);
		urlLink.put(TextAttribute.FOREGROUND, Color.BLUE);
		setTextAttributes(ScoreElements.TEXT_FILEURL, urlLink);
		setTextStyle(ScoreElements.TEXT_FILEURL, Font.ITALIC);
	}
	
	private void positions() {
		setPosition(
			new byte[] { ScoreElements.TEXT_TITLE,
						 ScoreElements.TEXT_SUBTITLE },
			VerticalPosition.TOP,
			HorizontalPosition.CENTER
		);
		setPosition(
			new byte[] { ScoreElements.TEXT_COMPOSER,
						 ScoreElements.TEXT_ORIGIN },
			VerticalPosition.TOP,
			HorizontalPosition.RIGHT
		);
		setPosition(
			new byte[] { ScoreElements.TEXT_LYRICIST,
						 ScoreElements.TEXT_RHYTHM,
						 ScoreElements.TEXT_GROUP,
						 ScoreElements.TEXT_PARTS_ORDER },
			VerticalPosition.TOP,
			HorizontalPosition.LEFT
		);
		
		setPosition(
			new byte[] { ScoreElements.TEXT_CHORDS,
						 ScoreElements.TEMPO,
						 ScoreElements.PART_LABEL },
			VerticalPosition.ABOVE_STAFF,
			HorizontalPosition.LEFT
		);
		
		setPosition(FOOTNOTES,
			VerticalPosition.BOTTOM,
			HorizontalPosition.LEFT
		);
		
		setPosition(ScoreElements._DEFAULT,
				VerticalPosition.BOTTOM,
				HorizontalPosition.LEFT
		);
	}
	
	private void texts() {
		setTextPrefix(ScoreElements.TEXT_SOURCE, "Source: ");
	}
	
}
