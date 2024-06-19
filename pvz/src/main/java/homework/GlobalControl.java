package homework;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javafx.util.Duration;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.animation.KeyFrame;

public class GlobalControl {
    static Lock lock = new ReentrantLock();
    static ArrayList<Zombine> AllZombines = new ArrayList<>();
    static ArrayList<Plants> AllPlants = new ArrayList<>();
    static ArrayList<Bullets> AllBullets = new ArrayList<>();
    static Timeline moveStep;
    static Timeline attackListerner;
    static Timeline dieObjectCleanUper;
    static Pane rootPane = new Pane();

    public static void addBullets(Bullets b) {
        lock.lock();
        AllBullets.add(b);
        lock.unlock();
    }

    public static void addZombine(Zombine z) {
        lock.lock();
        AllZombines.add(z);
        lock.unlock();
    }

    public static void addPlants(Plants p) {
        lock.lock();
        AllPlants.add(p);
        lock.unlock();
    }

    GlobalControl() { }

    public static void initializeMoveStep() {
        moveStep = new Timeline();
        moveStep.setCycleCount(Timeline.INDEFINITE);
        moveStep.getKeyFrames().add(new KeyFrame(Duration.millis(1000 / Constants.GlobalFPS), e -> {
            lock.lock();
            for(Zombine z : AllZombines) {
                if(z.canMove())
                    z.nextStep();
            }
            for(Plants p : AllPlants) {
                p.nextStep();
            }
            for(Bullets b : AllBullets) {
                b.nextStep();
            }
            lock.unlock();
        }));
    }

    public static void initializeDieObjectCleanUp(){
        dieObjectCleanUper = new Timeline();
        dieObjectCleanUper.setCycleCount(Timeline.INDEFINITE);
        dieObjectCleanUper.getKeyFrames().add(new KeyFrame(Duration.millis(1000 / Constants.cleanUpFPS), e -> {
            lock.lock();
            for(int i = 0; i < AllZombines.size(); i++) {
                if(AllZombines.get(i).isDie()) {
                    AllZombines.get(i).removeImageView();
                    AllZombines.remove(i);
                }
            }
            for(int i = 0; i < AllPlants.size(); i++) {
                if(AllPlants.get(i).isDie()) {
                    AllPlants.get(i).removeImageView();
                    AllPlants.remove(i);
                }
            }
            lock.unlock();
        }));
        dieObjectCleanUper.play();
    }

    public static void initializeAttackingListening() {
        attackListerner = new Timeline();
        attackListerner.setCycleCount(Timeline.INDEFINITE);
        attackListerner.getKeyFrames().add(new KeyFrame(Duration.millis(1000 / Constants.ZombineAttackingFPS), e -> {
            lock.lock();
            ArrayList<MapPosition> zombinesPos = new ArrayList<>();
            for(Zombine z : AllZombines) {
                zombinesPos.add(z.getMapPosition());
            }
            ArrayList<MapPosition> plantsPos = new ArrayList<>();
            for(Plants p : AllPlants) {
                plantsPos.add(p.getMapPosition());
            }
            ArrayList<MapPosition> bulletPos = new ArrayList<>();
            for(Bullets b : AllBullets) {
               // bulletPos.add(b.getMapPosition());
            }
            boolean[] attackingHappend = new boolean[AllZombines.size()];
            for(int pid = 0; pid < plantsPos.size(); pid++) {
                for(int zid = 0; zid < zombinesPos.size(); zid++) {
                    if(zombinesPos.get(zid).equals(plantsPos.get(pid))) {
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
            lock.unlock();
        }));
        attackListerner.play();
    }

    public static void startMoveStep() {
        moveStep.play();
    }

    public static void initializeEverything() {
        initializeMoveStep();
        initializeDieObjectCleanUp();
        initializeAttackingListening();
        startMoveStep();
    }

}
