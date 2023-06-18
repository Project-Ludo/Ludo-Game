package io.github.ludogame.component;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import io.github.ludogame.pawn.PawnColor;
import javafx.util.Duration;

public class AnimationComponent extends Component {

    private final AnimatedTexture animatedTexture;
    private final AnimationChannel animChannelRun, animChannelIdle, animChannelJump, animChannelDeath;

    private boolean isIdle;

    private boolean isMovingRight;

    public static final int ANIMATION_DURATION = 1;
    private final int ANIMATION_FRAME_SIZE = 32;

    public AnimationComponent(PawnColor pawnColor) {
        this.animChannelIdle = new AnimationChannel(pawnColor.idleImage, 7, ANIMATION_FRAME_SIZE, ANIMATION_FRAME_SIZE, Duration.seconds(ANIMATION_DURATION + 1), 1, 6);
        this.animChannelRun = new AnimationChannel(pawnColor.runImage, 6, ANIMATION_FRAME_SIZE, ANIMATION_FRAME_SIZE, Duration.seconds(ANIMATION_DURATION), 1, 5);
        this.animChannelJump = new AnimationChannel(pawnColor.jumpImage, 8, ANIMATION_FRAME_SIZE, ANIMATION_FRAME_SIZE, Duration.seconds(ANIMATION_DURATION), 1, 7);
        this.animChannelDeath = new AnimationChannel(pawnColor.deathImage, 8, ANIMATION_FRAME_SIZE, ANIMATION_FRAME_SIZE, Duration.seconds(ANIMATION_DURATION), 1, 8);

        this.animatedTexture = new AnimatedTexture(animChannelIdle);
        animatedTexture.loop();
        this.isIdle = true;
        isMovingRight = true;
    }

    public boolean isAnimIdle(){
        return isIdle;
    }

    public void setAnimatedTextureRun() {
        if (entity == null) {
            return;
        }

//        if(isMovingRight()){
//            animatedTexture.setScaleX(1);
//        }else{
//            animatedTexture.setScaleX(-1);
//        }
        animatedTexture.loopAnimationChannel(animChannelRun);
        isIdle= false;
    }

    public void setAnimatedTextureIdle() {
        if (entity == null) {
            return;
        }
//        if(isMovingRight()){
//            animatedTexture.setScaleX(1);
//        }else{
//            animatedTexture.setScaleX(-1);
//        }
        animatedTexture.loopAnimationChannel(animChannelIdle);
        isIdle = true;
    }

    public void setAnimatedTextureJump() {
        if (entity == null) {
            return;
        }

        animatedTexture.loopAnimationChannel(animChannelJump);
        isIdle = false;
    }

    public void setAnimatedTextureDeath() {
        if (entity == null) {
            return;
        }

        animatedTexture.loopAnimationChannel(animChannelDeath);
        isIdle = false;
    }

    public void setOnceTextureJump(){
        //Nie wykonaj sie jeśli nie ma Pawn lub jest jakaś inna animacja od stania
        if (entity == null || !animatedTexture.getAnimationChannel().equals(animChannelIdle)) {
            return;
        }
//        if(isMovingRight()){
//            animatedTexture.setScaleX(1);
//        }else{
//            animatedTexture.setScaleX(-1);
//        }
        animatedTexture.playAnimationChannel(animChannelJump);
        FXGL.runOnce(this::setAnimatedTextureIdle, Duration.seconds(ANIMATION_DURATION));
    }

    public void setOnceTextureDeath(){
        //Nie wykonaj sie jeśli nie ma Pawn lub jest jakaś inna animacja od stania
        if (entity == null || !animatedTexture.getAnimationChannel().equals(animChannelIdle)) {
            return;
        }
//        if(isMovingRight()){
//            animatedTexture.setScaleX(1);
//        }else{
//            animatedTexture.setScaleX(-1);
//        }
        animatedTexture.playAnimationChannel(animChannelDeath);
        FXGL.runOnce(this::setAnimatedTextureIdle, Duration.seconds(ANIMATION_DURATION));
    }

    public void setOnceVictory(){
        if (entity == null || !animatedTexture.getAnimationChannel().equals(animChannelIdle)) {
            return;
        }
//        if(isMovingRight()){
//            animatedTexture.setScaleX(1);
//        }else{
//            animatedTexture.setScaleX(-1);
//        }
        animatedTexture.playAnimationChannel(animChannelDeath);
        FXGL.runOnce(()->this.entity.getViewComponent().setVisible(false), Duration.seconds(ANIMATION_DURATION));
    }

    public void switchAnimation() {
        if (animatedTexture.getAnimationChannel() == animChannelRun) {
            setAnimatedTextureIdle();
        } else {
            setAnimatedTextureRun();
        }
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(animatedTexture);
    }

    public boolean isMovingRight() {
        return isMovingRight;
    }

    public void setMovingRight(boolean movingRight) {
        isMovingRight = movingRight;
    }
}