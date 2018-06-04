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

public class Worldcreater {

    public Worldcreater(World world, TiledMap map){

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
            body.createFixture(fixtureDef);

        }

        for(MapObject object:map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
            new Coin(world,map,rectangle);

        }

        for(MapObject object:map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
            new Bricks(world,map,rectangle);

        }


    }
}
