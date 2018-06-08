package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;



public class Coin extends Interact {
    private static TiledMapTileSet set;
    private final int BLankcoin=921;
    public Coin(PlayScreen screen, MapObject object){
        super(screen, object);
        set=map.getTileSets().getTileSet("1-1");
        fixture.setUserData(this);
        setCfiter(MyGdxGame.cbit);

    }

    @Override
    public void onHead(Mario mario) {
        setCfiter(MyGdxGame.debit);

        if(getcell().getTile().getId()!=BLankcoin) {
            if(object.getProperties().containsKey("mushroom")) screen.spawnItem(new Itemdef(new Vector2(body.getPosition().x,body.getPosition().y+16/MyGdxGame.ppm),Mushroom.class));
           // Hud.addscore(200);
            else if(object.getProperties().containsKey("flower")) screen.spawnItem(new Itemdef(new Vector2(body.getPosition().x,body.getPosition().y+16/MyGdxGame.ppm),Fireflower.class));
            else  if(object.getProperties().containsKey("coins")) screen.spawnItem(new Itemdef(new Vector2(body.getPosition().x,body.getPosition().y+16/MyGdxGame.ppm),Coins.class));

        }

        getcell().setTile(set.getTile(BLankcoin));
    }
}
