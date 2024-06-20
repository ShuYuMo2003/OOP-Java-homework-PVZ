package homework;

import java.io.UnsupportedEncodingException;

public class BucketheadZombine extends Zombine {
    public BucketheadZombine(int row, int column) throws UnsupportedEncodingException {
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
