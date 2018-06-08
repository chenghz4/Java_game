package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class Fireflower extends Item {
    public Fireflower(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        setRegion(screen.getAtlas1().findRegion("fireflower"), 0, 0, 16, 16);
        speed = new Vector2(0, 0);
    }

    @Override
    public void define() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(getX(), getY());
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = screen.getWorld().createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / MyGdxGame.ppm);
        fixtureDef.filter.categoryBits = MyGdxGame.ibit;
        fixtureDef.filter.maskBits = MyGdxGame.debit | MyGdxGame.mbit | MyGdxGame.bbit | MyGdxGame.cbit | MyGdxGame.objectbit;

        fixtureDef.shape = shape;
        body.createFixture(fixtureDef).setUserData(this);
    }

    @Override
    public void use(Mario mario) {
        des();
        Hud.addscore(1000);
        mario.firegrow();
    }

    @Override
    public void update(float dt) {
        if (!destroyed)
            super.update(dt);
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        speed.y = body.getLinearVelocity().y;
        body.setLinearVelocity(speed);
    }

}
