package com.hechsmanwilczak.ecorun.Sprites.Items;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.hechsmanwilczak.ecorun.Screens.PlayScreen;

public abstract class Item extends Sprite {
    protected PlayScreen screen;
    protected World world;
    protected boolean toDestroy;
    protected boolean destroyed;
    protected Body body;

    public Item(PlayScreen screen, float x, float y, Integer cat){
        this.screen = screen;
        this.world = screen.getWorld();
        setPosition(x,y);
        defineItem(cat);
        destroyed = false;
        toDestroy = false;
    }

    public abstract void defineItem(Integer category);
    public abstract void onCollision(Integer category);

    public void update(float dt){
        if(toDestroy && !destroyed){
            world.destroyBody(body);
            destroyed = true;
        }
    }

    public void destroy() {
        toDestroy = true;
    }
    public void draw(Batch batch){
        if(!destroyed){
            super.draw(batch);
        }
    }
}
