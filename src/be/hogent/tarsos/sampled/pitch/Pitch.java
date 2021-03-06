package be.hogent.tarsos.sampled.pitch;

/**
 * A class representing pitch. Can be used to convert pitch units or to base
 * pitch interval calculations on.
 * 
 * @author Joren Six
 */
public final class Pitch {

	/**
	 * The pitch in Hz.
	 */
	private/* final */double pitchInHertz;

	/**
	 * Create a new pitch object with a certain pitch.
	 * 
	 * @param hertzValue
	 *            The Pitch in Hertz.
	 */
	private Pitch(final double hertzValue) {
		this.pitchInHertz = hertzValue;
	}

	/**
	 * Get the pitch value in a given unit.
	 * 
	 * @param unit
	 *            The requested unit.
	 * @return A pitch value in the requested unit.
	 */
	public double getPitch(final PitchUnit unit) {
		double value = 0.0;
		switch (unit) {
		case ABSOLUTE_CENTS:
			value = PitchConverter.hertzToAbsoluteCent(pitchInHertz);
			break;
		case RELATIVE_CENTS:
			value = PitchConverter.hertzToRelativeCent(pitchInHertz);
			break;
		case MIDI_KEY:
			value = PitchConverter.hertzToMidiKey(pitchInHertz);
			break;
		case MIDI_CENT:
			value = PitchConverter.hertzToMidiCent(pitchInHertz);
			break;
		case HERTZ:
			value = pitchInHertz;
			break;
		default:
			throw new AssertionError("Unsupported unit: " + unit.name());
		}
		return value;
	}

	/**
	 * Calculates which octave this pitch falls into. The octave index is based
	 * on MIDI keys. Keys [0,11] fall in octave -1, [11,23] in octave 0,...
	 * 
	 * @return The octave the pitch falls into, calculated using MIDI key.
	 * @exception IllegalArgumentException
	 *                If the pitch does not fall within the range of valid MIDI
	 *                KEYS.
	 */
	public int octaveIndex() {
		final int midiKey = (int) getPitch(PitchUnit.MIDI_KEY);
		return midiKey / 12 - 1;
	}

	/**
	 * Returns the name of the MIDI key corresponding to the given hertzValue.
	 * The MIDI key is the key returned by the convertHertzToMidiKey method.
	 * 
	 * @return A note name like C3, A4 or A3#/B3b.
	 * @exception IllegalArgumentException
	 *                When the hertzValue is outside the valid MIDI key range.
	 */
	public String noteName() {
		String name = "";
		// The x is replaced by the octave index
		
		// fred jAM edited 4-1-2011
		final String[] noteNamesFlats = { "Cx", "Dbx", "Dx", "Ebx", "Ex", "Fx",
				"Gbx", "Gx", "Abx", "Ax", "Bbx", "Bx", };

		final String[] noteNamesSharp = { "Cx", "C#x", "Dx", "D#x", "Ex", "Fx",
				"F#x", "Gx", "G#x", "Ax", "A#x", "Bx", };

		final int midiKey = (int) getPitch(PitchUnit.MIDI_KEY);
		// System.out.println("midiKey: " + midiKey);
		final int noteIndex = midiKey % 12;
		// System.out.println(noteIndex);
		final int octaveIndex = octaveIndex();

		name = noteNamesSharp[noteIndex].replace("x",
				Integer.toString(octaveIndex));
		// name = noteNamesFlats[noteIndex].replace("x",
		// Integer.toString(octaveIndex));
		return name;
	}

	@Override
	public String toString() {
		return String.valueOf(pitchInHertz);
	}

	/**
	 * A pitch is seen as a western musical pitch if it is less than 15 cents
	 * removed from the 'correct' pitch. The correct pitch is tuned using A4 =
	 * 440Hz.
	 * 
	 * @return True if the pitch is western and musical, false otherwise.
	 */
	public boolean isWesternMusicalPitch() {
		final double midiCent = getPitch(PitchUnit.MIDI_CENT);
		final double midiKey = getPitch(PitchUnit.MIDI_KEY);
		return Math.abs(midiCent - (int) midiKey) < 0.15;
	}

