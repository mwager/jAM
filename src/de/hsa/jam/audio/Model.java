package de.hsa.jam.audio;

import java.io.File;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.Vector;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioFormat.Encoding;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.TargetDataLine;

import abc.midi.BasicMidiConverter;
import abc.midi.MidiConverterAbstract;
import abc.midi.PlayerStateChangeEvent;
import abc.midi.TempoChangeEvent;
import abc.midi.TunePlayer;
import abc.midi.TunePlayerListenerInterface;
import abc.notation.Accidental;
import abc.notation.KeySignature;
import abc.notation.MusicElement;
import abc.notation.Note;
import abc.notation.NoteAbstract;
import abc.notation.Tune;
import abc.notation.Voice;
import abc.parser.TuneParser;
import be.hogent.tarsos.sampled.AudioDispatcher;
import be.hogent.tarsos.sampled.AudioProcessor;
import be.hogent.tarsos.sampled.BlockingAudioPlayer;
import be.hogent.tarsos.sampled.SampledAudioUtilities;
import be.hogent.tarsos.sampled.filters.LowPassFS;
import be.hogent.tarsos.sampled.pitch.Pitch;
import be.hogent.tarsos.sampled.pitch.PitchUnit;
import be.hogent.tarsos.sampled.pitch.Yin;
import de.hsa.jam.ControllerEngine;
import de.hsa.jam.jAM;
import de.hsa.jam.audio.collector.AudioBufferQueue;
import de.hsa.jam.audio.collector.NoteCollectorWorker;
import de.hsa.jam.audio.midi.MidiMetronome;
import de.hsa.jam.evaluation.Evaluator;
import de.hsa.jam.util.jAMUtils;

/**
 * This class represents the global Model (MVC) of the jAM application.
 * 
 * <p>Duties of this class:</p>
 * <ul>
 *     <li> init streams from mic or from a wave-file</li>
 *     <li> start/stop processing, MIDI, metronome, tuner ...</li>
 *     <li> hold global important data like BufferSize, BufferOverlap, Samplerate, bpm etc</li>
 *     <li> controll evaluation</li>
 *     
 * @author Michael Wager
 */
public class Model extends AbstractModel {
    private String savedFileName = jAM.HOME_PATH + "/jAM/jAM_session.wav";

//    private boolean ALREADY_WRITTEN_TO_FILE = false;

    // ----- capturing params: -----
    private float SAMPLERATE = 44100;
    private int CHUNK = 1024; 			// TODO das hier muss immer noch mit GUI ubereinstimen !!!
    private int overlapPercentage = 0;
    private int OVERLAP = CHUNK * overlapPercentage / 100;
    private String PDA = "MPM"; // YIN oder MPM
    private int bpm = 60;
    private String TONART = "C", TAKTART = "4/4";
    private int TAKTARTINDEX = 0;

    private boolean metroSelected = true;
    private boolean plottingSelected = false;
    private boolean lowPassSelected = false;
    private boolean playbackSelected = false;
    private int transposeRecIndex = 0;

    private AudioDispatcher dispatcher = null;
    private AudioBufferQueue queue;
    private NoteCollectorWorker collector;
    
    private AudioFormat format;
    private TargetDataLine line = null;
//    private Mixer selectedMixer = null;
    private Mixer.Info mixerInfo;
    
    private WaveFileWriter wfr;
    private AudioProcessor myAudioProcessor;
    private MidiMetronome metro;
    private TunePlayer player;

    // ----- ABC Tune params -----
    private Tune tune;
    final String NEWLINE = "\n";
    private String tuneAsString = "", lastTuneAsString="";
    private String[] tuneAsArray;
    private Vector<String> notesAsArray;
    private int INDEX = 6;

    private File audioFile = null;

    boolean PROCESSING = false, PLAYING_MIDI = false;

    private Vector<String> errorVec = new Vector<String>();

