package gui;

import java.io.File;
import java.nio.file.Files;

import audio.Tone;
import data.BeatSystem;
import data.MicrotonalFile;
import data.ToneSystem;
import javafx.scene.layout.VBox;

public class PianoPane extends VBox {

	public PianoPane(MicrotonalFile microtonalFile) {
		ToneSystem toneSystem = microtonalFile.getToneSystem();
		BeatSystem beatSystem = microtonalFile.getBeatSystem();
		for (Tone tone : toneSystem.getTones(beatSystem)) {
			getChildren().add(new PianoKeyPane(tone));
		}

	}
}
