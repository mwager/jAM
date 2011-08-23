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

import scanner.Set;

/** 
 * This class provides parser instances that reduce the parsing 
 * scope to tunes headers (music is excluded). This results in a much 
 * faster parsing than parsing the whole tunes and enables you to 
 * build up tunes indexes fastly.<BR/>
 * Just invoke the usual methods {@link #parseFile(java.io.File)} or 
 * {@link #parseFile(java.io.Reader)} and you'll get in return instances of 
 * Tune without any music part. */
public class AbcHeadersParser extends AbcFileParser {
	
	/** Default constructor. */
	public AbcHeadersParser() {
		this(AbcVersion.v1_6);
	}

	/** Default constructor. */
	public AbcHeadersParser(AbcVersion abcVersion) {
		super(abcVersion);
		// Override the definition of an abc line 
		FIRST_ABC_LINE = new Set(AbcTokenType.TEXT);
	}
	
	/** Overrides the standard definition of an 
	 * abc-line ::= (1*element line-ender) / mid-tune-field / tex-command 
	 * and replaces it as "text" to accelerate the parsing and avoid retrieving
	 * tokens from this part. */
    protected void parseAbcLine(Set follow) {
    	//System.out.println(this.getClass().getName() + "parsing line !");
    	Set current = new Set(AbcTokenType.LINE_FEED);
    	accept(AbcTokenType.TEXT, current, follow);
    	current.remove(AbcTokenType.LINE_FEED);
    	accept(AbcTokenType.LINE_FEED, current, follow);
    }

}
