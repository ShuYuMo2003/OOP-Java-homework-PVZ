package homework;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class NormalPea extends Bullets{
    // static private Image NormalFrame = null;
    // static private Image BoomFrame = null;
    NormalPea() {}
    NormalPea(double x, double y) {
        super(x, y,
            //   "file:/" + Constants.getImagesPath().getPath() + "Bullets/PeaNormal/PeaNormal_0.png",
              "file:" + Constants.getImagesPath().getPath() + "Bullets/PeaNormal/PeaNormal_0.png",
              "file:" + Constants.getImagesPath().getPath() + "Bullets/PeaNormalExplode/PeaNormalExplode_0.png",
              Constants.NormalPeaDamage, Constants.NormalPeaSpeed
        );
    }
}
