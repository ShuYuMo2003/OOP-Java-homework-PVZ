package homework;

import java.io.UnsupportedEncodingException;

import javafx.scene.image.Image;

/**
 * Represents a specific type of zombie, FlagZombine.
 * Extends the Zombine class and provides additional functionalities
 * and characteristics specific to FlagZombine.
 */
public class FlagZombine extends Zombine {

    // Static image for the FlagZombine
    public static Image staticImage = Constants.getCachedImage(
        // Retrieves the first image from the directory of FlagZombie images
        ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/FlagZombie/FlagZombie")[0]
    );

    private int row;  // Row position of the FlagZombine on the grid

    /**
     * Constructor for FlagZombine.
     * @param row The row position of the FlagZombine.
     * @param column The column position of the FlagZombine.
     */
    public FlagZombine(int row, int column) {
        // Calling superclass constructor with specific parameters
        super(Constants.getZombinePos(row, column)[0],
              Constants.getZombinePos(row, column)[1],
              Constants.FlagZombineSpeed,
              Constants.FlagZombineAttackValue,
              ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/NormalZombie/ZombieDie"),
              Constants.ZombineDieFPS);

        this.row = row;  // Setting the row position

        // Adding different stages of the FlagZombine using ZombineStage objects
        addStage(new ZombineStage(
            "With Flag Stage",
            ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/FlagZombie/FlagZombie"),
            ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/FlagZombie/FlagZombieAttack"),
            100));

        addStage(new ZombineStage("Lost Head Stage",
            ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/FlagZombie/FlagZombieLostHead"),
            ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/FlagZombie/FlagZombieLostHeadAttack"),
            45));

        initialTimeline(Constants.FlagZombineFPS);  // Initializing timeline with FPS for FlagZombine
        play();  // Starting animation playback
    }

    /**
     * Retrieves the row position of the FlagZombine.
     * @return The row position.
     */
    public int getRow() {
        return row;
    }
}
