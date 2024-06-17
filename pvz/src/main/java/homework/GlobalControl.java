package homework;

import java.util.ArrayList;
import javafx.util.Duration;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;

public class GlobalControl {
    static ArrayList<MoveableElement> AllZombines = new ArrayList<>();
    static ArrayList<MoveableElement> AllPlants = new ArrayList<>();
    static Timeline moveStep;

    public static void addZombine(Zombine z) {
        AllZombines.add(z);
    }

    GlobalControl() { }

    public static void initializeMoveStep() {
        moveStep = new Timeline();
        moveStep.setCycleCount(Timeline.INDEFINITE);
        moveStep.getKeyFrames().add(new KeyFrame(Duration.millis(1000 / Constants.GlobalFPS), e -> {
            for(MoveableElement z : AllZombines) {
                z.nextStep();
                System.err.println("Zombine: next step.");
            }
            for(MoveableElement p : AllPlants) {
                p.nextStep();
            }
        }));
    }

    public static void startMoveStep() {
        moveStep.play();
    }

}
