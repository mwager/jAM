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

public class Bar implements Cloneable, Serializable {
	
	private static final long serialVersionUID = 2228266565656223997L;

	private short m_barNumber;

	private int m_posInMusic;

	protected Bar(short barNum, int posInMus) {
		m_barNumber = barNum;
		m_posInMusic = posInMus;
	}

	protected short getBarNumber() {
		return m_barNumber;
	}

	protected void setBarNumber(short s) {
		m_barNumber = s;
	}

	protected int getPosInMusic() {
		return m_posInMusic;
	}

	protected void setPosInMusic(int posInMusic) {
		this.m_posInMusic = posInMusic;
	}
}