package de.hsa.jam.audio;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import be.hogent.tarsos.sampled.AudioProcessor;
import be.hogent.tarsos.sampled.SampledAudioUtilities;

/**
 * Simple WaveFileWriter. After reading buffer from soundcard, write to filename.
 *
 * @author Michael Wager
 */
public final class WaveFileWriter implements AudioProcessor {

	private SourceDataLine line;

	private ByteArrayOutputStream out;
	private AudioFormat format;

	/**
	 * The overlap and step size defined not in samples but in bytes. So it
	 * depends on the bit depth. Since the integer data type is used only
	 * 8,16,24,... bits or 1,2,3,... bytes are supported.
	 */
	private final int byteOverlap, byteStepSize;
	private String filename;

	private Vector<byte[]> buffer;

	public WaveFileWriter(final AudioFormat format, final int bufferSize,
			final int overlap, String filename) throws LineUnavailableException {
		out = new ByteArrayOutputStream();
		line = SampledAudioUtilities.getOpenLineFromConfiguredMixer(format);
		// overlap in samples * nr of bytes / sample = bytes overlap
		this.byteOverlap = overlap * format.getFrameSize();
		this.byteStepSize = bufferSize * format.getFrameSize() - byteOverlap;
		this.filename = filename;
		this.format = format;
		// buffer=new Vector<byte[]>();
	}

	Thread thread;
	float[] audioFloatBuffer;
	byte[] audioByteBuffer;

	public void processFull(final float[] audioFloatBuffer,
			final byte[] audioByteBuffer) {
		// buffer.add(audioByteBuffer); //store
		try {
			out.write(audioByteBuffer, 0, audioByteBuffer.length);
		} catch (Exception e) {
			System.err.println("ERROR In WaveFileWriter");
		}// TODO
	}

	public void processOverlapping(final float[] audioFloatBuffer,
			final byte[] audioByteBuffer) {
		// buffer.add(audioByteBuffer); //store
		try {
			out.write(audioByteBuffer, byteOverlap, byteStepSize);
		} catch (Exception e) {
			System.err.println("ERROR In WaveFileWriter");
		}// TODO
	}

	public void processingFinished() {
		// cleanup
		line.drain();
		line.close();

		// default nach jeder Aufnahme:
		writeToDisk(this.filename);
	}

	public void saveToDisk(String name) {
		writeToDisk(name);
	}

	private void writeToDisk(String name) {
		try {
			out.flush();
			out.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		AudioInputStream audioInputStream;
		byte audioBytes[] = out.toByteArray();
		// jAMUtils.printArray(audioBytes);

		ByteArrayInputStream bais = new ByteArrayInputStream(audioBytes);
		audioInputStream = new AudioInputStream(bais, format, audioBytes.length
				/ format.getFrameSize());

		// long milliseconds = (long)((audioInputStream.getFrameLength() * 1000)
		// / audioInputStream.getFormat().getFrameRate());
		// double duration = milliseconds / 1000.0;
		// System.out.println("Captured " + duration +
		// " seconds of audio data, now writing to wave..."); //TODO tmpdir

		try {
			audioInputStream.reset();
		} catch (Exception ex) {
			ex.printStackTrace();
			return;
		}

		File file = new File(name);
		try {
			if (AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE,
					file) == -1) {
				throw new IOException("Problems writing to file");
			}

//			System.out.println("WRITTEN!!!!!!");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
