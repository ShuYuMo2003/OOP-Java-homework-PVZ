package homework;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javafx.util.Duration;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.animation.KeyFrame;
import javafx.scene.image.ImageView;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.animation.TranslateTransition;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;



public class GlobalControl {
    static Lock lock = new ReentrantLock();
    static ArrayList<Zombine> AllZombines = new ArrayList<>();
    static ArrayList<Plants> AllPlants = new ArrayList<>();
    static ArrayList<Bullets> AllBullets = new ArrayList<>();
    static ArrayList<Sun> AllSuns = new ArrayList<>();
    static ArrayList<Brain> AllBrains = new ArrayList<>();

    static ArrayList<String> MessageQueue = new ArrayList<>();

    static ArrayList<MapPosition> zombinesPos = new ArrayList<>();
    static ArrayList<MapPosition> plantsPos = new ArrayList<>();

    static String selectedPlantsType = null;
    static ImageView selectedPlantsImageView = null;

    static String selectedZombineType = null;
    static ImageView selectedZombineImageView = null;
    static int haveResult = -1;

    static PlantsCard[] plantsCards = new PlantsCard[Constants.PlantsCardCount];
    static ZombinesCard[] zombineCards = new ZombinesCard[Constants.ZombineCardCount];
    

    private static ImageView backgroundImageView;
    private static ImageView cardsChooserImageView;
    private static Zombine winZombine = null;

    private static MediaPlayer mainBgmPlayer;
    
    
    private static int sunCount = 0;
    private static int brainCount = 0;
    private static Label sunLabel = new Label();
    private static Label brainLabel = new Label();

    public static void initializeSunNBrainLabel() {
        sunLabel.setText("0000000000000000000000000000000");
        brainLabel.setText("0000000000000000000000000000000");
        sunLabel.setLayoutX(70);
        sunLabel.setLayoutY(70);
        sunLabel.setFont(Font.font("Arial", FontWeight.BOLD, 40));

        rootPane.getChildren().addAll(sunLabel, brainLabel);
    }

   

    public static void setSunCount(int count) {
        lock.lock();
        try {
            sunCount = count;
            updateSunLabel();
        } finally {
            lock.unlock();
        }
    }

    public static void modifySunCount(int delta) {
        lock.lock();
        try {
            sunCount += delta;
            updateSunLabel();
        } finally {
            lock.unlock();
        }
    }

    public static void addSun(Sun sun) {
        lock.lock();
        try {
            AllSuns.add(sun);
            modifySunCount(25);
        } finally {
            lock.unlock();
        }
    }

    public static void spendSun(int cost) {
        lock.lock();
        try {
            sunCount -= cost;
            updateSunLabel();
        } finally {
            lock.unlock();
        }
    }

    private static void updateSunLabel() {
        Platform.runLater(() -> sunLabel.setText("Sun: " + sunCount));
    }

    public static void setBrainCount(int count) {
        lock.lock();
        try {
            brainCount = count;
            modifyBrainCount(0);
        } finally {
            lock.unlock();
        }
    }

    public static void modifyBrainCount(int delta) {
        lock.lock();
        try {
            brainCount += delta;
            updateBrainLabel();
            int nowId = 0;
            for (Map.Entry<String, URL> card : Constants.ZombineCardImage) {
                if (Constants.ZombineBrainCost.get(card.getKey()) <= brainCount) {
                    zombineCards[nowId].getCardImageView().setEffect(new ColorAdjust() {{
                        setBrightness(0.5);
                    }});
                }
                nowId += 1;
            }
        } finally {
            lock.unlock();
        }
    }

    public static void addBrain(Brain brain) {
        lock.lock();
        try {
            AllBrains.add(brain);
            modifyBrainCount(25);
        } finally {
            lock.unlock();
        }
    }

    public static void spendBrain(int cost) {
        lock.lock();
        try {
            brainCount -= cost;
            updateBrainLabel();
        } finally {
            lock.unlock();
        }
    }