	/**
	 * Return a new pitch object using value in a certain unit.
	 * 
	 * @param unit
	 *            The unit of the pitch value.
	 * @param value
	 *            The value Itself.
	 * @return A new Pitch object.
	 * @exception IllegalArgumentException
	 *                If RELATIVE_CENTS is given: Cannot convert relative cent
	 *                value to absolute frequency.
	 */
	public static Pitch getInstance(final PitchUnit unit, final double value) {
		double hertzValue = Double.MAX_VALUE;
		switch (unit) {
		case ABSOLUTE_CENTS:
			hertzValue = PitchConverter.absoluteCentToHertz(value);
			break;
		case RELATIVE_CENTS:
			throw new IllegalArgumentException(
					"Cannot convert relative cent value to absolute "
							+ "frequency. Pitch object creation failed.");
		case MIDI_KEY:
			hertzValue = PitchConverter.midiKeyToHertz((int) value);
			break;
		case MIDI_CENT:
			hertzValue = PitchConverter.midiCentToHertz(value);
			break;
		case HERTZ:
			hertzValue = value;
			break;
		default:
			throw new AssertionError("Unsupported unit: " + unit.name());
		}
		return new Pitch(hertzValue);
	}

	
	
	/************************ changes for jAM *************************/
	// jAM
	public double getLowestPossible() {
		String name = noteName();
		double lastPitch = 0;

		for (double i = pitchInHertz; i > 0; i -= 0.1) {
			Pitch tmp = Pitch.getInstance(PitchUnit.HERTZ, i);
			// System.out.println(i + " - " + tmp.noteName() + " - " + name);

			if (!name.equals(tmp.noteName()))
				return lastPitch;

			lastPitch = i;
		}
		return -1;
	}

	// jAM
	public double getHighestPossible() {
		String name = noteName();
		double lastPitch = 0;

		for (double i = pitchInHertz; i < pitchInHertz + 100; i += 0.1) {
			Pitch tmp = Pitch.getInstance(PitchUnit.HERTZ, i);
			// System.out.println(i + " - " + tmp.noteName() + " - " + name);

			if (!name.equals(tmp.noteName()))
				return lastPitch;

			lastPitch = i;
		}
		return -1;
	}
	// public static void main(String[] args) {
	// Pitch p = Pitch.getInstance(PitchUnit.HERTZ, 440);
	// System.out.println(p.getLowestPossible());
	//
	// System.out.println(p.getHighestPossible());
	//
	// }

	// jAM
	public float getIdealFreq(String note, int octave) {
		float baseFreq = 0; // fn = f0 * 1**(1/12.0)
		if (note.equals("C")) {
			baseFreq = 261.64f;
		} else if (note.equals("Db")) {
			baseFreq = 277.2f;
		} else if (note.equals("D")) {
			baseFreq = 293.68f;
		} else if (note.equals("Eb")) {
			baseFreq = 311.12f;
		} else if (note.equals("E")) {
			baseFreq = 329.64f;
		} else if (note.equals("F")) {
			baseFreq = 349.24f;
		} else if (note.equals("Gb")) {
			baseFreq = 370.0f;
		} else if (note.equals("G")) {
			baseFreq = 392.0f;
		} else if (note.equals("Ab")) {
			baseFreq = 415.32f;
		} else if (note.equals("A")) {
			baseFreq = 440.0f;
		} else if (note.equals("Bb")) {
			baseFreq = 466.16f;
		} else if (note.equals("B")) {
			baseFreq = 493.92f;
		}

		return (float) (baseFreq * Math.pow(2.0, octave - 3));
	}

	// jAM
	public String getBaseNote(float freq) {
		float lognote = (float) (Math.log((double) freq) / Math.log((double) 2)); // log2(freq)
		float octave = (float) Math.floor((double) lognote);
		lognote = lognote - octave;
		String note = "C";

		if (lognote >= 0.99 || lognote < 0.07)
			note = "C";
		else if (lognote >= 0.07 && lognote < 0.16)
			note = "Db";
		else if (lognote >= 0.16 && lognote < 0.24)
			note = "D";
		else if (lognote >= .24 && lognote < .32)
			note = "Eb";
		else if (lognote >= .32 && lognote < .41)
			note = "E";
		else if (lognote >= .41 && lognote < .49)
			note = "F";
		else if (lognote >= .49 && lognote < .57)
			note = "Gb";
		else if (lognote >= .57 && lognote < .66)
			note = "G";
		else if (lognote >= .66 && lognote < .74)
			note = "Ab";
		else if (lognote >= .74 && lognote < .82)
			note = "A";
		else if (lognote >= .82 && lognote < .91)
			note = "Bb";
		else if (lognote >= .91 && lognote < .99)
			note = "B";

		octave = octave - 5;

		return note + " " + (int) octave;
	}

	// jAM
	public void convertPitch(int nOctavesUp) {
		/**
		 * Frequenzberechnung: f(i) = f0 * 2^(i/12.0) f0 ist die BasisFrequenz,
		 * zb 440.0 i steht fuer einen Halbtonschritt Bsp: Freq von Ais? f(1) =
		 * 440 * 2^(1/12.0) = 466,16
		 */
		this.pitchInHertz *= Math.pow(2, (nOctavesUp / 12.0f));
	}
}
