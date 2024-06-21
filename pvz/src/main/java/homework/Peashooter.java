package homework;

import javafx.scene.layout.Pane;
import javafx.scene.image.Image;

public class Peashooter extends Plants {
    public static Image staticImage = new Image(
        ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Plants/Peashooter")[0]
    );
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
        return new NormalBullets(this.x, this.y);
    }
}
