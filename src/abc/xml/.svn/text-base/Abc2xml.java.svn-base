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
package abc.xml;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import abc.notation.Accidental;
import abc.notation.BarLine;
import abc.notation.EndOfStaffLine;
import abc.notation.KeySignature;
import abc.notation.MultiNote;
import abc.notation.Music;
import abc.notation.Note;
import abc.notation.NotesSeparator;
import abc.notation.RepeatBarLine;
import abc.notation.TimeSignature;
import abc.notation.Tune;
import abc.notation.Tuplet;
import abc.notation.Voice;

public class Abc2xml {

	protected static final String SCORE_PARTWISE_TAG = "score-partwise";
	protected static final String MOVEMENT_NUMBER_TAG = "movement-number";
	protected static final String MOVEMENT_TITLE_TAG = "movement-title";
	protected static final String IDENTIFICATION_TAG = "identification";
	protected static final String ENCODING_TAG = "encoding";
	protected static final String SOFTWARE_TAG = "software";
	protected static final String ENCODING_DATE_TAG = "encoding-date";
	protected static final String PART_LIST_TAG = "part-list";
	protected static final String SCORE_PART_TAG = "score-part";
	protected static final String PART_NAME_TAG = "part-name";
	protected static final String PART_TAG = "part";
	protected static final String MEASURE_TAG = "measure";
	protected static final String ATTRIBUTES_TAG = "attributes";
	protected static final String DIVISIONS_TAG = "divisions";
	protected static final String CLEF_TAG = "clef";
	protected static final String SIGN_TAG = "sign";
	protected static final String LINE_TAG = "line";
	// protected static final String DIVISION_TAG = "division";

	protected static final String NOTE_TAG = "note";
	protected static final String CHORD_TAG = "chord";
	protected static final String PITCH_TAG = "pitch";
	protected static final String REST_TAG = "rest";
	protected static final String STEP_TAG = "step";
	protected static final String ALTER_TAG = "alter";
	protected static final String OCTAVE_TAG = "octave";
	protected static final String DURATION_TAG = "duration";
	protected static final String TIE_TAG = "tie";
	protected static final String DOT_TAG = "dot";
	protected static final String TYPE_TAG = "type";
	protected static final String TIMEMODIFICATION_TAG = "time-modification";
	protected static final String ACTUALNOTES_TAG = "actual-notes";
	protected static final String NORMALNOTES_TAG = "normal-notes";
	protected static final String NOTATIONS_TAG = "notations";
	protected static final String TIED_TAG = "tied";
	protected static final String TUPLET_TAG = "tuplet";
	protected static final String TYPE_ATTRIBUTE = "type";
	protected static final String KEY_TAG = "key";
	protected static final String FIFTHS_TAG = "fifths";
	protected static final String MODE_TAG = "mode";
	protected static final String TIME_TAG = "time";
	protected static final String BEATS_TAG = "beats";
	protected static final String BEAT_TYPE_TAG = "beat-type";
	protected static final String BAR_LINE_TAG = "barline";
	protected static final String REPEAT_TAG = "repeat";
	protected static final String ACCIDENTAL_TAG = "accidental";

	protected static final String BEAM_TAG = "beam";

	protected static final String ID_ATTRIBUTE = "id";
	protected static final String NUMBER_ATTRIBUTE = "number";
	protected static final String LOCATION_ATTRIBUTE = "location";
	protected static final String DIRECTION_ATTRIBUTE = "direction";

	protected static final int DIVISIONS_PER_QUARTER_NOTE = 480;

