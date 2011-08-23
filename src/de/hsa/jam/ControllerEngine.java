package de.hsa.jam;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.logging.Handler;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import de.hsa.jam.evaluation.Evaluator;
import de.hsa.jam.ui.CustomFileFilter;

/**
 * This class implements the controller. 
 * 
 * The controller translates user actions into Property Updates on the Model.
 * <br />
 * If the Model fires a Property Change the controller translates this action into a Component Update.
 *
 * @author Michael Wager
 */
public class ControllerEngine extends AbstractController {
//	private static final Logger LOG = Logger.getLogger(ControllerEngine.class.getName());
	
	// ----- PROPERTIES FUER USER ACTIONS ----- (Properties this controller
	// expects to be stored in one or more registered models)
	public static final String ERROR_PROPERTY = "jAM_error";
	public static final String END_OF_APP_PROPERTY = "shutdown";

	public static final String NEW_PROJECT_PROPERTY = "newProject";
	public static final String LOAD_WAV_FILE_PROPERTY = "setAudioFile";
	public static final String OPEN_PROJECT_PROPERTY = "openProject";
	public static final String SAVE_WAV_PROPERTY = "saveWave";
	public static final String SAVE_PROJECT_PROPERTY = "saveProject";
	public static final String SAVE_MIDI_PROPERTY = "saveMidi";
	public static final String SAVE_IMAGE_PROPERTY = "saveImage";

	public static final String HELP_PROPERTY = "helpMe";
	public static final String ABOUT_PROPERTY = "aboutMe";

	public static final String INIT_PROCESSING = "initProcessing";
	public static final String START_STOP_PROCESSING_BUTTON_PROPERTY = "startStopProcessing";
	public static final String START_STOP_MIDI_PROPERTY = "playOrStopMIDI";
	public static final String MIDIBUTTON_NAME_PROPERTY = "MidiButtonName";
	public static final String SELECT_MIXER_PROPERTY = "selectMixer";
	public static final String INPUTLEVEL_PROPERTY = "InputLevel";

	public static final String UPDATE_SCORE_PROPERTY = "updateScore"; // nur die
																		// Noten
	public static final String PARSE_AND_SET_TUNE_PROPERTY = "parseAndSetTune"; // den
																				// ganzen
																				// "TuneAsString"
	public static final String SAVE_TUNE_PROPERTY = "saveTuneAsString";
	public static final String SHOW_TUNE_PROPERTY = "showTune"; // Tune Object
	public static final String HIGHLIGHT_NOTE_PROPERTY = "selectNote"; // highlight
																	// notes
																	// while
																	// played

	public static final String WRITE_SCORE_TO_IMAGE_PROPERTY = "writeToScore";
	public static final String WRITE_SCORE_TO_MIDI_PROPERTY = "writeScoreToMidi";

	public static final String TITLE_PROPERTY = "setTitle";
	public static final String INFO_LABEL_PROPERTY = "setInfoLabelText";

	public static final String BPM_PROPERTY = "setBPM";
	public static final String TONART_PROPERTY = "setTonart";
	public static final String CLEF_PROPERTY = "setClef";
	public static final String TAKTART_PROPERTY = "setTaktart";
	public static final String TRANSPOSE_PROPERTY = "transpose";
	public static final String TRANSPOSE_REC_PROPERTY = "setTransposeRecording";
	public static final String METRO_PROPERTY = "setMetroSelected";
	public static final String PLAYBACK_PROPERTY = "setPlaybackSelected";
	public static final String PLOTTING_PROPERTY = "setPlottingSelected";
	public static final String LOWPASS_PROPERTY = "setLowPassEnabled";
	public static final String CHUNK_PROPERTY = "setChunk";
	public static final String OVERLAP_PROPERTY = "setOverlap";
	public static final String PDA_PROPERTY = "setPDA";
	public static final String YIN_THRESHOLD_PROPERTY = "setYinThreshold";
	public static final String MPM_THRESHOLD_PROPERTY = "setMpmThreshold";
	public static final String MINDUR_PROPERTY = "setMinDuration";
	public static final String MINLEV_PROPERTY = "setMinLevel";

	public static final String SCROLL_DOWN_PROPERTY = "scrollDown";

	// chromatic tuner
	public static final String CHROMATIC_TUNER_START_PROPERTY = "startChromaticTuner";
	public static final String CHROMATIC_TUNER_STOP_PROPERTY = "stopChromaticTuner";
	public static final String UPDATE_TUNER_PROPERTY = "dummy";
	
	public static final String CTRLZ_PROPERTY = "ctrlz";

	public static final String METRO_SHOW_PROPERTY = "showMetro";
	public static final String METRO_START_PROPERTY = "startMetro";
	public static final String METRO_STOP_PROPERTY = "stopMetro";

	public static final String INIT_EVALUATION_PROPERTY = "initEvaluation";

	// public static final String LOG_PROPS =
	// "/de/hsa/jam/util/logging.properties";//TODO
	private Handler handler;

	public void initApp() {
//		LOG.log(Level.FINE, "¤HALLO" );
		
		setModelProperty(UPDATE_SCORE_PROPERTY, "");
		setModelProperty(INIT_PROCESSING, "initApp");
	}

