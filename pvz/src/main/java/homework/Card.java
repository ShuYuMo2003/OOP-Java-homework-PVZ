package homework;

import javafx.animation.ScaleTransition;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public abstract class Card {
    private ImageView cardImageView;
    private double x;
    private double y;
    private double width;
    private double height;
    ColorAdjust brighten = new ColorAdjust() {{
        setBrightness(0.7);
    }};
    ScaleTransition scaleTransition;

    Card() {}

    Card(Image img, double x, double y, double width, double height) {
        cardImageView = new ImageView(img);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        cardImageView.setX(x);
        cardImageView.setY(y);
        cardImageView.setFitWidth(width);
        cardImageView.setFitHeight(height);

        scaleTransition = new ScaleTransition(Duration.millis(200), cardImageView);
        scaleTransition.setToX(1.1);
        scaleTransition.setToY(1.1);
        scaleTransition.setCycleCount(1);
        scaleTransition.setAutoReverse(false);

        cardImageView.setOnMouseEntered(e -> {
            cardImageView.setEffect(brighten);
            scaleTransition.playFromStart();
        });
        cardImageView.setOnMouseExited(e -> {
            cardImageView.setEffect(null);
            scaleTransition.stop();
            cardImageView.setScaleX(1.0);
            cardImageView.setScaleY(1.0);
        });

        cardImageView.setOnMouseClicked(e -> {
            processOnMouseClicked(e);
            // 阻止冒泡
            e.consume();
        });

        GlobalControl.rootPane.getChildren().add(cardImageView);
    }

    protected abstract void processOnMouseClicked(MouseEvent e);
}
