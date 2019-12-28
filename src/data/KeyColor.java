package data;

import javafx.scene.paint.Color;

public enum KeyColor {
	BLACK("black", "grey", Color.WHITE, 0),
	WHITE("white", "grey", Color.BLACK, 1),
	GREY("grey", "black", Color.BLACK, 2);
	
	private final String backgroundColor;
	private final String borderColor;
	private final Color textColor;
	private final int id;
	
	
	
	private KeyColor(String backgroundColor, String borderColor, Color textColor, int id) {
		this.backgroundColor = backgroundColor;
		this.borderColor = borderColor;
		this.textColor = textColor;
		this.id = id;
	}


	public int getId() {
		return id;
	} 
	
	
	public String getBackgroundColor() {
		return backgroundColor;
	}


	public String getBorderColor() {
		return borderColor;
	}


	public Color getTextColor() {
		return textColor;
	}


	public static KeyColor getById(int id) {
		for (KeyColor keyColor : KeyColor.values()) {
			if (keyColor.getId() == id) return keyColor;
		}
		return null;
	}
	
	public static KeyColor[] transform(int[] ids) {
		KeyColor[] keyColors = new KeyColor[ids.length];
		for (int i = 0; i < keyColors.length; i++) {
			keyColors[i] = getById(ids[i]);
		}
		return keyColors;
	}
}
