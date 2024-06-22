package homework;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Brain {
    private double x;
    private double y;
    private ImageView imageView;
    String[] framesPath = ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Plants/Brain");

    int producedPassedFrames = 0;
    int currentFrameId = 0;
    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000 / Constants.BrainFPS), e -> {
        imageView.setImage(new Image(framesPath[currentFrameId]));
        currentFrameId = (currentFrameId + 1) % framesPath.length;
        producedPassedFrames += 1;

        double dy = 30 - 0.8 * producedPassedFrames;
        double dx = -10;
        if(Constants.WindowHeight - imageView.getY() < 100) {
            dy = 0;
            dx = -5;
        }
        imageView.setY(imageView.getY() - 0.4 * dy);
        imageView.setX(imageView.getX() - 0.4 * dx);
    }));

    public Brain(double x, double y) {
        this.x = x;
        this.y = y;
        imageView = new ImageView(new Image(framesPath[0]));
        imageView.setX(x);
        imageView.setY(y);
        imageView.setOnMouseClicked(event -> {
            collectBrain();
            event.consume();
        });
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        GlobalControl.rootPane.getChildren().add(imageView);
    }

    private void collectBrain() {
        GlobalControl.modifyBrainCount(25);  // Collecting a brain increases the brain count by 25
        GlobalControl.rootPane.getChildren().remove(imageView);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public ImageView getImageView() {
        return imageView;
    }
}
