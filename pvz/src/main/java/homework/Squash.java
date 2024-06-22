package homework;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class Squash extends Plants {
    public static Image staticImage = Constants.getCachedImage(
        ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Plants/Squash/Squash")[0]
    );
    private static final String[] squashFrames = ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Plants/Squash/Squash");
    private static final String[] squashAimFrames = ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Plants/Squash/SquashAim");
    private static final String[] squashAttackFrames = ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Plants/Squash/SquashAttack");

    private boolean aiming;
    private boolean attacking;

    Squash() {}

    Squash(int row, int column) {
        super(row, column,
              Constants.PlantsColumnXPos[column] - 20,
              Constants.PlantsRowYPos[row] - 150,
              squashFrames,
              Constants.SquashHealth,
              Constants.SquashAttackFPS);
        this.aiming = false;
        this.attacking = false;
        initialTimeline(Constants.SquashFPS, true, e->{});
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
                    Zombine target = findClosestZombie();
                    if (target != null) {
                        aim(target);
                    }
                }
                )

            );
        shooter.setCycleCount(Timeline.INDEFINITE);
    }

    public void aim(Zombine target) {
        if (!aiming && !attacking) {
            this.aiming = true;
            this.timeline.stop();
            this.frames = squashAimFrames;
            initialTimeline(Constants.SquashAimFPS, false, e -> attack(target));
            this.timeline.play();
        }
    }

    public void attack(Zombine target) {
        if (aiming && !attacking) {
            this.aiming = false;
            this.attacking = true;
            this.frames = squashAttackFrames;

            currentFrameId = 0;
            if(this.timeline != null) this.timeline.stop();
            this.timeline = new Timeline(
                new KeyFrame(Duration.millis(1000 / Constants.SquashAttackFPS), e -> {
                    this.imageview.setImage(Constants.getCachedImage(this.frames[currentFrameId]));
                    // System.err.println("set image as " + this.frames[currentFrameId]);
                    if(currentFrameId == this.frames.length - 1) {
                        this.timeline.stop();
                        setDie();
                    }
                    if(currentFrameId == 2) {
                        target.setDie();
                    }
                    if(1 <= currentFrameId && currentFrameId <= 1) {
                        this.setSpeed(7, 0);
                    } else {
                        this.setSpeed(0, 0);
                    }

                    currentFrameId = (currentFrameId + 1) % this.frames.length;
                })
            );

            this.timeline.setCycleCount(Timeline.INDEFINITE);
            this.timeline.play();
        }
    }

    @Override
    public void setDie() {
        die = true;
        if (this.shooter != null)
            this.shooter.stop();
        timeline.stop();
        removeImageView();
    }
}
