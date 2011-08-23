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

import javax.sound.midi.MetaMessage;
import abc.notation.KeySignature;

/** A midi message to set key signature DOES NOT WORK !!!. */
public class KeySignatureMessage extends MetaMessage
{
  /** Creates a midi message to change key from an ABC key.
   * @param key */
  public KeySignatureMessage(KeySignature key)
  {
    super();

/*    FF 59 02 sf mi  Key Signature
 Key Signature, expressed as the number of sharps or flats, and a major/minor flag.
0 represents a key of C, negative numbers represent 'flats', while positive numbers represent 'sharps'.

 sf  number of sharps or flats
-7 = 7 flats
0 = key of C
+7 = 7 sharps
 mi  0 = major key
1 = minor key
  */

  }
}
