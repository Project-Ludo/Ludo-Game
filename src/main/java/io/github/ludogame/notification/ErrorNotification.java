package io.github.ludogame.notification;

import com.almasb.fxgl.dsl.FXGL;
import io.github.ludogame.config.Config;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

public class ErrorNotification {

    private StackPane customBox;
    private Text message;

    public ErrorNotification(String message) {
        initNotification();

        addText(message);

        FXGL.getGameScene().addUINode(customBox);
        FXGL.runOnce(() -> FXGL.getGameScene().removeUINode(customBox), Duration.seconds(4));
    }

    private void initNotification() {
        this.customBox = new StackPane();

        Image image = new Image(this.getClass().getClassLoader().getResource("notification/textures/notification_background.png").toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(200);
        imageView.setFitHeight(100);

        customBox.getChildren().add(imageView);

        customBox.setLayoutX(Config.MAP_WIDTH/2 - imageView.getFitWidth()/2);
        customBox.setLayoutY(-5);
    }

    private void addText(String message) {
        this.message = new Text(message);
        this.message.setFont(Font.font("Arial", 16));
        this.message.setFill(Color.BLACK);
        this.message.setTextAlignment(TextAlignment.CENTER);
        this.message.setWrappingWidth(180);

        customBox.getChildren().add(this.message);
    }

}
