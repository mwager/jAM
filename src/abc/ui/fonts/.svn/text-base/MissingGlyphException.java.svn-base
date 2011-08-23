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
package abc.ui.fonts;

public class MissingGlyphException extends RuntimeException {

	private static final long serialVersionUID = -4681153095189221311L;

	protected MissingGlyphException(String oToString,
			MusicalFont font) {
		super("Missing glyph for " + oToString
				+ " in font " + font.getName());
	}

	protected MissingGlyphException(Object o,
			MusicalFont font) {
		this(o.toString(), font);
	}
	
}
