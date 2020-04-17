package com.hechsmanwilczak.ecorun.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.hechsmanwilczak.ecorun.EcoRun;
import com.hechsmanwilczak.ecorun.Scenes.Hud;
import com.hechsmanwilczak.ecorun.Sprites.Earth;
import com.hechsmanwilczak.ecorun.Sprites.Enemy;
import com.hechsmanwilczak.ecorun.Sprites.Items.Item;
import com.hechsmanwilczak.ecorun.Sprites.PlasticBag;
import com.hechsmanwilczak.ecorun.Tools.*;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;

import java.util.PriorityQueue;
import java.util.Timer;
import java.util.TimerTask;

public class PlayScreen implements Screen {
    private EcoRun game;
    private TextureAtlas atlas;

    private Viewport gameport;
    private OrthographicCamera gamecam;
    private Hud hud;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //Box2d
    private World world;
    private Box2DDebugRenderer b2dr;
    private B2WorldCreatorEmpty creator;
    private WorldContactListener contactListener;

    //sprites
    private Earth player;
    private Array<Item> items;

    //items
    private Integer noPlastic;
    private Integer noMetal;
    private Integer noPaper;

    //current level
    private static Integer level;

    public static Boolean rThrown, yThrown, bThrown;

    public PlayScreen(EcoRun game, Integer lvl) {
        atlas = new TextureAtlas("Earth_and_enemy.pack");

        rThrown = false;
        yThrown = false;
        bThrown = false;
        level = lvl;

        setTrashAmount();

        this.game = game;
        gamecam = new OrthographicCamera();
        gameport = new FitViewport(EcoRun.V_WIDTH / EcoRun.PPM, EcoRun.V_HEIGHT / EcoRun.PPM, gamecam);
        hud = new Hud(game.batch, noPlastic, noMetal, noPaper);

        mapLoader = new TmxMapLoader();
        if (level == 1)
            map = mapLoader.load("ecorun-lvl1.tmx");
        else if (level == 2)
            map = mapLoader.load("ecorun-lvl2.tmx");
        else
            map = mapLoader.load("ecorun-lvl3.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / EcoRun.PPM);
        gamecam.position.set(gameport.getWorldWidth() / 2, gameport.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0, -10), true);
        b2dr = new Box2DDebugRenderer();
        b2dr.setDrawBodies(false); //invisible tiled object green boxes

        if (level == 1) {
            creator = new B2WorldCreator(this);
        } else if (level == 2) {
            creator = new B2WorldCreatorLvl2(this);
        } else {
            creator = new B2WorldCreatorLvl3(this);
        }

        player = new Earth(this);

        contactListener = new WorldContactListener();
        world.setContactListener(contactListener);

    }

    private void setTrashAmount() {
        if (level == 1) {
            noMetal = 1;
            noPlastic = 1;
            noPaper = 1;
        } else if (level == 2) {
            noMetal = 2;
            noPlastic = 2;
            noPaper = 2;
        } else {
            noMetal = 3;
            noPlastic = 3;
            noPaper = 3;
        }
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    @Override
    public void show() {

    }

    public void handleInput(float dt) {
        if (player.currentState != Earth.State.DEAD) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
                if (contactListener.isPlayerOnGround())
                    player.b2body.applyLinearImpulse(new Vector2(0, 3f), player.b2body.getWorldCenter(), true);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2)
                player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2)
                player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
            //inside red bin
            if (Gdx.input.isKeyPressed(Input.Keys.R) && player.redBin) {
                hud.areAllCollected(0);
            }
            //inside yellow bin
            if (Gdx.input.isKeyPressed(Input.Keys.Y) && player.yellowBin) {
                hud.areAllCollected(1);
            }
            //inside blue bin
            if (Gdx.input.isKeyPressed(Input.Keys.B) && player.blueBin) {
                hud.areAllCollected(2);
            }
        }

    }

    public void update(float dt) {
        handleInput(dt);

        world.step(1 / 60f, 6, 2);

        player.update(dt);

        for (Enemy enemy : creator.getPlasticBagArray())
            enemy.update(dt);

        for (Item item : creator.getLeavesArray())
            item.update(dt);

        for (Item item : creator.getTrashArray())
            item.update(dt);

        //move camera when earth is not dead
        if (player.currentState != Earth.State.DEAD) {
            gamecam.position.x = player.b2body.getPosition().x;
        }

        gamecam.update();
        renderer.setView(gamecam);


    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        b2dr.render(world, gamecam.combined);

        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        player.draw(game.batch);

        for (Enemy enemy : creator.getPlasticBagArray())
            enemy.draw(game.batch);

        for (Item item : creator.getLeavesArray())
            item.draw(game.batch);

        for (Item item : creator.getTrashArray())
            item.draw(game.batch);

        game.batch.end();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

        if (gameOver()) {
            game.setScreen(new GameOverScreen(game));
            dispose();
        }
    }

    public boolean gameOver() {
        if (player.currentState == Earth.State.DEAD && player.getStateTimer() > 2)
            return true;
        return false;
    }

    public void nextLevel() {
        game.setScreen(new PlayScreen((EcoRun) game, level + 1));
    }

    @Override
    public void resize(int width, int height) {
        gameport.update(width, height);

    }

    public TiledMap getMap() {
        return map;
    }

    public World getWorld() {
        return world;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        b2dr.dispose();
        world.dispose();
        hud.dispose();
    }

    public Hud getHud() {
        return hud;
    }


}
