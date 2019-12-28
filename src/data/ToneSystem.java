package data;

import static data.SerializationConstants.*;

import java.util.Arrays;
import java.util.Properties;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import javafx.scene.paint.Color;

public class ToneSystem {
	private String name;
	private int tonesPerOctave;
	private KeyColor[] keyColors;
	private String[] toneNames;
	private int numberOfOctaves;
	private double topFrequency;

	public ToneSystem(String name, KeyColor[] keyColors, String[] toneNames, int numberOfOctaves, double topFrequency) {
		this.name = name;
		this.tonesPerOctave = keyColors.length;
		this.keyColors = keyColors;
		this.toneNames = toneNames;
		this.numberOfOctaves = numberOfOctaves;
		this.topFrequency = topFrequency;
	}

	public ToneSystem(int tonesPerOctave) {
		this(new KeyColor[tonesPerOctave]);
	}

	public ToneSystem(KeyColor[] keyColors) {
		this.tonesPerOctave = keyColors.length;
		this.name = String.valueOf(tonesPerOctave) + "-TET";
		this.numberOfOctaves = 120 / tonesPerOctave;
		this.keyColors = keyColors;
		this.toneNames = new String[tonesPerOctave];
		for (int i = 0; i < toneNames.length; i++) {
			toneNames[i] = String.valueOf(i);
		}
		this.topFrequency = 523.251 * Math.pow(2, numberOfOctaves / 2);
		System.out.println(topFrequency);
	}
	
	public ToneSystem(int[] keyColorIds) {
		this(KeyColor.transform(keyColorIds));
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTonesPerOctave() {
		return tonesPerOctave;
	}

	public void setTonesPerOctave(int tonesPerOctave) {
		this.tonesPerOctave = tonesPerOctave;
	}

	public KeyColor[] getKeyColors() {
		return keyColors;
	}

	public void setKeyColors(KeyColor[] keyColors) {
		this.keyColors = keyColors;
	}

	public String[] getToneNames() {
		return toneNames;
	}

	public void setToneNames(String[] toneNames) {
		this.toneNames = toneNames;
	}

	public int getNumberOfOctaves() {
		return numberOfOctaves;
	}

	public void setNumberOfOctaves(int numberOfOctaves) {
		this.numberOfOctaves = numberOfOctaves;
	}

	public double getTopFrequency() {
		return topFrequency;
	}

	public void setTopFrequency(double topFrequency) {
		this.topFrequency = topFrequency;
	}

	public String serializeKeyColors(KeyColor[] keyColors) {
		StringJoiner result = new StringJoiner(NOTE_DELIMITER);
		for (KeyColor color : keyColors) {
			result.add(String.valueOf(color.getId()));
		}
		return result.toString();
	}

	public static KeyColor[] deserializeKeyColors(String data) {
		String[] dataArray = data.split(NOTE_DELIMITER);
		KeyColor[] keyColors = new KeyColor[dataArray.length];
		for (int i = 0; i < keyColors.length; i++) {
			keyColors[i] = KeyColor.getById(Integer.valueOf(dataArray[i]));
		}
		return keyColors;
	}

	public String serializeToneNames(String[] keyNames) {
		return String.join(NOTE_DELIMITER, keyNames);
	}

	public static String[] deserializeToneNames(String data) {
		return data.split(NOTE_DELIMITER);
	}

	public void serialize(Properties properties) {
		properties.setProperty(KEY_NAME, getName());
		properties.setProperty(KEY_COLORS, serializeKeyColors(getKeyColors()));
		properties.setProperty(KEY_TONE_NAMES, serializeToneNames(getToneNames()));
		properties.setProperty(KEY_NUMBER_OF_OCTAVES, String.valueOf(getNumberOfOctaves()));
		properties.setProperty(KEY_TOP_FREQUENCY, String.valueOf(getTopFrequency()));
	}

	public static ToneSystem deserialize(Properties properties) {
		String name = properties.getProperty(KEY_NAME);
		KeyColor[] keyColors = deserializeKeyColors(properties.getProperty(KEY_COLORS));
		String[] toneNames = deserializeToneNames(properties.getProperty(KEY_TONE_NAMES));
		int numberOfOctaves = Integer.valueOf(properties.getProperty(KEY_NUMBER_OF_OCTAVES));
		double topFrequency = Double.valueOf(properties.getProperty(KEY_TOP_FREQUENCY));
		return new ToneSystem(name, keyColors, toneNames, numberOfOctaves, topFrequency);
	}
}
