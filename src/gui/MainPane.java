package gui;

import data.MicrotonalFile;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainPane extends VBox {

	private MicrotonalFile microtonalFile;

	public static final int CELL_SIZE = 24;

	public MainPane(PianoRollApp app, Stage stage, MicrotonalFile microtonalFile) {
		MenuBand menuBand = new MenuBand(app, stage, this);
		setMicrotonalFile(microtonalFile);
		ScrollPane workingArea = new ScrollPane();
		workingArea.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		workingArea.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		HBox content = new HBox();
		content.getChildren().addAll(new PianoPane(microtonalFile), new SheetPane(microtonalFile));
		
		workingArea.setContent(content);
		getChildren().addAll(menuBand, workingArea);
	}

	public MicrotonalFile getMicrotonalFile() {
		return microtonalFile;
	}

	public void setMicrotonalFile(MicrotonalFile microtonalFile) {
		this.microtonalFile = microtonalFile;
	}
}
