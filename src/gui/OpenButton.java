package gui;

import java.io.File;
import java.io.IOException;

import data.MicrotonalFile;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class OpenButton extends Button {

	private final FileChooser fileChooser = new FileChooser();

	public OpenButton(PianoRollApp app, Stage stage) {
		super("Open");
		setOnAction(e -> {
			File file = fileChooser.showOpenDialog(stage);
			if (file != null) {
				try {
					app.openMain(MicrotonalFile.load(file.getAbsolutePath()));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
	}
}
