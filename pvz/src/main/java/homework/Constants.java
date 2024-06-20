
package homework;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import java.util.Map.Entry;
import java.util.AbstractMap.SimpleEntry;

import javafx.scene.image.Image;

public class Constants {
    final public static boolean Debug = true;
    final public static int WindowWidth = 1300;
    final public static int WindowHeight = 640;
    final public static String MainStageTitle = "Plants Vs. Zombies";
    // MapStage2XPos 0 : Normal Stage; 1 : die.
    final public static double PlayingStageMapXPos = -180d;
    final public static double cleanUpFPS = 60;
    final public static double GlobalFPS = 30;

    final public static double BulletNZombineCollisionDistance_2 = Math.pow(100, 2);

    // Zombines
    final public static double ZombineAttackingFPS = 10;
    final public static double ZombineDieFPS = 11;

    // Normal Zombine
    final public static double NormalZombineSpeed = 0.5;
    final public static double NormalZombineFPS = 11;
    final public static double NormalZombineAttackValue = 1;

    //Conehead Zombine
    final public static double ConeheadZombineSpeed = 0.8;
    final public static double ConeheadZombineAttackValue = 1.0;
    final public static double ConeheadZombineFPS = 15;

    // Plants
    // Peashooter
    final public static double PeashooterFPS = 11;
    final public static double PeashooterShootFPS = 1;
    final public static double PeashooterHealth = 100;

    // Bullets
    final public static double BulletFPS = 5;

    // Normal Pea
    final public static double NormalPeaDamage = 20;
    final public static double NormalPeaSpeed = 8;

    //SunFlower
    final public static double SunflowerHealth = 200;
    final public static double SunflowerSunProductionFPS = 0.1;
    final public static double SunflowerFPS = 11;


    final public static double[] ZombineRowYPos    = { 170, 275, 384, 484, 590 };
    final public static double[] ZombineColumnXPos = { 170, 255, 350, 435, 520, 600, 690, 765, 870 };
    final public static double[] PlantsRowYPos     = { 170, 275, 384, 484, 590 };
    final public static double[] PlantsColumnXPos  = { 170, 255, 350, 435, 520, 600, 690, 765, 870 };
    final public static double INF = 1e40;
    final public static double EPS = 1e-5;

    final public static int MaxRow = 5;
    final public static int MaxColumn = 9;


    private static HashMap<String, Image> imageCache = new HashMap<>();

    static public synchronized Image getCachedImage(String imagePath) {
        if(imageCache.containsKey(imagePath)) {
            return imageCache.get(imagePath);
        } else {
            Image image = new Image(imagePath);
            imageCache.put(imagePath, image);
            return image;
        }
    }

    private static String[] NecessaryImagePathPreCached = {
        "file:/D:/Codes_and_Projects/PlantsVsZombines/pvz/target/classes/images/Bullets/PeaNormal/PeaNormal_0.png",
        "file:/D:/Codes_and_Projects/PlantsVsZombines/pvz/target/classes/images/Bullets/PeaNormalExplode/PeaNormalExplode_0.png",
    };
    public static void preCacheImages() {
        for(int pid = 0; pid < NecessaryImagePathPreCached.length; pid++) {
            getCachedImage(NecessaryImagePathPreCached[pid]);
            System.err.println("Cached (" + (pid + 1) + " / " + NecessaryImagePathPreCached.length + ")" + NecessaryImagePathPreCached[pid]);
        }
    }


    public static double[] getZombinePos(int row, int column) {
        double[] res = {ZombineColumnXPos[column], ZombineRowYPos[row]};
        return res;
    }

    public static URL getImagesPath() {
        // System.out.println("QAQ" + Constants.class.getClassLoader().getResource("").getPath());

        // String directoryPath = Constants.class.getClassLoader().getResource("images/").getPath();
        // System.out.println("directoryPath = " + directoryPath);
        // java.io.File directory = new java.io.File(directoryPath);
        // java.io.File[] files = directory.listFiles();

        // System.out.println(directoryPath);
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

    // Cards



    // Plants Cards
    final public static double PlantCardGap = 1.65;
    final public static double PlantCardWidth = 60;
    final public static double PlantCardHeight = 80;
    final public static int PlantsCardCount = 8;
    final public static double PlantsCardXPos = 94;
    final public static double PlantsCardYPos = 18;
    final public static ArrayList<Entry<String, URL>> PlantsCardImage = new ArrayList<>() {{
        add(new SimpleEntry<String, URL>("Peashooter", getPeaShooterCardImage()));
        add(new SimpleEntry<String, URL>("Sunflower",  getSunflowerCardImage()));
        add(new SimpleEntry<String, URL>("Peashooter", getPeaShooterCardImage()));
        add(new SimpleEntry<String, URL>("Peashooter", getPeaShooterCardImage()));
        add(new SimpleEntry<String, URL>("Peashooter", getPeaShooterCardImage()));
        add(new SimpleEntry<String, URL>("Peashooter", getPeaShooterCardImage()));
        add(new SimpleEntry<String, URL>("Peashooter", getPeaShooterCardImage()));
        add(new SimpleEntry<String, URL>("Peashooter", getPeaShooterCardImage()));
    }};

    public static URL getPeaShooterCardImage() {
        return Constants.class.getResource("/images/Cards/card_peashooter.png");
    }
    public static URL getSunflowerCardImage() {
        return Constants.class.getResource("/images/Cards/card_sunflower.png");
    }

    // zombine Cards
    final public static double ZombineCardGap = 3;
    final public static double ZombineCardWidth = 58;
    final public static double ZombineCardHeight = 79;
    final public static int ZombineCardCount = 8;
    final public static double ZombineCardXPos = 717;
    final public static double ZombineCardYPos = 18;
    final public static ArrayList<Entry<String, URL>> ZombineCardImage = new ArrayList<>() {{
        add(new SimpleEntry<String, URL>("NormalZombine", getNormalZombineCardImage()));
        add(new SimpleEntry<String, URL>("NormalZombine", getNormalZombineCardImage()));
        add(new SimpleEntry<String, URL>("NormalZombine", getNormalZombineCardImage()));
        add(new SimpleEntry<String, URL>("NormalZombine", getNormalZombineCardImage()));
        add(new SimpleEntry<String, URL>("NormalZombine", getNormalZombineCardImage()));
        add(new SimpleEntry<String, URL>("NormalZombine", getNormalZombineCardImage()));
        add(new SimpleEntry<String, URL>("NormalZombine", getNormalZombineCardImage()));
        add(new SimpleEntry<String, URL>("NormalZombine", getNormalZombineCardImage()));
    }};

    public static URL getNormalZombineCardImage() {
        return Constants.class.getResource("/images/Cards/card_NormalZombie.png");
    }


}
