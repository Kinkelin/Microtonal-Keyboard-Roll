package audio;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;
import java.util.Map.Entry;

import data.BeatSystem;
import data.MicrotonalFile;
import data.MidiRollKey;
import data.ToneSystem;
import data.ToneSystemFile;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class WavFileGenerator extends Thread {

	public static final String AUDIO_DIRECTORY = String.join(File.separator, "resources", "audio", "tonesystems")
			+ File.separator;
	public static final String WAV_SUFFIX = ".wav";

	private File directory;
	private ToneSystem toneSystem;
	private BeatSystem beatSystem;
	private Tone[] tones;
	private Map<MidiRollKey, Integer> notes;
	private Thread successor;

	private IntegerProperty progress = new SimpleIntegerProperty(0);
	private int numberOfAudioFiles;
	private BooleanProperty finished = new SimpleBooleanProperty(false);
	
	public IntegerProperty progressProperty() {
		return progress;
	}
	
	public BooleanProperty finishedProperty() {
		return finished;
	}

	public int getNumberOfAudioFiles() {
		return numberOfAudioFiles;
	}
	
	public void setSuccessor(Thread successor) {
		this.successor = successor;
	}

	public WavFileGenerator(MicrotonalFile microtonalFile) {
		toneSystem = microtonalFile.getToneSystem();
		beatSystem = microtonalFile.getBeatSystem();
		tones = toneSystem.getTones(microtonalFile.getBeatSystem());
		notes = microtonalFile.getNotes();
		directory = new File(AUDIO_DIRECTORY + toneSystem.getName());

		progress.set(0);
		numberOfAudioFiles = tones.length + notes.size();
	}

	@Override
	public void run() {
		finished.set(false);
		progress.set(0);
		numberOfAudioFiles = tones.length + notes.size();
		try {
			prepareDirectory();
			createDefaultWavFiles();
			createSpecialWavFiles();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finished.set(true);
		if (successor != null) {
			successor.start();
		}
	}

	/**
	 * Makes sure, that the directory exists and doesn't contain any false wav files
	 */
	private void prepareDirectory() throws IOException {
		ToneSystem existingToneSystem = null;
		try {
			ToneSystemFile toneSystemFile = ToneSystemFile.loadByName(toneSystem.getName());
			if (toneSystemFile != null) {
				existingToneSystem = toneSystemFile.getToneSystem();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (!directory.exists()) {
			Files.createDirectory(directory.toPath());
		}
		if (existingToneSystem == null || !existingToneSystem.equals(toneSystem)) {
			for (File file : directory.listFiles()) {
				file.delete();
			}
			new ToneSystemFile(ToneSystemFile.getFilePath(toneSystem.getName()), toneSystem).save();
		}
	}

	/**
	 * Creates wav files for all tones with a duration of one unit
	 */
	private void createDefaultWavFiles() throws IOException {
		for (Tone tone : tones) {
			String fileName = directory.getCanonicalPath() + File.separator + tone.getFormattedFrequency() + "_1"
					+ WAV_SUFFIX;
			File wavFile = new File(fileName);
			if (!wavFile.exists()) {
				System.out.println(String.format("write wav file freq = %f ; unit duration = %f ; filename = %s",
						(float) tone.getFrequency(), beatSystem.getUnitDurationInSeconds(), fileName));
				WavFileWriter.SINGLETON.writeWavFile((float) tone.getFrequency(), beatSystem.getUnitDurationInSeconds(),
						fileName);
			}
			tone.addWavFile(1, fileName);
			progress.set(progress.get() + 1);
		}
	}

	/**
	 * Creates wav files for all tone and duration combinations that are used in the
	 * notes
	 */
	private void createSpecialWavFiles() throws IOException {
		for (Entry<MidiRollKey, Integer> note : notes.entrySet()) {
			Tone tone = tones[note.getKey().getHeight()];
			String fileName = directory.getCanonicalPath() + File.separator + tone.getFormattedFrequency() + "_"
					+ note.getValue() + WAV_SUFFIX;
			File wavFile = new File(fileName);
			if (!wavFile.exists()) {
				WavFileWriter.SINGLETON.writeWavFile((float) tone.getFrequency(),
						note.getValue() * beatSystem.getUnitDurationInSeconds(), fileName);
			}
			tone.addWavFile(note.getValue(), fileName);
			progress.set(progress.get() + 1);
		}
	}

}
