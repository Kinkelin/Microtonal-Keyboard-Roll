package gui;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class AboutInformation extends Alert {

	public static final String CONTENT_TEXT = "About"
			+ "\n\n"
			+ "Author: Finn Kuenkele"
			+ "\n"
			+ "Contact: finn@kuenkele.net"
		/*	+ "\n\n\n"
			+ "Credit"
			+ "\n\n"
			+ "Icons: https://www.flaticon.com/packs/android-app-9"*/;
	
	public AboutInformation() {
		super(AlertType.NONE,CONTENT_TEXT, ButtonType.OK);
		setTitle("Information");
	}
}
