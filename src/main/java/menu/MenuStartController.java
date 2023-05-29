package menu;

import controller.SceneController;
import com.almasb.fxgl.dsl.FXGL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class MenuStartController extends MenuDefaultButtonAction implements Initializable {

    //    @FXML
//    public Button qmButton;
//    @FXML
//    public Button musicButton;
//    @FXML
//    public Button exitButton;
    @FXML
    private Button toConnectionSceneButton;
    @FXML
    public Label bajkoweLudoText;

    private SceneController sceneController;

    public MenuStartController(){
        //startButton.setText("START_V1");
    }

    public void setSceneController(SceneController sceneController) {
        this.sceneController = sceneController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //startButton.setSkin(new MyButtonSkin(startButton));

        imageInit(toConnectionSceneButton, "menu/texture/start-button.png");
        imageInit(qmButton, "menu/texture/qm-button_default.png");
        imageInit(exitButton, "menu/texture/exit-button_default.png");
        imageInit(musicButton, "menu/texture/music-button_default.png");
        imageInit(bajkoweLudoText, "menu/texture/tile.png");
        //imageInit(returnButton, "menu/texture/return-button_default.png");
    }
    ///Start Button
    public void toConnectionSceneButtonAction(ActionEvent actionEvent) {
        if(sceneController == null){
            System.out.println("NULL Scene controller");
            return;
        }
        FXGL.getGameScene().removeUINode(sceneController.getMenuStart());
        FXGL.getGameScene().addUINode(sceneController.getMenuConnection());
    }
    //QM mutton
    public void qmButtonAction(ActionEvent actionEvent){
        if(sceneController == null){
            System.out.println("NULL Scene controller");
            return;
        }
        FXGL.getGameScene().removeUINode(sceneController.getMenuStart());
        FXGL.getGameScene().addUINode(sceneController.getMenuRules());
    }

    //Exit Button
    public void exitButtonAction(ActionEvent actionEvent) {
        FXGL.getGameController().exit();
    }
}
