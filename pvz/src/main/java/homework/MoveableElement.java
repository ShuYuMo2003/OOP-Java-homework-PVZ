package homework;

public abstract class MoveableElement {
    protected double x;
    protected double y;

    MoveableElement() { }

    MoveableElement(double x, double y) {
        this.x = x;
        this.y = y;
    }

    protected abstract void nextStep();
}
