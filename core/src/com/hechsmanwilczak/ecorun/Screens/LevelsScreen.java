package com.hechsmanwilczak.ecorun.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.hechsmanwilczak.ecorun.EcoRun;

public class LevelsScreen implements Screen {
    private Viewport viewport;
    private Stage stage;
    private BitmapFont font;
    private OrthographicCamera cam;

    private Game game;
    private TextureRegion texRegBg;

    public LevelsScreen(Game game){
        this.game = game;
        cam = new OrthographicCamera();
        viewport = new FitViewport(EcoRun.V_WIDTH, EcoRun.V_HEIGHT,cam);
        stage = new Stage(viewport, ((EcoRun) game).batch);
        Gdx.input.setInputProcessor(stage);
        font = new BitmapFont(Gdx.files.internal("font.fnt"));

        texRegBg = new TextureRegion(new Texture(Gdx.files.internal("bg.jpg")));

        Table table = new Table();
        table.center();
        table.setBackground(new TextureRegionDrawable(texRegBg));
        table.setFillParent(true);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;

        TextButton level1Button=new TextButton("Level 1",textButtonStyle);
        level1Button.setText("Level 1");
        level1Button.setHeight(230);
        level1Button.setWidth(500);
        level1Button.setPosition(50,90);
        level1Button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                goToGameScreen(1);
            }
        });

        TextButton level2Button=new TextButton("Level 2",textButtonStyle);
        level2Button.setText("Level 2");
        level2Button.setHeight(230);
        level2Button.setWidth(500);
        level2Button.setPosition(50,50);
        level2Button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                goToGameScreen(2);
            }
        });


        TextButton level3Button=new TextButton("Level 3",textButtonStyle);
        level3Button.setText("Level 3");
        level3Button.setHeight(230);
        level3Button.setWidth(500);
        level3Button.setPosition(50,10);
        level3Button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                goToGameScreen(3);
            }
        });


        table.add(level1Button).expandX().padBottom(10);
        table.row();
        table.add(level2Button).expandX().padBottom(10);
        table.row();
        table.add(level3Button).expandX();
        table.row();

        stage.addActor(table);

    }

    public void goToGameScreen(Integer lvl){
        EcoRun.music.setVolume(0.1f);
        game.setScreen(new PlayScreen((EcoRun) game, lvl,0));
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
}
