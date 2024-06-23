package homework;


import javafx.scene.image.Image;

public class NewspaperZombine extends Zombine {
    public static Image staticImage = Constants.getCachedImage(
        ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/NewspaperZombie/NewspaperZombie")[0]
    );
    private int row;
    public int getRow() { return row; }
    public NewspaperZombine(int row, int column) {
        super(Constants.getZombinePos(row, column)[0],
              Constants.getZombinePos(row, column)[1],
              Constants.NewspaperZombineSpeed,
              Constants.NewspaperZombineAttackValue,
              ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/NewspaperZombie/NewspaperZombieDie"),
              Constants.ZombineDieFPS);
        this.row = row;
        addStage(new ZombineStage(
            "With Newspaper Stage",
            ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/NewspaperZombie/NewspaperZombie"),
            ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/NewspaperZombie/NewspaperZombieAttack"),
            130));
        addStage(new ZombineStage("Without Newspaper Stage",
            ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/NewspaperZombie/NewspaperZombieNoPaper"),
            ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/NewspaperZombie/NewspaperZombieNoPaperAttack"),
            55));
        addStage(new ZombineStage("Lost Head Stage",
            ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/NewspaperZombie/NewspaperZombieLostHead"),
            ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/NewspaperZombie/NewspaperZombieLostHeadAttack"),
            45));

        initialTimeline(Constants.NewspaperZombineFPS);
        play();
    }
}