	/** C major */
	protected static final Accidental[] KEY_NO_ACCIDENTAL = { Accidental.NATURAL, Accidental.NATURAL,
			Accidental.NATURAL, Accidental.NATURAL, Accidental.NATURAL, Accidental.NATURAL,
			Accidental.NATURAL };
	/** G major */
	protected static final Accidental[] KEY_SHARP_1ST = { Accidental.NATURAL, Accidental.NATURAL,
			Accidental.NATURAL, Accidental.SHARP, Accidental.NATURAL, Accidental.NATURAL,
			Accidental.NATURAL };
	/** D major */
	protected static final Accidental[] KEY_SHARP_2ND = { Accidental.SHARP, Accidental.NATURAL,
			Accidental.NATURAL, Accidental.SHARP, Accidental.NATURAL, Accidental.NATURAL,
			Accidental.NATURAL };
	/** A major */
	protected static final Accidental[] KEY_SHARP_3RD = { Accidental.SHARP, Accidental.NATURAL,
			Accidental.NATURAL, Accidental.SHARP, Accidental.SHARP, Accidental.NATURAL,
			Accidental.NATURAL };
	/** E major */
	protected static final Accidental[] KEY_SHARP_4TH = { Accidental.SHARP, Accidental.SHARP, Accidental.NATURAL,
			Accidental.SHARP, Accidental.SHARP, Accidental.NATURAL, Accidental.NATURAL };
	/** B major */
	protected static final Accidental[] KEY_SHARP_5TH = { Accidental.SHARP, Accidental.SHARP, Accidental.NATURAL,
			Accidental.SHARP, Accidental.SHARP, Accidental.SHARP, Accidental.NATURAL };
	/** F# major */
	protected static final Accidental[] KEY_SHARP_6TH = { Accidental.SHARP, Accidental.SHARP, Accidental.SHARP,
			Accidental.SHARP, Accidental.SHARP, Accidental.SHARP, Accidental.NATURAL };
	/** C# major */
	protected static final Accidental[] KEY_SHARP_7TH = { Accidental.SHARP, Accidental.SHARP, Accidental.SHARP,
			Accidental.SHARP, Accidental.SHARP, Accidental.SHARP, Accidental.SHARP };

	/** F major */
	protected static final Accidental[] KEY_FLAT_1ST = { Accidental.NATURAL, Accidental.NATURAL,
			Accidental.NATURAL, Accidental.NATURAL, Accidental.NATURAL, Accidental.NATURAL,
			Accidental.FLAT };
	/** Bb major */
	protected static final Accidental[] KEY_FLAT_2ND = { Accidental.NATURAL, Accidental.NATURAL, Accidental.FLAT,
			Accidental.NATURAL, Accidental.NATURAL, Accidental.NATURAL, Accidental.FLAT };
	/** Eb major */
	protected static final Accidental[] KEY_FLAT_3RD = { Accidental.NATURAL, Accidental.NATURAL, Accidental.FLAT,
			Accidental.NATURAL, Accidental.NATURAL, Accidental.FLAT, Accidental.FLAT };
	/** Ab major */
	protected static final Accidental[] KEY_FLAT_4TH = { Accidental.NATURAL, Accidental.FLAT, Accidental.FLAT,
			Accidental.NATURAL, Accidental.NATURAL, Accidental.FLAT, Accidental.FLAT };
	/** Db major */
	protected static final Accidental[] KEY_FLAT_5TH = { Accidental.NATURAL, Accidental.FLAT, Accidental.FLAT,
			Accidental.NATURAL, Accidental.FLAT, Accidental.FLAT, Accidental.FLAT };
	/** Gb major */
	protected static final Accidental[] KEY_FLAT_6TH = { Accidental.FLAT, Accidental.FLAT, Accidental.FLAT,
			Accidental.NATURAL, Accidental.FLAT, Accidental.FLAT, Accidental.FLAT };
	/** Cb major */
	protected static final Accidental[] KEY_FLAT_7TH = { Accidental.FLAT, Accidental.FLAT, Accidental.FLAT,
			Accidental.FLAT, Accidental.FLAT, Accidental.FLAT, Accidental.FLAT };

	private KeySignature keySignature;

