package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import java.lang.Object;
public class Listener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA=contact.getFixtureA();
        Fixture fixB=contact.getFixtureB();
        int Def=fixA.getFilterData().categoryBits|fixB.getFilterData().categoryBits;
        Fixture head;
        Fixture object;
        if(fixA.getUserData()=="head"||fixB.getUserData()=="head"){
            if(fixA.getUserData()=="head") {
                 head=fixA;
                object=fixB;
            }

            else {
                head=fixB;
                object=fixA;
            }

            if(object.getUserData()!=null&&Interact.class.isAssignableFrom(object.getUserData().getClass())){
             ((Interact) object.getUserData()).onHead();
            }

        }


        switch (Def){
            case MyGdxGame.enheadbit|MyGdxGame.mbit:
                if(fixA.getFilterData().categoryBits==MyGdxGame.enheadbit)
                    ((Enemies)fixA.getUserData()).onhead();
                else if(fixB.getFilterData().categoryBits==MyGdxGame.enheadbit)
                    ((Enemies)fixB.getUserData()).onhead();
                break;
            case MyGdxGame.enbit|MyGdxGame.objectbit:
                if(fixA.getFilterData().categoryBits==MyGdxGame.enbit)
                    ((Enemies)fixA.getUserData()).reverse(true,false);
                else if(fixB.getFilterData().categoryBits==MyGdxGame.enbit)
                    ((Enemies)fixB.getUserData()).reverse(true,false);
                break;
            case MyGdxGame.enbit|MyGdxGame.bbit:
                if(fixA.getFilterData().categoryBits==MyGdxGame.enbit)
                    ((Enemies)fixA.getUserData()).reverse(true,false);
                else if(fixB.getFilterData().categoryBits==MyGdxGame.enbit)
                    ((Enemies)fixB.getUserData()).reverse(true,false);
                break;
            case MyGdxGame.mbit|MyGdxGame.enbit:
                Hud.update_life();
                break;
            case MyGdxGame.enbit:
                    ((Enemies)fixA.getUserData()).reverse(true,false);
                ((Enemies)fixB.getUserData()).reverse(true,false);
                break;
            case MyGdxGame.objectbit|MyGdxGame.ibit:
                if(fixA.getFilterData().categoryBits==MyGdxGame.ibit)
                    ((Item)fixA.getUserData()).reverse(true,false);
                else if(fixB.getFilterData().categoryBits==MyGdxGame.ibit)
                    ((Item)fixB.getUserData()).reverse(true,false);
                break;
            case MyGdxGame.mbit|MyGdxGame.ibit:
                Hud.addscore(1000);
                if(fixA.getFilterData().categoryBits==MyGdxGame.ibit)
                    ((Item)fixA.getUserData()).use((Mario) fixB.getUserData());
                else if(fixB.getFilterData().categoryBits==MyGdxGame.ibit)
                    ((Item)fixB.getUserData()).use((Mario)fixA.getUserData());
                break;

                default:break;


        }

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
