package com.hechsmanwilczak.ecorun.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.hechsmanwilczak.ecorun.EcoRun;

public class MenuScreen implements Screen {
    private Viewport viewport;
    private Stage stage;

    private Game game;
    private BitmapFont menuFont;

    //background
    private TextureRegion texRegBg;

    public MenuScreen(Game game){
        this.game = game;
        viewport = new FitViewport(EcoRun.V_WIDTH, EcoRun.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, ((EcoRun) game).batch);
        Gdx.input.setInputProcessor(stage);

        menuFont = new BitmapFont(Gdx.files.internal("font.fnt"));
        Label.LabelStyle font = new Label.LabelStyle(menuFont, Color.WHITE);

        texRegBg = new TextureRegion(new Texture(Gdx.files.internal("bg.jpg")));
        Table table = new Table();
        table.center();
        table.setBackground(new TextureRegionDrawable(texRegBg));
        table.setFillParent(true);

        Label gameNameLabel = new Label("EcoRun", font);
        Label playLabel = new Label("click to play!", font);


        Image playButtonImage = new Image(new Texture("play.png"));
        playButtonImage.setSize(30, 16);
        playButtonImage.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                goToGameScreen();
            }
        });

        Image infoButtonImage = new Image(new Texture("info.png"));
        infoButtonImage.setSize(30, 16);
        infoButtonImage.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                goToInfoScreen();
            }
        });

        Image exitButtonImage = new Image(new Texture("exit.png"));
        exitButtonImage.setSize(30, 16);
        exitButtonImage.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        //nie wiem czemu ale nie wykrywa kliknięcia przyciskow na calej powierzchni : (
        // ale one i tak są do zmiany więc moze się to jakoś inaczej rozwiaze
        //TODO: background image, wykrywanie przyciskow

        table.add(gameNameLabel).expandX();
        table.row();
        table.add(playLabel).expandX().padBottom(10f);
        table.row();
        table.add(playButtonImage).expandX().padTop(10f);
        table.row();
        table.add(infoButtonImage).expandX().padTop(1f);
        table.row();
        table.add(exitButtonImage).expandX().padTop(1f);


        stage.addActor(table);

    }

    public void goToGameScreen(){
        game.setScreen(new LevelsScreen((EcoRun) game));
        dispose();
    }

    public void goToInfoScreen(){
        game.setScreen(new InfoScreen((EcoRun) game));
        dispose();
    }

    @Override
    public void show() {


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

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
        stage.dispose();
    }
}
