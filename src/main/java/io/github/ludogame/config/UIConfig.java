package io.github.ludogame.config;

import com.almasb.fxgl.dsl.FXGL;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

import java.util.List;

public final class UIConfig {

    public static final int BOARD_WIDTH = 500;
    public static final int BOARD_START_LAYOUT_X = ((Config.MAP_WIDTH / 2) - (BOARD_WIDTH / 2));
    public static final int BOARD_START_LAYOUT_Y = ((Config.MAP_HEIGHT / 2) - (BOARD_WIDTH / 2));
    public static final String TITLE_TEXT = "menu/texture/title.png";
    //STARTS POINT
//    private static final int START_SPAWN_POINT_X_V1 = (Config.MAP_WIDTH / 2) - (Config.BLOCK_SIZE * Config.MAP_SIZE / 2) + Config.BLOCK_SIZE;
//    private static final int START_SPAWN_POINT_X_V2 = START_SPAWN_POINT_X_V1 + 11 * Config.BLOCK_SIZE;
//    private static final int START_SPAWN_POINT_Y_V1 = (Config.MAP_HEIGHT / 2) - (Config.BLOCK_SIZE * Config.MAP_SIZE / 2) + Config.BLOCK_SIZE;
//    private static final int START_SPAWN_POINT_Y_V2 = START_SPAWN_POINT_Y_V1 + 11 * Config.BLOCK_SIZE;

    //Blue Player
    public static final Point2D SPAWN_POINT_CELL_BLUE_V1 = new Point2D(10, 4);
    public static final Point2D SPAWN_POINT_CELL_BLUE_V2 = new Point2D(12, 4);
    public static final Point2D SPAWN_POINT_CELL_BLUE_V3 = new Point2D(10, 6);
    public static final Point2D SPAWN_POINT_CELL_BLUE_V4 = new Point2D(12, 6);
    public static final Point2D SPAWN_POINT_BLUE_V1 = new Point2D((int)SPAWN_POINT_CELL_BLUE_V1.getX() * Config.BLOCK_SIZE + Config.BLOCK_SIZE/2, (int)SPAWN_POINT_CELL_BLUE_V1.getY() * Config.BLOCK_SIZE + Config.BLOCK_SIZE/2);
    public static final Point2D SPAWN_POINT_BLUE_V2 = new Point2D((int)SPAWN_POINT_CELL_BLUE_V2.getX() * Config.BLOCK_SIZE + Config.BLOCK_SIZE/2, (int)SPAWN_POINT_CELL_BLUE_V2.getY() * Config.BLOCK_SIZE + Config.BLOCK_SIZE/2);
    public static final Point2D SPAWN_POINT_BLUE_V3 = new Point2D((int)SPAWN_POINT_CELL_BLUE_V3.getX() * Config.BLOCK_SIZE + Config.BLOCK_SIZE/2, (int)SPAWN_POINT_CELL_BLUE_V3.getY() * Config.BLOCK_SIZE + Config.BLOCK_SIZE/2);
    public static final Point2D SPAWN_POINT_BLUE_V4 = new Point2D((int)SPAWN_POINT_CELL_BLUE_V4.getX() * Config.BLOCK_SIZE + Config.BLOCK_SIZE/2, (int)SPAWN_POINT_CELL_BLUE_V4.getY() * Config.BLOCK_SIZE + Config.BLOCK_SIZE/2);
    public static final List<Point2D> SPAWN_POINTS_BLUE = List.of(SPAWN_POINT_BLUE_V1, SPAWN_POINT_BLUE_V2, SPAWN_POINT_BLUE_V3, SPAWN_POINT_BLUE_V4);
    public static final List<Point2D> SPAWN_POINTS_CELL_BLUE = List.of(SPAWN_POINT_CELL_BLUE_V1, SPAWN_POINT_CELL_BLUE_V2, SPAWN_POINT_CELL_BLUE_V3, SPAWN_POINT_CELL_BLUE_V4);


    public static final Image RED_IDLE_IMG = FXGL.getAssetLoader().loadImage("player/1 Pink_Monster/Pink_Monster_Idle_7.png");
    public static final Image RED_RUN_IMG = FXGL.getAssetLoader().loadImage("player/1 Pink_Monster/Pink_Monster_Run_6.png");
    public static final Image RED_JUMP_IMG = FXGL.getAssetLoader().loadImage("player/1 Pink_Monster/Pink_Monster_Jump_8.png");
    public static final Image RED_DEATH_IMG = FXGL.getAssetLoader().loadImage("player/1 Pink_Monster/Pink_Monster_Death_8.png");

