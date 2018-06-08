package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

public class Bricks extends Interact {
    public Bricks(PlayScreen screen , MapObject object){
        super(screen, object);
        fixture.setUserData(this);
        setCfiter(MyGdxGame.bbit);
    }

    @Override
    public void onHead(Mario mario) {
        setCfiter(MyGdxGame.desbit);
        getcell().setTile(null);
      //  Hud.addscore(200);
    }

}
