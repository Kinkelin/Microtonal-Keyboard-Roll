package gui;

import java.io.File;

import audio.AudioLoader;
import audio.FilePlayer;
import data.MicrotonalFile;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MenuBand extends HBox {
	private final FileChooser fileChooser = new FileChooser();

	public MenuBand(PianoRollApp app, Stage stage, MainPane mainPane) {
		MicrotonalFile microtonalFile = mainPane.getMicrotonalFile();
		Button playButton = new Button("Play");
		playButton.setOnAction(e -> new AudioLoader(microtonalFile, new FilePlayer(microtonalFile)));

		Button infoButton = new Button("Info");
		infoButton.setOnAction(e -> new AboutInformation().showAndWait());

		Button saveButton = new Button("Save");
		saveButton.setOnAction(e -> {
			fileChooser.setInitialDirectory(new File(String.join(File.separator, "resources", "files")));
			fileChooser.getExtensionFilters()
					.add(new FileChooser.ExtensionFilter("Microtonal files (*.microtonal)", "*.microtonal"));
			File file = fileChooser.showSaveDialog(stage);
			if (file != null) {
				microtonalFile.setFilePath(file.getAbsolutePath());
				microtonalFile.save();
			}
		});

		Button openButton = new OpenButton(app, stage);

		Button closeButton = new Button("Close");
		closeButton.setOnAction(e -> app.openStart());

		getChildren().addAll(playButton, saveButton, openButton, closeButton, infoButton);
	}
}
