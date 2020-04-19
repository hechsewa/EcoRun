package com.hechsmanwilczak.ecorun.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.hechsmanwilczak.ecorun.EcoRun;
import com.hechsmanwilczak.ecorun.Scenes.Hud;
import com.hechsmanwilczak.ecorun.Screens.PlayScreen;

public class Smog extends InteractiveTileObject {
    public Smog(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds);
        fixture.setUserData(this);
        setCategoryFilter(EcoRun.SMOG_BIT);
    }

    @Override
    public void onHeadTouch() {
    }

    @Override
    public void onCollision() {
        Gdx.app.log("Smog", "collision");
        if(Earth.inMask){
          fixture.setSensor(true);
        } else {
            Hud.showDialog(2,0);
        }
    }
}