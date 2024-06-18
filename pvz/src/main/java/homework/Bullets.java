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

    @Override
    protected void rangeCheck() {
        if(this.x > Constants.WindowHeight) {
            GlobalControl.rootPane.getChildren().remove(this.imageview);
        }
    }
}
