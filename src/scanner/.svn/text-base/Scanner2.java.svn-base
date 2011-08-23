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
package scanner;

import java.io.BufferedReader;

/** A Scanner optimsed for buffered streams. (Experimental purpose for now,
 * not used in abc4j).*/
class Scanner2 extends Scanner
{
	//private String m_previousLine = null;
	private String m_theLine = null;
	private int m_theLineIndex = -1;
    private boolean endOfStreamReached = false;

    /** Creates a new scanner to scan the specified stream
     * @param stream The stream to be scanned. */
    public Scanner2(BufferedReader stream) {
       super(stream);
    }
    
    public Scanner2() {
        super();
     }

    /** Returns the next token encountered.
     * @return The next token encountered.
     * @exception Thrown if there's no next valid token. */
    public Token nextToken() throws NoSuchTokenException
    {
      String token = null;
      //boolean endOfStreamReached = false;
      //char[] currentChar = new char[1];
      while (token==null && !endOfStreamReached)
      {
        try
        {
        	m_currentChar[0]=nextChar();
        	if (m_currentChar[0]=='§')
        		endOfStreamReached = true;
          else
          {
            m_previousPosition.setPosition(m_position);
            m_position.setColumn(m_position.getColumn()+1);
            m_position.setCharactersOffset(m_position.getCharactersOffset()+1);
            //if (currentChar[0]=='\n')

            if (FSA.getTransitionFor(m_currentChar[0])!=null)
            {
              FSA.sendChar(m_currentChar[0]);
              m_currentLine.append(m_currentChar);
              if (m_currentChar[0]=='\n')
              {
                notifyListenersForLineScanned(new String(m_currentLine));
                m_currentLine = new StringBuffer();
              }
            }
            else
            {
              if (FSA.getCurrentState().isTokenState())
              {
                //==================a valid token has been found.
                //try
                //{
                  //the last character will be reinjected => we go back from one character.
                  previousChar();
                  m_position.setPosition(m_previousPosition);
                //}
                //catch (IOException ex)
                //{ex.printStackTrace();}
                token = FSA.getReceivedCharacters();
              }
              else
              {
                //=================an invalid character has been found.
                m_currentLine.append(m_currentChar);
                notifyListenersForInvalidCharacter(m_currentChar[0], (CharStreamPosition)m_position.clone());
                FSA.initialize();
                //===== line changed in case on invalid character
                if (m_currentChar[0]=='\n')
                {
                  notifyListenersForLineScanned(new String(m_currentLine));
                  m_currentLine = new StringBuffer();
                  m_position.setColumn(0);
                  m_position.setLine(m_position.getLine()+1);
                }
                //===== end of line changed in case on invalid character
              }
            }
          }
        }
        catch (Exception e)
        { e.printStackTrace(); }
      }
      if (endOfStreamReached)
      {
        if (FSA.getCurrentState().isTokenState())
          token = FSA.getReceivedCharacters();
        else
        {
          FSA.initialize();
          throw new NoSuchTokenException();
        }
      }
      TokenType currentState = FSA.getCurrentState().getType();
      FSA.initialize();
      Token tok = new Token(token,
                            currentState,
                            new CharStreamPosition(m_position.getColumn()-(token.length()-1)
                                                   , m_position.getLine()
                                                   , m_position.getCharactersOffset()-(token.length()-1)));
      notifyListenersForToken(tok);
      if (token.indexOf('\n')!=-1)
      {
        m_position.setColumn(0);
        m_position.setLine(m_position.getLine()+1);
      }
      return tok;
    }
    
    public char nextChar() {
    	//System.out.println("nextChar");
    	char c = '§';
    	try {
    		if (m_theLine==null)
    			m_theLine = ((BufferedReader)m_charStream).readLine();

    		if (m_theLineIndex==-2) {
    			m_theLineIndex++;
    			c = '\n';
    		}
    		else {
    			if (m_theLineIndex==m_theLine.length()-1) {
    				//m_previousLine = m_theLine;
    				m_theLine = ((BufferedReader)m_charStream).readLine();
    				if (m_theLine!=null) {
    					m_theLineIndex = -1;
    					c = '\n';
    				}
    			}
    			else {
    				if (m_theLine!=null) {
    					m_theLineIndex++;
    					c = m_theLine.charAt(m_theLineIndex);
    				}
    			}
    		}
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    	if (m_theLine==null && c=='§') {
    		//System.out.println("THE M_LINE IS NULL and c = " + c);
    		endOfStreamReached = true;
    	}
    	return c;
    }
    
    public void previousChar() {
    	//System.out.println("previousChar");
    	if (m_theLine==null || m_theLineIndex==-2)
    		throw new IllegalStateException("No previous character : nothing has been started !");
    	else
    		m_theLineIndex--;
    }

    /** Returns <TT>true</TT> if there's any character left.
     * @return <TT>true</TT> if there's any character left, <TT>false</TT>
     * otherwise. */
    public boolean hasNext() {
    	return !endOfStreamReached;
    }

}
