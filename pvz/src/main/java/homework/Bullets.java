package homework;

import javafx.scene.layout.Pane;

public abstract class Bullets extends MoveableElement {
    protected double damage;
    protected String[] frames;
    protected int currentFrameId;
    static double xOffset = -70;
    static double yOffset = -70;

    Bullets() {}

    Bullets(double x, double y, String[] framePath, double damage, double speed, Pane rootPane) {
        super(x + xOffset, y + yOffset, speed, 0, rootPane);
        this.damage = damage;
        this.frames = framePath;
        this.currentFrameId = 0;
    }
}
