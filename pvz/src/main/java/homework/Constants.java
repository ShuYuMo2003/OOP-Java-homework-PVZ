
package homework;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.AbstractMap.SimpleEntry;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.media.Media;

public class Constants {
    // Game Mode Flags
    final public static boolean GameModeSingle =true;
    final public static boolean Debug = true;

    // Network Configuration
    final public static int BroadcastSocketPort = 10086;
    final public static int SocketPort = 10088;
    final public static int SendBroadcastIntervalMillis = 500;
    final public static String BroadcastMessagePrefix = "PVZ0912";
    public static String ServerIP = null;
    public static boolean isServerNPlants = false;

    // Player Information
    public static class Player {
        private final String username;
        private final String ip;
        private final double lastUpdate;

        Player(String username, String ip, double lastUpdate) {
            this.username = username;
            this.lastUpdate = lastUpdate;
            this.ip = ip;
        }

        public String getUsername() {
            return username;
        }

        public String getIp() {
            return ip;
        }

        public double getLastUpdate() {
            return lastUpdate;
        }
    }

    final public static HashMap<String, Player> OnlineUser = new HashMap<>();
    public static ObservableList<Player> player = FXCollections.observableArrayList();

    static public void addOrUpdateUser(String username, String ip) {
        OnlineUser.put(ip, new Player(username, ip, System.currentTimeMillis()));
        for (String key : OnlineUser.keySet()) {
            if (System.currentTimeMillis() - OnlineUser.get(key).lastUpdate > 3000) {
                OnlineUser.remove(key);
            }
        }
        player.clear();
        for (String key : OnlineUser.keySet()) {
            player.add(OnlineUser.get(key));
        }
    }

    public static String username = "default";

    public static void setUsername(String name) {
        System.err.println("Set username to " + name);
        username = name;
    }

    // Window Configuration
    final public static int WindowWidth = 1300;
    final public static int WindowHeight = 640;
    final public static String MainStageTitle = "Plants Vs. Zombies";

    // Game Timing
    final public static int LongestTimeForZombine = 3 * 60; // 3 minutes
    final public static double PlayingStageMapXPos = -180d;
    final public static double cleanUpFPS = 5;
    final public static double GlobalFPS = 20;
    final public static double ProcessMessageFPS = 20;

    // Sun
    final public static double SunFPS = 30;

    // Collision
    final public static double BulletNZombineCollisionDistance_2 = Math.pow(100, 2);

    // Gravestone
    final public static double GravestoneHealth = 1000000000;
    final public static double GravestoneBrainProductionFPS = 0.055;
    final public static double GravestoneFPS = 10;

    // Brain
    final public static double BrainFPS = 30;

    // Zombines
    final public static double ZombineAttackingFPS = 10;
    final public static double ZombineDieFPS = 11;

    // Specific Zombine Types
    // Normal Zombine
    final public static double NormalZombineSpeed = 0.5;
    final public static double NormalZombineFPS = 11;
    final public static double NormalZombineAttackValue = 10;

    // Conehead Zombine
    final public static double ConeheadZombineSpeed = 0.5;
    final public static double ConeheadZombineAttackValue = 10;
    final public static double ConeheadZombineFPS = 11;

    // Buckethead Zombine
    final public static double BucketheadZombineSpeed = 0.5;
    final public static double BucketheadZombineAttackValue = 10;
    final public static double BucketheadZombineFPS = 11;

    // Flag Zombine
    final public static double FlagZombineSpeed = 0.6;
    final public static double FlagZombineAttackValue = 10;
    final public static double FlagZombineFPS = 11;

    // Newspaper Zombine
    final public static double NewspaperZombineSpeed = 0.4;
    final public static double NewspaperZombineAttackValue = 15;
    final public static double NewspaperZombineFPS = 11;

    // Plants
    // Peashooter
    final public static double PeashooterFPS = 11;
    final public static double PeashooterShootFPS = 0.5;
    final public static double PeashooterHealth = 100;

    // PepPeaShooter
    final public static double PepPeaShooterFPS = 11;
    final public static double PepPeaShooterShootFPS = 1;
    final public static double PepPeaShooterHealth = 100;

    // SnowPeashooter
    final public static int SnowPeashooterFPS = 11;
    final public static int SnowPeashooterShootFPS = 1;
    final public static double SnowPeashooterHealth = 100;

    // Bullets
    final public static double BulletFPS = 2;

