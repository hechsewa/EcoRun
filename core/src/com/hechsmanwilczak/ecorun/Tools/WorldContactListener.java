package com.hechsmanwilczak.ecorun.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import com.hechsmanwilczak.ecorun.EcoRun;
import com.hechsmanwilczak.ecorun.Sprites.Enemy;
import com.hechsmanwilczak.ecorun.Sprites.InteractiveTileObject;

public class WorldContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        if(fixA.getUserData() == "head" || fixB.getUserData() == "head"){
            Fixture head = fixA.getUserData()  == "head" ? fixA : fixB;
            Fixture object = head == fixA ? fixB : fixA;

            if(object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())){
                ((InteractiveTileObject) object.getUserData()).onHeadTouch();
            }
        }
        switch (cDef){
            case EcoRun.ENEMY_HEAD_BIT | EcoRun.EARTH_BIT :
                if(fixA.getFilterData().categoryBits == EcoRun.ENEMY_HEAD_BIT)
                    ((Enemy)fixA.getUserData()).hitOnHead();
                else
                    ((Enemy)fixB.getUserData()).hitOnHead();
                break;
            /*case EcoRun.ENEMY_RIGHT_BIT | EcoRun.GROUND_BIT :
                if(fixA.getFilterData().categoryBits == EcoRun.ENEMY_RIGHT_BIT)
                    //((Enemy)fixA.getUserData()).reverseVelocity(true, false);
                    Gdx.app.log("tu1","");
                else
                    //((Enemy)fixB.getUserData()).reverseVelocity(true, false);
                    Gdx.app.log("tu1","");
                break;
              */
            case EcoRun.EARTH_BIT | EcoRun.ENEMY_BIT :
                Gdx.app.log("died","");
                break;
            case EcoRun.ENEMY_BIT | EcoRun.ENEMY_BIT:
                ((Enemy)fixA.getUserData()).reverseVelocity(true, false);
                ((Enemy)fixB.getUserData()).reverseVelocity(true, false);
                break;
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
