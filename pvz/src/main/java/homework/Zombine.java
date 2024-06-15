package homework;

import java.util.ArrayList;

public abstract class Zombine extends MoveableElement {
    protected int attack;
    protected int speed;
    protected ArrayList<ZombineStage> stageStatue;

    Zombine() { }

    Zombine(double x, double y, int attack, int speed) {
        super(x, y);
        this.attack = attack;
        this.speed = speed;
    }

    @Override
    public void nextStep() {
        // TODO: implement here


        // 123
        return ;
    }

    protected abstract void attack();
    protected abstract void move();
    protected abstract void die();
}
