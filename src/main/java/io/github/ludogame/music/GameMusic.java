package io.github.ludogame.music;

import com.almasb.fxgl.audio.Audio;
import com.almasb.fxgl.audio.Music;
import com.almasb.fxgl.dsl.FXGL;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;
import java.util.ResourceBundle;

public class GameMusic{
    private final MediaPlayer mediaPlayer;
    private boolean isPlay;

    ///Music has to be in resources/music
    public GameMusic(String musicName){
        String musicPath = getClass().getClassLoader().getResource("assets/music/" + musicName).toExternalForm();
        Media media = new Media(musicPath);
        mediaPlayer = new MediaPlayer(media);

        mediaPlayer.setVolume(0.5);
        mediaPlayer.play();
        isPlay = true;
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
}
