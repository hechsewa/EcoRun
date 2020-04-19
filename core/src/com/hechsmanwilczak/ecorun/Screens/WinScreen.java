package com.hechsmanwilczak.ecorun.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.hechsmanwilczak.ecorun.EcoRun;
import com.hechsmanwilczak.ecorun.Scenes.Hud;

public class WinScreen implements Screen {
    private Viewport viewport;
    private Stage stage;
    private BitmapFont screenFont;
    private Game game;

    private TextureRegion texRegBg;

    public WinScreen(Game game){
        this.game = game;
        viewport = new FitViewport(EcoRun.V_WIDTH, EcoRun.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, ((EcoRun) game).batch);
        Gdx.input.setInputProcessor(stage);
        screenFont = new BitmapFont(Gdx.files.internal("font.fnt"));
        Label.LabelStyle font = new Label.LabelStyle(screenFont, Color.WHITE);

        texRegBg = new TextureRegion(new Texture(Gdx.files.internal("bg.jpg")));

        Table table = new Table();
        table.center();
        table.setBackground(new TextureRegionDrawable(texRegBg));
        table.setFillParent(true);

        Label congratsLabel = new Label("Congratulations!", font);
        Label descriptionLabel = new Label("you've won the game\n and saved the Earth #stayeco", font);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        //BitmapFont font2 = new BitmapFont();
        textButtonStyle.font = screenFont;
        textButtonStyle.fontColor = Color.GREEN;

        TextButton exitButton=new TextButton("Exit",textButtonStyle);
        exitButton.setText("Exit");
        exitButton.setHeight(230);
        exitButton.setWidth(500);
        exitButton.setPosition(50,30);
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        TextButton menuButton=new TextButton("Menu",textButtonStyle);
        menuButton.setText("Menu");
        menuButton.setHeight(230);
        menuButton.setWidth(500);
        menuButton.setPosition(50,50);
        menuButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                goToMenuScreen();
            }
        });

        table.add(congratsLabel).expandX();
        table.row();
        table.add(descriptionLabel).expandX().padTop(10f).padBottom(20f);
        table.row();
        table.add(menuButton).expandX().padBottom(10f);
        table.row();
        table.add(exitButton).expandX().padBottom(10f);

        stage.addActor(table);

    }


    public void goToMenuScreen(){
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
