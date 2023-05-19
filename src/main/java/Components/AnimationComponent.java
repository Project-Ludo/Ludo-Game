package Components;

import EntityTypePackage.EntityType;
import EntityTypePackage.PlayerType;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public class AnimationComponent extends Component {
    private AnimatedTexture animatedTexture;
    private AnimationChannel animChannelRun, animChannelStay;
    public AnimationComponent(PlayerType playerType) {
        initImage(playerType);
        animatedTexture = new AnimatedTexture(animChannelStay);
        animatedTexture.loop();
    }

    public void setAnimatedTextureRun(){
        if(entity == null){
            System.out.println("NULL");
            return;
        }
        animatedTexture.loopAnimationChannel(animChannelRun);
    }

    public void setAnimatedTextureStay(){
        if(entity == null){
            System.out.println("NULL");
            return;
        }
        animatedTexture.loopAnimationChannel(animChannelStay);
    }

    public void changeAnimation(){
        if(animatedTexture.getAnimationChannel() == animChannelRun){
            animatedTexture.loopAnimationChannel(animChannelStay);
        }else{
            animatedTexture.loopAnimationChannel(animChannelRun);
        }
    }

    private void initImage(PlayerType playerType){
        Image imageStay = null, imageRun = null;
        switch (playerType){
            case BLUE_PLAYER:{
                imageStay = FXGL.getAssetLoader().loadImage("player/3 Dude_Monster/Dude_Monster_Attack2_6.png");
                imageRun = FXGL.getAssetLoader().loadImage("player/3 Dude_Monster/Dude_Monster_Run_6.png");
                break;
            }
            case RED_PLAYER:{
                imageStay = FXGL.getAssetLoader().loadImage("player/1 Pink_Monster/Pink_Monster_Attack2_6.png");
                imageRun = FXGL.getAssetLoader().loadImage("player/1 Pink_Monster/Pink_Monster_Run_6.png");
                break;
            }
            case YEllOW_PLAYER:{
                imageStay = FXGL.getAssetLoader().loadImage("player/2 Owlet_Monster/Owlet_Monster_Attack2_6.png");
                imageRun = FXGL.getAssetLoader().loadImage("player/2 Owlet_Monster/Owlet_Monster_Run_6.png");
                break;
            }
            case GREEN_PLAYER:{
                imageStay = new Image("player/3 Dude_Monster/Dude_Monster.png");
                imageRun = new Image("player/3 Dude_Monster/Dude_Monster_Run_6.png");
                break;
            }
            default:
                imageStay = new Image("player/3 Dude_Monster/Dude_Monster.png");
                imageRun = new Image("player/3 Dude_Monster/Dude_Monster_Run_6.png");
        }

        animChannelStay = new AnimationChannel(
                imageStay,
                6,
                32,
                32,
                Duration.seconds(1),
                1,
                1
        );

        animChannelRun = new AnimationChannel(
                imageRun,
                6,
                32,
                32,
                Duration.seconds(1),
                1,
                5
        );
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(animatedTexture);
    }
}
