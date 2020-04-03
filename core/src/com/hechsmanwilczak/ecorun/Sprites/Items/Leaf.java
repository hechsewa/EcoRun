package com.hechsmanwilczak.ecorun.Sprites.Items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.hechsmanwilczak.ecorun.EcoRun;
import com.hechsmanwilczak.ecorun.Scenes.Hud;
import com.hechsmanwilczak.ecorun.Screens.PlayScreen;

public class Leaf extends Item {
    public Leaf(PlayScreen screen, float x, float y){
        super(screen, x, y);
        setBounds(getX(), getY(), 16/EcoRun.PPM, 16/ EcoRun.PPM);
        setRegion(screen.getAtlas().findRegion("items"), 0,1,16,16);
    }

    @Override
    public void defineItem() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(4f / EcoRun.PPM);
        shape.setPosition(new Vector2(8f / EcoRun.PPM ,8f / EcoRun.PPM));
        fdef.filter.categoryBits = EcoRun.ITEM_BIT;
        fdef.filter.maskBits = EcoRun.GROUND_BIT |
                EcoRun.PORTAL_BIT |
                EcoRun.ENEMY_BIT  |
                EcoRun.EARTH_BIT;

        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);

    }

    @Override
    public void onCollision() {
        Hud.addScore(1);
        destroy();
    }

    @Override
    public void update(float dt) {
        super.update(dt);
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
