package homework;


import java.util.Map;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.scene.image.Image;

public class Chomper extends Plants {
    public static Image staticImage = Constants.getCachedImage(
        ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Plants/Chomper/Chomper")[0]
    );
    private boolean isEating = false;
    Chomper() {}

    String[] eatFrames = ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Plants/Chomper/ChomperAttack");
    int eatCurrentFrameId = 0;
    Timeline eatAction = null;

    Chomper(int row, int column) {
        super(row, column,
              Constants.PlantsColumnXPos[column] - 10,
              Constants.PlantsRowYPos[row] - 35,
              ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Plants/Chomper/Chomper"),
              Constants.ChomperHealth,
              Constants.ChomperAttackFPS
        );
        initialTimeline(Constants.ChomperFPS, true, e->{});
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



    private void eatZombine(Zombine zombie) {
        isEating = true;
        timeline.stop();

        eatCurrentFrameId = 0;

        eatAction = new Timeline(new KeyFrame(
            Duration.millis(1000 / Constants.ChomperFPS), event -> {
                if (eatCurrentFrameId < eatFrames.length) {
                    this.getImageView().setImage(Constants.getCachedImage(eatFrames[eatCurrentFrameId]));
                    eatCurrentFrameId++;
                    if(eatCurrentFrameId == eatFrames.length * 2 / 3) {
                        zombie.setDie();
                    }
                } else {
                    resetFrames(ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Plants/Chomper/ChomperDigest"));
                    timeline.play();

                    Timeline digestionTimeline = new Timeline(new KeyFrame(
                        Duration.millis(Constants.ChomperDigestionTime), ee -> {
                            isEating = false;
                            resetFrames(ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Plants/Chomper/Chomper"));
                        }
                    ));
                    digestionTimeline.setCycleCount(1);
                    digestionTimeline.play();

                    eatAction.stop();
                }
            }
        ));
        eatAction.setCycleCount(Timeline.INDEFINITE);
        eatAction.play();
    }
}
