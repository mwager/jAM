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

import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * ABC text replacements resource bundle
 * <BR>
 * e.g.: <TT>\^a</TT> &rarr; <TT>â</TT>
 */
public class AbcTextReplacements extends ResourceBundle {

	private static AbcTextReplacements instance = null;
	
	static final Map map = new HashMap();

	public static AbcTextReplacements getInstance() {
		if (instance == null)
			instance = new AbcTextReplacements();
		return instance;
	}

	private AbcTextReplacements() {
		map.put("\\`A", "\u00C0");//A-grave
		map.put("\\`a", "\u00E0");//a-grave
		map.put("\\'A", "\u00C1");//A-acute
		map.put("\\'a", "\u00E1");//a-acute
		map.put("\\^A", "\u00C2");//A-circumflex
		map.put("\\^a", "\u00E2");//a-circumflex
		map.put("\\~A", "\u00C3");//A-tilde
		map.put("\\~a", "\u00E3");//a-tilde
		map.put("\\\"A", "\u00C4");//A-umlaut
		map.put("\\\"a", "\u00E4");//a-umlaut
		map.put("\\oA", "\u00C5");//A-ring
		map.put("\\oa", "\u00E5");//a-ring
		map.put("\\=A", "\u0100");//A-macron
		map.put("\\=a", "\u0101");//a-macron
		map.put("\\uA", "\u0102");//A-breve
		map.put("\\ua", "\u0103");//a-breve
		map.put("\\;A", "\u0104");//A-ogonek
		map.put("\\;a", "\u0105");//a-ogonek
		map.put("\\,C", "\u00C7");//C-cedilla
		map.put("\\,c", "\u00E7");//c-cedilla
		map.put("\\'C", "\u0106");//C-acute
		map.put("\\'c", "\u0107");//c-acute
		map.put("\\^C", "\u0108");//C-circ
		map.put("\\^c", "\u0109");//c-circ
		map.put("\\.C", "\u010A");//C-dotabove
		map.put("\\.c", "\u010B");//c-dotabove
		map.put("\\vC", "\u010C");//C-caron
		map.put("\\vc", "\u010D");//c-caron
		map.put("\\vD", "\u010E");//D-caron
		map.put("\\vd", "\u010F");//d-caron
		map.put("\\=D", "\u0110");//D-stroke
		map.put("\\=d", "\u0111");//d-stroke
		map.put("\\`E", "\u00C8");//E-grave
		map.put("\\`e", "\u00E8");//e-grave
		map.put("\\'E", "\u00C9");//E-acute
		map.put("\\'e", "\u00E9");//e-acute
		map.put("\\^E", "\u00CA");//E-circ
		map.put("\\^e", "\u00EA");//e-circ
		map.put("\\\"E", "\u00CB");//E-umlaut
		map.put("\\\"e", "\u00EB");//e-umlaut
		map.put("\\=E", "\u0112");//E-macron
		map.put("\\=e", "\u0113");//e-macron
		map.put("\\uE", "\u0114");//E-breve
		map.put("\\ue", "\u0115");//e-breve
		map.put("\\.E", "\u0116");//E-dotabove
		map.put("\\.e", "\u0117");//e-dotabove
		map.put("\\;E", "\u0118");//E-ogonek
		map.put("\\;e", "\u0119");//e-ogonek
		map.put("\\vE", "\u011A");//E-caron
		map.put("\\ve", "\u011B");//e-caron
		map.put("\\^G", "\u011C");//G-circ
		map.put("\\^g", "\u011D");//g-circ
		map.put("\\uG", "\u011E");//G-breve
		map.put("\\ug", "\u011F");//g-breve
		map.put("\\.G", "\u0120");//G-dotabove
		map.put("\\.g", "\u0121");//g-dotabove
		map.put("\\,G", "\u0122");//G-cedilla
		map.put("\\,g", "\u0123");//g-cedilla
		map.put("\\^H", "\u0124");//H-circ
		map.put("\\^h", "\u0125");//h-circ
		map.put("\\=H", "\u0126");//H-stroke
		map.put("\\=h", "\u0127");//h-stroke
		map.put("\\`I", "\u00CC");//I-grave
		map.put("\\`i", "\u00EC");//i-grave
		map.put("\\'I", "\u00CD");//I-acute
		map.put("\\'i", "\u00ED");//i-acute
		map.put("\\^I", "\u00CE");//I-circ
		map.put("\\^i", "\u00EE");//i-circ
		map.put("\\\"I", "\u00CF");//I-umlaut
		map.put("\\\"i", "\u00EF");//i-umlaut
		map.put("\\~I", "\u0128");//I-tilde
		map.put("\\~i", "\u0129");//i-tilde
		map.put("\\=I", "\u012A");//I-macron
		map.put("\\=i", "\u012B");//i-macron
		map.put("\\uI", "\u012C");//I-breve
		map.put("\\ui", "\u012D");//i-breve
		map.put("\\;I", "\u012E");//I-ogonek
		map.put("\\;i", "\u012F");//i-ogonek
		map.put("\\.I", "\u0130");//I-dotabove
		map.put("\\.i", "\u0131");//i-dotless
		map.put("\\^J", "\u0134");//J-circ
		map.put("\\^j", "\u0135");//j-circ
		map.put("\\,K", "\u0136");//K-cedilla
		map.put("\\,k", "\u0137");//k-cedilla
		map.put("\\'L", "\u0139");//L-acute
		map.put("\\'l", "\u013A");//l-acute
		map.put("\\,L", "\u013B");//L-cedilla
		map.put("\\,l", "\u013C");//l-cedilla
		map.put("\\vL", "\u013D");//L-caron
		map.put("\\vl", "\u013E");//l-caron
		map.put("\\.L", "\u013F");//L-dotafter
		map.put("\\.l", "\u0140");//l-dotafter
		map.put("\\/L", "\u0141");//L-slash
		map.put("\\/l", "\u0142");//l-slash
		map.put("\\~N", "\u00D1");//N-tilde
		map.put("\\~n", "\u00F1");//n-tilde
		map.put("\\'N", "\u0143");//N-acute
		map.put("\\'n", "\u0144");//n-acute
		map.put("\\,N", "\u0145");//N-cedilla
		map.put("\\,n", "\u0146");//n-cedilla
		map.put("\\vN", "\u0147");//N-caron
		map.put("\\vn", "\u0148");//n-caron
		map.put("\\`O", "\u00D2");//O-grave
		map.put("\\`o", "\u00F2");//o-grave
		map.put("\\'O", "\u00D3");//O-acute
		map.put("\\'o", "\u00F3");//o-acute
		map.put("\\^O", "\u00D4");//O-circ
		map.put("\\^o", "\u00F4");//o-circ
		map.put("\\~O", "\u00D5");//O-tilde
		map.put("\\~o", "\u00F5");//o-tilde
		map.put("\\\"O", "\u00D6");//O-umlaut
		map.put("\\\"o", "\u00F6");//o-umlaut
		map.put("\\/O", "\u00D8");//O-slash
		map.put("\\/o", "\u00F8");//o-slash
		map.put("\\=O", "\u014C");//O-macron
		map.put("\\=o", "\u014D");//o-macron
		map.put("\\uO", "\u014E");//O-breve
		map.put("\\uo", "\u014F");//o-breve
		map.put("\\:O", "\u0150");//O-hungumlaut
		map.put("\\:o", "\u0151");//o-hungumlaut
		map.put("\\'R", "\u0154");//R-acute
		map.put("\\'r", "\u0155");//r-acute
		map.put("\\,R", "\u0156");//R-cedilla
		map.put("\\,r", "\u0157");//r-cedilla
		map.put("\\vR", "\u0158");//R-caron
		map.put("\\vr", "\u0159");//r-caron
		map.put("\\'S", "\u015A");//S-acute
		map.put("\\'s", "\u015B");//s-acute
		map.put("\\^S", "\u015C");//S-circ
		map.put("\\^s", "\u015D");//s-circ
		map.put("\\,S", "\u015E");//S-cedilla
		map.put("\\,s", "\u015F");//s-cedilla
		map.put("\\vS", "\u0160");//S-caron
		map.put("\\vs", "\u0161");//s-caron
		map.put("\\,T", "\u0162");//T-cedilla
		map.put("\\,t", "\u0163");//t-cedilla
		map.put("\\vT", "\u0164");//T-caron
		map.put("\\vt", "\u0165");//t-caron
		map.put("\\=T", "\u0166");//T-stroke
		map.put("\\=t", "\u0167");//t-stroke
		map.put("\\`U", "\u00D9");//U-grave
		map.put("\\`u", "\u00F9");//u-grave
		map.put("\\'U", "\u00DA");//U-acute
		map.put("\\'u", "\u00FA");//u-acute
		map.put("\\^U", "\u00DB");//U-circ
		map.put("\\^u", "\u00FB");//u-circ
		map.put("\\\"U", "\u00DC");//U-umlaut
		map.put("\\\"u", "\u00FC");//u-umlaut
		map.put("\\~U", "\u0168");//U-tilde
		map.put("\\~u", "\u0169");//u-tilde
		map.put("\\=U", "\u016A");//U-macron
		map.put("\\=u", "\u016B");//u-macron
		map.put("\\uU", "\u016C");//U-breve
		map.put("\\uu", "\u016D");//u-breve
		map.put("\\oU", "\u016E");//U-ring
		map.put("\\ou", "\u016F");//u-ring
		map.put("\\:U", "\u0170");//U-hungumlaut
		map.put("\\:u", "\u0171");//u-hungumlaut
		map.put("\\;U", "\u0172");//U-ogonek
		map.put("\\;u", "\u0173");//u-ogonek
		map.put("\\^W", "\u0174");//W-circ
		map.put("\\^w", "\u0175");//w-circ
		map.put("\\'Y", "\u00DD");//Y-acute
		map.put("\\'y", "\u00FD");//y-acute
		map.put("\\\"Y", "\u0178");//Y-umlaut
		map.put("\\\"y", "\u00FF");//y-umlaut
		map.put("\\^Y", "\u0176");//Y-circ
		map.put("\\^y", "\u0177");//y-circ
		map.put("\\'Z", "\u0179");//Z-acute
		map.put("\\'z", "\u017A");//z-acute
		map.put("\\^Z", "Z");//Z-circ, didn't found the code
		map.put("\\^z", "z");//z-circ, didn't found the code
		map.put("\\.Z", "\u017B");//Z-dotabove
		map.put("\\.z", "\u017C");//z-dotabove
		map.put("\\vZ", "\u017D");//Z-caron
		map.put("\\vz", "\u017E");//z-caron

		//ligatures
		map.put("\\AE", "\u00C6");//AE ligature
		map.put("\\ae", "\u00E6");//ae ligature
		map.put("\\ss", "\u00DF");//ss ligature
		map.put("\\NG", "\u014A");//NG ligature
		map.put("\\ng", "\u014B");//ng ligature
		map.put("\\OE", "\u0152");//OE ligature
		map.put("\\oe", "\u0153");//oe ligature
		
		//Latin Extended-B, added by Sylvain (iubito)
		map.put("\\vA", "\u01CD");//A-caron
		map.put("\\va", "\u01CE");//a-caron
		map.put("\\vI", "\u01CF");//I-caron
		map.put("\\vi", "\u01D0");//i-caron
		map.put("\\vO", "\u01D1");//O-caron
		map.put("\\vo", "\u01D2");//o-caron
		map.put("\\vU", "\u01D3");//U-caron
		map.put("\\vu", "\u01D4");//u-caron
		map.put("\\=G", "\u01E4");//G-stroke
		map.put("\\=g", "\u01E5");//g-stroke
		map.put("\\uG", "\u01E6");//G-breve
		map.put("\\ug", "\u01E7");//g-breve
		map.put("\\uK", "\u01E8");//K-breve
		map.put("\\uk", "\u01E9");//k-breve
		map.put("\\,O", "\u01EA");//O-cedilla
		map.put("\\,o", "\u01EB");//o-cedilla
		map.put("\\'G", "\u01F4");//G-acute
		map.put("\\'g", "\u01F5");//g-acute
		map.put("\\`N", "\u01F8");//N-grave
		map.put("\\`n", "\u01F9");//n-grave
		map.put("\\.A", "\u0226");//A-dotabove
		map.put("\\.a", "\u0227");//a-dotabove
		map.put("\\,E", "\u0228");//E-cedilla
		map.put("\\,e", "\u0229");//e-cedilla
		map.put("\\.O", "\u022E");//O-dotabove
		map.put("\\.o", "\u022F");//o-dotabove
		map.put("\\=Y", "\u0232");//Y-macron
		map.put("\\=y", "\u0233");//y-macron
	}

	public Enumeration getKeys() {
		//return Collections.enumeration(map.keySet());
		return new Enumeration() {
			private final Iterator i = map.keySet().iterator();
			public boolean hasMoreElements() { return i.hasNext(); }
			public Object nextElement() { return i.next(); }
		};
	}
	
	public Collection values() {
		return map.values();
	}

	protected Object handleGetObject(String key) {
		return map.get(key);
	}

}