    // Normal Pea
    final public static double NormalPeaDamage = 10;
    final public static double NormalPeaSpeed = 7;

    // Snow Pea
    final public static double SnowPeaDamage = 20;
    final public static double SnowPeaSpeed = 6;

    // Sunflower
    final public static double SunflowerHealth = 100;
    final public static double SunflowerSunProductionFPS = 0.08;
    final public static double SunflowerFPS = 11;

    // Chomper
    final public static double ChomperHealth = 200;
    final public static double ChomperFPS = 8;
    final public static double ChomperAttackFPS = 0.5;
    final public static double ChomperRange = 100;
    final public static double ChomperDigestionTime = 8000;

    // Squash
    final public static double SquashHealth = 100.0;
    final public static double SquashAttackFPS = 4;
    final public static double SquashAimFPS = 2;
    final public static double SquashFPS = 11;

    // Positioning
    final public static double[] ZombineRowYPos = { 170, 275, 384, 484, 590 };
    final public static double[] ZombineColumnXPos = { 170, 255, 350, 435, 520, 600, 690, 765, 870 };
    final public static double[] PlantsRowYPos = { 170, 275, 384, 484, 590 };
    final public static double[] PlantsColumnXPos = { 170, 255, 350, 435, 520, 600, 690, 765, 870 };

    // Constants for calculations
    final public static double INF = 1e40;
    final public static double EPS = 1e-5;

    final public static int MaxRow = 5;
    final public static int MaxColumn = 9;

    // Image Caching
    private static HashMap<String, Image> imageCache = new HashMap<>();

    static public synchronized Image getCachedImage(String imagePath) {
        if (imageCache.containsKey(imagePath)) {
            return imageCache.get(imagePath);
        } else {
            Image image = new Image(imagePath);
            imageCache.put(imagePath, image);
            return image;
        }
    }

    public static double[] getZombinePos(int row, int column) {
        double[] res = { ZombineColumnXPos[column], ZombineRowYPos[row] };
        return res;
    }

    public static URL getImagesPath() {
        return Constants.class.getResource("/images/");
    }

    public static URL getBackgroudImage() {
        return Constants.class.getResource("/images/Items/Background/Background_1.jpg");
    }

    public static URL getPlantsChooserImage() {
        return Constants.class.getResource("/images/Screen/PlantsChooserBackground.png");
    }

    public static URL getZombineChooserImage() {
        return Constants.class.getResource("/images/Screen/ZombineChooserBackground.png");
    }

    public static URL getZombineWinImage() {
        return Constants.class.getResource("/images/Screen/zombieswin.jpg");
    }

    public static URL getPlantWinImage() {
        return Constants.class.getResource("/images/Screen/plantswin.jpg");
    }

    // Music
    final static Media mainBgmMusic = new Media(Constants.class.getResource("/voices/mainBgm.mp3").toString());
    public static Media getMainBgmMusic() {
        return mainBgmMusic;
    }
    final static Media zombineHitted = new Media(Constants.class.getResource("/voices/zombineHitted.mp3").toString());
    public static Media getZombineHittedMusic() {
        return zombineHitted;
    }
    final static Media zombineVictory = new Media(Constants.class.getResource("/voices/zombineVictory.mp3").toString());
    public static Media getZombineVictoryMusic() {
        return zombineVictory;
    }
    final static Media zombineAttack = new Media(Constants.class.getResource("/voices/zombineAttack.mp3").toString());
    public static Media getZombineAttackMusic() {
        return zombineAttack;
    }
    final static Media collectSun = new Media(Constants.class.getResource("/voices/collectSun.mp3").toString());
    public static Media getCollectSunMusic() {
        return collectSun;
    }
    final static Media addNewObject = new Media(Constants.class.getResource("/voices/addNewObject.mp3").toString());
    public static Media getAddNewObjectMusic() {
        return addNewObject;
    }
    final static Media plantVictory = new Media(Constants.class.getResource("/voices/plantVictory.mp3").toString());
    public static Media getPlantVictoryMusic() {
        return plantVictory;
    }

