package homework;

import java.io.UnsupportedEncodingException;
import javafx.scene.image.Image;

public class BucketHeadZombine extends Zombine {
    // Static image for the BucketHeadZombine
    public static Image staticImage = Constants.getCachedImage(
        ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/BucketheadZombie/BucketheadZombie")[0]
    );

    // Row position of the BucketHeadZombine on the game grid
    private int row;
    public int getRow() { return row; } // Getter for the row

    // Constructor for the BucketHeadZombine
    public BucketHeadZombine(int row, int column) {
        // Call the superclass constructor with initial position, speed, attack value, and death animation
        super(Constants.getZombinePos(row, column)[0],
              Constants.getZombinePos(row, column)[1],
              Constants.BucketheadZombineSpeed,
              Constants.BucketheadZombineAttackValue,
              ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/NormalZombie/ZombieDie"),
              Constants.ZombineDieFPS);
        
        this.row = row; // Set the row for the instance

        // Add stages to the Zombine
        addStage(new ZombineStage(
            "With Bucket Stage", // Stage name
            ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/BucketheadZombie/BucketheadZombie"), // Walking animation frames with bucket
            ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/BucketheadZombie/BucketheadZombieAttack"), // Attack animation frames with bucket
            350 // Health points for this stage
        ));

        addStage(new ZombineStage(
            "Without Bucket Stage", // Stage name
            ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/NormalZombie/Zombie"), // Walking animation frames without bucket
            ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/NormalZombie/ZombieAttack"), // Attack animation frames without bucket
            95 // Health points for this stage
        ));

        addStage(new ZombineStage(
            "Lost head Stage", // Stage name
            ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/NormalZombie/ZombieLostHead"), // Walking animation frames when head is lost
            ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/NormalZombie/ZombieLostHeadAttack"), // Attack animation frames when head is lost
            45 // Health points for this stage
        ));

        // Initialize the animation timeline with the specified FPS
        initialTimeline(Constants.BucketheadZombineFPS);
        play(); // Start the animation
    }
}
