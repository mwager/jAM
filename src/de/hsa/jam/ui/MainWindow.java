package de.hsa.jam.ui;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.util.logging.Logger;

import javax.sound.sampled.Mixer;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import abc.notation.MusicElement;
import abc.notation.Tune;
import abc.parser.TuneParser;
import abc.ui.swing.TuneEditorPane;
import be.hogent.tarsos.sampled.SampledAudioUtilities;
import de.hsa.jam.ControllerEngine;

/*	All user events	
 * 		menu:
 * new project			--> actionListener
 * open wav				--> actionListener
 * open project			--> actionListener
 save project			--> actionListener
 saveImage			--> actionListener
 save Midi			--> actionListener
 prefs				--> actionListener
 MainWin:
 help				--> actionListener
 about				--> actionListener
 start/stop capture --> actionListener
 play/stop midi	--> actionListener
 select inputs	--> actionListener
 abc_area --> keyListener

 Preferences:
 bpm        --> actionListener
 Tonart     --> actionListener
 taktart    --> actionListener
 transpose  --> actionListener
 transpRec  --> get
 ---
 metro      --> get
 playback   --> actionListener
 plotting   --> get
 lowPass    --> get
 chunk      --> actionListener
 overlap    --> actionListener
 ---
 yin		   --> actionListener
 mpm        --> actionListener
 minDur     --> actionListener
 minLevel   -->actionListener
 */

/**
 * This class implements the MainFrame of the application.<br />
 * There is a menu, a userpanel with rec/play, the scorepanel and a prefenence
 *  frame
 * 
 * @author Michael Wager
 */
public class MainWindow extends AbstractView {
	private Logger LOG = Logger.getLogger(MainWindow.class.getName());

	private final ControllerEngine controller;

	private PreferenceWindow prefWindow;
	private ScorePanel scorePanel;
	private JScrollPane scroller;

	private JMenuItem newProject, openWAV, open, save, saveWav, saveImage,
			saveMIDI, prefs;
	private JComboBox mic_or_file, inputSelectBox;
	private JLabel infoLabel;
	private JProgressBar prgbar;
	// private JTextArea abc_area;
	private TuneEditorPane abc_area;

	private JButton start, play, playMidi;

	private final int w = 1000; // Toolkit.getDefaultToolkit().getScreenSize().width/2;
	private final int h = Toolkit.getDefaultToolkit().getScreenSize().height;

	private JFrame jframe;
	
