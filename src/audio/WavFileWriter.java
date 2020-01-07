package audio;

import java.lang.Math;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * based on
 * https://stackoverflow.com/questions/40209335/how-to-generate-a-wav-file-with-a-sinusoidal-wave-and-user-defined-duration-and
 *
 */
public class WavFileWriter {
	public static final WavFileWriter SINGLETON = new WavFileWriter();

	private RandomAccessFile raw;

	private int byteCount = 0;

	private float freq;
	private int sRate = 44100;
	private int bitDepth = 16;
	private int nChannels = 1;
	private double dur;

	private float changeRate;

	public void writeWavFile(float frequency, double duration, String filePath) {
		freq = frequency;
		changeRate = (float) ((2.0 * Math.PI * freq) / sRate);

		dur = duration;

		try {
			raw = new RandomAccessFile(filePath, "rw");
			raw.setLength(0); // Set file length to 0, to prevent unexpected behavior in case the file already
								// existed
			raw.writeBytes("RIFF");
			raw.writeInt(0); // Final file size not known yet, write 0. This is = sample count + 36 bytes
								// from header.
			raw.writeBytes("WAVE");
			raw.writeBytes("fmt ");
			raw.writeInt(Integer.reverseBytes(16)); // Sub-chunk size, 16 for PCM
			raw.writeShort(Short.reverseBytes((short) 1)); // AudioFormat, 1 for PCM
			raw.writeShort(Short.reverseBytes((short) nChannels));// Number of channels, 1 for mono, 2 for stereo
			raw.writeInt(Integer.reverseBytes(sRate)); // Sample rate
			raw.writeInt(Integer.reverseBytes(sRate * bitDepth * nChannels / 8)); // Byte rate,
																					// SampleRate*NumberOfChannels*bitDepth/8
			raw.writeShort(Short.reverseBytes((short) (nChannels * bitDepth / 8))); // Block align,
																					// NumberOfChannels*bitDepth/8
			raw.writeShort(Short.reverseBytes((short) bitDepth)); // Bit Depth
			raw.writeBytes("data");
			raw.writeInt(0); // Data chunk size not known yet, write 0. This is = sample count.
		} catch (IOException e) {
			System.out.println("I/O exception occured while writing data");
			e.printStackTrace();
		}
		for (int i = 0; i < sRate * dur; i++) {
			writeSample(calc(i, changeRate, new double[] { 80, 50, 50, 15, 5, 10 }));
		}

		closeFile();
		System.out.print("Finished");
	}

	private float calc(int i, float changeRate, double[] overtoneStrengths) {
		double total = 0;
		for (double overtoneStrength : overtoneStrengths)
			total += overtoneStrength;
		double result = 0;
		for (int j = 0; j < overtoneStrengths.length; j++)
			result += Math.sin(i * changeRate * (j + 1)) * overtoneStrengths[j] / total;
		return (float) result;
	}

	private void writeSample(float floatValue) {
		try {
			char shortSample = (char) ((floatValue) * 0x7FFF);
			raw.writeShort(Character.reverseBytes(shortSample));
			byteCount += 2;
		} catch (IOException e) {
			System.out.println("I/O exception occured while writing data");
		}
	}

	private void closeFile() {
		try {
			raw.seek(4); // Write size to RIFF header
			raw.writeInt(Integer.reverseBytes(byteCount + 36));
			raw.seek(40); // Write size to Subchunk2Size field
			raw.writeInt(Integer.reverseBytes(byteCount));
			raw.close();
		} catch (IOException e) {
			System.out.println("I/O exception occured while closing output file");
		}
	}
}