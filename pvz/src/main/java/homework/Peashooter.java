package homework;

import javafx.scene.layout.Pane;

public class Peashooter extends Plants {
    Peashooter() {}
    Peashooter(int row, int column, Pane rootPane) {
        super(Constants.PlantsColumnXPos[column],
              Constants.PlantsRowYPos[row],
              ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Plants/Peashooter"),
              100, rootPane
        );
        initialTimeline(Constants.PeashooterFPS, true);
        play();
    }
}