	public MainWindow(String title, final float VERSION,
			final ControllerEngine c) {
		this.controller = c;

		jframe = new JFrame();
		jframe.setTitle(title);
		jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		jframe.setLayout(new BorderLayout());

		scorePanel = new ScorePanel();
		prefWindow = new PreferenceWindow(controller);

		// -----------------------------------------------------------------------------------
		// Menubar
		JMenuBar bar = new JMenuBar();
		JMenu file = new JMenu("Datei");
		newProject = new JMenuItem("Neues Projekt... (ctrl + n)");
		newProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.newProject();
			}
		});
		file.add(newProject);

		openWAV = new JMenuItem("Oeffne Wave Datei... (ctrl + o)");
		openWAV.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.openDialogWAV();
			}
		});
		file.add(openWAV);

		open = new JMenuItem(
				"Oeffne .jAM Datei...('gespeichertes Projekt') (ctrl + l)");
		open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.openDialog();
			}
		});
		file.add(open);

		saveWav = new JMenuItem("Speichern als WaveDatei...");
		saveWav.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// controller.saveWavDialog();
				showErrorMessage("Geht noch nicht. Aber nach jeder Aufnahme wird ne WaveDatei in "
						+ System.getProperty("user.home")
						+ "/jAM/jAM_session.wav gespeichert!");
			}
		});
		file.add(saveWav);

		save = new JMenuItem("Speichern als jAM - 'Projekt'... (ctrl + s)");
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.saveDialog();
			}
		});
		file.add(save);

		saveImage = new JMenuItem("Speichern der Noten als Bild...");
		saveImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.saveImageDialog();
			}
		});
		file.add(saveImage);

		saveMIDI = new JMenuItem("Speichern als MIDI Datei...");
		saveMIDI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.saveMIDIDialog();
			}
		});
		file.add(saveMIDI);

		prefs = new JMenuItem("Einstellungen (ctrl + e)");
		prefs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				prefWindow.setVisible(true);
			}
		});
		file.add(prefs);

		// ------------------------------------------------- das 2. Menu
		JMenu functions = new JMenu("Funktionen");
		
		JMenuItem ctrlz = new JMenuItem("Rueckgaengig... ");
		ctrlz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.ctrlz();
			}
		});
		functions.add(ctrlz);

		JMenuItem metro = new JMenuItem("Metronom");
		metro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.showMetronom();
			}
		});
		functions.add(metro);

		JMenuItem tuner = new JMenuItem("Chromatisches Stimmgeraet (ctrl + t)");
		tuner.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.startChromaticTuner();
			}
		});
		functions.add(tuner);

		// JMenuItem eval = new JMenuItem("Evaluation");
		// eval.addActionListener(new ActionListener() {
		// public void actionPerformed(ActionEvent e) {
		// controller.initEvaluation();
		// }
		// });
		// functions.add(eval);

		// TODO hilfe und about
		JMenu help = new JMenu("Hilfe");
		JMenuItem helpMe = new JMenuItem("Hilfe");
		helpMe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new JFrame("Hilfe");
				String html = "<html><head></head><body>";
				html += "<h1>jAM Hilfe</h1>";
				html += "1. Bevor man rec klickt: <ul>";
				html += "<li>ggf. Mikrofon-Input w&auml;hlen</li>";
				html += "<li>sonst ggf. beats per minute(BPM), Tonart, Schl&uuml;ssel und Taktart w&auml;hlen</li>";
				html += "</ul>";
				html += "Man kann auch zu nem eigenen Metronom oder Lied spielen: (&uuml;ber Kopfh&ouml;hrer!)<br />";
				html += "<b>Daf&uuml;r muss allerdings in Einstellungen die gleiche Geschwindigkeit in BPM gew&auml;hlt werden !</b>";
				html += "<br /><br />";
				html += "2. Sobald Aufnahme gemacht wurde kann man diese so oft man will mit anderen Einstellungen<br />";
				html += "durchlaufen lassen. Daf&uuml;r muss man die WaveDatei nach der Aufnahme laden:<br />";
				html += "Die Aufnahme wird im Heimverzeichnis: "
						+ System.getProperty("user.home")
						+ "/jAM/jAM_session.wav NACH JEDER Aufnahme gespeichert!<br />";
				html += "<b> NACH der Aufnahme:</b><br />";
				html += "Ist die Aufnahme geladen kann man nun mit den Einstellungen spielen und dann immer wieder rec klicken.<br />";
				html += "Au&szlig;erdem ist es m&ouml;glich die erzeugten Noten direkt in dem Textbereich zu editieren.<br />";
				html += "Um das zu tun muss man jedoch abc-notation verstehen.";
				html += "<br /><br />";
				html += "3. Will man dann ne neue Aufnahme machen muss man 'Neues Projekt' w&auml;hlen.<br />";

				html += "<br /><br />";
				html += "WICHTIG: <br />";
				html +=" - Je l&auml;nger der gespielte Ton ist, destor besser sollte die Transkription werden! Deshalb nur 70, 60, und 80 BPM bis jetzt! <br />";
				html +=" - Aufnahme immer ohne Overlap machen! <br />";
				html +=" - Log Datei geht in  "+ System.getProperty("user.home") + "/jAM/jAM.log"+"<br />";
				html += "</body></html>";
				JLabel htmlPane = new JLabel(html);
				JScrollPane scrollPane = new JScrollPane(htmlPane);
				a.add(scrollPane, BorderLayout.NORTH);

				int h=550;
				a.setBounds(0, 0, 700, h);
				htmlPane.setPreferredSize(new Dimension(a.getWidth(), h));
				a.setVisible(true);
			}
		});
		help.add(helpMe);
		JMenuItem about = new JMenuItem("About");
		about.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(jframe,"jAM - java automatic music transcription v" + VERSION
										+ "\n\ncontact:\nbreddafredda@web.de\n\nWeb:\nhttp://saeft.com/jAM");

				// JFrame a = new JFrame("About jAM");
				// a.add(new JLabel("JAM VERSION ??? usw..."));
				// a.setBounds(400, 300, 100, 50);
				// a.setVisible(true);

			}
		});
		help.add(about);

		bar.add(file);
		bar.add(functions);
		bar.add(help);
		jframe.setJMenuBar(bar);

		// -----------------------------------------------------------------------------------
		// ControllPanel

		// ----- controll panel
		JPanel controllPanel = new JPanel();
		controllPanel.setLayout(new FlowLayout());

		start = new JButton("rec");
		start.setToolTipText("start/stop capturing (from mic or from file)");
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.startStopProcessing(e);
			}
		});
		controllPanel.add(start);

		playMidi = new JButton("play");
		playMidi.setToolTipText("play/stop midi");
		playMidi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.startStopMidi(e);
			}
		});
		controllPanel.add(playMidi);

		String[] list = new String[SampledAudioUtilities.getMixerInfo(false,
				true).size()];
		int i = 0;
		for (Mixer.Info mixer : SampledAudioUtilities.getMixerInfo(false, true)) {
			list[i++] = mixer.toString();
		}
		inputSelectBox = new JComboBox(list);
		inputSelectBox.setToolTipText("vorhandene Inputs");
		inputSelectBox.setPreferredSize(new Dimension(300, 30));
		inputSelectBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.inputSelection((String) inputSelectBox.getSelectedItem()); 
			}
		});
		controllPanel.add(inputSelectBox);

		prgbar = new JProgressBar();
		prgbar.setValue(0);
		prgbar.setToolTipText("input level");
		controllPanel.add(prgbar);

		// ------------------------- INFORMATIONEN ZUM TUNE(titel, autor, bpm,
		// tonart und noten in textarea) -------------------
		JPanel tunePanel = new JPanel();

		// abc_area = new JTextArea(8, 50);
		abc_area = new TuneEditorPane();
		abc_area.setBackground(new Color(255, 255, 255));
		abc_area.setPreferredSize(new Dimension(w - 200, 130));

		JScrollPane scrollerABCArea = new JScrollPane(abc_area);
		abc_area.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent arg0) {
				controller.saveTuneAsString(abc_area.getText());
//				System.out.println("saved: \n" + abc_area.getText());
			}
			public void keyReleased(KeyEvent arg0) {
				controller.parseAndSetTune(abc_area.getText());
				// abc_area.setSelectedItem(elmnt)
			}
			public void keyPressed(KeyEvent arg0) {
			}
		});
		tunePanel.add(scrollerABCArea);

		JPanel USERINPUT_PANEL = new JPanel(); // -> das panel mit dem
												// controllpanel und dem
												// TunePanel soll aufs MainWin
												// NORTH!
		USERINPUT_PANEL.setLayout(new BorderLayout());
		USERINPUT_PANEL.add(controllPanel, BorderLayout.NORTH);
		USERINPUT_PANEL.add(tunePanel, BorderLayout.CENTER);
		infoLabel = new JLabel("INFO");
		Font font = new Font(infoLabel.getFont().getFamily(), Font.BOLD,
				infoLabel.getFont().getSize());
		infoLabel.setFont(font);
		USERINPUT_PANEL.add(infoLabel, BorderLayout.SOUTH);

		// ----- scroller mit ScorePanel -----
		scroller = new JScrollPane(scorePanel);

		// Logger-UI ? notwendig?
		// JPanel logger = new JPanel(new FlowLayout());
		// logger.add(new JTextField("TESTETSTETSTSTSTSTSTTSTSTST"));

		// ----- SplitPane -----
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, USERINPUT_PANEL, scroller);
		// splitPane.setOneTouchExpandable(true);
		// splitPane.setDividerLocation(150);
		// USERINPUT_PANEL.setPreferredSize(new Dimension(w, h/4 ));
		scroller.setPreferredSize(new Dimension(w, 250));// (h - h/4 - 100)));

		// JSplitPane outer = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
		// splitPane, logger); //nested: unten den Logger !
		jframe.add(splitPane, BorderLayout.CENTER);
		// logger.setBackground(Color.BLACK);
		// logger.setPreferredSize(new Dimension(w, 100));
		// logger.setMaximumSize(new Dimension(w, 100));
		// logger.setMinimumSize(new Dimension(w, 100));
		// jframe.add(outer, BorderLayout.CENTER);

		jframe.pack();
		// setSize(w, h);
		jframe.setBounds(150, 0, w, h - 30);

		addGlobalListener();
	}

	private void addGlobalListener() {
		EventQueue e = Toolkit.getDefaultToolkit().getSystemEventQueue();
		e.push(new EventQueue() {
			protected void dispatchEvent(AWTEvent event) {
				if (event instanceof KeyEvent) {
					KeyEvent keyEvent = (KeyEvent) event;
					if (keyEvent.isControlDown()) {
						if (keyEvent.getID() == 401) { // nur eins der 3 events
//						 System.out.println(keyEvent.getKeyCode());

							if (keyEvent.getKeyCode() == 79
									|| keyEvent.getKeyChar() == 'o')
								controller.openDialogWAV();
							else if (keyEvent.getKeyCode() == 83
									|| keyEvent.getKeyChar() == 's')
								controller.saveDialog();
							if (keyEvent.getKeyCode() == 76
									|| keyEvent.getKeyChar() == 'l')
								controller.openDialog();
							else if (keyEvent.getKeyCode() == 69
									|| keyEvent.getKeyChar() == 'e') // open preferences
								prefWindow.setVisible(true);
							else if (keyEvent.getKeyCode() == 78
									|| keyEvent.getKeyChar() == 'n') // new project
								controller.newProject();
							else if (keyEvent.getKeyCode() == 84
									|| keyEvent.getKeyChar() == 't') // start tuner
								controller.startChromaticTuner();
							
							else if (keyEvent.getKeyCode() == 90 || keyEvent.getKeyChar() == 'z') // ctrl z
								controller.ctrlz();
							
							
						}
					}
				}
				super.dispatchEvent(event);
			}
		});
		DropTarget dropTarget = new DropTarget(jframe,
				new DropTargetListener() {
					public void dragEnter(DropTargetDragEvent e) {
					}

					public void dragExit(DropTargetEvent e) {
					}

					public void dragOver(DropTargetDragEvent e) {
					}

					public void drop(DropTargetDropEvent e) {
						try {
							Transferable tr = e.getTransferable();
							DataFlavor[] flavors = tr.getTransferDataFlavors();
							// System.out.println("LEN: " + flavors.length);

							for (int i = 0; i < flavors.length; i++)
								if (flavors[i].isFlavorJavaFileListType()) {
									// ZunŠchst annehmen
									e.acceptDrop(e.getDropAction());

									String filename = tr.getTransferData(flavors[i]).toString();
									filename = filename.replace("[", "");
									filename = filename.replace("]", "");

									if (filename.contains(".wav")) 
										controller.loadWavFile(new File(filename));
									
									else if(filename.contains(".jAM") || filename.contains(".abc"))
										controller.loadAbcFile(new File(filename));
									
									else
										showErrorMessage("Nur .wav .jAM oder .abc Dateien !");

									// System.out.println("DATA: " + filename);

									// Lis<File> files = (java.util.ArrayList)
									// tr.getTransferData(flavors[i]);
									// System.out.println(files.get(0));
									// label.setText("OK");
									e.dropComplete(true);
									return;
								}
						} catch (Throwable t) {
							showErrorMessage("Fehler beim droppen der Datei: "
									+ t.getMessage());
							t.printStackTrace();
						}
						e.rejectDrop();
					}

					public void dropActionChanged(DropTargetDragEvent e) {
					}
				});

		// need to now when user terminates the application
		Runtime rt = Runtime.getRuntime();
		rt.addShutdownHook(new Thread() {
			public void run() {
				controller.end();
			}
		});
	}

	public void showIt() {
		jframe.setVisible(true);
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
	// mvc: jede view muss diese methode implementieren
	public void modelPropertyChange(final PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(ControllerEngine.ERROR_PROPERTY)) {
			showErrorMessage(evt.getNewValue().toString());
		} else if (evt.getPropertyName().equals(
				ControllerEngine.START_STOP_PROCESSING_BUTTON_PROPERTY)) {
			String newStringValue = evt.getNewValue().toString();
			if (!start.getText().equals(newStringValue))
				start.setText(newStringValue);
		} else if (evt.getPropertyName().equals(
				ControllerEngine.MIDIBUTTON_NAME_PROPERTY)) {
			String newStringValue = evt.getNewValue().toString();
			if (!playMidi.getText().equals(newStringValue))
				playMidi.setText(newStringValue);
		} else if (evt.getPropertyName().equals(
				ControllerEngine.INPUTLEVEL_PROPERTY)) {
			float newFloatValue = Float
					.parseFloat(evt.getNewValue().toString());
			if (prgbar.getValue() != (int) newFloatValue)
				prgbar.setValue((int) newFloatValue);
		} else if (evt.getPropertyName().equals(
				ControllerEngine.UPDATE_SCORE_PROPERTY)) {
			String tuneAsString = (String) (evt.getNewValue());
			Tune tune = new TuneParser().parse(tuneAsString);
			scorePanel.setTune(tune);
			if (!abc_area.getText().equals(tuneAsString))
				abc_area.setText(tuneAsString);
		}
		// TODO transpose eig sollte aus dem tranpnierten Tune der String
		// generiert werden und dann wieder
		// UPDATE_SCORE_PROPERTY, also folgendes kann dann wieder weg !
		else if (evt.getPropertyName().equals(ControllerEngine.SHOW_TUNE_PROPERTY)) {
			Tune tune = (Tune) (evt.getNewValue());
			scorePanel.setTune(tune);
		}

		else if (evt.getPropertyName().equals(ControllerEngine.TITLE_PROPERTY)) {
			String title = (String) (evt.getNewValue());
			if (!title.equals(jframe.getTitle()))
				jframe.setTitle(title);
		} else if (evt.getPropertyName().equals(
				ControllerEngine.INFO_LABEL_PROPERTY)) {
			String info = (String) (evt.getNewValue());
			if (!infoLabel.getText().equals(info))
				infoLabel.setText(info);
		} else if (evt.getPropertyName()
				.equals(ControllerEngine.METRO_PROPERTY)) {
			boolean sel = (Boolean) (evt.getNewValue());
			if (prefWindow.metroCheckbox.isSelected() != sel)
				prefWindow.metroCheckbox.setSelected(sel);
		} else if (evt.getPropertyName().equals(
				ControllerEngine.WRITE_SCORE_TO_IMAGE_PROPERTY)) {
			try {
				scorePanel.writeScoreTo(new File((String) (evt.getNewValue())));
			} catch (Exception e) {
				e.printStackTrace();
				showErrorMessage("Fehler beim Speichern der Noten als Bild... "
						+ e.getMessage());
			}
		} else if (evt.getPropertyName().equals(
				ControllerEngine.SCROLL_DOWN_PROPERTY)) {
			scrollDown(Integer.parseInt(evt.getNewValue().toString()));
		} else if (evt.getPropertyName().equals(ControllerEngine.BPM_PROPERTY)) {
			int bpm = Integer.parseInt(evt.getNewValue().toString());
			prefWindow.setBpm(bpm);
		} else if (evt.getPropertyName()
				.equals(ControllerEngine.CHUNK_PROPERTY)) {
			int chunk = Integer.parseInt(evt.getNewValue().toString());
			prefWindow.setChunk(chunk);
		} else if (evt.getPropertyName().equals(
				ControllerEngine.OVERLAP_PROPERTY)) {
			int o = Integer.parseInt(evt.getNewValue().toString());
			prefWindow.setOverlap(o);
		} else if (evt.getPropertyName().equals(
				ControllerEngine.TRANSPOSE_REC_PROPERTY)) {
			int t = Integer.parseInt(evt.getNewValue().toString());
			prefWindow.setTransposeRec(t);
		} else if (evt.getPropertyName().equals(
				ControllerEngine.TONART_PROPERTY)) {
			prefWindow.setTonart(evt.getNewValue().toString());
		} else if (evt.getPropertyName().equals(
				ControllerEngine.TAKTART_PROPERTY)) {
			prefWindow.setTaktart(evt.getNewValue().toString());
		} else if (evt.getPropertyName().equals(
				ControllerEngine.HIGHLIGHT_NOTE_PROPERTY)) {
			MusicElement note = (MusicElement) (evt.getNewValue());
			scorePanel.selectNote(note);
		}
	}

	private void showErrorMessage(String msg) {
		JOptionPane.showMessageDialog(jframe, msg);
	}

	private void scrollDown(int pixel) {
		try {
			Point point = new Point(0, pixel);
			scroller.getViewport().setViewPosition(point);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

class PreferenceWindow extends JFrame {
	// sind fuer mainWin sichtbar:
	JCheckBox playback, plotting, metroCheckbox, lowPass, bassClefCheckbox;
	JComboBox sampleRateSelectBox, chunckSelectBox, overlapSelectBox,
			pdaSelectBox, transposeCombobox, bpmSelectBox, instrument,
			tonartSelectbox, taktartSelectbox;
	JTextField yinTreshold, mpmTreshold, minDur, minLevel;

	// private IPreferencesListener prefListener;
	private ControllerEngine controller;

	public PreferenceWindow(ControllerEngine c) {
		this.controller = c;

		setTitle("Einstellungen");
		setLayout(new BorderLayout());
		setBounds(0, 300, 800, 200);

		JPanel tuneOptions = new JPanel(new FlowLayout());
		JPanel recOptions = new JPanel(new FlowLayout());
		JPanel pdaOptions = new JPanel(new FlowLayout());

		// 1 -------------------- TUNE OPTIONS
		// ----------------------------------------
		JLabel title = new JLabel("tune options");
		title.setFont(new Font("sansserif", Font.BOLD, 13));
		tuneOptions.add(title);

		// ----- BPM Auswahlliste
		String[] list = new String[] { "60", "70", "80"/*,  "100", "120"*/ }; //TODO bpm evaluate !
		bpmSelectBox = new JComboBox(list);
		bpmSelectBox.setSelectedIndex(0);
		bpmSelectBox.setToolTipText("beats per minute");
		bpmSelectBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.setBPM(Integer.parseInt((String) bpmSelectBox.getSelectedItem()));
			}
		});
		tuneOptions.add(bpmSelectBox);

		// ----- Tonart
		list = new String[] { "C/a", "G/e", "D/h", "A/f#", "E/c#", "B/g#",
				"F#/d#", "F/d", "Bb/g", "Eb/c", "Ab/f", "Db/bb", "C#/a#",
				"Gb/eb" };
		tonartSelectbox = new JComboBox(list);
		tonartSelectbox.setSelectedIndex(0);
		tonartSelectbox.setToolTipText("Tonart (englisch)");
		tonartSelectbox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.setTonart(((String) tonartSelectbox
						.getSelectedItem()).split("/")[0]);
			}
		});
		tuneOptions.add(tonartSelectbox);

		// ----- BassSchluessel?
		bassClefCheckbox = new JCheckBox("BassSchluessel");
		bassClefCheckbox.setSelected(false);
		bassClefCheckbox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.setClef(bassClefCheckbox.isSelected());
			}
		});
		tuneOptions.add(bassClefCheckbox);

		// ----- Taktart
		list = new String[] { "4/4"/* , "3/4", "5/4" */}; // --> dafuer andere
															// bpm's ??? erstmal
															// durchdenken bevor
															// support !!!
		taktartSelectbox = new JComboBox(list);
		taktartSelectbox.setSelectedIndex(0);
		taktartSelectbox.setToolTipText("Taktart");
		taktartSelectbox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.setTaktart(taktartSelectbox.getSelectedIndex());
			}
		});
		tuneOptions.add(taktartSelectbox);

		// ----- Transponieren
		list = new String[] { "-12", "-11", "-10", "-9", "-8", "-7", "-6",
				"-5", "-4", "-3", "-2", "-1", "0", "1", "2", "3", "4", "5",
				"6", "7", "8", "9", "10", "11", "12" };
		transposeCombobox = new JComboBox(list);
		transposeCombobox.setSelectedIndex(12);
		transposeCombobox.setToolTipText("transponieren (in Halbtonschritten)");
		transposeCombobox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.transpose(Integer.parseInt((String) transposeCombobox.getSelectedItem()));
				transposeCombobox.setSelectedIndex(12);
			}
		});
		tuneOptions.add(transposeCombobox);

		// ----- transponiert aufnehmen
		list = new String[] { "Normal", "Bb-Instrument" };
		instrument = new JComboBox(list);
		instrument.setSelectedIndex(0);
		instrument
				.setToolTipText("transponiert aufnehmen(zB bei Bb Instrumenten)");
		instrument.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.setTransposeRecording(instrument.getSelectedIndex());
			}
		});
		tuneOptions.add(instrument);

		add(tuneOptions, BorderLayout.NORTH);

		// 2 -------------------- REC OPTIONS
		// ----------------------------------------
		title = new JLabel("recording options");
		title.setFont(new Font("sansserif", Font.BOLD, 13));
		recOptions.add(title);

		// ----- checkbox metronome
		metroCheckbox = new JCheckBox("metronome");
		metroCheckbox.setToolTipText("use metronome/click");
		metroCheckbox.setSelected(true);
		metroCheckbox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.setMetroSelected(metroCheckbox.isSelected());
			}
		});
		recOptions.add(metroCheckbox);

		// ----- checkbox playback
		playback = new JCheckBox("playback");
		playback.setToolTipText("playback while recording (experimental!)");
		playback.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.setPlaybackSelected(playback.isSelected());
			}
		});
		recOptions.add(playback);

		// ----- checkbox plotting
		plotting = new JCheckBox("plotting");
		plotting.setToolTipText("plotting output");
		plotting.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.setPlottingSelected(plotting.isSelected());
			}
		});
		recOptions.add(plotting);

		// ----- checkbox lowPass
		lowPass = new JCheckBox("low-pass");
		lowPass.setToolTipText("enable low-pass filter");
		lowPass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.setLowPassEnabled(lowPass.isSelected());
			}
		});
		recOptions.add(lowPass);

		// -----sampleRate, buffersize und overlap
		// String[] list = new String[]{"8000", "11025", "16000", "22050",
		// "44100"};
		// sampleRateSelectBox = new JComboBox(list);
		// sampleRateSelectBox.setSelectedIndex(4);
		// sampleRateSelectBox.setToolTipText("SampleRate");
		// sampleRateSelectBox.addActionListener(new ActionListener() {
		// public void actionPerformed(ActionEvent arg0) {
		// // System.out.println("CHUNK: " + chunckSelectBox.getSelectedItem());
		// prefListener.setSampleRate(Integer.parseInt((String)sampleRateSelectBox.getSelectedItem()));
		// prefListener.initAllThreads(prefListener.getInputIndex());
		// }
		// });
		// recOptions.add(sampleRateSelectBox);

		list = new String[] { "512", "1024", "2048", "4096", "2206"};
		chunckSelectBox = new JComboBox(list);
		chunckSelectBox.setSelectedIndex(1);
		chunckSelectBox.setToolTipText("buffer size");
		chunckSelectBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.setChunk(Integer.parseInt((String) chunckSelectBox
						.getSelectedItem()));
			}
		});
		recOptions.add(chunckSelectBox);

		list = new String[] { "0%", "10%", "25%", "50%", "75%" };
		overlapSelectBox = new JComboBox(list);
		overlapSelectBox.setSelectedIndex(0);// (4);
		overlapSelectBox.setToolTipText("buffer overlap");
		overlapSelectBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.setOverlap(Integer.parseInt(((String) overlapSelectBox.getSelectedItem()).replaceAll("%", "")));
			}
		});
		recOptions.add(overlapSelectBox);

		list = new String[] { "YIN", "MPM" };
		pdaSelectBox = new JComboBox(list);
		pdaSelectBox.setSelectedIndex(1);
		pdaSelectBox.setToolTipText("Pitch Detection Algorithm");
		pdaSelectBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.setPDA((String) pdaSelectBox.getSelectedItem());
			}
		});
		recOptions.add(pdaSelectBox);

		add(recOptions, BorderLayout.CENTER);

		// 3 -------------------- PDA/COLLECTOR OPTIONS
		// ----------------------------------------
		title = new JLabel("pda/collector options");
		title.setToolTipText("Press ENTER to save");
		title.setFont(new Font("sansserif", Font.BOLD, 13));
		pdaOptions.add(title);

		yinTreshold = new JTextField("0.13");
		yinTreshold.setToolTipText("YIN threshold (default 0.13)");
		yinTreshold.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.setYinThreshold(Float.parseFloat(yinTreshold
						.getText()));
			}
		});
		pdaOptions.add(yinTreshold);

		mpmTreshold = new JTextField("0.93");
		mpmTreshold.setToolTipText("MPM threshold (default 0.93)");
		mpmTreshold.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.setMpmThreshold(Float.parseFloat(mpmTreshold
						.getText()));
			}
		});
		pdaOptions.add(mpmTreshold);

		minDur = new JTextField("50");
		minDur.setToolTipText("minimum duration of collector -> ONSET/OFFSET CONSTRAINTS (i.e. 40ms)");
		minDur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.setMinDuration(Integer.parseInt(minDur.getText()));
			}
		});
		pdaOptions.add(minDur);

		minLevel = new JTextField("-70.00");
		minLevel.setToolTipText("minimum sound input level(dB) -> ONSET/OFFSET CONSTRAINTS");
		minLevel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.setMinLevel(Float.parseFloat(minLevel.getText()));
			}
		});
		pdaOptions.add(minLevel);

		add(pdaOptions, BorderLayout.SOUTH);

		// TODO: restore button !!!!
		// TODO prefs immer in configDatei schreiben und diese am Anfang laden!
		// ~/.jAM/prefs.conf oder sowas... !!!
	}

	// ----- SETTER -----
	public void setChunk(int c) {
		// System.out.println("UI setChunk: " + c);
		if (c == 512)
			chunckSelectBox.setSelectedIndex(0);
		else if (c == 1024)
			chunckSelectBox.setSelectedIndex(1);
		else if (c == 2048)
			chunckSelectBox.setSelectedIndex(2);
		else if (c == 4096)
			chunckSelectBox.setSelectedIndex(3);
		else if (c == 2206)
			chunckSelectBox.setSelectedIndex(4);
	}

	public void setOverlap(int oPerc) {
		// System.out.println("UI setOverlap: " + oPerc);
		if (oPerc == 0)
			overlapSelectBox.setSelectedIndex(0);
		else if (oPerc == 10)
			overlapSelectBox.setSelectedIndex(1);
		else if (oPerc == 25)
			overlapSelectBox.setSelectedIndex(2);
		else if (oPerc == 50)
			overlapSelectBox.setSelectedIndex(3);
		else if (oPerc == 75)
			overlapSelectBox.setSelectedIndex(4);
	}

	public void setBpm(int bpm) {
		// System.out.println("UI setBpm: " + bpm);
		if (bpm == 60)
			this.bpmSelectBox.setSelectedIndex(0);
		else if (bpm == 70)
			this.bpmSelectBox.setSelectedIndex(1);
		else if (bpm == 80)
			this.bpmSelectBox.setSelectedIndex(2);
	}

	public void setTonart(String t) {
		// System.out.println("UI setTonart: " + t);

		if (t.equals("C"))
			tonartSelectbox.setSelectedIndex(0);
		else if (t.equals("G"))
			tonartSelectbox.setSelectedIndex(1);
		else if (t.equals("D"))
			tonartSelectbox.setSelectedIndex(2);
		else if (t.equals("A"))
			tonartSelectbox.setSelectedIndex(3);
		else if (t.equals("E"))
			tonartSelectbox.setSelectedIndex(4);
		else if (t.equals("B"))
			tonartSelectbox.setSelectedIndex(5);
		else if (t.equals("F#"))
			tonartSelectbox.setSelectedIndex(6);
		else if (t.equals("F"))
			tonartSelectbox.setSelectedIndex(7);
		else if (t.equals("Bb"))
			tonartSelectbox.setSelectedIndex(8);
		else if (t.equals("Eb"))
			tonartSelectbox.setSelectedIndex(9);
		else if (t.equals("Ab"))
			tonartSelectbox.setSelectedIndex(10);
		else if (t.equals("Db"))
			tonartSelectbox.setSelectedIndex(11);
		else if (t.equals("C#"))
			tonartSelectbox.setSelectedIndex(12);
		else if (t.equals("Gb"))
			tonartSelectbox.setSelectedIndex(13);
	}

	public void setTaktart(String t) {
		// System.out.println("UI setTaktart: " + t);

		if (t.equals("4/4"))
			taktartSelectbox.setSelectedIndex(0);
		else if (t.equals("3/4"))
			taktartSelectbox.setSelectedIndex(1);
		else if (t.equals("5/4"))
			taktartSelectbox.setSelectedIndex(2);
	}

	public void setTransposeRec(int t) {
		// System.out.println("UI setTransposeRec: " + t);
		instrument.setSelectedIndex(t);
	}
}