package homework;

import java.util.ArrayList;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;
import javafx.scene.image.Image;

public abstract class Zombine extends MoveableElement {
    protected double damage;
    protected boolean isAttacking;
    protected ArrayList<ZombineStage> stageStatus = new ArrayList<>();
    protected Timeline timeline;
    protected boolean die;

    protected String[] dieFramesPath;
    protected double dieFramesFPS;
    protected int dieCurrentFrameId = 0;
    protected Timeline dieTimeline;
    protected Timeline attackMusicPlay;

    protected boolean hadShowHeadAnimation = true;

    static private double xOffset = -150;
    static private double yOffset = -130;
    static MediaPlayer mediaPlayer = new MediaPlayer(Constants.getZombineAttackMusic());

    public abstract int getRow();


    Zombine() { }

    Zombine(double x, double y, double speed, double damage, String[] dieFramesPath, double dieFramesFPS) {
        super(x + xOffset, y + yOffset, -speed, 0);
        this.clearStage();
        this.damage = damage;
        this.dieFramesPath = dieFramesPath;
        this.dieFramesFPS = dieFramesFPS;
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    }

    protected void clearStage() { stageStatus.clear(); }

    protected void addStage(ZombineStage NewStage) { stageStatus.add(NewStage); }

    protected String getFramePath() {
        for(int i = 0; i < stageStatus.size(); i++) {
            if(!stageStatus.get(i).isDie()) {
                return stageStatus.get(i).getFramePath(!isAttacking);
            }
        }
        setDie();
        return null;
        // throw new RuntimeException("This zombine is dead.");
    }

    public void initialTimeline(double fps) {
        timeline = new Timeline(
            new KeyFrame(Duration.millis(1000 / fps), e -> {
                String curFramePath = getFramePath();
                if(curFramePath == null) {
                    timeline.stop();
                    return;
                }
                imageview.setImage(Constants.getCachedImage(curFramePath));
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
                if(distance2 < minDistance2) {
                    minDistance2 = distance2;
                    mapPosition = new MapPosition(j, i);
                }
            }
        }
        return mapPosition;
    }

    public static MapPosition getMapPosition(double xx, double yy) {
        MapPosition mapPosition = new MapPosition(0, 0);
        double minDistance2 = Constants.INF;
        for(int i = 0; i < Constants.MaxColumn; i++) {
            for(int j = 0; j < Constants.MaxRow; j++) {
                double x = Constants.ZombineColumnXPos[i] - 80;
                double y = Constants.ZombineRowYPos[j] - 60;
                double distance2 = Math.pow(x - xx, 2) + Math.pow(y - yy, 2);
                if(distance2 < minDistance2) {
                    minDistance2 = distance2;
                    mapPosition = new MapPosition(j, i);
                }
            }
        }
        return mapPosition;
    }

    @Override
    public double getX() {
        return this.x - xOffset;
    }
    @Override
    public double getY() {
        return this.y - yOffset;
    }

    public void play() {
        timeline.play();
    }

    public void pause() {
        timeline.pause();
    }

    public double getDamage() {
        return damage;
    }

    /*
     * @description: apply attack of the bullet to this zombine.
     * @param damage: the damage of the bullet
     */
    public void applyAttack(double damage) {
        if(damage < Constants.EPS) return;

        for(int i = 0; i < stageStatus.size(); i++) {
            if(!stageStatus.get(i).isDie()) {
                stageStatus.get(i).applyAttack(damage);
                break;
            }
        }
        // System.err.println("Zombine get damage: " + damage);
    }

    public void setAttack(boolean isAttacking) {
        this.isAttacking = isAttacking;
        if(isAttacking) {
            if(mediaPlayer.getStatus() != MediaPlayer.Status.PLAYING) {
                mediaPlayer.play();
            }
        } else {
            if(mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                mediaPlayer.stop();
            }
        }
        // System.err.println("set zombines attacking!");
    }
    protected void handleStageTransition() {
        for (int i = stageStatus.size() - 1; i >= 0; i--) {
            if (stageStatus.get(i).isDie() && stageStatus.size() > 1) {
                stageStatus.remove(i);
            }
        }
    }
    public boolean canMove() {
        return !isAttacking && !die;
    }
    public boolean isDie() {
        return die;
    }
    public void setDie() {
        die = true;
        timeline.stop();
        dieTimeline = new Timeline(
            new KeyFrame(Duration.millis(1000 / this.dieFramesFPS), e -> {
                this.imageview.setImage(Constants.getCachedImage(dieFramesPath[dieCurrentFrameId]));
                if (dieCurrentFrameId == dieFramesPath.length - 1) {
                    dieTimeline.stop();
                    removeImageView();
                    deprecated = true;
                } else {
                    dieCurrentFrameId++;
                }
            })
        );
        dieTimeline.setCycleCount(Timeline.INDEFINITE);
        dieTimeline.play();
    }

    public boolean getDeprecated() {
        return deprecated;
    }
}
