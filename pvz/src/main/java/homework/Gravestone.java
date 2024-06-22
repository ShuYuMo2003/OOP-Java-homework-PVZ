package homework;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class Gravestone extends Plants {
    public static Image staticImage = Constants.getCachedImage(
        ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Plants/Gravestone")[0]
    );

    Gravestone() {}

    Gravestone(int row, int column) {
        super(row, column,
              Constants.PlantsColumnXPos[column],
              Constants.PlantsRowYPos[row],
              ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Plants/Gravestone"),
              Constants.GravestoneHealth,
              Constants.GravestoneBrainProductionFPS
        );
        initialTimeline(Constants.GravestoneFPS, true, e->{});
        initializeBrainProducer();
        play();
    }

    private void initializeBrainProducer() {
        Timeline brainProductionTimeline = new Timeline(new KeyFrame(
            Duration.millis(10000 / Constants.GravestoneBrainProductionFPS),
            event -> produceBrain()
        ));
        brainProductionTimeline.setCycleCount(Timeline.INDEFINITE);
        brainProductionTimeline.play();
    }

    private void produceBrain() {
        Brain brain = new Brain(this.getX() - 70, this.getY() - 100);
        GlobalControl.addBrain(brain);
    }

    @Override
    protected Bullets getNewBullets() {
        return null;
    }
}
