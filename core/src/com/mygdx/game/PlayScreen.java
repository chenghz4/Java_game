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
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

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

    public PlayScreen(MyGdxGame game){

        this.game=game;
        camera=new OrthographicCamera();
        viewport=new StretchViewport(MyGdxGame.width/MyGdxGame.ppm,MyGdxGame.height/MyGdxGame.ppm,camera);
        hud=new Hud(game.batch);
        mapLoader=new TmxMapLoader();
        map=mapLoader.load("level1.tmx");
        renderer=new OrthogonalTiledMapRenderer(map,1/MyGdxGame.ppm);
        camera.position.set(viewport.getWorldWidth()/2,viewport.getWorldHeight()/2,0);
        world=new World(new Vector2(0,-15),true);
        box2DDebugRenderer=new Box2DDebugRenderer();
        new Worldcreater(world,map);
        atlas=new TextureAtlas("mario_item.pack");
        player=new Mario(world,this);

    }
    @Override
    public void show() {

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
        game.batch.end();

       game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

    }

    public void input(float dt){
        if(Gdx.input.isTouched()){
        camera.position.x=camera.position.x+100*dt;


        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.W)&&player.body2.getLinearVelocity().x<=1.5){

            player.body2.applyLinearImpulse(new Vector2(0,4f),player.body2.getWorldCenter(),true);
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.D)&& player.body2.getLinearVelocity().x<=2){

            player.body2.applyLinearImpulse(new Vector2(1f,0),player.body2.getWorldCenter(),true);
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.A)&& player.body2.getLinearVelocity().x>=-2){

            player.body2.applyLinearImpulse(new Vector2(-1f,0),player.body2.getWorldCenter(),true);
        }



    }
    public TextureAtlas getAtlas(){

        return atlas;
    }


    public void update(float dt){
        input(dt);

        world.step(1/60f,6,2);
        player.update(dt);
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
