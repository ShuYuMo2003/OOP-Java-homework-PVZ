package homework;

import javafx.scene.layout.Pane;
import javafx.scene.image.Image;

/**
 * The PepPeaShooter class represents a plant that shoots peas at zombies in the game.
 * It extends the Plants class and includes specific functionalities for the PepPeaShooter plant.
 */
public class PepPeaShooter extends Plants {
    // Static image representing the PepPeaShooter.
    public static Image staticImage = Constants.getCachedImage(
        ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Plants/RepeaterPea")[0]
    );

    // The row position of the PepPeaShooter.
    protected int row;

    /**
     * Default constructor for PepPeaShooter.
     */
    PepPeaShooter() {}

    /**
     * Parameterized constructor for PepPeaShooter.
     * 
     * @param row The row position of the PepPeaShooter.
     * @param column The column position of the PepPeaShooter.
     */
    PepPeaShooter(int row, int column) {
        super(row, column,
              // Set the x-coordinate based on the column position.
              Constants.PlantsColumnXPos[column],
              // Set the y-coordinate based on the row position.
              Constants.PlantsRowYPos[row],
              // Load all image files for the PepPeaShooter.
              ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Plants/RepeaterPea"),
              // Set the health of the PepPeaShooter.
              Constants.PepPeaShooterHealth,
              // Set the frames per second for shooting.
              Constants.PepPeaShooterShootFPS
        );
        this.row = row;
        // Initialize the timeline for the PepPeaShooter.
        initialTimeline(Constants.PepPeaShooterFPS, true, e -> {});
        // Initialize the shooter mechanism.
        initializeShooter();
        // Start playing the animation.
        play();
    }

    /**
     * Creates and returns a new bullet for the PepPeaShooter.
     * 
     * @return A new NormalBullets object.
     */
    protected Bullets getNewBullets() {
        return new NormalBullets(row, this.x, this.y);
    }
}
