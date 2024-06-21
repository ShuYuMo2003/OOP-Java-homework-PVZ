package homework;

public class SnowBullets extends Bullets{
    // static private Image NormalFrame = null;
    // static private Image BoomFrame = null;
    SnowBullets() {}
    SnowBullets(double x, double y) {
        super(x, y,
              "file:" + Constants.getImagesPath().getPath() + "Bullets/SnowPea/SnowPea_0.png",
              "file:" + Constants.getImagesPath().getPath() + "Bullets/SnowPeaExplode/SnowPeaExplode_0.png",
              Constants.SnowPeaDamage, Constants.SnowPeaSpeed
        );
    }
}