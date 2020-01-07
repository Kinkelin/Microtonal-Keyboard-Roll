package audio;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;

import javax.sound.sampled.*;

/**
 * modified http://www.java-gaming.org/index.php/topic,1948
 *
 */
public class SoundManager {

	public static final SoundManager SINGLETON = new SoundManager();

	private static final Logger LOG = Logger.getLogger(SoundManager.class.getName());

	private javax.sound.sampled.Line.Info lineInfo;

	private Vector afs;
	private Vector sizes;
	private Vector infos;
	private Vector audios;
	private int num = 0;
	private Map<String, Integer> clips = new HashMap<>();

	public SoundManager() {
		afs = new Vector();
		sizes = new Vector();
		infos = new Vector();
		audios = new Vector();
	}

	public int addClip(String s) {
		if (!clips.containsKey(s)) {
			try {
				System.out.println("SoundManager.addClip(" + s + ")");
				// AudioInputStream audioInputStream = AudioSystem
				// .getAudioInputStream(ClassLoader.getSystemResourceAsStream(s));
				AudioInputStream audioInputStream = AudioSystem
						.getAudioInputStream(new BufferedInputStream(new FileInputStream(s)));
				AudioFormat af = audioInputStream.getFormat();
				int size = (int) (af.getFrameSize() * audioInputStream.getFrameLength());
				byte[] audio = new byte[size];
				DataLine.Info info = new DataLine.Info(Clip.class, af, size);
				audioInputStream.read(audio, 0, size);

				afs.add(af);
				sizes.add(new Integer(size));
				infos.add(info);
				audios.add(audio);
				clips.put(s, num);
				num++;
			} catch (Exception e) {
				System.out.println(e.getClass().getName() + " bei addClip(" + s + ")");
				e.printStackTrace();
			}
		}
		return num - 1;
	}

	public void playSound(String s) {
		if (clips.containsKey(s)) {
			playSound(clips.get(s));
		} else {
			System.out.println("playSound(" + s + ") kann den Clip nicht finden.");
		}
	}

	public void playSound(int x) {
		if (x > num) {
			LOG.info("playSound: sample nr[" + x + "] is not available");
		} else {
			Clip clip;
			try {
				clip = (Clip) AudioSystem.getLine((DataLine.Info) infos.elementAt(x));
				clip.open((AudioFormat) afs.elementAt(x), (byte[]) audios.elementAt(x), 0,
						((Integer) sizes.elementAt(x)).intValue());
				clip.start();
			} catch (LineUnavailableException e) {
				e.printStackTrace();
			}

		}
	}
}