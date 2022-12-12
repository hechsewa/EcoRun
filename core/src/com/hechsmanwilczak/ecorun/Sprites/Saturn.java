package com.hechsmanwilczak.ecorun.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.hechsmanwilczak.ecorun.EcoRun;
import com.hechsmanwilczak.ecorun.Scenes.Hud;
import com.hechsmanwilczak.ecorun.Screens.PlayScreen;

public class Saturn extends Sprite {
    public enum State {JUMPING, STILL, RUNNING, DEAD, HIT};
    public State currentState;
    public State previousState;
    public World world;
    public Body b2body;
    private TextureRegion saturnStill;
    private TextureRegion saturnDead;
    private Animation<TextureRegion> saturnRun;
    private Animation<TextureRegion> saturnJump;
    private Animation<TextureRegion> saturnHit;
    private float stateTimer;
    private Boolean runningRight;
    private Boolean saturnIsDead;
    private PlayScreen screen;
    public static Boolean hit, redBin, yellowBin, blueBin, inPortal, inMask;
    public static float binBounds;
    public static int binType;

    public Saturn(PlayScreen screen){
        super(screen.getAtlas().findRegion("saturn_left"));
        this.world = screen.getWorld();
        this.screen = screen;

        //init variables
        currentState = State.STILL;
        previousState = State.STILL;
        stateTimer = 0;
        runningRight = true;
        saturnIsDead = false;
        hit = false;
        redBin = false;
        yellowBin = false;
        blueBin = false;
        inPortal = false;
        inMask = false;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        //running animation
        for (int i=1; i<4; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("saturn_left"), i*16, 0, 16, 16));
        saturnRun = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

        //jumping animation
        frames.add(new TextureRegion(screen.getAtlas().findRegion("saturn_left"), 4*16, 0, 16, 16));
        saturnJump = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

        saturnStill = new TextureRegion(screen.getAtlas().findRegion("saturn_left"), 0,0, 16, 16);

        saturnDead = new TextureRegion(screen.getAtlas().findRegion("saturn_dead"), 0, 0, 16, 16);

        //hit by enemy animation
        frames.add(saturnDead);
        frames.add(saturnStill);
        frames.add(saturnDead);
        frames.add(saturnStill);
        saturnHit = new Animation<TextureRegion>(0.05f, frames);
        frames.clear();

        defineSaturn();
        setBounds(0,0, 16/EcoRun.PPM, 16/EcoRun.PPM);
        setRegion(saturnStill);

    }

    public void update(float dt) {
        if (screen.getHud().isLifeZero() && !isDead())
            die();
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt));
        //handle hit animation
        if (hit) {
            setRegion(saturnHit.getKeyFrame(stateTimer));
            if (saturnHit.isAnimationFinished(stateTimer))
                hit = false;
            else
                hit = true;
        }
        //handle inside a bin
        checkBin();
        //handle inside a portal
        insidePortal();
    }

    public TextureRegion getFrame(float dt) {
        currentState = getState();
        TextureRegion region;
        switch (currentState) {
            case JUMPING:
                region = saturnJump.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = saturnRun.getKeyFrame(stateTimer, true);
                break;
            case DEAD:
                region = saturnDead;
                break;
            case STILL:
            default:
                region = saturnStill;
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
        if (saturnIsDead)
            return State.DEAD;
        else if (b2body.getLinearVelocity().y > 0)
            return State.JUMPING;
        else if (b2body.getLinearVelocity().x != 0)
            return State.RUNNING;
        else
            return State.STILL;
    }

    public void die(){
        if(!isDead()){
            saturnIsDead = true;
            Filter filter = new Filter();
            filter.maskBits = EcoRun.NOTHING_BIT;
            for(Fixture fixture : b2body.getFixtureList())
                fixture.setFilterData(filter);
            b2body.applyLinearImpulse(new Vector2(0,4f), b2body.getWorldCenter(), true);
        }
    }

    public boolean isDead(){
        return saturnIsDead;
    }

    public float getStateTimer(){
        return stateTimer;
    }

    public void defineSaturn(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(32 / EcoRun.PPM, 32 / EcoRun.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(4f / EcoRun.PPM);
        fdef.filter.categoryBits = EcoRun.EARTH_BIT;
        //collides with:
        fdef.filter.maskBits = EcoRun.GROUND_BIT |
                EcoRun.WATER_BIT |
                EcoRun.PORTAL_BIT |
                EcoRun.ENEMY_BIT |
                EcoRun.ENEMY_HEAD_BIT |
                EcoRun.PLASTIC_BIT |
                EcoRun.METAL_BIT |
                EcoRun.PAPER_BIT |
                EcoRun.ITEM_BIT |
                EcoRun.SMOG_MASK_BIT |
                EcoRun.OIL_BIT |
                EcoRun.SMOG_BIT |
                EcoRun.BIN_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef);

        //head touch
        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-4 / EcoRun.PPM, 7 / EcoRun.PPM), new Vector2(4 / EcoRun.PPM, 7 / EcoRun.PPM));
        fdef.shape = head;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData("head");

        //bottom of the earth touch - for ground detection
        PolygonShape bottom = new PolygonShape();
        bottom.setAsBox(4/EcoRun.PPM, 4/EcoRun.PPM);
        fdef.shape = bottom;
        fdef.filter.categoryBits = EcoRun.EARTH_BIT;
        fdef.filter.maskBits = EcoRun.GROUND_BIT;
        b2body.createFixture(fdef).setUserData("bottom");
    }

    private void insidePortal() {
        if (inPortal && Hud.areTrashThrown()){
          b2body.setActive(false);
          screen.nextLevel();
          inPortal = false;
        } else if (inPortal) {
            Hud.showDialog(3,0);
            inPortal = false;
        } else {
            inPortal = false;
        }
    }

    private void checkBin() {
        if (100*b2body.getPosition().x >= binBounds && 100*b2body.getPosition().x <= (binBounds + 16)) {
            if (binType == 0) { //redBin active
                redBin = true;
                yellowBin = false;
                blueBin = false;
                Hud.showDialog(1,0);
            } else if (binType == 1) { //yellowBin active
                redBin = false;
                yellowBin = true;
                blueBin = false;
                Hud.showDialog(1,1);
            } else if (binType == 2) { //blueBin active
                redBin = false;
                yellowBin = false;
                blueBin = true;
                Hud.showDialog(1,2);
            }
            else { //nothing active
                binBounds = 0f;
                binType = -1;
                redBin = false;
                yellowBin = false;
                blueBin = false;
            }
        }
    }
}