    /**
     * <p>Init the MidiMetronome, TunePlayer, AudioBufferQueue and a New Abc-Tune as String,</p>
     * <p>and init a custom AudioProcessor: It starts the collector, </p>
     * <p>once data was received from the AudioDispatcher, then just adds these buffer to the queue</p>
     * */
    public Model() {
        metro = new MidiMetronome(bpm);// default 60 bpm

        myAudioProcessor = new AudioProcessor() {
            // int processedSampless;
            int cnt = 0;
            Vector<Float> levels = new Vector<Float>();

            public void processFull(float[] audioFloatBuffer, byte[] audioByteBuffer) {
                collector.start();
                cnt = 1;

                addBufferToQueue(audioFloatBuffer, audioByteBuffer);
            }

            public void processOverlapping(float[] audioFloatBuffer, byte[] audioByteBuffer) {
                // this.processedSamples -= OVERLAP;

                cnt++;

                addBufferToQueue(audioFloatBuffer, audioByteBuffer);
            }

            public void processingFinished() {
                jAM.log("Model: -------------------- OK, processing finished (added all buffer to queue) --------------------", false);

                queue.add(null); 

//                int delta=5;
//        		Vector<double []>minima = jAMUtils.detectLocalMinima(levels, delta);
        		
        		//TODO Erweiterung: evt vorher auf RMS Level pro Buffer basierend segmentieren, 
        		//dann fuer jedes Segment pitches erkennen und Entscheidung treffen??
//        		if(!isEvaluating()) {
//        			jAMUtils.printArray(levels);
//        			System.out.print("####################################### MINIMA ===> [");
//        			for (int i = 0; i < minima.size(); i++) {
//        				System.out.print("(" + minima.get(i)[0] + "," + minima.get(i)[1] + "), ");
//        			}
//        			System.out.println("]");
//        		}
                
            }

            public void addBufferToQueue(float[] audioFloatBuffer, byte[] audioByteBuffer) { // audioFloatBuffer hat die laenge
                                                // von CHUNK !!!
            	//TODO dB ???
                float level = jAMUtils.calculateRMSLevel(audioByteBuffer, audioFloatBuffer);
                
                levels.add(level);

                // JUST ADD to queue
                queue.add(audioFloatBuffer/*, level*/);

                firePropertyChange(ControllerEngine.INPUTLEVEL_PROPERTY, 0, level);
            }
        };

        player = new TunePlayer();
        player.addListener(new TunePlayerListenerInterface() {
            public void tempoChanged(TempoChangeEvent e) {}
            public void playBegin(PlayerStateChangeEvent e) {}
            public void partPlayed(int begin, int end) {}
            public void notePlayed(NoteAbstract note) {
                 firePropertyChange(ControllerEngine.HIGHLIGHT_NOTE_PROPERTY, null, note);
            }
            public void playEnd(PlayerStateChangeEvent e) {
                jAM.log("Model-TunePlayerListenerInterface: Tune abgespielt", false);
                PLAYING_MIDI = false;

                firePropertyChange(ControllerEngine.MIDIBUTTON_NAME_PROPERTY, "stop", "play");
            }
        });

        queue = new AudioBufferQueue();

        initNewTuneAsString();
    }

    private Evaluator evaluator = null;

