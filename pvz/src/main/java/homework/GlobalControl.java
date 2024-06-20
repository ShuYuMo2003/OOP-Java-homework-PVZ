package homework;

import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javafx.util.Duration;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.animation.KeyFrame;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

public class GlobalControl {
    static Lock lock = new ReentrantLock();
    static ArrayList<Zombine> AllZombines = new ArrayList<>();
    static ArrayList<Plants> AllPlants = new ArrayList<>();
    static ArrayList<Bullets> AllBullets = new ArrayList<>();

    static ArrayList<MapPosition> zombinesPos = new ArrayList<>();
    static ArrayList<MapPosition> plantsPos = new ArrayList<>();

    static String selectedPlantsType = null;
    static ImageView selectedPlantsImageView = null;

    static PlantsCard[] plantsCards = new PlantsCard[Constants.PlantsCardCount];

    private static ImageView backgroundImageView;

    private static ImageView cardsChooserImageView;

    private static void initializeBackgroudImage() {
        Image backgroundImage = new Image(Constants.getBackgroudImage().toString());
        backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitHeight(Constants.WindowHeight);
        backgroundImageView.setPreserveRatio(true);
        backgroundImageView.setX(Constants.PlayingStageMapXPos);
        GlobalControl.rootPane.getChildren().add(backgroundImageView);
    }

    private static void initializePlantCardsChooser() {
        Image cardsChooserImage = new Image(Constants.getPlantsChooserImage().toString());
        cardsChooserImageView = new ImageView(cardsChooserImage);
        cardsChooserImageView.setFitWidth(Constants.WindowWidth * 0.45);
        cardsChooserImageView.setPreserveRatio(true);
        cardsChooserImageView.setX(10);
        cardsChooserImageView.setY(10);
        GlobalControl.rootPane.getChildren().add(cardsChooserImageView);
    }

    private static void initializeZombineCardsChooser() {
        Image cardsChooserImage = new Image(Constants.getZombineChooserImage().toString());
        cardsChooserImageView = new ImageView(cardsChooserImage);
        cardsChooserImageView.setFitWidth(Constants.WindowWidth * 0.45);
        cardsChooserImageView.setPreserveRatio(true);
        cardsChooserImageView.setX(Constants.WindowWidth * 0.55 - 10);
        cardsChooserImageView.setY(10);
        GlobalControl.rootPane.getChildren().add(cardsChooserImageView);
    }

    static Timeline moveStep;
    static Timeline attackListerner;
    static Timeline dieObjectCleanUper;
    static Timeline bulletsListerner;
    static Pane rootPane = new Pane();


    public static void setSelectedPlants(String type, ImageView imageView) {
        selectedPlantsType = type;
        selectedPlantsImageView = imageView;

        GlobalControl.rootPane.getChildren().add(selectedPlantsImageView);
        GlobalControl.rootPane.setOnMouseMoved(event -> {
            if (imageView != null) {
                imageView.setLayoutX(event.getX() - imageView.getFitWidth() / 2 - 50);
                imageView.setLayoutY(event.getY() - imageView.getFitHeight() / 2 - 50);
            }
        });
    }

    public static void initializeCardSelectedApply() {
        GlobalControl.rootPane.setOnMouseClicked(event -> {
            System.err.println("selectedPlantsType = " + selectedPlantsType);
            if(selectedPlantsType != null) {
                MapPosition mpos = Plants.getMapPosition(event.getX(), event.getY());
                switch(selectedPlantsType) {
                    case "Peashooter":
                        GlobalControl.addPlants(new Peashooter(mpos.row, mpos.column));
                        break;
                }
            }

            selectedPlantsType = null;
            try {
                rootPane.getChildren().remove(selectedPlantsImageView);
                selectedPlantsImageView = null;
            } catch (Exception e) {
                System.err.println("Error: " + e);
            }
            rootPane.setOnMouseMoved(null);
        });
    }

    public static void initializePlantsCardImageView() {
        int nowId = 0;
        double currentPosX = Constants.PlantsCardXPos;
        double currentPosY = Constants.PlantsCardYPos;
        for(Map.Entry<String, URL> card : Constants.PlantsCardImage) {
            plantsCards[nowId] = new PlantsCard(
                new Image(card.getValue().toString()),
                card.getKey(),
                currentPosX,
                currentPosY,
                Constants.CardWidth,
                Constants.CardHeight
            );
            currentPosX += Constants.CardWidth + Constants.CardGap;
            System.err.println("Added card " + card.getKey() + " at " + currentPosX + " " + currentPosY);
            nowId += 1;
        }
    }

    public static void setSelectedCard(Plants img) {

    }

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

    public static void zombineWin() {
        System.err.println("Zombine win!");
        System.exit(0);
    }

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
            for(Zombine z : AllZombines) {
                if(z.getX() < 0) {
                    zombineWin();
                }
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
                if(AllZombines.get(i).getDeprecated()) {
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
            for(int i = 0; i < AllBullets.size(); i++) {
                if(AllBullets.get(i).isBoomed()) {
                    AllBullets.get(i).removeImageView();
                    AllBullets.remove(i);
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
            zombinesPos.clear();
            for(Zombine z : AllZombines) {
                zombinesPos.add(z.getMapPosition());
            }
            plantsPos.clear();
            for(Plants p : AllPlants) {
                plantsPos.add(p.getMapPosition());
            }
            // zombine attack to the plants
            boolean[] attackingHappend = new boolean[AllZombines.size()];
            for(int pid = 0; pid < plantsPos.size(); pid++) {
                for(int zid = 0; zid < zombinesPos.size(); zid++) {
                    if(zombinesPos.get(zid).equals(plantsPos.get(pid))) {
                        AllZombines.get(zid).setAttack(true);
                        attackingHappend[zid] = true;
                        AllPlants.get(pid).getDamage(AllZombines.get(zid).getDamage());
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

    public static void initializeBulletsAttackListerner() {
        bulletsListerner = new Timeline();
        bulletsListerner.setCycleCount(Timeline.INDEFINITE);
        bulletsListerner.getKeyFrames().add(new KeyFrame(Duration.millis(1000 / Constants.BulletFPS), e->{
            lock.lock();
            for(Zombine z : AllZombines) {
                for(Bullets b : AllBullets) {
                    double distance2 = Math.pow(z.getX() - b.getX(), 2) + Math.pow(z.getY() - b.getY(), 2);
                    // System.err.println("distance2 = " + distance2 + " " + Constants.BulletNZombineCollisionDistance_2);
                    if(distance2 < Constants.BulletNZombineCollisionDistance_2) {
                        z.applyAttack(b.getDamage());
                        b.boom();
                    }
                    // else if (z.getMapPosition().row != b.getMapPosition().row) {
                    //     System.err.println("row z = " + z.getMapPosition().row);
                    //     System.err.println("row b = " + b.getMapPosition().row);
                    // }
                }
            }

            lock.unlock();
        }));
        bulletsListerner.play();
    }

    public static void startMoveStep() {
        moveStep.play();
    }

    public static void initializeEverything() {
        initializeMoveStep();
        initializeBackgroudImage();
        initializePlantCardsChooser();
        initializeZombineCardsChooser();
        initializeDieObjectCleanUp();
        initializeAttackingListening();
        initializeBulletsAttackListerner();
        initializePlantsCardImageView();
        initializeCardSelectedApply();
        startMoveStep();
    }

}
