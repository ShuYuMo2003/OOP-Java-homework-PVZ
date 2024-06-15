package homework;

import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class Constants {
    final public static int WindowWidth = 960;
    final public static int WindowHeight = 640;
    final public static String MainStageTitle = "Plants Vs. Zombies";
    enum MapStage{
        PLAY, DIE, SHOWZOMBIE;
    }
    final public static Map<MapStage, Integer> MapStage2XPos = new HashMap<MapStage, Integer>(){
        {
            put(MapStage.PLAY, -170);
            put(MapStage.DIE, 0);
            put(MapStage.SHOWZOMBIE, -500);
        }
    };
    public static String getBackgroudImage() {
        return Constants.class.getResource("/images/Items/Background/Background_1.jpg").toString();
    }
}
