package homework;

import javafx.scene.layout.Pane;
import javafx.scene.image.Image;

/**
 * The SnowPeashooter class represents a type of plant in the game that shoots snow peas at zombies.
 * It extends the Plants class and includes specific functionalities for shooting snow peas.
 */
public class SnowPeashooter extends Plants {
    
    // Static image for the SnowPeashooter, loaded from cached images.
    public static Image staticImage = Constants.getCachedImage(
        ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Plants/SnowPeashooter")[0]
    );

    protected int row;  // The row position of the SnowPeashooter

    /**
     * Default constructor for SnowPeashooter.
     */
    SnowPeashooter() {}

    /**
     * Parameterized constructor for SnowPeashooter.
     * 
     * @param row The row position of the SnowPeashooter.
     * @param column The column position of the SnowPeashooter.
     */
    SnowPeashooter(int row, int column) {
        super(row, column,
              Constants.PlantsColumnXPos[column],
              Constants.PlantsRowYPos[row],
              ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Plants/SnowPeashooter"),
              Constants.SnowPeashooterHealth,
              Constants.SnowPeashooterShootFPS
        );
        this.row = row;
        // Initialize the animation timeline for the SnowPeashooter.
        initialTimeline(Constants.SnowPeashooterFPS, true, e -> {});
        // Initialize the shooting mechanism.
        initializeShooter();
        // Start the SnowPeashooter animations and shooting mechanism.
        play();
    }

    /**
     * Override method from the Plants class. Returns a new instance of SnowBullets.
     * 
     * @return A new SnowBullets object representing the bullets shot by the SnowPeashooter.
     */
    protected Bullets getNewBullets() {
        return new SnowBullets(row, this.x, this.y);
    }
}
