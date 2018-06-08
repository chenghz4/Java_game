package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;


public class Ghost extends Enemies {
    private Animation<TextureRegion> walk;
    private Array<TextureRegion> frames;
    private float statetime;
    Ghost(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        frames=new Array<TextureRegion>();
        statetime=0;
        for(int i=0;i<2;i++)
            frames.add(new TextureRegion(screen.getAtlas2().findRegion("ghost"),0,0,15,25));
        walk=new Animation(0.4f,frames);
        setBounds(getX(),getY(),15/MyGdxGame.ppm,25/MyGdxGame.ppm);
    }

    @Override
    protected void define() {
        BodyDef bodyDef=new BodyDef();
        bodyDef.position.set(getX(),getY());
        bodyDef.type=BodyDef.BodyType.DynamicBody;
        body2=screen.getWorld().createBody(bodyDef);

        FixtureDef fixtureDef=new FixtureDef();
        CircleShape shape=new CircleShape();
        shape.setRadius(8/MyGdxGame.ppm);
        fixtureDef.filter.categoryBits=MyGdxGame.enbit;
        fixtureDef.filter.maskBits=MyGdxGame.debit|MyGdxGame.cbit|MyGdxGame.bbit|MyGdxGame.objectbit|MyGdxGame.enbit|MyGdxGame.mbit;


        fixtureDef.shape=shape;
        body2.createFixture(fixtureDef).setUserData(this);
    }

    @Override
    public void update(float dt) {
        TextureRegion region;
        region=walk.getKeyFrame(statetime,true);
        statetime=statetime+dt;
        body2.setLinearVelocity(speed);
        setPosition((float) (body2.getPosition().x - getWidth() / 2), (float) (body2.getPosition().y - getHeight() / 2 ));
        setRegion(walk.getKeyFrame(statetime, true));
    }

    @Override
    public void onhead() {

    }

    @Override
    public void reverse(boolean x, boolean y) {
        super.reverse(x, y);
    }
}
