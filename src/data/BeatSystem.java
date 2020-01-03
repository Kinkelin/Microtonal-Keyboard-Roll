package data;

import static data.SerializationConstants.*;

import java.util.Properties;

public class BeatSystem {
	
	private int numberOfUnitsPerBeat;
	private int numberOfBeatsPerBar;
	private double beatsPerMinute;

	public BeatSystem(int numberOfUnitsPerBeat, int numberOfBeatsPerBar, double beatsPerMinute) {
		this.numberOfUnitsPerBeat = numberOfUnitsPerBeat;
		this.numberOfBeatsPerBar = numberOfBeatsPerBar;
		this.beatsPerMinute = beatsPerMinute;
	}
	public int getNumberOfUnitsPerBeat() {
		return numberOfUnitsPerBeat;
	}
	public void setNumberOfUnitsPerBeat(int numberOfUnitsPerBeat) {
		this.numberOfUnitsPerBeat = numberOfUnitsPerBeat;
	}
	public int getNumberOfBeatsPerBar() {
		return numberOfBeatsPerBar;
	}
	public void setNumberOfBeatsPerBar(int numberOfBeatsPerBar) {
		this.numberOfBeatsPerBar = numberOfBeatsPerBar;
	}
	public double getBeatsPerMinute() {
		return beatsPerMinute;
	}
	public void setBeatsPerMinute(double beatsPerMinute) {
		this.beatsPerMinute = beatsPerMinute;
	}
	
	public double getUnitDurationInSeconds() {
		return 60.0 / (beatsPerMinute * numberOfUnitsPerBeat);
	}
	
	public void serialize(Properties properties) {
		properties.setProperty(KEY_UNITS_PER_BEAT, String.valueOf(getNumberOfUnitsPerBeat()));
		properties.setProperty(KEY_BEATS_PER_BAR, String.valueOf(getNumberOfBeatsPerBar()));
		properties.setProperty(KEY_BPM, String.valueOf(getBeatsPerMinute()));
	}

	public static BeatSystem deserialize(Properties properties) {
		int unitsPerBeat = Integer.valueOf(properties.getProperty(KEY_UNITS_PER_BEAT));
		int beatsPerBar = Integer.valueOf(properties.getProperty(KEY_BEATS_PER_BAR));
		double bpm = Double.valueOf(properties.getProperty(KEY_BPM));
		return new BeatSystem(unitsPerBeat, beatsPerBar, bpm);
	}
}
