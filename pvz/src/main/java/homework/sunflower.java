package homework;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class Sunflower extends Plants {
    public static Image staticImage = new Image(
        ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Plants/Sunflower")[0]
    );

    Sunflower() {}

    Sunflower(int row, int column) {
        super(row, column,
              Constants.PlantsColumnXPos[column],
              Constants.PlantsRowYPos[row],
              ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Plants/Sunflower"),
              Constants.SunflowerHealth,
              Constants.SunflowerSunProductionFPS
        );
        initialTimeline(Constants.SunflowerFPS, true, e->{});
        initializeSunProducer();
        play();
    }

    private void initializeSunProducer() {
        Timeline sunProductionTimeline = new Timeline(new KeyFrame(
            Duration.millis(1000 / Constants.SunflowerSunProductionFPS),
            event -> produceSun()
        ));
        sunProductionTimeline.setCycleCount(Timeline.INDEFINITE);
        sunProductionTimeline.play();
    }

    private void produceSun() {
        Sun sun = new Sun(this.getX() - 70, this.getY() - 100);
        GlobalControl.addSun(sun);
    }

    @Override
    protected Bullets getNewBullets() {
        return null;
    }
}
