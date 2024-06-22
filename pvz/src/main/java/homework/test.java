package homework;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.BufferedInputStream;

import java.io.File;

public class test extends Application {

    @Override
    public void start(Stage primaryStage) {
        // 创建播放按钮
        Button playButton = new Button("播放声音");

        // 设置按钮点击事件
        playButton.setOnAction(e -> playSound());

        // 创建布局并添加按钮
        StackPane root = new StackPane();
        root.getChildren().add(playButton);

        // 创建场景并设置到舞台
        Scene scene = new Scene(root, 300, 200);
        primaryStage.setTitle("JavaFX 播放声音示例");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void playSound() {
        // 获取声音文件的URL
        String soundFile = "file:/D:/Codes_and_Projects/PlantsVsZombines/pvz/target/classes/ThemeSong2.mp3";
        Media sound = new Media(soundFile);
        System.err.println("Playing sound: " + soundFile);
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}