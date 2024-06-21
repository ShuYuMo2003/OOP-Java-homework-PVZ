package homework;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.scene.image.Image;

public class Chomper extends Plants {
    private boolean isEating = false;
    Chomper() {}

    Chomper(int row, int column) {
        super(Constants.PlantsColumnXPos[column],
              Constants.PlantsRowYPos[row],
              ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Plants/Chomper/Chomper"),
              Constants.ChomperHealth,
              Constants.ChomperAttackFPS
        );
        initialTimeline(Constants.ChomperFPS, true);
        initializeAttack();
        play();
    }

    @Override
    protected Bullets getNewBullets() {
        return null;
    }

    private void initializeAttack() {
        shooter = new Timeline(
            new KeyFrame(Duration.millis(1000 / this.attackFPS), e -> {
                if (!isEating) {
                    Zombine target = findClosestZombie();
                    if (target != null) {
                        eatZombine(target);
                    }
                }}
                )

            );
        shooter.setCycleCount(Timeline.INDEFINITE);
    }

    private Zombine findClosestZombie() {
        double minDistance = Constants.ChomperRange;
        Zombine closestZombie = null;
        for (Zombine zombie : GlobalControl.getZombines()) {
            double distance = Math.sqrt(Math.pow(zombie.getX() - this.getX(), 2) + Math.pow(zombie.getY() - this.getY(), 2));
            if (distance < minDistance) {
                minDistance = distance;
                closestZombie = zombie;
            }
            ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Plants/Chomper/ChomperAttack");
        }
        return closestZombie;
    }

    private void eatZombine(Zombine zombie) {
        isEating = true;
        zombie.setDie();
        ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Plants/Chomper/ChomperDigest");

        Timeline digestionTimeline = new Timeline(new KeyFrame(
            Duration.millis(Constants.ChomperDigestionTime), event -> {
                isEating = false;
                this.imageview.setImage(new Image(frames[currentFrameId]));
            }
        ));
        digestionTimeline.setCycleCount(1);
        digestionTimeline.play();
    }
}
