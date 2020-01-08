package gui;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ToneRect extends Rectangle {

	public static final int BORDER_STRENGTH = 2;

	public ToneRect(int height, int time, int toneLength) {
		super(time * MainPane.CELL_SIZE + BORDER_STRENGTH, height * MainPane.CELL_SIZE + BORDER_STRENGTH,
				toneLength * MainPane.CELL_SIZE - 2 * BORDER_STRENGTH, MainPane.CELL_SIZE - 2 * BORDER_STRENGTH);
		setFill(Color.DARKOLIVEGREEN);
		setStroke(Color.DARKSLATEGRAY);
	}
}
