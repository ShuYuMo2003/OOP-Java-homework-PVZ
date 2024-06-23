package homework;

import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javafx.util.Duration;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.animation.KeyFrame;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;



public class GlobalControl {
    static Lock lock = new ReentrantLock();
    static ArrayList<Zombine> AllZombines = new ArrayList<>();
    static ArrayList<Plants> AllPlants = new ArrayList<>();
    static ArrayList<Bullets> AllBullets = new ArrayList<>();
    static ArrayList<Sun> AllSuns = new ArrayList<>();
    static ArrayList<Brain> AllBrains = new ArrayList<>();
    static ProgressBar progressBar = new ProgressBar(0);

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

    static int[] zombineCountOnEachRow = new int[Constants.MaxRow];


    private static ImageView backgroundImageView;
    private static ImageView cardsChooserImageView;
    private static Zombine winZombine = null;

    private static MediaPlayer mainBgmPlayer;


    private static int sunCount = 150;
    private static int brainCount = 50;
    private static Label sunLabel = new Label();
    private static Label brainLabel = new Label();

    private static void updateSunBrainLabelPosition() {
        sunLabel.setLayoutX(40);
        sunLabel.setLayoutY(76);
        brainLabel.setLayoutX(1234);
        brainLabel.setLayoutY(76);
    }

    public static void initializeSunNBrainLabel() {
        sunLabel.setFont(Font.font("Colonna MT", FontWeight.BOLD, 20));
        brainLabel.setFont(Font.font("Colonna MT", FontWeight.BOLD, 20));
        updateSunLabel();
        updateBrainLabel();
        rootPane.getChildren().addAll(sunLabel, brainLabel);
    }

    public static int getSunCount() {
        return sunCount;
    }
    public static int getBrainCount() {
        return brainCount;
    }

    public static void addSun(Sun sun) {

        AllSuns.add(sun);

    }

    public static void addBrain(Brain brain) {

        AllBrains.add(brain);

    }

    public static void setSunCount(int count) {
        try {
            sunCount = count;
            updateSunLabel();
            for(PlantsCard card : plantsCards) {
                card.checkCanSelected();
            }
        } finally {
        }
    }

    public static void modifySunCount(int delta) {
        try {
            setSunCount(sunCount + delta);
        } finally {
        }
    }

    public static void setBrainCount(int count) {
        try {
            brainCount = count;
            updateBrainLabel();
            for(ZombinesCard card : zombineCards) {
                card.checkCanSelected();
            }
        } finally {
        }
    }

    public static void modifyBrainCount(int delta) {
        try {
            setBrainCount(brainCount + delta);
        } finally {
        }
    }

    private static void updateSunLabel() {
        // Platform.runLater(() -> {
            if(!Constants.isServerNPlants && !Constants.GameModeSingle)
                sunLabel.setText("");
            else
                sunLabel.setText("" + sunCount);
            updateSunBrainLabelPosition();
        // });
    }

    private static void updateBrainLabel() {
        // Platform.runLater(() -> {
            if(Constants.isServerNPlants && !Constants.GameModeSingle)
                brainLabel.setText("");
            else
                brainLabel.setText("" + brainCount);
            updateSunBrainLabelPosition();
        // });
    }

    private static void refreshBG() {
        try {
            rootPane.getChildren().remove(backgroundImageView);
        } catch (Exception e) {
            System.err.println("Error: " + e);
        }
        Image backgroundImage = Constants.getCachedImage(Constants.getBackgroudImage().toString());
        backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitHeight(Constants.WindowHeight);
        backgroundImageView.setPreserveRatio(true);
        backgroundImageView.setX(Constants.PlayingStageMapXPos);
        GlobalControl.rootPane.getChildren().add(0, backgroundImageView);
    }

    private static void initializeBackgroudImage() {
        Image backgroundImage = Constants.getCachedImage(Constants.getBackgroudImage().toString());
        backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitHeight(Constants.WindowHeight);
        backgroundImageView.setPreserveRatio(true);
        backgroundImageView.setX(Constants.PlayingStageMapXPos);
        GlobalControl.rootPane.getChildren().add(backgroundImageView);
    }

    private static void initializePlantCardsChooser() {
        Image cardsChooserImage = Constants.getCachedImage(Constants.getPlantsChooserImage().toString());
        cardsChooserImageView = new ImageView(cardsChooserImage);
        cardsChooserImageView.setFitWidth(Constants.WindowWidth * 0.45);
        cardsChooserImageView.setPreserveRatio(true);
        cardsChooserImageView.setX(10);
        cardsChooserImageView.setY(10);
        GlobalControl.rootPane.getChildren().add(cardsChooserImageView);
    }

