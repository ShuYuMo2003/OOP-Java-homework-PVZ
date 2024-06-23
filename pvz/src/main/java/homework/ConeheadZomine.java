package homework;

import java.io.UnsupportedEncodingException;

import javafx.scene.image.Image;

public class ConeheadZomine extends Zombine {
    // Static image for ConeheadZombie
    public static Image staticImage = Constants.getCachedImage(
        ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/ConeheadZombie/ConeheadZombie")[0]
    );

    private int row; // Row in which the zombie is located

    // Getter method for the row
    public int getRow() {
        return row;
    }

    // Constructor for the ConeheadZomine class
    public ConeheadZomine(int row, int column) {
        super(
            Constants.getZombinePos(row, column)[0], // X position
            Constants.getZombinePos(row, column)[1], // Y position
            Constants.ConeheadZombineSpeed, // Speed of the ConeheadZombie
            Constants.ConeheadZombineAttackValue, // Attack value of the ConeheadZombie
            ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/NormalZombie/ZombieDie"), // Frames for the death animation
            Constants.ZombineDieFPS // FPS for the death animation
        );
        this.row = row; // Set the row

        // Add stages for the different states of the ConeheadZombie
        addStage(new ZombineStage(
            "With Cone Stage", // Stage with cone
            ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/ConeheadZombie/ConeheadZombie"), // Frames for walking with cone
            ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/ConeheadZombie/ConeheadZombieAttack"), // Frames for attacking with cone
            150 // Health points for this stage
        ));
        addStage(new ZombineStage(
            "Without Cone Stage", // Stage without cone
            ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/NormalZombie/Zombie"), // Frames for walking without cone
            ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/NormalZombie/ZombieAttack"), // Frames for attacking without cone
            95 // Health points for this stage
        ));
        addStage(new ZombineStage(
            "Lost Head Stage", // Stage after losing head
            ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/NormalZombie/ZombieLostHead"), // Frames for walking without head
            ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/NormalZombie/ZombieLostHeadAttack"), // Frames for attacking without head
            45 // Health points for this stage
        ));

        // Initialize the animation timeline with the appropriate FPS
        initialTimeline(Constants.ConeheadZombineFPS);
        // Start the animation
        play();
    }
}
