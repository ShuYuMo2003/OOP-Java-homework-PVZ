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

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {
    private static Pane rootPane = new Pane();
    private static ImageView backgroundImageView;

    private static void initializeBackgroudImage() {
        Image backgroundImage = new Image(Constants.getBackgroudImage());
        backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitHeight(Constants.WindowHeight);
        backgroundImageView.setPreserveRatio(true);
        backgroundImageView.setX(Constants.MapStage2XPos.get(Constants.MapStage.PLAY));
        rootPane.getChildren().add(backgroundImageView);
    }

    private static void moveBackgroundImage(Constants.MapStage stage, int seconds) {
        TranslateTransition tt = new TranslateTransition(Duration.seconds(seconds), backgroundImageView);
        double currentX = backgroundImageView.getX();
        tt.setToX(Constants.MapStage2XPos.get(stage) - currentX);
        tt.play();
    }

    @Override
    public void start(Stage stage) throws IOException {

        initializeBackgroudImage();



        Scene scene = new Scene(rootPane, Constants.WindowWidth, Constants.WindowHeight);
        stage.setScene(scene);
        stage.show();
        stage.setTitle(Constants.MainStageTitle);
        stage.setResizable(false);
    }

    public static void main(String[] args) {
        launch();
    }

}

