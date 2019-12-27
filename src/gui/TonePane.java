package gui;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import audio.SoundManager;
import audio.WavFileWriter;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class TonePane extends StackPane {

	private double frequency;
	private String wavFileName;

	public TonePane(double frequency, boolean whiteKey, String name) {
		Region rect = new Region();
		String bgColor = whiteKey ? "white" : "black";
		rect.setStyle("-fx-background-color: " + bgColor
				+ "; -fx-border-style: solid; -fx-border-width: 1; -fx-border-color: grey; "
				+ "-fx-min-width: 128; -fx-min-height:24; -fx-max-width:128; -fx-max-height: 24;");
		Text text = new Text(formatFrequency(frequency) + " " + name);
		text.setFill(whiteKey ? Color.BLACK : Color.WHITE);
		getChildren().addAll(rect, text);

		this.frequency = frequency;
		wavFileName = "audio/temp/" + formatFrequency(frequency) + ".wav";
		System.out.println(frequency);
		WavFileWriter.SINGLETON.writeWavFile((float) frequency, 1, wavFileName);

		SoundManager.SINGLETON.addClip(wavFileName);
		setOnMouseClicked(e -> SoundManager.SINGLETON.playSound(wavFileName));

	}

	private String formatFrequency(double frequency) {
		if (frequency >= 10000) {
			return String.format("%.0f", frequency);
		} else if (frequency >= 1000) {
			return String.format("%.1f", frequency);
		} else if (frequency >= 100) {
			return String.format("%.2f", frequency);
		} else if (frequency >= 10) {
			return String.format("%.3f", frequency);
		} else {
			return String.format("%.4f", frequency);
		}
	}
}
