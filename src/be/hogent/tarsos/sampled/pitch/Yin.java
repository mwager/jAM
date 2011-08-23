package be.hogent.tarsos.sampled.pitch;

/**
 * An implementation of the AUBIO_YIN pitch tracking algorithm. See <a href=
 * "http://recherche.ircam.fr/equipes/pcm/cheveign/ps/2002_JASA_YIN_proof.pdf"
 * >the YIN paper.</a> Implementation based on <a
 * href="http://aubio.org">aubio</a>
 * 
 * @author Joren Six
 * @author Paul Brossier
 */

public final class Yin implements PurePitchDetector {
	/**
	 * The default YIN threshold value. Should be around 0.10~0.15. See YIN
	 * paper for more information.
	 */
	private static final double DEFAULT_THRESHOLD = 0.20;

	/**
	 * The default size of an audio buffer (in samples).
	 */
	public static final int DEFAULT_BUFFER_SIZE = 1024;

	/**
	 * The default overlap of two consecutive audio buffers (in samples).
	 */
	public static final int DEFAULT_OVERLAP = 512;

	/**
	 * The actual YIN threshold.
	 */
	private final double threshold;

	/**
	 * The audio sample rate. Most audio has a sample rate of 44.1kHz.
	 */
	private final float sampleRate;

	/**
	 * The buffer that stores the calculated values. It is exactly half the size
	 * of the input buffer.
	 */
	private final float[] yinBuffer;

	/**
	 * The probability of the last detected pitch.
	 */
	private float probability;

	/**
	 * Create a new pitch detector for a stream with the defined sample rate.
	 * Processes the audio in blocks of the defined size.
	 * 
	 * @param audioSampleRate
	 *            The sample rate of the audio stream. E.g. 44.1 kHz.
	 * @param bufferSize
	 *            The size of a buffer. E.g. 1024.
	 */
	public Yin(final float audioSampleRate, final int bufferSize) {
		this(audioSampleRate, bufferSize, DEFAULT_THRESHOLD);
	}

	/**
	 * Create a new pitch detector for a stream with the defined sample rate.
	 * Processes the audio in blocks of the defined size.
	 * 
	 * @param audioSampleRate
	 *            The sample rate of the audio stream. E.g. 44.1 kHz.
	 * @param bufferSize
	 *            The size of a buffer. E.g. 1024.
	 * @param yinThreshold
	 *            The parameter that defines which peaks are kept as possible
	 *            pitch candidates. See the YIN paper for more details.
	 */
	public Yin(final float audioSampleRate, final int bufferSize,
			final double yinThreshold) {
		this.sampleRate = audioSampleRate;
		this.threshold = yinThreshold;
		yinBuffer = new float[bufferSize / 2];

		/*
		 * jAM optimize YIN ?:
		 * 
		 * 44100/35 == 1260 44100/600 == 73.5 
		 * 
		 **/
	}

	// jAM
	public float[] getCurrentBuffer() {
		return this.yinBuffer;
	}

	/**
	 * The main flow of the YIN algorithm. Returns a pitch value in Hz or -1 if
	 * no pitch is detected.
	 * 
	 * @return a pitch value in Hz or -1 if no pitch is detected.
	 */
	public float getPitch(final float[] audioBuffer) {

		int tauEstimate = -1;
		float pitchInHertz = -1;

		// step 2
		difference(audioBuffer);

		// step 3
		cumulativeMeanNormalizedDifference();

		// step 4
		tauEstimate = absoluteThreshold();

		// System.out.println("tauEstimate : " + tauEstimate);

		// step 5
		if (tauEstimate != -1) {
			final float betterTau = parabolicInterpolation(tauEstimate);

			// System.out.println("nach parabol. interpolation : " + betterTau);

			// step 6
			// TODO Implement optimization for the AUBIO_YIN algorithm.
			// reduce error rate from 0.77% to 0.5%
			// using the data of the YIN paper
//			bestLocalEstimate();

			// conversion to Hz
			pitchInHertz = sampleRate / betterTau;
		}

		return pitchInHertz;
	}

	/**
	 * Implements the difference function as described in step 2 of the YIN
	 * paper.
	 */
	private void difference(final float[] audioBuffer) {
		int index, tau;
		float delta;
		for (tau = 0; tau < yinBuffer.length; tau++) {
			yinBuffer[tau] = 0;
		}
		for (tau = 1; tau < yinBuffer.length; tau++) {
			for (index = 0; index < yinBuffer.length; index++) {
				delta = audioBuffer[index] - audioBuffer[index + tau];
				yinBuffer[tau] += delta * delta;
			}
		}

		// System.out.println("Buffer nach difference(): ");
		// MathUtils.printArray(yinBuffer);

		// System.out.println("##### NACH AUTOKORELATION: #####");
		// MathUtils.printArray(yinBuffer);
	}

	/**
	 * The cumulative mean normalized difference function as described in step 3
	 * of the YIN paper. <br>
	 * <code>
	 * yinBuffer[0] == yinBuffer[1] = 1
	 * </code>
	 */
	// cumulativeMean = Kumulative Häufigkeit oder Summenhäufigkeit!
	private void cumulativeMeanNormalizedDifference() {
		int tau;
		yinBuffer[0] = 1;
		float runningSum = 0;
		for (tau = 1; tau < yinBuffer.length; tau++) {
			runningSum += yinBuffer[tau];
			yinBuffer[tau] *= tau / runningSum;
		}
		// System.out.println("##### NACH Kumulative Häufigkeit - NORMALISIERUNG: #####");
		// MathUtils.printArray(yinBuffer);
	}

