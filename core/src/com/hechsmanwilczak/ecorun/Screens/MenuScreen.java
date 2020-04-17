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

        float btnWidth = EcoRun.V_WIDTH/4f; //100
        float btnHeight = EcoRun.V_HEIGHT/5f; //41
        float btnPosX = EcoRun.V_WIDTH/8f; //50
        float btnPosY = (3f*EcoRun.V_HEIGHT)/7f; //90
        float btnPosMov = EcoRun.V_HEIGHT/6f; //35

        menuFont = new BitmapFont(Gdx.files.internal("font.fnt"));
        Label.LabelStyle font = new Label.LabelStyle(menuFont, Color.WHITE);

        texRegBg = new TextureRegion(new Texture(Gdx.files.internal("bg.jpg")));
        Table table = new Table();
        table.center();
        table.setBackground(new TextureRegionDrawable(texRegBg));
        table.setFillParent(true);

        Label gameNameLabel = new Label("ECO-RUN", font);
        Label playLabel = new Label("click to play!", font);

        //TextBtn style
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = new BitmapFont(Gdx.files.internal("font.fnt"));
        textButtonStyle.fontColor = Color.GREEN;

        //Play the game
        TextButton playBtn =new TextButton("Play",textButtonStyle);
        playBtn.setText("Play");
        playBtn.setHeight(btnHeight);
        playBtn.setWidth(btnWidth);
        playBtn.setPosition(btnPosX,btnPosY);
        playBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                goToGameScreen();
            }
        });

        //Info about the game
        TextButton infoBtn =new TextButton("Information",textButtonStyle);
        infoBtn.setText("Information");
        infoBtn.setHeight(btnHeight);
        infoBtn.setWidth(btnWidth);
        infoBtn.setPosition(btnPosX,btnPosY-btnPosMov);
        infoBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                goToInfoScreen();
            }
        });

        //Exit
        TextButton exitBtn =new TextButton("Exit",textButtonStyle);
        exitBtn.setText("Exit");
        exitBtn.setHeight(btnHeight);
        exitBtn.setWidth(btnWidth);
        exitBtn.setPosition(btnPosX,btnPosY-2*btnPosMov);
        exitBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        //TODO: background image, wykrywanie przyciskow

        table.add(gameNameLabel).expandX();
        table.row();
        table.add(playLabel).expandX().padBottom(10f);
        table.row();
        table.add(playBtn).expandX().padTop(10f);
        table.row();
        table.add(infoBtn).expandX().padTop(5f);
        table.row();
        table.add(exitBtn).expandX().padTop(5f);


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