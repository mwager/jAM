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
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import abc.notation.RepeatBarLine;
import abc.ui.scoretemplates.ScoreElements;

/** This class is in charge of rendering a repeat bar. */
class JRepeatBar extends JBar{

	//TODO move this to ScoreMetrics
	public static final char[][] DIGITS = {
			{'\uF0C1', '\uF02E'}, 
			{'\uF0AA', '\uF02E'},
			{'\uF0A3', '\uF02E'},
			{'\uF0A2', '\uF02E'},
			{'\uF0B0', '\uF02E'},
			{'\uF0A4', '\uF02E'},
			{'\uF0A6', '\uF02E'},
			{'\uF0A5', '\uF02E'},
			{'\uF0BB', '\uF02E'}};

	public JRepeatBar(RepeatBarLine barLine, Point2D base, ScoreMetrics c) {
		super(barLine,base, c); 
	}
	
	public double render(Graphics2D context){
		Color previousColor = context.getColor();
		setColor(context, ScoreElements.BAR_LINES);
		ScoreMetrics metrics = getMetrics();
		double x = super.getWidth();// metrics.getNoteWidth();
		double staffHeight = metrics.getStaffCharBounds().getHeight();
		double staffWidth = metrics.getStaffCharBounds().getWidth();
		String chars = "";
		byte[] numbers = ((RepeatBarLine)m_barLine).getRepeatNumbers();
		for (int i = 0; i < numbers.length; i++) {
			chars += String.copyValueOf(DIGITS[numbers[i]-1]);
		}
		char[] ch = chars.toCharArray();
		double topY = getStaffLine().getTopY();
		Point2D base = getBase();
		//ensure that there is enough space!
		topY = Math.min(topY, base.getY()-staffHeight*1.7);
		double digitHeight = metrics.getBounds(ch).getHeight();
		context.drawChars(ch, 0, ch.length, 
				(int)(base.getX()+x+1),
				(int)(topY+digitHeight*1.5));
		//vertical line
		context.drawLine(
				(int)(base.getX()+x-1), 
				(int)(base.getY()-staffHeight*1.1), 
				(int)(base.getX()+x-1),
				(int)topY);
		//Horizontal line
		context.drawLine(
				(int)(base.getX()+x-1),
				(int)topY,
				(int)(base.getX()+x-1+staffWidth),
				(int)topY);
		context.setColor(previousColor);
		
		//renderDebugBoundingBox(context);
		return super.render(context);
	}
}
