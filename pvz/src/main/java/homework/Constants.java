package homework;

import java.net.URL;

public class Constants {
    final public static int WindowWidth = 1300;
    final public static int WindowHeight = 640;
    final public static String MainStageTitle = "Plants Vs. Zombies";
    // MapStage2XPos 0 : Normal Stage; 1 : die.
    final public static double PlayingStageMapXPos = -180d;
    final public static double cleanUpFPS = 10;
    final public static double NormalZombineSpeed = 0.8;
    final public static double NormalZombineFPS = 11;
    final public static double NormalZombineAttackValue = 10;
    final public static double PeashooterFPS = 11;
    final public static double ZombineAttackingFPS = 2;
    final public static double GlobalFPS = 30;

    final public static double[] ZombineRowYPos    = { 170, 275, 384, 484, 590 };
    final public static double[] ZombineColumnXPos = { 170, 255, 350, 435, 520, 600, 690, 765, 870 };
    final public static double[] PlantsRowYPos     = { 170, 275, 384, 484, 590 };
    final public static double[] PlantsColumnXPos  = { 170, 255, 350, 435, 520, 600, 690, 765, 870 };
    final public static double INF = 1e40;
    final public static double EPS = 1e-5;

    final public static int MaxRow = 5;
    final public static int MaxColumn = 9;


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


}
