package homework;

import java.util.ArrayList;
import java.util.Set;

import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;
import javafx.scene.image.Image;

/**
 * Abstract class representing a zombie-like creature capable of movement and attacks.
 */
public abstract class Zombine extends MoveableElement {
    // Attributes
    protected double damage;  // Damage inflicted by the zombine
    protected boolean isAttacking;  // Flag indicating if zombine is currently attacking
    protected ArrayList<ZombineStage> stageStatus = new ArrayList<>();  // Stages of the zombine's lifecycle
    protected Timeline timeline;  // Animation timeline for regular behavior
    protected boolean die;  // Flag indicating if zombine is dead

    // Attributes for dying animation
    protected String[] dieFramesPath;  // Paths to frames of dying animation
    protected double dieFramesFPS;  // Frames per second for dying animation
    protected int dieCurrentFrameId = 0;  // Current frame index for dying animation
    protected Timeline dieTimeline;  // Timeline for dying animation
    protected Timeline attackMusicPlay;  // Timeline for attack sound/music

    protected boolean hadShowHeadAnimation = true;  // Flag indicating if head animation has been shown

    // Static attributes for positioning adjustments and media player
    static private double xOffset = -150;
    static private double yOffset = -130;
    static MediaPlayer mediaPlayer = new MediaPlayer(Constants.getZombineAttackMusic());

    /**
     * Abstract method to get the row of the zombine in a grid or map.
     * @return Row index of the zombine.
     */
    public abstract int getRow();

    /**
     * Default constructor for zombine.
     */
    Zombine() { }

