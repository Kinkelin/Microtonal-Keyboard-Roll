package data;

import static data.SerializationConstants.*;

import java.util.Properties;

public class ToneSystem {
	private String name;
	private int tonesPerOctave;
	private boolean[] whiteKeys;
	private String[] toneNames;
	private int numberOfOctaves;
	private double topFrequency;

	public ToneSystem(String name, boolean[] whiteKeys, String[] toneNames, int numberOfOctaves, double topFrequency) {
		this.name = name;
		this.tonesPerOctave = whiteKeys.length;
		this.whiteKeys = whiteKeys;
		this.toneNames = toneNames;
		this.numberOfOctaves = numberOfOctaves;
		this.topFrequency = topFrequency;
	}

	public ToneSystem(int tonesPerOctave) {
		this(new boolean[tonesPerOctave]);
	}

	public ToneSystem(boolean[] whiteKeys) {
		this.tonesPerOctave = whiteKeys.length;
		this.name = String.valueOf(tonesPerOctave) + "-TET";
		this.numberOfOctaves = 120 / tonesPerOctave;
		this.whiteKeys = whiteKeys;
		this.toneNames = new String[tonesPerOctave];
		for (int i = 0; i < toneNames.length; i++) {
			toneNames[i] = String.valueOf(i);
		}
		this.topFrequency = 523.251 * Math.pow(2, numberOfOctaves / 2);
		System.out.println(topFrequency);
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

	public boolean[] getWhiteKeys() {
		return whiteKeys;
	}

	public void setWhiteKeys(boolean[] whiteKeys) {
		this.whiteKeys = whiteKeys;
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

	public String serializeWhiteKeys(boolean[] whiteKeys) {
		StringBuilder result = new StringBuilder();
		for (boolean whiteKey : whiteKeys) {
			result.append(whiteKey ? WHITE_KEY_TRUE : WHITE_KEY_FALSE);
		}
		return result.toString();
	}

	public static boolean[] deserializeWhiteKeys(String data) {
		boolean[] notes = new boolean[data.length()];
		for (int i = 0; i < data.length(); i++) {
			notes[i] = data.charAt(i) == WHITE_KEY_TRUE;
		}
		return notes;
	}

	public String serializeToneNames(String[] keyNames) {
		return String.join(NOTE_DELIMITER, keyNames);
	}

	public static String[] deserializeToneNames(String data) {
		return data.split(NOTE_DELIMITER);
	}

	public void serialize(Properties properties) {
		properties.setProperty(KEY_NAME, getName());
		properties.setProperty(KEY_WHITE_KEYS, serializeWhiteKeys(getWhiteKeys()));
		properties.setProperty(KEY_TONE_NAMES, serializeToneNames(getToneNames()));
		properties.setProperty(KEY_NUMBER_OF_OCTAVES, String.valueOf(getNumberOfOctaves()));
		properties.setProperty(KEY_TOP_FREQUENCY, String.valueOf(getTopFrequency()));
	}

	public static ToneSystem deserialize(Properties properties) {
		String name = properties.getProperty(KEY_NAME);
		boolean[] whiteKeys = deserializeWhiteKeys(properties.getProperty(KEY_WHITE_KEYS));
		String[] toneNames = deserializeToneNames(properties.getProperty(KEY_TONE_NAMES));
		int numberOfOctaves = Integer.valueOf(properties.getProperty(KEY_NUMBER_OF_OCTAVES));
		double topFrequency = Double.valueOf(properties.getProperty(KEY_TOP_FREQUENCY));
		return new ToneSystem(name, whiteKeys, toneNames, numberOfOctaves, topFrequency);
	}
}
