package gui;

import audio.Tone;
import data.KeyColor;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class MidiSquarePane extends StackPane {

	private boolean hasTone;
	private Tone tone;
	private Rectangle rect;

	public MidiSquarePane(Tone tone) {
		this.tone = tone;
		Region rect = new Region();
		rect.setStyle(
				"-fx-background-color: white" + "; -fx-border-style: solid; -fx-border-width: 1; -fx-border-color: grey"
						+ "; " + "-fx-min-width: 24; -fx-min-height:24; -fx-max-width:24; -fx-max-height: 24;");
		getChildren().addAll(rect);
		setOnMouseClicked(e -> {
			toggleTone();
		});
	}
	
	private void toggleTone() {
		hasTone = !hasTone;
		if (hasTone) {
			tone.play(-1);
			rect = new Rectangle(24, 24);
			rect.setFill(Color.GREEN);
			getChildren().add(rect);
		} else {
			getChildren().remove(rect);
		}
	}
}
