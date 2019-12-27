package gui;

import java.io.File;
import java.nio.file.Files;

import data.ToneSystem;
import javafx.scene.layout.VBox;

public class PianoPane extends VBox {

	public PianoPane(ToneSystem toneSystem) {

		for (File file : new File("resources/audio/temp").listFiles()) {
			if (!file.isDirectory()) {
				file.delete();
			}
		}
		for (int i = 0; i < toneSystem.getNumberOfOctaves(); i++) {
			for (int j = 0; j < toneSystem.getTonesPerOctave(); j++) {
				double frequency = (double) toneSystem.getTopFrequency() * Math.pow(2.0, -1.0
						* ((toneSystem.getTonesPerOctave() * i + j + 1.0) / (double) toneSystem.getTonesPerOctave()));
				int currentKey = toneSystem.getTonesPerOctave() - j - 1;
				getChildren().add(new TonePane(frequency, toneSystem.getWhiteKeys()[currentKey],
						toneSystem.getToneNames()[currentKey]));
			}
		}

	}
}
