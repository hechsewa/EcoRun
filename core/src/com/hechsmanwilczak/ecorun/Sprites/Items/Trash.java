package com.hechsmanwilczak.ecorun.Sprites.Items;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.hechsmanwilczak.ecorun.EcoRun;
import com.hechsmanwilczak.ecorun.Scenes.Hud;
import com.hechsmanwilczak.ecorun.Screens.PlayScreen;
import com.hechsmanwilczak.ecorun.Sprites.Earth;

public class Trash extends Item {

    public Trash(PlayScreen screen, float x, float y, Integer category){
        super(screen, x, y, category);
        setBounds(getX(), getY(), 16/EcoRun.PPM, 16/ EcoRun.PPM);
        if(category == 1)
            setRegion(screen.getAtlas().findRegion("items"), 32,1,16,16);
        else if (category == 2)
            setRegion(screen.getAtlas().findRegion("items"), 48,1,16,16);
        else if (category == 0)
            setRegion(screen.getAtlas().findRegion("items"), 64,1,16,16);
        else
            setRegion(screen.getAtlas().findRegion("items"), 80, 1, 16, 16);

    }

    @Override
    public void defineItem(Integer category) {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(2f / EcoRun.PPM);
        shape.setPosition(new Vector2(8f / EcoRun.PPM ,8f / EcoRun.PPM));
        if (category == 1)
            fdef.filter.categoryBits = EcoRun.METAL_BIT;
        else if (category == 0)
            fdef.filter.categoryBits = EcoRun.PLASTIC_BIT;
        else if (category == 2)
            fdef.filter.categoryBits = EcoRun.PAPER_BIT;
        else
            fdef.filter.categoryBits = EcoRun.SMOG_MASK_BIT;

        fdef.isSensor = true;
        fdef.density = 8.0f;
        fdef.filter.maskBits = EcoRun.GROUND_BIT |
                EcoRun.PORTAL_BIT |
                EcoRun.ENEMY_BIT  |
                EcoRun.EARTH_BIT;

        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);

    }

    @Override
    public void onCollision(Integer category) {
        if (category == 0)
            Hud.addPlastic();
        else if(category == 1) {
            Hud.addMetal();
        }
        else if (category == 2)
            Hud.addPaper();
        else {
            Earth.inMask = true;
            Hud.showDialog(0,0);
        }
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
