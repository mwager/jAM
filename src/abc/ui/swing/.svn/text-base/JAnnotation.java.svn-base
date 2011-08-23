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

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import abc.notation.BarLine;
import abc.ui.scoretemplates.HorizontalPosition;
import abc.ui.scoretemplates.ScoreElements;
import abc.ui.scoretemplates.TextJustification;
import abc.ui.scoretemplates.TextVerticalAlign;
import abc.ui.scoretemplates.VerticalPosition;

/** TODO doc */
public class JAnnotation extends JText {

	private byte hPosition = HorizontalPosition.CENTER;
	private byte vPosition = VerticalPosition.TOP;
	private byte tJustify = TextJustification.CENTER;
	private byte tVAlign = TextVerticalAlign.BASELINE;
	
	/** Constructor
	 * @param mtrx The score metrics needed
	 */
	protected JAnnotation(ScoreMetrics mtrx, String text) {
		super(mtrx, text, ScoreElements.TEXT_ANNOTATIONS);
		setTextPosition();
	}
	
	private void setTextPosition() {
		String s = getText();
		String newText = s.substring(1);
		if (newText.length() == 0)
			newText = s;
		char c = s.charAt(0);
		switch(c) {
		case '<':
			vPosition = VerticalPosition.MIDDLE;
			hPosition = HorizontalPosition.LEFT;
			tJustify = TextJustification.RIGHT;
			tVAlign = TextVerticalAlign.MIDDLE;
			setText(newText);
			break;
		case '>':
			vPosition = VerticalPosition.MIDDLE;
			hPosition = HorizontalPosition.RIGHT;
			tJustify = TextJustification.LEFT;
			tVAlign = TextVerticalAlign.MIDDLE;
			setText(newText);
			break;
		case '_':
			vPosition = VerticalPosition.UNDER_STAFF;
			hPosition = HorizontalPosition.CENTER;
			tJustify = TextJustification.CENTER;
			tVAlign = TextVerticalAlign.TOP;
			setText(newText);
			break;
		case '^':
		case '@':
			setText(newText);
		default:
			vPosition = VerticalPosition.ABOVE_STAFF;
			hPosition = HorizontalPosition.CENTER;
			tJustify = TextJustification.CENTER;
			tVAlign = TextVerticalAlign.BOTTOM;
			break;
		}
	}
	
	public byte getTextJustification() {
		return tJustify;
	}
	
	public byte getTextVerticalAlign() {
		return tVAlign;
	}

	public byte getHorizontalPosition() {
		return hPosition;
	}

	public byte getVerticalPosition() {
		return vPosition;
	}
	
	protected void onAttachmentChanged() {
		JScoreElementAbstract element = getAttachedElement();
		if (element == null)
			return;
		
		if (element instanceof JBar) {
			BarLine bar = (BarLine)element.getMusicElement();
			if ((vPosition == VerticalPosition.ABOVE_STAFF)
					|| (vPosition == VerticalPosition.BOTTOM)
					|| (vPosition == VerticalPosition.TOP)
					|| (vPosition == VerticalPosition.UNDER_STAFF)) {
				switch(bar.getType()) {
				case BarLine.BEGIN:
				case BarLine.REPEAT_OPEN:
					hPosition = HorizontalPosition.LEFT;
					tJustify = TextJustification.LEFT;
					break;
				case BarLine.DOUBLE:
				case BarLine.END:
				case BarLine.REPEAT_CLOSE:
					hPosition = HorizontalPosition.RIGHT;
					tJustify = TextJustification.RIGHT;
					break;
				}
			}
		}
		
		//
		//Sets the base position according to the attached
		//element and text position/justification
		//
		
		double x = -1;//getBase().getX();
		double y = -1;//getBase().getY();
		Rectangle2D box = element.getBoundingBox();
		box = box.createUnion(
				new Rectangle2D.Double(box.getMinX(), element.getStaffLine().get5thLineY(),
								box.getWidth(), element.getStaffLine().getHeight()));
		//we have a box width=width of element, height=staff height
		switch(getVerticalPosition()) {
		case VerticalPosition.UNDER_STAFF:
		case VerticalPosition.BOTTOM:
			y = box.getMaxY();
			break;
		case VerticalPosition.MIDDLE:
			y = box.getCenterY();
			if (element instanceof JNoteElementAbstract)
				y = ((JNoteElementAbstract) element)
						.getNotePosition().getY()
					- getMetrics().getNoteHeight()*.75;
			break;
		case VerticalPosition.ABOVE_STAFF:
		case VerticalPosition.TOP:
		default:
			y = box.getMinY();
			break;
		}
		switch(getHorizontalPosition()) {
		case HorizontalPosition.LEFT:
			x = box.getMinX();
			if (tJustify == TextJustification.RIGHT) {
				x = box.getMinX() //- getWidth()
					- getMetrics().getNoteWidth()/2;
			}
			break;
		case HorizontalPosition.RIGHT:
			x = box.getMaxX();
			if (tJustify == TextJustification.LEFT) {
				x = box.getMaxX()
					+ getMetrics().getNoteWidth()/2;
			}
			break;
		case HorizontalPosition.CENTER:
		default:
			x = box.getCenterX();
		}
		//getBase().setLocation(x, y);
		setBase(new Point2D.Double(x, y));
	}

}
