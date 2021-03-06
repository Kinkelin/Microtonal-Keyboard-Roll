package test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import audio.SoundManager;

public class AudioTest {

	public static void main(String[] args) {

		String problemFile = String.join(File.separator, "audio", "temp", "523,25.wav");
		String goodFile = String.join(File.separator, "audio", "temp", "554,37.wav");

		int num = SoundManager.SINGLETON.addClip(problemFile);
		SoundManager.SINGLETON.playSound(num);
		for (int i = 0; i < 1000; i++) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
