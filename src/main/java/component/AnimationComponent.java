package component;

import type.PlayerType;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.util.Duration;

public class AnimationComponent extends Component {

    private final AnimatedTexture animatedTexture;
    private final AnimationChannel animChannelRun, animChannelStay;

    public AnimationComponent(PlayerType playerType) {
        this.animChannelStay = new AnimationChannel(playerType.stayImage, 6, 32, 32, Duration.seconds(1), 1, 1);
        this.animChannelRun = new AnimationChannel(playerType.runImage, 6, 32, 32, Duration.seconds(1), 1, 5);
        this.animatedTexture = new AnimatedTexture(animChannelStay);
        animatedTexture.loop();
    }

    public void setAnimatedTextureRun() {
        if (entity == null) {
            return;
        }

        animatedTexture.loopAnimationChannel(animChannelRun);
    }

    public void setAnimatedTextureStay() {
        if (entity == null) {
            return;
        }

        animatedTexture.loopAnimationChannel(animChannelStay);
    }

    public void switchAnimation() {
        animatedTexture.loopAnimationChannel(animatedTexture.getAnimationChannel() == animChannelRun ? animChannelStay : animChannelRun);
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(animatedTexture);
    }
}