	// ----- methoden user actions -----
	public void end() {
		setModelProperty(END_OF_APP_PROPERTY, new Object());
	}

	public void startStopProcessing(ActionEvent e) {
		setModelProperty(START_STOP_PROCESSING_BUTTON_PROPERTY, new Object());
	}

	public void startStopMidi(ActionEvent e) {
		setModelProperty(START_STOP_MIDI_PROPERTY, new Object());
	}

	public void parseAndSetTune(String tuneAsString) {
		setModelProperty(PARSE_AND_SET_TUNE_PROPERTY, tuneAsString);
	}
	public void saveTuneAsString(String tuneAsString) {
		setModelProperty(SAVE_TUNE_PROPERTY, tuneAsString);
	}

	public void inputSelection(String mixerInfo) {
		setModelProperty(SELECT_MIXER_PROPERTY, mixerInfo);
	}

	public void setBPM(int bpm) {
		setModelProperty(BPM_PROPERTY, bpm);
	}

	public void setTonart(String t) {
		setModelProperty(TONART_PROPERTY, t);
	}

	public void setClef(boolean bassClef) {
		setModelProperty(CLEF_PROPERTY, bassClef);
	}

	public void setTaktart(int idx) {
		setModelProperty(TAKTART_PROPERTY, idx);
	}

	public void transpose(int semitones) {
		setModelProperty(TRANSPOSE_PROPERTY, semitones);
	}

	public void setTransposeRecording(int idx) {
		setModelProperty(TRANSPOSE_REC_PROPERTY, idx);
	}

	public void setMetroSelected(boolean sel) {
		setModelProperty(METRO_PROPERTY, sel);
	}

	public void setPlaybackSelected(boolean s) {
		setModelProperty(PLAYBACK_PROPERTY, s);
	}

	public void setPlottingSelected(boolean s) {
		setModelProperty(PLOTTING_PROPERTY, s);
	}

	public void setLowPassEnabled(boolean s) {
		setModelProperty(LOWPASS_PROPERTY, s);
	}

	public void setChunk(int CHUNK) {
		setModelProperty(CHUNK_PROPERTY, CHUNK);
	}

	public void setOverlap(int overlap) {
		setModelProperty(OVERLAP_PROPERTY, overlap);
	}

	public void setPDA(String pda) {
		setModelProperty(PDA_PROPERTY, pda);
	}

	public void setYinThreshold(float t) {
		setModelProperty(YIN_THRESHOLD_PROPERTY, t);
	}

	public void setMpmThreshold(float t) {
		setModelProperty(MPM_THRESHOLD_PROPERTY, t);
	}

	public void setMinDuration(int d) {
		setModelProperty(MINDUR_PROPERTY, d);
	}

	public void setMinLevel(float l) {
		setModelProperty(MINLEV_PROPERTY, l);
	}

	public void loadWavFile(File audioFile) {
		setModelProperty(LOAD_WAV_FILE_PROPERTY, audioFile);
	}

	public void startChromaticTuner() {
		setModelProperty(CHROMATIC_TUNER_START_PROPERTY, new Object());
	}

	public void stopChromaticTuner() {
		setModelProperty(CHROMATIC_TUNER_STOP_PROPERTY, new Object());
	}

	public void showMetronom() {
		setModelProperty(METRO_SHOW_PROPERTY, new Object());
	}

	public void startMetronom(int bpm) {
		setModelProperty(METRO_START_PROPERTY, bpm);
	}

	public void stopMetronom() {
		setModelProperty(METRO_STOP_PROPERTY, new Object());
	}
	public void ctrlz() {
		setModelProperty(CTRLZ_PROPERTY, new Object());
	}

	public void newProject() {
		boolean OK = false;
		int result = JOptionPane.showConfirmDialog(null, "Projekt speichern ?",
				"Neues Projekt", JOptionPane.YES_NO_CANCEL_OPTION);
		if (result == JOptionPane.YES_OPTION) {
			saveDialog();
			OK = true;
		} else if (result == JOptionPane.NO_OPTION) {
			OK = true;
		}

		if (OK) {
			setModelProperty(NEW_PROJECT_PROPERTY, new Object());
		}
	}

