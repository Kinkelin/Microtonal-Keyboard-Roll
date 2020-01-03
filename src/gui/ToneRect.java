package gui;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ToneRect extends Rectangle {

	public ToneRect(int height, int time, int toneLength) {
		super(time * MainPane.CELL_SIZE, height * MainPane.CELL_SIZE, toneLength * MainPane.CELL_SIZE, MainPane.CELL_SIZE);
		setFill(Color.DARKOLIVEGREEN);
		setStroke(Color.DARKSLATEGRAY);
	}
}
