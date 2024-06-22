package homework;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;


public class Sun {
    private double x;
    private double y;
    private ImageView imageView;
    String[] framesPath = ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Plants/Sun");

    public boolean deprecated = false;

    int productedPassedFrames = 0;
    int currentFrameId = 0;
    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000 / Constants.SunFPS), e -> {
        imageView.setImage(Constants.getCachedImage(framesPath[currentFrameId]));
        currentFrameId = (currentFrameId + 1) % framesPath.length;
        productedPassedFrames += 1;

        double dy = 30 - 0.8 * productedPassedFrames;
        double dx = -10;
        if(Constants.WindowHeight - imageView.getY() < 100) {
            dy = 0;
            dx = -5;
        }
        imageView.setY(this.y = imageView.getY() - 0.4 * dy);
        imageView.setX(this.x = imageView.getX() - 0.4 * dx);
        if(imageView.getX() < 0 || imageView.getY() < 0 || imageView.getX() > Constants.WindowWidth || imageView.getY() > Constants.WindowHeight) {
            deprecated = true;
        }
    }));

    public Sun(double x, double y) {
        this.x = x;
        this.y = y;
        imageView = new ImageView(Constants.getCachedImage(framesPath[0]));
        imageView.setX(x);
        imageView.setY(y);
        imageView.setOnMouseClicked(event -> {
            collectSun();
            event.consume();
        });
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        GlobalControl.rootPane.getChildren().add(imageView);
    }

    private void collectSun() {
        GlobalControl.modifySunCount(25);  // Collecting a sun increases the sun count by 25
        GlobalControl.rootPane.getChildren().remove(imageView);
        deprecated = true;
        GlobalControl.playOnce(Constants.getCollectSunMusic(), 1.3);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
