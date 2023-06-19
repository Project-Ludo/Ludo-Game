package io.github.ludogame.player;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public enum PlayerColor {
    BLUE(Color.BLUE),
    RED(Color.RED),
    YELLOW(Color.rgb(204,147,0)),
    GREEN(Color.GREEN);

    public final Paint paint;

    PlayerColor(Paint paint) {
        this.paint = paint;
    }
}