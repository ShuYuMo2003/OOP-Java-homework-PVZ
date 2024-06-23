package homework;

import javafx.scene.image.Image;

/**
 * Represents a NewspaperZombine, a type of zombie with specific characteristics.
 * Extends from Zombine class.
 */
public class NewspaperZombine extends Zombine {

    // Static image for NewspaperZombine
    public static Image staticImage = Constants.getCachedImage(
        // Retrieves the first image from the directory of NewspaperZombie images
        ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/NewspaperZombie/NewspaperZombie")[0]
    );

    private int row;  // Row position of the NewspaperZombine

    /**
     * Retrieves the row position of the NewspaperZombine.
     * @return The row position.
     */
    public int getRow() {
        return row;
    }

    /**
     * Constructor for NewspaperZombine.
     * Initializes its position, speed, attack value, stages, and animation.
     * @param row The row position of the NewspaperZombine.
     * @param column The column position of the NewspaperZombine.
     */
    public NewspaperZombine(int row, int column) {
        super(
            Constants.getZombinePos(row, column)[0],                    // X position
            Constants.getZombinePos(row, column)[1],                    // Y position
            Constants.NewspaperZombineSpeed,                            // Speed
            Constants.NewspaperZombineAttackValue,                      // Attack value
            ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/NewspaperZombie/NewspaperZombieDie"),  // Die animation images
            Constants.ZombineDieFPS                                    // Die animation FPS
        );
        this.row = row;  // Set row position

        // Add different stages of NewspaperZombine
        addStage(new ZombineStage(
            "With Newspaper Stage",                                     // Stage name
            ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/NewspaperZombie/NewspaperZombie"),  // Images for this stage
            ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/NewspaperZombie/NewspaperZombieAttack"),  // Attack images
            130                                                         // FPS for this stage
        ));
        addStage(new ZombineStage(
            "Without Newspaper Stage",                                  // Stage name
            ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/NewspaperZombie/NewspaperZombieNoPaper"),  // Images for this stage
            ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/NewspaperZombie/NewspaperZombieNoPaperAttack"),  // Attack images
            55                                                          // FPS for this stage
        ));
        addStage(new ZombineStage(
            "Lost Head Stage",                                          // Stage name
            ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/NewspaperZombie/NewspaperZombieLostHead"),  // Images for this stage
            ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/NewspaperZombie/NewspaperZombieLostHeadAttack"),  // Attack images
            45                                                          // FPS for this stage
        ));

        initialTimeline(Constants.NewspaperZombineFPS);  // Initialize timeline for animation
        play();  // Start animation playback
    }
}
