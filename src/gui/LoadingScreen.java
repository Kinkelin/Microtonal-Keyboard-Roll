package gui;

import data.MicrotonalFile;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class LoadingScreen extends Alert {

	private Text text;
	private ProgressBar progressBar;

	public LoadingScreen(MicrotonalFile microtonalFile) {
		super(AlertType.NONE);
		setTitle("Creating audio files");
		HBox content = new HBox();
		progressBar = new ProgressBar(0);
		text = new Text();
		content.getChildren().addAll(progressBar, text);
		getDialogPane().setContent(content);
	}

	public void postFinish() {
		textProperty().unbind();
		progressProperty().unbind();
		getButtonTypes().add(ButtonType.CLOSE);
		Button closeButton = (Button) getDialogPane().lookupButton(ButtonType.CLOSE);
		closeButton.fire();
	}
	
	public StringProperty textProperty() {
		return text.textProperty();
	}
	
	public DoubleProperty progressProperty() {
		return progressBar.progressProperty();
	}
}
