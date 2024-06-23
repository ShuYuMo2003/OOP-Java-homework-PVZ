package homework;

import javafx.scene.image.ImageView;

/**
 * Abstract class representing a moveable element in a game.
 * Provides basic functionalities for movement and image handling.
 */
public abstract class MoveableElement {

    protected boolean deprecated = false;  // Flag indicating if the element is deprecated
    protected double x;                    // X-coordinate of the element
    protected double y;                    // Y-coordinate of the element
    protected double dx, dy;               // Speed components in x and y directions
    protected ImageView imageview;         // ImageView to display the element's image

    /**
     * Default constructor for MoveableElement.
     */
    MoveableElement() { }

    /**
     * Constructor for MoveableElement with initial position and speed.
     * @param x Initial x-coordinate of the element.
     * @param y Initial y-coordinate of the element.
     * @param dx Speed in the x-direction.
     * @param dy Speed in the y-direction.
     */
    MoveableElement(double x, double y, double dx, double dy) {
        this.x = x;                 // Initialize x-coordinate
        this.y = y;                 // Initialize y-coordinate
        this.setSpeed(dx, dy);      // Set initial speed
        this.imageview = new ImageView();  // Initialize ImageView
        this.imageview.setX(x);     // Set ImageView x-coordinate
        this.imageview.setY(y);     // Set ImageView y-coordinate
        GlobalControl.rootPane.getChildren().add(this.imageview);  // Add ImageView to rootPane
    }

    /**
     * Sets the speed of the element.
     * @param dx Speed in the x-direction.
     * @param dy Speed in the y-direction.
     */
    public void setSpeed(double dx, double dy) {
        this.dx = dx;  // Set speed in x-direction
        this.dy = dy;  // Set speed in y-direction
    }

    /**
     * Placeholder method for range checking. Intended to be overridden in subclasses.
     */
    protected void rangeCheck() {
        // Placeholder method for range checking
    }

    /**
     * Retrieves the current x-coordinate of the element.
     * @return The current x-coordinate.
     */
    public double getX() {
        return this.x;
    }

    /**
     * Retrieves the current y-coordinate of the element.
     * @return The current y-coordinate.
     */
    public double getY() {
        return this.y;
    }

    /**
     * Retrieves the center x-coordinate of the element.
     * @return The center x-coordinate.
     */
    public double getCenterX() {
        return this.x + this.imageview.getFitWidth() / 2;
    }

    /**
     * Retrieves the center y-coordinate of the element.
     * @return The center y-coordinate.
     */
    public double getCenterY() {
        return this.y + this.imageview.getFitHeight() / 2;
    }

    /**
     * Moves the element to its next position based on its current speed.
     * Updates the ImageView position accordingly.
     */
    public void nextStep() {
        this.x += this.dx;               // Update x-coordinate based on speed
        this.y += this.dy;               // Update y-coordinate based on speed
        this.imageview.setX(this.x);     // Update ImageView x-coordinate
        this.imageview.setY(this.y);     // Update ImageView y-coordinate
        rangeCheck();                    // Perform range checking
    }

    /**
     * Removes the ImageView of the element from the root pane.
     */
    public void removeImageView() {
        GlobalControl.rootPane.getChildren().remove(this.imageview);  // Remove ImageView from rootPane
    }

    /**
     * Retrieves the ImageView associated with the element.
     * @return The ImageView of the element.
     */
    public ImageView getImageView() {
        return this.imageview;
    }
}
