package test;

import java.io.IOException;

import data.ToneSystem;
import data.ToneSystemFile;

public class ResourceCreator {

	public static void main(String[] args) {
		// "Normal" 12-TET
		ToneSystem normal = new ToneSystem(
				new boolean[] { true, false, true, false, true, true, false, true, false, true, false, true });
		normal.setToneNames(new String[]{"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"});
		String filePath = "resources//toneSystems//presets//" + normal.getName() + ".tonesystem";
		new ToneSystemFile(filePath, normal).save();
		
		try {
			System.out.println(normal.serializeToneNames(ToneSystemFile.load(filePath).getToneSystem().getToneNames()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
