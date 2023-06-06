package io.github.ludogame.menu;

import animatefx.animation.Shake;
import com.almasb.fxgl.core.serialization.Bundle;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.net.Client;
import io.github.ludogame.LudoPlayerApp;
import io.github.ludogame.config.UIConfig;
import io.github.ludogame.network.client.ClientConnector;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class ConnectionMenuController extends DefaultMenuButtonAction implements Initializable {

    @FXML
    public TextField nicknameTextField;

    @FXML
    public TextField ipTextField;

    @FXML
    public Label playerNameText;

    @FXML
    public Label serverAddressText;

    @FXML
    public Label errorNicknameText;

    @FXML
    public Label errorIpAddressText;

    private static final Pattern PLAYER_NAME_PATTERN = Pattern.compile("[a-z0-9_]+", Pattern.CASE_INSENSITIVE);
    private static final Pattern IP_ADDRESS_PATTERN = Pattern.compile("[0-9.:]+", Pattern.CASE_INSENSITIVE);
    private static final int PLAYER_NAME_MAX_LENGTH = 20;
    private static final int IP_ADDRESS_MAX_LENGTH = 21;
    private static final int PLAYER_NAME_MIN_LENGTH = 3;
    private static final int IP_ADDRESS_MIN_LENGTH = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        changeControlTexture(startButton, UIConfig.START_BUTTON_DEFAULT);
        changeControlTexture(exitButton, UIConfig.EXIT_BUTTON_DEFAULT);
        changeControlTexture(musicButton, UIConfig.MUSIC_BUTTON_DEFAULT);
        changeControlTexture(playerNameText, UIConfig.PLAYER_NAME_TEXT);
        changeControlTexture(serverAddressText, UIConfig.SERVER_ADDRESS_TEXT);

        initializeTextField(nicknameTextField, errorNicknameText, PLAYER_NAME_MAX_LENGTH, PLAYER_NAME_MIN_LENGTH, PLAYER_NAME_PATTERN);
        initializeTextField(ipTextField, errorIpAddressText, IP_ADDRESS_MAX_LENGTH, IP_ADDRESS_MIN_LENGTH, IP_ADDRESS_PATTERN);

        nicknameTextField.setText("shorv");
        ipTextField.setText("127.0.0.1:55555");
    }

    public void onStartButtonClick() {
        changeControlTextureFor(startButton, UIConfig.START_BUTTON_CLICK, 150, UIConfig.START_BUTTON_DEFAULT);

        if (validateText(nicknameTextField, PLAYER_NAME_MAX_LENGTH, PLAYER_NAME_MIN_LENGTH, PLAYER_NAME_PATTERN) != ValidationError.NO_ERROR) {
            new Shake(nicknameTextField).setSpeed(1.2).play();
            return;
        }

        if (validateText(ipTextField, IP_ADDRESS_MAX_LENGTH, IP_ADDRESS_MIN_LENGTH, IP_ADDRESS_PATTERN) != ValidationError.NO_ERROR) {
            new Shake(ipTextField).setSpeed(1.2).play();
            return;
        }
        LudoPlayerApp.player.setNickname(nicknameTextField.getText());
        String[] split = ipTextField.getText().split(":");
        String ip = split[0];
        int port = Integer.parseInt(split[1]);

        ClientConnector clientConnector = new ClientConnector();
        Client<Bundle> connect = clientConnector.connect(ip, port, LudoPlayerApp.player);
        FXGL.run(() -> LudoPlayerApp.player.setDataBundle(connect), Duration.millis(500));

        sceneController.changeSceneAfter(sceneController.getServerLobbyScene(), 150);
    }

    public void onExitButtonClick() {
        changeControlTexture(exitButton, UIConfig.EXIT_BUTTON_CLICK);
        sceneController.changeSceneAfter(sceneController.getMainMenuScene(), 150);
    }

    public void onMusicButtonClick() {
        changeControlTextureFor(musicButton, UIConfig.MUSIC_BUTTON_CLICK, 150, UIConfig.MUSIC_BUTTON_HOVER);
    }

    private void initializeTextField(TextField textField, Label errorMessageField, int maxLength, int minLength, Pattern pattern) {
        setTextFieldBackground(textField, UIConfig.TEXT_BOX);
        initializeTextFieldListener(textField, errorMessageField, maxLength, minLength, pattern);
        textField.setFont(new Font(textField.getFont().getName(), 40 - maxLength));
    }

    private void setTextFieldBackground(TextField textField, String backgroundPath) {
        Image textboxImage = new Image(backgroundPath, textField.getPrefWidth(), textField.getPrefHeight(), false, true);
        BackgroundImage myBI = new BackgroundImage(textboxImage,
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT
        );
        textField.setBackground(new Background(myBI));
    }

    private void initializeTextFieldListener(TextField textField, Label errorMessage, int maxLength, int minLength, Pattern pattern) {
        textField.textProperty().addListener(((ov, oldValue, newValue) -> {
            ValidationError validationError = validateText(textField, maxLength, minLength, pattern);

            if (validationError == ValidationError.TOO_LONG) {
                String s = textField.getText().substring(0, maxLength);
                textField.setText(s);

                errorMessage.setText(String.format(UIConfig.ERROR_TEXT_TOO_LONG, maxLength));
                errorMessage.setTextFill(Color.RED);

                new Shake(textField).setSpeed(1.2).play();
                setTextFieldBackground(textField, UIConfig.TEXT_BOX_ERROR);
                return;
            }

            if (validationError == ValidationError.NO_ERROR) {
                errorMessage.setText("");
                setTextFieldBackground(textField, UIConfig.TEXT_BOX);
                return;
            }

            if (validationError == ValidationError.EMPTY_FIELD) {
                errorMessage.setText(UIConfig.EMPTY_TEXT_FIELD);
                errorMessage.setTextFill(Color.RED);
                setTextFieldBackground(textField, UIConfig.TEXT_BOX_ERROR);
                return;
            }

            if (validationError == ValidationError.PATTERN) {
                errorMessage.setText(UIConfig.ERROR_NOT_ALLOWED_CHAR);
                errorMessage.setTextFill(Color.RED);
                setTextFieldBackground(textField, UIConfig.TEXT_BOX_ERROR);
                return;
            }

            if (validationError == ValidationError.TOO_SHORT) {
                errorMessage.setText(String.format(UIConfig.WARNING_TEXT_TOO_SHORT, minLength));
                errorMessage.setTextFill(Color.GOLD);
                setTextFieldBackground(textField, UIConfig.TEXT_BOX);
            }
        }));
    }

    private ValidationError validateText(TextField textField, int maxLength, int minLength, Pattern pattern) {
        if (textField.getText().length() > maxLength) {
            return ValidationError.TOO_LONG;
        }

        if (textField.getText().length() <= 0) {
            return ValidationError.EMPTY_FIELD;
        }

        if (textField.getText().length() < minLength) {
            return ValidationError.TOO_SHORT;
        }

        if (!pattern.matcher(textField.getText()).matches()) {
            return ValidationError.PATTERN;
        }

        return ValidationError.NO_ERROR;
    }

    private enum ValidationError {
        PATTERN,
        TOO_LONG,
        TOO_SHORT,
        EMPTY_FIELD,
        NO_ERROR
    }
}