    /**
     * Constructor to initialize a zombine with specific parameters.
     * @param x Initial x-coordinate
     * @param y Initial y-coordinate
     * @param speed Movement speed
     * @param damage Damage inflicted by the zombine
     * @param dieFramesPath Paths to frames of dying animation
     * @param dieFramesFPS Frames per second for dying animation
     */
    Zombine(double x, double y, double speed, double damage, String[] dieFramesPath, double dieFramesFPS) {
        super(x + xOffset, y + yOffset, -speed, 0);  // Initialize position and speed
        this.clearStage();  // Clear initial stage status
        this.damage = damage;  // Set damage
        this.dieFramesPath = dieFramesPath;  // Set dying animation frames
        this.dieFramesFPS = dieFramesFPS;  // Set dying animation FPS
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);  // Set attack music to loop indefinitely
    }

    /**
     * Clear all stages of the zombine.
     */
    protected void clearStage() {
        stageStatus.clear();
    }

    /**
     * Add a new stage to the zombine's lifecycle.
     * @param newStage ZombineStage object representing the new stage.
     */
    protected void addStage(ZombineStage newStage) {
        stageStatus.add(newStage);
    }

    /**
     * Retrieve the path of the current frame image.
     * @return Path to the current frame image.
     */
    protected String getFramePath() {
        for(int i = 0; i < stageStatus.size(); i++) {
            if(!stageStatus.get(i).isDie()) {
                return stageStatus.get(i).getFramePath(!isAttacking);
            }
        }
        setDie();  // If no live stage found, mark zombine as dead
        return null;
    }

    /**
     * Initialize animation timeline for zombine's regular behavior.
     * @param fps Frames per second for the animation.
     */
    public void initialTimeline(double fps) {
        timeline = new Timeline(
            new KeyFrame(Duration.millis(1000 / fps), e -> {
                String curFramePath = getFramePath();
                if(curFramePath == null) {
                    timeline.stop();  // Stop timeline if zombine is dead
                    return;
                }
                imageview.setImage(Constants.getCachedImage(curFramePath));
            })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    /**
     * Retrieve the zombine's position on a map based on its current coordinates.
     * @return MapPosition object representing the zombine's position.
     */
    public MapPosition getMapPosition() {
        MapPosition mapPosition = new MapPosition(0, 0);  // Initialize map position
        double minDistance2 = Constants.INF;  // Set initial minimum distance to a large value
        for(int i = 0; i < Constants.MaxColumn; i++) {
            for(int j = 0; j < Constants.MaxRow; j++) {
                double x = Constants.ZombineColumnXPos[i] + xOffset;
                double y = Constants.ZombineRowYPos[j] + yOffset;
                double distance2 = Math.pow(x - this.x, 2) + Math.pow(y - this.y, 2);  // Calculate distance squared
                if(distance2 < minDistance2) {
                    minDistance2 = distance2;
                    mapPosition = new MapPosition(j, i);  // Update map position if closer
                }
            }
        }
        return mapPosition;
    }

    /**
     * Static method to retrieve the map position based on given coordinates.
     * @param xx X-coordinate
     * @param yy Y-coordinate
     * @return MapPosition object representing the position on the map.
     */
    public static MapPosition getMapPosition(double xx, double yy) {
        MapPosition mapPosition = new MapPosition(0, 0);  // Initialize map position
        double minDistance2 = Constants.INF;  // Set initial minimum distance to a large value
        for(int i = 0; i < Constants.MaxColumn; i++) {
            for(int j = 0; j < Constants.MaxRow; j++) {
                double x = Constants.ZombineColumnXPos[i] - 80;  // Adjusted x position
                double y = Constants.ZombineRowYPos[j] - 60;  // Adjusted y position
                double distance2 = Math.pow(x - xx, 2) + Math.pow(y - yy, 2);  // Calculate distance squared
                if(distance2 < minDistance2) {
                    minDistance2 = distance2;
                    mapPosition = new MapPosition(j, i);  // Update map position if closer
                }
            }
        }
        return mapPosition;
    }

    /**
     * Get the x-coordinate adjusted for display.
     * @return Adjusted x-coordinate.
     */
    @Override
    public double getX() {
        return this.x - xOffset;
    }

    /**
     * Get the y-coordinate adjusted for display.
     * @return Adjusted y-coordinate.
     */
    @Override
    public double getY() {
        return this.y - yOffset;
    }

    /**
     * Start the zombine's animation timeline.
     */
    public void play() {
        timeline.play();
    }

    /**
     * Pause the zombine's animation timeline.
     */
    public void pause() {
        timeline.pause();
    }

    /**
     * Get the damage inflicted by the zombine.
     * @return Damage value.
     */
    public double getDamage() {
        return damage;
    }

    /**
     * Apply an attack to the zombine, reducing its health.
     * @param damage Damage amount inflicted by the attacker.
     */
    public void applyAttack(double damage) {
        if(damage < Constants.EPS) return;  // If damage is negligible, do nothing

        for(int i = 0; i < stageStatus.size(); i++) {
            if(!stageStatus.get(i).isDie()) {
                stageStatus.get(i).applyAttack(damage);  // Apply damage to the zombine's current stage
                break;  // Stop after applying damage to the first non-dead stage
            }
        }
    }

    /**
     * Set the zombine's attacking state.
     * @param isAttacking Flag indicating if the zombine is attacking.
     */
    public void setAttack(boolean isAttacking) {
        this.isAttacking = isAttacking;
        if(isAttacking) {
            if(mediaPlayer.getStatus() != MediaPlayer.Status.PLAYING) {
                mediaPlayer.play();  // Start attack sound/music if not already playing
            }
        } else {
            if(mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                mediaPlayer.stop();  // Stop attack sound/music if playing
            }
        }
    }

    /**
     * Handle transitions between stages of the zombine's lifecycle.
     */
    protected void handleStageTransition() {
        for (int i = stageStatus.size() - 1; i >= 0; i--) {
            if (stageStatus.get(i).isDie() && stageStatus.size() > 1) {
                stageStatus.remove(i);  // Remove dead stages if there are more than one
            }
        }
    }

    /**
     * Check if the zombine can move.
     * @return True if zombine can move, false otherwise.
     */
    public boolean canMove() {
        return !isAttacking && !die;  // Zombine can move if not attacking or dead
    }

    /**
     * Check if the zombine is dead.
     * @return True if zombine is dead, false otherwise.
     */
    public boolean isDie() {
        return die;
    }
    
    /**
     *Set the zombine as dead, triggering its dying animation.
     */
        public void setDie() {
        die = true;  // Mark zombine as dead
        timeline.stop();  // Stop regular animation timeline
        dieTimeline = new Timeline(
            new KeyFrame(Duration.millis(1000 / this.dieFramesFPS), e -> {
                this.imageview.setImage(Constants.getCachedImage(dieFramesPath[dieCurrentFrameId]));  // Display current frame of dying animation
                if (dieCurrentFrameId == dieFramesPath.length - 1) {
                    dieTimeline.stop();  // Stop dying animation timeline when all frames are displayed
                    removeImageView();  // Remove image view from display
                    deprecated = true;  // Mark zombine as deprecated (no longer used)
                } else {
                    dieCurrentFrameId++;  // Move to the next frame in the dying animation
                }
            })
        );
        dieTimeline.setCycleCount(Timeline.INDEFINITE);  // Set dying animation to loop indefinitely
        dieTimeline.play();  // Start playing the dying animation timeline
    }

    /**
     * Check if the zombine is deprecated (no longer in use).
     * @return True if zombine is deprecated, false otherwise.
     */
    public boolean getDeprecated() {
        return deprecated;
    }
}

