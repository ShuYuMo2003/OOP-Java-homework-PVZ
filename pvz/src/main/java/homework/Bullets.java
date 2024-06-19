package homework;

import java.util.HashMap;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public abstract class Bullets extends MoveableElement {
    protected double damage;
    protected Image normalFrame;
    protected Image boomedFrame;

    static double xOffset = 15;
    static double yOffset = 0;

    protected boolean boomed = false;

    Bullets() {}

    Bullets(double x, double y,
        String normalFramePath,
        String boomedFramePath,
        double damage, double speed) {

        super(x + xOffset, y + yOffset, speed, 0);

        this.damage = damage;

        this.normalFrame = Constants.getCachedImage(normalFramePath);
        this.boomedFrame = Constants.getCachedImage(boomedFramePath);
        imageview.setImage(this.normalFrame);
    }

    public void boom() {
        imageview.setImage(this.boomedFrame);
        boomed = true;
        Timeline tt = new Timeline(
            new KeyFrame(Duration.millis(500), e -> {
                GlobalControl.rootPane.getChildren().remove(imageview);
            })
        );
        tt.setCycleCount(1);
    }

    public MapPosition getMapPosition() {
        MapPosition mapPosition = new MapPosition(0, 0);
        double minDistance2 = Constants.INF;
        for(int i = 0; i < Constants.MaxColumn; i++) {
            for(int j = 0; j < Constants.MaxRow; j++) {
                double x = Constants.PlantsColumnXPos[i] + xOffset;
                double y = Constants.PlantsRowYPos[j] + yOffset;
                double distance2 = Math.pow(x - this.x, 2) + Math.pow(y - this.y, 2);
                if(distance2 < minDistance2) {
                    minDistance2 = distance2;
                    mapPosition = new MapPosition(j, i);
                }
            }
        }
        return mapPosition;
    }


    @Override
    protected void rangeCheck() {
        if(this.x > Constants.WindowHeight) {
            GlobalControl.rootPane.getChildren().remove(this.imageview);
        }
    }
}