    private static void updateBrainLabel() {
        Platform.runLater(() -> brainLabel.setText("Brain: " + brainCount));
    }

    private static void refreshBG() {
        try {
            rootPane.getChildren().remove(backgroundImageView);
        } catch (Exception e) {
            System.err.println("Error: " + e);
        }
        Image backgroundImage = new Image(Constants.getBackgroudImage().toString());
        backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitHeight(Constants.WindowHeight);
        backgroundImageView.setPreserveRatio(true);
        backgroundImageView.setX(Constants.PlayingStageMapXPos);
        GlobalControl.rootPane.getChildren().add(0, backgroundImageView);
    }

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
    static Timeline processMessageQueue;
    static Pane rootPane = new Pane();


    public static void setSelectedPlants(String type, ImageView imageView) {
        if (selectedPlantsImageView != null) {
            try {
                rootPane.getChildren().remove(selectedPlantsImageView);
            } catch (Exception e) {
                System.err.println("Error: " + e);
            }
        }
        selectedPlantsType = type;
        selectedPlantsImageView = imageView;



        GlobalControl.rootPane.getChildren().add(selectedPlantsImageView);
        GlobalControl.rootPane.setOnMouseMoved(event -> {
            double xOffset = 0;
            double yOffset = 0;
            if(selectedPlantsType == "Squash") {
                xOffset = 0;
                yOffset = -100;
            }
            if (imageView != null) {
                imageView.setX(event.getX() - imageView.getFitWidth() / 2 - 50 + xOffset);
                imageView.setY(event.getY() - imageView.getFitHeight() / 2 - 50 + yOffset);
            }
        });
    }

    public static void setSelectedZombine(String type, ImageView imageView) {
        if(selectedZombineImageView != null) {
            try {
                rootPane.getChildren().remove(selectedZombineImageView);
            } catch (Exception e) {
                System.err.println("Error: " + e);
            }
        }
        selectedZombineType = type;
        selectedZombineImageView = imageView;

        GlobalControl.rootPane.getChildren().add(selectedZombineImageView);
        GlobalControl.rootPane.setOnMouseMoved(event -> {
            if (imageView != null) {
                imageView.setX(event.getX() - imageView.getFitWidth() / 2 - 70);
                imageView.setY(event.getY() - imageView.getFitHeight() / 2 - 70);
                // MapPosition mpos = Zombine.getMapPosition(event.getX(), event.getY());
                // System.err.println("mpos = " + mpos.row + " " + mpos.column);
            }
        });
    }

    public static void addNewPlant(String type, MapPosition mpos) {
        if(Constants.isServerNPlants && !Constants.GameModeSingle) {
            SocketServer.send(type + "__" + mpos.row + "__" + mpos.column + "__Plant");
        }
        switch(type) {
            case "Peashooter":
                GlobalControl.addPlants(new Peashooter(mpos.row, mpos.column));
                break;
            case "Sunflower":
                GlobalControl.addPlants(new Sunflower(mpos.row, mpos.column));
                break;
            case "PepPeaShooter":
                GlobalControl.addPlants(new PepPeaShooter(mpos.row, mpos.column));
                break;
            case "SnowPeashooter":
                GlobalControl.addPlants(new SnowPeashooter(mpos.row, mpos.column));
                break;
            case "Chomper":
                GlobalControl.addPlants(new Chomper(mpos.row, mpos.column));
                break;
            case "Squash":
                GlobalControl.addPlants(new Squash(mpos.row, mpos.column));
                break;

        }
    }

    public static void addNewZombine(String type, MapPosition mpos) {
        if(!Constants.isServerNPlants && !Constants.GameModeSingle)
            SocketClient.send(type + "__" + mpos.row + "__" + mpos.column + "__Zombine");
        switch(type) {
            case "NormalZombine":
                GlobalControl.addZombine(new NormalZombine(mpos.row, mpos.column));
                break;
            case "ConeheadZomine":
                GlobalControl.addZombine(new ConeheadZomine(mpos.row, mpos.column));
                break;
            case "FlagZombine":
                GlobalControl.addZombine(new FlagZombine(mpos.row, mpos.column));
                break;
            case "NewspaperZombine":
                GlobalControl.addZombine(new NewspaperZombine(mpos.row, mpos.column));
                break;
            case "BucketHeadZombine":
                GlobalControl.addZombine(new BucketHeadZombine(mpos.row, mpos.column));
                break;
        }
    }

