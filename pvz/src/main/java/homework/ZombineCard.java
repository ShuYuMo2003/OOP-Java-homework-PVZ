package homework;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ZombineCard extends Card{
    String name;
    ZombineCard() {}
    ZombineCard(Image img, String name, double x, double y, double width, double height) {
        super(img, x, y, width, height);
        this.name = name;
    }

    protected void processOnMouseClicked(MouseEvent e) {
        System.err.println("Processing Cilick on " + name);
        switch (name) {
            case "NormalZombine":
                GlobalControl.setSelectedZombine(name, new ImageView(NormalZombine.staticImage) {{setX(e.getSceneX()); setY(e.getSceneY());}});
                break;
        }

    }
}
