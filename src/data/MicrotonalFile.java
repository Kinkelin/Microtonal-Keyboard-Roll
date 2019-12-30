package data;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.StringJoiner;

import static data.SerializationConstants.*;

public class MicrotonalFile {

	private String filePath;
	private ToneSystem toneSystem;
	private BeatSystem beatSystem;
	private Map<MidiRollKey, Integer> notes;
	private int length;

	public MicrotonalFile(String filePath, ToneSystem toneSystem, BeatSystem beatSystem,
			Map<MidiRollKey, Integer> notes, int length) {
		this.filePath = filePath;
		this.toneSystem = toneSystem;
		this.beatSystem = beatSystem;
		this.notes = notes;
		this.length = length;
	}

	public String serializeNotes(Map<MidiRollKey, Integer> notes) {
		StringJoiner result = new StringJoiner(NOTE_DELIMITER);
		for (Map.Entry<MidiRollKey, Integer> note : notes.entrySet()) {
			result.add(String.join(INTERNAL_NOTE_DELIMITER, String.valueOf(note.getKey().getHeight()),
					String.valueOf(note.getKey().getTime()), String.valueOf(note.getValue())));
		}
		return result.toString();
	}

	public static Map<MidiRollKey, Integer> deserializeNotes(String data) {
		Map<MidiRollKey, Integer> notes = new HashMap<>();
		String[] noteStrings = data.split(NOTE_DELIMITER);
		for (String noteString : noteStrings) {
			if (!noteString.isEmpty()) {
				String[] noteInfo = noteString.split(INTERNAL_NOTE_DELIMITER);
				notes.put(new MidiRollKey(Integer.valueOf(noteInfo[0]), Integer.valueOf(noteInfo[1])),
						Integer.valueOf(noteInfo[2]));
			}
		}
		return notes;
	}

	public void save() {
		try (OutputStream output = new FileOutputStream(filePath)) {
			Properties properties = new Properties();
			toneSystem.serialize(properties);
			beatSystem.serialize(properties);
			properties.setProperty(KEY_NOTES, serializeNotes(notes));
			properties.setProperty(KEY_LENGTH, String.valueOf(length));
			
			properties.store(output, MICROTONAL_FILE_COMMENT);
		} catch (IOException io) {
			io.printStackTrace();
		}
	}

	public static MicrotonalFile load(String filePath) throws IOException {
		try (InputStream input = new FileInputStream(filePath)) {
			Properties properties = new Properties();
			properties.load(input);
			ToneSystem toneSystem = ToneSystem.deserialize(properties);
			BeatSystem beatSystem = BeatSystem.deserialize(properties);
			Map<MidiRollKey, Integer> notes = deserializeNotes(properties.getProperty(KEY_NOTES));
			int length = Integer.valueOf(properties.getProperty(KEY_LENGTH));
			return new MicrotonalFile(filePath, toneSystem, beatSystem, notes, length);
		}
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public BeatSystem getBeatSystem() {
		return beatSystem;
	}

	public void setBeatSystem(BeatSystem beatSystem) {
		this.beatSystem = beatSystem;
	}

	public Map<MidiRollKey, Integer> getNotes() {
		return notes;
	}

	public void setNotes(Map<MidiRollKey, Integer> notes) {
		this.notes = notes;
	}

	public ToneSystem getToneSystem() {
		return toneSystem;
	}

	public void setToneSystem(ToneSystem toneSystem) {
		this.toneSystem = toneSystem;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}
}
