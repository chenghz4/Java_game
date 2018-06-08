package com.mygdx.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.Color;



public class Hud implements Disposable{
   public Stage stage;
   private Viewport viewport;
   public static int life;
   private float count;
   public static int score;

   static Label scorelabel;
   static Label timelabel;
   Label levellabel;
   Label countlabel;
   Label worldlabel;
   Label mariolabel;

   public Hud(SpriteBatch sb){
       life=3;
       count=0;
       score=0;

       viewport=new FitViewport(MyGdxGame.width,MyGdxGame.height,new OrthographicCamera());
       stage=new Stage(viewport,sb);

       Table table=new Table();
       table.top();
       table.setFillParent(true);
        timelabel=new Label(String.format("%03d",life),new Label.LabelStyle(new BitmapFont(),Color.WHITE));
        scorelabel=new Label(String.format("%06d",score),new Label.LabelStyle(new BitmapFont(),Color.WHITE));
        countlabel=new Label("Life",new Label.LabelStyle(new BitmapFont(),Color.WHITE));
        levellabel=new Label("Level",new Label.LabelStyle(new BitmapFont(),Color.WHITE));
        worldlabel=new Label("World",new Label.LabelStyle(new BitmapFont(),Color.WHITE));
        mariolabel=new Label("Mario",new Label.LabelStyle(new BitmapFont(),Color.WHITE));

        table.add(mariolabel).expandX().padTop(10);
        table.add(worldlabel).expandX().padTop(10);
        table.add(countlabel).expandX().padTop(10);
        table.row();
        table.add(scorelabel).expandX();
        table.add(levellabel).expandX();
        table.add(timelabel).expandX();
        stage.addActor(table);


   }
    public static void update_life(){
        life--;
        timelabel.setText(String.format("%03d",life));
       //d if(life<=0)
          //  while(true);

    }
    public static void addscore(int value){
       score=score+value;
       scorelabel.setText(String.format("%06d",score));


    }
    @Override
    public void dispose() {
        stage.dispose();
    }
}