    private static void initializeZombineCardsChooser() {
        Image cardsChooserImage = Constants.getCachedImage(Constants.getZombineChooserImage().toString());
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
                modifySunCount(-Constants.PlantsSunCost.get(selectedPlantsType));
            }

            if(selectedZombineImageView != null) {
                MapPosition mpos = Zombine.getMapPosition(event.getX(), event.getY());
                if(mpos.column >= Constants.MaxColumn - 2) {
                    playOnce(Constants.getAddNewObjectMusic(), 0.9);
                    addNewZombine(selectedZombineType, mpos);
                    modifyBrainCount(-Constants.ZombineBrainCost.get(selectedZombineType));
                }
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
                Constants.getCachedImage(card.getValue().toString()),
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
                Constants.getCachedImage(card.getValue().toString()),
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

    public static void addBullets(Bullets b) {
        AllBullets.add(b);
    }

    public static void addZombine(Zombine z) {
        AllZombines.add(z);
    }

    public static void addPlants(Plants p) {
        AllPlants.add(p);
    }


    GlobalControl() { }

    // private static void MoveMapToDie() {
    //     TranslateTransition tt = new TranslateTransition(Duration.seconds(1), backgroundImageView);
    //     double currentX = backgroundImageView.getX();
    //     tt.setToX(0 - currentX);
    //     tt.play();
    // }

    public static void plantsWin() {
        if(haveResult != -1) return;
        haveResult = 0;

        System.err.println("Plants win!");
        for(Zombine z : AllZombines) {
            z.setDie();
        }
        ImageView imageview = new ImageView(Constants.getCachedImage(Constants.getPlantWinImage().toString()));
        imageview.setFitHeight(Constants.WindowHeight * 0.6);
        imageview.setPreserveRatio(true);
        rootPane.getChildren().add(imageview);

        imageview.setTranslateY(-Constants.WindowHeight);
        TranslateTransition tt = new TranslateTransition(Duration.seconds(2), imageview);
        tt.setToY(0); // 移动到中心位置
        tt.play();


        playOnce(Constants.getPlantVictoryMusic(), 0.9);
        moveStep.stop();
        mainBgmPlayer.stop();
    }

    public static void zombineWin() {
        if(haveResult != -1) return;
        haveResult = 1;

        System.err.println("Zombine win!");
        for(Plants p : AllPlants) {
            p.setDie();
        }
        ImageView imageview = new ImageView(Constants.getCachedImage(Constants.getZombineWinImage().toString()));
        imageview.setFitHeight(Constants.WindowHeight * 0.6);
        imageview.setPreserveRatio(true);
        rootPane.getChildren().add(imageview);

        imageview.setTranslateY(-Constants.WindowHeight);
        TranslateTransition tt = new TranslateTransition(Duration.seconds(2), imageview);
        tt.setToY(0); // 移动到中心位置
        tt.play();

        playOnce(Constants.getZombineVictoryMusic(), 0.9);
        moveStep.stop();
        mainBgmPlayer.stop();
    }

    public static void initializeMoveStep() {
        moveStep = new Timeline();
        moveStep.setCycleCount(Timeline.INDEFINITE);
        moveStep.getKeyFrames().add(new KeyFrame(Duration.millis(1000 / Constants.GlobalFPS), e -> {
            if(haveResult != -1) return ;

            Platform.runLater(() -> {
                for(int i = 0; i < Constants.MaxRow; i++) zombineCountOnEachRow[i] = 0;
                refreshBG();

                for(Zombine z : AllZombines) {
                    if(z.canMove())
                        z.nextStep();
                    zombineCountOnEachRow[z.getRow()] += 1;
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
            });
        }));
    }
    public static void initializeProcessMessageQueue() {
        processMessageQueue = new Timeline();
        processMessageQueue.setCycleCount(Timeline.INDEFINITE);
        processMessageQueue.getKeyFrames().add(new KeyFrame(Duration.millis(1000 / Constants.ProcessMessageFPS), e -> {

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

        }));
        processMessageQueue.play();
    }
    static int verboxPutput = 0;
    public static void initializeDieObjectCleanUp(){
        dieObjectCleanUper = new Timeline();
        dieObjectCleanUper.setCycleCount(Timeline.INDEFINITE);
        dieObjectCleanUper.getKeyFrames().add(new KeyFrame(Duration.millis(1000 / Constants.cleanUpFPS), e -> {
            Platform.runLater(() -> {
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
                for(int i = 0; i < AllSuns.size(); i++) {
                    if(AllSuns.get(i).deprecated) {
                        AllSuns.remove(i);
                    }
                }
                for(int i = 0; i < AllBrains.size(); i++) {
                    if(AllBrains.get(i).deprecated) {
                        AllBrains.remove(i);
                    }
                }
                verboxPutput += 1;
                if(verboxPutput % 100 == 0) {
                    System.err.println("AllZombines.size() = " + AllZombines.size());
                    System.err.println("AllPlants.size() = " + AllPlants.size());
                    System.err.println("AllBullets.size() = " + AllBullets.size());
                    System.err.println("AllSuns.size() = " + AllSuns.size());
                    System.err.println("AllBrains.size() = " + AllBrains.size());
                }

            });
        }));
        dieObjectCleanUper.play();
    }

    public static void initializeAttackingListening() {
        attackListerner = new Timeline();
        attackListerner.setCycleCount(Timeline.INDEFINITE);
        attackListerner.getKeyFrames().add(new KeyFrame(Duration.millis(1000 / Constants.ZombineAttackingFPS), e -> {
            Platform.runLater(()->{
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

            });
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
            Platform.runLater(()->{
                // ArrayList<Zombine>[] zbsByRow = new ArrayList[Constants.MaxRow];
                // for(int i = 0; i < Constants.MaxRow; i++) {
                //     zbsByRow[i] = new ArrayList<Zombine>();
                // }

                // ArrayList<Bullets>[] bsByRow = new ArrayList[Constants.MaxRow];
                // for(int i = 0; i < Constants.MaxRow; i++) {
                //     bsByRow[i] = new ArrayList<Bullets>();
                // }

                // for(Zombine z : AllZombines) {
                //     zbsByRow[z.getMapPosition().row].add(z);
                // }

                // for(Bullets b : AllBullets) {
                //     bsByRow[b.getMapPosition().row].add(b);
                // }

                // for(int i = 0; i < Constants.MaxRow; i++) {
                    for(int aa = 0; aa < AllZombines.size(); aa++) {
                        for(int bb = 0; bb < AllBullets.size(); bb++) {
                            Zombine z = AllZombines.get(aa);
                            Bullets b = AllBullets.get(bb);
                            if(isCollision(z.getImageView(), b.getImageView())) {
                                z.applyAttack(b.getDamage());
                                playOnce(Constants.getZombineHittedMusic(), 0.8);
                                b.boom();
                            }
                        }
                    }
                // }

            });
        }));
        bulletsListerner.play();
    }

    public static void startMoveStep() {
        moveStep.play();
    }

    // Music


    public static void initializeMainBGM() {
        Media sound = Constants.getMainBgmMusic();
        mainBgmPlayer = new MediaPlayer(sound);
        System.err.println("Playing " + Constants.getMainBgmMusic().toString());
        mainBgmPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mainBgmPlayer.play();
    }

    static MediaPlayer player;
    public static void playOnce(Media sound, double volumn) {
        player = new MediaPlayer(sound);
        player.setVolume(volumn);
        player.play();
    }
    static private void initializeTimeCountdown() {
        Label timeLabel = new Label("! START !");
        timeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                for (int i = 0; i <= 1000; i++) {
                    // Simulate work
                    Thread.sleep(1000 * Constants.LongestTimeForZombine / 1000);
                    // Update progress
                    updateProgress(i, 1000);
                    final String msg = "Rest Time for Zombine: " + String.format("%.2f", Constants.LongestTimeForZombine * (1 - (double)i / 1000))+ " Seconds";
                    Platform.runLater(()->{ timeLabel.setText(msg); });
                }
                return null;
            }
        };
        progressBar.progressProperty().bind(task.progressProperty());

        progressBar.setPrefWidth(Constants.WindowWidth);
        progressBar.setPrefHeight(20);

        new Thread(task).start();

        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(progressBar, timeLabel);

        VBox root = new VBox(stackPane);
        root.setAlignment(Pos.CENTER);
        root.setLayoutY(Constants.WindowHeight - 20);

        rootPane.getChildren().add(root);
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
        initializeZombineCardImageView();
        initializeCardSelectedApply();
        initializeProcessMessageQueue();
        initializeMainBGM();
        initializeSunNBrainLabel();
        Timeline tt = new Timeline();
        tt.getKeyFrames().add(new KeyFrame(Duration.seconds(Constants.LongestTimeForZombine), e->{
            plantsWin();
        }));
        tt.setCycleCount(1);
        initializeTimeCountdown();
        tt.play();
        startMoveStep();
    }

    public static ArrayList<Zombine> getZombines() {
        return AllZombines;
    }

}
