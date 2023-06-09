package io.github.ludogame.component;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import io.github.ludogame.pawn.PawnColor;
import javafx.util.Duration;

public class AnimationComponent extends Component {

    private final AnimatedTexture animatedTexture;
    private final AnimationChannel animChannelRun, animChannelIdle;

    public AnimationComponent(PawnColor pawnColor) {
        this.animChannelIdle = new AnimationChannel(pawnColor.idleImage, 7, 32, 32, Duration.seconds(2), 1, 6);
        this.animChannelRun = new AnimationChannel(pawnColor.runImage, 6, 32, 32, Duration.seconds(1), 1, 5);
        this.animatedTexture = new AnimatedTexture(animChannelIdle);
        animatedTexture.loop();
    }

    public void setAnimatedTextureRun() {
        if (entity == null) {
            return;
        }

        animatedTexture.loopAnimationChannel(animChannelRun);
    }

    public void setAnimatedTextureIdle() {
        if (entity == null) {
            return;
        }

        animatedTexture.loopAnimationChannel(animChannelIdle);
    }

    public void switchAnimation() {
        animatedTexture.loopAnimationChannel(animatedTexture.getAnimationChannel() == animChannelRun ? animChannelIdle : animChannelRun);
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(animatedTexture);
    }
}
