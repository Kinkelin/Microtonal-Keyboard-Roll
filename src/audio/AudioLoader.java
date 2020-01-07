package audio;

import data.MicrotonalFile;
import gui.LoadingScreen;
import javafx.application.Platform;

public class AudioLoader {

	public AudioLoader(MicrotonalFile microtonalFile, Thread postFinish) {

		WavFileGenerator generator = new WavFileGenerator(microtonalFile);

		Platform.runLater(() -> {
			LoadingScreen loadingScreen = new LoadingScreen(microtonalFile);
			loadingScreen.progressProperty()
					.bind(generator.progressProperty().divide((double) generator.getNumberOfAudioFiles()));
			generator.finishedProperty().addListener((observ, old, neww) -> {
				if (neww) {
					Platform.runLater(() -> loadingScreen.postFinish());
				}
			});

			String format = "%s/" + generator.getNumberOfAudioFiles();
			loadingScreen.textProperty().bind(generator.progressProperty().asString(format));
			loadingScreen.show();
		});

		generator.setSuccessor(postFinish);
		generator.start();
	}
}
