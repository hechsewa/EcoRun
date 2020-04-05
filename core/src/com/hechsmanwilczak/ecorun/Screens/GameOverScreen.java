package com.hechsmanwilczak.ecorun.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.hechsmanwilczak.ecorun.EcoRun;

public class GameOverScreen implements Screen {
    private Viewport viewport;
    private Stage stage;
    private BitmapFont screenFont;
    private Game game;

    public GameOverScreen(Game game){
        this.game = game;
        viewport = new FitViewport(EcoRun.V_WIDTH, EcoRun.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, ((EcoRun) game).batch);
        Gdx.input.setInputProcessor(stage);
        screenFont = new BitmapFont(Gdx.files.internal("font.fnt"));
        Label.LabelStyle font = new Label.LabelStyle(screenFont, Color.WHITE);

        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label gameOverLabel = new Label("GAME OVER", font);
        Label playAgainLabel = new Label("play again or go back to menu", font);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        //BitmapFont font2 = new BitmapFont();
        textButtonStyle.font = screenFont;
        textButtonStyle.fontColor = Color.ORANGE;

        TextButton playAgainButton=new TextButton("Play again",textButtonStyle);
        playAgainButton.setText("Play again");
        playAgainButton.setHeight(230);
        playAgainButton.setWidth(500);
        playAgainButton.setPosition(50,50);
        playAgainButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                goToGameScreen();
            }
        });

        TextButton menuButton=new TextButton("Menu",textButtonStyle);
        menuButton.setText("Menu");
        menuButton.setHeight(230);
        menuButton.setWidth(500);
        menuButton.setPosition(50,30);
        menuButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                goToMenuScreen();
            }
        });

        table.add(gameOverLabel).expandX();
        table.row();
        table.add(playAgainLabel).expandX().padTop(10f).padBottom(20f);
        table.row();
        table.add(playAgainButton).expandX().padBottom(10f);
        table.row();
        table.add(menuButton).expandX().padBottom(10f);

        stage.addActor(table);

    }

    public void goToGameScreen(){
        game.setScreen(new PlayScreen((EcoRun) game));

        dispose();
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
