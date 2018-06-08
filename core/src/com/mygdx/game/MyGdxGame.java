package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

public class MyGdxGame extends Game {
	public SpriteBatch batch;

	public static final int width=400;
	public static final int height=208;
	public static final float ppm=100;
	public  static final short nobit=0;
    public static final short debit=1;
    public static final short mbit=2;
    public static final short bbit=4;
    public static final short cbit=8;
    public static final short desbit=16;
    public static final short enbit=32;
    public static final short objectbit=64;
    public static final short enheadbit=128;
    public  static final short ibit=256;
	public  static final short mhbit=512;
    public  static final short fbit=1024;
	public  static final short firebit=2048;


	@Override
	public void create () {
		batch = new SpriteBatch();
        setScreen(new Levelscreen(this));

	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();

	}
}
