package de.hsa.jam.evaluation;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Vector;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import de.hsa.jam.jAM;
import de.hsa.jam.audio.Model;
import de.hsa.jam.util.jAMUtils;

/**
 * This class evaluates the system, based on the reference melodies in a database
 *  
 * @author Michael Wager
 */
public class Evaluator {
	private Model model;

	private Connection connection = null;
	private ResultSet rs;
	private Statement statement;

	// SPALTEN !
	private String name;
	private AudioInputStream stream = null;
	private Vector<Integer> refMelody;
	private int bpm, transponierend = 0;
	private String Tonart, Taktart;
	private String Mic;
	private String instrument;

	private Vector<Float> erkennungsratenNotenInsgesamt = null;// ALLE
	private Vector<Float> erkennungsratenRPInsgesamt = null;// ALLE
	private Vector<Float> besteErkennungsratenNoten = null;// ALLE BESTEN PRO ZEILE
	
	private Vector<String> instrumente;
	private HashMap<String, Vector<Float>> instrErkennungsraten; // INSTR
																	// BASIEREND
	private long START_TIME;

	private Vector<Float> erkennungsratenProZeileNoten; // zB an 0. stelle: 90%
	private Vector<Float> erkennungsratenProZeilePR;
	private Vector<String> parameterProZeile; // dann hier an 0. stelle:
												// "512Bytes / 0%"

	//  nur immer die BESTEN Raten pro Zeile (also pro 20 Durchläufe)
	// speichern !
	private Vector<String> bestOfTheBest; // zB: [0]="99% 512 0%" (indizes
											// entsprechen IDs in DB_Tabelle)
	
