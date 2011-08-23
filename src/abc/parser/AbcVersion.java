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

public class AbcVersion {

	public static final AbcVersion v1_6 = new AbcVersion(1.6f);
	public static final AbcVersion v2_0 = new AbcVersion(2f);

	private float m_version = 0f;

	private AbcVersion(float version) {
		m_version = version;
	}

	public boolean isGreaterOrEqual(AbcVersion av) {
		return m_version >= av.m_version;
	}

	public boolean equals(Object o) {
		if (o instanceof AbcVersion) {
			return ((AbcVersion) o).m_version == m_version;
		} else
			return super.equals(o);
	}

}
