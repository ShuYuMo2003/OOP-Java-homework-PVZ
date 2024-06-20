package homework;

import javafx.scene.image.ImageView;

public abstract class MoveableElement {
    protected boolean deprecated = false;
    protected double x;
    protected double y;
    protected double dx, dy;
    protected ImageView imageview;

    MoveableElement() { }

    MoveableElement(double x, double y, double dx, double dy) {
        this.x = x;
        this.y = y;
        this.setSpeed(dx, dy);
        this.imageview = new ImageView();
        this.imageview.setX(x);
        this.imageview.setY(y);
        GlobalControl.rootPane.getChildren().add(this.imageview);
    }

    public void setSpeed(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    protected void rangeCheck() {}

    public double getX() {
        return this.x;
    }
    public double getY() {
        return this.y;
    }

    public double getCenterX() {
        return this.x + this.imageview.getFitWidth() / 2;
    }
    public double getCenterY() {
        return this.y + this.imageview.getFitHeight() / 2;
    }

    public void nextStep() {
        this.x += this.dx;
        this.y += this.dy;
        this.imageview.setX(this.x);
        this.imageview.setY(this.y);
        rangeCheck();
    }

    public void removeImageView() {
        GlobalControl.rootPane.getChildren().remove(this.imageview);
    }

    public ImageView getImageView() {
        return this.imageview;
    }
}
