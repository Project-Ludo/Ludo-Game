package menu;

import com.almasb.fxgl.animation.AnimationBuilder;
import com.almasb.fxgl.dsl.FXGL;
import config.UIConfig;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ResourceBundle;

public class ConnectionMenuController extends DefaultMenuButtonAction implements Initializable {

    public TextField nicknameTextField;
    public TextField ipTextField;
    public Label playerNameText;
    public Label serverAddressText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        changeControlTexture(startButton, UIConfig.START_BUTTON_DEFAULT);
        changeControlTexture(exitButton, UIConfig.EXIT_BUTTON_DEFAULT);
        changeControlTexture(musicButton, UIConfig.MUSIC_BUTTON_DEFAULT);
        changeControlTexture(playerNameText, UIConfig.PLAYER_NAME_TEXT);
        changeControlTexture(serverAddressText, UIConfig.SERVER_ADDRESS_TEXT);
        initializeTextField(nicknameTextField, 20);
        initializeTextField(ipTextField, 21);
    }

    public void onStartButtonClick() {
        changeControlTexture(startButton, UIConfig.START_BUTTON_CLICK);
        sceneController.changeSceneAfter(sceneController.getServerLobbyScene(), 150);
    }

    public void onExitButtonClick() {
        changeControlTexture(exitButton, UIConfig.EXIT_BUTTON_CLICK);
        sceneController.changeSceneAfter(sceneController.getMainMenuScene(), 150);
    }

    public void onMusicButtonClick() {
        changeControlTextureFor(musicButton, UIConfig.MUSIC_BUTTON_CLICK, 150, UIConfig.MUSIC_BUTTON_HOVER);
    }

    private void initializeTextField(TextField textField, int maxLength) {
        Image textboxImage = new Image(UIConfig.TEXT_BOX, textField.getPrefWidth(), textField.getPrefHeight(), false, true);
        BackgroundImage myBI = new BackgroundImage(textboxImage,
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT
        );

        textField.setBackground(new Background(myBI));
        textField.textProperty().addListener(((ov, oldValue, newValue) -> {
            if (textField.getText().length() > maxLength) {
                String s = textField.getText().substring(0, maxLength);
                textField.setText(s);
            }
        }));

        textField.setFont(new Font(textField.getFont().getName(), 40 - maxLength));
    }
}
