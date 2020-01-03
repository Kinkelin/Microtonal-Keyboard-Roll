package audio;

import java.util.Map;
import java.util.stream.Collectors;

import data.BeatSystem;
import data.MicrotonalFile;
import data.MidiRollKey;
import data.ToneSystem;

public class FilePlayer extends Thread {

	private MicrotonalFile file;

	public FilePlayer(MicrotonalFile file) {
		this.file = file;
	}

	@Override
	public void run() {
		ToneSystem toneSystem = file.getToneSystem();
		BeatSystem beatSystem = file.getBeatSystem();
		Tone[] tones = toneSystem.getTones(beatSystem);
		long sleepTime = (long) Math.round(1000 * 60 / (beatSystem.getBeatsPerMinute() * beatSystem.getNumberOfUnitsPerBeat()));

		for (int i = 0; i < file.getLength() * beatSystem.getNumberOfBeatsPerBar()
				* beatSystem.getNumberOfUnitsPerBeat(); i++) {
			final int time = i;
			Map<MidiRollKey, Integer> currentNotes = file.getNotes().entrySet().stream()
					.filter(e -> e.getKey().getTime() == time)
					.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
			for (Map.Entry<MidiRollKey, Integer> entry : currentNotes.entrySet()) {
				tones[entry.getKey().getHeight()].play(entry.getValue());
			}
			try {
				sleep(sleepTime);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
	}
}
