package com.hechsmanwilczak.ecorun.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.hechsmanwilczak.ecorun.EcoRun;
import com.hechsmanwilczak.ecorun.Screens.PlayScreen;

public class Earth extends Sprite {
    public enum State {JUMPING, STILL, RUNNING};
    public State currentState;
    public State previousState;
    public World world;
    public Body b2body;
    private TextureRegion earthStill;

    private Animation<TextureRegion> earthRun;
    private Animation<TextureRegion> earthJump;
    private float stateTimer;
    private Boolean runningRight;

    public Earth(PlayScreen screen){
        super(screen.getAtlas().findRegion("earth_left"));
        this.world = screen.getWorld();
        currentState = State.STILL;
        previousState = State.STILL;
        stateTimer = 0;
        runningRight = true;


        Array<TextureRegion> frames = new Array<TextureRegion>();
        //running animation
        for (int i=1; i<4; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("earth_left"), i*16, 0, 16, 16));
        earthRun = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

        //jumping animation
        frames.add(new TextureRegion(screen.getAtlas().findRegion("earth_left"), 4*16, 0, 16, 16));
        earthJump = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

        earthStill = new TextureRegion(screen.getAtlas().findRegion("earth_left"), 0,0, 16, 16);

        defineEarth();
        setBounds(0,0, 16/EcoRun.PPM, 16/EcoRun.PPM);
        setRegion(earthStill);

    }

    public void update(float dt){
        setPosition(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - getHeight()/2);
        setRegion(getFrame(dt));
    }

    public TextureRegion getFrame(float dt) {
        currentState = getState();
        TextureRegion region;
        switch (currentState) {
            case JUMPING:
                region = earthJump.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = earthRun.getKeyFrame(stateTimer, true);
                break;
            case STILL:
            default:
                region = earthStill;
                break;
        }

        //if running to the left and texture is not flipped
        if((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()){
            region.flip(true, false);
            runningRight = false;
        }
        //running to the right but facing left
        else if ((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()){
            region.flip(true, false);
            runningRight = true;
        }

        stateTimer = currentState == previousState ? stateTimer+dt : 0;
        previousState = currentState;
        return region;
    }

    public State getState() {
        if (b2body.getLinearVelocity().y > 0)
            return State.JUMPING;
        else if (b2body.getLinearVelocity().x != 0)
            return State.RUNNING;
        else
            return State.STILL;
    }

    public void defineEarth(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(32 / EcoRun.PPM, 32 / EcoRun.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6.5f / EcoRun.PPM);
        fdef.filter.categoryBits = EcoRun.EARTH_BIT;
        //collides with:
        fdef.filter.maskBits = EcoRun.GROUND_BIT |
                EcoRun.WATER_BIT |
                EcoRun.PORTAL_BIT |
                EcoRun.ENEMY_BIT |
                EcoRun.ENEMY_HEAD_BIT |
                EcoRun.ITEM_BIT |
                EcoRun.BIN_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef);

        //head touch
        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-4 / EcoRun.PPM, 7 / EcoRun.PPM), new Vector2(4 / EcoRun.PPM, 7 / EcoRun.PPM));
        fdef.shape = head;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData("head");

    }
}
