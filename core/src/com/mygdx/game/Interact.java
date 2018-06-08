package com.mygdx.game;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Interact {

    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;
    protected Rectangle bounds;
    protected Body body;
    public Fixture fixture;
    protected PlayScreen screen;
    protected MapObject object;
    public Interact(PlayScreen screen, MapObject object){
        this.object=object;
        this.screen=screen;
        this.world=screen.getWorld();
        this.map=screen.getMap();
        this.bounds=((RectangleMapObject) object).getRectangle();
        BodyDef bodyDef=new BodyDef();
        FixtureDef fixtureDef=new FixtureDef();
        PolygonShape shape=new PolygonShape();

        bodyDef.type= BodyDef.BodyType.StaticBody;
        bodyDef.position.set((bounds.getX()+bounds.getWidth()/2)/MyGdxGame.ppm,(bounds.getY()+bounds.getHeight()/2)/MyGdxGame.ppm);
        body=world.createBody(bodyDef);
        shape.setAsBox(bounds.getWidth()/2/MyGdxGame.ppm,bounds.getHeight()/2/MyGdxGame.ppm);
        fixtureDef.shape=shape;
        fixture=body.createFixture(fixtureDef);


    }


    public abstract void onHead(Mario mario);
    public void setCfiter(short fbit){
        Filter filter=new Filter();
        filter.categoryBits=fbit;
        fixture.setFilterData(filter);

    }
    public TiledMapTileLayer.Cell getcell(){
        TiledMapTileLayer layer=(TiledMapTileLayer) map.getLayers().get(1);
        return layer.getCell((int)(body.getPosition().x*MyGdxGame.ppm/16),(int)(body.getPosition().y*MyGdxGame.ppm/16));




    }
}
