package type;

import config.Config;
import javafx.scene.image.Image;

public enum PlayerType {
    YEllOW(Config.YELLOW_STAY_IMG, Config.YELLOW_RUN_IMG),
    GREEN(Config.GREEN_STAY_IMG, Config.GREEN_RUN_IMG),
    RED(Config.RED_STAY_IMG, Config.RED_RUN_IMG),
    BLUE(Config.BLUE_STAY_IMG, Config.BLUE_RUN_IMG);

    public final Image stayImage;
    public final Image runImage;

    PlayerType(Image stayImage, Image runImage) {
        this.stayImage = stayImage;
        this.runImage = runImage;
    }
}
