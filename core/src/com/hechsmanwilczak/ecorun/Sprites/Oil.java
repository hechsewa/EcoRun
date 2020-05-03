package com.hechsmanwilczak.ecorun.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.hechsmanwilczak.ecorun.EcoRun;
import com.hechsmanwilczak.ecorun.Scenes.Hud;
import com.hechsmanwilczak.ecorun.Screens.PlayScreen;

import java.util.Timer;
import java.util.TimerTask;

public class Oil extends InteractiveTileObject {
    public Oil(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds);
        fixture.setUserData(this);
        setCategoryFilter(EcoRun.OIL_BIT);
    }

    @Override
    public void onHeadTouch() {
    }

    @Override
    public void onCollision() {
        Gdx.app.log("Oil", "collision");
        Hud.loseScore(1);
    }
}
