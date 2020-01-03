package test;

import java.io.File;
import java.io.IOException;

import data.ToneSystem;
import data.ToneSystemFile;

public class ResourceCreator {

	public static void main(String[] args) {
		// "Normal" 12-TET
		ToneSystem normal = new ToneSystem(new int[] { 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1 });
		normal.setToneNames(new String[] { "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B" });
		normal.setName(normal.getName() + " (normal)");
		saveAsToneSystemFile(normal);

		// "Quartertones" 24-TET
		ToneSystem quartertones = new ToneSystem(
				new int[] { 1, 2, 0, 2, 1, 2, 0, 2, 1, 2, 1, 2, 0, 2, 1, 2, 0, 2, 1, 2, 0, 2, 1, 2 });
		quartertones.setToneNames(new String[] { "C", "C1/4", "C#", "C3/4", "D", "D1/4", "D#", "D3/4", "E", "E1/4", "F",
				"F1/4", "F#", "F3/4", "G", "G1/4", "G#", "G3/4", "A", "A1/4", "A#", "A3/4", "B", "B1/4" });
		quartertones.setName(quartertones.getName() + " (quartertones)");
		saveAsToneSystemFile(quartertones);

		// 19-TET
		ToneSystem tet19 = new ToneSystem(new int[] { 1, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0 });
		tet19.setToneNames(new String[] { "#1 'C'", "#2", "#3", "#4 'D'", "#5", "#6", "#7 'E'", "#8", "#9 'F'", "#10",
				"#11", "#12 'G'", "#13", "#14", "#15 'A'", "#16", "#17", "#18 'B'", "#19" });
		saveAsToneSystemFile(tet19);

		// 17-TET
		ToneSystem tet17 = new ToneSystem(new int[] { 1, 0, 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1 });
		tet17.setToneNames(new String[] { "#1 'C'", "#2", "#3", "#4 'D'", "#5", "#6", "#7 'E'", "#8 'F'", "#9", "#10",
				"#11 'G'", "#12", "#13", "#14 'A'", "#15", "#16", "#17 'B'" });
		saveAsToneSystemFile(tet17);

		// Others
		int[] others = new int[] { 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 13, 14, 15, 16, 18, 20, 21, 22, 23 };
		for (int other : others) {
			ToneSystem tetOther = new ToneSystem(other);
			saveAsToneSystemFile(tetOther);
		}

	}

	private static void saveAsToneSystemFile(ToneSystem preset) {
		String presetFilePath = String.join(File.separator, "resources", "toneSystems", "presets",
				preset.getName() + ".tonesystem");
		new ToneSystemFile(presetFilePath, preset).save();
	}

}
