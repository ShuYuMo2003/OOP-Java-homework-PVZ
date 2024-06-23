package homework;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;

/**
 * The Squash class represents a type of plant in the game that can attack zombies by squashing them.
 * It extends the Plants class and includes specific functionalities for aiming and attacking zombies.
 */
public class Squash extends Plants {
    
    // Static image for the Squash, loaded from cached images.
    public static Image staticImage = Constants.getCachedImage(
        ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Plants/Squash/Squash")[0]
    );

    // Arrays of images for different states of the Squash
    private static final String[] squashFrames = ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Plants/Squash/Squash");
    private static final String[] squashAimFrames = ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Plants/Squash/SquashAim");
    private static final String[] squashAttackFrames = ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Plants/Squash/SquashAttack");

    private boolean aiming;  // Flag indicating if the Squash is aiming
    private boolean attacking;  // Flag indicating if the Squash is attacking

    /**
     * Default constructor for Squash.
     */
    Squash() {}

    /**
     * Parameterized constructor for Squash.
     * 
     * @param row The row position of the Squash.
     * @param column The column position of the Squash.
     */
    Squash(int row, int column) {
        super(row, column,
              Constants.PlantsColumnXPos[column] - 20,
              Constants.PlantsRowYPos[row] - 150,
              squashFrames,
              Constants.SquashHealth,
              Constants.SquashAttackFPS);
        this.aiming = false;
        this.attacking = false;
        // Initialize the animation timeline for the Squash.
        initialTimeline(Constants.SquashFPS, true, e -> {});
        // Initialize the attack mechanism.
        initializeAttack();
        // Start the Squash animations and attack mechanism.
        play();
    }

    /**
     * Override method from the Plants class. Squash does not produce bullets, so this returns null.
     * 
     * @return null as Squash does not have attacking capabilities that produce bullets.
     */
    @Override
    protected Bullets getNewBullets() {
        return null;
    }

    /**
     * Initializes the timeline for the Squash's attack mechanism. 
     * The Squash searches for the closest zombie to aim and attack.
     */
    private void initializeAttack() {
        shooter = new Timeline(
            new KeyFrame(Duration.millis(1000 / this.attackFPS), e -> {
                Zombine target = findClosestZombie();
                if (target != null) {
                    aim(target);
                }
            })
        );
        shooter.setCycleCount(Timeline.INDEFINITE);
    }

    /**
     * Aims at the target zombie. Changes the Squash's state to aiming and prepares to attack.
     * 
     * @param target The target zombie to aim at.
     */
    public void aim(Zombine target) {
        if (!aiming && !attacking) {
            this.aiming = true;
            this.timeline.stop();
            this.frames = squashAimFrames;
            initialTimeline(Constants.SquashAimFPS, false, e -> attack(target));
            this.timeline.play();
        }
    }

    /**
     * Attacks the target zombie. Changes the Squash's state to attacking and executes the attack animation.
     * 
     * @param target The target zombie to attack.
     */
    public void attack(Zombine target) {
        if (aiming && !attacking) {
            this.aiming = false;
            this.attacking = true;
            this.frames = squashAttackFrames;

            currentFrameId = 0;
            if (this.timeline != null) this.timeline.stop();
            this.timeline = new Timeline(
                new KeyFrame(Duration.millis(1000 / Constants.SquashAttackFPS), e -> {
                    this.imageview.setImage(Constants.getCachedImage(this.frames[currentFrameId]));
                    // Stop the timeline and set the Squash as dead after the attack animation completes.
                    if (currentFrameId == this.frames.length - 1) {
                        this.timeline.stop();
                        setDie();
                    }
                    // Kill the target zombie when the attack reaches the impact frame.
                    if (currentFrameId == 2) {
                        target.setDie();
                    }
                    // Move the Squash slightly during the attack animation.
                    if (1 <= currentFrameId && currentFrameId <= 1) {
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

    /**
     * Sets the Squash as dead. Stops all animations and removes the image from the view.
     */
    @Override
    public void setDie() {
        die = true;
        if (this.shooter != null)
            this.shooter.stop();
        timeline.stop();
        removeImageView();
    }
}
