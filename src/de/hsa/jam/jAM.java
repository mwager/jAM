package de.hsa.jam;

import java.io.File;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import de.hsa.jam.audio.Model;
import de.hsa.jam.ui.ChromaticTunerFrame;
import de.hsa.jam.ui.MainWindow;
import de.hsa.jam.ui.MetronomeFrame;

/**
 * Mainclass: Initialize a Model and views and start application.
 *
 *
 *
 * <p>This application depends on the following external libraries:</p>
 * <ul>
 * <li><a target="_blank" href="http://code.google.com/p/abc4j/">abc4j</a></li>
 * <li><a target="_blank" href="http://tarsos.0110.be">Tarsos</a></li>
 * </ul>
 * <br />
 * Some changes were made in the following classes: (search for "jAM")<br />
 * <h3>abc4j</h3>
 * <ul>
 * <li>src/abc/midi/MidiConverterAbstract.java</li>
 * <li>src/abc/midi/TunePlayer.java</li>
 * </ul>
 * <h3>Tarsos</h3>
 * <ul>
 * <li>src/be/hogent/tarsos/sampled/AudioDispatcher.java</li>
 * <li>src/be/hogent/tarsos/sampled/pitch/McLeodPitchMethod.java</li>
 * <li>src/be/hogent/tarsos/sampled/pitch/Pitch.java</li>
 * <li>src/be/hogent/tarsos/sampled/pitch/Yin.java</li>
 * </ul>
 * 
 * @author Michael Wager
 */
public class jAM {
	/**
	 * logs to ~/jAM.log
	 */
	private static Logger LOG = Logger.getLogger(jAM.class.getName());

	public static long START_TIME, GLOBAL_TIMESTAMP;

	// public static final String DESKTOP_PATH = System.getProperty("user.home")
	// + "/Desktop";
	
	/** 
	 * System.getProperty("user.home");
	 * */
	public static final String HOME_PATH = System.getProperty("user.home");
	
	public static boolean EVALUATING = false;

	/**
	 * version of application
	 */
	private static final float VERSION = 0.8f;
	
	/**
	 * title of application
	 */
	public static String TITLE = "jAM - java automatic music transcription v" + VERSION;

	private static Model model;
	
	public static boolean SYSOUT=false;

	/**
	 * @param args
	 *            - if none: start app, else if args[0]=="eval": start evaluation
	 * */
	public static void main(String[] args) {
		START_TIME = System.currentTimeMillis();
		configureLogging();

		if (args.length == 0) {
			model = new Model(); // kann mehrere geben

			ControllerEngine controller = new ControllerEngine();

			// views kennen controller
			MainWindow mainView = new MainWindow(TITLE, VERSION, controller);
			ChromaticTunerFrame chromaticTuner = new ChromaticTunerFrame(controller);
			MetronomeFrame metroFrame = new MetronomeFrame(controller);

			controller.addView(mainView);
			controller.addView(chromaticTuner);
			controller.addView(metroFrame);
			controller.addModel(model);

			controller.initApp();
			mainView.showIt();

			// controller.startChromaticTuner();
		}

		// else: evaluate (no ui needed)
		else if (args.length == 1 && args[0].equals("eval")) {
			EVALUATING = true;
			model = new Model();
			ControllerEngine controller = new ControllerEngine();
			controller.addModel(model);
			controller.initApp();
			controller.initEvaluation();
		}
	}

	/**
	 * Global log function.
	 */
	public static void log(String msg, boolean warning) {
		if (warning)
			LOG.warning(msg);
		else
			LOG.info(msg);

		// model.jAM_error(msg);
	}

	/**
	 * configure Logging to ~/jAM/jAM.log
	 */
	private static void configureLogging() {
		try {
			boolean append = false;
			
			try {
				new File(HOME_PATH + "/jAM").mkdir();
			}catch (Exception e) {}
			
			FileHandler handler = new FileHandler(HOME_PATH + "/jAM/jAM.log", append);
			handler.setFormatter(new SimpleFormatter());
			// handler.setLevel(Level.FINEST);
			LOG.addHandler(handler);
			// LOG.severe("SEVERE MSG");
			// LOG.warning("WARNING");
			// LOG.info("INFO!!!!!");

			// HMM???...
			// LOG=Logger.getLogger(ControllerEngine.class.getName());
			// final InputStream stream =
			// jAM.class.getResourceAsStream("/de/hsa/jam/util/logging.properties");
			// LogManager.getLogManager().readConfiguration(stream);
			// //nun ein konfigurierter Logger:
			// LOG=Logger.getLogger(ControllerEngine.class.getName());

		} catch (Exception e) {
			e.printStackTrace();
		}
		// handler=new ConsoleHandler();
		// LOG.setUseParentHandlers(false);

		// TODO
		// a default (not configured) logger
		// LOG = Logger.getLogger(jAM.class.getName());
		// try {
		// final InputStream stream = jAM.class.getResourceAsStream(LOG_PROPS);
		// LogManager.getLogManager().readConfiguration(stream);
		// // a configured logger
		// LOG = Logger.getLogger(jAM.class.getName());
		// } catch (final SecurityException e) {
		// System.err.println("SHIT1");
		// LOG.log(Level.SEVERE, e.getLocalizedMessage(), e);
		// } catch (final IOException e) {
		// System.err.println("SHIT2");
		// LOG.log(Level.SEVERE, e.getLocalizedMessage(), e);
		// }
	}
}