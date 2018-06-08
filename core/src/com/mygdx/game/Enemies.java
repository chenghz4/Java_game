package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Enemies extends Sprite {
    protected World world;
    protected PlayScreen screen;
    public Body body2;
    public Vector2 speed;


    Enemies(PlayScreen screen,float x,float y){

        this.world=screen.getWorld();
        this.screen=screen;
        setPosition(x,y);
        speed=new Vector2(1,-1);
        define();
        body2.setActive(false);
    }
    protected abstract void define();
    public abstract void update(float dt);
    public abstract void onhead();


    public void reverse(boolean x, boolean y){
        if(x)
            speed.x=-speed.x;
        if(y)
             speed.y=-speed.y;


    }


}
