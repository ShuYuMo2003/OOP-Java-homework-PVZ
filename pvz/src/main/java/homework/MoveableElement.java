package homework;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public abstract class MoveableElement {
    protected DoubleProperty x = new SimpleDoubleProperty();
    protected DoubleProperty y = new SimpleDoubleProperty();
    protected double dx, dy;
    public ImageView imageview;

    MoveableElement() { }

    MoveableElement(double x, double y, double dx, double dy, Pane rootPane) {
        this.x.set(x);
        this.y.set(y);
        this.setSpeed(dx, dy);
        this.imageview = new ImageView();
        imageview.xProperty().bind(this.x);
        imageview.yProperty().bind(this.y);
        rootPane.getChildren().add(this.imageview);
    }

    public void setSpeed(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public void nextStep() {
        this.x.add(dx);
        this.y.add(dy);
    }
}
