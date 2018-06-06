package com.mygdx.game;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class Worldcreater {
    public Array<Gooba> goobas;
    public Worldcreater(PlayScreen screen){
        World world=screen.getWorld();
        TiledMap map=screen.getMap();

        BodyDef bodyDef=new BodyDef();
        PolygonShape shape=new PolygonShape();
        FixtureDef fixtureDef=new FixtureDef();
        Body body;



        for(MapObject object:map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
            bodyDef.type= BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rectangle.getX()+rectangle.getWidth()/2)/MyGdxGame.ppm,(rectangle.getY()+rectangle.getHeight()/2)/MyGdxGame.ppm);
            body=world.createBody(bodyDef);
            shape.setAsBox(rectangle.getWidth()/2/MyGdxGame.ppm,rectangle.getHeight()/2/MyGdxGame.ppm);
            fixtureDef.shape=shape;
            body.createFixture(fixtureDef);

        }

        for(MapObject object:map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
            bodyDef.type= BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rectangle.getX()+rectangle.getWidth()/2)/MyGdxGame.ppm,(rectangle.getY()+rectangle.getHeight()/2)/MyGdxGame.ppm);
            body=world.createBody(bodyDef);
            shape.setAsBox(rectangle.getWidth()/2/MyGdxGame.ppm,rectangle.getHeight()/2/MyGdxGame.ppm);
            fixtureDef.shape=shape;
            fixtureDef.filter.categoryBits=MyGdxGame.objectbit;
            body.createFixture(fixtureDef);

        }

        for(MapObject object:map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            new Coin(screen,object);

        }

        for(MapObject object:map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            new Bricks(screen,object);

        }

        goobas=new Array<Gooba>();
        for(MapObject object:map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
            goobas.add(new Gooba(screen,rectangle.getX()/MyGdxGame.ppm,rectangle.getY()/MyGdxGame.ppm));

        }

    }

    public Array<Gooba> getGoobas() {
        return goobas;
    }
}
