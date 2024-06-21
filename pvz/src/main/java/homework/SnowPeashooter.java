package homework;

import javafx.scene.layout.Pane;
import javafx.scene.image.Image;

public class SnowPeashooter extends Plants {
    public static Image staticImage = new Image(
        ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Plants/SnowPeashooter")[0]
    );
    SnowPeashooter() {}
    SnowPeashooter(int row, int column) {
        super(row, column,
              Constants.PlantsColumnXPos[column],
              Constants.PlantsRowYPos[row],
              ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Plants/SnowPeashooter"),
              Constants.SnowPeashooterHealth,
              Constants.SnowPeashooterShootFPS
        );
        initialTimeline(Constants.SnowPeashooterFPS, true, e->{});
        initializeShooter();
        play();
    }
    protected Bullets getNewBullets() {
        return new SnowBullets(this.x, this.y);
    }
}
