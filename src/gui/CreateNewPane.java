package gui;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import data.BeatSystem;
import data.MicrotonalFile;
import data.ToneSystem;
import data.ToneSystemFile;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class CreateNewPane extends VBox {

	private final FileChooser fileChooser = new FileChooser();
	private TextField toneSystemFilePath;
	private Button toneSystemChooser;
	private Text unitsPerBeatLabel;
	private TextField unitsPerBeat;
	private Text beatsPerBarLabel;
	private TextField beatsPerBar;
	private Button backButton;
	private Button okButton;
	private Text bpmLabel;
	private TextField bpm;

	public CreateNewPane(PianoRollApp pianoRollApp, Stage stage) {

		toneSystemFilePath = new TextField();
		toneSystemChooser = new Button("Choose tone system");
		toneSystemChooser.setOnAction(e -> {
			fileChooser.setInitialDirectory(new File("resources//toneSystems"));
			File file = fileChooser.showOpenDialog(stage);
			if (file != null) {
				toneSystemFilePath.setText(file.getAbsolutePath());
			}
		});

		unitsPerBeatLabel = new Text("Units per beat");
		unitsPerBeat = new TextField("4");

		beatsPerBarLabel = new Text("Beats per bar");
		beatsPerBar = new TextField("4");

		bpmLabel = new Text("Beats per minute");
		bpm = new TextField("120");

		backButton = new Button("Back");
		backButton.setOnAction(e -> pianoRollApp.openStart());

		okButton = new Button("Ok");
		okButton.setOnAction(e -> {
			try {

				ToneSystem toneSystem = ToneSystemFile.load(toneSystemFilePath.getText()).getToneSystem();
				BeatSystem beatSystem = new BeatSystem(Integer.valueOf(unitsPerBeat.getText()),
						Integer.valueOf(beatsPerBar.getText()), Integer.valueOf(bpm.getText()));
				pianoRollApp.openMain(new MicrotonalFile(null, toneSystem, beatSystem, new HashMap<>()));
			} catch (NumberFormatException | IOException e1) {
				e1.printStackTrace();
			}
		});

		getChildren().addAll(toneSystemChooser, toneSystemFilePath, unitsPerBeatLabel, unitsPerBeat, beatsPerBarLabel,
				beatsPerBar, bpmLabel, bpm, backButton, okButton);

	}

}
