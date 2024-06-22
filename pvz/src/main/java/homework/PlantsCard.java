package homework;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class PlantsCard extends Card{
    String name;

    PlantsCard() {}
    PlantsCard(Image img, String name, double x, double y, double width, double height) {
        super(img, x, y, width, height);
        this.name = name;
        checkCanSelected();
    }

    protected void processOnMouseClicked(MouseEvent e) {
        System.err.println("Processing Cilick on " + name);
        if(!Constants.isServerNPlants && !Constants.GameModeSingle) {
            return;
        }
        switch (name) {
            case "Peashooter":
                GlobalControl.setSelectedPlants(name, new ImageView(Peashooter.staticImage){{setX(e.getSceneX()); setY(e.getSceneY());}});
                break;
            case "Sunflower":
                GlobalControl.setSelectedPlants(name, new ImageView(Sunflower.staticImage){{setX(e.getSceneX()); setY(e.getSceneY());}});
                break;
            case "PepPeaShooter":
                GlobalControl.setSelectedPlants(name, new ImageView(PepPeaShooter.staticImage){{setX(e.getSceneX()); setY(e.getSceneY());}});
                break;
            case "SnowPeashooter":
                GlobalControl.setSelectedPlants(name, new ImageView(SnowPeashooter.staticImage){{setX(e.getSceneX()); setY(e.getSceneY());}});
                break;
            case "Chomper":
                GlobalControl.setSelectedPlants(name, new ImageView(Chomper.staticImage){{setX(e.getSceneX()); setY(e.getSceneY());}});
                break;
            case "Squash":
                GlobalControl.setSelectedPlants(name, new ImageView(Squash.staticImage){{setX(e.getSceneX()) ; setY(e.getSceneY());}});
                break;

        }
    }
    protected boolean checkCanSelected() {
        if(GlobalControl.getSunCount() < Constants.PlantsSunCost.get(name) || (!Constants.isServerNPlants && !Constants.GameModeSingle)) {
            getCardImageView().setEffect(darker);
            return false;
        }
        getCardImageView().setEffect(null);
        return true;
    }

}
