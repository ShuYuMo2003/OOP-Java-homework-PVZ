package homework;

import java.util.HashMap;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public abstract class Bullets extends MoveableElement {
    protected double damage; // Damage dealt by the bullet
    protected Image normalFrame; // Image of the bullet in its normal state
    protected Image boomedFrame; // Image of the bullet after it has boomed

    static double xOffset = 15; // Horizontal offset for bullet position
    static double yOffset = 0; // Vertical offset for bullet position
    static int row = 0; // Row in which the bullet is located

    protected boolean boomed = false; // Boolean flag indicating if the bullet has boomed

    // Default constructor
    Bullets() {}

    // Constructor with parameters
    Bullets(int row, double x, double y,
            String normalFramePath,
            String boomedFramePath,
            double damage, double speed) {

        super(x + xOffset, y + yOffset, speed, 0); // Initialize the super class (MoveableElement) with adjusted positions and speed
        this.row = row; // Set the row for this bullet

        this.damage = damage; // Set the damage value

        // Load the images for normal and boomed states
        this.normalFrame = Constants.getCachedImage(normalFramePath);
        this.boomedFrame = Constants.getCachedImage(boomedFramePath);
        imageview.setImage(this.normalFrame); // Set the initial image to the normal frame
    }

    // Method to handle the bullet booming
    public void boom() {
        setSpeed(0, 0); // Stop the bullet
        imageview.setImage(this.boomedFrame); // Change the image to the boomed frame
        damage = 0; // Set damage to 0 after booming
        Timeline tt = new Timeline(
            new KeyFrame(Duration.millis(100), e -> {
                boomed = true; // Set boomed to true after 100 milliseconds
            })
        );
        tt.setCycleCount(1); // Only run the timeline once
        tt.play(); // Start the timeline
    }

    // Get the map position of the bullet based on its coordinates
    public MapPosition getMapPosition() {
        MapPosition mapPosition = new MapPosition(0, 0); // Default map position
        double minDistance2 = Constants.INF; // Initialize minimum distance with a large value
        for(int i = 0; i < Constants.MaxColumn; i++) {
            for(int j = 0; j < Constants.MaxRow; j++) {
                double x = Constants.PlantsColumnXPos[i] + xOffset; // Calculate x position for comparison
                double y = Constants.PlantsRowYPos[j] + yOffset; // Calculate y position for comparison
                double distance2 = Math.pow(x - this.x, 2) + Math.pow(y - this.y, 2); // Calculate squared distance
                if(distance2 < minDistance2) {
                    minDistance2 = distance2; // Update minimum distance
                    mapPosition = new MapPosition(j, i); // Update map position
                }
            }
        }
        return mapPosition; // Return the calculated map position
    }

    // Getter for the damage value
    public double getDamage() {
        return this.damage;
    }

    // Check if the bullet has boomed
    public boolean isBoomed() {
        return this.boomed;
    }

    @Override
    public double getX() {
        return this.x - xOffset; // Adjust x position by subtracting the offset
    }
    @Override
    public double getY() {
        return this.y - yOffset; // Adjust y position by subtracting the offset
    }

    // Pause the bullet (trigger the boom method)
    void pause() {
        boom();
    }

    // Check if the bullet is out of the game window bounds
    protected void rangeCheck() {
        if(this.x > Constants.WindowWidth + 100 || this.y > Constants.WindowHeight + 100) {
            boomed = true; // Set boomed to true if the bullet is out of bounds
        }
    }
}
