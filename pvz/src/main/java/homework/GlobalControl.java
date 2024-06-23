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
    // Static Lock for synchronization
static Lock lock = new ReentrantLock();

// Collections for game entities
static ArrayList<Zombine> AllZombines = new ArrayList<>();
static ArrayList<Plants> AllPlants = new ArrayList<>();
static ArrayList<Bullets> AllBullets = new ArrayList<>();
static ArrayList<Sun> AllSuns = new ArrayList<>();
static ArrayList<Brain> AllBrains = new ArrayList<>();

// Progress bar for visual feedback
static ProgressBar progressBar = new ProgressBar(0);

// Message queue for handling game messages
static ArrayList<String> MessageQueue = new ArrayList<>();

// Positions for entities on the map
static ArrayList<MapPosition> zombinesPos = new ArrayList<>();
static ArrayList<MapPosition> plantsPos = new ArrayList<>();

// Selected plant and zombine type for player interaction
static String selectedPlantsType = null;
static ImageView selectedPlantsImageView = null;
static String selectedZombineType = null;
static ImageView selectedZombineImageView = null;

// Result status
static int haveResult = -1;

// Arrays to manage cards for plants and zombines
static PlantsCard[] plantsCards = new PlantsCard[Constants.PlantsCardCount];
static ZombinesCard[] zombineCards = new ZombinesCard[Constants.ZombineCardCount];

// Array to track zombine count on each row
static int[] zombineCountOnEachRow = new int[Constants.MaxRow];

// ImageViews for background and card chooser
private static ImageView backgroundImageView;
private static ImageView cardsChooserImageView;

// Winning zombine instance
private static Zombine winZombine = null;

// Media player for background music
private static MediaPlayer mainBgmPlayer;

// Counts for in-game resources
private static int sunCount = 250;
private static int brainCount = 50;

// Labels to display resource counts
private static Label sunLabel = new Label();
private static Label brainLabel = new Label();

// Method to update positions of sun and brain labels
private static void updateSunBrainLabelPosition() {
    sunLabel.setLayoutX(40);
    sunLabel.setLayoutY(76);
    brainLabel.setLayoutX(1234);
    brainLabel.setLayoutY(76);
}

// Initialization method for sun and brain labels
public static void initializeSunNBrainLabel() {
    sunLabel.setFont(Font.font("Colonna MT", FontWeight.BOLD, 20));
    brainLabel.setFont(Font.font("Colonna MT", FontWeight.BOLD, 20));
    updateSunLabel();
    updateBrainLabel();
    rootPane.getChildren().addAll(sunLabel, brainLabel); // Assuming rootPane is defined elsewhere
}

// Getter methods for sun and brain counts
public static int getSunCount() {
    return sunCount;
}
public static int getBrainCount() {
    return brainCount;
}

// Methods to add suns and brains to the game
public static void addSun(Sun sun) {
    AllSuns.add(sun);
}
public static void addBrain(Brain brain) {
    AllBrains.add(brain);
}

