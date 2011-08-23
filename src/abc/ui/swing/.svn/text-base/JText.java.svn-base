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
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.LineMetrics;
import java.awt.font.TextAttribute;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.text.AttributedString;
import java.text.AttributedCharacterIterator.Attribute;
import java.util.Iterator;
import java.util.Map;

import abc.notation.Accidental;
import abc.notation.Chord;
import abc.notation.MusicElement;
import abc.ui.scoretemplates.HorizontalPosition;
import abc.ui.scoretemplates.ScoreElements;
import abc.ui.scoretemplates.TextJustification;
import abc.ui.scoretemplates.TextVerticalAlign;

/**
 * TODO doc
 */
public class JText extends JScoreElementAbstract {

	private byte m_textField;

	private String m_text = null;
	
	private int nb_lines = 1;
	
	private String[] m_text_lines = null;
	
	private JScoreElementAbstract m_scoreElement = null;
	
	private byte m_verticalAlign = TextVerticalAlign.BASELINE;
	
	private byte m_textJustification = -1;

	/**
	 * Constructor
	 * 
	 * @param mtrx
	 *            The score metrics needed
	 * @param text
	 *            The text
	 * @param textField
	 *            One of {@link ScoreElements} constants
	 */
	protected JText(ScoreMetrics mtrx, String text, byte textField) {
		super(mtrx);
		this.m_textField = textField;
		setText(text);
	}
	
	protected JScoreElementAbstract getAttachedElement() {
		return m_scoreElement;
	}
	
	protected void setAttachedTo(JScoreElementAbstract scoreEl) {
		m_scoreElement = scoreEl;
		onAttachmentChanged();
	}
	
	/**
	 * Override this method if you want to modify some
	 * properties depending on the attached score element
	 */
	protected void onAttachmentChanged() {
		
	}
	
	protected void setText(String text) {
		this.m_text = text.replaceAll("\\\\n", "\n")
						.replaceAll(";", "\n");
		this.m_text_lines = m_text!=null?m_text.split("\n")
										:new String[0];
		this.nb_lines = m_text_lines.length;		
	}
	
	public byte getTextJustification() {
		if (m_textJustification == -1) {
			switch (getHorizontalPosition()) {
			case HorizontalPosition.CENTER:
				m_textJustification = TextJustification.CENTER; break;
			case HorizontalPosition.RIGHT:
				m_textJustification = TextJustification.RIGHT; break;
			default:
				m_textJustification = TextJustification.LEFT;
			}
		}
		return m_textJustification;
	}
	
	public void setTextJustification(byte b) {
		m_textJustification = b;
	}
	
	public byte getTextVerticalAlign() {
		return m_verticalAlign;
	}
	public void setTextVerticalAlign(byte b) {
		m_verticalAlign = b;
	}

	/**
	 * Returns the horizontal alignment
	 * 
	 * @see ScoreTemplate#getPosition(byte)
	 * @return one of {@link abc.ui.scoretemplates.HorizontalPosition} constants
	 */
	public byte getHorizontalPosition() {
		return getTemplate().getPosition(m_textField)[1];
	}

	/**
	 * Returns the vertical alignment
	 * 
	 * @see ScoreTemplate#getPosition(byte)
	 * @return one of {@link abc.ui.scoretemplates.VerticalPosition} constants
	 */
	public byte getVerticalPosition() {
		return getTemplate().getPosition(m_textField)[0];
	}
	
	public Rectangle2D getBoundingBox() {
		Dimension dim = getDimension();
		double x = getBase().getX();
		switch(getTextJustification()) {
		case TextJustification.CENTER: x -= dim.getWidth()/2; break;
		case TextJustification.RIGHT: x -= dim.getWidth(); break;
		}
		double y = getBase().getY();
		double o = getOneLineHeight();
		switch(getTextVerticalAlign()) {
		case TextVerticalAlign.BASELINE: y -= o; break;
		case TextVerticalAlign.BOTTOM: y -= dim.getHeight(); break;
		case TextVerticalAlign.MIDDLE: y -= dim.getHeight()/2; break;
		case TextVerticalAlign.TOP: //unchanged
		}
		return new Rectangle2D.Double(x, y,
				dim.getWidth(),
				dim.getHeight());
	}
	
	public Dimension getDimension() {
		return new Dimension((int) getWidth(), (int) getHeight());
	}
	
	private double getOneLineHeight() {
		return (double) getMetrics().getTextFontHeight(m_textField);
	}

	/**
	 * Returns the height of this score element.
	 * 
	 * @return The height of this score element.
	 */
	public double getHeight() {
		return nb_lines * getOneLineHeight();
	}

	/**
	 * Returns the tune's music element represented by this graphical score
	 * element.
	 * 
	 * @return The tune's music element represented by this graphical score
	 *         element. <TT>null</TT> if this graphical score element is not
	 *         related to any music element.
	 * @see MusicElement
	 */
	public MusicElement getMusicElement() {
		return null;
	}

	public String getText() {
		String p = getTemplate().getTextPrefix(m_textField);
		String s = getTemplate().getTextSuffix(m_textField);
		p = p!=null?p:"";
		s = s!=null?s:"";
		return p+m_text+s;
	}
	
