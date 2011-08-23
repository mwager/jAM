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
package abc.ui.swing;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import abc.ui.fonts.MusicalFont;
import abc.ui.scoretemplates.AttributeNotDefinedException;
import abc.ui.scoretemplates.HorizontalPosition;
import abc.ui.scoretemplates.ScoreAttribute;
import abc.ui.scoretemplates.SizeUnit;
import abc.ui.scoretemplates.ScoreElements;
import abc.ui.scoretemplates.VerticalPosition;

/**
 * Like a stylesheet, a score template determinates texts (title, composer,
 * annotations...) positions, visibility, font size and style...
 * 
 * <ul>
 * <li>Fields are denoted by {@link ScoreElements} constants such as
 * {@link ScoreElements#TEXT_TITLE}, {@link ScoreElements#TEXT_COMPOSER}.
 * <li>positions are denoted by a couple of byte from {@link VerticalPosition} and
 * {@link HorizontalPosition} constants.
 * <li>font size can be expressed in percent of the default text size or in
 * fixed pt. See {@link #setTextSize(byte, float, byte)}
 * <li>font style is denoted by constants from Font class : {@link Font#PLAIN},
 * {@link Font#BOLD}, {@link Font#ITALIC} and Font.BOLD+Font.ITALIC
 * </ul>
 * 
 * A score template defines also notation font size, margins,
 * space between 2 staves...
 * 
 * @see abc.ui.scoretemplates.DefaultScoreTemplate
 */
//TODO tutorial "how to create a score template?"
public abstract class ScoreTemplate implements Cloneable, Serializable {
	
	private static final long serialVersionUID = 7515101953534976829L;
	
	private class FieldInfos implements Cloneable, Serializable {
		private static final long serialVersionUID = 6515009020904541858L;
		String[] m_fontFamilyNames = {};
		float m_fontSize = 100f;
		SizeUnit m_fontSizeUnit = SizeUnit.PERCENT;
		int m_fontStyle = Font.PLAIN;
		String m_prefix = null;
		String m_suffix = null;
		Hashtable m_textAttributes = null;
		byte m_textField;
		boolean m_visible = true;
		FieldInfos(byte textField) {
			m_textField = textField;
		}
		public Object clone() throws CloneNotSupportedException {
			FieldInfos ret = new FieldInfos(m_textField);
			ret.m_fontFamilyNames = (String[]) m_fontFamilyNames.clone();
			ret.m_fontSize = m_fontSize;
			ret.m_fontSizeUnit = (SizeUnit) m_fontSizeUnit.clone();
			ret.m_fontStyle = m_fontStyle;
			ret.m_prefix = m_prefix;
			ret.m_suffix = m_suffix;
			if (m_textAttributes != null)
				ret.m_textAttributes = (Hashtable) m_textAttributes.clone();
			ret.m_textField = m_textField;
			ret.m_visible = m_visible;
			return ret;
		}
		public boolean equals(Object o) {
			if (o instanceof FieldInfos)
				return ((FieldInfos) o).m_textField == m_textField;
			else
				return super.equals(o);
		}
		public int hashCode() {
			return super.hashCode();
		}
		private String styleToString() {
			switch (m_fontStyle) {
			case Font.PLAIN: return "plain";
			case Font.BOLD: return "bold";
			case Font.ITALIC: return "italic";
			case Font.BOLD+Font.ITALIC: return "bold+italic";
			default: return "<unknown font style>";
			}
		}
		public String toString() {
			StringBuffer ret = new StringBuffer();
			ret.append(ScoreElements.toString(m_textField)+" ("+m_textField+") {\n");
			ret.append("\tfont face: "+Arrays.toString(m_fontFamilyNames)+";\n");
			ret.append("\tfont size: "+m_fontSize+m_fontSizeUnit+";\n");
			ret.append("\tfont style: "+styleToString()+";\n");
			ret.append("\tprefix: "+m_prefix+";\n");
			ret.append("\tsuffix: "+m_suffix+";\n");
			if (m_textAttributes != null) {
				ret.append("\ttext attributes {\n");
				Iterator it = m_textAttributes.keySet().iterator();
				while (it.hasNext()) {
					Object k = it.next();
					Object v = m_textAttributes.get(k);
					ret.append("\t"+k+": "+v+";\n");
				}
				ret.append("\t}\n");
			}
			ret.append("\tvisible: "+(m_visible?"true":"false")+";\n");
			ret.append("}");
			return ret.toString();
		}
	}

