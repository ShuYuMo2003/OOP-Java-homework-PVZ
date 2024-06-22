package homework;

import javafx.scene.layout.Pane;
import javafx.scene.image.Image;

public class SnowPeashooter extends Plants {
    public static Image staticImage = Constants.getCachedImage(
        ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Plants/SnowPeashooter")[0]
    );

    protected int row;
    SnowPeashooter() {}
    SnowPeashooter(int row, int column) {
        super(row, column,
              Constants.PlantsColumnXPos[column],
              Constants.PlantsRowYPos[row],
              ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Plants/SnowPeashooter"),
              Constants.SnowPeashooterHealth,
              Constants.SnowPeashooterShootFPS
        );
        this.row = row;
        initialTimeline(Constants.SnowPeashooterFPS, true, e->{});
        initializeShooter();
        play();
    }
    protected Bullets getNewBullets() {
        return new SnowBullets(row, this.x, this.y);
    }
}
