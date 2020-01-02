package test;

import audio.Tone;
import data.BeatSystem;
import data.ToneSystem;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class GUITest extends Application {

	@Override
	public void start(Stage stage) {
		ScrollPane pane = new ScrollPane();
		final Scene scene = new Scene(pane, 300, 250);
		Pane box = new Pane();

		int cellSize = 24;
		int unitsPerBar = 16;
		int height = 120 * cellSize;
		int width = unitsPerBar * 16 * cellSize;

		box.getChildren().add(new Rectangle(width, height, Color.WHITE));

		for (int bar = 0; bar < 16; bar++) {
			for (int beat = 0; beat < 4; beat++) {
				for (int unit = 0; unit < 4; unit++) {
					int x = (bar * unitsPerBar + beat * 4 + unit) * cellSize;
					Line line = new Line(x, 0, x, height);
					line.setStroke(unit == 0 ? Color.BLACK : Color.DARKGREY);
					line.setStrokeWidth(unit == 0 ? beat == 0 ? 3 : 2 : 1);
					box.getChildren().add(line);
				}
			}
		}
		for (int i = 0; i < 120; i++) {
			int y = i * cellSize;
			Line line = new Line(0, y, width, y);
			line.setStroke(Color.DARKGREY);
			box.getChildren().add(line);
		}

		box.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				System.out.println("Mouse click position: " + event.getX() + " | " + event.getY());
				System.out.println("bar: " + (event.getX() / (cellSize  * unitsPerBar)));
				System.out.println("beat: " + ((event.getX() / (4*cellSize)) % 4));
				System.out.println("unit: " + ((event.getX() /cellSize)% 4));
			}
		});

		pane.setContent(box);
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