	public Evaluator() {
		reset();
		START_TIME = System.currentTimeMillis();
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public void reset() {
		this.ID = 0;
		erkennungsratenNotenInsgesamt = new Vector<Float>();
		erkennungsratenRPInsgesamt=new Vector<Float>();
		besteErkennungsratenNoten=new Vector<Float>();
		instrumente = new Vector<String>();
		instrErkennungsraten = new HashMap<String, Vector<Float>>();
		erkennungsratenProZeileNoten = new Vector<Float>();
		erkennungsratenProZeilePR=new Vector<Float>();
		parameterProZeile = new Vector<String>();
		ABC_NOTES = new Vector<String>();
		bestOfTheBest = new Vector<String>();
	}

	/**
	 * initialize database connection
	 * */
	public boolean connectToDB() {
		jAM.log("********** INITIALIZING DB CONNECTION **********", false);
		try {
			Class.forName("com.mysql.jdbc.Driver");
			jAM.log("MySQL JDBC driver loaded ok.", false);

			connection = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1/jam", "root", "InetP");

			if (!connection.isClosed()) {
				jAM.log("Successfully connected to MySQL server using TCP/IP...",
						false);
			}

			return true;

		} catch (Exception e) {
			e.printStackTrace();
			connection = null;
			return false;
		}
	}
	/**
	 * close database connection
	 * */
	public void closeDBConnection() {
		try {
			if (connection != null)
				connection.close();
			jAM.log("Successfully closed DB connection", false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void gimmiCurrentResults(boolean fertig) {
		if (fertig)
			finalStats();
		else
			statsPerLine();
	}

	/**
	 * print some statistics every line
	 * */
	private void statsPerLine() {
		String s = "********************************** ERGEBNISSE DER EVALUATION PRO ZEILE **********************************\n";

		float sum = 0;
		float[] arr = jAMUtils.findMax(erkennungsratenProZeileNoten);
		besteErkennungsratenNoten.add(arr[1]);
		
		float[] arr2 = jAMUtils.findMax(erkennungsratenProZeilePR);
		
		String proZeile = "";
		String bestABCNOTES = "";
		try {
			proZeile = "Beste Noten-Erkennungsrate dieser Melodie:\t" + arr[1] + "%" + " mit Parameter: " + parameterProZeile.get((int) arr[0]) + "\n"
					 + "Beste PR   -Erkennungsrate dieser Melodie:\t" + arr2[1] + "%\n";
					
			bestABCNOTES = ABC_NOTES.get((int) arr[0]);
			bestOfTheBest.add(name + ": " + arr[1] + "% mit Params: " + parameterProZeile.get((int) arr[0]));
		} catch (Exception e) {
			e.printStackTrace();
			proZeile = "FEHLER: " + e.getMessage();
		}

		s += "TITEL: " + name + " Mic: " + Mic + " INSTRUMENT: " + instrument + "\n PDA: " + PITCHDETECTOR + "\n";
		s += proZeile + "\n";
		s += "Tabelle:\n";
		s += "PDA:\t\tBufferSize:\tBufferOverlap:\tNotenerkennungsrate:\n";
		int l = 0;
		for (int i = 0; i < model.getChunks().length; i++) { // 4*5 == 20 Mal durch hier

			for (int j = 0; j < model.getOverlaps().length; j++) {
				s += PITCHDETECTOR + "\t\t";
				s += model.getChunks()[i] + "\t\t";

				float wert = -1;
				try {
					wert = erkennungsratenProZeileNoten.get(l++);
				} catch (Exception e) {
				}

				s += model.getOverlaps()[j] + "\t\t" + format(wert) + "%\n";
			}
		}
		s += "\nTranskription mit bester Recall-Erkennungsrate fuer diese Zeile:\n"
				+ bestABCNOTES + "\n";
		s += "\n\n\n\n";
		// da diese PRO Zeile ausgewertet werden:
		erkennungsratenProZeileNoten.clear();
		erkennungsratenProZeilePR.clear();
		parameterProZeile.clear();
		ABC_NOTES.clear();

		jAM.log(s, false);
	}

	private String finalEval = "";

	/**
	 * collect data for final statistics
	 * */
	private void finalStats() {
		float sum = 0;
		for (int i = 0; i < erkennungsratenNotenInsgesamt.size(); i++) {
			sum += erkennungsratenNotenInsgesamt.get(i);
		}
		float avg = sum / erkennungsratenNotenInsgesamt.size();
		
		sum = 0;
		for (int i = 0; i < erkennungsratenRPInsgesamt.size(); i++) {
			sum += erkennungsratenRPInsgesamt.get(i);
		}
		float avg1 = sum / erkennungsratenRPInsgesamt.size();
		
		sum=0;
		for (int i = 0; i < besteErkennungsratenNoten.size(); i++) {
			sum += besteErkennungsratenNoten.get(i);
		}
		float avg2 = sum / besteErkennungsratenNoten.size();

		finalEval += "PDA: " + PITCHDETECTOR + "\n";
		finalEval += "Durchschnittliche NOTEN-Erkennungsrate(Rynn) des Systems: " + avg + "% ==> bezogen auf alle Testdaten und auf PDA: " + PITCHDETECTOR + "\n"
			+        "Durchschnittliche Erkennungsrate(Recall+Precision -> harmonic mean) des Systems: " + avg1 + "% ==> bezogen auf alle Testdaten und auf PDA: " + PITCHDETECTOR + "\n"
			+ "Durchschnittliche Noten-Erkennungsrate der Besten pro Zeile: " + avg2
			+ "% ==> bezogen auf je die Beste Noten-Erkennungsrate pro Zeile und auf PDA: " + PITCHDETECTOR + "\n";
		
//		float[] arr = jAMUtils.findMax(erkennungsratenNotenInsgesamt);
//		finalEval += "Beste Erkennungsrate: " + arr[1] + "% an INDEX=" + arr[0] + "\n";

		finalEval += "\n----- Instrumente basierende Evaluation: -----\n";
		for (int i = 0; i < instrumente.size(); i++) {
			finalEval += instrumente.get(i) + "\t";
		}
		finalEval += "\n";

		for (int i = 0; i < instrumente.size(); i++) {
			String instr = instrumente.get(i);
			// finalEval+= "Instrument: " + instr + "\n";

			sum = 0;
			for (int j = 0; j < instrErkennungsraten.get(instr).size(); j++) {
				sum += instrErkennungsraten.get(instr).get(j);
			}
			// finalEval+= "Erkennungsrate: " +
			// sum/instrErkennungsraten.get(instr).size() + "% basierend auf: "+
			// instrErkennungsraten.get(instr) + " Size: "+
			// instrErkennungsraten.get(instr).size() +"\n";
			finalEval += sum / instrErkennungsraten.get(instr).size() + "%\t";
		}

		finalEval += "\n\n";
		finalEval += "* * * * * bestOfTheBest: Die Besten Noten-Erkennungsraten mit Params pro Lied:\n";
		for (int i = 0; i < bestOfTheBest.size(); i++) {
			finalEval += bestOfTheBest.get(i) + "\n";
		}

		reset();
	}

	/**
	 * final statistic output
	 * */
	public void FINAL() {
		// und davon dann den Durchschnitt am Schluß
		finalEval = "\n\n\n\n\n********************************** === ENGUELTIGE ERGEBNISSE DER EVALUATION === **********************************\n"
				+ "Zeit: "
				+ (System.currentTimeMillis() - START_TIME)
				/ 1000.0
				/ 60.0f + " Minuten\n" + finalEval;

		finalEval += "--> MinDur: " + model.getMinDur() + "ms MinLevel: "
				+ model.getMinLev() + "\n";

		finalEval += "\n\n\n\n";
		jAM.log(finalEval, false);
	}

	private Blob blob;
	private int ID = 0;

	/**
	 * gets the next stream from the line in the table
	 * */
	public AudioInputStream getNextStream(boolean selectNextLine)
			throws Exception {
		if (selectNextLine)// TODO now einmal sollte reichen !
			ID++;

		
		ID=7;
		statement = connection.createStatement();
		statement.executeQuery("SELECT * FROM Referenzmelodie WHERE id='" + ID
				+ "'");
		rs = statement.getResultSet();

		if (rs.next()) {
			int idVal = rs.getInt("id");
			name = rs.getString("name");
			model.setTitle("Zeile: #" + idVal + " - " + name);

			blob = rs.getBlob("WaveDatei");

			try {
				stream = convertFromBlob(blob);
			} catch (Exception e) {
				e.printStackTrace();
			}

			String refstring = rs.getString("referenznotenstring");
			refMelody = new Vector<Integer>();

			String[] ref = refstring.split(",");
			for (int i = 0; i < ref.length; i++) {
				refMelody.add(Integer.parseInt(ref[i++].trim()));
				refMelody.add(Integer.parseInt(ref[i].trim()));
			}

			bpm = rs.getInt("BPM");
			model.setBPM(bpm);

			Tonart = rs.getString("Tonart");
			model.setTonart(Tonart);

			Taktart = rs.getString("Taktart");
			int idx = 0;
			if (Taktart.equals("4/4"))
				idx = 0;
			else if (Taktart.equals("3/4"))
				idx = 1;
			else if (Taktart.equals("5/4"))
				idx = 2;
			model.setTaktart(idx);

			transponierend = rs.getInt("transponierend");
			model.setTransposeRecIndex(transponierend);

			Mic = rs.getString("Mic");
			instrument = rs.getString("instrument");

			/*
			 * System.out.println ( "id = " + idVal + ", \nname = " + name +
			 * ", \nrefstring = " + refstring + ", \nbpm = " + bpm +
			 * ", \nTonart = " + Tonart + ", \nTaktart = " + Taktart +
			 * ", \ntransponierend = " + transponierend + ", \nMic = " + Mic +
			 * ", \ninstrument = " + instrument );
			 */

			return stream;
		}

		// Wir sind fertig! es gibt keine Zeile mehr!
		else {
			rs.close();
			statement.close();
			return null;
		}
	}

	/**
	 * convert blob data to wave file
	 * */
	public AudioInputStream convertFromBlob(Blob blob) {
		try {
			File blobFile = new File(jAM.HOME_PATH + "/Desktop/evalFiles/"+ name + ".wav");
			FileOutputStream outStream = new FileOutputStream(blobFile);
			InputStream inStream = blob.getBinaryStream();

			int length = -1;
			int size = (int) blob.length();
			byte[] buffer = new byte[size];

			while ((length = inStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, length);
				outStream.flush();
			}

			inStream.close();
			outStream.close();

			return AudioSystem.getAudioInputStream(blobFile);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Vector<Integer> getNextRefMelody() {
		return refMelody;
	}

	//additional eval params
	private int cnt = 1;
	private String PITCHDETECTOR = "";
	private Vector<String> ABC_NOTES;
	
	/**
	 * NoteCollector calls this function when transcription is done.
	 * Calculate and collect important evaluation data like recall, precision and note-error here.
	 * */
	public void evaluateCurrentTranscription(Vector<Integer> evaluationSammler, String PITCHDETECTOR, String abcNotes) {
		this.PITCHDETECTOR = PITCHDETECTOR;

		/**Simulation des Beispiels aus der bac-Arbeit (Kapitel 6 Evaluation, Notenfehler):**/
//		refMelody = new Vector<Integer>();
//		refMelody.add(60);refMelody.add(2);
//		refMelody.add(62);refMelody.add(2);
//		refMelody.add(64);refMelody.add(2);
//		refMelody.add(62);refMelody.add(2);
//		refMelody.add(60);refMelody.add(4);
//		refMelody.add(0);refMelody.add(4);
//		evaluationSammler = new Vector<Integer>();
//		evaluationSammler.add(60);evaluationSammler.add(2);
//		evaluationSammler.add(62);evaluationSammler.add(2);
//		evaluationSammler.add(64);evaluationSammler.add(1);
//		evaluationSammler.add(52);evaluationSammler.add(1);
//		evaluationSammler.add(62);evaluationSammler.add(2);
//		evaluationSammler.add(60);evaluationSammler.add(4);
//		evaluationSammler.add(0);evaluationSammler.add(4);
		
		float anzahlNotenInsgesamt = refMelody.size() / 2;
		float anzahlErkannterNoten = evaluationSammler.size() / 2;

		float hitsRefNotes = countHitsRef(evaluationSammler); //vR wieviele relevante sind erkannt worden?
		float hitsTranscribedNotes = countHitsErkannt(evaluationSammler); //vT wieviele erkannte sind relevant? --> a
		
		//recall and precision
		float a=hitsTranscribedNotes, //-> gefunden + relevant
			  b=anzahlErkannterNoten-hitsTranscribedNotes, //-> gefunden, aber NICHT relevant
			  c=anzahlNotenInsgesamt-hitsRefNotes; //-> NICHT gefunden, aber relevant

		float recall =    a/ (a+c);
		float precision = a/ (a+b);
		//marmonic mean (F-Score) see http://en.wikipedia.org/wiki/Precision_and_recall
		float F =  2 * ((recall * precision) / (recall + precision)) * 100; // avg: (recall + precision) / 2 * 100;
		
		//Notenfehler gem. Ryynaeen:
		float noteError = 0.5f
						* (((anzahlNotenInsgesamt - hitsRefNotes) / anzahlNotenInsgesamt) + ((anzahlErkannterNoten - hitsTranscribedNotes) / anzahlErkannterNoten))
						* 100.0f;
		
		float Nn = (100.0f - noteError);
		
		//SIMULATE
//		System.out.println("############################################################");
//		System.out.println("############################################################");
//		System.out.println("############################################################");
//		System.out.println("\nNn: " + Nn + "%,  F: " + F + "%, recall: " +recall*100 + "% precision: " + precision*100  + "%\n"
//				+"hitsRefNotes: " + hitsRefNotes  + "hitsTranscribedNotes: " + hitsTranscribedNotes + "(=a) b=" + b + " c=" + c + "\n"
//				+ "anzahlNotenInsgesamt: " + anzahlNotenInsgesamt + " anzahlErkannterNoten: " +anzahlErkannterNoten + "\n");
//		System.out.println(refMelody);
//		System.out.println(evaluationSammler);
//		System.exit(0);

		
		//insgesamt,pro Zeile und pro Instrument
		erkennungsratenNotenInsgesamt.add(Nn);
		erkennungsratenRPInsgesamt.add(F);
		
		erkennungsratenProZeileNoten.add(Nn);
		erkennungsratenProZeilePR.add(F);
		parameterProZeile.add("BufferSize: " + model.getChunk() + "Bytes - Overlap: " + model.getOverlap());
		// und pro zeile den besten abcNotes String printen
		ABC_NOTES.add(abcNotes);
		
		
		if (!instrumente.contains(instrument))
			instrumente.add(instrument);

		if (instrErkennungsraten.get(instrument) == null)
			instrErkennungsraten.put(instrument, new Vector<Float>());

		instrErkennungsraten.get(instrument).add(Nn);

		System.out.println(cnt++ + " EVALUIERE: " + name
				//+ ", BPM: " + bpm
				//+ ", Tonart: " + Tonart + ", Taktart: " + Taktart
				//+ ", Transponierend: " + transponierend + " CHUNK: "
				//+ model.getChunk() + " Overlap: " + model.getOverlap()
				+ " Nn: " + format(Nn) + "%"
				+ " F: " + format(F) + "%" + " (recall: " + format(recall*100.0f) + "% precision: " + format(precision*100.0f) + "%)");
	}
	
	private String format(float f) {
		return String.format("%.4g", f); //4 steht fuer 12.12 also 4 ziffern !
	}
	
	// wieviele ref Noten sind in den erkannten? -> wieviele relevante sind erkannt worden?
	/**
	 * count how many relevant notes are detected
	 **/
	public int countHitsRef(Vector<Integer> evaluationSammle) {
		int cnt = 0;

		 Vector<Integer>evaluationSammler = (Vector<Integer>)evaluationSammle.clone();
		 Vector<Integer>refMelody = (Vector<Integer>)this.refMelody.clone();

		for (int i = 0; i < refMelody.size(); i++) {
			int referenzTon = refMelody.get(i);
			int referenzWert = refMelody.get(i+1);

			for (int j = 0; j < evaluationSammler.size(); j++) {
				int erkannterTon = evaluationSammler.get(j);
				int erkannterWert = evaluationSammler.get(j+1);

				if (referenzTon == erkannterTon && referenzWert == erkannterWert) {
					cnt++;
					
					refMelody.remove(i);
					refMelody.remove(i);
					i -= 2;
					
					evaluationSammler.remove(j);
					evaluationSammler.remove(j);
					j-=2;
					
					break;
				}
				j++;
			}
			i++;
		}
		return cnt;
	}

	// wieviele erkannte Noten sind in der RefMelo? -> wieviele erkannte sind relevant?
	/**
	 * count how many detected notes are relevant
	 **/
	public int countHitsErkannt(Vector<Integer> evaluationSammle) {
		int cnt = 0;

		Vector<Integer>refMelody = (Vector<Integer>)this.refMelody.clone();
		Vector<Integer>evaluationSammler = (Vector<Integer>)evaluationSammle.clone();

		// hole erkanntenTon und erkanntenWert und check GESAMTE REFERENZMELO ob
		// dieses vorkommt!
		for (int i = 0; i < evaluationSammler.size(); i++) {
			int erkannterTon = evaluationSammler.get(i);
			int erkannterWert = evaluationSammler.get(i+1);

			for (int j = 0; j < refMelody.size(); j++) {
				int referenzTon = refMelody.get(j);
				int referenzWert = refMelody.get(j+1);

				if (erkannterTon == referenzTon && erkannterWert == referenzWert) {
					cnt++;
					
					evaluationSammler.remove(i); 
					evaluationSammler.remove(i);
					i-=2;
					
					refMelody.remove(j);
					refMelody.remove(j); 
					j-=2;
					 
					// refMelody.remove(j-1);
					break;
				}
				j++;
			}
			i++;
		}
		return cnt;
	}
}