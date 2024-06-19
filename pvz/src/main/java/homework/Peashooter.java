package homework;

import javafx.scene.layout.Pane;

public class Peashooter extends Plants {
    Peashooter() {}
    Peashooter(int row, int column) {
        super(Constants.PlantsColumnXPos[column],
              Constants.PlantsRowYPos[row],
              ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Plants/Peashooter"),
              Constants.PeashooterHealth,
              Constants.PeashooterShootFPS
        );
        initialTimeline(Constants.PeashooterFPS, true);
        initializeShooter();
        play();
    }
    protected Bullets getNewBullets() {
        return new NormalPea(this.x, this.y);
    }
}