	/**
	 * Writes the specified tune to the specified file as MusicXML.
	 * 
	 * @param file
	 *            A file.
	 * @param tune
	 *            A tune.
	 * @throws IOException
	 *             Thrown if the file cannot be created.
	 */
	public void writeAsMusicXML(Tune tune, File file) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		Document doc = createMusicXmlDOM(tune);
		// dumpDOM(doc);
		writeAsMusicXML(doc, writer);
		writer.flush();
		writer.close();
	}

	/**
	 * Writes the specified Node to the given writer.
	 * 
	 * @param node
	 *            A DOM node.
	 * @param writer
	 *            A stream writer.
	 * @throws IOException
	 *             Thrown if the file cannot be created.
	 */
	public void writeAsMusicXML(Document doc, BufferedWriter writer) throws IOException {
		/*
		 * writer.write("<"+node.getNodeName()); NamedNodeMap attr =
		 * node.getAttributes(); if (attr!=null) for (int i=0;
		 * i<attr.getLength(); i++) writer.write(" " +
		 * attr.item(i).getNodeName() + "=" + attr.item(i).getNodeValue());
		 * writer.write(">"); writer.newLine(); NodeList nlist =
		 * node.getChildNodes(); for (int i=0; i<nlist.getLength(); i++)
		 * writeAsMusicXML(writer, nlist.item(i));
		 * writer.write("</"+node.getNodeName()+">"); writer.newLine();
		 */
		try {
			TransformerFactory transfac = TransformerFactory.newInstance();
			Transformer trans = transfac.newTransformer();
			// trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			trans.setOutputProperty(OutputKeys.INDENT, "yes");
			trans.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "-//Recordare//DTD MusicXML 2.0 Partwise//EN");
			trans.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "http://www.musicxml.org/dtds/partwise.dtd");

			// create string from xml tree
			// StringWriter sw = new StringWriter();
			StreamResult result = new StreamResult(writer);
			DOMSource source = new DOMSource(doc);

			trans.transform(source, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// String xmlString = sw.toString();
		// }
	}

	/**
	 * Creates a DOM representation (fullfilling musicXML dtd) of the specified
	 * tune.
	 * 
	 * @param tune
	 *            A tune.
	 * @return A DOM representation (fullfilling musicXML dtd) of the specified
	 *         tune.
	 */
	public Document createMusicXmlDOM(Tune tune) {
		Document doc = null;
		try {
			doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			Element root = doc.createElement(SCORE_PARTWISE_TAG);
			doc.appendChild(root);
			doc.setXmlVersion("1.0");
			root.setAttribute("version", "2.0");

			Element movementNumberEl = doc.createElement(MOVEMENT_NUMBER_TAG);
			root.appendChild(movementNumberEl);
			Element movementTitleEl = doc.createElement(MOVEMENT_TITLE_TAG);
			if (tune.getTitles().length > 0)
				movementTitleEl.appendChild(doc.createTextNode(tune.getTitles()[0]));
			root.appendChild(movementTitleEl);

			Element identificationEl = doc.createElement(IDENTIFICATION_TAG);
			Element encodingEl = doc.createElement(ENCODING_TAG);
			Element softwareEl = doc.createElement(SOFTWARE_TAG);
			softwareEl.appendChild(doc.createTextNode("ABC4J"));
			encodingEl.appendChild(softwareEl);
			Element encodingDateEl = doc.createElement(ENCODING_DATE_TAG);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			encodingDateEl.appendChild(doc.createTextNode(sdf.format(new Date())));
			encodingEl.appendChild(encodingDateEl);
			identificationEl.appendChild(encodingEl);
			root.appendChild(identificationEl);

			Element partListEl = doc.createElement(PART_LIST_TAG);
			Element scorePartEl = doc.createElement(SCORE_PART_TAG);
			scorePartEl.setAttribute(ID_ATTRIBUTE, "P1");
			Element partNameEl = doc.createElement(PART_NAME_TAG);

			Element partEl = doc.createElement(PART_TAG);
			partEl.setAttribute(ID_ATTRIBUTE, "P1");

			// partNameEl.appendChild(doc.createTextNode(tune.getTitles()[0]));
			scorePartEl.appendChild(partNameEl);
			partListEl.appendChild(scorePartEl);
			root.appendChild(partListEl);

			root.appendChild(partEl);

			// Music music = tune.getMusic();
			convert(doc, tune.getMusic(), partEl);

			/*
			 * int measureNb = tune.getMusic().countMeasures(); for (int i=1;
			 * i<=measureNb; i++) { Measure meas =
			 * tune.getMusic().getMeasure(i); partEl.appendChild(convert(doc,
			 * meas, i)); }
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}

	protected void appendTo(Element measureElement, TimeSignature time, Document context) {
		Element attributeEl = (Element) measureElement.getElementsByTagName(ATTRIBUTES_TAG).item(0);
		if (attributeEl == null) {
			/*
			 * attributeEl = context.createElement(ATTRIBUTES_TAG); Element
			 * divisionEl = context.createElement(DIVISIONS_TAG);
			 * divisionEl.appendChild(context.createTextNode(new
			 * Integer(DIVISIONS_PER_QUARTER_NOTE).toString()));
			 * attributeEl.appendChild(divisionEl);
			 * measureElement.appendChild(attributeEl); Element clefEl =
			 * context.createElement(CLEF_TAG); Element signEl =
			 * context.createElement(SIGN_TAG); Element linEl =
			 * context.createElement(LINE_TAG); clefEl.appendChild(signEl);
			 * clefEl.appendChild(linEl); attributeEl.appendChild(clefEl);
			 * measureElement.insertBefore(attributeEl,
			 * measureElement.getFirstChild());
			 */
			attributeEl = createMeasureGeneralAttributes(context);
			measureElement.insertBefore(attributeEl, measureElement.getFirstChild());
		}
		attributeEl.appendChild(convert(context, time));
	}

	protected void appendTo(Element measureElement, KeySignature key, Document doc) {
		Element attributeEl = (Element) measureElement.getElementsByTagName(ATTRIBUTES_TAG).item(0);
		if (attributeEl == null) {
			attributeEl = createMeasureGeneralAttributes(doc);
			measureElement.insertBefore(attributeEl, measureElement.getFirstChild());
		}
		attributeEl.appendChild(convert(doc, key));
		keySignature = key;
	}

	protected Element createMeasureGeneralAttributes(Document doc) {
		Element attributeEl = doc.createElement(ATTRIBUTES_TAG);

		Element divisionEl = doc.createElement(DIVISIONS_TAG);
		divisionEl.appendChild(doc.createTextNode(new Integer(DIVISIONS_PER_QUARTER_NOTE).toString()));
		attributeEl.appendChild(divisionEl);

		Element clefEl = doc.createElement(CLEF_TAG);
		Element signEl = doc.createElement(SIGN_TAG);
		signEl.appendChild(doc.createTextNode("G"));
		Element linEl = doc.createElement(LINE_TAG);
		linEl.appendChild(doc.createTextNode("2"));
		clefEl.appendChild(signEl);
		clefEl.appendChild(linEl);
		attributeEl.appendChild(clefEl);
		return attributeEl;
	}

	protected void convert(Document doc, Music music, Element musicElement) {
		int measureNb = 1;
		Element currentMeasureEl = doc.createElement(MEASURE_TAG);
		int addedMusicElement = 0;
		musicElement.appendChild(currentMeasureEl);
		Element lastShorterThanQuarterNote = null;
		// KeySignature key = null;
		// TimeSignature time = null;
		int voltaRunning = 0;
		currentMeasureEl.setAttribute(NUMBER_ATTRIBUTE, new Integer(measureNb).toString());
		Iterator it = music.getVoices().iterator();
		while (it.hasNext()) {
			Voice voice = (Voice) it.next();
		for (int i = 0; i < voice.size(); i++) {
			//MusicElement current = (MusicElement) music.elementAt(i);
			if (voice.elementAt(i) instanceof Note) {
				Note note = (Note) voice.elementAt(i);
				Element noteElement = convert(doc, note);
				if (note.getStrictDuration() < Note.QUARTER) {
					if (lastShorterThanQuarterNote == null) {
						// this is the first note of a group
						lastShorterThanQuarterNote = noteElement;
						Element beamNode = doc.createElement(BEAM_TAG);
						beamNode.setAttribute(NUMBER_ATTRIBUTE, "1");
						Node text = doc.createTextNode("begin");
						beamNode.appendChild(text);
						noteElement.appendChild(beamNode);
					} else {
						// this is part of a previously created beam
						lastShorterThanQuarterNote = noteElement;
						Element beamNode = doc.createElement(BEAM_TAG);
						beamNode.setAttribute(NUMBER_ATTRIBUTE, "1");
						// noteElement.appendChild(lastShorterThanQuarterNote);
						Node text = doc.createTextNode("continue");
						beamNode.appendChild(text);
						noteElement.appendChild(beamNode);
					}
				}
				currentMeasureEl.appendChild(noteElement);
				addedMusicElement++;
			} else if (voice.elementAt(i) instanceof MultiNote) {
				Node[] nodes = convert(doc, (MultiNote) voice.elementAt(i));
				for (int j = 0; j < nodes.length; j++)
					currentMeasureEl.appendChild(nodes[j]);
				addedMusicElement++;
			} else if (voice.elementAt(i) instanceof TimeSignature) {
				// time = (TimeSignature)music.elementAt(i);
				appendTo(currentMeasureEl, (TimeSignature) voice.elementAt(i), doc);
			} else if (voice.elementAt(i) instanceof KeySignature) {
				// key = (KeySignature)music.elementAt(i);
				appendTo(currentMeasureEl, (KeySignature) voice.elementAt(i), doc);
			} else if (voice.elementAt(i) instanceof BarLine) {

				BarLine barline = ((BarLine) voice.elementAt(i));
				Element barLineNode = null;
				if (voltaRunning > 1) { // end of volta > 2 on the first bar
										// line we find
					barLineNode = doc.createElement(BAR_LINE_TAG);
					currentMeasureEl.appendChild(barLineNode);
					Element endingEl = doc.createElement("ending");
					endingEl.setAttribute(NUMBER_ATTRIBUTE, Integer.toString(voltaRunning));
					endingEl.setAttribute(TYPE_ATTRIBUTE, "discontinue");
					barLineNode.appendChild(endingEl);
					voltaRunning = 0;
				}

				if (barline instanceof RepeatBarLine) { // start of volta
					RepeatBarLine repeatBarLine = (RepeatBarLine) barline;
					if (barLineNode == null) {
						barLineNode = doc.createElement(BAR_LINE_TAG);
						currentMeasureEl.appendChild(barLineNode);
					}
					Element endingEl = doc.createElement("ending");
					endingEl.setAttribute(NUMBER_ATTRIBUTE, Integer.toString(repeatBarLine.getRepeatNumbers()[0]));
					endingEl.setAttribute(TYPE_ATTRIBUTE, "start");
					barLineNode.appendChild(endingEl);
					voltaRunning = repeatBarLine.getRepeatNumbers()[0];
				}
				if (barline.getType() == BarLine.REPEAT_CLOSE) { // Close
																	// barline
					if (barline.toString().equals(":|")) {
						if (barLineNode == null) {
							barLineNode = doc.createElement(BAR_LINE_TAG);
							currentMeasureEl.appendChild(barLineNode);
						}
						if (voltaRunning == 1) {
							Element endingEl = doc.createElement("ending");
							endingEl.setAttribute(NUMBER_ATTRIBUTE, Integer.toString(voltaRunning));
							endingEl.setAttribute(TYPE_ATTRIBUTE, "stop");
							barLineNode.appendChild(endingEl);
							voltaRunning = 0;
						}
						barLineNode.setAttribute(LOCATION_ATTRIBUTE, "right");
						Element repeatNode = doc.createElement(REPEAT_TAG);
						repeatNode.setAttribute(DIRECTION_ATTRIBUTE, "backward");
						barLineNode.appendChild(repeatNode);
					}
				}
				if (addedMusicElement == 0) {
					if (barline.getType() == BarLine.REPEAT_OPEN) {
						if (barLineNode == null) {
							barLineNode = doc.createElement(BAR_LINE_TAG);
							currentMeasureEl.appendChild(barLineNode);
						}
						barLineNode.setAttribute(LOCATION_ATTRIBUTE, "left");
						Element repeatNode = doc.createElement(REPEAT_TAG);
						repeatNode.setAttribute(DIRECTION_ATTRIBUTE, "forward");
						barLineNode.appendChild(repeatNode);
					}
				} else if (addedMusicElement > 0) {// a bar line has been
					// detected , do we create a
					// new measure ?
					currentMeasureEl = doc.createElement(MEASURE_TAG);
					measureNb++;
					currentMeasureEl.setAttribute(NUMBER_ATTRIBUTE, new Integer(measureNb).toString());
					musicElement.appendChild(currentMeasureEl);
					if (barline.getType() == BarLine.REPEAT_OPEN) {
						if (barLineNode == null) {
							barLineNode = doc.createElement(BAR_LINE_TAG);
							barLineNode.setAttribute(LOCATION_ATTRIBUTE, "left");
						}
						Element repeatNode = doc.createElement(REPEAT_TAG);
						repeatNode.setAttribute(DIRECTION_ATTRIBUTE, "forward");
						barLineNode.appendChild(repeatNode);
						currentMeasureEl.appendChild(barLineNode);
					}
					addedMusicElement = 0;
				}
			} else if (voice.elementAt(i) instanceof EndOfStaffLine) {
				Element printEl = doc.createElement("print");
				printEl.setAttribute("new-system", "yes");
				currentMeasureEl.appendChild(printEl);
			}  if (voice.elementAt(i) instanceof NotesSeparator){
				//Node lastBeam=;
				if (lastShorterThanQuarterNote != null ) {
					Element beamNode;
					if (lastShorterThanQuarterNote.getElementsByTagName(BEAM_TAG).getLength() ==0)
						beamNode = doc.createElement(BEAM_TAG);
					else
						beamNode =(Element)lastShorterThanQuarterNote.getElementsByTagName(BEAM_TAG).item(0);
					
					beamNode.setAttribute(NUMBER_ATTRIBUTE, "1");
					beamNode.setTextContent("end");
					lastShorterThanQuarterNote.appendChild(beamNode);
					lastShorterThanQuarterNote = null;
				}
			}
			

		}//end each element in voice
		}//end each voice in music
	}

	protected Node convert(Document doc, KeySignature signature) {
		Element keyEl = doc.createElement(KEY_TAG);
		Element fifthEl = doc.createElement(FIFTHS_TAG);
		Accidental[] acc = signature.getAccidentals();
		if (Arrays.equals(acc, KEY_NO_ACCIDENTAL))
			fifthEl.appendChild(doc.createTextNode("0"));
		else if (signature.hasOnlySharps()) {
			if (Arrays.equals(acc, KEY_SHARP_1ST))
				fifthEl.appendChild(doc.createTextNode("1"));
			else if (Arrays.equals(acc, KEY_SHARP_2ND))
				fifthEl.appendChild(doc.createTextNode("2"));
			else if (Arrays.equals(acc, KEY_SHARP_3RD))
				fifthEl.appendChild(doc.createTextNode("3"));
			else if (Arrays.equals(acc, KEY_SHARP_4TH))
				fifthEl.appendChild(doc.createTextNode("4"));
			else if (Arrays.equals(acc, KEY_SHARP_5TH))
				fifthEl.appendChild(doc.createTextNode("5"));
			else if (Arrays.equals(acc, KEY_SHARP_6TH))
				fifthEl.appendChild(doc.createTextNode("6"));
			else if (Arrays.equals(acc, KEY_SHARP_7TH))
				fifthEl.appendChild(doc.createTextNode("7"));
		} else {
			if (Arrays.equals(acc, KEY_FLAT_1ST))
				fifthEl.appendChild(doc.createTextNode("-1"));
			else if (Arrays.equals(acc, KEY_FLAT_2ND))
				fifthEl.appendChild(doc.createTextNode("-2"));
			else if (Arrays.equals(acc, KEY_FLAT_3RD))
				fifthEl.appendChild(doc.createTextNode("-3"));
			else if (Arrays.equals(acc, KEY_FLAT_4TH))
				fifthEl.appendChild(doc.createTextNode("-4"));
			else if (Arrays.equals(acc, KEY_FLAT_5TH))
				fifthEl.appendChild(doc.createTextNode("-5"));
			else if (Arrays.equals(acc, KEY_FLAT_6TH))
				fifthEl.appendChild(doc.createTextNode("-6"));
			else if (Arrays.equals(acc, KEY_FLAT_7TH))
				fifthEl.appendChild(doc.createTextNode("-7"));
		}
		keyEl.appendChild(fifthEl);
		Element modeEl = doc.createElement(MODE_TAG);
		switch (signature.getMode()) {
		case 0:
			modeEl.appendChild(doc.createTextNode("aeolian"));
			break;
		case 1:
			modeEl.appendChild(doc.createTextNode("dorian"));
			break;
		case 2:
			modeEl.appendChild(doc.createTextNode("ionian"));
			break;
		case 3:
			modeEl.appendChild(doc.createTextNode("locrian"));
			break;
		case 4:
			modeEl.appendChild(doc.createTextNode("lydian"));
			break;
		case 5:
			modeEl.appendChild(doc.createTextNode("major"));
			break;
		case 6:
			modeEl.appendChild(doc.createTextNode("minor"));
			break;
		case 7:
			modeEl.appendChild(doc.createTextNode("mixolydian"));
			break;
		case 8:
			modeEl.appendChild(doc.createTextNode("phrygian"));
			break;
		default:
			modeEl.appendChild(doc.createTextNode("major"));
			break;
		}
		keyEl.appendChild(modeEl);
		return keyEl;
	}

	protected Node convert(Document doc, TimeSignature signature) {
		Element timeEl = doc.createElement(TIME_TAG);
		Element beatsEl = doc.createElement(BEATS_TAG);
		Element beatTypeEl = doc.createElement(BEAT_TYPE_TAG);
		beatsEl.appendChild(doc.createTextNode(Integer.toString(signature.getNumerator())));
		beatTypeEl.appendChild(doc.createTextNode(Integer.toString(signature.getDenominator())));
		timeEl.appendChild(beatsEl);
		timeEl.appendChild(beatTypeEl);
		return timeEl;
	}

	protected Node[] convert(Document doc, MultiNote chord) {
		Vector notes = chord.getNotesAsVector();
		Node[] nodes = new Node[notes.size()];
		for (int i = 0; i < notes.size(); i++) {
			nodes[i] = convert(doc, (Note) notes.elementAt(i));
			if (i != 0)
				nodes[i].insertBefore(doc.createElement(CHORD_TAG), nodes[i].getFirstChild());
		}
		return nodes;

	}

	protected Element convert(Document doc, Note note) {
		Element noteEl = doc.createElement(NOTE_TAG);
		Element durationEl = doc.createElement(DURATION_TAG);

		String stepValue = null;
		byte strictHeight = note.getStrictHeight();
		int octave = note.getOctaveTransposition();
		if (note.isRest()) {
			Element rest = doc.createElement(REST_TAG);
			noteEl.appendChild(rest);
		} else {
			switch (strictHeight) {
			case Note.C:
				stepValue = "C";
				break;
			case Note.D:
				stepValue = "D";
				break;
			case Note.E:
				stepValue = "E";
				break;
			case Note.F:
				stepValue = "F";
				break;
			case Note.G:
				stepValue = "G";
				break;
			case Note.A:
				stepValue = "A";
				break;
			case Note.B:
				stepValue = "B";
				break;
			}

			octave = octave + 4;
			String octaveValue = new Integer(octave).toString();
			// Element typeEl = doc.createElement(TYPE_TAG);
			// typeEl.appendChild(doc.createTextNode("quarter"));
			Element pitchEl = doc.createElement(PITCH_TAG);
			Element stepEl = doc.createElement(STEP_TAG);
			stepEl.appendChild(doc.createTextNode(stepValue));
			pitchEl.appendChild(stepEl);

			if (keySignature != null) {
				Accidental accidental = note.getAccidental();
				if (accidental.isInTheKey()) {
					accidental = keySignature.getAccidentalFor(note.getStrictHeight());
				}
				if (accidental.isDefined()) {
					int alterValue = accidental.getNearestOccidentalValue();

					Element alterEl = doc.createElement(ALTER_TAG);
					alterEl.appendChild(doc.createTextNode(Integer.toString(alterValue)));
					pitchEl.appendChild(alterEl);
				}
			}

			Element octaveEl = doc.createElement(OCTAVE_TAG);
			octaveEl.appendChild(doc.createTextNode(octaveValue));
			pitchEl.appendChild(octaveEl);

			noteEl.appendChild(pitchEl);

			if (note.hasAccidental()) {
				Node acc = doc.createElement(ACCIDENTAL_TAG);
				Node accValue = null;
				if (note.getAccidental().isFlat())
					accValue = doc.createTextNode("flat");
				else if (note.getAccidental().isNatural())
					accValue = doc.createTextNode("natural");
				else if (note.getAccidental().isSharp())
					accValue = doc.createTextNode("sharp");
				//TODO double flat/sharp
				acc.appendChild(accValue);
				noteEl.appendChild(acc);
			}
		}
		int relDuration = note.getDuration() * DIVISIONS_PER_QUARTER_NOTE / Note.QUARTER;
		durationEl.appendChild(doc.createTextNode(new Integer(relDuration).toString()));
		noteEl.appendChild(durationEl);
		if (note.isTied()) {
			String type = null;
			if (note.isBeginningTie()) {
				type = "start";
			} else if (note.isEndingTie()) {
				type = "stop";
			}
			if (type != null) {
				Element tieEl = doc.createElement(TIE_TAG);
				tieEl.setAttribute(TYPE_ATTRIBUTE, type);
				noteEl.appendChild(tieEl);
				Element notationsEl = (Element) noteEl.getElementsByTagName(NOTATIONS_TAG).item(0);
				if (notationsEl == null) {
					notationsEl = doc.createElement(NOTATIONS_TAG);
					noteEl.appendChild(notationsEl);
				}
				Element tiedEl = doc.createElement(TIED_TAG);
				tiedEl.setAttribute(TYPE_ATTRIBUTE, type);
				notationsEl.appendChild(tiedEl);
			}
		}

		if (note.isPartOfTuplet()) {
			Tuplet tuplet = note.getTuplet();
			Vector notes = tuplet.getNotesAsVector();
			int d = 0;
			for (Iterator iterator = notes.iterator(); iterator.hasNext();) {
				Note n = (Note) iterator.next();
				d += n.getStrictDuration();
			}
			int gcd = MathUtils.pgcd(d, tuplet.getTotalDuration());
			Element timeModificationEl = doc.createElement(TIMEMODIFICATION_TAG);

			Element actualNotesEl = doc.createElement(ACTUALNOTES_TAG);
			actualNotesEl.appendChild(doc.createTextNode(Integer.toString(d / gcd)));
			timeModificationEl.appendChild(actualNotesEl);

			Element normalNotesEl = doc.createElement(NORMALNOTES_TAG);
			normalNotesEl.appendChild(doc.createTextNode(Integer.toString(tuplet.getTotalDuration() / gcd)));
			timeModificationEl.appendChild(normalNotesEl);

			noteEl.appendChild(timeModificationEl);

			Element notationsEl = doc.createElement(NOTATIONS_TAG);
			Element tupletEl = doc.createElement(TUPLET_TAG);
			if (note.equals(notes.get(0))) {
				tupletEl.setAttribute(TYPE_ATTRIBUTE, "start");
				notationsEl.appendChild(tupletEl);
				noteEl.appendChild(notationsEl);
			} else if (note.equals(notes.get(notes.size() - 1))) {
				tupletEl.setAttribute(TYPE_ATTRIBUTE, "stop");
				notationsEl.appendChild(tupletEl);
				noteEl.appendChild(notationsEl);
			}
		}

		Node type = doc.createElement(TYPE_TAG);
		Node typeValue = null;
		switch (note.getStrictDuration()) {
		case Note.SIXTY_FOURTH:
			typeValue = doc.createTextNode("64th");
			break;
		case Note.THIRTY_SECOND:
			typeValue = doc.createTextNode("32nd");
			break;
		case Note.SIXTEENTH:
			typeValue = doc.createTextNode("16th");
			break;
		case Note.EIGHTH:
			typeValue = doc.createTextNode("eighth");
			break;
		case Note.QUARTER:
			typeValue = doc.createTextNode("quarter");
			break;
		case Note.HALF:
			typeValue = doc.createTextNode("half");
			break;
		case Note.WHOLE:
			typeValue = doc.createTextNode("whole");
			break;
		}
		if (typeValue != null) {
			type.appendChild(typeValue);
			noteEl.appendChild(type);
		}

		if (note.countDots() >= 1) {
			for (int i = 0; i < note.countDots(); i++) {
				Node dot = doc.createElement(DOT_TAG);
				noteEl.appendChild(dot);
			}
		}
		return noteEl;
	}

	protected void dumpDOM(Document doc) {
		try {
			TransformerFactory transfac = TransformerFactory.newInstance();
			Transformer trans = transfac.newTransformer();
			// trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			trans.setOutputProperty(OutputKeys.INDENT, "yes");
			trans.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "test 2 doctype");

			// create string from xml tree
			// StringWriter sw = new StringWriter();
			StreamResult result = new StreamResult(System.out);
			DOMSource source = new DOMSource(doc);
			trans.transform(source, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
