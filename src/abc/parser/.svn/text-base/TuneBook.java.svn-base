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


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.Vector;

import scanner.InvalidCharacterEvent;
import scanner.ScannerListenerInterface;
import scanner.TokenEvent;
import abc.notation.NoSuchTuneException;
import abc.notation.Tune;

/** This class provides an object representation of a tunebook. It enables you
 * to store tunes ordered by reference number. */
public class TuneBook {

  /** The file parser used to initialize the tunebook. */
  private AbcHeadersParser m_fileParser = null;
  /** The tune parser used to parse tunes notation. */
  private TuneParser m_parser = null;
  /** The structure used to store the tunes.
   * Key = Integer(ReferenceNumber)
   * Value = TranscribedTune instance */ 
  private TreeMap m_tunes = null;
  /** Listeners of this tunebook. */
  private Vector m_listeners = null;
  /** A structure that stores the same instances  as the ones in 
   * m_tunes but that follows the same ordering of tunes
   * as the ones in the ABC file. */
  private Vector m_originalTunesOrder = null;
  private File m_file = null;

	/**
	 * Creates a new tune book from the specified file.
	 * 
	 * @param abcFile
	 *            The file that contains tunes in abc notation.
	 * @exception FileNotFoundException
	 *                Thrown if the specified file doesn't exist.
	 * @see #TuneBook(File, AbcVersion)
	 */
	public TuneBook(File abcFile) throws FileNotFoundException {
		this(abcFile, AbcVersion.v1_6);
	}

	/**
	 * Creates a new tune book from the specified file.
	 * 
	 * @param abcFile
	 *            The file that contains tunes in abc notation.
	 * @param abcVersion
	 *            Specify which ABC version to use for parsing
	 *            {@link AbcVersion#v1_6} or {@link AbcVersion#v2_0}
	 * @exception FileNotFoundException
	 *                Thrown if the specified file doesn't exist.
	 */
	public TuneBook(File abcFile, AbcVersion abcVersion)
			throws FileNotFoundException {
		this(abcVersion);
		m_file = abcFile;
		buildTunesTreeMap(new BufferedReader(new InputStreamReader(
				new FileInputStream(abcFile))), null);
	}

	/**
	 * Creates a new tune book from the specified file and gets feedback from
	 * the parsing phasis via the specified listener.
	 * 
	 * @param abcFile
	 *            The file that contains tunes in abc notation.
	 * @param listener
	 *            Listener to be informed of the parsing phasis.
	 * @throws FileNotFoundException
	 *             Thrown if the specified file doesn't exist.
	 * @see #TuneBook(File, AbcFileParserListenerInterface, AbcVersion)
	 */
	public TuneBook(File abcFile, AbcFileParserListenerInterface listener)
			throws FileNotFoundException {
		this(abcFile, listener, AbcVersion.v1_6);
	}

	/**
	 * Creates a new tune book from the specified file and gets feedback from
	 * the parsing phasis via the specified listener.
	 * 
	 * @param abcFile
	 *            The file that contains tunes in abc notation.
	 * @param listener
	 *            Listener to be informed of the parsing phasis.
	 * @param abcVersion
	 *            Specify which ABC version to use for parsing
	 *            {@link AbcVersion#v1_6} or {@link AbcVersion#v2_0}
	 * @throws FileNotFoundException
	 *             Thrown if the specified file doesn't exist.
	 */
	public TuneBook(File abcFile, AbcFileParserListenerInterface listener,
			AbcVersion abcVersion) throws FileNotFoundException {
		this(abcVersion);
		m_file = abcFile;
		buildTunesTreeMap(new BufferedReader(new InputStreamReader(
				new FileInputStream(abcFile))), listener);
	}

	/**
	 * Creates a new tune book from the specified stream.
	 * 
	 * @param stream
	 *            The stream in abc notation.
	 * @exception IOException
	 *                If the stream does not support {@link Reader#mark(int)},
	 *                or if some other I/O error occurs
	 * @see #TuneBook(Reader, AbcVersion)
	 */
	public TuneBook(Reader stream) throws IOException {
		this(stream, AbcVersion.v1_6);
	}

