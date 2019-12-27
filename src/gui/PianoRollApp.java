package gui;

import data.MicrotonalFile;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class PianoRollApp extends Application {

	private Pane start;
	private Pane createNew;
	private Pane main;
	private Stage stage;
	
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
	}
	
}
