package homework;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Sun {
    private double x;
    private double y;
    private ImageView imageView;

    public Sun(double x, double y) {
        this.x = x;
        this.y = y;
        Image sunImage = new Image(ListFiles.listAllFiles(Constants.getImagesPath().getPath() + "Plants/Sun")[0]);
        imageView = new ImageView(sunImage);
        imageView.setX(x);
        imageView.setY(y);
        imageView.setOnMouseClicked(event -> collectSun());
        GlobalControl.rootPane.getChildren().add(imageView);
    }

    private void collectSun() {
        GlobalControl.modifySunCount(25);  // Collecting a sun increases the sun count by 25
        GlobalControl.rootPane.getChildren().remove(imageView);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
