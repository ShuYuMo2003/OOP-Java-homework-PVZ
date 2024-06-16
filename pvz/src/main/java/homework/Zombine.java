package homework;

import java.util.ArrayList;
import javafx.scene.layout.Pane;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;
import javafx.scene.image.Image;

public abstract class Zombine extends MoveableElement {
    protected int attackValue;
    protected boolean isAttacking;
    protected ArrayList<ZombineStage> stageStatus = new ArrayList<>();
    protected Timeline timeline;

    Zombine() { }

    Zombine(double x, double y, double speed, Pane rootPane) {
        super(x, y, -speed, 0, rootPane);
        this.clearStage();
    }

    protected void clearStage() { stageStatus.clear(); }

    protected void addStage(ZombineStage NewStage) { stageStatus.add(NewStage); }

    protected String getFramePath() {
        for(int i = stageStatus.size() - 1; i >= 0; i--) {
            if(!stageStatus.get(i).isDie()) {
                return stageStatus.get(i).getFramePath(!isAttacking);
            }
        }
        throw new RuntimeException("This zombine is dead.");
    }

    public void initialTimeline(double fps) {
        timeline = new Timeline(
            new KeyFrame(Duration.millis(1000 / fps), e -> {
                String curFramePath = getFramePath();
                System.out.println("playing " + curFramePath);
                imageview.setImage(new Image(curFramePath));
            })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    public void play() {
        timeline.play();
    }

    public void pause() {
        timeline.pause();
    }

    protected void attack() { isAttacking = true;}
    protected void die() {}
}