    // Cards
    // Plants Cards
    final public static double PlantCardGap = 1.65;
    final public static double PlantCardWidth = 60;
    final public static double PlantCardHeight = 80;
    final public static int PlantsCardCount = 8;
    final public static double PlantsCardXPos = 94;
    final public static double PlantsCardYPos = 18;
    final public static ArrayList<Entry<String, URL>> PlantsCardImage = new ArrayList<>() {{
        // Initializing list of plant card images with associated names
        add(new SimpleEntry<String, URL>("Peashooter", getPeaShooterCardImage()));
        add(new SimpleEntry<String, URL>("Sunflower",  getSunflowerCardImage()));
        add(new SimpleEntry<String, URL>("PepPeaShooter", getPepPeaShooterCardImage()));
        add(new SimpleEntry<String, URL>("SnowPeashooter", getSnowPeashooterCardImage()));
        add(new SimpleEntry<String, URL>("Chomper", getChomperCardImage()));
        add(new SimpleEntry<String, URL>("Squash", getSquashCardImage()));
        add(new SimpleEntry<String, URL>("Peashooter", getPeaShooterCardImage()));// Duplicate entry for demonstration
        add(new SimpleEntry<String, URL>("Peashooter", getPeaShooterCardImage()));// Duplicate entry for demonstration
    }};

    final public static HashMap<String, Integer> PlantsSunCost = new HashMap<>() {{
        // Mapping of plant names to their respective sun costs
        put("Peashooter", 100);
        put("Sunflower", 50);
        put("PepPeaShooter", 200);
        put("SnowPeashooter", 175);
        put("Chomper", 150);
        put("Squash", 50);
    }};

    // Retrieving URLs for plant card images
    public static URL getPeaShooterCardImage() {
        return Constants.class.getResource("/images/Cards/card_peashooter.png");
    }
    public static URL getSunflowerCardImage() {
        return Constants.class.getResource("/images/Cards/card_sunflower.png");
    }
    public static URL getPepPeaShooterCardImage() {
        return Constants.class.getResource("/images/Cards/card_repeaterpea.png");
    }
    public static URL getSnowPeashooterCardImage() {
        return Constants.class.getResource("/images/Cards/card_snowpea.png");
    }
    public static URL getChomperCardImage() {
        return Constants.class.getResource("/images/Cards/card_chomper.png");
    }
    public static URL getSquashCardImage() {
        return Constants.class.getResource("/images/Cards/card_squash.png");
    }

    // zombine Cards

    final public static double ZombineCardGap = 3;
    final public static double ZombineCardWidth = 58;
    final public static double ZombineCardHeight = 79;
    final public static int ZombineCardCount = 8;
    final public static double ZombineCardXPos = 717;
    final public static double ZombineCardYPos = 18;
    final public static ArrayList<Entry<String, URL>> ZombineCardImage = new ArrayList<>() {{
        // Initializing list of zombine card images with associated names
        add(new SimpleEntry<String, URL>("NormalZombine", getNormalZombineCardImage()));
        add(new SimpleEntry<String, URL>("BucketHeadZombine", getBucketHeadZombineCardImage()));
        add(new SimpleEntry<String, URL>("FlagZombine", getFlagZombineCardImage()));
        add(new SimpleEntry<String, URL>("ConeheadZomine", getConeheadZomineCardImage()));
        add(new SimpleEntry<String, URL>("NewspaperZombine", getNewspaperZombineCardImage()));
        add(new SimpleEntry<String, URL>("NormalZombine", getNormalZombineCardImage()));
        add(new SimpleEntry<String, URL>("NormalZombine", getNormalZombineCardImage()));
        add(new SimpleEntry<String, URL>("NormalZombine", getNormalZombineCardImage()));
    }};
    final public static HashMap<String, Integer> ZombineBrainCost = new HashMap<>() {{
        // Mapping of zombine names to their respective brain costs
        put("NormalZombine", 25);
        put("BucketHeadZombine", 100);
        put("FlagZombine", 30);
        put("ConeheadZomine", 75);
        put("NewspaperZombine", 50);
    }};

    // Retrieving URLs for zombine card images
    public static URL getNormalZombineCardImage() {
        return Constants.class.getResource("/images/Cards/card_NormalZombie.png");
    }
    public static URL getBucketHeadZombineCardImage() {
        return Constants.class.getResource("/images/Cards/card_BucketheadZombie.png");
    }
    public static URL getFlagZombineCardImage() {
        return Constants.class.getResource("/images/Cards/card_FlagZombie.png");
    }
    public static URL getConeheadZomineCardImage() {
        return Constants.class.getResource("/images/Cards/card_ConHeadZombie.png");
    }
    public static URL getNewspaperZombineCardImage() {
        return Constants.class.getResource("/images/Cards/card_NewspaperZombie.png");
    }



}