package homework;

import java.io.UnsupportedEncodingException;

public class ConeheadZombine extends NormalZombine {
    public ConeheadZombine(int row, int column) throws UnsupportedEncodingException {
        super(row, column);
        

        this.clearStage();
        
        
        addStage(new ZombineStage(
            "Hat Stage",
            ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/ConeheadZombie/Zombie"),
            ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/ConeheadZombie/ZombieAttack"),
            150)); 
        
        
        addStage(new ZombineStage(
            "Normal Stage",
            ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/NormalZombie/Zombie"),
            ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Zombies/NormalZombie/ZombieAttack"),
            100));
        
        // Initialize the timeline and start the animation
        initialTimeline(Constants.NormalZombineFPS);
        play();
    }
}


