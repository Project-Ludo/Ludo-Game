package io.github.ludogame.music;

import com.almasb.fxgl.audio.Audio;
import com.almasb.fxgl.audio.Music;
import com.almasb.fxgl.dsl.FXGL;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;
import java.util.ResourceBundle;

public class GameMusic{
    private final MediaPlayer mediaPlayer;
    private boolean isPlay;

    private Slider slider;

    ///Music has to be in resources/music
    public GameMusic(String musicName){
        String musicPath = getClass().getClassLoader().getResource("assets/music/" + musicName).toExternalForm();
        Media media = new Media(musicPath);
        mediaPlayer = new MediaPlayer(media);

        mediaPlayer.setVolume(0);
        mediaPlayer.play();
        isPlay = true;

        initSlider();
    }

    private void initSlider() {
        slider = new Slider();
        slider.setOrientation(Orientation.VERTICAL);
        slider.setMin(0);
        slider.setMax(100);
        slider.setLayoutX(20);
        slider.setLayoutY(20);
        slider.setValue(0);
        slider.valueProperty().addListener(
                observable -> mediaPlayer.setVolume(slider.getValue()/100));
    }

    public void stopMusic(){
        mediaPlayer.stop();
    }

    public void playMusic(){
        mediaPlayer.play();
    }
    public void pauseMusic(){
        mediaPlayer.pause();
    }

    public void switchMusic(){
        if (isPlay){
            pauseMusic();
            isPlay = false;
        }else {
            playMusic();
            isPlay = true;
        }
    }

    public void addSliderToUI(Button musicButton){
        slider.setLayoutX(musicButton.getLayoutX() + musicButton.getPrefWidth()/2 - slider.getPrefWidth()/2);
        slider.setLayoutY(musicButton.getLayoutY() + musicButton.getPrefHeight());
        FXGL.getGameScene().addUINode(slider);
    }

    public void removeSliderFromUI(){
        FXGL.getGameScene().removeUINode(slider);
    }
}