	private class Position implements Cloneable, Serializable {
		private static final long serialVersionUID = -395979710910173640L;
		byte m_horizontal = HorizontalPosition.LEFT;
		byte m_vertical = VerticalPosition.TOP;
		private Position(byte v, byte h) {
			m_vertical = v;
			m_horizontal = h;
		}
		public Object clone() {
			return new Position(m_vertical, m_horizontal);
		}
		/* @param s "top;center", "Position[top;center]" */
		/*Position(String s) {
			this();
			if ((s.indexOf('[') != -1) && (s.indexOf(']') != -1)) {
				s = s.substring(s.indexOf('[')+1, s.indexOf(']'));
			}
			if (s.indexOf(';') != -1) {
				String[] s2 = s.split(";");
				if (s2.length == 2) {
					m_vertical = VerticalPosition.toVerticalAlign(s2[0]);
					m_horizontal = HorizontalAlign.toHorizontalAlign(s2[1]);
				}
			}
		}*/
		public boolean equals(Object o) {
			if (o instanceof Position) {
				Position p = (Position) o;
				return p.m_horizontal == m_horizontal
						&& p.m_vertical == m_vertical;
			} else if (o instanceof byte[]) {
				byte[] bs = (byte[]) o;
				return m_vertical == bs[0] && m_horizontal == bs[1];
			} else
				return super.equals(o);
		}
		public int hashCode() {
			//don't know why, but with super.hashCode
			//m_fieldPositions.get(aPosition) never find
			//an already stored position
			//return super.hashCode();
			return m_vertical*1000 + m_horizontal;
		}
		public String toString() {
			return "Position["+VerticalPosition.toString(m_vertical)
				+";"+HorizontalPosition.toString(m_horizontal)+"]";
		}
	}

	private Hashtable m_attributes = new Hashtable();
	
	private String[] m_defaultTextFontFamilyNames = { "Dialog", "Arial" };

	private transient Engraver m_engraver = null;

	/** Map of Byte(TextField.xxx) => FieldInfos */
	private Hashtable m_fields = new Hashtable();

	/** Map of Position => Vector of TextField.xxx */
	private Hashtable m_fieldsPosition = new Hashtable();

	/** Map of font names => boolean */
	private transient Hashtable m_fontAvailability = null;

	private transient Graphics2D m_graphics = null;
	
	private transient Vector m_listeners = null;

	private transient ScoreMetrics m_metrics = null;
	
	private Vector getListeners() {
		if (m_listeners == null)
			m_listeners = new Vector(2, 2);
		return m_listeners;
	}
	
	public void addListener(ScoreTemplateChangeListener stcl) {
		if (getListeners().indexOf(stcl) == -1) {
			getListeners().add(stcl);
			stcl.onTemplateChange();
		}
	}
	
