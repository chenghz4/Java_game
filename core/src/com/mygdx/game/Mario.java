package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.g2d.Animation;
import java.lang.Object;
public class Mario extends Sprite {
    public enum State{
        Falling,Jumping,Running,Standing,Growing,Dead,FireGrow

    }
    private   Animation<TextureRegion> marioRun;
   // private   Animation<TextureRegion> mariojump;
    private TextureRegion mariojump;
    public State currentstate;
    public State previousState;
    private float statetimer;
    private boolean isrunning;
    public World world;
    public Body body2;
    public TextureRegion mariostand;
    public TextureRegion bigmariostand;
    public TextureRegion bigmario_jump;
    public TextureRegion mariodead;
    private   Animation<TextureRegion> bigmarioRun;
    private   Animation<TextureRegion> growbigmario;
    public TextureRegion firemariostand;
    public TextureRegion firemario_jump;
    private   Animation<TextureRegion> firemarioRun;
    private   Animation<TextureRegion> growfiremario;
    public boolean isbig;
    private boolean isrungrow;
    private boolean timetodefinebig;
    public boolean isfire;
    private boolean isrunfiregrow;
    private boolean timetodefinefire;
    private boolean timetoredefine;
    private boolean timetoredefinefire;
    private boolean isdead;
    private PlayScreen screen;
    private Array<Fireball> fireballs;

