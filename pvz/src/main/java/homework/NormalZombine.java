package homework;

import java.nio.file.Paths;
import javafx.scene.layout.Pane;

public class NormalZombine extends Zombine{
    public NormalZombine(double x, double y, Pane rootPane) {
        super(x, y, Constants.NormalZombineSpeed, rootPane);
        addStage(new ZombineStage(
            "Initial Stage",
            ListFiles.listAllFiles(Paths.get(Constants.getImagesPath(), "Zombines/NormalZombine/Zombie").toString()),
            ListFiles.listAllFiles(Paths.get(Constants.getImagesPath(), "Zombines/NormalZombine/ZombieAttack").toString()),
            100));
        initialTimeline(Constants.NormalZombineFPS);
        play();
    }
}
