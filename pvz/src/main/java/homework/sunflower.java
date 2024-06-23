package homework;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;

/**
 * The Sunflower class represents a type of plant in the game that produces sun energy.
 * It extends the Plants class and includes specific functionalities for sun production.
 */
public class Sunflower extends Plants {
    
    // Static image for the Sunflower, loaded from cached images.
    public static Image staticImage = Constants.getCachedImage(
        ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Plants/Sunflower")[0]
    );

    /**
     * Default constructor for Sunflower.
     */
    Sunflower() {}

    /**
     * Parameterized constructor for Sunflower.
     * 
     * @param row The row position of the Sunflower.
     * @param column The column position of the Sunflower.
     */
    Sunflower(int row, int column) {
        super(row, column,
              Constants.PlantsColumnXPos[column],
              Constants.PlantsRowYPos[row],
              ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Plants/Sunflower"),
              Constants.SunflowerHealth,
              Constants.SunflowerSunProductionFPS
        );
        // Initialize the animation timeline for the Sunflower.
        initialTimeline(Constants.SunflowerFPS, true, e -> {});
        // Initialize the sun production mechanism.
        initializeSunProducer();
        // Start the Sunflower animations and sun production.
        play();
    }

    /**
     * Initializes the timeline for sun production. 
     * The Sunflower produces sun at regular intervals defined by the FPS rate.
     */
    private void initializeSunProducer() {
        Timeline sunProductionTimeline = new Timeline(new KeyFrame(
            Duration.millis(10000 / Constants.SunflowerSunProductionFPS),
            event -> {
                if (isDie()) {
                    // If the Sunflower is dead, do nothing.
                } else {
                    // If the Sunflower is alive, produce a sun.
                    produceSun();
                }
            }
        ));
        // Set the timeline to run indefinitely.
        sunProductionTimeline.setCycleCount(Timeline.INDEFINITE);
        // Start the sun production timeline.
        sunProductionTimeline.play();
    }

    /**
     * Produces a sun and adds it to the global control for collection by the player.
     */
    private void produceSun() {
        // Create a new Sun object at the Sunflower's position with some offset.
        Sun sun = new Sun(this.getX() - 70, this.getY() - 100);
        // Add the newly created sun to the global control.
        GlobalControl.addSun(sun);
    }

    /**
     * Override method from the Plants class. Sunflowers do not produce bullets, so this returns null.
     * 
     * @return null as Sunflowers do not have attacking capabilities.
     */
    @Override
    protected Bullets getNewBullets() {
        return null;
    }
}
