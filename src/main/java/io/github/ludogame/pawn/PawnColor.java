package io.github.ludogame.pawn;

import io.github.ludogame.config.Config;
import io.github.ludogame.config.UIConfig;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

import java.util.List;

public enum PawnColor {
    YEllOW(UIConfig.YELLOW_IDLE_IMG, UIConfig.YELLOW_RUN_IMG, Config.YELLOW_PAWN_START_SPAWN_POINT, Config.YELLOW_PAWN_FINISH_POINT, Config.YELLOW_PATH),
    GREEN(UIConfig.GREEN_IDLE_IMG, UIConfig.GREEN_RUN_IMG, Config.GREEN_PAWN_START_SPAWN_POINT, Config.GREEN_PAWN_FINISH_POINT, Config.GREEN_PATH),
    RED(UIConfig.RED_IDLE_IMG, UIConfig.RED_RUN_IMG, Config.RED_PAWN_START_SPAWN_POINT, Config.RED_PAWN_FINISH_POINT, Config.RED_PATH),
    BLUE(UIConfig.BLUE_IDLE_IMG, UIConfig.BLUE_RUN_IMG, Config.BLUE_PAWN_START_SPAWN_POINT, Config.BLUE_PAWN_FINISH_POINT, Config.BLUE_PATH);

    public final Image idleImage;
    public final Image runImage;
    public final Point2D startPoint;
    public final Point2D finishPoint;
    public final List<Point2D> path;

    PawnColor(Image idleImage, Image runImage, Point2D startPoint, Point2D finishPoint, List<Point2D> path) {
        this.idleImage = idleImage;
        this.runImage = runImage;
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
        this.path = path;
    }
}
