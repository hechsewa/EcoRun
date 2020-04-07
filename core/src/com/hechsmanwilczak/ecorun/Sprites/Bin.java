package com.hechsmanwilczak.ecorun.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.hechsmanwilczak.ecorun.EcoRun;
import com.hechsmanwilczak.ecorun.Scenes.Hud;
import com.hechsmanwilczak.ecorun.Screens.PlayScreen;

public class Bin extends InteractiveTileObject {
    public Bin(PlayScreen screen, Rectangle bounds){
      super(screen, bounds);
      fixture.setUserData(this);
      setCategoryFilter(EcoRun.BIN_BIT);
      fixture.setSensor(true);
    }

    @Override
    public void onHeadTouch() {
        Gdx.app.log("Bin", "head touch");
    }

    @Override
    public void onCollision() {
        Gdx.app.log("Bin", "Collision");
        //setCategoryFilter(EcoRun.IN_BIN_BIT);
    }
}
