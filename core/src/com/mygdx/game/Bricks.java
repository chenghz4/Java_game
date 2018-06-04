package com.mygdx.game;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

public class Bricks extends Interact {
    public Bricks(World world, TiledMap map, Rectangle bounds){
        super(world, map, bounds);

    }
}
