package gui;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import audio.Tone;
import data.BeatSystem;
import data.MicrotonalFile;
import data.MidiRollKey;
import data.ToneSystem;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class SheetPane extends Pane {

	private boolean clickStarted = false;
	private double startX;
	private double startY;
	private MicrotonalFile microtonalFile;

	public SheetPane(MicrotonalFile microtonalFile) {
		this.microtonalFile = microtonalFile;
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

		setOnMousePressed(e -> {
			clickStarted = true;
			startX = e.getX();
			startY = e.getY();
		});
		setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (clickStarted) {
					int height = getHeightFromY(startY);
					int startTime = getTimeFromX(startX);
					int endTime = getTimeFromX(event.getX());
					int duration = endTime - startTime + 1;
					if (duration > 0) {
						if (spaceBlocked(height, startTime, endTime)) {
							List<MidiRollKey> keysToRemove = new LinkedList<>();
							for (MidiRollKey key : microtonalFile.getNotes().keySet()) {
								if (spaceBlocked(key, height, startTime, endTime)) {
									getChildren().remove(toneRects.get(key));
									toneRects.remove(key);
									keysToRemove.add(key);
								}
							}
							keysToRemove.forEach(k -> microtonalFile.getNotes().remove(k));
						} else {
							MidiRollKey key = new MidiRollKey(height, startTime);
							ToneRect newTone = new ToneRect(height, startTime, duration);
							getChildren().add(newTone);
							toneRects.put(key, newTone);
							microtonalFile.getNotes().put(key, duration);
						}
					}
					clickStarted = false;
					System.out.println(String.format("onMouseReleased x=%s y=%s", event.getX(), event.getY()));
				}
			}
		});

	}

	private boolean spaceBlocked(int height, int startTime, int endTime) {
		for (MidiRollKey key : microtonalFile.getNotes().keySet()) {
			if (spaceBlocked(key, height, startTime, endTime)) {
				return true;
			}
		}
		return false;
	}

	private boolean spaceBlocked(MidiRollKey key, int height, int startTime, int endTime) {
		return height == key.getHeight() && key.getTime() <= endTime
				&& key.getTime() + microtonalFile.getNotes().get(key) >= startTime;
	}

	private int getHeightFromY(double y) {
		return (int) Math.floor(y / MainPane.CELL_SIZE);
	}

	private int getTimeFromX(double x) {
		return (int) Math.floor(x / MainPane.CELL_SIZE);
	}
}
