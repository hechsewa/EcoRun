package com.hechsmanwilczak.ecorun.Sprites;

import com.badlogic.gdx.math.Rectangle;
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
        Hud.zeroLives();
        setCategoryFilter(EcoRun.TOUCHED_WATER_BIT);
    }
}
