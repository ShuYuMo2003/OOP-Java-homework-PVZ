package homework;

public class NormalBullets extends Bullets{
    // static private Image NormalFrame = null;
    // static private Image BoomFrame = null;
    NormalBullets() {}
    NormalBullets(double x, double y) {
        super(x, y,
              "file:" + Constants.getImagesPath().getPath() + "Bullets/PeaNormal/PeaNormal_0.png",
              "file:" + Constants.getImagesPath().getPath() + "Bullets/PeaNormalExplode/PeaNormalExplode_0.png",
              Constants.NormalPeaDamage, Constants.NormalPeaSpeed
        );
    }
}