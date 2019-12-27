package data;

import static data.SerializationConstants.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class ToneSystemFile {
	private String filePath;
	private ToneSystem toneSystem;
	
	public ToneSystemFile(String filePath, ToneSystem toneSystem) {
		this.filePath = filePath;
		this.toneSystem = toneSystem;
	}
	
	public void save() {
		try (OutputStream output = new FileOutputStream(filePath)) {
			Properties properties = new Properties();
			toneSystem.serialize(properties);
			properties.store(output, TONE_SYSTEM_FILE_COMMENT);
		} catch (IOException io) {
			io.printStackTrace();
		}
	}

	public static ToneSystemFile load(String filePath) throws IOException {
		try (InputStream input = new FileInputStream(filePath)) {
			Properties properties = new Properties();
			properties.load(input);
			ToneSystem toneSystem = ToneSystem.deserialize(properties);
			return new ToneSystemFile(filePath, toneSystem);
		}
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public ToneSystem getToneSystem() {
		return toneSystem;
	}

	public void setToneSystem(ToneSystem toneSystem) {
		this.toneSystem = toneSystem;
	}


}
