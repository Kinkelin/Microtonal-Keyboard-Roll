package gui;

import java.io.IOException;

import audio.WavFileGenerator;
import data.MicrotonalFile;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PianoRollApp extends Application {

	private Pane start;
	private Pane createNew;
	private Pane main;
	private Stage stage;
	private Alert loadingScreen;
	private Text text;
	private ProgressBar progressBar;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		start = new StartPane(this, primaryStage);
		stage = primaryStage;
		stage.setScene(new Scene(start, 1024, 768));
		stage.setTitle("Microtonal Keyboard Roll");
		stage.show();
	}

	public void openStart() {
		stage.getScene().setRoot(start);
	}

	public void openCreateNew() {
		createNew = new CreateNewPane(this, stage);
		stage.getScene().setRoot(createNew);
	}

	public void openMain(MicrotonalFile microtonalFile) {
		main = new MainPane(this, stage, microtonalFile);
		stage.getScene().setRoot(main);

		loadingScreen = new Alert(AlertType.NONE);

		WavFileGenerator generator = new WavFileGenerator(microtonalFile);

		loadingScreen.setTitle("Creating audio files");
		HBox content = new HBox();
		progressBar = new ProgressBar(0);
		progressBar.progressProperty().bind(generator.progressProperty().divide((double) generator.getNumberOfAudioFiles()));

		generator.finishedProperty().addListener((observ, old, nevv) -> {
			if (nevv)
				Platform.runLater(() -> postFinish());
		});

		text = new Text();
		String format = "%s/" + generator.getNumberOfAudioFiles();
		text.textProperty().bind(generator.progressProperty().asString(format));
		content.getChildren().addAll(progressBar, text);
		loadingScreen.getDialogPane().setContent(content);
		loadingScreen.show();

		generator.start();

		System.out.println("fertig");
	}

	public void postFinish() {
		text.textProperty().unbind();
		progressBar.progressProperty().unbind();
		loadingScreen.getButtonTypes().add(ButtonType.CLOSE);
		Button closeButton = (Button) loadingScreen.getDialogPane().lookupButton(ButtonType.CLOSE);
		closeButton.fire();
	}

}
