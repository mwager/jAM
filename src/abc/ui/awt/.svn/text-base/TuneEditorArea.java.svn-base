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
package abc.ui.awt;

import java.awt.*;
import java.awt.event.*;
import abc.notation.*;
import abc.parser.*;

/** A pane for displaying and editing tunes. This pane handles copy/paste
 * actions. */
public class TuneEditorArea extends TextArea
{
  private static final long serialVersionUID = -687399329203942962L;
  private static final int IDLE_TIME_BEFORE_REFRESH = 200;
  private int m_idleTimeBeforeRefresh = IDLE_TIME_BEFORE_REFRESH;
  /** The thread in charge of refreshing the tune representation of this editor pane. */
  private ParsingRefresh m_refresher = null;
  /** The tune currently represented in this editor pane. */
  private Tune m_tune = null;

  //private int m_idleTime = 0;
  //private boolean m_notationChanged = false;

  public TuneEditorArea()
  { this(new TuneParser()); }

  public TuneEditorArea(TuneParser parser)
  {
    setFont(new Font("Courier", Font.PLAIN, 12));
    m_refresher = new ParsingRefresh(this, parser);
  }

  public boolean getScrollableTracksViewportWidth()
  { return false;  }

  public TuneParser getParser()
  { return m_refresher.getParser(); }

  public Tune getTune()
  { return m_tune; }

  private class ParsingRefresh extends Thread implements TextListener
  {
    private TextArea m_document = null;
    private TuneParser m_parser = null;
    private int m_idleTime = 0;
    private Object m_mutex = new Object();

    public ParsingRefresh(TextArea document, TuneParser parser)
    {
      super("ABC-TuneEditorAreaRefresh");
      m_parser = parser;
      m_document = document;
      m_document.addTextListener(this);
      start();
    }

    public TuneParser getParser()
    { return m_parser; }

    public void run()
    {
      while (true)
      {
        try
        {
          synchronized(m_mutex)
          {
            //System.out.println("Area - wait()");
            m_mutex.wait();
            do
            {
              //System.out.println("Area - compting before parsing : " + m_idleTime);
              m_mutex.wait(10);
              m_idleTime+=10;
            }
            while (m_idleTime<=IDLE_TIME_BEFORE_REFRESH);
            try
            {

            	String tuneNotation = TuneEditorArea.this.getText();
            	if (!tuneNotation.equals(""))
            	{
            		//System.out.println("Area - parsing("+ tuneNotation +")");
              		m_tune = m_parser.parse(tuneNotation);
            	}
            }
            catch (Exception e)
            { e.printStackTrace(); }
           }
        }
        catch (InterruptedException e)
        { e.printStackTrace(); }
      }
    }

    public void textValueChanged(TextEvent e)
    {
      //m_notationChanged = true;
      synchronized(m_mutex)
      {
        m_idleTime=0;
        m_mutex.notify();
      }
    }
  }
}
