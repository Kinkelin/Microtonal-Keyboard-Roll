package gui;

import audio.Tone;
import data.KeyColor;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class PianoKeyPane extends StackPane {

	public PianoKeyPane(Tone tone) {
		Region rect = new Region();
		KeyColor keyColor = tone.getKeyColor();
		String height = String.valueOf(MainPane.CELL_SIZE);
		rect.setStyle("-fx-background-color: " + keyColor.getBackgroundColor()
				+ "; -fx-border-style: solid; -fx-border-width: 1; -fx-border-color: " + keyColor.getBorderColor()
				+ "; " + "-fx-min-width: 128; -fx-min-height:" + height + "; -fx-max-width:128; -fx-max-height: "
				+ height + ";");
		Text text = new Text(tone.getDisplayName());
		text.setFill(keyColor.getTextColor());
		getChildren().addAll(rect, text);
		setOnMouseClicked(e -> tone.play(1));

	}

}
