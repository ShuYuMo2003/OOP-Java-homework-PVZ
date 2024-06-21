package homework;

import javafx.scene.layout.Pane;
import javafx.scene.image.Image;

public class PepPeaShooter extends Plants {
    public static Image staticImage = new Image(
        ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Plants/RepeaterPea")[0]
    );
    PepPeaShooter() {}
    PepPeaShooter(int row, int column) {
        super(row, column,
              Constants.PlantsColumnXPos[column],
              Constants.PlantsRowYPos[row],
              ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Plants/RepeaterPea"),
              Constants.PepPeaShooterHealth,
              Constants.PepPeaShooterShootFPS
        );
        initialTimeline(Constants.PepPeaShooterFPS, true, e->{});
        initializeShooter();
        play();
    }
    protected Bullets getNewBullets() {
        return new NormalBullets(this.x, this.y);
    }
}