	/**
	 * Creates a new tune book from the specified stream.
	 * 
	 * @param stream
	 *            The stream in abc notation.
	 * @exception IOException
	 *                If the stream does not support {@link Reader#mark(int)},
	 *                or if some other I/O error occurs
	 * @param abcVersion
	 *            Specify which ABC version to use for parsing
	 *            {@link AbcVersion#v1_6} or {@link AbcVersion#v2_0}
	 */
	public TuneBook(Reader stream, AbcVersion abcVersion) throws IOException {
		this(abcVersion);
		buildTunesTreeMap(stream, null);
	}

  /**
	 * Creates a new tune book from the specified stream and gets feedback from
	 * the parsing phasis via the specified listener.
	 * 
	 * @param stream
	 *            The stream in abc notation.
	 * @param listener
	 *            Listener to be informed of the parsing phasis.
	 * @exception IOException
	 *                If the stream does not support {@link Reader#mark(int)},
	 *                or if some other I/O error occurs *
	 * @see #TuneBook(Reader, AbcFileParserListenerInterface, AbcVersion)
	 */
	public TuneBook(Reader stream, AbcFileParserListenerInterface listener)
			throws IOException {
		this(stream, listener, AbcVersion.v1_6);
	}

	/**
	 * Creates a new tune book from the specified stream and gets feedback from
	 * the parsing phasis via the specified listener.
	 * 
	 * @param stream
	 *            The stream in abc notation.
	 * @param listener
	 *            Listener to be informed of the parsing phasis.
	 * @exception IOException
	 *                If the stream does not support {@link Reader#mark(int)},
	 *                or if some other I/O error occurs *
	 * @param abcVersion
	 *            Specify which ABC version to use for parsing
	 *            {@link AbcVersion#v1_6} or {@link AbcVersion#v2_0}
	 */
	public TuneBook(Reader stream, AbcFileParserListenerInterface listener,
			AbcVersion abcVersion) throws IOException {
		this(abcVersion);
		buildTunesTreeMap(stream, listener);
	}

	/**
	 * Creates an empty tunebook, using v1.6 parser.
	 *
	 * @see #TuneBook(AbcVersion)
	 */
	public TuneBook() {
		this(AbcVersion.v1_6);
	}
  
	/**
	 * Creates an empty tunebook, using specified abc version
	 * 
	 * @param abcVersion
	 *            Specify which ABC version to use for parsing
	 *            {@link AbcVersion#v1_6} or {@link AbcVersion#v2_0}
	 */
	public TuneBook(AbcVersion abcVersion) {
		m_fileParser = new AbcHeadersParser(abcVersion);
		m_parser = new TuneParser(abcVersion);
		m_tunes = new TreeMap();
		m_originalTunesOrder = new Vector();
		m_listeners = new Vector();
	}

  /** Saves this tunebook to the specified file.
   * @param file The file where all tunes notation should be stored.
   * @exception IOException Thrown if the specified file doesn't exist. */
  public void saveTo(File file) throws IOException
  {
    m_file = file;
    save();
  }

  /** Returns the file associated to this TuneBook if any.
   * @return Returns the file associated to this TuneBook if any.
   * <TT>null</TT> is returned if this TuneBook has been created from 
   * scratch for instance. */
  public File getFile()
  { return m_file; }

  public void save() throws IOException
  {
    FileWriter writer = new FileWriter(m_file);
    for (int i=0; i<m_originalTunesOrder.size(); i++)
    {
      TranscribedTune tune = (TranscribedTune)m_originalTunesOrder.elementAt(i);
      if (tune.header!=null) writer.write(tune.header);
      writer.write(tune.notation);
      if (
          tune.notation.charAt(tune.notation.length()-1)!='\n'
          || tune.notation.charAt(tune.notation.length()-2)!='\n'
          )
        writer.write("\n");
    }
    writer.flush();
    System.out.println("Saving to " + m_file.toString());
  }

