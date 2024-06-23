package homework;

/**
 * The SnowBullets class represents the bullets shot by the SnowPeashooter in the game.
 * It extends the Bullets class and includes specific functionalities and properties
 * for snow bullets.
 */
public class SnowBullets extends Bullets {

    // Default constructor for SnowBullets.
    SnowBullets() {}

    /**
     * Parameterized constructor for SnowBullets.
     * 
     * @param row The row position of the bullet.
     * @param x The x-coordinate of the bullet.
     * @param y The y-coordinate of the bullet.
     */
    SnowBullets(int row, double x, double y) {
        super(row, x, y,
              // The file path for the normal frame of the snow bullet.
              "file:" + Constants.getImagesPath().getPath() + "Bullets/SnowPea/SnowPea_0.png",
              // The file path for the explosion frame of the snow bullet.
              "file:" + Constants.getImagesPath().getPath() + "Bullets/SnowPeaExplode/SnowPeaExplode_0.png",
              // The damage dealt by the snow bullet.
              Constants.SnowPeaDamage, 
              // The speed of the snow bullet.
              Constants.SnowPeaSpeed
        );
    }
}
