package homework;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.util.Duration;
import javafx.event.EventHandler;

/**
 * Abstract class representing plants in the game.
 * Extends from MoveableElement for basic movement properties.
 */
public abstract class Plants extends MoveableElement {

    protected double health;                 // Health of the plant
    protected Timeline timeline;             // Timeline for animations
    protected Timeline shooter;              // Timeline for shooting bullets
    protected double attackFPS;              // FPS for attacking
    protected String[] frames;               // Frames for animation
    protected int currentFrameId;            // Current frame ID in animation
    protected boolean die = false;           // Flag indicating if the plant is dead
    protected static double xOffset = -70;   // X offset for positioning
    protected static double yOffset = -70;   // Y offset for positioning

    private int row;                         // Row position of the plant
    private int column;                      // Column position of the plant

    /**
     * Default constructor for Plants.
     * Currently not used in the implementation.
     */
    Plants() {
        // Default constructor not used in current implementation
    }

    /**
     * Constructor for Plants.
     * Initializes the plant with specific parameters.
     * @param row The row position of the plant.
     * @param column The column position of the plant.
     * @param x The initial x-coordinate of the plant.
     * @param y The initial y-coordinate of the plant.
     * @param framesPaths Paths to frames for animation.
     * @param health Health points of the plant.
     * @param attackFPS FPS for attack animation.
     */
    Plants(int row, int column, double x, double y, String[] framesPaths, double health, double attackFPS) {
        super(x + xOffset, y + yOffset, 0, 0);  // Initialize MoveableElement with adjusted position
        this.health = health;                   // Set health
        currentFrameId = 0;                     // Initialize frame ID
        this.frames = framesPaths;              // Set frames for animation
        this.attackFPS = attackFPS;             // Set attack FPS
        this.row = row;                         // Set row position
        this.column = column;                   // Set column position
    }

    /**
     * Resets the frames used for animation.
     * @param frames New frames to set.
     */
    protected void resetFrames(String[] frames) {
        this.frames = frames;
        currentFrameId = 0;
    }

    /**
     * Retrieves the row position of the plant.
     * @return The row position.
     */
    protected int getRow() {
        return row;
    }

    /**
     * Retrieves the column position of the plant.
     * @return The column position.
     */
    protected int getColumn() {
        return column;
    }

    /**
     * Initializes the timeline for animation.
     * @param fps Frames per second for animation.
     * @param infinite Indicates if the animation should loop indefinitely.
     * @param onFinished Event handler for when the animation finishes.
     */
    protected void initialTimeline(double fps, boolean infinite, EventHandler<ActionEvent> onFinished) {
        currentFrameId = 0;
        if (this.timeline != null) this.timeline.stop();  // Stop previous timeline if exists
        this.timeline = new Timeline(
            new KeyFrame(Duration.millis(1000 / fps), e -> {
                this.imageview.setImage(Constants.getCachedImage(frames[currentFrameId]));  // Set current frame image
                if (!infinite) {
                    if (currentFrameId == frames.length - 1) {
                        this.timeline.stop();  // Stop timeline if not infinite and reached end
                        onFinished.handle(e);  // Handle finished event
                    }
                }
                currentFrameId = (currentFrameId + 1) % frames.length;  // Move to next frame
            })
        );
        this.timeline.setCycleCount(infinite ? Timeline.INDEFINITE : 1);  // Set cycle count
    }

    /**
     * Retrieves the map position of the plant.
     * @return MapPosition object representing the position on the grid.
     */
    public MapPosition getMapPosition() {
        MapPosition mapPosition = new MapPosition(row, column);  // Create MapPosition with current row and column
        return mapPosition;
    }

