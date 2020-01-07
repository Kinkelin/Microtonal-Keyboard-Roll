package audio;

import data.KeyColor;

public class Tone {

	private double frequency;
	private String name;
	private int octave;
	private KeyColor keyColor;
	private String wavFileName;
	
	public String getWavFileName() {
		return wavFileName;
	}

	public void setWavFileName(String wavFileName) {
		this.wavFileName = wavFileName;
		SoundManager.SINGLETON.addClip(wavFileName);
	}

	public Tone(double frequency, String name, int octave, KeyColor keyColor, double defaultDuration) {
		this.frequency = frequency;
		this.name = name;
		this.octave = octave;
		this.keyColor = keyColor;	
	}
	
	public String getDisplayName() {
		return getFormattedFrequency() + " " + name;
	}
	
	public String getFormattedFrequency() {
		return formatFrequency(frequency);
	}
	
	public void play(double duration) {
		SoundManager.SINGLETON.playSound(wavFileName);
	}
	
	public double getFrequency() {
		return frequency;
	}
	public void setFrequency(double frequency) {
		this.frequency = frequency;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getOctave() {
		return octave;
	}
	public void setOctave(int octave) {
		this.octave = octave;
	}
	
	public String formatFrequency(double frequency) {
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


	public KeyColor getKeyColor() {
		return keyColor;
	}


	public void setKeyColor(KeyColor keyColor) {
		this.keyColor = keyColor;
	}
}
