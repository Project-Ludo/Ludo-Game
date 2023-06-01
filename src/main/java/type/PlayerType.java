package type;

import config.UIConfig;
import javafx.scene.image.Image;

public enum PlayerType {
    YEllOW(UIConfig.YELLOW_IDLE_IMG, UIConfig.YELLOW_RUN_IMG),
    GREEN(UIConfig.GREEN_IDLE_IMG, UIConfig.GREEN_RUN_IMG),
    RED(UIConfig.RED_IDLE_IMG, UIConfig.RED_RUN_IMG),
    BLUE(UIConfig.BLUE_IDLE_IMG, UIConfig.BLUE_RUN_IMG);

    public final Image idleImage;
    public final Image runImage;

    PlayerType(Image idleImage, Image runImage) {
        this.idleImage = idleImage;
        this.runImage = runImage;
    }
}
