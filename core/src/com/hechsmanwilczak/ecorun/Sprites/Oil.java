package com.hechsmanwilczak.ecorun.Sprites;

import com.badlogic.gdx.math.Rectangle;
import com.hechsmanwilczak.ecorun.EcoRun;
import com.hechsmanwilczak.ecorun.Scenes.Hud;
import com.hechsmanwilczak.ecorun.Screens.PlayScreen;

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
        if (Hud.getScore()-1 >= 0)
            Hud.loseScore(1);
    }
}