    // wird einmal vom controller gecallt:
    public void initEvaluation(Evaluator evaluator) {
        this.metroSelected = false; // erst hier, dann update auf gui
        firePropertyChange(ControllerEngine.METRO_PROPERTY, true, false);

        PROCESSING = true;
        this.evaluator = evaluator;
        this.evaluator.setModel(this); // !
        try {
            evaluationRun();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public boolean isEvaluating() {
        return evaluator != null;
    }

    private int evalCnt = 1;
    private int[] chunks = new int[] { 512, 1024, 2048 };
    private int[] overlaps = new int[] { 0, 10, 25, 50, 75 };

    /**
     * run complete evaluation
     * */
    public void evaluationRun() throws Exception {
        String[] pdas = { "YIN", "MPM" };

        AudioInputStream audioInputStream = null;

        for (int x = 0; x < pdas.length; x++) {
            evaluator.reset();
            String PDA = pdas[x];
            System.out.println("========================================================================>>>> NEUER EVAL DURCHLAUF MIT PDA: " + PDA);

            while ((audioInputStream = evaluator.getNextStream(true)) != null) {
                System.out.println("========================================================================>>>> Eval DURCHLAUF: #"+ evalCnt++);

                for (int i = 0; i < chunks.length; i++) {
                    setChunk(chunks[i]);

                    for (int j = 0; j < overlaps.length; j++) {
                        try {
                            if (audioInputStream == null) {
                                audioInputStream = evaluator.getNextStream(false);
                                // System.out.println("EVAL: GOT STREAM");
                            }

                            setOverlap(overlaps[j]);

                            SAMPLERATE = audioInputStream.getFormat().getSampleRate();
//                            long milliseconds = (long) ((audioInputStream.getFrameLength() * 1000) / audioInputStream.getFormat().getFrameRate());
                            // laenge der wav datei in secs: 
//                            double secs = milliseconds / 1000.0;

                            // JEDES MAL NEUEN DISPATCHER, QUEUE UND NEUEN
                            // COLLECTOR!
                            queue = new AudioBufferQueue();
                            collector = new NoteCollectorWorker(this, queue,SAMPLERATE, CHUNK, OVERLAP, bpm, PDA);
                            collector.setEvaluator(evaluator);
                            setTonart(TONART); // -> setzt auch fuer collector!
                            setTaktart(TAKTARTINDEX); // -> setzt auch fuer
                                                        // collector!
                            collector.start();

                            try {
                                dispatcher.stop();
                            } catch (Exception e) {
                                System.out.println("EXC disp.stop(): "
                                        + e.getMessage());
                            }

                            dispatcher = new AudioDispatcher(audioInputStream,line, CHUNK, OVERLAP);

                            dispatcher.addAudioProcessor(myAudioProcessor);
                            dispatcher.start();

                            // System.out.println("STARTED DISPATCHER !!! collector.isCollecting(): "
                            // + collector.isCollecting());
                            System.out.println("........... waiting ...........");
                            while (collector.isCollecting()) {
                                jAMUtils.sleep(200);
                            }
                            audioInputStream = null;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        // evaluator.gimmiCurrentResults(false);

                    }// end overlaps for
                }// end chunks for

                // ----- jetzt ist eine Zeile durch
                evaluator.gimmiCurrentResults(false);
            }// end while lines in dbTable

            evaluator.gimmiCurrentResults(true);
        }// END YIN / MPM

        // wir sind durch: (es gibt nix mehr in DB)
        evaluator.FINAL();
        evaluator.closeDBConnection();
        System.exit(0);
    }

    /**
     * init Processing: <br />
     * <ul>
     * <li>init a new AudioBufferQueue and a new NoteCollectorWorker</li>
     * <li>init a new AudioInputStream from Mic or from file</li>
     * <li>init a new AudioDispatcher: add BlockingAudioPlayer, LowPassFS, WaveFileWriter and the custom AudioProcessor </li>
     * </ul>
     * */
    public void initProcessing(String dummy) {
        // System.out.println("initProcessing dummy: " + dummy);

        PROCESSING = false;

        // FIXME: dann wird hier noch 3 mal initProcessing gecallt!
        // denn: ui elemente werden geetzt und angezeigt per setSelectedItem
        // was wiederum die actionPerformed() zum ausfÙhren bringt, dann wieder
        // hier....
        // firePropertyChange(ControllerEngine.INPUTLEVEL_PROPERTY, -1, 0);
        // firePropertyChange(ControllerEngine.CHUNK_PROPERTY, -1, CHUNK);
        // firePropertyChange(ControllerEngine.OVERLAP_PROPERTY, -1,
        // overlapPercentage);

        try {
            queue = new AudioBufferQueue();
            collector = new NoteCollectorWorker(this, queue, SAMPLERATE, CHUNK,OVERLAP, bpm, PDA);
            // setTonart(this.TONART);
            // setTaktart(this.TAKTARTINDEX);

            AudioInputStream audioInputStream = null;
            if (audioFile != null) {
                audioInputStream = fromFile(audioFile); // "/Users/fred/Desktop/bachelor/Samples/git_all_empty.wav"

                firePropertyChange(ControllerEngine.START_STOP_PROCESSING_BUTTON_PROPERTY, "", "process");
                
                this.metroSelected = false; // erst hier, dann update auf gui
                firePropertyChange(ControllerEngine.METRO_PROPERTY, true, false);

                long milliseconds = (long) ((audioInputStream.getFrameLength() * 1000) / audioInputStream.getFormat().getFrameRate());
                // laenge der wav datei in secs: //TODO anzeigen!
                double secs = milliseconds / 1000.0;

                firePropertyChange(ControllerEngine.TITLE_PROPERTY, "", audioFile.getName() + " geladen.");
                firePropertyChange(ControllerEngine.INFO_LABEL_PROPERTY, "","Datei " + audioFile.getName() + " dauert: " + secs+ " Sekunden...");

            } else {
                try {
                    this.metroSelected = true; // erst hier, dann update auf gui
                    firePropertyChange(ControllerEngine.METRO_PROPERTY, false,true);
                    audioInputStream = fromMic();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (audioInputStream == null)
                jAM_error("audioInputStream==null");

            SAMPLERATE = audioInputStream.getFormat().getSampleRate();
            // System.out.println("INITED WITH SampleRate: " + SAMPLERATE);

            dispatcher = new AudioDispatcher(audioInputStream, line, CHUNK, OVERLAP);

            if (playbackSelected)
                dispatcher.addAudioProcessor(new BlockingAudioPlayer(format, CHUNK, OVERLAP));

            if(lowPassSelected)
            	dispatcher.addAudioProcessor(new LowPassFS(30, SAMPLERATE, OVERLAP));
           
            // TODO SAVE SESSION TO WAVE !!!
            // if(!ALREADY_WRITTEN_TO_FILE)
            wfr = new WaveFileWriter(format, CHUNK, OVERLAP, savedFileName);
            dispatcher.addAudioProcessor(wfr);

            dispatcher.addAudioProcessor(myAudioProcessor);

        } catch (Exception e) {
            e.printStackTrace();
            jAM_error("Error in init all threads " + e.getMessage());
        }
    }

    /**
     * global error logging
     **/
    public void jAM_error(String msg) {
        jAM.log("Model: (jAM_error) " + msg, true);
        this.errorVec.add(msg);
        firePropertyChange(ControllerEngine.ERROR_PROPERTY, "", msg);
    }

    /**
     * shutdown hook - this function gets called before the application terminates
     **/
    public void shutdown(Object o) {
        jAM.log("Model: ******************** Application terminated. ********************", false);
        jAM.log("Model: Collected Error Messages: " + (errorVec.size() == 0 ? "None" : ""), false);
        for (int i = 0; i < errorVec.size(); i++) {
            jAM.log(errorVec.get(i), false);
        }
    }

    public void setAudioFile(File audioFile) {
        this.audioFile = audioFile;
        if (PROCESSING)
            stopProcessing();

        // ist automatisch newProject !
//        ALREADY_WRITTEN_TO_FILE = false;
        initProcessing(null);
    }

    /**
     * stop dispatcher and collector
     **/
    private void stopProcessing() {
        metro.stop();

        if (dispatcher != null)
            dispatcher.stop();
        // dispatcher=null;

        // wenn man auf stop klickt muss definitiv aufhehoert werden!
        collector.stopCollecting(false);

        PROCESSING = false;

        firePropertyChange(ControllerEngine.START_STOP_PROCESSING_BUTTON_PROPERTY, "stop", audioFile==null? "rec": "process");
        firePropertyChange(ControllerEngine.INPUTLEVEL_PROPERTY, -1, 0);

        initProcessing(null);
    }

    /**
     * start or stop processing<br />
     * starts dispatcher and maybe metronome
     **/
    public void startStopProcessing(Object o) {
        if (PROCESSING) {
            stopProcessing();
        } else {
            stopMIDI();

            // jAM.GLOBAL_TIMESTAMP = System.currentTimeMillis();

            try {
                dispatcher.start();
            } catch (Exception e) {
                e.printStackTrace();
                jAM_error("Fehler beim Starten des dispatchers: " + e.getCause().toString());
            }

            // das model muss wissen ob metro selected is oder nich ! (es wird
            // von hier nichts an GUI abgefragt, sondern nur an GUI GEGEBEN!)
            // das Model wei¤ also am Anfang genmau was auf der GUI is
            // und dann bekommt es nur noch durch user actions seine befehle!
            if (metroSelected) {
                this.metro.start();
            }
            PROCESSING = true;

            firePropertyChange(ControllerEngine.START_STOP_PROCESSING_BUTTON_PROPERTY,"rec", "stop");
            initNewTuneAsString();
            updateScore("");
            firePropertyChange(ControllerEngine.UPDATE_SCORE_PROPERTY, "old", tuneAsString);
        }
    }

    /**
     * play the transcription
     **/
    public void playOrStopMIDI(Object o) {
        if (PLAYING_MIDI) {
            stopMIDI();
        } else {
            this.player.start();
            this.player.play(tune, tuneAsString);
            this.PLAYING_MIDI = true;
            firePropertyChange(ControllerEngine.MIDIBUTTON_NAME_PROPERTY,
                    "play", "stop");
        }
    }

    public void stopMIDI() {
        try {
            if (player != null && player.isPlaying()) {
                this.player.stopPlaying();
                this.PLAYING_MIDI = false;
                firePropertyChange(ControllerEngine.MIDIBUTTON_NAME_PROPERTY,
                        "stop", "play");
            }
        } catch (Exception e) {
            jAM_error("Model: Error - StopMIDI: " + e.getMessage());
        }
    }
    
    public void selectMixer(String selMixerName) {
        for (Mixer.Info info : SampledAudioUtilities.getMixerInfo(false, true)) {
            if (selMixerName.equals(info.toString())) {
                this.mixerInfo=info;
                //this.selectedMixer = AudioSystem.getMixer(info);
                
                String s=("selected mixerInfo: " + this.mixerInfo.toString()) + "\n";
                jAM.log(s, false);
                
                newProject(null);
            }
        }
    }

    // ----- functions fuer Abc Tune -----
    public void updateScore(String notesAsString) {
        tuneAsArray[INDEX] = notesAsString;

        String tuneAsString = "";
        for (int i = 0; i < tuneAsArray.length; i++) {
            tuneAsString += tuneAsArray[i];
        }

        parseAndSetTune(tuneAsString);
    }

    public void parseAndSetTune(String tuneAsString) {
        this.tuneAsString = tuneAsString;
        tune = new TuneParser().parse(tuneAsString);

//        extractTuneAsString(tune);
        
        // System.out.println("Backup: \n" + tuneAsString);
        firePropertyChange(ControllerEngine.UPDATE_SCORE_PROPERTY, "", tuneAsString);
    }
    
    public void transpose(Integer semitones) {
        tune = Tune.transpose(tune, semitones);
        
        String s = this.tuneAsString + "\n\n";
        
        String transposedNotes = extractTuneAsString(tune);
        updateScore(transposedNotes);
        
        s += "to: \n" + this.tuneAsString;
        jAM.log("Transposed ("+ semitones +" semitones) from:\n" + s, false);
        
        firePropertyChange(ControllerEngine.SHOW_TUNE_PROPERTY, "", tune);
    }
    
    private String extractTuneAsString(Tune tune) {
    	//zum extrahieren der Daten fŸr Evaluation:
        // TODO geht nur bis zu ner GANZEN.. und nur bis zu 16tel also keine
        // punktierte 16tel mehr..
        String s = "----- MODEL: EXTRAHIERE FUER EVALUATION-----\n";
        // MIDI Representation extrahieren! fuer evaluation !
        Iterator it = tune.getMusic().getVoices().iterator();
        String abcNotes="";
        boolean flatKey=false;
        
        while (it.hasNext()) {
            Voice voice = (Voice) it.next();
//            s += ("VOICE: " + voice) + "\n";

            //zuerst Tonart checken
            String tonart="";
            switch(voice.getKey().getNote()) {
            	case Note.A:
            		tonart="A";
            		break;
            	case Note.B:
            		tonart="B";
            		break;
            	case Note.C:
            		tonart="C";
            		break;
            	case Note.D:
            		tonart="D";
            		break;
            	case Note.E:
            		tonart="E";
            		break;
            	case Note.F:
            		tonart="F";
            		break;
            	case Note.G:
            		tonart="G";
            		break;
            }
            
            if(voice.getKey().hasOnlyFlats())tonart+="b";
    		else if(voice.getKey().hasOnlyFlats())tonart+="#";
            setTonart(tonart);
            
            flatKey=voice.getKey().hasOnlyFlats();
            
            int i = 0;
            while (i < voice.size()) {
                if (voice.elementAt(i) instanceof abc.notation.Note) {
                    // &&
                    // !((abc.notation.Note)voice.elementAt(i)).isEndingTie()) {

                    Note note = (Note) voice.elementAt(i);
                    // System.out.println(note);
                    int midiKey = (note.getMidiLikeHeight());
                    midiKey = (midiKey == -128) ? 0 : midiKey + 60; // -128 bei Pause ! sonst bei C == 0 also +60 !
                    
//                    System.out.print(note.getName() +  " ");
                    
                    s += (midiKey + ",");

                    if(note.isBeginingSlur())
                    	abcNotes += "(";
                    
                    if(midiKey==0)
                    	abcNotes += "z";
//                    else //wenn wir #'s haben weden andere Noten mit b gemalt ?
//                    	abcNotes += flatKey? jAMUtils.ABC_NOTES_FLAT[midiKey] : jAMUtils.ABC_NOTES_SHARP[midiKey];
                    else {
                    	String name = note.getName();
                    	abcNotes+= name.contains("#") ? "^" : "";
                    	abcNotes+= name.contains("b") ? "_" : "";
                    	abcNotes+= name.contains("=") ? "=" : "";
                    	
                    	name = name.replace("#","");
                    	name = name.replace("b","");
                    	name = name.replace("=","");

                    	switch (note.getOctaveTransposition()) {
						case 0:
							abcNotes += name;
							break;
						case 1:
							abcNotes += name.toLowerCase();
							break;
						case 2:
							abcNotes += name.toLowerCase() + "'";
							break;
						case 3:
							abcNotes += name.toLowerCase() + "''";
							break;
						case 4:
							abcNotes += name.toLowerCase() + "'''"; 
							break;
						case -1:
							abcNotes += name + ",";
							break;
						case -2:
							abcNotes += name + ",,";
							break;
						case -3:
							abcNotes += name + ",,";
							break;
						default:
							break;
						}
                    }
                    
                    int dur = 0;
                    switch (note.getDuration()) {
                    case Note.WHOLE:
                        dur = 16;
                        break;
                    case Note.DOTTED_HALF:
                        dur = 12;
                        break;
                    case Note.HALF:
                        dur = 8;
                        break;
                    case Note.DOTTED_QUARTER:
                        dur = 6;
                        break;
                    case Note.QUARTER:
                        dur = 4;
                        break;
                    case Note.DOTTED_EIGHTH:
                        dur = 3;
                        break;
                    case Note.EIGHTH:
                        dur = 2;
                        break;
                    case Note.SIXTEENTH:
                        dur = 1;
                        break;
                    default:
                        break;
                    }
                    s += (dur + ", ");
                    
                    
                    abcNotes+= (dur==1) ? "" : dur;
                    
                    if(note.isBeginningTie())
                    	abcNotes += "-";
                    
                    if(note.isEndingSlur())
                    	abcNotes += ")";
                    
                }else if (voice.elementAt(i) instanceof abc.notation.BarLine)
                	abcNotes+="|";
//                else if (voice.elementAt(i) instanceof abc.notation.)
                	
                i++;
            }
        }
        
        System.out.println();

//         if(!isEvaluating())
//        	 System.out.println("Referenzstring: " + s + "\n\nAbcNotes:\n" + abcNotes);
         
         return abcNotes;
    }
    
    public void saveTuneAsString(String s) {
    	lastTuneAsString=s;
    }
    public void ctrlz(Object o) {
//    	firePropertyChange(ControllerEngine.UPDATE_SCORE_PROPERTY, "", lastTuneAsString);
    	updateScore(lastTuneAsString);
    }

    private String TITEL = "unbenannt", 
    			   AUTOR = System.getProperty("user.name");
    
    private void initNewTuneAsString() {
        tuneAsArray = new String[] { "X:0" + NEWLINE,
                "T:" + TITEL + "\nC:" + AUTOR + NEWLINE,
                // "S:dis is the source of this tune" + NEWLINE,
                "Q:1/4 = " + bpm + NEWLINE, "M:" + TAKTART + NEWLINE,
                "L:1/16" + NEWLINE, "K:" + TONART + NEWLINE, "", // wird von noteDetection befuellt
        };
    }

    public void writeToScore(String name) {
        firePropertyChange(ControllerEngine.WRITE_SCORE_TO_IMAGE_PROPERTY, "", name);
    }

    public void writeScoreToMidi(String name) throws Exception {
        File file = new File(name);
        // from abc: Create a converter to convert a tune into midi sequence
        MidiConverterAbstract conv = new BasicMidiConverter();
        Sequence s = conv.toMidiSequence(tune);
        // All available midi file type for the tune's sequence
        int[] types = MidiSystem.getMidiFileTypes(s);
        // Write the sequence as a midi file.
        MidiSystem.write(s, types[0], file);
    }

    public boolean plottingSelected() {
        return this.plottingSelected;
    }

    public boolean lowPassSelected() {
        return this.lowPassSelected;
    }

    public int getTransposeRecIndex() {
        return this.transposeRecIndex;
    }

    public void setTransposeRecIndex(int i) {
        this.transposeRecIndex = i;
        firePropertyChange(ControllerEngine.TRANSPOSE_REC_PROPERTY, -1, i);
    }

    public void newProject(Object o) {
        if (PROCESSING)
            stopProcessing();

//        ALREADY_WRITTEN_TO_FILE = false;
        audioFile = null;
        
        setOverlap(0);
//        initProcessing(null);

        firePropertyChange(ControllerEngine.TITLE_PROPERTY, "", jAM.TITLE);
        firePropertyChange(ControllerEngine.INFO_LABEL_PROPERTY, "", "INFO");
        firePropertyChange(ControllerEngine.INPUTLEVEL_PROPERTY, -1, 0);
        firePropertyChange(ControllerEngine.START_STOP_PROCESSING_BUTTON_PROPERTY, "", "rec");

        firePropertyChange(ControllerEngine.METRO_PROPERTY, false, true);
        initNewTuneAsString();
        updateScore("");
    }

    public void saveProject(String name) throws Exception {
        File f = new File(name);
        FileWriter writer = new FileWriter(f);
        writer.write(tuneAsString);
        writer.flush();
        writer.close();
        writer = null;
    }

    public void saveWave(String name) {
        this.wfr.saveToDisk(name);
    }

    public void setTitle(String t) {
        firePropertyChange(ControllerEngine.TITLE_PROPERTY, "", t);
    }

    public void setBPM(Integer bpm) {
        this.bpm = bpm;
        this.metro.setBPM(bpm);
        collector.setBPM(bpm);
        firePropertyChange(ControllerEngine.BPM_PROPERTY, 0, bpm);
    }

    private String[] tonarten = new String[] { "C", "G", "D", "A", "E", "B",
            "F#", "F", "Bb", "Eb", "Ab", "Db", "C#", "Gb" };

    public void setTonart(String t) {
        this.TONART = TONART.contains(" bass") ?  (t + " bass") : t;
        initNewTuneAsString();
        updateScore("");
        int idx = 0;
        for (int i = 0; i < tonarten.length; i++) {
            if (t.equals(tonarten[i])) {
                idx = i;
                break;
            }
        }
        collector.setTonart(idx);
        firePropertyChange(ControllerEngine.TONART_PROPERTY, "", TONART);
    }

    public void setClef(Boolean bassClef) {
    	jAM.log("setClef: " + (bassClef ? "bass clef": "violin clef") , false);
        if (bassClef && !TONART.contains(" bass"))
            this.TONART += " bass";
        else
            this.TONART = this.TONART.replace(" bass", "");

        initNewTuneAsString();
        updateScore("");
    }

    public void setTaktart(Integer index) {
        switch (index) {
        case 0: // 4/4tel
            this.TAKTART = "4/4";
            this.TAKTARTINDEX = 0;
            this.collector.setTaktart(4, 4);
            break; // 3/4tel
        case 1:
            this.TAKTART = "3/4";
            this.TAKTARTINDEX = 1;
            this.collector.setTaktart(3, 4);
            break;
        case 2: // 5/4tel
            this.TAKTART = "5/4";
            this.TAKTARTINDEX = 2;
            this.collector.setTaktart(5, 4);
            break;
        }
        initNewTuneAsString();
        updateScore("");
        firePropertyChange(ControllerEngine.TAKTART_PROPERTY, "", TAKTART);
    }

    public void setTransposeRecording(Integer idx) {
        this.transposeRecIndex = idx;
    }

    public void setMetroSelected(Boolean s) {
        this.metroSelected = s;
    }

    public void setPlaybackSelected(Boolean s) {
        this.playbackSelected = s;
        initProcessing(null);
    }

    public void setPlottingSelected(Boolean s) {
        this.plottingSelected = s;
    }

    public void setLowPassEnabled(Boolean s) {
        this.lowPassSelected = s;
    }

    public void setChunk(Integer CHUNK) {
        this.CHUNK = CHUNK;
        this.OVERLAP = CHUNK * overlapPercentage / 100;
        if (!isEvaluating())
            initProcessing(null);
        firePropertyChange(ControllerEngine.CHUNK_PROPERTY, -1, CHUNK);
    }

    public void setOverlap(Integer overlapPercentage) {
        this.overlapPercentage = overlapPercentage;
        this.OVERLAP = CHUNK * overlapPercentage / 100;
        if (!isEvaluating())
            initProcessing(null);
        
        firePropertyChange(ControllerEngine.OVERLAP_PROPERTY, -1, overlapPercentage);
    }

    public void setPDA(String pda) {
        collector.setPITCHDETECTOR(pda);
    }

    public int getChunk() {
        return CHUNK;
    }

    public int getOverlap() {
        return OVERLAP;
    }

    public int[] getChunks() {
        return chunks;
    }

    public int[] getOverlaps() {
        return overlaps;
    }

    public void setYinThreshold(Float t) {
        collector.setYinTreshold(t);
        // firePropertyChange(propertyName, oldValue, newValue)
    }

    public int getMinDur() {
        return collector.getMinDur();
    }

    public float getMinLev() {
        return collector.getMinLev();
    }

    public void setMpmThreshold(Float t) {
        collector.setMpmTreshold(t);
        // firePropertyChange(propertyName, oldValue, newValue)
    }

    public void setMinDuration(Integer d) {
        collector.setMinDur(d);
        // firePropertyChange(propertyName, oldValue, newValue)
    }

    public void setMinLevel(Float l) {
        collector.setMinLev(l);
        // firePropertyChange(propertyName, oldValue, newValue)
    }

    private TargetDataLine getLine(DataLine.Info dataLineInfo, boolean first) throws Exception {
        // line = AudioSystem.getTargetDataLine(format);
        if (first)
            return (TargetDataLine) AudioSystem.getLine(dataLineInfo);
        else {
            //wir haben das mixerInfo gespeichert beim Auswaehlen auf ui:
            Mixer selectedMixer = AudioSystem.getMixer(this.mixerInfo);
            return (TargetDataLine) selectedMixer.getLine(dataLineInfo);
        }
    }

    private AudioInputStream fromMic() throws LineUnavailableException {
        int sampleSizeInBits = 16; 
        boolean mono = true;
        // new AudioFormat(sampleRate, sampleSizeInBits, channels, signed,
        // bigEndian) //default
        format = new AudioFormat(Encoding.PCM_SIGNED, // encoding
                SAMPLERATE, // sampleRate
                sampleSizeInBits, // sampleSizeInBits
                mono ? 1 : 2, // channels
                sampleSizeInBits / 8, // frameSize
                SAMPLERATE, // frameRate (==samplerate)
                // true, //signed
                false); // bigEndian

        final DataLine.Info dataLineInfo = new DataLine.Info(
                TargetDataLine.class, format);

        if (!AudioSystem.isLineSupported(dataLineInfo)) {
            jAM_error("Line matching " + dataLineInfo + " not supported.");
            return null;
        }

        // ----- FIRST TIME: -----
        //beim Ersten Mal per AudioSystem.getLine()
        if (mixerInfo == null) {
            try {
                if(line==null)
                    line = getLine(dataLineInfo, true); //NUR EINMAL HOLEN!
                line.open(format, CHUNK);
                jAM.log("Model: Got Line and its open ! FORMAT TO CAPTURE: " + format + "\n", false);

            } catch (Exception e) {
                e.printStackTrace();
                jAM_error("Fehler beim MicCheck. Waehle einen anderen Sound-Eingang! Error beim Holen und Oeffnen der ERSTEN Line. siehe stacktrace \n"+ e.getMessage());
            }
            /*** no good idea
            for (Mixer.Info info : SampledAudioUtilities.getMixerInfo(false, true)) {
                Mixer selectedMixer = AudioSystem.getMixer(info); // den Ersten nehmen
                                                            // !
                if (selectedMixer.isLineSupported(dataLineInfo)) {

                    //jAM.log("Model: TRYING FIRST suitable Selected mixer: "+ info.toString() + " MIXER: " + selectedMixer,false);

                    try {
                        line = getLine(dataLineInfo, true);
                        line.open(format, CHUNK);
                        jAM.log("Model: Got Line and its open ! FORMAT TO CAPTURE: " + format + "\n", false);
                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                        jAM_error("Fehler beim MicCheck. Waehle einen anderen Sound-Eingang! Error beim Holen und Oeffnen der ERSTEN Line. siehe stacktrace "+ e.getMessage());
                    }
                }
                ******/

            // ----- sonst: -----
        } else {
            try {
                line = getLine(dataLineInfo, false);
            } catch (Exception e) {
                e.printStackTrace();
                jAM_error("Fehler beim MicCheck.(get) Waehle einen anderen Sound-Eingang! \n" + e.getMessage());
            }
            try {
//                System.out.println("--> " + line.isOpen());
//                System.out.println("--> " + line.isActive());
//                System.out.println("--> " + line.isRunning());
//                if(!line.isOpen())
                line.open(format, CHUNK);
            } catch (Exception e) {
                e.printStackTrace();
                jAM_error("Fehler beim MicCheck.(open) Waehle einen anderen Sound-Eingang! \n" + e.getMessage());
            }
            jAM.log("Model: Line already open FORMAT TO CAPTURE: " + format + "\nSelected Mixer: " + mixerInfo.toString() + "\n", false);
        }

        // line.start(); //see dispatcher.start() !
        return new AudioInputStream(line);
    }

    private AudioInputStream fromFile(File audioFile) {
        if (!audioFile.exists()) {
            System.err.println("file not found: " + audioFile.getAbsolutePath());
            return null;
        }
        // try {
        // WavFile wavFile = WavFile.openWavFile(new File(filename));
        // System.out.println("------------------------------------------------");
        // wavFile.display();
        // System.out.println("------------------------------------------------");
        // // Get the number of audio channels in the wav file
        // int numChannels = wavFile.getNumChannels();
        // }catch (Exception e) {
        // e.printStackTrace();
        // }
        AudioInputStream stream;
        try {
            stream = AudioSystem.getAudioInputStream(audioFile);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        jAM.log("Model: ===== Loading file " + audioFile.getAbsolutePath() + "\nFORMAT: " + stream.getFormat(), false);
        return stream;
    }

    private AudioDispatcher dispatcherTuner;

    public void startChromaticTuner(Object o) {
        if (PROCESSING)
            stopProcessing();

        try {
            final int usedCHUNK = 4096;

            AudioInputStream stream = fromMic();
            dispatcherTuner = new AudioDispatcher(stream, line, usedCHUNK,
                    OVERLAP);
            dispatcherTuner.addAudioProcessor(new AudioProcessor() {
                private Yin yin = new Yin(SAMPLERATE, usedCHUNK);

                public void processFull(float[] audioFloatBuffer,
                        byte[] audioByteBuffer) {
                    float pitch = yin.getPitch(audioFloatBuffer);
                    // System.out.println(pitch);
                    Pitch p = Pitch.getInstance(PitchUnit.HERTZ, pitch);
                    String[] arr = p.getBaseNote(pitch).split(" ");
                    String note = p.noteName(), note2 = arr[0];
                    int oktave = Integer.parseInt(arr[1]);

                    // String str = pitch == -1 ? "NO PITCH DETECTED"
                    // : (note + " at " + String.format("%.5g%n", pitch) +
                    // "Hz - IDEAL: " + String.format("%.5g%n",
                    // p.getIdealFreq(note2 , oktave)) +
                    // "Hz - (BufferSize: "+usedCHUNK + ")");

                    String[] ret = new String[] { note, "" + pitch,
                            "" + p.getIdealFreq(note2, oktave) };

                    firePropertyChange(ControllerEngine.UPDATE_TUNER_PROPERTY,
                            "", ret);
                }

                public void processOverlapping(float[] audioFloatBuffer,
                        byte[] audioByteBuffer) {
                    processFull(audioFloatBuffer, audioByteBuffer);
                }

                public void processingFinished() {
                    jAM.log("Model: --- Finished Tuning ---", false);
                }
            });
            dispatcherTuner.start();
            firePropertyChange(ControllerEngine.CHROMATIC_TUNER_START_PROPERTY,
                    "", "showTunerUI");
            jAM.log("Model: Tuner started", false);

        } catch (Exception e) {
            e.printStackTrace();
            jAM_error("Error while tuning: " + e.getMessage());
        }
    }

    public void stopChromaticTuner(Object o) {
        dispatcherTuner.stop();
        dispatcherTuner = null;
        initProcessing(null);
        jAM.log("Model: Tuner stopped", false);
    }

    private MidiMetronome m;

    public void showMetro(Object o) {
        firePropertyChange(ControllerEngine.METRO_SHOW_PROPERTY, "", "showIt");
        // jAM.log("Model: Tuner showed up", false);
        m = new MidiMetronome(60);
    }

    public void startMetro(Integer bpm) {
        // firePropertyChange(ControllerEngine.METRO_START_PROPERTY, "",
        // "startIt"); //???
        m.stop();
        m.setBPM(bpm);
        m.start();
        jAM.log("Model: Metro started", false);
    }

    public void stopMetro(Object o) {
        m.stop();
        jAM.log("Model: Metro stopped", false);
    }
}