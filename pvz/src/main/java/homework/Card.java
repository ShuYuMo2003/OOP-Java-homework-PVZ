package homework;

import javax.swing.undo.AbstractUndoableEdit;

import javafx.animation.ScaleTransition;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public abstract class Card {
    private ImageView cardImageView; // ImageView for displaying the card image
    private double x; // X-coordinate of the card
    private double y; // Y-coordinate of the card
    private double width; // Width of the card
    private double height; // Height of the card

    // Color adjustment for brightening the card
    ColorAdjust brighten = new ColorAdjust() {{
        setBrightness(0.7);
    }};

    // Color adjustment for darkening the card
    ColorAdjust darker = new ColorAdjust() {{
        setBrightness(-0.7);
    }};

    ScaleTransition scaleTransition; // Animation for scaling the card

    // Getter for the card ImageView
    public ImageView getCardImageView() {
        return cardImageView;
    }

    // Default constructor
    Card() {}

    // Constructor with parameters
    Card(Image img, double x, double y, double width, double height) {
        cardImageView = new ImageView(img); // Initialize the ImageView with the given image
        this.x = x; // Set the X-coordinate
        this.y = y; // Set the Y-coordinate
        this.width = width; // Set the width
        this.height = height; // Set the height
        cardImageView.setX(x); // Set the X position of the ImageView
        cardImageView.setY(y); // Set the Y position of the ImageView
        cardImageView.setFitWidth(width); // Set the width of the ImageView
        cardImageView.setFitHeight(height); // Set the height of the ImageView

        // Initialize the scale transition animation
        scaleTransition = new ScaleTransition(Duration.millis(200), cardImageView);
        scaleTransition.setToX(1.1); // Scale to 110% on X-axis
        scaleTransition.setToY(1.1); // Scale to 110% on Y-axis
        scaleTransition.setCycleCount(1); // Only play the animation once
        scaleTransition.setAutoReverse(false); // Do not reverse the animation

        // Set event handler for mouse entering the card
        cardImageView.setOnMouseEntered(e -> {
            if(checkCanSelected()) { // Check if the card can be selected
                cardImageView.setEffect(brighten); // Apply the brighten effect
                scaleTransition.playFromStart(); // Play the scale animation from the start
            }
        });

        // Set event handler for mouse exiting the card
        cardImageView.setOnMouseExited(e -> {
            if(checkCanSelected()) { // Check if the card can be selected
                cardImageView.setEffect(null); // Remove any effect
                scaleTransition.stop(); // Stop the scale animation
                cardImageView.setScaleX(1.0); // Reset the scale on X-axis
                cardImageView.setScaleY(1.0); // Reset the scale on Y-axis
            }
        });

        // Set event handler for mouse clicking the card
        cardImageView.setOnMouseClicked(e -> {
            if(checkCanSelected()){ // Check if the card can be selected
                processOnMouseClicked(e); // Process the mouse click event
            }
            e.consume(); // Consume the event to prevent further propagation
        });

        // Add the card ImageView to the root pane
        GlobalControl.rootPane.getChildren().add(cardImageView);
    }

    // Abstract method to check if the card can be selected
    protected abstract boolean checkCanSelected();

    // Abstract method to process the mouse click event
    protected abstract void processOnMouseClicked(MouseEvent e);
}
