package de.hsa.jam.audio.midi;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import de.hsa.jam.jAM;

/**
 * This class implements a simple MIDI-based metronome
 *  
 * @author Michael Wager
 */
public class MidiMetronome extends JPanel {
	// private Logger LOG = Logger.getLogger(MidiMetronome.class.getName());

	private Sequence sequence;
	private MidiPlayer player;

	public MidiMetronome(int bpm) { // TODO Listener style wie im hicosim
		init(bpm); // nur einmal, kein open() mehr

		// LOG.info("MidiMetronome init");
	}

	public int getBPM() {
		return this.player.getBPM();
	}

	public void setBPM(int bpm) {
		this.player.setBPM(bpm);
	}

	private void init(int bpm) {
		int ticksPerBeat = 500000;
		int instrument_channel = 9;// midi channel
		int instrument = 76;// wood click

		ShortMessage s1 = new ShortMessage();
		ShortMessage s2 = new ShortMessage();
		Track track = null;

		try {
			sequence = new Sequence(Sequence.PPQ, ticksPerBeat);
			track = sequence.createTrack();

			s1.setMessage(ShortMessage.NOTE_ON, instrument_channel, instrument,
					127);
			s2.setMessage(ShortMessage.NOTE_OFF, instrument_channel,
					instrument, 127);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
		track.add(new MidiEvent(s1, 0));
		track.add(new MidiEvent(s2, ticksPerBeat));

		player = new MidiPlayer();
		player.setBPM(bpm);
	}

	// public MidiPlayer getPlayer() {
	// return player;
	// }
	public void start() {
		player.play(sequence, true);
	}

	public void stop() {
		player.stop();
	}

	// public static void main(String[] args)throws Exception {
	// // JFrame f = new JFrame("Metronom");
	// // f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	// // f.setLayout(new FlowLayout());
	// //
	// // MidiMetronomPanel m = new MidiMetronomPanel(null);
	// // f.add(m);
	// //
	// // f.setSize(400, 300);
	// // f.setVisible(true);
	// // f.setResizable(false);
	// //
	// // //START
	// // m.setBPM(200);
	// // m.start();
	//
	// long start = System.currentTimeMillis();
	//
	//
	// // player.play(sequence, true);
	// // //TODO kein thread mehr notwendig ??? !!! fuer metronome da
	// sequenceer.start() !!!
	// //
	// // System.out.println("ok");
	// //
	// //
	// // System.out.println(System.currentTimeMillis()-start);
	// }
}

class MidiPlayer implements MetaEventListener {

	// Midi meta event
	public static final int END_OF_TRACK_MESSAGE = 47;

	private Sequencer sequencer;

	private boolean loop;

	private boolean paused;

	/**
	 * Creates a new MidiPlayer object.
	 */
	public MidiPlayer() {
		try {
			sequencer = MidiSystem.getSequencer();
			sequencer.setSlaveSyncMode(Sequencer.SyncMode.INTERNAL_CLOCK);
			sequencer.setTempoInBPM(60); // default
			sequencer.open();
			sequencer.addMetaEventListener(this);
		} catch (MidiUnavailableException ex) {
			sequencer = null;
		}
	}

	/**
	 * Loads a sequence from the file system. Returns null if an error occurs.
	 */
	public Sequence getSequence(String filename) {
		try {
			return getSequence(new FileInputStream(filename));
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * Loads a sequence from an input stream. Returns null if an error occurs.
	 */
	public Sequence getSequence(InputStream is) {
		try {
			if (!is.markSupported()) {
				is = new BufferedInputStream(is);
			}
			Sequence s = MidiSystem.getSequence(is);
			is.close();
			return s;
		} catch (InvalidMidiDataException ex) {
			ex.printStackTrace();
			return null;
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * Plays a sequence, optionally looping. This method returns immediately.
	 * The sequence is not played if it is invalid.
	 */
	public void play(Sequence sequence, boolean loop) {

		// sequencer.addControllerEventListener(new ControllerEventListener() {
		//
		// public void controlChange(ShortMessage arg0) {
		// System.out.println("??? - " + arg0.toString());
		//
		// }
		// },new int[]{0} );

		// System.out.println("METRO START PLAYING, TIME: " +
		// (System.currentTimeMillis()-jAM.GLOBAL_TIMESTAMP) );

		if (sequencer != null && sequence != null && sequencer.isOpen()) {
			try {
				sequencer.setSequence(sequence);

				// System.out.println(sequencer.getTickLength());
				// System.out.println(sequencer.getTickPosition());

				if (loop)
					sequencer.setLoopCount(sequencer.LOOP_CONTINUOUSLY);

				sequencer.start();

				// System.out.println(sequencer.getMicrosecondPosition());

				this.loop = loop;
			} catch (InvalidMidiDataException ex) {
				ex.printStackTrace();
			}
		}
	}

	public void setBPM(int bpm) {
		sequencer.setTempoInBPM(bpm);
	}

	public int getBPM() {
		return (int) this.sequencer.getTempoInBPM();
	}

	/**
	 * This method is called by the sound system when a meta event occurs. In
	 * this case, when the end-of-track meta event is received, the sequence is
	 * restarted if looping is on.
	 */
	public void meta(MetaMessage event) {

		if (event.getType() == END_OF_TRACK_MESSAGE) {
			if (sequencer != null && sequencer.isOpen() && loop) {
				sequencer.start();
			}
		}
	}

	/**
	 * Stops the sequencer and resets its position to 0.
	 */
	public void stop() {
		// System.out.println("stop() " + sequencer.getMicrosecondLength());

		if (sequencer != null && sequencer.isOpen()) {
			sequencer.stop();
			sequencer.setMicrosecondPosition(0);
		}
	}

	/**
	 * Closes the sequencer.
	 */
	public void close() {
		if (sequencer != null && sequencer.isOpen()) {
			sequencer.close();
		}
	}

	/**
	 * Gets the sequencer.
	 */
	public Sequencer getSequencer() {
		return sequencer;
	}

	/**
	 * Sets the paused state. Music may not immediately pause.
	 */
	public void setPaused(boolean paused) {
		if (this.paused != paused && sequencer != null && sequencer.isOpen()) {
			this.paused = paused;
			if (paused) {
				sequencer.stop();
			} else {
				sequencer.start();
			}
		}
	}

	/**
	 * Returns the paused state.
	 */
	public boolean isPaused() {
		return paused;
	}

}