/**
 * 
 */
package de.hsa.jam.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.geom.Line2D;
import java.util.Vector;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.Line;
import javax.swing.JFrame;
import javax.swing.JPanel;

import de.hsa.jam.audio.AudioFloatConverter;
import de.hsa.jam.util.jAMUtils;

/**
 * Simple JFrame to plot float arrays in a thread
 *
 * @author Michael Wager
 */
public class SimplePlotterFrame extends JPanel implements Runnable {

	private boolean PLOTTING, CRITICAL_SECTION = false;
	private Thread thread;

	private float[] data;
	private String info = "INFO";

//	private AudioFloatConverter converter;
	
	/**
	 * 
	 */
	public SimplePlotterFrame(String title, int y) {
		JFrame f = new JFrame();
		f.setBackground(Color.WHITE);
		f.setBounds(0, y, 700, 200);
		f.setTitle(title);
		f.add(this);

		setData(new float[] {}); // dummmy
		f.addWindowListener(new WindowListener() {
			public void windowOpened(WindowEvent arg0) {
			}

			public void windowIconified(WindowEvent arg0) {
			}

			public void windowDeiconified(WindowEvent arg0) {
			}

			public void windowDeactivated(WindowEvent arg0) {
			}

			public void windowClosing(WindowEvent arg0) {
				stop();
				System.out.println("STOPPED PLOTTER");
			}

			public void windowClosed(WindowEvent arg0) {
			}

			public void windowActivated(WindowEvent arg0) {
			}
		});
		f.setVisible(true);
		f.setFocusable(false);
		
//		converter = AudioFloatConverter.getConverter(format);
		
	}

	public void start() {
		thread = new Thread(this);
		thread.setName("Metronom");
		thread.start();
	}

	public void stop() {
		if (thread != null)
			thread.interrupt();
		thread = null;
		PLOTTING = false;
	}

	public void setData(byte[] data1) {
//		converter.toFloatArray(audioByteBuffer, audioFloatBuffer);
		float [] data = new float[data1.length];
		for (int i = 0; i < data.length; i++) {
			data[i] = data1[i];
		}
		// while(CRITICAL_SECTION){};//System.out.println("PLOTTER BUSY WAITING");}
		this.data = data;
	}
	
	
	public void setData(float[] data) {
		// while(CRITICAL_SECTION){};//System.out.println("PLOTTER BUSY WAITING");}
		this.data = data;
	}

	// public void addPoint(int midiKey) {
	// midiKeys.add(midiKey);
	// }

	public void run() {
		PLOTTING = true;

		while (PLOTTING) {
			repaint();
			jAMUtils.sleep(100);
		}
	}

	public void paint(Graphics g) {
		super.paint(g);

		int w = this.getSize().width;
		int h = this.getSize().height;

		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.BLACK);
		g2.draw(new Line2D.Double(0, h / 2, w, h / 2));
		g2.draw(new Line2D.Double(50, 0, 50, h));

		// ##### plot signal relative to width and heigth, every buffer(see
		// setDataOld)
		g2.setColor(Color.BLUE);
		float X = 0, Y = 0, OLD_X = 50, OLD_Y = h / 2;

		// ab hier darf data nicht geschrieben werden
		// CRITICAL_SECTION=true; naja.. erstmal besser als busy waiting
		float maxVal = jAMUtils.findMax(data)[1];
		for (int i = 0; i < data.length; i++) {
			X = 50 + i * w / data.length;// (float)i/(float)w;
			Y = (h / 2) - data[i] * (h / 2) / maxVal;
			g2.draw(new Line2D.Double(OLD_X, OLD_Y, X, Y));
			OLD_X = X;
			OLD_Y = Y;
		}
		// CRITICAL_SECTION=false;

		g.drawString(info + "          BufferSize: " + data.length, 60,
				h / 2 + 50);

		// ##### draw MidiKeys Ÿber buffer
		// for (int i = 0; i < midiKeys.size(); i++) {
		// X=i+1;
		// Y=h/2 - midiKeys.get(i);
		// System.out.println(X);
		// g2.draw(new Line2D.Double(OLD_X, OLD_Y, X, Y));
		// OLD_X=X;
		// OLD_Y=Y;
		// }

	}

	public void setInfoString(String info) {
		this.info = info;
	}

	/**
	 * @param args
	 */
	// public static void main(String[] args) {
	// SimplePlotter p = new SimplePlotter("TESTING...);
	// p.start();
	//
	// float[]arr=new float[1000];
	// for (int i = 0; i < 1000; i++) {
	// arr[i] = (float)Math.random();
	// }
	// p.setData(arr);
	// p.setInfoString("TOLLE INFO!");
	//
	// }

}
