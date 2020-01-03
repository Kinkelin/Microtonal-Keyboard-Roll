package gui;

import java.util.HashMap;
import java.util.Map;

import audio.Tone;
import data.BeatSystem;
import data.MicrotonalFile;
import data.MidiRollKey;
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
		ToneSystem toneSystem = microtonalFile.getToneSystem();
		BeatSystem beatSystem = microtonalFile.getBeatSystem();
		Tone[] tones = toneSystem.getTones(beatSystem);
		int unitsPerBar = beatSystem.getNumberOfBeatsPerBar() * beatSystem.getNumberOfUnitsPerBeat();
		int height = tones.length * MainPane.CELL_SIZE;
		int width = unitsPerBar * microtonalFile.getLength() * MainPane.CELL_SIZE;

		getChildren().add(new Rectangle(width, height, Color.WHITE));

		for (int bar = 0; bar < microtonalFile.getLength(); bar++) {
			for (int beat = 0; beat < beatSystem.getNumberOfBeatsPerBar(); beat++) {
				for (int unit = 0; unit < beatSystem.getNumberOfUnitsPerBeat(); unit++) {
					int x = (bar * unitsPerBar + beat * beatSystem.getNumberOfUnitsPerBeat() + unit)
							* MainPane.CELL_SIZE;
					Line line = new Line(x, 0, x, height);
					line.setStroke(unit == 0 ? Color.BLACK : Color.DARKGREY);
					line.setStrokeWidth(unit == 0 ? beat == 0 ? 3 : 2 : 1);
					getChildren().add(line);
				}
			}
		}
		for (int i = 0; i < tones.length; i++) {
			int y = i * MainPane.CELL_SIZE;
			Line line = new Line(0, y, width, y);
			line.setStroke(Color.DARKGREY);
			getChildren().add(line);
		}

		Map<MidiRollKey, ToneRect> toneRects = new HashMap<>();
		
		for (MidiRollKey key : microtonalFile.getNotes().keySet()) {
			ToneRect newTone = new ToneRect(key.getHeight(), key.getTime(), 1);
			getChildren().add(newTone);
			toneRects.put(key, newTone);
		}
		
		setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
//				System.out.println("Mouse click position: " + event.getX() + " | " + event.getY());
//				System.out.println("bar: " + (event.getX() / (MainPane.CELL_SIZE * unitsPerBar)));
//				System.out.println("beat: " + ((event.getX() / (MainPane.CELL_SIZE * beatSystem.getNumberOfUnitsPerBeat()))
//						% beatSystem.getNumberOfBeatsPerBar()));
//				System.out.println("unit: " + ((event.getX() / MainPane.CELL_SIZE) % beatSystem.getNumberOfUnitsPerBeat()));
				int height = (int) Math.floor(event.getY() / MainPane.CELL_SIZE);
				int time = (int) Math.floor(event.getX() / MainPane.CELL_SIZE);
				MidiRollKey key = new MidiRollKey(height, time);
//				System.out.println("height: " + height + " time: " + time);
				if (toneRects.containsKey(key)) {
					getChildren().remove(toneRects.get(key));
					toneRects.remove(key);
					microtonalFile.getNotes().remove(key);
				} else {
					ToneRect newTone = new ToneRect(height, time, 1);
					getChildren().add(newTone);
					toneRects.put(key, newTone);
					microtonalFile.getNotes().put(key, 1);
				}
			}
		});
	}

}
