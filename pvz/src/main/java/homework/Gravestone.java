package homework;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;

/**
 * Represents a Gravestone object in the game, extending from Plants.
 * Gravestone has specific functionalities like brain production and animation.
 */
public class Gravestone extends Plants {

    // Static image for Gravestone
    public static Image staticImage = Constants.getCachedImage(
        // Retrieves the first image from the directory of Gravestone images
        ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Plants/Gravestone")[0]
    );

    /**
     * Default constructor for Gravestone.
     * Initializes without specific properties.
     */
    Gravestone() {}

    /**
     * Constructor for Gravestone with row and column positions.
     * @param row The row position of the Gravestone.
     * @param column The column position of the Gravestone.
     */
    Gravestone(int row, int column) {
        super(row, column,
              // Sets initial position based on Constants
              Constants.PlantsColumnXPos[column] + 300,
              Constants.PlantsRowYPos[row],
              // Loads all images of Gravestone
              ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Plants/Gravestone"),
              Constants.GravestoneHealth,
              Constants.GravestoneBrainProductionFPS
        );

        // Initializes timeline for Gravestone animation
        initialTimeline(Constants.GravestoneFPS, true, e->{});
        initializeBrainProducer();  // Starts brain production
        play();  // Starts animation playback
    }

    /**
     * Initializes the brain production process using a Timeline.
     * Adjusts brain production rate randomly.
     */
    private void initializeBrainProducer() {
        Timeline brainProductionTimeline = new Timeline(new KeyFrame(
            // Calculates brain production rate dynamically
            Duration.millis(1000 / (Constants.GravestoneBrainProductionFPS + 0.01 * Math.random())),
            event -> produceBrain()
        ));
        brainProductionTimeline.setCycleCount(Timeline.INDEFINITE);  // Repeats indefinitely
        brainProductionTimeline.play();  // Starts brain production timeline
    }

    /**
     * Retrieves the map position of the Gravestone.
     * @return A MapPosition object representing the position (-1, -1) indicating it's not on the map.
     */
    public MapPosition getMapPosition(){
        // Not on the map, does not interact with others.
        return new MapPosition(-1, -1);
    }

    /**
     * Produces a Brain object at the Gravestone's position.
     * Adds the brain to the global control.
     */
    private void produceBrain() {
        // Checks if in server mode and not in single player mode
        if(Constants.isServerNPlants && !Constants.GameModeSingle) {
            // No action in this scenario
        } else {
            Brain brain = new Brain(this.getX() - 70, this.getY() - 100);  // Creates a new Brain object
            GlobalControl.addBrain(brain);  // Adds the brain to the global control
        }
    }

    /**
     * Overrides the method to return new bullets (not used for Gravestone).
     * @return Always returns null since Gravestone does not shoot bullets.
     */
    @Override
    protected Bullets getNewBullets() {
        return null;  // Gravestone does not shoot bullets
    }
}