    //Green Player
    public static final Point2D SPAWN_POINT_CELL_GREEN_V1 = new Point2D(10, 14);
    public static final Point2D SPAWN_POINT_CELL_GREEN_V2 = new Point2D(12, 14);
    public static final Point2D SPAWN_POINT_CELL_GREEN_V3 = new Point2D(10, 16);
    public static final Point2D SPAWN_POINT_CELL_GREEN_V4 = new Point2D(12, 16);
    public static final Point2D SPAWN_POINT_GREEN_V1 = new Point2D((int)SPAWN_POINT_CELL_GREEN_V1.getX() * Config.BLOCK_SIZE + Config.BLOCK_SIZE/2, (int)SPAWN_POINT_CELL_GREEN_V1.getY() * Config.BLOCK_SIZE + Config.BLOCK_SIZE/2);
    public static final Point2D SPAWN_POINT_GREEN_V2 = new Point2D((int)SPAWN_POINT_CELL_GREEN_V2.getX() * Config.BLOCK_SIZE + Config.BLOCK_SIZE/2, (int)SPAWN_POINT_CELL_GREEN_V2.getY() * Config.BLOCK_SIZE + Config.BLOCK_SIZE/2);
    public static final Point2D SPAWN_POINT_GREEN_V3 = new Point2D((int)SPAWN_POINT_CELL_GREEN_V3.getX() * Config.BLOCK_SIZE + Config.BLOCK_SIZE/2, (int)SPAWN_POINT_CELL_GREEN_V3.getY() * Config.BLOCK_SIZE + Config.BLOCK_SIZE/2);
    public static final Point2D SPAWN_POINT_GREEN_V4 = new Point2D((int)SPAWN_POINT_CELL_GREEN_V4.getX() * Config.BLOCK_SIZE + Config.BLOCK_SIZE/2, (int)SPAWN_POINT_CELL_GREEN_V4.getY() * Config.BLOCK_SIZE + Config.BLOCK_SIZE/2);
    public static final List<Point2D> SPAWN_POINTS_GREEN= List.of(SPAWN_POINT_GREEN_V1, SPAWN_POINT_GREEN_V2, SPAWN_POINT_GREEN_V3, SPAWN_POINT_GREEN_V4);

    public static final List<Point2D> SPAWN_POINTS_CELL_GREEN = List.of(SPAWN_POINT_CELL_GREEN_V1, SPAWN_POINT_CELL_GREEN_V2, SPAWN_POINT_CELL_GREEN_V3, SPAWN_POINT_CELL_GREEN_V4);

    public static final Image BLUE_IDLE_IMG = FXGL.getAssetLoader().loadImage("player/3 Dude_Monster/Dude_Monster_Idle_7.png");
    public static final Image BLUE_RUN_IMG = FXGL.getAssetLoader().loadImage("player/3 Dude_Monster/Dude_Monster_Run_6.png");
    public static final Image BLUE_JUMP_IMG = FXGL.getAssetLoader().loadImage("player/3 Dude_Monster/Dude_Monster_Jump_8.png");
    public static final Image BLUE_DEATH_IMG = FXGL.getAssetLoader().loadImage("player/3 Dude_Monster/Dude_Monster_Death_8.png");


    //Red Player
    public static final Point2D SPAWN_POINT_CELL_RED_V1 = new Point2D(20, 14);
    public static final Point2D SPAWN_POINT_CELL_RED_V2 = new Point2D(22, 14);
    public static final Point2D SPAWN_POINT_CELL_RED_V3 = new Point2D(20, 16);
    public static final Point2D SPAWN_POINT_CELL_RED_V4 = new Point2D(22, 16);
    public static final Point2D SPAWN_POINT_RED_V1 = new Point2D((int)SPAWN_POINT_CELL_RED_V1.getX() * Config.BLOCK_SIZE + Config.BLOCK_SIZE/2, (int)SPAWN_POINT_CELL_RED_V1.getY() * Config.BLOCK_SIZE + Config.BLOCK_SIZE/2);
    public static final Point2D SPAWN_POINT_RED_V2 = new Point2D((int)SPAWN_POINT_CELL_RED_V2.getX() * Config.BLOCK_SIZE + Config.BLOCK_SIZE/2, (int)SPAWN_POINT_CELL_RED_V2.getY() * Config.BLOCK_SIZE + Config.BLOCK_SIZE/2);
    public static final Point2D SPAWN_POINT_RED_V3 = new Point2D((int)SPAWN_POINT_CELL_RED_V3.getX() * Config.BLOCK_SIZE + Config.BLOCK_SIZE/2, (int)SPAWN_POINT_CELL_RED_V3.getY() * Config.BLOCK_SIZE + Config.BLOCK_SIZE/2);
    public static final Point2D SPAWN_POINT_RED_V4 = new Point2D((int)SPAWN_POINT_CELL_RED_V4.getX() * Config.BLOCK_SIZE + Config.BLOCK_SIZE/2, (int)SPAWN_POINT_CELL_RED_V4.getY() * Config.BLOCK_SIZE + Config.BLOCK_SIZE/2);
    public static final List<Point2D> SPAWN_POINTS_RED = List.of(SPAWN_POINT_RED_V1, SPAWN_POINT_RED_V2, SPAWN_POINT_RED_V3, SPAWN_POINT_RED_V4);

    public static final List<Point2D> SPAWN_POINTS_CELL_RED = List.of(SPAWN_POINT_CELL_RED_V1, SPAWN_POINT_CELL_RED_V2, SPAWN_POINT_CELL_RED_V3, SPAWN_POINT_CELL_RED_V4);


