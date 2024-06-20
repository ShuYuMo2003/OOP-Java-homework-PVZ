package homework;

import java.io.UnsupportedEncodingException;

public class NewspaperZombine extends Zombine {
    public NewspaperZombine(int row, int column) throws UnsupportedEncodingException {
        super(Constants.getZombinePos(row, column)[0],
              Constants.getZombinePos(row, column)[1],
              Constants.NewspaperZombineSpeed,
              Constants.NewspaperZombineAttackValue,
              ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/NewspaperZombie/NewspaperZombieDie"),
              Constants.ZombineDieFPS);

        addStage(new ZombineStage(
            "With Newspaper Stage",
            ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/NewspaperZombie/NewspaperZombie"),
            ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/NewspaperZombie/NewspaperZombieAttack"),
            50));
        addStage(new ZombineStage("Without Newspaper Stage",
            ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/NewspaperZombie/NewspaperZombieNoPaper"),
            ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/NewspaperZombie/NewspaperZombieNoPaperAttack"),
            100));
        addStage(new ZombineStage("Lost Head Stage",
            ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/NewspaperZombie/NewspaperZombieLostHead"),
            ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/NewspaperZombie/NewspaperZombieLostHeadAttack"),
            45));

        initialTimeline(Constants.NewspaperZombineFPS);
        play();
    }
}
