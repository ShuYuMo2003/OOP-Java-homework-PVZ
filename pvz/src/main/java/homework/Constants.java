package homework;

import java.net.URL;

public class Constants {
    final public static int WindowWidth = 1300;
    final public static int WindowHeight = 640;
    final public static String MainStageTitle = "Plants Vs. Zombies";
    // MapStage2XPos 0 : Normal Stage; 1 : die.
    final public static double PlayingStageMapXPos = -180d;
    final public static double NormalZombineSpeed = 0;
    final public static double NormalZombineFPS = 11;
    final public static double PeashooterFPS = 11;
    final public static double GlobalFPS = 30;
    final public static double[] RowYPos    = { 195, 365, 521, 695, 833 };
    final public static double[] ColumnXPos = { 203, 330, 450, 600, 720, 855, 970, 1101, 1240 };

    public static double[] getPos(int row, int column) {
        double[] res = {ColumnXPos[column], RowYPos[row]};
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