// Setter methods for sun and brain counts with synchronization
public static void setSunCount(int count) {
    try {
        sunCount = count;
        updateSunLabel();
        for (PlantsCard card : plantsCards) {
            card.checkCanSelected(); // Method to update UI for plant cards
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
        for (ZombinesCard card : zombineCards) {
            card.checkCanSelected(); // Method to update UI for zombine cards
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

// Methods to update text in sun and brain labels
private static void updateSunLabel() {
    // Update sun label text and position
    if (!Constants.isServerNPlants && !Constants.GameModeSingle)
        sunLabel.setText("");
    else
        sunLabel.setText("" + sunCount);
    updateSunBrainLabelPosition();
}

private static void updateBrainLabel() {
    // Update brain label text and position
    if (Constants.isServerNPlants && !Constants.GameModeSingle)
        brainLabel.setText("");
    else
        brainLabel.setText("" + brainCount);
    updateSunBrainLabelPosition();
}
// Method to refresh the background image displayed on the game screen
private static void refreshBG() {
    try {
        rootPane.getChildren().remove(backgroundImageView); // Remove old background image
    } catch (Exception e) {
        System.err.println("Error: " + e); // Log any errors encountered during removal
    }
    
    // Load and set the new background image
    Image backgroundImage = Constants.getCachedImage(Constants.getBackgroudImage().toString());
    backgroundImageView = new ImageView(backgroundImage);
    backgroundImageView.setFitHeight(Constants.WindowHeight);
    backgroundImageView.setPreserveRatio(true);
    backgroundImageView.setX(Constants.PlayingStageMapXPos);
    GlobalControl.rootPane.getChildren().add(0, backgroundImageView); // Add to rootPane at index 0 (bottom layer)
}

// Method to initialize the background image on the game screen
private static void initializeBackgroudImage() {
    // Load and set the initial background image
    Image backgroundImage = Constants.getCachedImage(Constants.getBackgroudImage().toString());
    backgroundImageView = new ImageView(backgroundImage);
    backgroundImageView.setFitHeight(Constants.WindowHeight);
    backgroundImageView.setPreserveRatio(true);
    backgroundImageView.setX(Constants.PlayingStageMapXPos);
    GlobalControl.rootPane.getChildren().add(backgroundImageView); // Add to rootPane
}

// Method to initialize the plant cards chooser image
private static void initializePlantCardsChooser() {
    // Load and set the plant cards chooser image
    Image cardsChooserImage = Constants.getCachedImage(Constants.getPlantsChooserImage().toString());
    cardsChooserImageView = new ImageView(cardsChooserImage);
    cardsChooserImageView.setFitWidth(Constants.WindowWidth * 0.45);
    cardsChooserImageView.setPreserveRatio(true);
    cardsChooserImageView.setX(10);
    cardsChooserImageView.setY(10);
    GlobalControl.rootPane.getChildren().add(cardsChooserImageView); // Add to rootPane
}

// Method to initialize the zombine cards chooser image
private static void initializeZombineCardsChooser() {
    // Load and set the zombine cards chooser image
    Image cardsChooserImage = Constants.getCachedImage(Constants.getZombineChooserImage().toString());
    cardsChooserImageView = new ImageView(cardsChooserImage);
    cardsChooserImageView.setFitWidth(Constants.WindowWidth * 0.45);
    cardsChooserImageView.setPreserveRatio(true);
    cardsChooserImageView.setX(Constants.WindowWidth * 0.55 - 10);
    cardsChooserImageView.setY(10);
    GlobalControl.rootPane.getChildren().add(cardsChooserImageView); // Add to rootPane
}

// Timelines for game animations and processes
static Timeline moveStep;
static Timeline attackListerner;
static Timeline dieObjectCleanUper;
static Timeline bulletsListerner;
static Timeline processMessageQueue;
static Pane rootPane = new Pane(); // Root pane to hold UI elements

// Method to set the selected plant type and its draggable image
public static void setSelectedPlants(String type, ImageView imageView) {
    // Remove previously selected plant image if exists
    if (selectedPlantsImageView != null) {
        try {
            rootPane.getChildren().remove(selectedPlantsImageView);
        } catch (Exception e) {
            System.err.println("Error: " + e);
        }
    }
    
    // Set new selected plant type and image
    selectedPlantsType = type;
    selectedPlantsImageView = imageView;

    // Add selected plant image to rootPane and enable drag functionality
    GlobalControl.rootPane.getChildren().add(selectedPlantsImageView);
    GlobalControl.rootPane.setOnMouseMoved(event -> {
        double xOffset = 0;
        double yOffset = 0;
        if (selectedPlantsType.equals("Squash")) {
            xOffset = 0;
            yOffset = -100;
        }
        if (imageView != null) {
            imageView.setX(event.getX() - imageView.getFitWidth() / 2 - 50 + xOffset);
            imageView.setY(event.getY() - imageView.getFitHeight() / 2 - 50 + yOffset);
        }
    });
}

// Method to set the selected zombine type and its draggable image
public static void setSelectedZombine(String type, ImageView imageView) {
    // Remove previously selected zombine image if exists
    if (selectedZombineImageView != null) {
        try {
            rootPane.getChildren().remove(selectedZombineImageView);
        } catch (Exception e) {
            System.err.println("Error: " + e);
        }
    }
    
    // Set new selected zombine type and image
    selectedZombineType = type;
    selectedZombineImageView = imageView;

    // Add selected zombine image to rootPane and enable drag functionality
    GlobalControl.rootPane.getChildren().add(selectedZombineImageView);
    GlobalControl.rootPane.setOnMouseMoved(event -> {
        if (imageView != null) {
            imageView.setX(event.getX() - imageView.getFitWidth() / 2 - 70);
            imageView.setY(event.getY() - imageView.getFitHeight() / 2 - 70);
        }
    });
}

// Method to add a new plant based on type and map position
public static void addNewPlant(String type, MapPosition mpos) {
    // Send plant creation message in networked mode
    if (Constants.isServerNPlants && !Constants.GameModeSingle) {
        SocketServer.send(type + "__" + mpos.row + "__" + mpos.column + "__Plant");
    }
    
    // Create and add plant instance based on type
    switch (type) {
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

// Method to add a new zombine based on type and map position
public static void addNewZombine(String type, MapPosition mpos) {
    // Send zombine creation message in networked mode
    if (!Constants.isServerNPlants && !Constants.GameModeSingle) {
        SocketClient.send(type + "__" + mpos.row + "__" + mpos.column + "__Zombine");
    }
    
    // Create and add zombine instance based on type
    switch (type) {
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

    // Method to initialize card selection and application on mouse click
public static void initializeCardSelectedApply() {
    GlobalControl.rootPane.setOnMouseClicked(event -> {
        System.err.println("selectedPlantsType = " + selectedPlantsType);
        
        // Handling plant selection and placement
        if (selectedPlantsType != null) {
            MapPosition mpos = Plants.getMapPosition(event.getX(), event.getY());
            playOnce(Constants.getAddNewObjectMusic(), 0.9); // Play sound effect
            addNewPlant(selectedPlantsType, mpos); // Add selected plant to the game
            modifySunCount(-Constants.PlantsSunCost.get(selectedPlantsType)); // Deduct sun cost
        }

        // Handling zombine selection and placement
        if (selectedZombineImageView != null) {
            MapPosition mpos = Zombine.getMapPosition(event.getX(), event.getY());
            if (mpos.column >= Constants.MaxColumn - 2) { // Check if within valid column for zombines
                playOnce(Constants.getAddNewObjectMusic(), 0.9); // Play sound effect
                addNewZombine(selectedZombineType, mpos); // Add selected zombine to the game
                modifyBrainCount(-Constants.ZombineBrainCost.get(selectedZombineType)); // Deduct brain cost
            }
        }

        // Clear selected plant and zombine types, remove their respective image views
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

        rootPane.setOnMouseMoved(null); // Disable mouse move listener
    });
}

// Method to initialize plant cards' ImageViews based on Constants data
public static void initializePlantsCardImageView() {
    int nowId = 0;
    double currentPosX = Constants.PlantsCardXPos;
    double currentPosY = Constants.PlantsCardYPos;

    // Loop through each plant card and create corresponding PlantsCard objects
    for (Map.Entry<String, URL> card : Constants.PlantsCardImage) {
        plantsCards[nowId] = new PlantsCard(
            Constants.getCachedImage(card.getValue().toString()), // Load card image
            card.getKey(), // Plant type
            currentPosX,
            currentPosY,
            Constants.PlantCardWidth,
            Constants.PlantCardHeight
        );
        currentPosX += Constants.PlantCardWidth + Constants.PlantCardGap; // Adjust X position for next card
        System.err.println("Added card " + card.getKey() + " at " + currentPosX + " " + currentPosY);
        nowId += 1;
    }
}

// Method to initialize zombine cards' ImageViews based on Constants data
public static void initializeZombineCardImageView() {
    int nowId = 0;
    double currentPosX = Constants.ZombineCardXPos;
    double currentPosY = Constants.ZombineCardYPos;

    // Loop through each zombine card and create corresponding ZombinesCard objects
    for (Map.Entry<String, URL> card : Constants.ZombineCardImage) {
        System.err.println("Adding card " + card.getKey() + " at " + currentPosX + " " + currentPosY);
        zombineCards[nowId] = new ZombinesCard(
            Constants.getCachedImage(card.getValue().toString()), // Load card image
            card.getKey(), // Zombine type
            currentPosX,
            currentPosY,
            Constants.ZombineCardWidth,
            Constants.ZombineCardHeight
        );
        currentPosX += Constants.ZombineCardWidth + Constants.ZombineCardGap; // Adjust X position for next card
        System.err.println("Added card " + card.getKey() + " at " + currentPosX + " " + currentPosY);
        nowId += 1;
    }
}

// Method to add bullets to the game
public static void addBullets(Bullets b) {
    AllBullets.add(b);
}

// Method to add zombines to the game
public static void addZombine(Zombine z) {
    AllZombines.add(z);
}

// Method to add plants to the game
public static void addPlants(Plants p) {
    AllPlants.add(p);
}

// Constructor for GlobalControl class
GlobalControl() { }

// Method to handle game over when plants win
public static void plantsWin() {
    if (haveResult != -1) return; // Check if game result already determined
    haveResult = 0; // Set result to plants win

    System.err.println("Plants win!");
    
    // Set all zombines to die and display victory image
    for (Zombine z : AllZombines) {
        z.setDie();
    }
    ImageView imageview = new ImageView(Constants.getCachedImage(Constants.getPlantWinImage().toString()));
    imageview.setFitHeight(Constants.WindowHeight * 0.6);
    imageview.setPreserveRatio(true);
    rootPane.getChildren().add(imageview);

    imageview.setTranslateY(-Constants.WindowHeight);
    TranslateTransition tt = new TranslateTransition(Duration.seconds(2), imageview);
    tt.setToY(0); // Move to center position
    tt.play();

    playOnce(Constants.getPlantVictoryMusic(), 0.9); // Play victory music
    moveStep.stop(); // Stop game animation timeline
    mainBgmPlayer.stop(); // Stop main background music
}

// Method to handle game over when zombines win
public static void zombineWin() {
    if (haveResult != -1) return; // Check if game result already determined
    haveResult = 1; // Set result to zombines win

    System.err.println("Zombine win!");
    
    // Set all plants to die and display defeat image
    for (Plants p : AllPlants) {
        p.setDie();
    }
    ImageView imageview = new ImageView(Constants.getCachedImage(Constants.getZombineWinImage().toString()));
    imageview.setFitHeight(Constants.WindowHeight * 0.6);
    imageview.setPreserveRatio(true);
    rootPane.getChildren().add(imageview);

    imageview.setTranslateY(-Constants.WindowHeight);
    TranslateTransition tt = new TranslateTransition(Duration.seconds(2), imageview);
    tt.setToY(0); // Move to center position
    tt.play();

    playOnce(Constants.getZombineVictoryMusic(), 0.9); // Play defeat music
    moveStep.stop(); // Stop game animation timeline
    mainBgmPlayer.stop(); // Stop main background music
}

// Method to initialize the game's main animation timeline
public static void initializeMoveStep() {
    moveStep = new Timeline();
    moveStep.setCycleCount(Timeline.INDEFINITE);
    moveStep.getKeyFrames().add(new KeyFrame(Duration.millis(1000 / Constants.GlobalFPS), e -> {
        if (haveResult != -1) return; // Check if game result already determined

        Platform.runLater(() -> {
            // Reset zombine count on each row and refresh background
            for (int i = 0; i < Constants.MaxRow; i++) zombineCountOnEachRow[i] = 0;
            refreshBG();

            // Move each zombine, plant, and bullet to their next step
            for (Zombine z : AllZombines) {
                if (z.canMove())
                    z.nextStep();
                zombineCountOnEachRow[z.getRow()] += 1; // Update zombine count on row
            }
            for (Plants p : AllPlants) {
                p.nextStep();
            }
            for (Bullets b : AllBullets) {
                b.nextStep();
            }
            
            // Check if zombine has reached the end of the map (lose condition)
            for (Zombine z : AllZombines) {
                if (z.getX() < 0) {
                    winZombine = z;
                    zombineWin();
                    break;
                }
            }
        });
    }));
}

// Method to initialize the process message queue timeline for network communication
public static void initializeProcessMessageQueue() {
    processMessageQueue = new Timeline();
    processMessageQueue.setCycleCount(Timeline.INDEFINITE);
    processMessageQueue.getKeyFrames().add(new KeyFrame(Duration.millis(1000 / Constants.ProcessMessageFPS), e -> {
        // Process each message in the message queue
        for (String message : MessageQueue) {
            String[] args = message.split("__");
            System.err.println("Processing message: " + message);

            // Add new plant or zombine based on received message
            if (args.length == 4 && args[3].equals("Plant")) {
                GlobalControl.addNewPlant(args[0], new MapPosition(Integer.parseInt(args[1]), Integer.parseInt(args[2])));
            }
            if (args.length == 4 && args[3].equals("Zombine")) {
                GlobalControl.addNewZombine(args[0], new MapPosition(Integer.parseInt(args[1]), Integer.parseInt(args[2])));
            }
        }
        MessageQueue.clear(); // Clear processed messages from the queue
    }));
    processMessageQueue.play(); // Start the process message queue timeline
}
static int verboxPutput = 0;

// Method to initialize the clean-up process for dead objects.
public static void initializeDieObjectCleanUp() {
    // Create a timeline for the cleanup process, running indefinitely.
    dieObjectCleanUper = new Timeline();
    dieObjectCleanUper.setCycleCount(Timeline.INDEFINITE);
    
    // Add a keyframe to the timeline, running every frame based on the cleanUpFPS constant.
    dieObjectCleanUper.getKeyFrames().add(new KeyFrame(Duration.millis(1000 / Constants.cleanUpFPS), e -> {
        // Run the cleanup process on the JavaFX application thread.
        Platform.runLater(() -> {
            // Iterate over all zombies and remove deprecated ones.
            for (int i = 0; i < AllZombines.size(); i++) {
                if (AllZombines.get(i).getDeprecated()) {
                    AllZombines.get(i).removeImageView();
                    AllZombines.remove(i);
                }
            }

            // Iterate over all plants and remove dead ones.
            for (int i = 0; i < AllPlants.size(); i++) {
                if (AllPlants.get(i).isDie()) {
                    AllPlants.get(i).removeImageView();
                    AllPlants.remove(i);
                }
            }

            // Iterate over all bullets and remove exploded ones.
            for (int i = 0; i < AllBullets.size(); i++) {
                if (AllBullets.get(i).isBoomed()) {
                    AllBullets.get(i).removeImageView();
                    AllBullets.remove(i);
                }
            }

            // Remove deprecated suns.
            for (int i = 0; i < AllSuns.size(); i++) {
                if (AllSuns.get(i).deprecated) {
                    AllSuns.remove(i);
                }
            }

            // Remove deprecated brains.
            for (int i = 0; i < AllBrains.size(); i++) {
                if (AllBrains.get(i).deprecated) {
                    AllBrains.remove(i);
                }
            }

            // Increment the verbose output counter and print current sizes of collections every 100 iterations.
            verboxPutput += 1;
            if (verboxPutput % 100 == 0) {
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

// Method to initialize the attack listener.
public static void initializeAttackingListening() {
    // Create a timeline for the attack listener, running indefinitely.
    attackListerner = new Timeline();
    attackListerner.setCycleCount(Timeline.INDEFINITE);
    
    // Add a keyframe to the timeline, running every frame based on the ZombineAttackingFPS constant.
    attackListerner.getKeyFrames().add(new KeyFrame(Duration.millis(1000 / Constants.ZombineAttackingFPS), e -> {
        // Run the attack logic on the JavaFX application thread.
        Platform.runLater(() -> {
            zombinesPos.clear(); // Clear the list of zombie positions.
            for (Zombine z : AllZombines) {
                zombinesPos.add(z.getMapPosition()); // Add each zombie's position to the list.
            }

            plantsPos.clear(); // Clear the list of plant positions.
            for (Plants p : AllPlants) {
                plantsPos.add(p.getMapPosition()); // Add each plant's position to the list.
            }

            // Initialize an array to track if an attack happened for each zombie.
            boolean[] attackingHappend = new boolean[AllZombines.size()];
            // Check for attacks by comparing positions of zombies and plants.
            for (int pid = 0; pid < plantsPos.size(); pid++) {
                for (int zid = 0; zid < zombinesPos.size(); zid++) {
                    if (zombinesPos.get(zid).equals(plantsPos.get(pid))) {
                        AllZombines.get(zid).setAttack(true);
                        attackingHappend[zid] = true;
                        AllPlants.get(pid).getDamage(AllZombines.get(zid).getDamage());
                    }
                }
            }

            // If a zombie is not attacking, set its attack state to false.
            for (int zid = 0; zid < zombinesPos.size(); zid++) {
                if (!attackingHappend[zid]) {
                    AllZombines.get(zid).setAttack(false);
                }
            }

        });
    }));
    attackListerner.play();
}

// Method to check for collisions between a zombie and a bullet.
public static boolean isCollision(ImageView zombine, ImageView bullet) {
    double x1 = zombine.getX();
    double y1 = zombine.getY();
    double w1 = zombine.getBoundsInParent().getWidth();
    double h1 = zombine.getBoundsInParent().getHeight();

    x1 = x1 + w1 / 2; w1 /= 2;

    double x2 = bullet.getX();
    double y2 = bullet.getY();
    double w2 = bullet.getBoundsInParent().getWidth();
    double h2 = bullet.getBoundsInParent().getHeight();
    x2 = x2 + w2 / 2; w2 /= 2;

    // Return true if the bounding boxes of the zombie and bullet overlap.
    return x1 < x2 + w2 && x1 + w1 > x2 && y1 < y2 + h2 && y1 + h1 > y2;
}

// Method to initialize the bullets attack listener.
public static void initializeBulletsAttackListerner() {
    // Create a timeline for the bullets attack listener, running indefinitely.
    bulletsListerner = new Timeline();
    bulletsListerner.setCycleCount(Timeline.INDEFINITE);

    // Add a keyframe to the timeline, running every frame based on the BulletFPS constant.
    bulletsListerner.getKeyFrames().add(new KeyFrame(Duration.millis(1000 / Constants.BulletFPS), e -> {
        // Run the attack logic on the JavaFX application thread.
        Platform.runLater(() -> {
            for (int aa = 0; aa < AllZombines.size(); aa++) {
                for (int bb = 0; bb < AllBullets.size(); bb++) {
                    Zombine z = AllZombines.get(aa);
                    Bullets b = AllBullets.get(bb);
                    // Check if the zombie and bullet collide.
                    if (isCollision(z.getImageView(), b.getImageView())) {
                        z.applyAttack(b.getDamage()); // Apply damage to the zombie.
                        playOnce(Constants.getZombineHittedMusic(), 0.8); // Play hit sound.
                        b.boom(); // Make the bullet explode.
                    }
                }
            }

        });
    }));
    bulletsListerner.play();
}

// Method to start the move step timeline.
public static void startMoveStep() {
    moveStep.play();
}

// Music-related methods

// Method to initialize the main background music.
public static void initializeMainBGM() {
    Media sound = Constants.getMainBgmMusic();
    mainBgmPlayer = new MediaPlayer(sound);
    System.err.println("Playing " + Constants.getMainBgmMusic().toString());
    mainBgmPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    mainBgmPlayer.play();
}

static MediaPlayer player;
// Method to play a sound effect once.
public static void playOnce(Media sound, double volumn) {
    player = new MediaPlayer(sound);
    player.setVolume(volumn);
    player.play();
}

// Method to initialize the countdown timer.
static private void initializeTimeCountdown() {
    Label timeLabel = new Label("! START !");
    timeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));
    Task<Void> task = new Task<Void>() {
        @Override
        protected Void call() throws Exception {
            for (int i = 0; i <= 1000; i++) {
                // Simulate work by sleeping.
                Thread.sleep(1000 * Constants.LongestTimeForZombine / 1000);
                // Update progress.
                updateProgress(i, 1000);
                final String msg = "Rest Time for Zombine: " + String.format("%.2f", Constants.LongestTimeForZombine * (1 - (double) i / 1000)) + " Seconds";
                Platform.runLater(() -> {
                    timeLabel.setText(msg);
                });
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

    // Method to initialize all components and start the game.
public static void initializeEverything() {
    // Initialize the step movement for the game.
    initializeMoveStep();

    // Initialize the background image of the game.
    initializeBackgroudImage();

    // Initialize the plant cards chooser interface.
    initializePlantCardsChooser();

    // Initialize the zombie cards chooser interface.
    initializeZombineCardsChooser();

    // Initialize the cleanup process for dead objects.
    initializeDieObjectCleanUp();

    // Initialize the attack listener for handling attacks between zombies and plants.
    initializeAttackingListening();

    // Initialize the bullets attack listener for handling collisions between bullets and zombies.
    initializeBulletsAttackListerner();

    // Initialize the image views for plant cards.
    initializePlantsCardImageView();

    // Initialize the image views for zombie cards.
    initializeZombineCardImageView();

    // Initialize the process for applying selected cards.
    initializeCardSelectedApply();

    // Initialize the process message queue for handling game messages.
    initializeProcessMessageQueue();

    // Initialize the main background music.
    initializeMainBGM();

    // Initialize the labels for sun and brain counts.
    initializeSunNBrainLabel();

    // Create a timeline for ending the game after the longest time for zombies has passed.
    Timeline tt = new Timeline();
    tt.getKeyFrames().add(new KeyFrame(Duration.seconds(Constants.LongestTimeForZombine), e -> {
        plantsWin(); // Trigger the plants win scenario.
    }));
    tt.setCycleCount(1); // Set the timeline to run only once.

    // Initialize the countdown timer.
    initializeTimeCountdown();

    // Play the timeline.
    tt.play();

    // Start the movement step.
    startMoveStep();
}

// Method to get the list of all zombies.
public static ArrayList<Zombine> getZombines() {
    return AllZombines;
}}
