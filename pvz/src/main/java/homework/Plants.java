package homework;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.util.Duration;


public abstract class Plants extends MoveableElement{
    protected double health;
    protected Timeline timeline;
    protected String[] frames;
    protected int currentFrameId;

    Plants() {}
    Plants(double x, double y, String[] framesPaths, double health, Pane rootPane) {
        super(x, y, 0, 0, rootPane);
        this.health = health;
        currentFrameId = 0;
        this.frames = framesPaths;
    }

    protected void initialTimeline(double fps, boolean infinte) {
        this.timeline = new Timeline(
            new KeyFrame(Duration.millis(1000 / fps), e -> {
                currentFrameId = (currentFrameId + 1) % frames.length;
                this.imageview.setImage(new Image(frames[currentFrameId]));
            })
        );
        if(infinte) this.timeline.setCycleCount(Timeline.INDEFINITE);
        else this.timeline.setCycleCount(1);
    }

    public void play() {
        this.timeline.play();
    }

    public void pause() {
        this.timeline.pause();
    }
}
