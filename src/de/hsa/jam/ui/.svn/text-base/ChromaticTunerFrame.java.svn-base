package de.hsa.jam.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.geom.Line2D;
import java.beans.PropertyChangeEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import be.hogent.tarsos.sampled.pitch.Pitch;
import be.hogent.tarsos.sampled.pitch.PitchUnit;

import de.hsa.jam.ControllerEngine;
import de.hsa.jam.util.jAMUtils;

/**
 * Class implements a JFrame with a basic chromatic tuner.
 *  
 * @author Michael Wager
 */
public class ChromaticTunerFrame extends AbstractView {
	private final ControllerEngine controller;
	private JFrame jframe;
	private PaintPanel paintPanel;

	public ChromaticTunerFrame(final ControllerEngine controller) {
		this.controller = controller;

		jframe = new JFrame("Chromatischer Tuner");
		jframe.addWindowListener(new WindowListener() {
			public void windowOpened(WindowEvent arg0) {
			}

			public void windowIconified(WindowEvent arg0) {
			}

			public void windowDeiconified(WindowEvent arg0) {
			}

			public void windowDeactivated(WindowEvent arg0) {
			}

			public void windowClosing(WindowEvent arg0) {
				controller.stopChromaticTuner();
			}

			public void windowClosed(WindowEvent arg0) {
			}

			public void windowActivated(WindowEvent arg0) {
			}
		});

		paintPanel = new PaintPanel();
		jframe.add(paintPanel);
		jframe.setSize(400, 200);
	}

	/**
	 * Called by the controller when it needs to pass along a property change
	 * from a model. Note that the method checks each GUI parameter to determine
	 * if the current value is already equal to the incoming value. If it is,
	 * the method will not reset the value. This is done to prevent looping from
	 * occurring when a model property is reset.
	 * 
	 * @param evt
	 *            The property change event
	 */
	public void modelPropertyChange(final PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(
				ControllerEngine.CHROMATIC_TUNER_START_PROPERTY)) {
			jframe.setVisible(true);
			paintPanel.start();

		} else if (evt.getPropertyName().equals(
				ControllerEngine.UPDATE_TUNER_PROPERTY)) {
			// System.out.println((String)evt.getNewValue());
			// this.currentPitch = Float.parseFloat((String)evt.getNewValue());

			// label.setText( ""+evt.getNewValue() );

			paintPanel.updateInfos((String[]) evt.getNewValue());
		}
	}
}

class PaintPanel extends JPanel implements Runnable {
	private Thread t;
	private String[] infos;

	public PaintPanel() {
		super();
		setBackground(new Color(255, 255, 255));
	}

	public void updateInfos(String[] str) {
		this.infos = str;
	}

	public void start() {
		t = new Thread(this);
		t.setName("ChromaticTunerPainterThread");
		t.start();
	}

	public void stop() {
		if (t != null)
			t.interrupt();
		t = null;
	}

	public void run() {
		while (t != null) {
			repaint();
			jAMUtils.sleep(150);
		}
	}

	/**
	 * <pre>
	 * 	 	<b>Funktionsweise:</b>
	 * 		 Beispiel:
	 * 		 ----------
	 * 		Pitch: 101.9  --> erkannter Pitch (G#2)
	 * 		Ideal: 103.83 --> idealer Pitch bezogen auf erkannten 
	 * 		Min: 100.93   --> MinPitch welcher noch zu G#2 gehšrt
	 * 		Max: 106.84   --> MaxPitch welcher noch zu G#2 gehšrt
	 * 	
	 * 		100.93      103.83       106.84   --> pitchRange
	 * 		0	        200	     400      	  --> breite des fensters!
	 * 		
	 * 		dann: Max - Min = 106.84-100.93 = 5.91 (pitchRangeDif)
	 * 		also von 0   bis   5.91/2   bis 5.91
	 * 		==> 0        2.95     5.91
	 * 						
	 * 		NUN:
	 * 		Pitch1:  Pitch1 - Min   =  101.9 - 100.93 = 0.97000
	 * 				0.97000 * 400/5.91 = 65.651
	 * </pre>
	 */
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int w = this.getWidth(), h = this.getHeight();

		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setStroke(new BasicStroke(1f));
		Font font = new Font("Comic Sans MS", Font.PLAIN, 13);
		g2.setFont(font);
		g2.setColor(new Color(0, 0, 0));// black

		if (infos == null)
			return;

		float pitch = Float.parseFloat(infos[1]);
		float idealPitch = Float.parseFloat(infos[2]);
		String noteOfIdealPitch = Pitch
				.getInstance(PitchUnit.HERTZ, idealPitch).noteName();
		double x = w / 2;

		if (pitch > 0) {
			double lowestPossible = Pitch.getInstance(PitchUnit.HERTZ, pitch)
					.getLowestPossible();
			double highestPossible = Pitch.getInstance(PitchUnit.HERTZ, pitch)
					.getHighestPossible();
			// System.out.println("Pitch: " + pitch + " Ideal: " + idealPitch +
			// " Min: " + lowestPossible + " Max: " + highestPossible);
			// System.out.println(0 + "\t" + w/2 + "\t" + w);

			double pitchRangeDif = highestPossible - lowestPossible;

			// die rote Linie berechnen
			x = (pitch - lowestPossible) * w / pitchRangeDif;
			// System.out.println(x);
		} else
			x = 0; // ganz links wenn nichts erkannt

		// Die Note und idealPitch oben:
		if (infos[0].equals("C-1"))
			g2.drawString("NO PITCH", w / 2 - 30, 15);
		else
			g2.drawString(infos[0] + " at " + pitch + "Hz - IDEAL: "
					+ idealPitch + "Hz", w / 2 - 100, 15);

		// die Mittellinie:
		g2.draw(new Line2D.Double(0, h / 2.0, w, h / 2.0));

		// Mittelstrich (damit man wei§ wo Mitte is)
		g2.draw(new Line2D.Double(w / 2.0, (h / 2) - 50, w / 2.0, (h / 2) + 50));

		// ideale Note in Mitte unter die Linie:
		g2.drawString(noteOfIdealPitch.equals("C1") ? "" : noteOfIdealPitch,
				w / 2 - 7, h / 2 + 40);

		// Die rote Linie malen
		g2.setColor(Color.RED);
		g2.draw(new Line2D.Double(x, (h / 2) - 30, x, (h / 2) + 30));
	}
}