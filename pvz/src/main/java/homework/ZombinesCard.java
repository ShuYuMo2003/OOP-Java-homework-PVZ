package homework;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * ZombinesCard class represents a card that a player can use to select different types of zombies in the game.
 * It extends the Card class and includes specific functionalities for handling zombie card selections.
 */
public class ZombinesCard extends Card {
    String name;

    /**
     * Default constructor for ZombinesCard.
     */
    ZombinesCard() {}

    /**
     * Parameterized constructor for ZombinesCard.
     * 
     * @param img The image of the card.
     * @param name The name of the zombie type.
     * @param x The x-coordinate of the card's position.
     * @param y The y-coordinate of the card's position.
     * @param width The width of the card.
     * @param height The height of the card.
     */
    ZombinesCard(Image img, String name, double x, double y, double width, double height) {
        super(img, x, y, width, height);
        this.name = name;
        checkCanSelected();
    }

    /**
     * Method to handle the mouse click event on the card.
     * 
     * @param e The MouseEvent triggered when the card is clicked.
     */
    protected void processOnMouseClicked(MouseEvent e) {
        // Check if the game is in server mode and not in single player mode, then return without doing anything.
        if (Constants.isServerNPlants && !Constants.GameModeSingle) {
            return;
        }
        System.err.println("Processing Click on " + name);

        // Switch case to handle different types of zombies based on the card's name.
        switch (name) {
            case "NormalZombine":
                GlobalControl.setSelectedZombine(name, new ImageView(NormalZombine.staticImage) {{
                    setX(e.getSceneX());
                    setY(e.getSceneY());
                }});
                break;
            case "ConeheadZomine":
                GlobalControl.setSelectedZombine(name, new ImageView(ConeheadZomine.staticImage) {{
                    setX(e.getSceneX());
                    setY(e.getSceneY());
                }});
                break;
            case "FlagZombine":
                GlobalControl.setSelectedZombine(name, new ImageView(FlagZombine.staticImage) {{
                    setX(e.getSceneX());
                    setY(e.getSceneY());
                }});
                break;
            case "NewspaperZombine":
                GlobalControl.setSelectedZombine(name, new ImageView(NewspaperZombine.staticImage) {{
                    setX(e.getSceneX());
                    setY(e.getSceneY());
                }});
                break;
            case "BucketHeadZombine":
                GlobalControl.setSelectedZombine(name, new ImageView(BucketHeadZombine.staticImage) {{
                    setX(e.getSceneX());
                    setY(e.getSceneY());
                }});
                break;
            default:
                // Throw an exception if the zombie name is unknown.
                throw new RuntimeException("Unknown Zombine name: " + name);
        }
    }

    /**
     * Method to check if the card can be selected.
     * 
     * @return true if the card can be selected, false otherwise.
     */
    protected boolean checkCanSelected() {
        // Check if the player has enough brains to select the zombie and if the game is not in server mode.
        if (GlobalControl.getBrainCount() < Constants.ZombineBrainCost.get(name) || (Constants.isServerNPlants && !Constants.GameModeSingle)) {
            // If not selectable, apply a darker effect to the card image.
            getCardImageView().setEffect(darker);
            return false;
        }
        // If selectable, remove any effects from the card image.
        getCardImageView().setEffect(null);
        return true;
    }
}
