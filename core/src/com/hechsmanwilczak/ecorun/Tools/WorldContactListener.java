package com.hechsmanwilczak.ecorun.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import com.hechsmanwilczak.ecorun.EcoRun;
import com.hechsmanwilczak.ecorun.Scenes.Hud;
import com.hechsmanwilczak.ecorun.Sprites.Bin;
import com.hechsmanwilczak.ecorun.Sprites.Earth;
import com.hechsmanwilczak.ecorun.Sprites.Enemy;
import com.hechsmanwilczak.ecorun.Sprites.InteractiveTileObject;
import com.hechsmanwilczak.ecorun.Sprites.Items.Item;
import com.hechsmanwilczak.ecorun.Sprites.Items.Trash;

public class WorldContactListener implements ContactListener {

    private boolean playerOnGround;

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        if (fixA.getUserData() == "head" || fixB.getUserData() == "head") {
            Fixture head = fixA.getUserData() == "head" ? fixA : fixB;
            Fixture object = head == fixA ? fixB : fixA;

            if (object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())) {
                ((InteractiveTileObject) object.getUserData()).onHeadTouch();
            }
        }

        if (fixA.getUserData() == "body" || fixB.getUserData() == "collision") {
            Fixture body = fixA.getUserData() == "body" ? fixA : fixB;
            Fixture object = body == fixA ? fixB : fixA;
            if (object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())) {
                ((InteractiveTileObject) object.getUserData()).onCollision();
            }
        }

        //zablokowanie podwojnego skoku
        if ((fixA.getUserData() != null && fixA.getUserData().equals("bottom")) ||
        (fixB.getUserData() != null && fixB.getUserData().equals("bottom"))){
            playerOnGround = true;
        }

        switch (cDef) {
            case EcoRun.ENEMY_HEAD_BIT | EcoRun.EARTH_BIT:
                //isEnemyHit = true;
                if (fixA.getFilterData().categoryBits == EcoRun.ENEMY_HEAD_BIT)
                    ((Enemy) fixA.getUserData()).hitOnHead();
                else
                    ((Enemy) fixB.getUserData()).hitOnHead();
                break;
            case EcoRun.EARTH_BIT | EcoRun.WATER_BIT:
                Hud.zeroLives();
                if (fixA.getFilterData().categoryBits == EcoRun.WATER_BIT)
                    ((InteractiveTileObject) fixA.getUserData()).onCollision();
                else
                    ((InteractiveTileObject) fixB.getUserData()).onCollision();
                break;
            case EcoRun.EARTH_BIT | EcoRun.OIL_BIT:
                if (fixA.getFilterData().categoryBits == EcoRun.OIL_BIT)
                    ((InteractiveTileObject) fixA.getUserData()).onCollision();
                else
                    ((InteractiveTileObject) fixB.getUserData()).onCollision();
                break;
            case EcoRun.EARTH_BIT | EcoRun.ENEMY_BIT:
                //if(!isEnemyHit)
                Hud.loseLive();
                Earth.hit = true;
                break;
            case EcoRun.ENEMY_BIT | EcoRun.ENEMY_BIT:
                ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                break;
            case EcoRun.PLASTIC_BIT | EcoRun.EARTH_BIT:
                if (fixA.getFilterData().categoryBits == EcoRun.PLASTIC_BIT)
                    ((Item) fixA.getUserData()).onCollision(0);
                else
                    ((Item) fixB.getUserData()).onCollision(0);
                break;
            case EcoRun.METAL_BIT | EcoRun.EARTH_BIT:
                if (fixA.getFilterData().categoryBits == EcoRun.METAL_BIT)
                    ((Trash) fixA.getUserData()).onCollision(1);
                else
                    ((Trash) fixB.getUserData()).onCollision(1);
                break;
            case EcoRun.PAPER_BIT | EcoRun.EARTH_BIT:
                if (fixA.getFilterData().categoryBits == EcoRun.PAPER_BIT)
                    ((Trash) fixA.getUserData()).onCollision(2);
                 else
                    ((Trash) fixB.getUserData()).onCollision(2);
                break;
            case EcoRun.SMOG_MASK_BIT | EcoRun.EARTH_BIT:
                if (fixA.getFilterData().categoryBits == EcoRun.SMOG_MASK_BIT)
                    ((Item) fixA.getUserData()).onCollision(3);
                else
                    ((Item) fixB.getUserData()).onCollision(3);
                break;
            case EcoRun.ITEM_BIT | EcoRun.EARTH_BIT:
                if (fixA.getFilterData().categoryBits == EcoRun.ITEM_BIT)
                    ((Item) fixA.getUserData()).onCollision(3);
                else
                    ((Item) fixB.getUserData()).onCollision(3);
                break;
            case EcoRun.SMOG_BIT | EcoRun.EARTH_BIT:
                if (fixA.getFilterData().categoryBits == EcoRun.SMOG_BIT)
                    ((InteractiveTileObject) fixA.getUserData()).onCollision();
                else
                    ((InteractiveTileObject) fixB.getUserData()).onCollision();
                break;
            case EcoRun.BIN_BIT | EcoRun.EARTH_BIT:
                Bin bin;
                if (fixA.getFilterData().categoryBits == EcoRun.BIN_BIT) {
                    ((InteractiveTileObject) fixA.getUserData()).onCollision();
                    bin = (Bin) fixA.getUserData();
                }
                else {
                    ((InteractiveTileObject) fixB.getUserData()).onCollision();
                    bin = (Bin) fixB.getUserData();
                }
                Earth.binBounds = bin.getBinBounds();
                Earth.binType = bin.getBinType();
                break;
            case EcoRun.PORTAL_BIT | EcoRun.EARTH_BIT:
                Earth.inPortal = true;
                break;
        }
    }

    public boolean isPlayerOnGround(){
        return playerOnGround;
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        //no longer on the ground
        if ((fixA.getUserData()!= null && fixA.getUserData().equals("bottom"))) {
            playerOnGround = false;
        }
        if (fixB.getUserData() != null && fixB.getUserData().equals("bottom")){
            playerOnGround = false;
        }

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
