package homework;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Brain {
    private double x; // x-coordinate of the brain object
    private double y; // y-coordinate of the brain object
    private ImageView imageView; // ImageView to display the brain image
    String[] framesPath = ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Plants/Brain"); // Array of paths to brain animation frames
    public boolean deprecated; // Flag to indicate if the brain is no longer active
    int producedPassedFrames = 0; // Counter for the number of frames that have passed
    int currentFrameId = 0; // Index of the current frame in the animation
    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000 / Constants.BrainFPS), e -> {
        // This KeyFrame is executed periodically to update the brain's state
        imageView.setImage(Constants.getCachedImage(framesPath[currentFrameId])); // Update the image of the brain
        currentFrameId = (currentFrameId + 1) % framesPath.length; // Move to the next frame in the animation
        producedPassedFrames += 1; // Increment the frame counter

        // Calculate new position of the brain
        double dy = 30 - 0.8 * producedPassedFrames; // Vertical displacement decreases over time
        double dx = -10; // Horizontal displacement is constant
        if(Constants.WindowHeight - imageView.getY() < 100) { // If the brain is near the bottom of the window
            dy = 0; // Stop vertical displacement
            dx = -5; // Reduce horizontal displacement
        }
        imageView.setY(this.y = imageView.getY() - 0.4 * dy); // Update the y-coordinate
        imageView.setX(this.x = imageView.getX() + 0.4 * dx); // Update the x-coordinate
        
        // Check if the brain is out of bounds and mark it as deprecated if it is
        if(imageView.getX() < 0 || imageView.getY() < 0 || imageView.getX() > Constants.WindowWidth || imageView.getY() > Constants.WindowHeight) {
            deprecated = true;
        }
    }));

    public Brain(double x, double y) {
        this.x = x; // Initialize x-coordinate
        this.y = y; // Initialize y-coordinate
        imageView = new ImageView(Constants.getCachedImage(framesPath[0])); // Set the initial image of the brain
        imageView.setX(x); // Set the initial x-coordinate of the ImageView
        imageView.setY(y); // Set the initial y-coordinate of the ImageView
        
        // Set the event handler for mouse clicks on the brain
        imageView.setOnMouseClicked(event -> {
            collectBrain(); // Collect the brain when clicked
            event.consume(); // Consume the event to prevent further processing
        });

        timeline.setCycleCount(Timeline.INDEFINITE); // Set the timeline to run indefinitely
        timeline.play(); // Start the timeline animation

        GlobalControl.rootPane.getChildren().add(imageView); // Add the brain's ImageView to the root pane
    }

    // Method to handle the collection of the brain
    private void collectBrain() {
        GlobalControl.modifyBrainCount(25); // Increase the brain count by 25
        deprecated = true; // Mark the brain as deprecated
        GlobalControl.rootPane.getChildren().remove(imageView); // Remove the brain's ImageView from the root pane
    }

    // Getter for the x-coordinate
    public double getX() {
        return x;
    }

    // Getter for the y-coordinate
    public double getY() {
        return y;
    }

    // Getter for the ImageView
    public ImageView getImageView() {
        return imageView;
    }
}
