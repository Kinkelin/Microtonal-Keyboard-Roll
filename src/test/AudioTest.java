package test;

import audio.SoundManager;

public class AudioTest {

	public static void main(String[] args) {
		int num = SoundManager.SINGLETON.addClip("audio/temp/103,83.wav");
		SoundManager.SINGLETON.playSound(num);
		for (int i = 0; i < 1000; i++) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
