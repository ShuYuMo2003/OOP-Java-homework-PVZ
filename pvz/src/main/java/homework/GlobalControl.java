package homework;

import java.util.ArrayList;
import javafx.util.Duration;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.animation.KeyFrame;

public class GlobalControl {
    static ArrayList<Zombine> AllZombines = new ArrayList<>();
    static ArrayList<Plants> AllPlants = new ArrayList<>();
    static Timeline moveStep;
    static Timeline attackListerner;
    static Timeline dieObjectCleanUper;
    static Pane rootPane;

    public static void addZombine(Zombine z) {
        AllZombines.add(z);
    }

    public static void addPlants(Plants p) {
        AllPlants.add(p);
    }

    GlobalControl() { }

    public static void initializeMoveStep() {
        moveStep = new Timeline();
        moveStep.setCycleCount(Timeline.INDEFINITE);
        moveStep.getKeyFrames().add(new KeyFrame(Duration.millis(1000 / Constants.GlobalFPS), e -> {
            for(Zombine z : AllZombines) {
                if(z.canMove())
                    z.nextStep();
            }
            for(Plants p : AllPlants) {
                p.nextStep();
            }
        }));
    }

    public static void initializeDieObjectCleanUp(){
        dieObjectCleanUper = new Timeline();
        dieObjectCleanUper.setCycleCount(Timeline.INDEFINITE);
        dieObjectCleanUper.getKeyFrames().add(new KeyFrame(Duration.millis(1000 / Constants.cleanUpFPS), e -> {
            for(int i = 0; i < AllZombines.size(); i++) {
                if(AllZombines.get(i).isDie()) {
                    AllZombines.get(i).removeImageView(rootPane);
                    AllZombines.remove(i);
                }
            }
            for(int i = 0; i < AllPlants.size(); i++) {
                if(AllPlants.get(i).isDie()) {
                    // System.err.println("Plant i = " + i + " is dead. ");
                    AllPlants.get(i).removeImageView(rootPane);
                    AllPlants.remove(i);
                }
            }
        }));
        dieObjectCleanUper.play();
    }

    public static void initializeAttackingListening() {
        attackListerner = new Timeline();
        attackListerner.setCycleCount(Timeline.INDEFINITE);
        attackListerner.getKeyFrames().add(new KeyFrame(Duration.millis(1000 / Constants.ZombineAttackingFPS), e -> {
            ArrayList<MapPosition> zombinesPos = new ArrayList<>();
            System.err.println("Checking for attacking!");
            for(Zombine z : AllZombines) {
                zombinesPos.add(z.getMapPosition());
                System.err.println("Zombine at " + z.getMapPosition().toString());
            }
            ArrayList<MapPosition> plantsPos = new ArrayList<>();
            for(Plants p : AllPlants) {
                plantsPos.add(p.getMapPosition());
                System.err.println("Plants at " + p.getMapPosition().toString());
            }
            boolean[] attackingHappend = new boolean[AllZombines.size()];
            for(int pid = 0; pid < plantsPos.size(); pid++) {
                for(int zid = 0; zid < zombinesPos.size(); zid++) {
                    if(zombinesPos.get(zid).equals(plantsPos.get(pid))) {
                        System.err.println("Attacking between " + pid + ", " + zid);
                        AllZombines.get(zid).setAttack(true);
                        attackingHappend[zid] = true;
                        AllPlants.get(pid).getDamage(AllZombines.get(zid).getAttackValue());
                    }
                }
            }
            for(int zid = 0; zid < zombinesPos.size(); zid++) {
                if(!attackingHappend[zid]) {
                    AllZombines.get(zid).setAttack(false);
                }
            }
        }));
        attackListerner.play();
    }

    public static void startMoveStep() {
        moveStep.play();
    }

    public static void initializeEverything(Pane _rootPane) {
        rootPane = _rootPane;
        initializeMoveStep();
        initializeDieObjectCleanUp();
        initializeAttackingListening();
        startMoveStep();
    }

}
