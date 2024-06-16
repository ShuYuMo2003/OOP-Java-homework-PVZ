package homework;

import java.io.UnsupportedEncodingException;
import java.nio.file.Paths;
import javafx.scene.layout.Pane;

public class NormalZombine extends Zombine{
    public NormalZombine(double x, double y, Pane rootPane) throws UnsupportedEncodingException {
        super(x, y, Constants.NormalZombineSpeed, rootPane);
        addStage(new ZombineStage(
            "Initial Stage",
            ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/NormalZombie/Zombie"),
            ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/NormalZombie/ZombieAttack"),
            100));
        initialTimeline(Constants.NormalZombineFPS);
        play();
    }
}
