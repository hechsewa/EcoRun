package com.hechsmanwilczak.ecorun.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.hechsmanwilczak.ecorun.EcoRun;

public class Bin extends InteractiveTileObject {
    public Bin(World world, TiledMap map, Rectangle bounds){
      super(world, map, bounds);
    }
}
