package homework;

import java.util.Map;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.scene.image.Image;

public class Chomper extends Plants {
    // Static image of Chomper
    public static Image staticImage = Constants.getCachedImage(
        ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Plants/Chomper/Chomper")[0]
    );

    private boolean isEating = false; // Flag to check if Chomper is currently eating
    Chomper() {} // Default constructor

    // Frames for the eating animation
    String[] eatFrames = ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Plants/Chomper/ChomperAttack");
    int eatCurrentFrameId = 0; // Current frame index for eating animation
    Timeline eatAction = null; // Timeline for the eating animation

    // Constructor with parameters
    Chomper(int row, int column) {
        super(row, column,
              Constants.PlantsColumnXPos[column] - 10,
              Constants.PlantsRowYPos[row] - 35,
              ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Plants/Chomper/Chomper"),
              Constants.ChomperHealth,
              Constants.ChomperAttackFPS
        );
        initialTimeline(Constants.ChomperFPS, true, e -> {}); // Initialize the plant's timeline
        initializeAttack(); // Initialize the attack mechanism
        play(); // Start the animation
    }

    // Chomper does not shoot bullets, so this method returns null
    @Override
    protected Bullets getNewBullets() {
        return null;
    }

    // Initialize the attack mechanism
    private void initializeAttack() {
        shooter = new Timeline(
            new KeyFrame(Duration.millis(1000 / this.attackFPS), e -> {
                if (!isEating) { // If Chomper is not eating
                    Zombine target = findClosestZombie(); // Find the closest zombie
                    if (target != null) {
                        eatZombine(target); // If a zombie is found, start eating it
                    }
                }
            })
        );
        shooter.setCycleCount(Timeline.INDEFINITE); // Repeat indefinitely
    }

    // Method to handle the eating action of Chomper
    private void eatZombine(Zombine zombie) {
        isEating = true; // Set the eating flag to true
        timeline.stop(); // Stop the current animation timeline

        eatCurrentFrameId = 0; // Reset the frame index for eating animation

        // Timeline for the eating animation
        eatAction = new Timeline(new KeyFrame(
            Duration.millis(1000 / Constants.ChomperFPS), event -> {
                if (eatCurrentFrameId < eatFrames.length) { // If there are more frames to display
                    this.getImageView().setImage(Constants.getCachedImage(eatFrames[eatCurrentFrameId])); // Update the image
                    eatCurrentFrameId++;
                    if(eatCurrentFrameId == eatFrames.length * 2 / 3) {
                        zombie.setDie(); // Kill the zombie when the eating animation is two-thirds complete
                    }
                } else {
                    resetFrames(ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Plants/Chomper/ChomperDigest")); // Reset to digest frames
                    timeline.play(); // Resume the main timeline

                    // Timeline for the digestion period
                    Timeline digestionTimeline = new Timeline(new KeyFrame(
                        Duration.millis(Constants.ChomperDigestionTime), ee -> {
                            isEating = false; // Reset the eating flag
                            resetFrames(ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Plants/Chomper/Chomper")); // Reset to normal frames
                        }
                    ));
                    digestionTimeline.setCycleCount(1); // Execute only once
                    digestionTimeline.play(); // Start the digestion timeline

                    eatAction.stop(); // Stop the eating animation timeline
                }
            }
        ));
        eatAction.setCycleCount(Timeline.INDEFINITE); // Repeat indefinitely
        eatAction.play(); // Start the eating animation timeline
    }
}