    /**
     * Retrieves the map position based on specified coordinates.
     * @param xx X-coordinate to find closest map position.
     * @param yy Y-coordinate to find closest map position.
     * @return MapPosition object representing the closest position on the grid.
     */
    public static MapPosition getMapPosition(double xx, double yy) {
        MapPosition mapPosition = new MapPosition(0, 0);  // Initialize MapPosition with default values
        double minDistance2 = Constants.INF;  // Initialize minimum distance

        // Iterate through all possible positions on the grid
        for (int i = 0; i < Constants.MaxColumn; i++) {
            for (int j = 0; j < Constants.MaxRow; j++) {
                double x = Constants.PlantsColumnXPos[i] - 40;  // Adjusted x position
                double y = Constants.PlantsRowYPos[j] - 20;   // Adjusted y position
                double distance2 = Math.pow(x - xx, 2) + Math.pow(y - yy, 2);  // Calculate squared distance

                // Update closest position if found closer
                if (distance2 < minDistance2) {
                    minDistance2 = distance2;
                    mapPosition = new MapPosition(j, i);  // Update map position
                }
            }
        }
        return mapPosition;  // Return closest map position
    }

    /**
     * Finds the closest zombie to the plant within range.
     * @return Closest zombie found or null if none in range.
     */
    protected Zombine findClosestZombie() {
        // Iterate through all zombies in the game
        for (Zombine zombie : GlobalControl.getZombines()) {
            MapPosition zombiePosition = zombie.getMapPosition();  // Get zombie's map position

            // Check if zombie is in the same row and within one column distance
            if (zombiePosition.row == this.getRow() && (Math.abs(zombiePosition.column - this.getColumn()) <= 1)) {
                return zombie;  // Return closest zombie found
            }
        }
        return null;  // Return null if no zombie found in range
    }

    /**
     * Inflicts damage to the plant.
     * @param damage Amount of damage to inflict.
     */
    public void getDamage(double damage) {
        health -= damage;  // Decrease health by damage amount

        // Check if health is below threshold to consider the plant dead
        if (health <= Constants.EPS) {
            setDie();  // Set plant as dead
        }
    }

    /**
     * Abstract method to be implemented by subclasses to create new bullets.
     * @return New instance of Bullets or its subclass.
     */
    protected abstract Bullets getNewBullets();

    /**
     * Initializes the shooter timeline for shooting bullets.
     */
    public void initializeShooter() {
        shooter = new Timeline(
            new KeyFrame(Duration.millis(1000 / this.attackFPS), e -> {
                if (GlobalControl.zombineCountOnEachRow[row] != 0)  // Check if there are zombies in the row
                    GlobalControl.addBullets(getNewBullets());  // Add new bullets to the game
            })
        );
        shooter.setCycleCount(Timeline.INDEFINITE);  // Set shooter timeline to repeat indefinitely
    }

    /**
     * Sets the plant as dead, stopping its animation and removing it from the scene.
     */
    public void setDie() {
        die = true;  // Set die flag to true
        if (this.shooter != null)
            this.shooter.stop();  // Stop shooter timeline if exists
        timeline.stop();  // Stop animation timeline
        GlobalControl.rootPane.getChildren().remove(this.imageview);  // Remove plant from the scene
    }

    /**
     * Retrieves the x-coordinate of the plant.
     * @return The x-coordinate.
     */
    public double getX() {
        return this.x - xOffset;  // Adjusted x-coordinate
    }

    /**
     * Retrieves the y-coordinate of the plant.
     * @return The y-coordinate.
     */
    public double getY() {
        return this.y - yOffset;  // Adjusted y-coordinate
    }

    /**
     * Checks if the plant is dead.
     * @return True if the plant is dead, false otherwise.
     */
    public boolean isDie() {
        return die;  // Return die flag
    }

    /**
     * Starts playing the plant's animation and shooting.
     */
    public void play() {
        this.timeline.play();  // Play animation timeline
        if (this.shooter != null)
            this.shooter.play();  // Play shooter timeline if exists
    }

    /**
     * Pauses the plant's animation.
     */
    public void pause() {
        this.timeline.pause();  // Pause animation timeline
    }
}
