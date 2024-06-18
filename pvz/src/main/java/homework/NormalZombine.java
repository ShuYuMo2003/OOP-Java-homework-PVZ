package homework;

import java.io.UnsupportedEncodingException;
import java.nio.file.Paths;
import javafx.scene.layout.Pane;

public class NormalZombine extends Zombine{
    static private double xOffset = -300;
    static private double yOffset = -300;
    public NormalZombine(int row, int column, Pane rootPane) throws UnsupportedEncodingException {
        super(Constants.getPos(row, column)[0] + xOffset,
              Constants.getPos(row, column)[1] + yOffset,
              Constants.NormalZombineSpeed, rootPane);
        System.out.println("x = " + Constants.getPos(row, column)[0] + xOffset);
        System.err.println("y = " + Constants.getPos(row, column)[1] + yOffset);
        addStage(new ZombineStage(
            "Initial Stage",
            ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/NormalZombie/Zombie"),
            ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/NormalZombie/ZombieAttack"),
            100));
        initialTimeline(Constants.NormalZombineFPS);
        play();
    }
}