	/**
	 * Implements step 4 of the AUBIO_YIN paper.
	 */
	private int absoluteThreshold() {
		// Uses another loop construct
		// than the AUBIO implementation
		int tau;
		// first two positions in yinBuffer are always 1
		// So start at the third (index 2)
		for (tau = 2; tau < yinBuffer.length; tau++) {
			if (yinBuffer[tau] < threshold) {
				while (tau + 1 < yinBuffer.length
						&& yinBuffer[tau + 1] < yinBuffer[tau]) {
					tau++;
				}
				// found tau, exit loop and return
				// store the probability
				// From the YIN paper: The threshold determines the list of
				// candidates admitted to the set, and can be interpreted as the
				// proportion of aperiodic power tolerated
				// within a ëëperiodicíí signal.
				//
				// Since we want the periodicity and and not aperiodicity:
				// periodicity = 1 - aperiodicity
				probability = 1 - yinBuffer[tau];
				break;
			}
		}

		// if no pitch found, tau => -1
		if (tau == yinBuffer.length || yinBuffer[tau] >= threshold) {
			tau = -1;
			probability = 0;
		}

		return tau;
	}

	/**
	 * Implements step 5 of the AUBIO_YIN paper. It refines the estimated tau
	 * value using parabolic interpolation. This is needed to detect higher
	 * frequencies more precisely. See http://fizyka.umk.pl/nrbook/c10-2.pdf and
	 * for more background
	 * http://fedc.wiwi.hu-berlin.de/xplore/tutorials/xegbohtmlnode62.html
	 * 
	 * @param tauEstimate
	 *            the estimated tau value.
	 * @return a better, more precise tau value.
	 */
	private float parabolicInterpolation(final int tauEstimate) {
		final float betterTau;
		final int x0;
		final int x2;

		// System.out.println("freq vor interpol: " + sampleRate/tauEstimate);

		if (tauEstimate < 1) {
			x0 = tauEstimate;
		} else {
			x0 = tauEstimate - 1;
		}
		if (tauEstimate + 1 < yinBuffer.length) {
			x2 = tauEstimate + 1;
		} else {
			x2 = tauEstimate;
		}
		if (x0 == tauEstimate) {
			if (yinBuffer[tauEstimate] <= yinBuffer[x2]) {
				betterTau = tauEstimate;
			} else {
				betterTau = x2;
			}
		} else if (x2 == tauEstimate) {
			if (yinBuffer[tauEstimate] <= yinBuffer[x0]) {
				betterTau = tauEstimate;
			} else {
				betterTau = x0;
			}
		} else {
			float s0, s1, s2;
			s0 = yinBuffer[x0];
			s1 = yinBuffer[tauEstimate];
			s2 = yinBuffer[x2];
			// fixed AUBIO implementation, thanks to Karl Helgason:
			// (2.0f * s1 - s2 - s0) was incorrectly multiplied with -1
			float a1 = (s2 - s0);
			betterTau = tauEstimate + a1 / (2 * (2 * s1 - s2 - s0));
		}

		// System.out.println("freq nach interpol: " + sampleRate/betterTau);
		return betterTau;
	}

	// jAM - step 6 from YIN paper
	private void bestLocalEstimate() {
		//damnit my math prof could not help me, and i dont get it from the paper
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see be.hogent.tarsos.sampled.pitch.PurePitchDetector#getProbability()
	 */
	public float getProbability() {
		return probability;
	}

//	public static void main(String[] args) {
//		float fs = 16;
//		float freq = 5;
//		float sec = 1;
//
//		float[] buf = new float[(int) fs];
//
//		for (int i = 0; i < buf.length; i++) {
//			float t = i / fs;
//			buf[i] = (float) Math.sin(2 * Math.PI * freq * sec * t);
//		}
//
//		System.out.println("Buffer vorher: ");
//		jAMUtils.printArray(buf);
//		Yin yin = new Yin(fs, (int) fs);
//		System.out.println("PITCH: " + yin.getPitch(buf) + "hz");
//		System.out.println(yin.getProbability() * 100 + "%");
//	}
	
	// public static void main(String[] args) {
	// float freq=20.0f;
	// float SR = 256.0f; //Abtastrate muss doppelt so groß wie freq sein!
	//
	// int CHUNK=256;
	//
	// float secs=CHUNK/SR;
	// float PI = (float)Math.PI;
	//
	// float[] f=new float[CHUNK];
	//
	// float t=0;
	// for(int i = 0; i < CHUNK; i++) {
	// f[i] = (float)Math.sin(2*PI * freq * t * secs);
	// t += 1.0f/SR;
	// }
	//
	// System.out.println("##### YIN-TEST: bufferSize: " + CHUNK +
	// " Abtastrate: " + SR + " Freq: " + freq + ". => " + secs +
	// " sekunden ==> sin(2*PI * freq * t * secs)");
	//
	// MathUtils.printArray(f);
	//
	// Yin yin = new Yin(SR, CHUNK);
	// System.out.println("YIN PITCH: " + yin.getPitch(f) + "Hz");
	// System.out.println("YIN WAHRSCHEINLICHKEIT: " + yin.getProbability() +
	// " => " + yin.getProbability()*100.0f + "%" );
	//
	// }
}