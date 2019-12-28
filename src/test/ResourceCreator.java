package test;

import java.io.IOException;

import data.ToneSystem;
import data.ToneSystemFile;

public class ResourceCreator {

	public static void main(String[] args) {
		// "Normal" 12-TET
		ToneSystem normal = new ToneSystem(new int[] { 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1 });
		normal.setToneNames(new String[] { "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B" });
		normal.setName(normal.getName() + " (normal)");
		String filePath = "resources//toneSystems//presets//" + normal.getName() + ".tonesystem";
		new ToneSystemFile(filePath, normal).save();

		// "Quartertones" 24-TET
		ToneSystem quartertones = new ToneSystem(
				new int[] { 1, 2, 0, 2, 1, 2, 0, 2, 1, 2, 1, 2, 0, 2, 1, 2, 0, 2, 1, 2, 0, 2, 1, 2 });
		quartertones.setToneNames(new String[] { "C", "C1/4", "C#", "C3/4", "D", "D1/4", "D#", "D3/4", "E", "E1/4", "F",
				"F1/4", "F#", "F3/4", "G", "G1/4", "G#", "G3/4", "A", "A1/4", "A#", "A3/4", "B", "B1/4" });
		quartertones.setName(quartertones.getName() + " (quartertones)");
		String qtFilePath = "resources//toneSystems//presets//" + quartertones.getName() + ".tonesystem";
		new ToneSystemFile(qtFilePath, quartertones).save();

		// 19-TET
		ToneSystem tet19 = new ToneSystem(new int[] { 1, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0 });
		tet19.setToneNames(new String[] { "1 'C'", "2", "3", "4 'D'", "5", "6", "7 'E'", "8", "9 'F'", "10", "11",
				"12 'G'", "13", "14", "15 'A'", "16", "17", "18 'B'", "19" });
		String tet19FilePath = "resources//toneSystems//presets//" + tet19.getName() + ".tonesystem";
		new ToneSystemFile(tet19FilePath, tet19).save();

		// 17-TET
		ToneSystem tet17 = new ToneSystem(new int[] { 1, 0, 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1 });
		tet17.setToneNames(new String[] { "1 'C'", "2", "3", "4 'D'", "5", "6", "7 'E'", "8 'F'", "9", "10", "11 'G'",
				"12", "13", "14 'A'", "15", "16", "17 'B'" });
		String tet17FilePath = "resources//toneSystems//presets//" + tet17.getName() + ".tonesystem";
		new ToneSystemFile(tet17FilePath, tet17).save();

	}

}
