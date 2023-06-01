package config;

import com.almasb.fxgl.dsl.FXGL;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

import java.lang.module.FindException;
import java.util.List;

public final class UIConfig {

    public static final int BOARD_WIDTH = 500;
    public static final int BOARD_START_LAYOUT_X = ((Config.MAP_WIDTH / 2) - (BOARD_WIDTH / 2));
    public static final int BOARD_START_LAYOUT_Y = ((Config.MAP_HEIGHT / 2) - (BOARD_WIDTH / 2));

    //STARTS POINT
    private static final int START_SPAWN_POINT_X_V1 = (Config.MAP_WIDTH / 2) - (Config.BLOCK_SIZE * Config.MAP_SIZE / 2) + Config.BLOCK_SIZE;
    private static final int START_SPAWN_POINT_X_V2 = START_SPAWN_POINT_X_V1 + 11 * Config.BLOCK_SIZE;
    private static final int START_SPAWN_POINT_Y_V1 = (Config.MAP_HEIGHT / 2) - (Config.BLOCK_SIZE * Config.MAP_SIZE / 2) + Config.BLOCK_SIZE;
    private static final int START_SPAWN_POINT_Y_V2 = START_SPAWN_POINT_Y_V1 + 11 * Config.BLOCK_SIZE;

    //Red Player
    public static final Point2D SPAWN_POINT_RED_V1 = new Point2D(START_SPAWN_POINT_X_V1, START_SPAWN_POINT_Y_V1);
    public static final Point2D SPAWN_POINT_RED_V2 = new Point2D(START_SPAWN_POINT_X_V1, START_SPAWN_POINT_Y_V1 + 2 * Config.BLOCK_SIZE);
    public static final Point2D SPAWN_POINT_RED_V3 = new Point2D(START_SPAWN_POINT_X_V1 + 2 * Config.BLOCK_SIZE, START_SPAWN_POINT_Y_V1 + 2 * Config.BLOCK_SIZE);
    public static final Point2D SPAWN_POINT_RED_V4 = new Point2D(START_SPAWN_POINT_X_V1 + 2 * Config.BLOCK_SIZE, START_SPAWN_POINT_Y_V1);
    public static final List<Point2D> SPAWN_POINTS_RED = List.of(SPAWN_POINT_RED_V1, SPAWN_POINT_RED_V2, SPAWN_POINT_RED_V3, SPAWN_POINT_RED_V4);
    public static final Image RED_IDLE_IMG = FXGL.getAssetLoader().loadImage("player/1 Pink_Monster/Pink_Monster_Idle_7.png");
    public static final Image RED_RUN_IMG = FXGL.getAssetLoader().loadImage("player/1 Pink_Monster/Pink_Monster_Run_6.png");

    //Blue Player
    public static final Point2D SPAWN_POINT_BLUE_V1 = new Point2D(START_SPAWN_POINT_X_V2 + Config.BLOCK_SIZE, START_SPAWN_POINT_Y_V1);
    public static final Point2D SPAWN_POINT_BLUE_V2 = new Point2D(START_SPAWN_POINT_X_V2 + Config.BLOCK_SIZE, START_SPAWN_POINT_Y_V1 + 2 * Config.BLOCK_SIZE);
    public static final Point2D SPAWN_POINT_BLUE_V3 = new Point2D(START_SPAWN_POINT_X_V2 - Config.BLOCK_SIZE, START_SPAWN_POINT_Y_V1 + 2 * Config.BLOCK_SIZE);
    public static final Point2D SPAWN_POINT_BLUE_V4 = new Point2D(START_SPAWN_POINT_X_V2 - Config.BLOCK_SIZE, START_SPAWN_POINT_Y_V1);
    public static final List<Point2D> SPAWN_POINTS_BLUE = List.of(SPAWN_POINT_BLUE_V1, SPAWN_POINT_BLUE_V2, SPAWN_POINT_BLUE_V3, SPAWN_POINT_BLUE_V4);
    public static final Image BLUE_IDLE_IMG = FXGL.getAssetLoader().loadImage("player/3 Dude_Monster/Dude_Monster_Idle_7.png");
    public static final Image BLUE_RUN_IMG = FXGL.getAssetLoader().loadImage("player/3 Dude_Monster/Dude_Monster_Run_6.png");

