package com.hechsmanwilczak.ecorun.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.hechsmanwilczak.ecorun.EcoRun;
import com.hechsmanwilczak.ecorun.Scenes.Hud;
import com.hechsmanwilczak.ecorun.Screens.PlayScreen;

public class Portal extends InteractiveTileObject {
    public Portal(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds);
        fixture.setUserData(this);
        setCategoryFilter(EcoRun.PORTAL_BIT);
        fixture.setSensor(true);
    }

    @Override
    public void onHeadTouch() {
        Gdx.app.log("Portal", "head touch");
        //setCategoryFilter(EcoRun.OPEN_PORTAL_BIT);
        Earth.inPortal = true;
    }

    @Override
    public void onCollision() {
    }
}
