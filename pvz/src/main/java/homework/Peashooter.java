package homework;

import javafx.scene.layout.Pane;
import javafx.scene.image.Image;

/**
 * The Peashooter class represents a plant that shoots peas at zombies in the game.
 * It extends the Plants class and includes specific functionalities for the Peashooter plant.
 */
public class Peashooter extends Plants {
    // Static image representing the Peashooter.
    public static Image staticImage = Constants.getCachedImage(
        ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Plants/Peashooter")[0]
    );

    // The row position of the Peashooter.
    protected int row;

    /**
     * Default constructor for Peashooter.
     */
    Peashooter() {}

    /**
     * Parameterized constructor for Peashooter.
     * 
     * @param row The row position of the Peashooter.
     * @param column The column position of the Peashooter.
     */
    Peashooter(int row, int column) {
        super(row, column,
              // Set the x-coordinate based on the column position.
              Constants.PlantsColumnXPos[column],
              // Set the y-coordinate based on the row position.
              Constants.PlantsRowYPos[row],
              // Load all image files for the Peashooter.
              ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Plants/Peashooter"),
              // Set the health of the Peashooter.
              Constants.PeashooterHealth,
              // Set the frames per second for shooting.
              Constants.PeashooterShootFPS
        );
        this.row = row;

        // Initialize the timeline for the Peashooter.
        initialTimeline(Constants.PeashooterFPS, true, e -> {});
        // Initialize the shooter mechanism.
        initializeShooter();
        // Start playing the animation.
        play();
    }

    /**
     * Creates and returns a new bullet for the Peashooter.
     * 
     * @return A new NormalBullets object.
     */
    protected Bullets getNewBullets() {
        return new NormalBullets(row, this.x, this.y);
    }
}
