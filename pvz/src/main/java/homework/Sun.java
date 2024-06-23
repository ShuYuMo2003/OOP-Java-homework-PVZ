package homework;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * The Sun class represents a sun object that appears in the game.
 * The sun object can be collected by the player to increase their sun count.
 */
public class Sun {
    private double x;
    private double y;
    private ImageView imageView;
    String[] framesPath = ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Plants/Sun");

    public boolean deprecated = false;

    int producedPassedFrames = 0;
    int currentFrameId = 0;

    // Timeline for animating the sun object
    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000 / Constants.SunFPS), e -> {
        // Update the image of the sun to create an animation effect
        imageView.setImage(Constants.getCachedImage(framesPath[currentFrameId]));
        currentFrameId = (currentFrameId + 1) % framesPath.length;
        producedPassedFrames += 1;

        // Calculate new position for the sun
        double dy = 30 - 0.8 * producedPassedFrames;
        double dx = -10;
        if (Constants.WindowHeight - imageView.getY() < 100) {
            dy = 0;
            dx = -5;
        }

        // Update the position of the sun
        imageView.setY(this.y = imageView.getY() - 0.4 * dy);
        imageView.setX(this.x = imageView.getX() - 0.4 * dx);

        // Mark the sun as deprecated if it moves out of the window bounds
        if (imageView.getX() < 0 || imageView.getY() < 0 || imageView.getX() > Constants.WindowWidth || imageView.getY() > Constants.WindowHeight) {
            deprecated = true;
        }
    }));

    /**
     * Constructor for creating a sun object at a specific position.
     * 
     * @param x The x-coordinate of the sun's initial position.
     * @param y The y-coordinate of the sun's initial position.
     */
    public Sun(double x, double y) {
        this.x = x;
        this.y = y;
        imageView = new ImageView(Constants.getCachedImage(framesPath[0]));
        imageView.setX(x);
        imageView.setY(y);

        // Set up mouse click event to collect the sun
        imageView.setOnMouseClicked(event -> {
            collectSun();
            event.consume();
        });

        // Set the timeline to run indefinitely and start it
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        // Add the sun image to the global root pane
        GlobalControl.rootPane.getChildren().add(imageView);
    }

    /**
     * Method to collect the sun. Increases the player's sun count and removes the sun from the screen.
     */
    private void collectSun() {
        GlobalControl.modifySunCount(25);  // Collecting a sun increases the sun count by 25
        GlobalControl.rootPane.getChildren().remove(imageView);  // Remove the sun from the screen
        deprecated = true;  // Mark the sun as deprecated
        GlobalControl.playOnce(Constants.getCollectSunMusic(), 1.3);  // Play the sun collection sound effect
    }

    /**
     * Getter method for the x-coordinate of the sun.
     * 
     * @return The x-coordinate of the sun.
     */
    public double getX() {
        return x;
    }

    /**
     * Getter method for the y-coordinate of the sun.
     * 
     * @return The y-coordinate of the sun.
     */
    public double getY() {
        return y;
    }
}