  /** Puts the specified tune notation in this tunebook.
   * If a tune with the same reference number was already existing, it updates
   * it. If it's a new tune, it adds it.
   * @param tuneNotation A string that describes a tune using ABC notation.
   * @return The Tune representation of the tuneNotation parameter. */
  public Tune putTune(String tuneNotation)
  {
    Tune parsedTune = m_parser.parseHeader(tuneNotation);
    Integer key = new Integer(parsedTune.getReferenceNumber());
    TranscribedTune tune = (TranscribedTune)m_tunes.get(key);
    if (tune!=null)
    {
      tune.notation = tuneNotation;
      tune.tune = parsedTune;
      tune.m_onlyHeader = true;
      notifyListenersForTuneChange(new TuneChangeEvent(this, TuneChangeEvent.TUNE_UPDATED, tune.tune, tuneNotation));
    }
    else
    {
      tune = new TranscribedTune();
      tune.notation = tuneNotation;
      tune.tune = parsedTune;
      tune.m_onlyHeader = true;
      tune.header = "";
      m_tunes.put(key, tune);
      m_originalTunesOrder.addElement(tune);
      notifyListenersForTuneChange(new TuneChangeEvent(this, TuneChangeEvent.TUNE_ADDED, tune.tune, tuneNotation));
    }
    return parsedTune;
  }

  /** Removes the tune with specified reference number.
   * @param referenceNumber The reference number of the tune that has to be
   * removed.
   * @return The tune that has been removed, <TT>null</TT> if no tune with the
   * corresponding reference number has been found. */
  public Tune removeTune(int referenceNumber)
  {
    if (m_tunes.remove(new Integer(referenceNumber))!=null)
    {
      TranscribedTune tune= null;
      for (int i=0; i<m_originalTunesOrder.size(); i++)
      {
        tune = (TranscribedTune)m_originalTunesOrder.elementAt(i);
        if (tune.tune.getReferenceNumber()==referenceNumber)
        {
          m_originalTunesOrder.removeElementAt(i);
          notifyListenersForTuneChange(new TuneChangeEvent(this, TuneChangeEvent.TUNE_REMOVED, tune.tune, tune.notation));
          return tune.tune;
        }
      }
    }
    return null;
  }

  /** Returns the tune with the specified reference number
   * @param referenceNumber The reference number of the tune that should be retrieved.
   * @return The tune corresponding to the specified reference number, <TT>null</TT>
   * if no tune found. */
  public Tune getTune(int referenceNumber)
  {
    Integer key = new Integer(referenceNumber);
    TranscribedTune tune = (TranscribedTune)m_tunes.get(key);
    if (tune!=null)
    {
      if (tune.m_onlyHeader==true)
      {
        tune.tune = m_parser.parse(tune.notation);
        tune.m_onlyHeader = false;
      }
      return tune.tune;
    }
    return null;
  }

  /** Returns the text put just before a tune in an ABC file.
   * For instance, with a file such as : 
   * <PRE>
   * %text 1 before the first tune
   * X:5
   * T:xxxx
   * K:D
   * ...
   * </PRE>
   * the invokation <PRE>getTuneHeader(5)</PRE> would return the string
   * <PRE>"%text 1 before the first tune\n"</PRE>
   * @return The text put just before a tune in an ABC file.
   */
  public String getTuneHeader(int referenceNumber)
  {
    Integer key = new Integer(referenceNumber);
    TranscribedTune tune = (TranscribedTune)m_tunes.get(key);
    if (tune!=null)
    {
      return tune.header;
    }
    return null;
  }

  /** Returns the notation of the tune corresponding to the specified
   * reference number.
   * @param referenceNumber A reference number.
   * @return A tune notation in ABC format. <TT>null</TT> if no tune has been
   * found.
   * @exception NoSuchTuneException Thrown if the specified reference number
   * doesn't exist in this tunebook. */
  public String getTuneNotation(int referenceNumber) throws NoSuchTuneException
  {
    Integer key = new Integer(referenceNumber);
    TranscribedTune tune = (TranscribedTune)m_tunes.get(key);
    if (tune!=null)
      return tune.notation;
    else
      return null;
  }

  /** Returns tunes header information of tunes contained in this tunebook.
   * @return An array containing <TT>Tune</TT> objects representing header
   * information of tunes contained in this tunebook. */
  public Tune[] getTunesHeaders()
  {
    Iterator it = m_tunes.keySet().iterator();
    Tune[] tunes = new Tune[m_tunes.size()];
    int index=0;
    while (it.hasNext())
    {
      tunes[index] = ((TranscribedTune)m_tunes.get(it.next())).tune;
      index++;
    }
    return tunes;
  }

  /** Returns the reference numbers of tunes contained in this tunebook.
   * @return An array containing the reference numbers of tunes contained in
   * this tunebook, ordered in the way they were added in this tunebook. */
  public int[] getReferenceNumbers()
  {
    Iterator it = m_tunes.keySet().iterator();
    int[] refNb = new int[m_tunes.size()];
    int index=0;
    while (it.hasNext())
    {
      refNb[index] = ((Integer)it.next()).intValue();
      index++;
    }
    return refNb;
  }

