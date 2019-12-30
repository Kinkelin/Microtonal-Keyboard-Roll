package gui;

import audio.Tone;
import data.MicrotonalFile;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class SheetPane extends HBox {

	public SheetPane(MicrotonalFile microtonalFile) {
		int height = microtonalFile.getToneSystem().getTones(microtonalFile.getBeatSystem()).length * 24;
		
		for (int bar = 0; bar < microtonalFile.getLength(); bar++) {
			for (int beat = 0; beat < microtonalFile.getBeatSystem().getNumberOfBeatsPerBar(); beat++) {
				for (int unit = 0; unit < microtonalFile.getBeatSystem().getNumberOfUnitsPerBeat(); unit++) {
					VBox midiSquaresColumn = new VBox();
					for (Tone tone : microtonalFile.getToneSystem().getTones(microtonalFile.getBeatSystem())) {
						midiSquaresColumn.getChildren().add(new MidiSquarePane(tone));
					}
					getChildren().add(midiSquaresColumn);
				}
				getChildren().add(new Rectangle(3, height));
			}
			getChildren().add(
					new Rectangle(8, height));
		}
	}

}
