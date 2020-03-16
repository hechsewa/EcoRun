package com.hechsmanwilczak.ecorun.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.hechsmanwilczak.ecorun.EcoRun;
import com.hechsmanwilczak.ecorun.Scenes.Hud;

public class PlayScreen implements Screen {
    private EcoRun game;
    private Viewport gameport;
    private OrthographicCamera gamecam;
    private Hud hud;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //Box2d
    private World world;
    private Box2DDebugRenderer b2dr;


    public PlayScreen(EcoRun game) {
        this.game = game;
        gamecam = new OrthographicCamera();
        gameport = new FitViewport(EcoRun.V_WIDTH, EcoRun.V_HEIGHT, gamecam);
        hud = new Hud(game.batch);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("ecorun-lvl1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        gamecam.position.set(gameport.getWorldWidth() / 2, gameport.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0, 0), true);
        b2dr = new Box2DDebugRenderer();

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;


        //ground layer
        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rectangle.getX() + rectangle.getWidth() / 2, rectangle.getY() + rectangle.getHeight() / 2);

            body = world.createBody(bdef);

            shape.setAsBox(rectangle.getWidth() / 2, rectangle.getHeight() / 2);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        //bins layer
        for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rectangle.getX() + rectangle.getWidth() / 2, rectangle.getY() + rectangle.getHeight() / 2);

            body = world.createBody(bdef);

            shape.setAsBox(rectangle.getWidth() / 2, rectangle.getHeight() / 2);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        //portal layer
        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rectangle.getX() + rectangle.getWidth() / 2, rectangle.getY() + rectangle.getHeight() / 2);

            body = world.createBody(bdef);

            shape.setAsBox(rectangle.getWidth() / 2, rectangle.getHeight() / 2);
            fdef.shape = shape;
            body.createFixture(fdef);
        }


    }
    @Override
    public void show() {

    }

    public void handleInput(float dt){
        if(Gdx.input.isTouched())
            gamecam.position.x += 100 * dt;
    }

    public void update(float dt){
        handleInput(dt);
        gamecam.update();
        renderer.setView(gamecam);
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); //oo to opengl fun

        renderer.render();

        b2dr.render(world, gamecam.combined);

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        gameport.update(width, height);

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

    }
}
