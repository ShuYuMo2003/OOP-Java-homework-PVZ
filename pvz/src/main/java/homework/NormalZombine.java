package homework;

import javafx.scene.image.Image;

/**
 * Represents a NormalZombine, a type of zombie with specific characteristics.
 * Extends from Zombine class.
 */
public class NormalZombine extends Zombine {

    // Static image for NormalZombine
    public static Image staticImage = Constants.getCachedImage(
        // Retrieves the first image from the directory of NormalZombie images
        ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/NormalZombie/Zombie")[0]
    );

    private int row;  // Row position of the NormalZombine

    /**
     * Retrieves the row position of the NormalZombine.
     * @return The row position.
     */
    public int getRow() {
        return row;
    }

    /**
     * Constructor for NormalZombine.
     * Initializes its position, speed, attack value, stages, and animation.
     * @param row The row position of the NormalZombine.
     * @param column The column position of the NormalZombine.
     */
    public NormalZombine(int row, int column) {
        super(
            Constants.getZombinePos(row, column)[0],                    // X position
            Constants.getZombinePos(row, column)[1],                    // Y position
            Constants.NormalZombineSpeed,                               // Speed
            Constants.NormalZombineAttackValue,                         // Attack value
            ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/NormalZombie/ZombieDie"),  // Die animation images
            Constants.ZombineDieFPS                                    // Die animation FPS
        );
        this.row = row;  // Set row position

        // Add different stages of NormalZombine
        addStage(new ZombineStage(
            "Initial Stage",                                            // Stage name
            ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/NormalZombie/Zombie"),  // Images for this stage
            ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/NormalZombie/ZombieAttack"),  // Attack images
            55                                                          // FPS for this stage
        ));
        addStage(new ZombineStage(
            "Lost Head Stage",                                          // Stage name
            ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/NormalZombie/ZombieLostHead"),  // Images for this stage
            ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/NormalZombie/ZombieLostHeadAttack"),  // Attack images
            45                                                          // FPS for this stage
        ));

        initialTimeline(Constants.NormalZombineFPS);  // Initialize timeline for animation
        play();  // Start animation playback
    }
}
