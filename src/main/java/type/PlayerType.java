package type;

import config.Config;
import javafx.scene.image.Image;

public enum PlayerType {
    YEllOW(Config.YELLOW_IDLE_IMG, Config.YELLOW_RUN_IMG),
    GREEN(Config.GREEN_IDLE_IMG, Config.GREEN_RUN_IMG),
    RED(Config.RED_IDLE_IMG, Config.RED_RUN_IMG),
    BLUE(Config.BLUE_IDLE_IMG, Config.BLUE_RUN_IMG);

    public final Image idleImage;
    public final Image runImage;

    PlayerType(Image idleImage, Image runImage) {
        this.idleImage = idleImage;
        this.runImage = runImage;
    }
}
