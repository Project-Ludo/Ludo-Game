package io.github.ludogame.notification;

import com.almasb.fxgl.dsl.FXGL;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class ErrorNotification {

    private StackPane customBox;
    private Text message;

    public ErrorNotification(String message) {
        initNotification();

        addText(message);

        FXGL.getGameScene().addUINode(customBox);
        FXGL.runOnce(() -> FXGL.getGameScene().removeUINode(customBox), Duration.seconds(5));
    }

    private void initNotification() {
        this.customBox = new StackPane();

        customBox.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, new CornerRadii(10), Insets.EMPTY)));
        customBox.setPadding(new Insets(20));

        customBox.setLayoutX(50);
        customBox.setLayoutY(50);
    }

    private void addText(String message) {
        this.message = new Text(message);
        this.message.setFont(Font.font("Arial", 18));
        this.message.setFill(Color.BLACK);

        customBox.getChildren().add(this.message);
    }

}
