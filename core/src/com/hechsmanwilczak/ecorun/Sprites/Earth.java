package com.hechsmanwilczak.ecorun.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.hechsmanwilczak.ecorun.EcoRun;

public class Earth extends Sprite {
    public World world;
    public Body b2body;

    public Earth(World world){
        this.world = world;
        defineEarth();
    }

    public void defineEarth(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(32 / EcoRun.PPM, 32 / EcoRun.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5 / EcoRun.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef);

    }
}
