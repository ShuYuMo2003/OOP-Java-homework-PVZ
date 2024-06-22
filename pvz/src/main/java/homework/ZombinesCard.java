package homework;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ZombinesCard extends Card{
    String name;
    ZombinesCard() {}
    ZombinesCard(Image img, String name, double x, double y, double width, double height) {
        super(img, x, y, width, height);
        this.name = name;
        checkCanSelected();
    }

    protected void processOnMouseClicked(MouseEvent e) {
        if(Constants.isServerNPlants && !Constants.GameModeSingle) {
            return;
        }
        System.err.println("Processing Cilick on " + name);
        switch (name) {
            case "NormalZombine":
                GlobalControl.setSelectedZombine(name, new ImageView(NormalZombine.staticImage) {{setX(e.getSceneX()); setY(e.getSceneY());}});
                break;
            case "ConeheadZomine":
                GlobalControl.setSelectedZombine(name, new ImageView(ConeheadZomine.staticImage) {{setX(e.getSceneX()); setY(e.getSceneY());}});
                break;
            case "FlagZombine":
                GlobalControl.setSelectedZombine(name, new ImageView(FlagZombine.staticImage) {{setX(e.getSceneX()); setY(e.getSceneY());}});
                break;
            case "NewspaperZombine":
                GlobalControl.setSelectedZombine(name, new ImageView(NewspaperZombine.staticImage) {{setX(e.getSceneX()); setY(e.getSceneY());}});
                break;
            case "BucketHeadZombine":
                GlobalControl.setSelectedZombine(name, new ImageView(BucketHeadZombine.staticImage) {{setX(e.getSceneX()); setY(e.getSceneY());}});
                break;
            default:
                throw new RuntimeException("Unknown Zombine name: " + name);
        }
    }
    protected boolean checkCanSelected() {
        if(GlobalControl.getBrainCount() < Constants.ZombineBrainCost.get(name) || (Constants.isServerNPlants && !Constants.GameModeSingle)) {
            getCardImageView().setEffect(darker);
            return false;
        }
        getCardImageView().setEffect(null);
        return true;
    }
}
