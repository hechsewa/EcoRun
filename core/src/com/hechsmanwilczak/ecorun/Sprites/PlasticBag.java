package com.hechsmanwilczak.ecorun.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.hechsmanwilczak.ecorun.EcoRun;
import com.hechsmanwilczak.ecorun.Scenes.Hud;
import com.hechsmanwilczak.ecorun.Screens.PlayScreen;

public class PlasticBag extends Enemy {

    private float stateTime;
    private int stepCount;
    private Animation walkAnimation;
    private Array<TextureRegion> frames;
    private boolean setToDestroy;
    private boolean destroyed;

    public PlasticBag(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        frames = new Array<TextureRegion>();
        for (int i = 0; i < 2; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("bag"), i * 16, 1, 16, 16));
        walkAnimation = new Animation(0.4f, frames);
        stateTime = 0;
        stepCount = 0;
        setBounds(getX(), getY(), 16 / EcoRun.PPM, 16 / EcoRun.PPM);
        setToDestroy = false;
        destroyed = false;
    }

    public void update(float dt) {
        stateTime += dt;
        stepCount += 1;
        if (setToDestroy && !destroyed) {
            world.destroyBody(b2body);
            destroyed = true;
            setRegion(new TextureRegion(screen.getAtlas().findRegion("bag"), 32, 1, 16, 16));
            stateTime = 0;
        } else if (!destroyed) {
            b2body.setLinearVelocity(velocity);
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            setRegion((TextureRegion) walkAnimation.getKeyFrame(stateTime, true));
            float vel = b2body.getPosition().x - getWidth() / 2;
            if (stepCount % 50 == 0) { //co 50 ramke zmiana kierunku
                this.reverseVelocity(true, false);
            }
        }
    }


    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(8f / EcoRun.PPM, 1f / EcoRun.PPM);
        fdef.filter.categoryBits = EcoRun.ENEMY_BIT;
        fdef.filter.maskBits = EcoRun.GROUND_BIT |
                EcoRun.PORTAL_BIT |
                EcoRun.ENEMY_BIT |
                EcoRun.EARTH_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

        PolygonShape head = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(-2, 4).scl(1 / EcoRun.PPM);
        vertice[1] = new Vector2(2, 4).scl(1 / EcoRun.PPM);
        vertice[2] = new Vector2(-2, 3).scl(1 / EcoRun.PPM);
        vertice[3] = new Vector2(2, 3).scl(1 / EcoRun.PPM);
        head.set(vertice);


        fdef.shape = head;
        fdef.restitution = 0.5f;
        fdef.filter.categoryBits = EcoRun.ENEMY_HEAD_BIT;
        b2body.createFixture(fdef).setUserData(this);
    }

    public void draw(Batch batch) {
        if (!destroyed || stateTime < 1)
            super.draw(batch);
    }

    @Override
    public void hitOnHead() {
        setToDestroy = true;
        Hud.addScore(10);
    }
}
