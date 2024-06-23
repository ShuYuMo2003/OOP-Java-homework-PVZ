package homework;

/**
 * Represents normal bullets used in the game.
 * Extends from Bullets class.
 */
public class NormalBullets extends Bullets {

    // Static images for normal and explosion frames (commented out since they are currently unused)
    // static private Image NormalFrame = null;
    // static private Image BoomFrame = null;

    /**
     * Default constructor for NormalBullets.
     * Used when no parameters are specified.
     */
    NormalBullets() {
        // Calls superclass constructor with default parameters
        // (Not used in current implementation)
    }

    /**
     * Constructor for NormalBullets.
     * Initializes the bullet with specific parameters.
     * @param row The row position where the bullet is fired.
     * @param x The initial x-coordinate of the bullet.
     * @param y The initial y-coordinate of the bullet.
     */
    NormalBullets(int row, double x, double y) {
        super(
            row,                                                        // Row position of the bullet
            x,                                                          // Initial x-coordinate
            y,                                                          // Initial y-coordinate
            "file:" + Constants.getImagesPath().getPath() + "Bullets/PeaNormal/PeaNormal_0.png",  // Image path for normal frame
            "file:" + Constants.getImagesPath().getPath() + "Bullets/PeaNormalExplode/PeaNormalExplode_0.png",  // Image path for explosion frame
            Constants.NormalPeaDamage,                                  // Damage value of the bullet
            Constants.NormalPeaSpeed                                   // Speed of the bullet
        );
    }
}
