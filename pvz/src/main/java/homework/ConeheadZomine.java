package homework;

import java.io.UnsupportedEncodingException;

import javafx.scene.image.Image;

public class ConeheadZomine extends Zombine{
    public static Image staticImage = Constants.getCachedImage(
        ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/ConeheadZombie/ConeheadZombie")[0]
    );
    public ConeheadZomine(int row, int column) {
        super(Constants.getZombinePos(row, column)[0],
              Constants.getZombinePos(row, column)[1],
              Constants.ConeheadZombineSpeed,
              Constants.ConeheadZombineAttackValue,
              ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/NormalZombie/ZombieDie"),
              Constants.ZombineDieFPS);

        addStage(new ZombineStage(
            "With Cone Stage",
            ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/ConeheadZombie/ConeheadZombie"),
            ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/ConeheadZombie/ConeheadZombieAttack"),
            150));
        addStage(new ZombineStage(
            "Without Cone Stage",
            ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/NormalZombie/Zombie"),
            ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/NormalZombie/ZombieAttack"),
        95));
        addStage(new ZombineStage(
            "Lost Head Stage",
            ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/NormalZombie/ZombieLostHead"),
            ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/NormalZombie/ZombieLostHeadAttack"),
        45));

        initialTimeline(Constants.ConeheadZombineFPS);
        play();
    }
}