    //Yellow Player
    public static final Point2D SPAWN_POINT_YELLOW_V1 = new Point2D(START_SPAWN_POINT_X_V2 + Config.BLOCK_SIZE, START_SPAWN_POINT_Y_V2 - Config.BLOCK_SIZE);
    public static final Point2D SPAWN_POINT_YELLOW_V2 = new Point2D(START_SPAWN_POINT_X_V2 + Config.BLOCK_SIZE, START_SPAWN_POINT_Y_V2 + Config.BLOCK_SIZE);
    public static final Point2D SPAWN_POINT_YELLOW_V3 = new Point2D(START_SPAWN_POINT_X_V2 - Config.BLOCK_SIZE, START_SPAWN_POINT_Y_V2 + Config.BLOCK_SIZE);
    public static final Point2D SPAWN_POINT_YELLOW_V4 = new Point2D(START_SPAWN_POINT_X_V2 - Config.BLOCK_SIZE, START_SPAWN_POINT_Y_V2 - Config.BLOCK_SIZE);
    public static final List<Point2D> SPAWN_POINTS_YELLOW = List.of(SPAWN_POINT_YELLOW_V1, SPAWN_POINT_YELLOW_V2, SPAWN_POINT_YELLOW_V3, SPAWN_POINT_YELLOW_V4);
    public static final Image YELLOW_IDLE_IMG = FXGL.getAssetLoader().loadImage("player/2 Owlet_Monster/Owlet_Monster_Idle_7.png");
    public static final Image YELLOW_RUN_IMG = FXGL.getAssetLoader().loadImage("player/2 Owlet_Monster/Owlet_Monster_Run_6.png");

    //Green Player
    public static final Point2D SPAWN_POINT_GREEN_V1 = new Point2D(START_SPAWN_POINT_X_V1, START_SPAWN_POINT_Y_V2 - Config.BLOCK_SIZE);
    public static final Point2D SPAWN_POINT_GREEN_V2 = new Point2D(START_SPAWN_POINT_X_V1, START_SPAWN_POINT_Y_V2 + Config.BLOCK_SIZE);
    public static final Point2D SPAWN_POINT_GREEN_V3 = new Point2D(START_SPAWN_POINT_X_V1 + 2 * Config.BLOCK_SIZE, START_SPAWN_POINT_Y_V2 + Config.BLOCK_SIZE);
    public static final Point2D SPAWN_POINT_GREEN_V4 = new Point2D(START_SPAWN_POINT_X_V1 + 2 * Config.BLOCK_SIZE, START_SPAWN_POINT_Y_V2 - Config.BLOCK_SIZE);
    public static final List<Point2D> SPAWN_POINTS_GREEN = List.of(SPAWN_POINT_GREEN_V1, SPAWN_POINT_GREEN_V2, SPAWN_POINT_GREEN_V3, SPAWN_POINT_GREEN_V4);
    public static final Image GREEN_IDLE_IMG = FXGL.getAssetLoader().loadImage("player/3 Dude_Monster/Dude_Monster_Idle_7.png");
    public static final Image GREEN_RUN_IMG = FXGL.getAssetLoader().loadImage("player/3 Dude_Monster/Dude_Monster_Run_6.png");

    // START BUTTON
    public static final String START_BUTTON_DEFAULT = "menu/texture/start-button_default.png";
    public static final String START_BUTTON_HOVER = "menu/texture/start-button_hover.png";
    public static final String START_BUTTON_CLICK = "menu/texture/start-button_click.png";

    // EXIT BUTTON
    public static final String EXIT_BUTTON_DEFAULT = "menu/texture/exit-button_default.png";
    public static final String EXIT_BUTTON_HOVER = "menu/texture/exit-button_hover.png";
    public static final String EXIT_BUTTON_CLICK = "menu/texture/exit-button_click.png";

    // RULES BUTTON
    public static final String RULES_BUTTON_DEFAULT = "menu/texture/rules-button_default.png";
    public static final String RULES_BUTTON_HOVER = "menu/texture/rules-button_hover.png";
    public static final String RULES_BUTTON_CLICK = "menu/texture/rules-button_click.png";

    // MUSIC BUTTON
    public static final String MUSIC_BUTTON_DEFAULT = "menu/texture/music-button_default.png";
    public static final String MUSIC_BUTTON_HOVER = "menu/texture/music-button_hover.png";
    public static final String MUSIC_BUTTON_CLICK = "menu/texture/music-button_click.png";

    public static final String TEXT_BOX = "menu/texture/textbox.png";
    public static final String TEXT_BOX_ERROR = "menu/texture/textbox-error.png";

    public static final String PLAYER_NAME_TEXT = "menu/texture/player-name-text.png";
    public static final String SERVER_ADDRESS_TEXT = "menu/texture/server-address-text.png";

    public static final String ERROR_TEXT_TOO_LONG = "Maksymalna długość to %s znaków";
    public static final String WARNING_TEXT_TOO_SHORT = "Minimalna długość to %s znaków";
    public static final String ERROR_NOT_ALLOWED_CHAR = "Tekst zawiera niedozwolone znaki";
    public static final String EMPTY_TEXT_FIELD = "Pole nie może być puste";
}
