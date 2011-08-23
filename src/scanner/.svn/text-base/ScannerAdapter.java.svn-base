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
package scanner;

import java.util.EventListener;

/** An empty  default implementation of for scanner listener. */
public class ScannerAdapter implements ScannerListenerInterface, EventListener
{
  /** Invoked when a new token has been generated.
   * @param event Event containing all information about the token generated. */
  public void tokenGenerated(TokenEvent event)
  {}

  /** Invoked when an invalid character has been found.
   * @param evt Event containing all information about the invalid character
   * found. */
  public void invalidCharacter(InvalidCharacterEvent evt)
  {}

  /** Invoked when a line has been processed.
   * @param line The line that has just been processed. */
  public void lineProcessed(String line)
  {}
}
