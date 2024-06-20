package homework;

import java.util.Map;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.util.Duration;


public abstract class Plants extends MoveableElement{
    protected double health;
    protected Timeline timeline;
    protected Timeline shooter;
    protected double attackFPS;
    protected String[] frames;
    protected int currentFrameId;
    protected boolean die = false;
    protected static double xOffset = -70;
    protected static double yOffset = -70;

    Plants() {}
    Plants(double x, double y, String[] framesPaths, double health, double attackFPS) {
        super(x + xOffset, y + yOffset, 0, 0);
        this.health = health;
        currentFrameId = 0;
        this.frames = framesPaths;
        this.attackFPS = attackFPS;
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

    public static MapPosition getMapPosition(double xx, double yy){
        MapPosition mapPosition = new MapPosition(0, 0);
        double minDistance2 = Constants.INF;
        for(int i = 0; i < Constants.MaxColumn; i++) {
            for(int j = 0; j < Constants.MaxRow; j++) {
                double x = Constants.PlantsColumnXPos[i] - 40;
                double y = Constants.PlantsRowYPos[j] - 20;
                double distance2 = Math.pow(x - xx, 2) + Math.pow(y - yy, 2);
                if(distance2 < minDistance2) {
                    minDistance2 = distance2;
                    mapPosition = new MapPosition(j, i);
                }
            }
        }
        return mapPosition;
    }

    public void getDamage(double damage) {
        health -= damage;
        // System.err.println("Got damage = " + damage + " health = " + health);
        if(health <= Constants.EPS) {
            setDie();
        }
    }

    protected abstract Bullets getNewBullets();

    public void initializeShooter() {
        shooter = new Timeline(
            new KeyFrame(Duration.millis(1000 / this.attackFPS), e -> {
                // System.err.println("Add new bullets!");
                GlobalControl.addBullets(getNewBullets());
            })
        );
        shooter.setCycleCount(Timeline.INDEFINITE);
    }

    public void setDie() {
        die = true;
        if(this.shooter != null)
            this.shooter.stop();
        timeline.stop();
        GlobalControl.rootPane.getChildren().remove(this.imageview);
    }

    public double getX() {
        return this.x - xOffset;
    }
    public double getY() {
        return this.y - yOffset;
    }

    public boolean isDie() {
        return die;
    }

    public void play() {
        this.timeline.play();
        if (this.shooter != null)
            this.shooter.play();
    }

    public void pause() {
        this.timeline.pause();
    }
}
