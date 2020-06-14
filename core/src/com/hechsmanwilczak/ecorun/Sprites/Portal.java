package com.hechsmanwilczak.ecorun.Sprites;

import com.badlogic.gdx.math.Rectangle;
import com.hechsmanwilczak.ecorun.EcoRun;
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
        Earth.inPortal = true;
    }

    @Override
    public void onCollision() {
    }
}
