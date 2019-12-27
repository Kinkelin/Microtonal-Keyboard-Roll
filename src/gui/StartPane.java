package gui;

import javafx.geometry.Insets;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class StartPane extends HBox {

	private final Button openButton;
	private final Button newButton;

	public StartPane(PianoRollApp app, Stage stage) {
		setSpacing(25);
		setPadding(new Insets(25, 25, 25, 25));

		openButton = new OpenButton(app, stage);

		newButton = new Button("New");
		newButton.setOnAction(e -> app.openCreateNew());

		getChildren().addAll(newButton, openButton);
	}
}
