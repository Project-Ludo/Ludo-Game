package io.github.ludogame.component;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import javafx.geometry.Point2D;

public class BalloonComponent extends Component {

    private Point2D velocity;

    @Override
    public void onAdded() {
        double w = FXGL.getAppWidth();
        double h = FXGL.getAppHeight();

        velocity = new Point2D(entity.getX() < w / 2 ? 1 : -1, entity.getY() < h / 2 ? 1 : -1)
                .normalize()
                .multiply(FXGLMath.random(40, 50));
    }

    @Override
    public void onUpdate(double tpf) {
        entity.translate(velocity.multiply(tpf));
    }
}