  /** Returns the highest reference number from all the reference
   * numbers this tunebook contains.
   * @return The highest reference number from all the reference
   * numbers this tunebook contains. -1 is returned if no tune is 
   * stored in this tunebook */
  public int getHighestReferenceNumber()
  {
    int[] refNumbers = getReferenceNumbers();
    int hi = -1;
    for (int i=0; i<refNumbers.length; i++)
      if (refNumbers[i]>hi)
        hi = refNumbers[i];
    return hi;
  }

  /** Returns the number of tunes contained in this tunebook.
   * @return The number of tunes contained in this tunebook. */
  public int size()
  { return m_tunes.size(); }

  public Vector toVector()
  {
    java.util.Set keys = m_tunes.keySet();
    Vector v = new Vector();
    Iterator keysIterator = keys.iterator();
    while (keysIterator.hasNext())
      v.addElement(((TranscribedTune)m_tunes.get(keysIterator.next())).tune);
    return v;
  }

  /** Adds a listener to this tunebook to be aware of tunes changes.
   * @param l The listener to be added. */
  public void addListener(TuneBookListenerInterface l)
  { m_listeners.addElement(l); }

  /** Removes a listener from this tunebook.
   * @param l The listener to be removed. */
  public void removeListener(TuneBookListenerInterface l)
  { m_listeners.removeElement(l); }

  protected void notifyListenersForTuneChange(TuneChangeEvent e)
  {
    for (int i=0; i<m_listeners.size() ;i++)
    ((TuneBookListenerInterface)m_listeners.elementAt(i)).tuneChanged(e);
  }

  //============================= fills up the tunebook structure
  private void buildTunesTreeMap(Reader readerStram, AbcFileParserListenerInterface clientListener)
  {
    m_tunes = new TreeMap();
    ParserListener listener = new ParserListener();
    m_fileParser.addListener(listener);
    if (clientListener!=null)
      m_fileParser.addListener(clientListener);
    m_fileParser.parseFile(readerStram);
    m_fileParser.removeListener(listener);
    m_fileParser.removeListener(clientListener);
  }

  private class TranscribedTune
  {
    public String header = null;
    public Tune tune = null;
    public String notation = null;
    public boolean m_onlyHeader = true;
  }

  private class ParserListener implements ScannerListenerInterface, AbcFileParserListenerInterface
  {
    private StringBuffer m_currentTuneNotation = null;
    private StringBuffer m_currentHeader = null;
    private boolean isInTune = false;

    public void fileBegin()
    {
     // System.out.println("file begin");
     m_currentHeader = new StringBuffer();
     m_currentTuneNotation = new StringBuffer();
    }

    public void tuneBegin()
    {
      //System.out.println("======================tune begin");
      isInTune = true;
    }

    public void invalidToken(InvalidTokenEvent event)
    {}

    public void validToken(TokenEvent event)
    {}

    public void invalidCharacter(InvalidCharacterEvent event)
    {}

    public void tuneEnd(Tune tune)
    {
      //System.out.println("===================tune end");
      TranscribedTune transcribedTune = new TranscribedTune();
      transcribedTune.tune = tune;
      transcribedTune.notation = new String(m_currentTuneNotation);
      if (m_currentHeader.length()>0)transcribedTune.header = new String(m_currentHeader);
      m_currentTuneNotation = new StringBuffer();
      m_currentHeader = new StringBuffer();
      m_tunes.put(new Integer(tune.getReferenceNumber()), transcribedTune);
      m_originalTunesOrder.addElement(transcribedTune);
      notifyListenersForTuneChange(new TuneChangeEvent(this, TuneChangeEvent.TUNE_UPDATED, tune, transcribedTune.notation));
      isInTune = false;
    }

    public void fileEnd()
    {
      //System.out.println("file End");
    }

    public void lineProcessed(String line)
    {
    	//System.out.println(this.getClass().getName() + " : " + line);
      if (isInTune)
        m_currentTuneNotation.append(line);
      else
        m_currentHeader.append(line);
    }

    public void tokenGenerated(TokenEvent event)
    {}
  }
}

