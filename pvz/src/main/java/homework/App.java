package homework;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;




import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {
    private static ImageView backgroundImageView;
    private static ImageView cardsChooserImageView;

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


    private static void MoveMapToDie(int seconds) {
        TranslateTransition tt = new TranslateTransition(Duration.seconds(seconds), backgroundImageView);
        double currentX = backgroundImageView.getX();
        tt.setToX(0 - currentX);
        tt.play();
    }

    @Override
    public void start(Stage stage) throws IOException {
        // Constants.preCacheImages();
        initializeBackgroudImage();
        initializePlantCardsChooser();
        initializeZombineCardsChooser();

        GlobalControl.initializeEverything();


        // 第一列 5 个豌豆射手
         for(int i = 0; i < 5; i++) {
            GlobalControl.addPlants(new Peashooter(i, 0));
         }


        for(int i = 0; i < 5; i++) {
            switch (i % 2) {
                case 0:
                    GlobalControl.addZombine(new NormalZombine(i, 3));
                    break;
                case 1:
                    GlobalControl.addZombine(new ConeheadZomine(i, 3));
                    break;
            }
        }

        // GlobalControl.addBullets(new NormalPea(200, 200));

        // Image img = new Image("file:/D:/Codes_and_Projects/PlantsVsZombines/pvz/target/classes/images/Zombies/NormalZombie/Zombie/Zombie_0.png");
        // //                         file:/D:/Codes_and_Projects/PlantsVsZombines/pvz/target/classes/images/Items/Background/Background_1.jpg
        // // System.err.println(Constants.getBackgroudImage().toString());
        // // Image img = new Image(Constants.getBackgroudImage().toString());
        // ImageView imgv = new ImageView(img);
        // imgv.setX(300);
        // imgv.setY(300);
        // imgv.setFitHeight(300);
        // imgv.setFitWidth(300);
        // GlobalControl.rootPane.getChildren().add(imgv);




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

