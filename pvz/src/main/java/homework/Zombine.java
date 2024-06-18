package homework;

import java.util.ArrayList;
import javafx.scene.layout.Pane;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;
import javafx.scene.image.Image;

public abstract class Zombine extends MoveableElement {
    protected double attackValue;
    protected boolean isAttacking;
    protected ArrayList<ZombineStage> stageStatus = new ArrayList<>();
    protected Timeline timeline;
    protected boolean die;

    static private double xOffset = -150;
    static private double yOffset = -130;

    Zombine() { }

    Zombine(double x, double y, double speed, double attackValue) {
        super(x + xOffset, y + yOffset, -speed, 0);
        this.clearStage();
        this.attackValue = attackValue;
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
                imageview.setImage(new Image(curFramePath));
            })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    public MapPosition getMapPosition() {
        // System.err.println("x = " + this.x + " y = " + this.y);
        MapPosition mapPosition = new MapPosition(0, 0);
        double minDistance2 = Constants.INF;
        for(int i = 0; i < Constants.MaxColumn; i++) {
            for(int j = 0; j < Constants.MaxRow; j++) {
                double x = Constants.ZombineColumnXPos[i] + xOffset;
                double y = Constants.ZombineRowYPos[j] + yOffset;
                double distance2 = Math.pow(x - this.x, 2) + Math.pow(y - this.y, 2);
                // System.err.println("checkingx = " + x + " y = " + y + " dis=" + distance2);
                if(distance2 < minDistance2) {
                    minDistance2 = distance2;
                    mapPosition = new MapPosition(j, i);
                    // System.err.println("Update to " + mapPosition.toString());
                }
            }
        }
        return mapPosition;
    }

    public void play() {
        timeline.play();
    }

    public void pause() {
        timeline.pause();
    }

    public double getAttackValue() {
        return attackValue;
    }

    public void setAttack(boolean isAttacking) {
        this.isAttacking = isAttacking;
        // System.err.println("set zombines attacking!");
    }
    public boolean canMove() {
        return !isAttacking && !die;
    }
    public boolean isDie() {
        return die;
    }
    public void setDie() {
        die = true;
    }
}
