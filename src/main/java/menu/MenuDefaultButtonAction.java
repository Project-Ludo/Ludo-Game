package menu;

import controller.SceneController;
import com.almasb.fxgl.dsl.FXGL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Labeled;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public abstract class MenuDefaultButtonAction{
    @FXML
    public Button qmButton;
    @FXML
    public Button returnButton;
    @FXML
    public Button musicButton;
    @FXML
    public Button exitButton;
    @FXML
    public Button toConnectionSceneButton;
    @FXML
    public Button toGameSceneButton;

    public void imageInit(Labeled control, String imagePath) {
        ImageView view = new ImageView(new Image(imagePath));
        view.setFitWidth(control.getPrefWidth());
        view.setPreserveRatio(true);
        control.setGraphic(view);
    }

    public void toConnectionSceneButtonHover(MouseEvent mouseEvent) {

    }

    public void toConnectionSceneButtonExited(MouseEvent mouseEvent) {

    }

    public void toGameSceneButtonHover(MouseEvent mouseEvent) {

    }

    public void toGameSceneButtonExited(MouseEvent mouseEvent) {

    }
    ///Music Button
    public void musicButtonAction(ActionEvent actionEvent) {
        System.out.println("Music");
    }
    public void musicButtonHover(MouseEvent dragEvent) {
        imageInit(musicButton, "menu/texture/music-button_hover.png");
    }
    public void musicButtonExited(MouseEvent mouseEvent) {
        imageInit(musicButton, "menu/texture/music-button_default.png");
    }

    //QM mutton
    public void qmButtonHover(MouseEvent dragEvent) {
        imageInit(qmButton, "menu/texture/qm-button_hover.png");
    }
    public void qmButtonExited(MouseEvent mouseEvent) {
        imageInit(qmButton, "menu/texture/qm-button_default.png");
    }

    //Return button
    public void returnButtonHover(MouseEvent dragEvent) {
        imageInit(qmButton, "menu/texture/qm-button_hover.png");
    }
    public void returnButtonExited(MouseEvent mouseEvent) {
        imageInit(qmButton, "menu/texture/return-button_default.png");
    }

    //Exit Button
    public void exitButtonHover(MouseEvent dragEvent) {
        imageInit(exitButton, "menu/texture/exit-button_hover.png");
    }

    public void exitButtonExited(MouseEvent dragEvent) {
        imageInit(exitButton, "menu/texture/exit-button_default.png");
    }
}