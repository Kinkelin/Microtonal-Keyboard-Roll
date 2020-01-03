package audio;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import data.BeatSystem;
import data.MicrotonalFile;
import data.MidiRollKey;
import data.ToneSystem;
import data.ToneSystemFile;

public class WavFileManager {

	public void createMissingAudio(MicrotonalFile microtonalFile) {
		ToneSystem toneSystem = microtonalFile.getToneSystem();
		BeatSystem beatSystem = microtonalFile.getBeatSystem();
		ToneSystem existingToneSystem = null;
		try {
			existingToneSystem = ToneSystemFile.load(toneSystem.getName()).getToneSystem();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Tone[] tones = toneSystem.getTones(microtonalFile.getBeatSystem());
		if (existingToneSystem == null || !existingToneSystem.equals(toneSystem)) {

			for (File file : new File("audio " + toneSystem.getName()).listFiles()) {
				if (!file.isDirectory()) {
					file.delete();
				}
			}

			for (Tone tone : tones) {
				String wavFileName = "audio/temp/" + tone.getFormattedFrequency() + ".wav";
				WavFileWriter.SINGLETON.writeWavFile((float) tone.getFrequency(), beatSystem.getUnitDurationInSeconds(),
						wavFileName);
			}
		}
		for (Entry<MidiRollKey, Integer> note : microtonalFile.getNotes().entrySet()) {
			Tone tone = tones[note.getKey().getHeight()];
			if (missing)
				WavFileWriter.SINGLETON.writeWavFile((float) tone.getFrequency(),
						note.getValue() * beatSystem.getUnitDurationInSeconds(),
						tone.getFormattedFrequency() + "_" + String.valueOf(beatSystem.getUnitDurationInSeconds()));
		}

	}
}
