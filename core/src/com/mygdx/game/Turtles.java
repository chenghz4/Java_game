package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;

public class Turtles extends Enemies {
    private float statetime;
    private boolean running;
    private Animation<TextureRegion> walkt;
    private TextureRegion shellt;
    private Array<TextureRegion> frames;
    private boolean setd;
    private boolean destroyed;
    public enum State{shell,walk};
    private State currentstate;
    private State previousstate;

    public Turtles(PlayScreen screen, float x, float y) {

        super(screen, x, y);
        frames=new Array<TextureRegion>();
        for(int i=0;i<2;i++)
            frames.add(new TextureRegion(screen.getAtlas2().findRegion("turtle"),i*16,0,16,22));
        walkt=new Animation(0.2f,frames);
        shellt=new TextureRegion(screen.getAtlas2().findRegion("turtle_shell"),14,1,16,16);
        statetime=0;
        destroyed=false;
        setd=false;
        running=true;
        currentstate=State.walk;
        previousstate=State.walk;

        setBounds(getX(),getY(),16/MyGdxGame.ppm,16/MyGdxGame.ppm);
    }

    @Override
    protected void define() {
        BodyDef bodyDef=new BodyDef();
        bodyDef.position.set(getX(),getY());
        bodyDef.type=BodyDef.BodyType.DynamicBody;
        body2=screen.getWorld().createBody(bodyDef);

        FixtureDef fixtureDef=new FixtureDef();
        CircleShape shape=new CircleShape();
        shape.setRadius(7/MyGdxGame.ppm);
        fixtureDef.filter.categoryBits=MyGdxGame.enbit;
        fixtureDef.filter.maskBits=MyGdxGame.debit|MyGdxGame.cbit|MyGdxGame.bbit|MyGdxGame.objectbit|MyGdxGame.enbit|MyGdxGame.mbit|MyGdxGame.firebit;


        fixtureDef.shape=shape;
        body2.createFixture(fixtureDef).setUserData(this);

        PolygonShape head=new PolygonShape();
        Vector2[] vector2s=new Vector2[4];
        vector2s[0]=new Vector2(-7,8).scl(1/MyGdxGame.ppm);
        vector2s[1]=new Vector2(7,8).scl(1/MyGdxGame.ppm);
        vector2s[2]=new Vector2(-3,3).scl(1/MyGdxGame.ppm);
        vector2s[3]=new Vector2(3,3).scl(1/MyGdxGame.ppm);
        head.set(vector2s);


        fixtureDef.shape=head;
        fixtureDef.restitution=0.5f;
        fixtureDef.filter.categoryBits=MyGdxGame.enheadbit;
        body2.createFixture(fixtureDef).setUserData(this);

    }

     public  TextureRegion getframe(float dt){
         TextureRegion region;
         switch (currentstate){
             case shell:
                 region=shellt;
                 break;
             case walk:
                 default:
             region=walkt.getKeyFrame(statetime);
             break;
         }
         if((body2.getLinearVelocity().x>0||running)&& !region.isFlipX()) {
             region.flip(true, false);
             running=false;
         }


         if ((body2.getLinearVelocity().x < 0||running) && region.isFlipX()) {
             region.flip(true, false);
             running=true;
         }

         if(currentstate==previousstate)
             statetime=statetime+dt;
         if(currentstate!=previousstate)
             statetime=0;
         previousstate=currentstate;

        return  region;
     }

    @Override
    public void update(float dt) {
        setRegion(getframe(dt));
        if(currentstate==State.shell&&statetime>5){
            currentstate=State.walk;
            speed.x=1;

        }
        setPosition(body2.getPosition().x - getWidth() / 2, body2.getPosition().y-8/MyGdxGame.ppm);
        body2.setLinearVelocity(speed);




    }


    @Override
    public void onhead() {
        if(currentstate!=State.shell){
            currentstate=State.shell;
            speed.x=0;


        }

    }
}
