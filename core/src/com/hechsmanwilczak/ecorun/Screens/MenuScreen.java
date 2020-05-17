package com.hechsmanwilczak.ecorun.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
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
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.hechsmanwilczak.ecorun.EcoRun;

public class MenuScreen implements Screen {
    private Viewport viewport;
    private Stage stage;

    private Game game;
    private BitmapFont menuFont;
    private BitmapFont detailFont;
    private Image imageLogo;
    private OrthographicCamera cam;

    //background
    private TextureRegion texRegBg;


    public MenuScreen(Game game){
        this.game = game;
        cam = new OrthographicCamera();
        viewport = new FitViewport(EcoRun.V_WIDTH, EcoRun.V_HEIGHT, cam);
        stage = new Stage(viewport, ((EcoRun) game).batch);
        Gdx.input.setInputProcessor(stage);


        float btnWidth = EcoRun.V_WIDTH/4f; //100
        float btnHeight = EcoRun.V_HEIGHT/5f; //41
        float btnPosX = EcoRun.V_WIDTH/8f; //50
        float btnPosY = (3f*EcoRun.V_HEIGHT)/7f; //90
        float btnPosMov = EcoRun.V_HEIGHT/6f; //35

        menuFont = new BitmapFont(Gdx.files.internal("font.fnt"));
        detailFont = new BitmapFont(Gdx.files.internal("EcoRun-30.fnt"));
        Label.LabelStyle font = new Label.LabelStyle(menuFont, Color.WHITE);
        Label.LabelStyle detFont = new Label.LabelStyle(detailFont, Color.WHITE);

        imageLogo = new Image(new Texture(Gdx.files.internal("logo.png")));
        //imageLogo.setScale(0.5f);
        //imageLogo.setPosition(0.4f*EcoRun.V_WIDTH,0.9f*EcoRun.V_HEIGHT, Align.center);

        texRegBg = new TextureRegion(new Texture(Gdx.files.internal("bg2.jpg")));
        Table table = new Table();
        table.center();
        table.setBackground(new TextureRegionDrawable(texRegBg));
        table.setFillParent(true);

        Label gameNameLabel = new Label("ECO-RUN", font);
        Label playLabel = new Label("click to play!", detFont);

        //TextBtn style
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = new BitmapFont(Gdx.files.internal("font.fnt"));
        //textButtonStyle.fontColor = Color.GREEN;

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

        table.add(imageLogo).expandX().size(220f,100f);
        //table.row();
        //table.add(playLabel).expandX().padBottom(10f);
        table.row();
        table.add(playBtn).expandX().padTop(5f);
        table.row();
        table.add(infoBtn).expandX().padTop(5f);
        table.row();
        table.add(exitBtn).expandX().padTop(5f);


        stage.addActor(table);

        EcoRun.music = EcoRun.assetManager.get("sounds/bgmusic.mp3", Music.class);
        EcoRun.music.setLooping(true);
        EcoRun.music.play();

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
