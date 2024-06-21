package homework;

import java.io.UnsupportedEncodingException;

import javafx.scene.image.Image;

public class BucketHeadZombine extends Zombine {
    public static Image staticImage = new Image(
        ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/BucketheadZombie/BucketheadZombie")[0]
    );
    public BucketHeadZombine(int row, int column) {
        super(Constants.getZombinePos(row, column)[0],
              Constants.getZombinePos(row, column)[1],
              Constants.BucketheadZombineSpeed,
              Constants.BucketheadZombineAttackValue,
              ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/NormalZombie/ZombieDie"),
              Constants.ZombineDieFPS);

        addStage(new ZombineStage(
            "With Bucket Stage",
            ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/BucketheadZombie/BucketheadZombie"),
            ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/BucketheadZombie/BucketheadZombieAttack"),
            550));
        addStage(new ZombineStage("Without Bucket Stage",
            ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/NormalZombie/Zombie"),
            ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/NormalZombie/ZombieAttack"),
            100));
        addStage(new ZombineStage("Lost head Stage",
            ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/NormalZombie/ZombieLostHead"),
            ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/NormalZombie/ZombieLostHeadAttack"),
            45));

        initialTimeline(Constants.BucketheadZombineFPS);
        play();
    }
}
