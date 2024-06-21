package homework;


import javafx.scene.image.Image;

public class NewspaperZombine extends Zombine {
    public static Image staticImage = new Image(
        ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/NewspaperZombie/NewspaperZombie")[0]
    );
    public NewspaperZombine(int row, int column) {
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
