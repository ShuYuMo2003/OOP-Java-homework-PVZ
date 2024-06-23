package homework;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * Represents a card for selecting different plants in the game.
 * Extends from Card and handles mouse click events for plant selection.
 */
public class PlantsCard extends Card {

    String name;  // Name of the plant associated with the card

    /**
     * Default constructor for PlantsCard.
     */
    PlantsCard() {
        // Default constructor not used in current implementation
    }

    /**
     * Constructor for PlantsCard.
     * @param img Image associated with the card.
     * @param name Name of the plant.
     * @param x X-coordinate of the card position.
     * @param y Y-coordinate of the card position.
     * @param width Width of the card.
     * @param height Height of the card.
     */
    PlantsCard(Image img, String name, double x, double y, double width, double height) {
        super(img, x, y, width, height);  // Call superclass constructor
        this.name = name;  // Set plant name
        checkCanSelected();  // Check if the card can be selected
    }

    /**
     * Handles mouse click events on the card.
     * @param e MouseEvent triggered by the click.
     */
    protected void processOnMouseClicked(MouseEvent e) {
        System.err.println("Processing Click on " + name);  // Print message for debugging

        // Check game mode conditions for plant selection
        if (!Constants.isServerNPlants && !Constants.GameModeSingle) {
            return;  // If not in single player mode or not server, return without selection
        }

        // Switch based on plant name to set selected plant in GlobalControl
        switch (name) {
            case "Peashooter":
                GlobalControl.setSelectedPlants(name, new ImageView(Peashooter.staticImage) {{
                    setX(e.getSceneX());
                    setY(e.getSceneY());
                }});
                break;
            case "Sunflower":
                GlobalControl.setSelectedPlants(name, new ImageView(Sunflower.staticImage) {{
                    setX(e.getSceneX());
                    setY(e.getSceneY());
                }});
                break;
            case "PepPeaShooter":
                GlobalControl.setSelectedPlants(name, new ImageView(PepPeaShooter.staticImage) {{
                    setX(e.getSceneX());
                    setY(e.getSceneY());
                }});
                break;
            case "SnowPeashooter":
                GlobalControl.setSelectedPlants(name, new ImageView(SnowPeashooter.staticImage) {{
                    setX(e.getSceneX());
                    setY(e.getSceneY());
                }});
                break;
            case "Chomper":
                GlobalControl.setSelectedPlants(name, new ImageView(Chomper.staticImage) {{
                    setX(e.getSceneX());
                    setY(e.getSceneY());
                }});
                break;
            case "Squash":
                GlobalControl.setSelectedPlants(name, new ImageView(Squash.staticImage) {{
                    setX(e.getSceneX());
                    setY(e.getSceneY());
                }});
                break;
        }
    }

    /**
     * Checks if the card can be selected based on game conditions.
     * @return True if the card can be selected, false otherwise.
     */
    protected boolean checkCanSelected() {
        // Check if there are enough sun points or not in single player mode
        if (GlobalControl.getSunCount() < Constants.PlantsSunCost.get(name) || (!Constants.isServerNPlants && !Constants.GameModeSingle)) {
            getCardImageView().setEffect(darker);  // Set darker effect on the card image
            return false;  // Return false indicating card cannot be selected
        }
        getCardImageView().setEffect(null);  // Remove any effect on the card image
        return true;  // Return true indicating card can be selected
    }
}
