package homework;

import java.io.UnsupportedEncodingException;
import java.nio.file.Paths;
import javafx.scene.layout.Pane;

public class NormalZombine extends Zombine{
    public NormalZombine(int row, int column) throws UnsupportedEncodingException {
        super(Constants.getZombinePos(row, column)[0],
              Constants.getZombinePos(row, column)[1],
              Constants.NormalZombineSpeed,
              Constants.NormalZombineAttackValue,
              ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/NormalZombie/ZombieDie"),
              Constants.ZombineDieFPS);

        addStage(new ZombineStage(
            "Initial Stage",
            ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/NormalZombie/Zombie"),
            ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/NormalZombie/ZombieAttack"),
            100));

        addStage(new ZombineStage("Lost Head Stage",
            ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/NormalZombie/ZombieLostHead"),
            ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/NormalZombie/ZombieLostHeadAttack"),
            45));
        initialTimeline(Constants.NormalZombineFPS);
        play();
    }
}
