package com.hechsmanwilczak.ecorun.Sprites;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.hechsmanwilczak.ecorun.EcoRun;
import com.hechsmanwilczak.ecorun.Scenes.Hud;
import com.hechsmanwilczak.ecorun.Screens.PlayScreen;

public class Water extends InteractiveTileObject {
    public Water(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds);
        fixture.setUserData(this);
        setCategoryFilter(EcoRun.WATER_BIT);
    }

    @Override
    public void onHeadTouch() {
    }

    @Override
    public void onCollision() {
        Gdx.app.log("Water", "collision");
        Hud.zeroLives();
        setCategoryFilter(EcoRun.TOUCHED_WATER_BIT);
    }
}
