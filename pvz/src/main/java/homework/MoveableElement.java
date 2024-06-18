package homework;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public abstract class MoveableElement {
    protected double x;
    protected double y;
    protected double dx, dy;
    protected ImageView imageview;

    MoveableElement() { }

    MoveableElement(double x, double y, double dx, double dy, Pane rootPane) {
        this.x = x;
        this.y = y;
        this.setSpeed(dx, dy);
        this.imageview = new ImageView();
        this.imageview.setX(x);
        this.imageview.setY(y);
        rootPane.getChildren().add(this.imageview);
    }

    public void setSpeed(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public void nextStep() {
        this.x += this.dx;
        this.y += this.dy;
        this.imageview.setX(this.x);
        this.imageview.setY(this.y);
    }

    public void removeImageView(Pane rootPane) {
        rootPane.getChildren().remove(this.imageview);
    }
}
