package com.hechsmanwilczak.ecorun.Sprites;

import com.badlogic.gdx.math.Rectangle;
import com.hechsmanwilczak.ecorun.EcoRun;
import com.hechsmanwilczak.ecorun.Screens.PlayScreen;

public class Bin extends InteractiveTileObject {
    public String binType;

    public Bin(PlayScreen screen, Rectangle bounds, String type){ //type: RedBin, YellowBin, BlueBin
      super(screen, bounds);
      binType = type;
      fixture.setUserData(this);
      setCategoryFilter(EcoRun.BIN_BIT);
      fixture.setSensor(true);
    }

    public int getBinType(){
        if (binType != null) {
            if (binType.equals("RedBin"))
                return 0;
            else if (binType.equals("YellowBin"))
                return 1;
            else
                return 2;
        }
        return 0;
    }

    public float getBinBounds(){
        return bounds.getX();
    }

    @Override
    public void onHeadTouch() {

    }

    @Override
    public void onCollision() {

    }
}
