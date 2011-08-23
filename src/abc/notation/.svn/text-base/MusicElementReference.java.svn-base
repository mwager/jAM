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
package abc.notation;

import java.io.Serializable;

/**
 * A music element "reference" is a set of 3 coordinates [part label ; index in
 * Music ; index in MultiNote]<br>
 * <br>
 * <ul>
 * <li>the part label, <TT>' '</TT> for default part
 * <li>the "horizontal" index set when added in {@link abc.notation.Tune.Music}
 * elements collection, <TT>-1</TT> if not set
 * <li>the "vertical" index for each note in a {@link abc.notation.MultiNote},
 * <TT>-1</TT> if not set.
 * </ul>
 */
public class MusicElementReference implements Serializable, Cloneable {

	private static final long serialVersionUID = 2819815880924977717L;

	private char part = ' ';
	
	private byte voice = 1;

	private short x = -1;

	private byte y = -1;

	protected MusicElementReference() {
		this(' ', (byte)1, (short)-1, (byte)-1);
	}
	
	public MusicElementReference(char p, byte _voice, short _x, byte _y) {
		part = p;
		voice = _voice;
		x = _x;
		y = _y;
	}

	public char getPart() {
		return part;
	}
	
	public byte getVoice() {
		return voice;
	}

	public short getX() {
		return x;
	}

	public byte getY() {
		return y;
	}

	protected void setPart(char p) {
		this.part = p;
	}
	
	protected void setVoice(byte _v) {
		this.voice = _v;
	}

	protected void setX(short _x) {
		this.x = _x;
	}

	protected void setY(byte _y) {
		this.y = _y;
	}

	public Object clone() {
		return new MusicElementReference(part, voice, x, y);
	}
	
	public boolean equals(Object o) {
		if (o instanceof MusicElementReference) {
			MusicElementReference msr = (MusicElementReference) o;
			return msr.getPart()==getPart()
				&& msr.getVoice()==getVoice()
				&& msr.getX()==getX()
				&& msr.getY()==getY();
		} else {
			return super.equals(o);
		}
	}
	
	public String toString() {
		return "[P:"+part+";V:"+voice+";"+x+";"+y+"]";
	}
}
