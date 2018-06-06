package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Item extends Sprite{

    protected PlayScreen screen;
    protected World world;
    protected Vector2 speed;
    protected boolean destroyed;
    protected boolean todes;
    protected Body body;



    public Item(PlayScreen screen,float x,float y){
        this.screen=screen;
        this.world=screen.getWorld();


        setPosition(x,y);
        setBounds(getX(),getY(),16/MyGdxGame.ppm,16/MyGdxGame.ppm);
        define();
        destroyed=false;
        todes=false;
    }
    public abstract void define();
    public abstract void use(Mario mario);

    public void update(float dt){

        if(todes&&!destroyed) {
            world.destroyBody(body);
            destroyed=true;
        }
    }

    @Override
    public void draw(Batch batch){

        if(!destroyed)
            super.draw(batch);


    }

    public void des(){

        todes=true;
    }

    public void reverse(boolean x, boolean y){
        if(x)
            speed.x=-speed.x;
        if(y)
            speed.y=-speed.y;


    }
}
