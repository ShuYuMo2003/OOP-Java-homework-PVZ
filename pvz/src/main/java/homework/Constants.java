package homework;

public class Constants {
    final public static int WindowWidth = 1300;
    final public static int WindowHeight = 640;
    final public static String MainStageTitle = "Plants Vs. Zombies";
    // MapStage2XPos 0 : Normal Stage; 1 : die.
    final public static double PlayingStageMapXPos = -180d;
    final public static double NormalZombineSpeed = 15d;
    final public static double NormalZombineFPS = 15;

    public static String getImagesPath() {
        return Constants.class.getResource("/images/").toString();
    }

    public static String getBackgroudImage() {
        return Constants.class.getResource("/images/Items/Background/Background_1.jpg").toString();
    }


}
