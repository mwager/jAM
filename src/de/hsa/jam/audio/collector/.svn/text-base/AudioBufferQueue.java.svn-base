package de.hsa.jam.audio.collector;

import java.util.LinkedList;

/**
 * Class implements a blocking AudioBuffer - Queue (FIFO) which holds float arrays
 *  
 * @author Michael Wager
 */
public class AudioBufferQueue {
	//LinkedList<float[][]> queue = new LinkedList<float[][]>();
	LinkedList<float[]> queue = new LinkedList<float[]>();

	public synchronized void add(float[] audioFloatBuffer) {
		// ende - bedingung
		if (audioFloatBuffer == null) {
//			queue.addLast(new float[][] { null, new float[] { -1 } });
			queue.addLast(null);
			return;
		}

		float[] clone = audioFloatBuffer.clone();
//		queue.addLast(new float[][] { clone, new float[] { level } });
		queue.addLast(clone);
		// queue.addLast(new float[][]{audioFloatBuffer, new float[]{level}});
		notify();
	}

	public synchronized float[] get() throws InterruptedException {
		// long start = System.currentTimeMillis();
		while (queue.isEmpty()) { // solange nichts da ist.. warte
			wait(); // wartet auf notify
			// System.out.println("WAITING!!!!!");
		}
		// System.out.println("==================================== WAITED: " +
		// (System.currentTimeMillis() - start));
		return queue.removeFirst();
	}

	public void clear() {
		queue.clear();
	}

	public int size() {
		return queue.size();
	}
}
