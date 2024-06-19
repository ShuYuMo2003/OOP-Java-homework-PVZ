package homework;

import javafx.scene.layout.Pane;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class sunflower extends Plants {
    sunflower() {}
    sunflower(int row, int column) {
        super(Constants.PlantsColumnXPos[column],
              Constants.PlantsRowYPos[row],
              ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Plants/Sunflower"),
              Constants.SunflowerHealth,
              Constants.SunflowerSunProductionFPS
        );
        initialTimeline(Constants.SunflowerFPS, true);
        initializeSunProducer();
        play();
    }

    private void initializeSunProducer() {
        Timeline sunProductionTimeline = new Timeline(new KeyFrame(
            Duration.millis(1000 / Constants.SunflowerSunProductionFPS),
            event -> produceSun()
        ));
        sunProductionTimeline.setCycleCount(Timeline.INDEFINITE);
        sunProductionTimeline.play();
    }

    private void produceSun() {
        System.out.println("Sunflower produces a sun at position (" + this.x + ", " + this.y + ")");
        // 这里你可以添加生成阳光的逻辑，例如创建一个Sun对象并将其放置在游戏界面上
    }
    @Override
    protected Bullets getNewBullets() {
        
        return null;
        
    }
    
}
