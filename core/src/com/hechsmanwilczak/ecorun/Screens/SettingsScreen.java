package com.hechsmanwilczak.ecorun.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.hechsmanwilczak.ecorun.AppSettings;
import com.hechsmanwilczak.ecorun.EcoRun;

public class SettingsScreen implements Screen {
    private Viewport viewport;
    private Stage stage;
    private OrthographicCamera cam;

    private Game game;
    private TextureRegion texRegBg;
    private BitmapFont fontInfo;
    private BitmapFont fontButton;

    private BitmapFont font;
    public int Gamevolume;

    public SettingsScreen(Game game){
        this.game = game;
        cam = new OrthographicCamera();
        viewport = new FitViewport(EcoRun.V_WIDTH, EcoRun.V_HEIGHT, cam);
        stage = new Stage(viewport, ((EcoRun) game).batch);
        Gdx.input.setInputProcessor(stage);

        fontButton = new BitmapFont(Gdx.files.internal("font.fnt"));
        fontInfo = new BitmapFont();
        fontInfo.getData().setScale(0.7f);

        Label.LabelStyle fontBtn = new Label.LabelStyle(fontButton, Color.WHITE);
        Label.LabelStyle fontIn = new Label.LabelStyle(fontInfo, Color.WHITE);
        texRegBg = new TextureRegion(new Texture(Gdx.files.internal("bg.jpg")));


        Table table = new Table();
        table.center();
        table.setFillParent(true);
        table.setBackground(new TextureRegionDrawable(texRegBg));

        Label infoLabel = new Label("Settings", fontBtn);

        Image backToMenuButton = new Image(new Texture("back.png"));
        backToMenuButton.setSize(125, 125);
        backToMenuButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                goToMenu();
            }
        });

        //volume settings
        Label musicLabel = new Label("Music Volume", fontBtn);
        font = new BitmapFont(Gdx.files.internal("font.fnt"));
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;
        //volumeup
        TextButton volUpBtn=new TextButton("UP",textButtonStyle);
        volUpBtn.setText("UP");
        volUpBtn.setHeight(230);
        volUpBtn.setWidth(500);
        volUpBtn.setPosition(50,85);
        volUpBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                AppSettings.setMusicVolume(volLim(AppSettings.getMusicVolume()+0.1f));
                EcoRun.music.setVolume(AppSettings.getMusicVolume());
            }
        });
        //volumedown
        TextButton volDwnBtn=new TextButton("DOWN",textButtonStyle);
        volDwnBtn.setText("DOWN");
        volDwnBtn.setHeight(230);
        volDwnBtn.setWidth(500);
        volDwnBtn.setPosition(50,85);
        volDwnBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                AppSettings.setMusicVolume(volLim(AppSettings.getMusicVolume()-0.1f));
                System.out.println(AppSettings.getMusicVolume());
                EcoRun.music.setVolume(AppSettings.getMusicVolume());
            }
        });

        //change highscore
        TextButton resetButton=new TextButton("Reset High Score",textButtonStyle);
        resetButton.setText("Reset High Score");
        resetButton.setHeight(230);
        resetButton.setWidth(500);
        resetButton.setPosition(50,30);
        resetButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                RecordScreen.setHighScore(0);
            }
        });

        table.add(infoLabel).expandX();
        table.row();
        table.add(musicLabel).expandX().padTop(20f);
        table.row();
        table.add(volUpBtn).expandX();
        table.row();
        table.add(volDwnBtn).expandX();
        table.row();
        table.add(resetButton).expandX().padTop(20f);
        table.row();
        table.add(backToMenuButton).expandX().padTop(20f);

        stage.addActor(table);

    }
    public void goToMenu(){
        game.setScreen(new MenuScreen((EcoRun) game));
        dispose();
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        SpriteBatch batch = new SpriteBatch();
        batch.setTransformMatrix(cam.view);
        batch.setProjectionMatrix(cam.projection);
        batch.begin();
        stage.act();
        stage.draw();
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        cam.update();
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

    public float volLim(float vol){
        if (vol <= 0f){
            return 0f;
        } else if (vol >= 1f) {
            return 1f;
        }
        return vol;
    }
}
