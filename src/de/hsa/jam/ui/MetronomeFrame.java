package de.hsa.jam.ui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import de.hsa.jam.ControllerEngine;
import de.hsa.jam.jAM;
import de.hsa.jam.audio.midi.MidiMetronome;

/**
 * This class implements a JFrame with a basic metronome.
 *
 * @author Michael Wager
 */
public class MetronomeFrame extends AbstractView {
	private final ControllerEngine controller;
	private JFrame jframe;

	private MidiMetronome metro;

	private final JTextField bpmField;

	public MetronomeFrame(final ControllerEngine controller) {
		this.controller = controller;

		jframe = new JFrame("Metronom");
		jframe.setLayout(new FlowLayout());
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
				controller.stopMetronom();
				bpmField.setText("60");
			}

			public void windowClosed(WindowEvent arg0) {
			}

			public void windowActivated(WindowEvent arg0) {
			}
		});

		final JLabel l = new JLabel("Beats per minute eingeben und ENTER!");
		jframe.add(l);

		bpmField = new JTextField(10);
		bpmField.setText("60");
		bpmField.setToolTipText("Beats per minute (hat nichts zu tun mit den BPM in 'Einstellungen'!)");
		bpmField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					controller.startMetronom(Integer.parseInt(bpmField
							.getText()));
				} catch (Exception e) {
					jAM.log(e.getMessage(), true);
					JOptionPane.showMessageDialog(jframe,
							"Fehler: " + e.getMessage());
				}
			}
		});
		jframe.add(bpmField);

		JButton b = new JButton("stop");
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.stopMetronom();
			}
		});
		jframe.add(b);

		jframe.setSize(400, 100);
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
		if (evt.getPropertyName().equals(ControllerEngine.METRO_SHOW_PROPERTY)) {
			jframe.setVisible(true);
		}
	}
}
