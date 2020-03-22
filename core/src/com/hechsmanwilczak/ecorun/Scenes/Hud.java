package com.hechsmanwilczak.ecorun.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.hechsmanwilczak.ecorun.EcoRun;

//dolny pasek - zycia, level, wynik
public class Hud implements Disposable {
    public Stage stage;
    private Viewport viewport;

    private Integer score;
    private Integer lives;

    Label scoreLabel;
    Label livesLabel;
    BitmapFont hudFont;

    public Hud(SpriteBatch sb){
        score = 0;
        lives = 3;
        viewport = new FitViewport(EcoRun.V_WIDTH, EcoRun.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);
        hudFont = new BitmapFont(Gdx.files.internal("hudFont.fnt"));

        Table table = new Table();
        table.bottom();
        table.padBottom(10);
        table.setFillParent(true);

        scoreLabel = new Label(String.format("score: %05d", score), new Label.LabelStyle(hudFont, Color.WHITE));
        livesLabel = new Label(String.format("lives: %01d", lives), new Label.LabelStyle(hudFont, Color.WHITE));

        table.add(scoreLabel).expandX().left().padLeft(20);
        table.add(livesLabel).expandX().right().padRight(20); //table.row() zeby utworzyc nowy rekord w tabeli

        stage.addActor(table);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
