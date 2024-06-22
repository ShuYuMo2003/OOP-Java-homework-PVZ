package homework;

import java.io.UnsupportedEncodingException;

import javafx.scene.image.Image;

public class FlagZombine extends Zombine {
    public static Image staticImage = Constants.getCachedImage(
        ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/FlagZombie/FlagZombie")[0]
    );
    private int row;
    public int getRow() { return row; }
    public FlagZombine(int row, int column) {
        super(Constants.getZombinePos(row, column)[0],
              Constants.getZombinePos(row, column)[1],
              Constants.FlagZombineSpeed,
              Constants.FlagZombineAttackValue,
              ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/NormalZombie/ZombieDie"),
              Constants.ZombineDieFPS);
        this.row = row;
        addStage(new ZombineStage(
            "With Flag Stage",
            ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/FlagZombie/FlagZombie"),
            ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/FlagZombie/FlagZombieAttack"),
            100));
        addStage(new ZombineStage("Lost Head Stage",
            ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/FlagZombie/FlagZombieLostHead"),
            ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/FlagZombie/FlagZombieLostHeadAttack"),
            45));

        initialTimeline(Constants.FlagZombineFPS);
        play();
    }
}
