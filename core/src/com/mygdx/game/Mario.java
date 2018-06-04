package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.g2d.Animation;
import java.lang.Object;
public class Mario extends Sprite {
    public enum State{
        Falling,Jumping,Runnging,Standing

    }
    private   Animation<TextureRegion> marioRun;
    private   Animation<TextureRegion> mariojump;
    public State currentstate;
    public State previousState;
    private float statetimer;
    private boolean isrunning;

    public World world;
    public Body body2;
    public TextureRegion mariostand;

    public Mario(World world,PlayScreen screen){

        this.world=world;
        currentstate=State.Standing;
        previousState=State.Standing;
        statetimer=0;
        isrunning=true;
        Array<TextureRegion> frames=new Array<TextureRegion>();

        frames.add(new TextureRegion(screen.getAtlas().findRegion("mario_walk"),28,3,14,100));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("mario_walk"),44,3,14,100));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("mario_walk"),60,3,14,100));
        marioRun=new Animation(0.1f,frames);
        frames.clear();


        frames.add(new TextureRegion(screen.getAtlas().findRegion("mario_jump"),28,3,14,100));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("mario_jump"),44,3,14,100));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("mario_jump"),60,3,14,100));
        mariojump=new Animation(0.1f,frames);
        frames.clear();


        mariostand=new TextureRegion(screen.getAtlas().findRegion("mario_walk"),28,3,14,100);
        define();

        setBounds(0,3,45/MyGdxGame.ppm,100/MyGdxGame.ppm);
        setRegion(mariostand);

    }

    public void update(float dt){
        setPosition((float) (body2.getPosition().x-getWidth()/2),(float) (body2.getPosition().y-getHeight()/2));
        setRegion(getframe(dt));

    }

    public TextureRegion getframe(float dt) {
       currentstate=getState();
       TextureRegion region;
       switch (currentstate){
           case Jumping:
               region= mariojump.getKeyFrame(statetimer);
               break;
           case Runnging:
               region= marioRun.getKeyFrame(statetimer,true);
               break;
           case Standing:
           case Falling:

               default: region=mariostand;
               break;
       }

       if((body2.getLinearVelocity().x<0||!isrunning)&& !region.isFlipX()){
            region.flip(true,false);
            isrunning=false;

        }

        else if ((body2.getLinearVelocity().x>0||isrunning)&&region.isFlipX()){
            region.flip(true,false);
           isrunning=true;

       }

       if(currentstate==previousState){

           statetimer=statetimer+dt;

       }
       if(currentstate!=previousState)  statetimer=0;

        previousState=currentstate;
       return region;
    }

    public State getState(){
        if(body2.getLinearVelocity().y>0||(body2.getLinearVelocity().y<0&&previousState==State.Jumping))
            return State.Jumping;
        else if(body2.getLinearVelocity().y<0)
            return State.Falling;
        else if(body2.getLinearVelocity().x!=0)
            return State.Runnging;
        else  return State.Standing;


    }

    public void define(){
        BodyDef bodyDef=new BodyDef();
        bodyDef.position.set(32/MyGdxGame.ppm,32/MyGdxGame.ppm);
        bodyDef.type=BodyDef.BodyType.DynamicBody;
        body2=world.createBody(bodyDef);

        FixtureDef fixtureDef=new FixtureDef();
        CircleShape shape=new CircleShape();
        shape.setRadius(5/MyGdxGame.ppm);

        fixtureDef.shape=shape;
        body2.createFixture(fixtureDef);



    }
}
