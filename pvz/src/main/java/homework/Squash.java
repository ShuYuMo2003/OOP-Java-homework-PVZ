package homework;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class Squash extends Plants {
    private static final String[] squashFrames = ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Plants/Squash/Squash");
    private static final String[] squashAimFrames = ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Plants/Squash/SquashAim");
    private static final String[] squashAttackFrames = ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Plants/Squash/SquashAttack");

    private boolean aiming;
    private boolean attacking;

    Squash() {}

    Squash(int row, int column) {
        super(Constants.PlantsColumnXPos[column],
              Constants.PlantsRowYPos[row],
              squashFrames,
              Constants.SquashHealth,
              Constants.SquashAttackFPS);
        this.aiming = false;
        this.attacking = false;
        initialTimeline(Constants.SquashFPS, true);
        play();
    }

    @Override
    protected Bullets getNewBullets() {
        
        return null;
    }

    public void aim() {
        if (!aiming && !attacking) {
            this.aiming = true;
            this.timeline.stop();
            this.frames = squashAimFrames;
            this.currentFrameId = 0;
            initialTimeline(Constants.SquashAimFPS, false);
            this.timeline.setOnFinished(e -> attack());
            this.timeline.play();
        }
    }

    public void attack() {
        if (aiming && !attacking) {
            this.aiming = false;
            this.attacking = true;
            this.frames = squashAttackFrames;
            this.currentFrameId = 0;
            initialTimeline(Constants.SquashAttackFPS, false);
            this.timeline.setOnFinished(e -> {
                
                setDie();  
            });
            this.timeline.play();
        }
    }

    @Override
    public void setDie() {
        die = true;
        if (this.shooter != null)
            this.shooter.stop();
        timeline.stop();
        GlobalControl.rootPane.getChildren().remove(this.imageview);
    }
}
