package homework;

import java.io.UnsupportedEncodingException;
import java.nio.file.Paths;
import javafx.scene.layout.Pane;

public class NormalZombine extends Zombine{
    public NormalZombine(int row, int column, Pane rootPane) throws UnsupportedEncodingException {
        super(Constants.getZombinePos(row, column)[0],
              Constants.getZombinePos(row, column)[1],
              Constants.NormalZombineSpeed,
              Constants.NormalZombineAttackValue,
              rootPane);

        addStage(new ZombineStage(
            "Initial Stage",
            ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/NormalZombie/Zombie"),
            ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/NormalZombie/ZombieAttack"),
            100));
        initialTimeline(Constants.NormalZombineFPS);
        play();
    }
}
