package homework;

import javafx.scene.layout.Pane;
import javafx.scene.image.Image;

public class Peashooter extends Plants {
    public static Image staticImage = Constants.getCachedImage(
        ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Plants/Peashooter")[0]
    );

    protected int row;

    Peashooter() {}
    Peashooter(int row, int column) {
        super(row, column,
              Constants.PlantsColumnXPos[column],
              Constants.PlantsRowYPos[row],
              ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Plants/Peashooter"),
              Constants.PeashooterHealth,
              Constants.PeashooterShootFPS
        );
        this.row = row;

        initialTimeline(Constants.PeashooterFPS, true, e->{});
        initializeShooter();
        play();
    }
    protected Bullets getNewBullets() {
        return new NormalBullets(row, this.x, this.y);
    }
}
