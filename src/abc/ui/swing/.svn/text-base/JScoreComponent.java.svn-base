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
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import abc.notation.MusicElement;
import abc.notation.Tune;
import abc.ui.scoretemplates.DefaultScoreTemplate;
import abc.ui.scoretemplates.ScoreAttribute;
import abc.ui.scoretemplates.SizeUnit;

/**
 * This JComponent displays tunes scores. You can get scores such as : <BR/>
 * <CENTER>
 * <IMG src="../../../images/scoreEx.jpg"/> </CENTER>
 * <BR/>
 * To render a tune score, just invoke the <TT>setTune(Tune)</TT> method
 * with your tune as parameter.<BR/>
 * Basically, a score if composed of {@link abc.ui.swing.JScoreElement score elements}
 * @see Tune
 * @see JScoreElement
 */
public class JScoreComponent extends JComponent
	implements ScoreTemplateChangeListener {

	private static final long serialVersionUID = 7903517456075406436L;
	private static final Color SELECTED_ITEM_COLOR = Color.RED;
	/** The graphical representation of the tune currently set.
	 * <TT>null</TT> if no tune is set. */
	private JTune m_jTune = null;

	/** The dimensions of this score. */
	private Dimension m_dimension = null;
	
	/** The place where all spacing dimensions are expressed. */
	private ScoreTemplate m_template = null;
	/** The buffer where the score image is put before rendition in the swing component. */
	private BufferedImage m_bufferedImage = null;
	/** The graphic context of the buffered image used to generate the score. */
	private Graphics2D m_bufferedImageGfx = null;
	/** Set to <TT>true</TT> if the score drawn into the buffered image is
	 * outdated and does not represent the tune currently set. */
	private boolean m_isBufferedImageOutdated = true;

	/** A vector of selected item(s) in this score. <TT>null</TT> if no
	 * item is selected. */
	private Collection m_selectedItems = null;

	//protected int staffLinesSpacing = -1;

	/** Default constructor. */
	public JScoreComponent() {
		m_dimension = new Dimension(1,1);
		setForeground(Color.black);
		setBackground(Color.white);
		initGfx();
	}

	protected void initGfx(){
		m_bufferedImage = new BufferedImage((int)m_dimension.getWidth(), (int)m_dimension.getHeight(), BufferedImage.TYPE_INT_ARGB);
		m_bufferedImageGfx = (Graphics2D)m_bufferedImage.createGraphics();
		//staffLinesSpacing = (int)(m_metrics.getStaffCharBounds().getHeight()*2.5);
	}
	
	public ScoreTemplate getTemplate() {
		if (m_template == null)
			setTemplate(new DefaultScoreTemplate());
		return m_template;
	}
	
	public void setTemplate(ScoreTemplate template) {
		if (m_template != null)
			m_template.removeListener(this);
		m_template = template;
		m_template.setGraphics(getGraphics2D());
		m_template.addListener(this);
		refresh();
	}
	
	private Graphics2D getGraphics2D() {
		if ((m_bufferedImage == null)
				|| (m_bufferedImageGfx == null))
			initGfx();
		return m_bufferedImageGfx;
	}
	
	public ScoreMetrics getScoreMetrics() {
		return getTemplate().getMetrics();
	}

	public Engraver getEngraver() {
		return getTemplate().getEngraver();
	}

	/** Draws the current tune score into the given graphic context.
	 * @param g Graphic context. */
	public void drawIn(Graphics2D g){
		if(m_jTune!=null) {
			m_jTune.render(g);
		}
	}

	public void paint(Graphics g){
		if (m_isBufferedImageOutdated) {
			//System.out.println("buf image is outdated");
			if (m_bufferedImage==null || m_dimension.getWidth()>m_bufferedImage.getWidth()
					|| m_dimension.getHeight()>m_bufferedImage.getHeight()) {
				initGfx();
			}
			m_bufferedImageGfx.setColor(getBackground());
			m_bufferedImageGfx.fillRect(0, 0, (int)m_bufferedImage.getWidth(), (int)m_bufferedImage.getHeight());
			drawIn(m_bufferedImageGfx);
			m_isBufferedImageOutdated=false;
		}
		((Graphics2D)g).drawImage(m_bufferedImage, 0, 0, null);
	}

	/** The size of the font used to display the music score.
	 * @param size The size of the font used to display the music score expressed in ?
	 * @deprecated use {@link #getScoreMetrics() getScoreMetrics()}.{@link ScoreMetrics#setNotationSize(float) setNotationSize(float)} and then {@link #refresh()}
	 * or better, set attribute {@link ScoreAttribute#NOTATION_SIZE} in template
	 */
	public void setSize(float size){
		System.err.println("Warning! deprecated method setSize");
		getTemplate().setAttributeSize(ScoreAttribute.NOTATION_SIZE, size, SizeUnit.PT);
	}

    /**
     * @deprecated this has no more effect, use template
     * {@link ScoreTemplate#setPosition(byte, byte, byte)}.
     * <pre>
     * myJScoreCompo.getTemplate().setPosition(
     * 		{@link abc.ui.scoretemplates.ScoreElements#TEXT_TITLE},
     * 		{@link abc.ui.scoretemplates.VerticalPosition#TOP},
     * 		{@link abc.ui.scoretemplates.HorizontalPosition#CENTER});
     * </pre>
     * @see DefaultScoreTemplate
     */
	public void showTitles(boolean show) {}

    /** Sets the note stem direction on a score.
     *  0=auto 1=up 2=down
     * @deprecated use template attribute {@link ScoreAttribute#NOTE_STEM_POLICY}
     */
    public void setStemmingPolicy (byte policy) {
    	getTemplate().setAttribute(ScoreAttribute.NOTE_STEM_POLICY, new Byte(policy));
    }
    /**
     * @deprecated use getTemplate().getAttributeNumber {@link ScoreAttribute#NOTE_STEM_POLICY}
     */
    public byte getStemmingPolicy() {
    	return (byte) getTemplate().getAttributeNumber(ScoreAttribute.NOTE_STEM_POLICY);
    }

	/**
	 * Refresh the score, blanks the component, compute and draw
	 * the score.<br>Call this method to refresh the component
	 * after changes on its {@link #getEngraver() Engraver} and
	 * {@link #getScoreMetrics() ScoreMetric}.
	 */
	public void refresh() {
		initGfx();
		if (m_jTune!=null) {
			setTune(m_jTune.getTune());
 		}
		repaint();
	}
	
	public Dimension getDimension() {
		if (m_jTune!=null) {
			setTune(m_jTune.getTune());
		}
		return m_dimension;
	}

	/** Writes the currently set tune score to a PNG output stream.
	 * @param os The PNG output stream
	 * @throws IOException Thrown if the given file cannot be accessed. */
	public void writeScoreTo(OutputStream os) throws IOException {
		if (m_jTune!=null) {
			setTune(m_jTune.getTune());
 		}
		BufferedImage bufferedImage = new BufferedImage((int)m_dimension.getWidth(), (int)m_dimension.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D bufferedImageGfx = (Graphics2D)bufferedImage.createGraphics();
		bufferedImageGfx.setColor(getBackground());//Color.WHITE);
		bufferedImageGfx.fillRect(0, 0, (int)bufferedImage.getWidth(), (int)bufferedImage.getHeight());
		drawIn(bufferedImageGfx);
		ImageIO.write(bufferedImage, "png", os);
	}

	/** Writes the currently set tune score to a PNG file.
	 * @param file The PNG output file.
	 * @throws IOException Thrown if the given file cannot be accessed. */
	public void writeScoreTo(File file) throws IOException {
		FileOutputStream fos = new FileOutputStream(file);
		try {
			writeScoreTo(fos);
		} finally {
			fos.close();
		}
	}
	
	/**
	 * Sets tune and template in one shot, this is optimized version,
	 * it avoid 2 calculation (first at setTemplate and second at setTune).
	 * 
	 * @param tune
	 * @param template
	 */
	public void setTuneAndTemplate(Tune tune, ScoreTemplate template) {
		//from setTemplate(template)
		if (m_template != null)
			m_template.removeListener(this);
		m_template = template;
		m_template.setGraphics(getGraphics2D());
		m_template.addListener(this);
		//from refresh()
		initGfx();
		setTune(tune);
	}

	/** Sets the tune to be renderered.
	 * @param tune The tune to be displayed. */
	public void setTune(Tune tune){
		m_jTune = new JTune(tune,
							new Point(0, 0),
							getTemplate());
		m_jTune.setColor(getForeground());

		m_selectedItems = null;
		m_dimension.setSize(m_jTune.getWidth(), m_jTune.getHeight());
		setPreferredSize(m_dimension);
		setSize(m_dimension);
		m_isBufferedImageOutdated=true;
		repaint();
	}

	/** Changes the justification of the rendition score. This will
	 * set the staff lines aligment to justify in order to have a more
	 * elegant display.
	 * @param isJustified <TT>true</TT> if the score rendition should be
	 * justified, <TT>false</TT> otherwise.
	 * @see #isJustified()
	 * @deprecated use template attribute {@link ScoreAttribute#JUSTIFY}
	 */
	public void setJustification(boolean isJustified) {
		setJustified(isJustified);
	}

	/** Changes the justification of the rendition score. This will
	 * set the staff lines aligment to justify in order to have a more
	 * elegant display.
	 * @param isJustified <TT>true</TT> if the score rendition should be
	 * justified, <TT>false</TT> otherwise.
	 * @see #isJustified()
	 * @deprecated use template attribute {@link ScoreAttribute#JUSTIFY}
	 */
	public void setJustified(boolean isJustified) {
		getTemplate().setAttribute(ScoreAttribute.JUSTIFY,
				new Boolean(isJustified));
	}

	/** Return <TT>true</TT> if the rendition staff lines alignment is
	 * justified, <TT>false</TT> otherwise.
	 * @return <TT>true</TT> if the rendition staff lines alignment is
	 * justified, <TT>false</TT> otherwise.
	 * @see #setJustified(boolean)
	 * @deprecated use template attribute {@link ScoreAttribute#JUSTIFY}
	 */
	public boolean isJustified() {
		return getTemplate().getAttributeBoolean(ScoreAttribute.JUSTIFY);
	}

	/** Returns the graphical score element fount at the given location.
	 * @param location A point in the score.
	 * @return The graphical score element found at the specified location.
	 * <TT>null</TT> is returned if no item is found at the given location. */
	public JScoreElement getScoreElementAt(Point location) {
		if (m_jTune!=null)
			return m_jTune.getScoreElementAt(location);
		else
			return null;
	}

	/** Highlights the given score element in the score.
	 * If an item was previously selected, this previous item
	 * is unselected.
	 * @param elmnt The music element to be highlighted in the
	 * score. <TT>null</TT> can be specified to remove
	 * highlighting.
	 * @see #setSelectedItem(JScoreElement) */
	public void setSelectedItem(MusicElement elmnt) {
		JScoreElement r = null;
		if (elmnt!=null)
			//r = (JScoreElementAbstract)m_jTune.getRenditionObjectsMapping().get(elmnt);
			r = m_jTune.getRenditionObjectFor(elmnt);
		//if (r!=null)
		//	System.out.println("Selecting item " + elmnt + "->" + r + "@" + r.getBase());
		setSelectedItem(r);
	}

	/** Highlights the given score element in the score.
	 * If an item was previously selected, this previous item
	 * is unselected.
	 * @param elmnt The score rendition element to be highlighted in the
	 * score. <TT>null</TT> can be specified to remove
	 * highlighting.
	 * @see #setSelectedItem(MusicElement) */
	public void setSelectedItem(JScoreElement elmnt){
		if (m_selectedItems!=null) {
			for (Iterator it = m_selectedItems.iterator(); it.hasNext();) {
				((JScoreElement) it.next()).setColor(null);
			}
			m_selectedItems = null;
		}
		if (elmnt!=null) {
			m_selectedItems = new Vector(1, 0);
			m_selectedItems.add(elmnt);
			elmnt.setColor(SELECTED_ITEM_COLOR);
		}
		repaint();
	}
	
	/** Highlights the given elements in the score.
	 * If item(s) was previously selected, it is unselected.
	 * @param elments A collection of score element to be highlighted in the
	 * score. <TT>null</TT> or empty collection can be specified to remove
	 * highlighting.
	 */
	public void setSelectedItems(Collection elements) {
		if (m_selectedItems!=null) {
			for (Iterator it = m_selectedItems.iterator(); it.hasNext();) {
				((JScoreElement) it.next()).setColor(null);
			}
			m_selectedItems = null;
		}
		if ((elements != null) && (elements.size() > 0)) {
			m_selectedItems = elements;
			for (Iterator it = m_selectedItems.iterator(); it.hasNext();) {
				((JScoreElement) it.next()).setColor(SELECTED_ITEM_COLOR);
			}
		}
	}

	/** Returns the graphical element that corresponds to a tune element.
	 * @param elmnt A tune element.
	 * @return The graphical score element that corresponds to the given
	 * tune element. <TT>null</TT> is returned if the given tune element
	 * does not have any graphical representation. */
	public JScoreElement getRenditionElementFor(MusicElement elmnt) {
		if (m_jTune!=null)
			//return (JScoreElement)m_jTune.getRenditionObjectsMapping().get(elmnt);
			return m_jTune.getRenditionObjectFor(elmnt);
		else
			return null;
	}

	/* (non-Javadoc)
	 * @see abc.ui.swing.ScoreTemplateChangeListener#onTemplateChange()
	 */
	public void onTemplateChange() {
		if (m_jTune != null)
			m_jTune.setOutdated();
		getScoreMetrics().reload();
	}

}
