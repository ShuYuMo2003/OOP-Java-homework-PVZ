package homework;

import javafx.scene.layout.Pane;

public class Peashooter extends Plants {
    Peashooter() {}
    Peashooter(double x, double y, Pane rootPane) {
        super(x, y,
            ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Plants/Peashooter"),
            100, rootPane
        );
        initialTimeline(Constants.PeashooterFPS, true);
        play();
    }
}