    public static final Image YELLOW_IDLE_IMG = FXGL.getAssetLoader().loadImage("player/2 Owlet_Monster/Owlet_Monster_Idle_7.png");
    public static final Image YELLOW_RUN_IMG = FXGL.getAssetLoader().loadImage("player/2 Owlet_Monster/Owlet_Monster_Run_6.png");
    public static final Image YELLOW_JUMP_IMG = FXGL.getAssetLoader().loadImage("player/2 Owlet_Monster/Owlet_Monster_Jump_8.png");
    public static final Image YELLOW_DEATH_IMG = FXGL.getAssetLoader().loadImage("player/2 Owlet_Monster/Owlet_Monster_Death_8.png");

    //Yellow Player
    public static final Point2D SPAWN_POINT_CELL_YELLOW_V1 = new Point2D(20, 4);
    public static final Point2D SPAWN_POINT_CELL_YELLOW_V2 = new Point2D(22, 4);
    public static final Point2D SPAWN_POINT_CELL_YELLOW_V3 = new Point2D(20, 6);
    public static final Point2D SPAWN_POINT_CELL_YELLOW_V4 = new Point2D(22, 6);
    public static final Point2D SPAWN_POINT_YELLOW_V1 = new Point2D((int)SPAWN_POINT_CELL_YELLOW_V1.getX() * Config.BLOCK_SIZE + Config.BLOCK_SIZE/2, (int)SPAWN_POINT_CELL_YELLOW_V1.getY() * Config.BLOCK_SIZE + Config.BLOCK_SIZE/2);
    public static final Point2D SPAWN_POINT_YELLOW_V2 = new Point2D((int)SPAWN_POINT_CELL_YELLOW_V2.getX() * Config.BLOCK_SIZE + Config.BLOCK_SIZE/2, (int)SPAWN_POINT_CELL_YELLOW_V2.getY() * Config.BLOCK_SIZE + Config.BLOCK_SIZE/2);
    public static final Point2D SPAWN_POINT_YELLOW_V3 = new Point2D((int)SPAWN_POINT_CELL_YELLOW_V3.getX() * Config.BLOCK_SIZE + Config.BLOCK_SIZE/2, (int)SPAWN_POINT_CELL_YELLOW_V3.getY() * Config.BLOCK_SIZE + Config.BLOCK_SIZE/2);
    public static final Point2D SPAWN_POINT_YELLOW_V4 = new Point2D((int)SPAWN_POINT_CELL_YELLOW_V4.getX() * Config.BLOCK_SIZE + Config.BLOCK_SIZE/2, (int)SPAWN_POINT_CELL_YELLOW_V4.getY() * Config.BLOCK_SIZE + Config.BLOCK_SIZE/2);
    public static final List<Point2D> SPAWN_POINTS_YELLOW = List.of(SPAWN_POINT_YELLOW_V1, SPAWN_POINT_YELLOW_V2, SPAWN_POINT_YELLOW_V3, SPAWN_POINT_YELLOW_V4);
    public static final List<Point2D> SPAWN_POINTS_CELL_YELLOW = List.of(SPAWN_POINT_CELL_YELLOW_V1, SPAWN_POINT_CELL_YELLOW_V2, SPAWN_POINT_CELL_YELLOW_V3, SPAWN_POINT_CELL_YELLOW_V4);


    public static final Image GREEN_IDLE_IMG = FXGL.getAssetLoader().loadImage("player/3 Dude_Monster/Dude_Monster_Idle_7.png");
    public static final Image GREEN_RUN_IMG = FXGL.getAssetLoader().loadImage("player/3 Dude_Monster/Dude_Monster_Run_6.png");
    public static final Image GREEN_JUMP_IMG = FXGL.getAssetLoader().loadImage("player/3 Dude_Monster/Dude_Monster_Jump_8.png");
    public static final Image GREEN_DEATH_IMG = FXGL.getAssetLoader().loadImage("player/3 Dude_Monster/Dude_Monster_Death_8.png");

    // START BUTTON
    public static final String START_BUTTON_DEFAULT = "menu/texture/start-button_default.png";
    public static final String START_BUTTON_HOVER = "menu/texture/start-button_hover.png";
    public static final String START_BUTTON_CLICK = "menu/texture/start-button_click.png";

    // CONNECT BUTTON
    public static final String CONNECT_BUTTON_DEFAULT = "menu/texture/connect-button_default.png";
    public static final String CONNECT_BUTTON_HOVER = "menu/texture/connect-button_hover.png";
    public static final String CONNECT_BUTTON_CLICK = "menu/texture/connect-button_click.png";

    // READY BUTTON
    public static final String READY_BUTTON_DEFAULT = "menu/texture/ready-button_default.png";
    public static final String READY_BUTTON_HOVER = "menu/texture/ready-button_hover.png";
    public static final String READY_BUTTON_CLICK = "menu/texture/ready-button_click.png";

    // THROW BUTTON
    public static final String THROW_BUTTON_DEFAULT = "menu/texture/throw-button_default.png";
    public static final String THROW_BUTTON_HOVER = "menu/texture/throw-button_hover.png";
    public static final String THROW_BUTTON_CLICK = "menu/texture/throw-button_click.png";

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