    public Mario(PlayScreen screen){
        this.screen=screen;
        this.world=screen.getWorld();
        currentstate=State.Standing;
        previousState=State.Standing;
        statetimer=0;
        isrunning=true;
        Array<TextureRegion> frames=new Array<TextureRegion>();

        frames.add(new TextureRegion(screen.getAtlas().findRegion("mario_walk"),28,3,14,100));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("mario_walk"),44,3,14,100));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("mario_walk"),60,3,14,100));
        marioRun=new Animation(0.1f,frames);
        frames.clear();


        frames.add(new TextureRegion(screen.getAtlas().findRegion("super_walk"),28,3,16,100));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("super_walk"),44,3,16,100));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("super_walk"),60,3,16,100));
        bigmarioRun=new Animation(0.1f,frames);
        frames.clear();

        frames.add(new TextureRegion(screen.getAtlas().findRegion("mario_walk"),28,3,14,100));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("super_walk"),44,3,16,100));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("mario_walk"),28,3,14,100));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("super_walk"),44,3,16,100));
        growbigmario=new Animation(0.2f,frames);
        frames.clear();

        frames.add(new TextureRegion(screen.getAtlas4().findRegion("superfire_walk"),26,3,16,100));
        frames.add(new TextureRegion(screen.getAtlas4().findRegion("superfire_walk"),42,3,16,100));
        frames.add(new TextureRegion(screen.getAtlas4().findRegion("superfire_walk"),58,3,16,100));
        firemarioRun=new Animation(0.1f,frames);
        frames.clear();

        frames.add(new TextureRegion(screen.getAtlas().findRegion("mario_walk"),28,3,14,100));
        frames.add(new TextureRegion(screen.getAtlas4().findRegion("superfire_walk"),42,3,16,100));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("mario_walk"),28,3,14,100));
        frames.add(new TextureRegion(screen.getAtlas4().findRegion("superfire_walk"),42,3,16,100));
        growfiremario=new Animation(0.2f,frames);
        frames.clear();





        mariostand=new TextureRegion(screen.getAtlas().findRegion("mario_walk"),28,3,14,100);
        mariojump=new TextureRegion(screen.getAtlas().findRegion("mario_jump"),44,3,14,100);
        bigmariostand=new TextureRegion(screen.getAtlas().findRegion("super_walk"),44,3,16,100);
        bigmario_jump=new TextureRegion(screen.getAtlas().findRegion("super_jump"),42,3,16,100);
        mariodead=new TextureRegion(screen.getAtlas().findRegion("mario_jump"),60,3,14,100);
        firemario_jump=new TextureRegion(screen.getAtlas4().findRegion("superfire_jump"),42,3,16,100);
        firemariostand=new TextureRegion(screen.getAtlas4().findRegion("superfire_walk"),42,3,16,100);



        define();

        setBounds(0,3,14/MyGdxGame.ppm,100/MyGdxGame.ppm);
        setRegion(mariostand);
        fireballs=new Array<Fireball>();

    }

    public void update(float dt){
        if(isbig||isfire)
            setPosition((float) (body2.getPosition().x-getWidth()/2),(float) (body2.getPosition().y-getHeight()/2-6/MyGdxGame.ppm));
       else
           setPosition((float) (body2.getPosition().x-getWidth()/2),(float) (body2.getPosition().y-getHeight()/2));


        setRegion(getframe(dt));
        if(timetodefinebig)
            bigdefine();
        if(timetodefinefire)
            firedefine();
        if(timetoredefine)
            redefine();
        if(timetoredefinefire)
            redefinefire();
        if(body2.getPosition().y<0){
            Hud.update_life();
            if(Hud.life<=0){
                isdead=true;
                Hud.life=0;
                Filter filter=new Filter();
                filter.maskBits=MyGdxGame.nobit;
                for(Fixture fixture:body2.getFixtureList())
                    fixture.setFilterData(filter);
                body2.applyLinearImpulse(new Vector2(0,4f),body2.getWorldCenter(),true);


            }
        }
        for(Fireball  ball : fireballs) {
            ball.update(dt);
            if(ball.isDestroyed())
                fireballs.removeValue(ball, true);
        }


    }

    public void grow(){
        if (!isfire) {
            isrungrow = true;
            isbig = true;
            timetodefinebig = true;
            setBounds(getX(), getY(), getWidth(), getHeight());
        }
    }

    public void firegrow(){
        if(!isbig) {
            isrunfiregrow = true;
            isfire = true;
            timetodefinefire = true;
            setBounds(getX(), getY(), getWidth(), getHeight());
        }
    }

    public void hit(){
        if(isbig) {
            isbig = false;
            timetoredefine= true;
            setBounds(getX(),getY(),getWidth(),getHeight());
        }
        else if(isfire){
            isfire=false;
            timetoredefinefire= true;
            setBounds(getX(),getY(),getWidth(),getHeight());

        }

        else Hud.update_life();

        if(Hud.life<=0){
            isdead=true;
            Filter filter=new Filter();
            filter.maskBits=MyGdxGame.nobit;
            for(Fixture fixture:body2.getFixtureList())
                fixture.setFilterData(filter);
            body2.applyLinearImpulse(new Vector2(0,4f),body2.getWorldCenter(),true);


        }

    }

    @Override
    public void draw(Batch batch){
        super.draw(batch);
            for (Fireball ball : fireballs){
                if(!ball.destroyed&&ball.ready)
                ball.draw(batch);
        }
    }

    public boolean mariodead(){
        return isdead;


    }
    public float getStatetimer(){

        return statetimer;
    }

    public void redefine(){

        Vector2 postion=body2.getPosition();
        world.destroyBody(body2);
        BodyDef bodyDef=new BodyDef();
        bodyDef.position.set(postion);
        bodyDef.type=BodyDef.BodyType.DynamicBody;
        body2=world.createBody(bodyDef);

        FixtureDef fixtureDef=new FixtureDef();
        CircleShape shape=new CircleShape();
        shape.setRadius(6/MyGdxGame.ppm);
        fixtureDef.filter.categoryBits=MyGdxGame.mbit;
        fixtureDef.filter.maskBits=MyGdxGame.debit|MyGdxGame.cbit|MyGdxGame.bbit|MyGdxGame.enbit|MyGdxGame.objectbit|MyGdxGame.enheadbit|MyGdxGame.ibit|MyGdxGame.fbit;
        fixtureDef.shape=shape;
        body2.createFixture(fixtureDef).setUserData(this);


        EdgeShape head=new EdgeShape();
        head.set(new Vector2(-2/MyGdxGame.ppm,8/MyGdxGame.ppm),new Vector2(2/MyGdxGame.ppm,8/MyGdxGame.ppm));
        fixtureDef.filter.categoryBits = MyGdxGame.mhbit;
        fixtureDef.shape=head;
        fixtureDef.isSensor=true;
        body2.createFixture(fixtureDef).setUserData(this);

        timetoredefine=false;


    }

    public void redefinefire(){

        Vector2 postion=body2.getPosition();
        world.destroyBody(body2);
        BodyDef bodyDef=new BodyDef();
        bodyDef.position.set(postion);
        bodyDef.type=BodyDef.BodyType.DynamicBody;
        body2=world.createBody(bodyDef);

        FixtureDef fixtureDef=new FixtureDef();
        CircleShape shape=new CircleShape();
        shape.setRadius(6/MyGdxGame.ppm);
        fixtureDef.filter.categoryBits=MyGdxGame.mbit;
        fixtureDef.filter.maskBits=MyGdxGame.debit|MyGdxGame.cbit|MyGdxGame.bbit|MyGdxGame.enbit|MyGdxGame.objectbit|MyGdxGame.enheadbit|MyGdxGame.ibit|MyGdxGame.fbit;
        fixtureDef.shape=shape;
        body2.createFixture(fixtureDef).setUserData(this);


        EdgeShape head=new EdgeShape();
        head.set(new Vector2(-2/MyGdxGame.ppm,8/MyGdxGame.ppm),new Vector2(2/MyGdxGame.ppm,8/MyGdxGame.ppm));
        fixtureDef.filter.categoryBits = MyGdxGame.mhbit;
        fixtureDef.shape=head;
        fixtureDef.isSensor=true;
        body2.createFixture(fixtureDef).setUserData(this);
        timetoredefinefire=false;



    }
    public void fire(){
        fireballs.add(new Fireball(screen, body2.getPosition().x+5/MyGdxGame.ppm, body2.getPosition().y, isrunning? true:false));
    }

    public void bigdefine(){

        Vector2 current=body2.getPosition();
        world.destroyBody(body2);

        BodyDef bodyDef=new BodyDef();
        bodyDef.position.set(current.add(0,10/MyGdxGame.ppm));
        bodyDef.type=BodyDef.BodyType.DynamicBody;
        body2=world.createBody(bodyDef);

        FixtureDef fixtureDef=new FixtureDef();
        CircleShape shape=new CircleShape();
        shape.setRadius(6/MyGdxGame.ppm);
        fixtureDef.filter.categoryBits=MyGdxGame.mbit;
        fixtureDef.filter.maskBits=MyGdxGame.debit|MyGdxGame.cbit|MyGdxGame.bbit|MyGdxGame.enbit|MyGdxGame.objectbit|MyGdxGame.enheadbit|MyGdxGame.ibit|MyGdxGame.fbit;
        fixtureDef.shape=shape;
        body2.createFixture(fixtureDef).setUserData(this);
        shape.setPosition(new Vector2(0,-14/MyGdxGame.ppm));
        body2.createFixture(fixtureDef).setUserData(this);

        EdgeShape head=new EdgeShape();
        head.set(new Vector2(-2/MyGdxGame.ppm,8/MyGdxGame.ppm),new Vector2(2/MyGdxGame.ppm,8/MyGdxGame.ppm));
        fixtureDef.filter.categoryBits = MyGdxGame.mhbit;
        fixtureDef.shape=head;
        fixtureDef.isSensor=true;
        body2.createFixture(fixtureDef).setUserData(this);

        timetodefinebig=false;

    }

    public void firedefine(){

        Vector2 current=body2.getPosition();
        world.destroyBody(body2);

        BodyDef bodyDef=new BodyDef();
        bodyDef.position.set(current.add(0,10/MyGdxGame.ppm));
        bodyDef.type=BodyDef.BodyType.DynamicBody;
        body2=world.createBody(bodyDef);

        FixtureDef fixtureDef=new FixtureDef();
        CircleShape shape=new CircleShape();
        shape.setRadius(6/MyGdxGame.ppm);
        fixtureDef.filter.categoryBits=MyGdxGame.mbit;
        fixtureDef.filter.maskBits=MyGdxGame.debit|MyGdxGame.cbit|MyGdxGame.bbit|MyGdxGame.enbit|MyGdxGame.objectbit|MyGdxGame.enheadbit|MyGdxGame.ibit|MyGdxGame.fbit;
        fixtureDef.shape=shape;
        body2.createFixture(fixtureDef).setUserData(this);
        shape.setPosition(new Vector2(0,-14/MyGdxGame.ppm));
        body2.createFixture(fixtureDef).setUserData(this);

        EdgeShape head=new EdgeShape();
        head.set(new Vector2(-2/MyGdxGame.ppm,8/MyGdxGame.ppm),new Vector2(2/MyGdxGame.ppm,8/MyGdxGame.ppm));
        fixtureDef.filter.categoryBits = MyGdxGame.mhbit;
        fixtureDef.shape=head;
        fixtureDef.isSensor=true;
        body2.createFixture(fixtureDef).setUserData(this);

        timetodefinefire=false;

    }




    public TextureRegion getframe(float dt) {
       currentstate=getState();
       TextureRegion region;
       switch (currentstate){
           case Dead:
               region=mariodead;
               break;

           case Growing:
               region=growbigmario.getKeyFrame(statetimer,true);
               if(growbigmario.isAnimationFinished(statetimer))
                   isrungrow=false;
               break;

           case FireGrow:
               region=growfiremario.getKeyFrame(statetimer,true);
               if(growfiremario.isAnimationFinished(statetimer))
                   isrunfiregrow=false;
               break;

           case Jumping:
               if(isbig) region=bigmario_jump;
               else if(isfire) region=firemario_jump;
               else region=mariojump;
               break;
           case Running:
               if(isbig)
                   region= bigmarioRun.getKeyFrame(statetimer,true);
               else if(isfire)
                   region= firemarioRun.getKeyFrame(statetimer,true);
               else
                   region= marioRun.getKeyFrame(statetimer,true);
               break;
           case Standing:
           case Falling:

               default:
                   if(isbig) region=bigmariostand;
                   else if(isfire) region=firemariostand;
                   else region=mariostand;
               break;
       }

       if((body2.getLinearVelocity().x<0||!isrunning)&& !region.isFlipX()){
            region.flip(true,false);
            isrunning=false;

        }

        else if ((body2.getLinearVelocity().x>0||isrunning)&&region.isFlipX()){
            region.flip(true,false);
           isrunning=true;

       }

       if(currentstate==previousState){

           statetimer=statetimer+dt;

       }
       if(currentstate!=previousState)  statetimer=0;

        previousState=currentstate;
       return region;
    }

    public State getState(){
        if(isdead)
            return State.Dead;
        else if(isrunfiregrow)
            return State.FireGrow;
        else if(isrungrow)
            return State.Growing;
        else if(body2.getLinearVelocity().y>0||(body2.getLinearVelocity().y<0&&previousState==State.Jumping))
            return State.Jumping;
        else if(body2.getLinearVelocity().y<0)
            return State.Falling;
        else if(body2.getLinearVelocity().x!=0)
            return State.Running;
        else  return State.Standing;


    }

    public void define(){
        BodyDef bodyDef=new BodyDef();
        bodyDef.position.set(32/MyGdxGame.ppm,32/MyGdxGame.ppm);
        bodyDef.type=BodyDef.BodyType.DynamicBody;
        body2=world.createBody(bodyDef);

        FixtureDef fixtureDef=new FixtureDef();
        CircleShape shape=new CircleShape();
        shape.setRadius(6/MyGdxGame.ppm);
        fixtureDef.filter.categoryBits=MyGdxGame.mbit;
        fixtureDef.filter.maskBits=MyGdxGame.debit|MyGdxGame.cbit|MyGdxGame.bbit|MyGdxGame.enbit|MyGdxGame.objectbit|MyGdxGame.enheadbit|MyGdxGame.ibit|MyGdxGame.fbit;
        fixtureDef.shape=shape;
        body2.createFixture(fixtureDef).setUserData(this);


        EdgeShape head=new EdgeShape();
        head.set(new Vector2(-2/MyGdxGame.ppm,8/MyGdxGame.ppm),new Vector2(2/MyGdxGame.ppm,8/MyGdxGame.ppm));
        fixtureDef.filter.categoryBits = MyGdxGame.mhbit;
        fixtureDef.shape=head;
        fixtureDef.isSensor=true;
        body2.createFixture(fixtureDef).setUserData(this);

    }
}
