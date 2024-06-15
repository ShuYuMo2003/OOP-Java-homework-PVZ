package homework;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        Button btn = new Button("Hello World");
        btn.setOnAction(e -> System.out.println("Hello World!"));


        scene = new Scene(btn, Constants.WindowWidth, Constants.WindowHeight);
        stage.setScene(scene);
        stage.show();
        stage.setTitle(Constants.MainStageTitle);
    }

    public static void main(String[] args) {
        launch();
    }

}

