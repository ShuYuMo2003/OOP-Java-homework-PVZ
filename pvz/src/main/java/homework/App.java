package homework;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    // private static void MoveMapToDie(int seconds) {
    //     TranslateTransition tt = new TranslateTransition(Duration.seconds(seconds), backgroundImageView);
    //     double currentX = backgroundImageView.getX();
    //     tt.setToX(0 - currentX);
    //     tt.play();
    // }

    @Override
    public void start(Stage stage) throws IOException {
        // Constants.preCacheImages();


        GlobalControl.initializeEverything();


        // 第一列 5 个豌豆射手
         //for(int i = 0; i < 5; i++) {
            //GlobalControl.addPlants(new Chomper(i, 0));
        // }
        for(int i = 0; i < 5; i++) {
         GlobalControl.addPlants(new SnowPeashooter(2, 0));
        }


        for(int i = 0; i < 5; i++) {
            switch (i % 2) {
                case 0:
                    GlobalControl.addZombine(new NormalZombine(i, 4));
                    break;
                case 1:
                    GlobalControl.addZombine(new NewspaperZombine(i, 3));
                    GlobalControl.addZombine(new ConeheadZomine(i, 4));
                    break;
            }
        }


        Scene scene = new Scene(GlobalControl.rootPane,
                Constants.WindowWidth,
                Constants.WindowHeight);
        stage.setScene(scene);
        stage.show();
        stage.setTitle(Constants.MainStageTitle);
        stage.setResizable(false);
    }

    public static void main(String[] args) {
        launch();
    }

}

