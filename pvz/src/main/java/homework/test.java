package homework;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class test extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create a play button
        Button playButton = new Button("Play Sound");

        // Set the button click event
        playButton.setOnAction(e -> playSound());

        // Create a layout and add the button
        StackPane root = new StackPane();
        root.getChildren().add(playButton);

        // Create a scene and set it to the stage
        Scene scene = new Scene(root, 300, 200);
        primaryStage.setTitle("JavaFX Sound Player Example");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void playSound() {
        // Path to the sound file
        String soundFile = "file:/D:/Codes_and_Projects/PlantsVsZombines/pvz/target/classes/ThemeSong2.mp3";

        // Create a Media object with the sound file path
        Media sound = new Media(soundFile);
        System.err.println("Playing sound: " + soundFile);

        // Create a MediaPlayer to play the sound
        MediaPlayer mediaPlayer = new MediaPlayer(sound);

        // Play the sound
        mediaPlayer.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