	public void openDialogWAV() {
		// TODO --> save paths:
//		JFileChooser ch = new JFileChooser("/Users/fred/Desktop/bachelor/Samples/TRAINING_DATA/Evaluation");
		JFileChooser ch = new JFileChooser(jAM.HOME_PATH + "/jAM/");
		
		ch.setDialogTitle("open wave file");
		CustomFileFilter filter = new CustomFileFilter(new String[] { "wav" },
				"Only .wav Files");
		ch.addChoosableFileFilter(filter);

		int returnVal = ch.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			if (ch.getSelectedFile().getAbsolutePath().contains(".wav")) {
				loadWavFile(new File(ch.getSelectedFile().getAbsolutePath()));
			} else
				setModelProperty(ERROR_PROPERTY, "Nur .wav Dateien !");
		}
	}

	public void openDialog() {
		// TODO --> save paths:
		JFileChooser ch = new JFileChooser(jAM.HOME_PATH);
		ch.setDialogTitle("open jAM project (abc file)");

		CustomFileFilter filter = new CustomFileFilter(new String[] { "jAM", "abc" }, "only .jAM or .abc files");
		ch.addChoosableFileFilter(filter);

		int returnVal = ch.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			if (ch.getSelectedFile().getAbsolutePath().endsWith(".jAM") || ch.getSelectedFile().getAbsolutePath().endsWith(".abc")) {
				loadAbcFile(ch.getSelectedFile());
			} else
				setModelProperty(ERROR_PROPERTY, "Nur .jAM oder .abc Dateien !");
		}
	}
	public void loadAbcFile(File f) {
		String tuneAsString = "", str;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(f));
			while ((str = reader.readLine()) != null) {
				tuneAsString += str + "\n";
			}
			tuneAsString += "\n";

			// System.out.println("READ IN TUNE: \n" + tuneAsString);

			setModelProperty(PARSE_AND_SET_TUNE_PROPERTY, tuneAsString);

		} catch (Exception e) {
			System.err.println("ERROR beim Laden der datei: " + f.getAbsolutePath());
		}
	}

	//TODO noch nich fertig
	public void saveWavDialog() {
		// TODO --> save paths:
		JFileChooser ch = new JFileChooser(jAM.HOME_PATH);
		ch.setDialogTitle("save WaveFile project");

		ch.setDialogType(JFileChooser.SAVE_DIALOG);
		CustomFileFilter filter = new CustomFileFilter(new String[] { "wav" },
				"Only .wav Files");
		ch.addChoosableFileFilter(filter);

		int returnVal = ch.showSaveDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			String name = ch.getSelectedFile().getAbsolutePath();
			if (!name.endsWith(".wav"))
				name += ".wav";
			try {
				setModelProperty(SAVE_WAV_PROPERTY, name);
			} catch (Exception e) {
				e.printStackTrace();
				setModelProperty(ERROR_PROPERTY,
						"Fehler beim Schreiben der WavDatei: " + name);
			}
		}
	}

	public void saveDialog() {
		// TODO --> save paths:
		JFileChooser ch = new JFileChooser(jAM.HOME_PATH);
		ch.setDialogTitle("save jAM project");

		ch.setDialogType(JFileChooser.SAVE_DIALOG);
		CustomFileFilter filter = new CustomFileFilter(new String[] { "jAM" },
				"Only .jAM Files");
		ch.addChoosableFileFilter(filter);

		int returnVal = ch.showSaveDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			String name = ch.getSelectedFile().getAbsolutePath();
			if (!name.endsWith(".jAM"))
				name += ".jAM";
			try {
				setModelProperty(SAVE_PROJECT_PROPERTY, name);
			} catch (Exception e) {
				e.printStackTrace();
				setModelProperty(ERROR_PROPERTY,
						"Fehler beim Schreiben der Datei: " + name);
			}
		}
	}

	public void saveImageDialog() {
		// TODO --> save paths:
		JFileChooser ch = new JFileChooser(jAM.HOME_PATH);
		ch.setDialogTitle("save score as image");

		ch.setDialogType(JFileChooser.SAVE_DIALOG);
		CustomFileFilter filter = new CustomFileFilter(new String[] { "png" },
				"Only .png Files");
		ch.addChoosableFileFilter(filter);

		int returnVal = ch.showSaveDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			String name = ch.getSelectedFile().getAbsolutePath();
			if (!name.endsWith(".png"))
				name += ".png";
			try {
				// view.writeScoreTo(new File(name));
				setModelProperty(WRITE_SCORE_TO_IMAGE_PROPERTY, name);
			} catch (Exception e) {
				e.printStackTrace();
				setModelProperty(ERROR_PROPERTY,
						"Fehler beim Speichern des Bildes: " + name);
			}
		}
	}

	public void saveMIDIDialog() {
		// TODO --> save paths:
		JFileChooser ch = new JFileChooser(jAM.HOME_PATH);
		ch.setDialogTitle("save as MIDI file");

		ch.setDialogType(JFileChooser.SAVE_DIALOG);
		CustomFileFilter filter = new CustomFileFilter(new String[] { "mid" },
				"Only .mid Files");
		ch.addChoosableFileFilter(filter);

		int returnVal = ch.showSaveDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			String name = ch.getSelectedFile().getAbsolutePath();
			if (!name.endsWith(".mid"))
				name += ".mid";
			try {
				setModelProperty(WRITE_SCORE_TO_MIDI_PROPERTY, name);
			} catch (Exception e) {
				e.printStackTrace();
				setModelProperty(ERROR_PROPERTY,
						"Fehler beim Schreiben der MIDI Datei: " + name);
			}
		}
	}

	public void initEvaluation() {
		Evaluator eval = new Evaluator();
		if (!eval.connectToDB()) {
			setModelProperty(ERROR_PROPERTY,
					"Fehler beim Verbinden mit der Datenbank");
			return;
		}
		setModelProperty(INIT_EVALUATION_PROPERTY, eval);
	}
}
