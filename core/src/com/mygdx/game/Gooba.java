package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class Gooba extends Enemies {
    private float statetime;
    private boolean running;
    private Animation<TextureRegion> walk;
    private Array<TextureRegion> frames;
    private boolean setd;
    private boolean destroyed;
    Gooba(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        frames=new Array<TextureRegion>();
        for(int i=0;i<2;i++)
           // frames.add(new TextureRegion(screen.getAtlas1().findRegion("mushroom"),0,0,16,16));
             frames.add(new TextureRegion(screen.getAtlas2().findRegion("goba"),i*16,2,16,16));
        walk=new Animation(0.4f,frames);
        statetime=0;
        destroyed=false;
        setd=false;
        running=true;
        //setBounds(getX(),getY(),16/MyGdxGame.ppm,23/MyGdxGame.ppm);
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
        fixtureDef.filter.maskBits=MyGdxGame.debit|MyGdxGame.cbit|MyGdxGame.bbit|MyGdxGame.objectbit|MyGdxGame.enbit|MyGdxGame.mbit;


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

    @Override
    public void onhead() {

        if(!setd)
        Hud.addscore(200);
        setd=true;
    }
    @Override
    public void draw(Batch batch){
        if(!destroyed||statetime<1) super.draw(batch);


    }
    @Override
    public void update(float dt){
        TextureRegion region;
        region=walk.getKeyFrame(statetime,true);
        statetime=statetime+dt;
        if(setd&&!destroyed){
            world.destroyBody(body2);
            destroyed=true;
            statetime=0;
            //setRegion(new TextureRegion(screen.getAtlas1().findRegion("turtle_shell"),14,1,16,21));
             setRegion(new TextureRegion(screen.getAtlas2().findRegion("goba"),32,0,16,17));
            setPosition((float) (body2.getPosition().x - getWidth() / 2), (float) (body2.getPosition().y - getHeight() / 2 ));




        }
        else if(!destroyed) {
            body2.setLinearVelocity(speed);
            setPosition((float) (body2.getPosition().x - getWidth() / 2), (float) (body2.getPosition().y - getHeight() / 2 ));
            setRegion(walk.getKeyFrame(statetime, true));

        }

        if((body2.getLinearVelocity().x>0||running)&& !region.isFlipX()) {
            region.flip(true, false);
            running=false;
        }


        if ((body2.getLinearVelocity().x < 0||running) && region.isFlipX()) {
                region.flip(true, false);
                running=true;
            }

        }

}
