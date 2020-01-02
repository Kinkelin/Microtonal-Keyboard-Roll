package gui;

import audio.Tone;
import data.BeatSystem;
import data.MicrotonalFile;
import data.ToneSystem;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class SheetPane extends Pane {

	public SheetPane(MicrotonalFile microtonalFile) {
		int cellSize = 24;
		ToneSystem toneSystem = microtonalFile.getToneSystem();
		BeatSystem beatSystem = microtonalFile.getBeatSystem();
		Tone[] tones = toneSystem.getTones(beatSystem);
		int unitsPerBar = beatSystem.getNumberOfBeatsPerBar() * beatSystem.getNumberOfUnitsPerBeat();
		int height = tones.length * cellSize;
		int width = unitsPerBar * microtonalFile.getLength() * cellSize;

		getChildren().add(new Rectangle(width, height, Color.WHITE));

		for (int bar = 0; bar < microtonalFile.getLength(); bar++) {
			for (int beat = 0; beat < beatSystem.getNumberOfBeatsPerBar(); beat++) {
				for (int unit = 0; unit < beatSystem.getNumberOfUnitsPerBeat(); unit++) {
					int x = (bar * unitsPerBar + beat * beatSystem.getNumberOfUnitsPerBeat() + unit) * cellSize;
					Line line = new Line(x, 0, x, height);
					line.setStroke(unit == 0 ? Color.BLACK : Color.DARKGREY);
					line.setStrokeWidth(unit == 0 ? beat == 0 ? 3 : 2 : 1);
					getChildren().add(line);
				}
			}
		}
		for (int i = 0; i < tones.length; i++) {
			int y = i * cellSize;
			Line line = new Line(0, y, width, y);
			line.setStroke(Color.DARKGREY);
			getChildren().add(line);
		}

		setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				System.out.println("Mouse click position: " + event.getX() + " | " + event.getY());
				System.out.println("bar: " + (event.getX() / (cellSize * unitsPerBar)));
				System.out.println("beat: " + ((event.getX() / (cellSize * beatSystem.getNumberOfUnitsPerBeat()))
						% beatSystem.getNumberOfBeatsPerBar()));
				System.out.println("unit: " + ((event.getX() / cellSize) % beatSystem.getNumberOfUnitsPerBeat()));
			}
		});
	}

}