    public static void initializeCardSelectedApply() {
        GlobalControl.rootPane.setOnMouseClicked(event -> {
            System.err.println("selectedPlantsType = " + selectedPlantsType);
            if(selectedPlantsType != null) {
                MapPosition mpos = Plants.getMapPosition(event.getX(), event.getY());
                playOnce(Constants.getAddNewObjectMusic(), 0.9);
                addNewPlant(selectedPlantsType, mpos);
            }

            if(selectedZombineImageView != null) {
                MapPosition mpos = Zombine.getMapPosition(event.getX(), event.getY());
                playOnce(Constants.getAddNewObjectMusic(), 0.9);
                addNewZombine(selectedZombineType, mpos);
            }

            selectedPlantsType = null;
            try {
                rootPane.getChildren().remove(selectedPlantsImageView);
                selectedPlantsImageView = null;
            } catch (Exception e) {
                System.err.println("Error: " + e);
            }

            selectedZombineType = null;
            try {
                rootPane.getChildren().remove(selectedZombineImageView);
                selectedZombineImageView = null;
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
                Constants.PlantCardWidth,
                Constants.PlantCardHeight
            );
            currentPosX += Constants.PlantCardWidth + Constants.PlantCardGap;
            System.err.println("Added card " + card.getKey() + " at " + currentPosX + " " + currentPosY);
            nowId += 1;
        }
    }

    public static void initializeZombineCardImageView() {
        int nowId = 0;
        double currentPosX = Constants.ZombineCardXPos;
        double currentPosY = Constants.ZombineCardYPos;
        for(Map.Entry<String, URL> card : Constants.ZombineCardImage) {
            System.err.println("Adding card " + card.getKey() + " at " + currentPosX + " " + currentPosY);
            zombineCards[nowId] = new ZombinesCard(
                new Image(card.getValue().toString()),
                card.getKey(),
                currentPosX,
                currentPosY,
                Constants.ZombineCardWidth,
                Constants.ZombineCardHeight
            );
            currentPosX += Constants.ZombineCardWidth + Constants.ZombineCardGap;
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

    // private static void MoveMapToDie() {
    //     TranslateTransition tt = new TranslateTransition(Duration.seconds(1), backgroundImageView);
    //     double currentX = backgroundImageView.getX();
    //     tt.setToX(0 - currentX);
    //     tt.play();
    // }

    public static void plantsWin() {
        haveResult = 0;
        lock.unlock();
        lock.lock();
        System.err.println("Plants win!");
        for(Zombine z : AllZombines) {
            z.setDie();
        }
        playOnce(Constants.getPlantVictoryMusic(), 0.9);
        moveStep.stop();
        mainBgmPlayer.stop();
    }

    public static void zombineWin() {
        haveResult = 1;

        System.err.println("Zombine win!");
        for(Plants p : AllPlants) {
            p.setDie();
        }
        playOnce(Constants.getZombineVictoryMusic(), 0.9);
        moveStep.stop();
        mainBgmPlayer.stop();
    }

    public static void initializeMoveStep() {
        moveStep = new Timeline();
        moveStep.setCycleCount(Timeline.INDEFINITE);
        moveStep.getKeyFrames().add(new KeyFrame(Duration.millis(1000 / Constants.GlobalFPS), e -> {
            if(haveResult != -1) return ;
            lock.lock();

            refreshBG();

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
                    winZombine = z;
                    zombineWin();
                    break;
                }
            }

            lock.unlock();
        }));
    }
    public static void initializeProcessMessageQueue() {
        processMessageQueue = new Timeline();
        processMessageQueue.setCycleCount(Timeline.INDEFINITE);
        processMessageQueue.getKeyFrames().add(new KeyFrame(Duration.millis(1000 / Constants.ProcessMessageFPS), e -> {
            lock.lock();
            for(String message : MessageQueue) {
                String[] args = message.split("__");
                System.err.println("Processing message: " + message);
                if(args.length == 4 && args[3].equals("Plant")) {
                    GlobalControl.addNewPlant(args[0],
                        new MapPosition(Integer.parseInt(args[1]), Integer.parseInt(args[2])));
                }
                if(args.length == 4 && args[3].equals("Zombine")) {
                    GlobalControl.addNewZombine(args[0],
                        new MapPosition(Integer.parseInt(args[1]), Integer.parseInt(args[2])));
                }
            }
            MessageQueue.clear();
            lock.unlock();
        }));
        processMessageQueue.play();
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

    public static boolean isCollision(ImageView zombine, ImageView bullet) {
        double x1 = zombine.getX();
        double y1 = zombine.getY();
        double w1 = zombine.getBoundsInParent().getWidth();
        double h1 = zombine.getBoundsInParent().getHeight();

        x1 = x1 + w1 / 2; w1 /= 2;
        // System.err.println("x1 = " + x1 + " y1 = " + y1 + " w1 = " + w1 + " h1 = " + h1);

        double x2 = bullet.getX();
        double y2 = bullet.getY();
        double w2 = bullet.getBoundsInParent().getWidth();
        double h2 = bullet.getBoundsInParent().getHeight();
        x2 = x2 + w2 / 2; w2 /= 2;

        return x1 < x2 + w2 && x1 + w1 > x2 && y1 < y2 + h2 && y1 + h1 > y2;
    }

    public static void initializeBulletsAttackListerner() {
        bulletsListerner = new Timeline();
        bulletsListerner.setCycleCount(Timeline.INDEFINITE);
        bulletsListerner.getKeyFrames().add(new KeyFrame(Duration.millis(1000 / Constants.BulletFPS), e->{
            lock.lock();
            for(Zombine z : AllZombines) {
                for(Bullets b : AllBullets) {
                    // double distance2 = Math.pow(z.getX() - b.getX(), 2) + Math.pow(z.getY() - b.getY(), 2);
                    // double distance2 = Math.pow(z.getCenterX() - b.getCenterX(), 2)
                                    //  + Math.pow(z.getCenterY() - b.getCenterY(), 2);
                    // System.err.println("distance2 = " + distance2 + " " + Constants.BulletNZombineCollisionDistance_2);
                    // if(distance2 < Constants.BulletNZombineCollisionDistance_2 && z.getMapPosition().row == b.getMapPosition().row) {
                    if(isCollision(z.getImageView(), b.getImageView())) {
                        z.applyAttack(b.getDamage());
                        playOnce(Constants.getZombineHittedMusic(), 0.8);
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

    // Music


    public static void initializeMainBGM() {
        Media sound = new Media(Constants.getMainBgmMusic().toString());
        mainBgmPlayer = new MediaPlayer(sound);
        System.err.println("Playing " + Constants.getMainBgmMusic().toString());
        mainBgmPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mainBgmPlayer.play();
    }

    public static void playOnce(URL music, double volumn) {
        Media sound = new Media(music.toString());
        MediaPlayer player = new MediaPlayer(sound);
        player.setVolume(volumn);
        player.play();
    }


    public static void initializeEverything() {
        initializeSunNBrainLabel();
        initializeMoveStep();
        initializeBackgroudImage();
        initializePlantCardsChooser();
        initializeZombineCardsChooser();
        initializeDieObjectCleanUp();
        initializeAttackingListening();
        initializeBulletsAttackListerner();
        initializePlantsCardImageView();
        initializeZombineCardImageView();
        initializeCardSelectedApply();
        initializeProcessMessageQueue();
        initializeMainBGM();
        startMoveStep();
    }

    public static ArrayList<Zombine> getZombines() {
        return AllZombines;
    }

}
