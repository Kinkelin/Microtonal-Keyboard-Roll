package audio;

import data.MicrotonalFile;
import gui.LoadingScreen;
import javafx.application.Platform;

public class AudioLoader {

	public AudioLoader(MicrotonalFile microtonalFile, Thread postFinish) {

		WavFileGenerator generator = new WavFileGenerator(microtonalFile);

		Platform.runLater(() -> {
			LoadingScreen loadingScreen = new LoadingScreen(microtonalFile);
			loadingScreen.bindGenerator(generator);
		});

		if (postFinish != null) {
			generator.addSuccessor(() -> postFinish.start());
		}
		generator.start();
	}
}
