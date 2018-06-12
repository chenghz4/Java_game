package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class PlayScreen implements Screen {
    private MyGdxGame game;
    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Hud hud;
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private Mario player;
    private TextureAtlas atlas;
    private TextureAtlas atlas1;
    private TextureAtlas atlas2;
    private TextureAtlas atlas4;
    private Worldcreater worldcreater;
    private Array<Item> items;
    private float firetimer;
    private LinkedBlockingQueue<Itemdef> itemdefs;
    public  String[] str = {"level1.tmx","level2.tmx","level3.tmx"};
    public static int i=0;
    public PlayScreen(MyGdxGame game){

        this.game=game;
        camera=new OrthographicCamera();
        viewport=new StretchViewport(MyGdxGame.width/MyGdxGame.ppm,MyGdxGame.height/MyGdxGame.ppm,camera);
        hud=new Hud(game.batch);
        mapLoader=new TmxMapLoader();
        map=mapLoader.load(str[i]);
        renderer=new OrthogonalTiledMapRenderer(map,1/MyGdxGame.ppm);
        camera.position.set(viewport.getWorldWidth()/2,viewport.getWorldHeight()/2,0);
        world=new World(new Vector2(0,-10),true);
        box2DDebugRenderer=new Box2DDebugRenderer();

        atlas=new TextureAtlas("mario_item.pack");
        atlas1=new TextureAtlas("items.pack");
        atlas2=new TextureAtlas("enemy1.pack");
        atlas4=new TextureAtlas("fire1.pack");
        player=new Mario(this);
        world.setContactListener(new Listener());
        worldcreater=new Worldcreater(this);


        items=new Array<Item>();
        itemdefs=new LinkedBlockingQueue<Itemdef>();
    }
    @Override
    public void show() {

    }

    public void spawnItem(Itemdef idef){
        itemdefs.add(idef);


    }

    public void handle(){

        if(!itemdefs.isEmpty()){
            Itemdef idef=itemdefs.poll();
            if(idef.type==Mushroom.class) {
                items.add(new Mushroom(this, idef.position.x, idef.position.y));
            }
            else if(idef.type==Fireflower.class) {
                items.add(new Fireflower(this, idef.position.x, idef.position.y));
            }
            else if(idef.type==Coins.class) {
                items.add(new Coins(this, idef.position.x, idef.position.y));
            }
        }
    }
    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.render();
        box2DDebugRenderer.render(world,camera.combined);
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();

        player.draw(game.batch);
        for(Enemies enemies:worldcreater.getenemy())
            enemies.draw(game.batch);
       for(Item item:items)
           item.draw(game.batch);
        game.batch.end();

       game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
        if(gameover()){

            game.setScreen(new Gameover(game));
            dispose();
        }

    }

    public boolean gameover(){
        if(player.currentstate==Mario.State.Dead&&player.getStatetimer()>2&&Hud.life<=0)
            return true;
        else
            return false;


    }

    public void input(float dt){
      firetimer=firetimer+(float)0.1*dt;
      if(player.currentstate!=Mario.State.Dead) {
          if (Gdx.input.isKeyJustPressed(Input.Keys.W) && player.body2.getLinearVelocity().x <= 2) {

              player.body2.applyLinearImpulse(new Vector2(0, 3f), player.body2.getWorldCenter(), true);
          }

          if (Gdx.input.isKeyJustPressed(Input.Keys.D) && player.body2.getLinearVelocity().x <= 2) {

              player.body2.applyLinearImpulse(new Vector2(1f, 0), player.body2.getWorldCenter(), true);
          }

          if (Gdx.input.isKeyJustPressed(Input.Keys.A) && player.body2.getLinearVelocity().x >= -2) {

              player.body2.applyLinearImpulse(new Vector2(-1f, 0), player.body2.getWorldCenter(), true);
          }
          if (Gdx.input.justTouched()&&player.isfire) {
              if(firetimer>8*dt) {
                  player.fire();
                  firetimer = 0;
              }
          }
      }
    }
    public TextureAtlas getAtlas(){

        return atlas;
    }

    public TextureAtlas getAtlas1(){

        return atlas1;
    }
    public TextureAtlas getAtlas2(){

        return atlas2;
    }
    public TextureAtlas getAtlas4(){

        return atlas4;
    }

    public void update(float dt){
        input(dt);
        handle();
        world.step(1/60f,6,2);
        player.update(dt);
        for(Enemies enemies:worldcreater.getenemy()) {

            enemies.update(dt);
            if(enemies.getX()<player.getX()+224/MyGdxGame.ppm)
                enemies.body2.setActive(true);
        }

        for(Item item:items) {
            item.update(dt);

        }
        if(Listener.next) {
            PlayScreen.i++;
            Listener.next=false;
            game.setScreen(new Levelscreen(game));


        }
        if(player.currentstate!=Mario.State.Dead)
        camera.position.x=player.body2.getPosition().x;
        camera.update();
        renderer.setView(camera);


    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);


    }

    @Override
    public void pause() {

    }

    public TiledMap getMap() {
        return map;
    }

    public World getWorld() {
        return world;
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        hud.dispose();
        box2DDebugRenderer.dispose();
    }
}