	protected String[] getTextLines() {
		String p = getTemplate().getTextPrefix(m_textField);
		String s = getTemplate().getTextSuffix(m_textField);
		p = p!=null?p:"";
		s = s!=null?s:"";
		String[] ret = new String[nb_lines];
		for (int i = 0; i < nb_lines; i++) {
			ret[i] = p+m_text_lines[i]+s;
		}
		return ret;
	}

	/**
	 * Returns the width of this score element.
	 * 
	 * @return The width of this score element.
	 */
	public double getWidth() {
		String[] lines = getTextLines();
		double max = 0;
		for (int i = 0; i < lines.length; i++) {
			max = Math.max(max,
					(double) getMetrics().getTextFontWidth(m_textField, lines[i])
				);
		}
		return max;
	}

	/** Callback invoked when the base has changed for this object. */
	protected void onBaseChanged() {
		// does nothing
	}

	/**
	 * Renders this Score element to the given graphic context.
	 * 
	 * @param g2
	 */
	public double render(Graphics2D g2) {
		Font previousFont = g2.getFont();
		Color previousColor = g2.getColor();

		Font font = getTemplate().getTextFont(m_textField);
		g2.setFont(font);
		setColor(g2, m_textField);
		LineMetrics lineMetrics = font.getLineMetrics(getText(), g2.getFontRenderContext());
		float leading = (float) Math.ceil(Math.max(lineMetrics.getLeading(), lineMetrics.getDescent()));
		//System.out.println("leading "+getText()+" = "+leading);

		//music font for sharp and flats
		Font musicFont;
		try {
			musicFont = getMusicalFont().getFont();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		musicFont = musicFont.deriveFont(font.getSize()*3f);
		//sharp and natural are translated up a little :)
		AffineTransform at = AffineTransform.getTranslateInstance(0, -font.getSize()/2.5);
		Font musicFontSharp = musicFont.deriveFont(at);
		at = AffineTransform.getTranslateInstance(0, -font.getSize()/3);
		Font musicFontNatural = musicFont.deriveFont(at);
		
		char sharp = getMusicalFont().getAccidental(Accidental.SHARP);
		char flat = getMusicalFont().getAccidental(Accidental.FLAT);
		char natural = getMusicalFont().getAccidental(Accidental.NATURAL);
		char double_flat = getMusicalFont().getAccidental(Accidental.DOUBLE_FLAT);
		char double_sharp = getMusicalFont().getAccidental(Accidental.DOUBLE_SHARP);
		
		String[] lines = getTextLines();
		double line_height = getOneLineHeight();
		for (int line_count = 0; line_count < lines.length; line_count++) {
			String text = lines[line_count].replace(Chord.UNICODE_SHARP, sharp)
				.replace(Chord.UNICODE_FLAT, flat)
				.replace(Chord.UNICODE_NATURAL, natural)
				.replaceAll("\\(b\\)", flat+"")
				.replaceAll("\\(bb\\)", double_flat+"")
				.replaceAll("\\(#\\)", sharp+"")
				.replaceAll("\\(##\\)", double_sharp+"")
				.replaceAll("\\(\\=\\)", natural+"");
			
			AttributedString as = new AttributedString(text);
			as.addAttributes(font.getAttributes(), 0, text.length());
			int begin = -1;
			for (int i = 0; i < text.length(); i++) {
				char charAtI = text.charAt(i);
				if (begin == -1)
					begin = i;
				if ((charAtI == sharp) || (charAtI == flat) || (charAtI == natural)) {
					if (begin != i) {
						as.addAttribute(TextAttribute.FONT, font, begin, i);
					}
					as.addAttribute(TextAttribute.FONT,
							charAtI==flat?musicFont
								:(charAtI==sharp?musicFontSharp
												:musicFontNatural),
							i, i+1);
					begin = -1;
				}
			}
			if (begin != -1) {
				as.addAttributes(font.getAttributes(), begin, text.length());
			}
			//end of flat/sharp replacement

			Map attrib = getTemplate().getTextAttributes(m_textField);
			if (attrib != null) {
				Iterator it = attrib.keySet().iterator();
				while (it.hasNext()) {
					Attribute ta = (Attribute) it.next();
					as.addAttribute(ta, attrib.get(ta));
				}
			}
			if (getTextJustification() == TextJustification.JUSTIFIED) {
				as.addAttribute(TextAttribute.JUSTIFICATION, TextAttribute.JUSTIFICATION_FULL);
			}
			
			double x = getBase().getX();
			double y = getBase().getY();
			Rectangle2D box = getBoundingBox();
			
			switch(getTextJustification()) {
			case TextJustification.CENTER:
				x -= box.getWidth()/2; break;
			case TextJustification.RIGHT:
				x -= box.getWidth(); break;
			}
			y = box.getMinY() + line_height;
			
			g2.drawString(as.getIterator(),
					(float) x,
					(float) (y-leading + line_count*line_height));
		}
		
		g2.setFont(previousFont);
		g2.setColor(previousColor);
		
		//renderDebugBoundingBox(g2);
		return getWidth();
	}

}