	/**
	 * Return a full copy of this template.
	 */
	public Object clone() {
		Object clone = null;
		try {
			// Write the object out to a byte array
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(bos);
			out.writeObject(this);
			out.flush();
			out.close();
			// Make an input stream from the byte array and read
			// a copy of the object back in.
			ObjectInputStream in = new ObjectInputStream(
				new ByteArrayInputStream(bos.toByteArray()));
			clone = in.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return clone;
	}
	
	/**
	 * Returns the boolean value associated to an attribute
	 * 
	 * @param sa
	 * @throws RuntimeException if sa doesn't accept boolean value
	 */
	public boolean getAttributeBoolean(ScoreAttribute sa) {
		if (!(sa.getDefaultValue() instanceof Boolean))
			throw new RuntimeException(sa.toString()
					+ " is not a boolean attribute");
		return ((Boolean) getAttributeObject(sa)).booleanValue();
	}
	
	/**
	 * Returns the number value associated to an attribute, as
	 * a double.
	 * 
	 * @param sa
	 * @throws RuntimeException if sa doesn't accept boolean value
	 */
	public double getAttributeNumber(ScoreAttribute sa) {
		if (!(sa.getDefaultValue() instanceof Number))
			throw new RuntimeException(sa.toString()
					+ " is not a number attribute");
		return ((Number) getAttributeObject(sa)).doubleValue();
	}

	/**
	 * Returns the object associated to an attribute
	 * 
	 * @param sa
	 * @return an object or <TT>null</TT> if attribute has not been defined.
	 * @see #getAttributeSize(ScoreAttribute)
	 */
	public Object getAttributeObject(ScoreAttribute sa) {
		Object ret = m_attributes.get(sa.getName());
		if (ret == null)
			ret = sa.getDefaultValue();
		return ret;
	}
	
	/**
	 * <B>For the real size, see {@link #getAttributeSize(ScoreAttribute)}!</B>
	 * <br>
	 * Gets the size of an attribute, without calculation i.e. the value
	 * returned has no unit information, you will not know if it's
	 * {@link SizeUnit#PX} or {@link SizeUnit#STAFF_HEIGHT} or ... <br>
	 * <br>
	 * May be useful for option panels where slider or other input component
	 * have the rough size value, without unit, and you want to keep the default
	 * unit (using {@link #setAttributeSize(ScoreAttribute, float)})
	 * 
	 * @param sa
	 * @return a float value
	 * @throws AttributeNotDefinedException
	 *             if attribute has not been defined in the template
	 * @throws RuntimeException
	 *             if attribute doesn't accept size value
	 */
	public float getAttributeRoughSize(ScoreAttribute sa)
			throws AttributeNotDefinedException, RuntimeException {
		if (!(sa.getDefaultValue() instanceof ScoreAttribute.Size))
			throw new RuntimeException(sa.toString()
					+ " is not a size attribute");
		ScoreAttribute.Size s = (ScoreAttribute.Size) getAttributeObject(sa);
		if (s == null)
			throw new AttributeNotDefinedException(sa);
		return s.getSize();
	}
	
	public Hashtable getAttributes() {
		return m_attributes;
	}
	
	/**
	 * Gets the size of an attribute, calculated from score metrics if needed.
	 * 
	 * @param sa
	 * @return a float value
	 * @throws AttributeNotDefinedException
	 *             if attribute has not been defined in the template
	 * @throws RuntimeException
	 *             if size unit is unknown or attribute doesn't accept size
	 *             value
	 */
	public float getAttributeSize(ScoreAttribute sa)
			throws AttributeNotDefinedException, RuntimeException {
		if (!(sa.getDefaultValue() instanceof ScoreAttribute.Size))
			throw new RuntimeException(sa.toString()
					+ " is not a size attribute");
		ScoreAttribute.Size s = (ScoreAttribute.Size) getAttributeObject(sa);
		if (s == null)
			throw new AttributeNotDefinedException(sa);
		SizeUnit u = s.getUnit();
		float size = s.getSize();
		if ((u.equals(SizeUnit.PX)) || (u.equals(SizeUnit.PT))) {
			return size;
		} else if (u.equals(SizeUnit.NOTE_HEIGHT)) {
			return (float) (size * getMetrics().getNoteHeight());
		} else if (u.equals(SizeUnit.NOTE_WIDTH)) {
			return (float) (size * getMetrics().getNoteWidth());
		} else if (u.equals(SizeUnit.GRACENOTE_HEIGHT)) {
			return (float) (size * getMetrics().getGraceNoteHeight());
		} else if (u.equals(SizeUnit.GRACENOTE_WIDTH)) {
			return (float) (size * getMetrics().getGraceNoteWidth());
		} else if (u.equals(SizeUnit.STAFF_HEIGHT)) {
			return (float) (size * getMetrics().getStaffCharBounds()
					.getHeight());
		} else if (u.equals(SizeUnit.STAFF_WIDTH)) {
			return (float) (size * getMetrics().getStaffCharBounds()
					.getWidth());
		} else if (u.equals(SizeUnit.PERCENT)) {
			return (float) (size/100 * getAttributeSize(ScoreAttribute.NOTATION_SIZE));
		} else
			throw new RuntimeException("unknown size unit for attribute: "
					+ sa.toString());
	}
	
	/**
	 * Returns the default text font family names, in order of preference.
	 * 
	 * If font family is not set for a field, it will use the first default font
	 * family available
	 * 
	 * @return e.g. new String[] { "Dialog", "Arial" }
	 */
	public String[] getDefaultTextFontFamilyNames() {
		return m_defaultTextFontFamilyNames;
	}
	
	/**
	 * Returns the default text size in pt.
	 * 
	 * if text size is expressed in percent, all font sizes will be calculated
	 * from default font size.
	 */
	public float getDefaultTextSize() {
		ScoreAttribute.Size s = (ScoreAttribute.Size)
			getAttributeObject(ScoreAttribute.TEXT_DEFAULT_SIZE);
		if (s.getUnit().equals(SizeUnit.PT))
			return s.getSize();
		else {
			//% of notation size
			return (s.getSize()/100f)
				* getAttributeSize(ScoreAttribute.NOTATION_SIZE);
		}
	}
	
	/**
	 * Returns the color of the given element
	 * @param scoreElement One of the {@link ScoreElements} constant
	 * @return a Color object, or <TT>null</TT> if no default color
	 * has been set.
	 */
	public Color getElementColor(byte scoreElement) {
		Object ret = m_attributes.get("COLOR_"+Byte.toString(scoreElement));
		if (ret == null) {
			ret = m_attributes.get("COLOR_"+Byte.toString(ScoreElements._DEFAULT));
			if (ret == null)
				return null; //No default color set
		}
		return (Color) ret;
	}
	
	public Engraver getEngraver() {
		if (m_engraver == null) {
			m_engraver = new Engraver(
				(int) getAttributeNumber(ScoreAttribute.ENGRAVER_MODE),
				(int) getAttributeNumber(ScoreAttribute.ENGRAVER_VARIATION)
			);
		}
		return m_engraver;
	}
	
	private FieldInfos getFieldInfos(byte field) {
		Byte B = new Byte(field);
		if (m_fields.get(B) == null) {
			m_fields.put(B, new FieldInfos(field));
		}
		return (FieldInfos) m_fields.get(B);
	}

	/**
	 * Returns the list of visible fields at the given position
	 * 
	 * @param vert
	 *            one of {@link VerticalPosition} constants
	 * @param horiz
	 *            one of {@link HorizontalPosition} constants
	 * @return array of {@link ScoreElements} constants
	 */
	public byte[] getFieldsAtPosition(byte vert, byte horiz) {
		Position p = new Position(vert, horiz);
		if (m_fieldsPosition.get(p) != null) {
			Vector v = (Vector) m_fieldsPosition.get(p);
			Vector ret = new Vector(v.size());
			for (int i = 0; i < v.size(); i++) {
				byte field = ((Byte) v.get(i)).byteValue();
				if (isVisible(field))
					ret.add(new Byte(field));
			}
			byte[] ret2 = new byte[ret.size()];
			for (int i = 0; i < ret2.length; i++) {
				ret2[i] = ((Byte) ret.get(i)).byteValue();
			}
			return ret2;
		}
		return new byte[0];
	}
	
	/**
	 * Returns the field that are positionned in footer, in order :
	 * <ul>
	 * <li>center
	 * <li>right
	 * <li>left
	 * <li>left+tab
	 * </ul>
	 * 
	 * @return array of {@link ScoreElements} constants
	 */
	public byte[] getFooterFields() {
		return getSectionFields(VerticalPosition.BOTTOM);
	}

	/**
	 * @return Returns the m_graphics.
	 */
	Graphics2D getGraphics() {
		return m_graphics;
	}

	/**
	 * Returns the field that are positionned in header, in order :
	 * <ul>
	 * <li>center
	 * <li>right
	 * <li>left
	 * <li>left+tab
	 * </ul>
	 * 
	 * @return array of {@link ScoreElements} constants
	 */
	public byte[] getHeaderFields() {
		return getSectionFields(VerticalPosition.TOP);
	}

	protected ScoreMetrics getMetrics() {
		if (m_metrics == null) {
			m_metrics = new ScoreMetrics(getGraphics(), this);
		}
		return m_metrics;
	}
	
	/**
	 * Returns the musical font mapping class
	 */
	public MusicalFont getMusicalFont() {
		return (MusicalFont) getAttributeObject(ScoreAttribute.NOTATION_FONT);
	}
	
	public float getNotationFontSize() {
		return getAttributeSize(ScoreAttribute.NOTATION_SIZE);
	}
	
	/**
	 * Returns position of a field
	 * 
	 * @param field
	 *            one of {@link ScoreElements} constants
	 * @return an array of 2 bytes : [VerticalPosition.xxx, HorizontalAlign.xxx]
	 *         <TT>null</TT> if position has not been defined
	 */
	public byte[] getPosition(byte field) {
		Iterator it = m_fieldsPosition.keySet().iterator();
		Byte B = new Byte(field);
		while (it.hasNext()) {
			Position p = (Position) it.next();
			if (((Vector) m_fieldsPosition.get(p)).contains(B)) {
				return new byte[] { p.m_vertical, p.m_horizontal };
			}
		}
		if (field != ScoreElements._DEFAULT) {
			return getPosition(ScoreElements._DEFAULT);
		}
		return null;
	}

	/**
	 * Returns the field that are positionned on verticalSection, in order :
	 * <ul>
	 * <li>center
	 * <li>right
	 * <li>left
	 * <li>left+tab
	 * </ul>
	 * 
	 * @param verticalSection
	 *            {@link VerticalPosition#TOP}, {@link VerticalPosition#BOTTOM}
	 * @return array of {@link ScoreElements} constants
	 */
	private byte[] getSectionFields(byte verticalSection) {
		byte[] center = getFieldsAtPosition(verticalSection,
				HorizontalPosition.CENTER);
		byte[] right = getFieldsAtPosition(verticalSection,
				HorizontalPosition.RIGHT);
		byte[] left = getFieldsAtPosition(verticalSection, HorizontalPosition.LEFT);
		byte[] leftTab = getFieldsAtPosition(verticalSection,
				HorizontalPosition.LEFT_TAB);
		byte[] ret = new byte[center.length + right.length + left.length
				+ leftTab.length];
		int idx = 0;
		System.arraycopy(center, 0, ret, idx, center.length);
		idx += center.length;
		System.arraycopy(right, 0, ret, idx, right.length);
		idx += right.length;
		System.arraycopy(left, 0, ret, idx, left.length);
		idx += left.length;
		System.arraycopy(leftTab, 0, ret, idx, leftTab.length);
		return ret;
	}
	
	/**
	 * Sets the text attributes of a field
	 * 
	 * @param field
	 *            one of {@link ScoreElements} constants
	 * @return
	 *            Map of attributes, <TT>null</TT> possible
	 * @see java.awt.font.TextAttribute
	 */
	public Hashtable getTextAttributes(byte field) {
		return getFieldInfos(field).m_textAttributes;
	}

	/**
	 * Returns the styled and sized font for a field.
	 * 
	 * @param field one of {@link ScoreElements} constants
	 */
	public Font getTextFont(byte field) {
		FieldInfos fi = getFieldInfos(field);
		Vector v = new Vector(m_defaultTextFontFamilyNames.length+fi.m_fontFamilyNames.length);
		for (int i = 0; i < fi.m_fontFamilyNames.length; i++) {
			v.add(fi.m_fontFamilyNames[i]);
		}
		for (int i = 0; i < m_defaultTextFontFamilyNames.length; i++) {
			v.add(m_defaultTextFontFamilyNames[i]);
		}
		Iterator it = v.iterator();
		Font font = null;
		String s = "";
		int style = getTextStyle(field);
		int size = (int) getTextSize(field);
		while (it.hasNext()) {
			String fontName = (String) it.next();
			if (s.length() > 0)
				s += ", ";
			s += fontName;
			if (isFontAvailable(fontName)) {
				font = new Font(fontName, style, size);
				break;
			}
		}
		if (font == null) {
			System.err.println("None of these fonts are available: " + s);
			font = new Font("Dialog", style, size);
		}
		if (fi.m_textAttributes != null) {
			font = font.deriveFont(fi.m_textAttributes);
		}
		return font;
	}

	/**
	 * Returns the prefix of a text field
	 * 
	 * @param field
	 *            one of {@link ScoreElements} constants
	 * @return
	 *            a string or <code>null</code>
	 */
	public String getTextPrefix(byte field) {
		return getFieldInfos(field).m_prefix;
	}
	
	/**
	 * Returns the suffix of a text field
	 * 
	 * @param field
	 *            one of {@link ScoreElements} constants
	 * @return
	 *            a string or <code>null</code>
	 */
	public String getTextSuffix(byte field) {
		return getFieldInfos(field).m_suffix;
	}
	
	/**
	 * Returns the font size of a field, in pt
	 * 
	 * @param field
	 *            one of {@link ScoreElements} constants
	 */
	public float getTextSize(byte field) {
		FieldInfos fi = getFieldInfos(field);
		if (fi.m_fontSizeUnit.equals(SizeUnit.PT))
			return fi.m_fontSize;
		else
			return getDefaultTextSize() * (fi.m_fontSize/100f);
	}

	/**
	 * Returns the font style of a text field
	 * 
	 * @param field
	 *            one of {@link ScoreElements} constants
	 * @return
	 *            style from Font class : {@link Font#PLAIN},
	 *            {@link Font#BOLD}, {@link Font#ITALIC} or
	 *            Font.BOLD+Font.ITALIC
	 */
	public int getTextStyle(byte field) {
		return getFieldInfos(field).m_fontStyle;
	}

	private boolean isFontAvailable(String fontName) {
		if (m_fontAvailability == null)
			m_fontAvailability = new Hashtable();
		if (m_fontAvailability.get(fontName) == null) {
			boolean b = false;
			String[] availableFamily = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getAvailableFontFamilyNames();
			for (int i = 0; i < availableFamily.length; i++) {
				if (availableFamily[i].equals(fontName)) {
					b = true;
					break;
				}
			}
			m_fontAvailability.put(fontName, new Boolean(b));
		}
		return ((Boolean) m_fontAvailability.get(fontName)).booleanValue();
	}

	/**
	 * Returns <TT>true</TT> if the score must be justified
	 * 
	 * @return getAttributeBoolean({@link ScoreAttribute#JUSTIFY})
	 */
	public boolean isJustified() {
		return getAttributeBoolean(ScoreAttribute.JUSTIFY);
	}
	
	/**
	 * Return <TT>true</TT> if the field is visible and should be
	 * displayed in score.
	 * @param field
	 *            one of {@link ScoreElements} constants
	 */
	public boolean isVisible(byte field) {
		return getFieldInfos(field).m_visible;
	}

	private void notifyListeners() {
		//reset the engraver, his properties may have changed
		if (m_engraver != null) {
			m_engraver.setMode(
					(int) getAttributeNumber(ScoreAttribute.ENGRAVER_MODE),
					(int) getAttributeNumber(ScoreAttribute.ENGRAVER_VARIATION)
				);
		}
		//notify all listeners for change
		Iterator it = getListeners().iterator();
		while (it.hasNext()) {
			((ScoreTemplateChangeListener) it.next()).onTemplateChange();
		}
	}

	public void removeListener(ScoreTemplateChangeListener stcl) {
		m_listeners.remove(stcl);
	}

	/**
	 * Sets the object associated to an attribute
	 * 
	 * @param sa
	 * @param value <TT>null</TT> to remove this attribute
	 */
	public void setAttribute(ScoreAttribute sa, Object value) {
		if (value == null)
			m_attributes.remove(sa.getName());
		else
			m_attributes.put(sa.getName(), value);
		notifyListeners();
	}
	
	/**
	 * Sets the size associated to an attribute, using the
	 * attribute's default unit
	 * 
	 * @param sa
	 * @param size
	 */
	public void setAttributeSize(ScoreAttribute sa, float size) {
		m_attributes.put(sa.getName(), new ScoreAttribute.Size(size,
			((ScoreAttribute.Size) sa.getDefaultValue()).getUnit()));
		notifyListeners();
	}

	/**
	 * Sets the size associated to an attribute
	 * 
	 * @param sa
	 * @param size
	 * @param unit {@link SizeUnit#PX}, {@link SizeUnit#STAFF_HEIGHT}...
	 * @throws RuntimeException if attribute doesn't accept size value
	 */
	public void setAttributeSize(ScoreAttribute sa, float size, SizeUnit unit) {
		if (!(sa.getDefaultValue() instanceof ScoreAttribute.Size))
			throw new RuntimeException(sa.toString()
					+ " is not a size attribute");
		m_attributes.put(sa.getName(), new ScoreAttribute.Size(size, unit));
		notifyListeners();
	}

	/**
	 * Set the default font faces, in order of preference.
	 * 
	 * If font face is not set for a field, it will use the first default font
	 * face available
	 * 
	 * @param faces
	 *            e.g. new String[] { "Dialog", "Arial" }
	 */
	public void setDefaultTextFontFamilyName(String[] faces) {
		if ((faces != null) && (faces.length > 0))
			m_defaultTextFontFamilyNames = faces;
		else
			m_defaultTextFontFamilyNames = new String[] { "Dialog" };
		notifyListeners();
	}

	/**
	 * Sets the default text size in pt.
	 * 
	 * if text sizes are expressed in percent, all sizes will be calculated
	 * from default size.
	 * 
	 * @param f
	 */
	public void setDefaultTextSize(float f) {
		setAttributeSize(ScoreAttribute.TEXT_DEFAULT_SIZE, f, SizeUnit.PT);
		notifyListeners();
	}

	/**
	 * Sets the color of the given element type
	 * 
	 * @param scoreElement One of the {@link ScoreElements} constant
	 * @param color <TT>null</TT> to remove this attribute
	 */
	public void setElementColor(byte scoreElement, Color color) {
		String name = "COLOR_"+Byte.toString(scoreElement);
		if (color == null)
			m_attributes.remove(name);
		else
			m_attributes.put(name, color);
		notifyListeners();
	}

	/**
	 * @param m_graphics
	 *            The m_graphics to set.
	 */
	void setGraphics(Graphics2D graphics) {
		this.m_graphics = graphics;
	}
	
	/**
	 * Sets if the score must be justified.
	 * 
	 * @param b
	 */
	public void setJustified(boolean b) {
		setAttribute(ScoreAttribute.JUSTIFY, new Boolean(b));
	}
	
	void setMetrics(ScoreMetrics sm) {
		m_metrics = sm;
	}
	
	/**
	 * Sets the position of a field, and sets the field visible
	 * 
	 * @param field
	 *            one of {@link ScoreElements} constants
	 * @param vert
	 *            one of {@link VerticalPosition} constants
	 * @param horiz
	 *            one of {@link HorizontalPosition} constants
	 */
	public void setPosition(byte field, byte vert, byte horiz) {
		setPosition(new byte[] {field}, vert, horiz);
	}
	
	/**
	 * Sets the position of several fields
	 * 
	 * @param fields
	 *            array of {@link ScoreElements} constants
	 * @param vert
	 *            one of {@link VerticalPosition} constants
	 * @param horiz
	 *            one of {@link HorizontalPosition} constants
	 */
	public void setPosition(byte[] fields, byte vert, byte horiz) {
		for (int i = 0; i < fields.length; i++) {
			setVisible(fields[i], true);
			Position p = new Position(vert, horiz);
			if (!p.equals(getPosition(fields[i]))) {
				Iterator it = m_fieldsPosition.values().iterator();
				Byte B = new Byte(fields[i]);
				while (it.hasNext()) {
					Vector v = (Vector) it.next();
					if (v.contains(B))
						v.remove(B);
				}
				//don't know why get(p) doesn't work, p.toString() is ok
				if (m_fieldsPosition.get(p) == null)
					m_fieldsPosition.put(p, new Vector());
				Vector v = (Vector) m_fieldsPosition.get(p);
				v.add(B);
			}
		}
		notifyListeners();
	}

	/**
	 * Sets the text attributes of a field
	 * 
	 * @param field
	 *            one of {@link ScoreElements} constants
	 * @param attrib
	 *            Map of attributes
	 * @see java.awt.font.TextAttribute
	 */
	public void setTextAttributes(byte field, Hashtable attrib) {
		getFieldInfos(field).m_textAttributes = attrib;
		notifyListeners();
	}

	/**
	 * Sets the text attributes of several fields
	 * 
	 * @param field
	 *            one of {@link ScoreElements} constants
	 * @param attrib
	 *            Map of attributes
	 * @see java.awt.font.TextAttribute
	 */
	public void setTextAttributes(byte[] fields, Hashtable attrib) {
		for (int i = 0; i < fields.length; i++) {
			getFieldInfos(fields[i]).m_textAttributes = attrib;
		}
		notifyListeners();
	}

	/**
	 * Sets the font family of a field
	 * 
	 * @param field
	 *            one of {@link ScoreElements} constants
	 * @param fontFamilies
	 *            array of font names, e.g. {"Georgia", "Verdana"}
	 */
	public void setTextFontFamilyName(byte field, String[] fontFamilies) {
		if (fontFamilies == null)
			fontFamilies = new String[0];
		getFieldInfos(field).m_fontFamilyNames = fontFamilies;
		notifyListeners();
	}
	
	/**
	 * Sets the font family of several fields
	 * 
	 * @param fields
	 *            one of {@link ScoreElements} constants
	 * @param fontFamilies
	 *            array of font names, e.g. {"Georgia", "Verdana"}
	 */
	public void setTextFontFamilyName(byte[] fields, String[] fontFamilies) {
		if (fontFamilies == null)
			fontFamilies = new String[0];
		for (int i = 0; i < fields.length; i++) {
			getFieldInfos(fields[i]).m_fontFamilyNames = fontFamilies;
		}
		notifyListeners();
	}
	
	/**
	 * Sets the font size of a field
	 * 
	 * @param field
	 *            one of {@link ScoreElements} constants
	 * @param size
	 *            text size in percent of {@link #getDefaultTextSize()} if
	 *            {@link #getTextSizeExpressedIn()} is
	 *            {@link #PERCENT}, else in pt
	 * @param unit
	 *            {@link SizeUnit#PERCENT} or {@link SizeUnit#PT}
	 */
	public void setTextSize(byte field, float size, SizeUnit unit) {
		FieldInfos fi = getFieldInfos(field);
		fi.m_fontSize = size;
		fi.m_fontSizeUnit = unit;
		notifyListeners();
	}

	/**
	 * Sets the font size of several fields
	 * 
	 * @param fields
	 *            array of {@link ScoreElements} constants
	 * @param size
	 *            text size in percent of {@link #getDefaultTextSize()} if
	 *            {@link #getTextSizeExpressedIn()} is
	 *            {@link #PERCENT}, else in pt
	 * @param unit
	 *            {@link SizeUnit#PERCENT} or {@link SizeUnit#PT}
	 */
	public void setTextSize(byte[] fields, float size, SizeUnit unit) {
		for (int i = 0; i < fields.length; i++) {
			FieldInfos fi = getFieldInfos(fields[i]);
			fi.m_fontSize = size;
			fi.m_fontSizeUnit = unit;
		}
		notifyListeners();
	}
	
	/**
	 * Sets the prefix of a field, e.g. "Source:" for source
	 * field.
	 * 
	 * @param field
	 *            one of {@link ScoreElements} constants
	 * @param s
	 *            a string or <code>null</code>
	 */
	public void setTextPrefix(byte field, String s) {
		getFieldInfos(field).m_prefix = s;
		notifyListeners();
	}

	/**
	 * Sets the prefix of a field, e.g. "Source:" for source
	 * field.
	 * 
	 * @param field
	 *            one of {@link ScoreElements} constants
	 * @param s
	 *            a string or <code>null</code>
	 */
	public void setTextSuffix(byte field, String s) {
		getFieldInfos(field).m_suffix = s;
		notifyListeners();
	}

	/**
	 * Sets the font style of a field
	 * 
	 * @param field
	 *            one of {@link ScoreElements} constants
	 * @param style
	 *            style from Font class : {@link Font#PLAIN},
	 *            {@link Font#BOLD}, {@link Font#ITALIC} or
	 *            Font.BOLD+Font.ITALIC
	 */
	public void setTextStyle(byte field, int style) {
		getFieldInfos(field).m_fontStyle = style;
		notifyListeners();
	}
	
	/**
	 * Sets the font style of several fields
	 * 
	 * @param fields
	 *            array of {@link ScoreElements} constants
	 * @param style
	 *            style from Font class : {@link Font#PLAIN},
	 *            {@link Font#BOLD}, {@link Font#ITALIC} or
	 *            Font.BOLD+Font.ITALIC
	 */
	public void setTextStyle(byte[] fields, int style) {
		for (int i = 0; i < fields.length; i++) {
			getFieldInfos(fields[i]).m_fontStyle = style;
		}
		notifyListeners();
	}
	
	/**
	 * Sets the visibility of a field
	 * 
	 * @param field
	 *            one of {@link ScoreElements} constants
	 * @param visible
	 */
	public void setVisible(byte field, boolean visible) {
		getFieldInfos(field).m_visible = visible;
		notifyListeners();
	}

	/**
	 * Sets the visibility of several fields
	 * 
	 * @param fields
	 *            array of {@link ScoreElements} constants
	 * @param visible
	 */
	public void setVisible(byte[] fields, boolean visible) {
		for (int i = 0; i < fields.length; i++) {
			getFieldInfos(fields[i]).m_visible = visible;
		}
		notifyListeners();
	}
	
	public String toString() {
		StringBuffer ret = new StringBuffer();
		ret.append("Score template: "+getClass().getName()+" {\n");
		ret.append("\tdefault text font face: "+Arrays.toString(m_defaultTextFontFamilyNames)+";\n");
		ret.append("\tdefault text size: "+getDefaultTextSize()+"pt;\n");
		ret.append("\n");
		Iterator it = m_attributes.keySet().iterator();
		ret.append("\tattributes {\n");
		while (it.hasNext()) {
			//ScoreAttribute sa = (ScoreAttribute) it.next();
			String attribName = (String) it.next();
			ret.append("\t\t"+ attribName+": "
					+m_attributes.get(attribName).toString()+";\n");
		}
		ret.append("\t}\n");
		ret.append("\n");
		/*Iterator*/ it = m_fieldsPosition.keySet().iterator();
		while (it.hasNext()) {
			Position p = (Position) it.next();
			ret.append("\t"+p.toString()+" {\n");
			Vector v = (Vector) m_fieldsPosition.get(p);
			Iterator it2 = v.iterator();
			while (it2.hasNext()) {
				byte field = ((Byte) it2.next()).byteValue();
				FieldInfos fi = getFieldInfos(field);
				ret.append("\t\t"+fi.toString().replace("\n", "\n\t\t")+"\n");
			}
			ret.append("\t}\n\n");
		}
		ret.append("}\n");
		return ret.toString();
	}
	
}
