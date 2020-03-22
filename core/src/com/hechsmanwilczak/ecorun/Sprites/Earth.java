package com.hechsmanwilczak.ecorun.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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

    public Earth(World world, PlayScreen screen){
        super(screen.getAtlas().findRegion("earth_left"));
        this.world = world;
        currentState = State.STILL;
        previousState = State.STILL;
        stateTimer = 0;
        runningRight = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        //running animation
        for (int i=1; i<4; i++)
            frames.add(new TextureRegion(getTexture(), i*16+1, 1, 16, 16));
        earthRun = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

        //jumping animation
        frames.add(new TextureRegion(getTexture(), 4*16+1, 1, 16, 16));
        earthJump = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

        defineEarth();
        earthStill = new TextureRegion(getTexture(), 1,1, 16, 16);
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

        fdef.shape = shape;
        b2body.createFixture(fdef);

    }
}
