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
    public boolean deprecated;
    int producedPassedFrames = 0;
    int currentFrameId = 0;
    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000 / Constants.BrainFPS), e -> {
        imageView.setImage(Constants.getCachedImage(framesPath[currentFrameId]));
        currentFrameId = (currentFrameId + 1) % framesPath.length;
        producedPassedFrames += 1;

        double dy = 30 - 0.8 * producedPassedFrames;
        double dx = -10;
        if(Constants.WindowHeight - imageView.getY() < 100) {
            dy = 0;
            dx = -5;
        }
        imageView.setY(this.y = imageView.getY() - 0.4 * dy);
        imageView.setX(this.x = imageView.getX() + 0.4 * dx);
        if(imageView.getX() < 0 || imageView.getY() < 0 || imageView.getX() > Constants.WindowWidth || imageView.getY() > Constants.WindowHeight) {
            deprecated = true;
        }
    }));

    public Brain(double x, double y) {
        this.x = x;
        this.y = y;
        imageView = new ImageView(Constants.getCachedImage(framesPath[0]));
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
        deprecated = true;
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
