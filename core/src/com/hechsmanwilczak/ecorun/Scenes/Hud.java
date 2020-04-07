package com.hechsmanwilczak.ecorun.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
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

    private static Integer score;
    private static Integer lives;
    private static Integer items;

    private static Label scoreLabel;
    private static Label livesLabel;
    private static Label itemsLabel;
    private static Label plasticLabel;
    private static Label metalLabel;
    private static Label paperLabel;
    BitmapFont hudFont;

    private static Integer noPlastic, colPlastic;
    private static Integer noMetal, colMetal;
    private static Integer noPaper, colPaper;
    private Image imagePlastic, imageMetal, imagePaper, imageLife;

    public Hud(SpriteBatch sb, Integer plastic, Integer metal, Integer paper){
        score = 0;
        lives = 3;
        items = 0;
        noPlastic = plastic; //ilosc plastiku na planszy
        noMetal = metal; //ilosc metalu na planszy
        noPaper = paper; //ilosc papieru na planszy
        colPlastic = 0; //zebrana ilosc plastiku
        colMetal = 0;
        colPaper = 0;

        viewport = new FitViewport(EcoRun.V_WIDTH, EcoRun.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);
        hudFont = new BitmapFont(Gdx.files.internal("hudFont.fnt"));

        //images
        imageLife = new Image(new Texture(Gdx.files.internal("heart.png")));
        imagePlastic =  new Image(new Texture(Gdx.files.internal("plastic.png")));
        imageMetal =  new Image(new Texture(Gdx.files.internal("metal.png")));
        imagePaper =  new Image(new Texture(Gdx.files.internal("paper.png")));

        Table table = new Table();
        Table topTable = new Table();
        topTable.top();
        table.bottom();
        table.padBottom(10);
        topTable.padTop(10);
        topTable.setFillParent(true);
        table.setFillParent(true);

        scoreLabel = new Label(String.format("score: %05d", score), new Label.LabelStyle(hudFont, Color.WHITE));
        plasticLabel = new Label(String.format(": %01d/%01d", colPlastic, noPlastic), new Label.LabelStyle(hudFont, Color.WHITE));
        metalLabel = new Label(String.format(": %01d/%01d", colMetal, noMetal), new Label.LabelStyle(hudFont, Color.WHITE));
        paperLabel = new Label(String.format(": %01d/%01d", colPaper, noPaper), new Label.LabelStyle(hudFont, Color.WHITE));

        livesLabel = new Label(String.format("lives: %01d", lives), new Label.LabelStyle(hudFont, Color.WHITE));

        topTable.add(imagePlastic).center().padRight(5);
        topTable.add(plasticLabel).center().padRight(20);
        topTable.add(imageMetal).center().padRight(5);
        topTable.add(metalLabel).center().padRight(20);
        topTable.add(imagePaper).center().padRight(5);
        topTable.add(paperLabel).center();

        table.add(scoreLabel).expandX().left().padLeft(20);
        table.add(livesLabel).expandX().right().padRight(20); //table.row() zeby utworzyc nowy rekord w tabeli
        /*for (int i =1; i<=lives; i++){
            if (i==lives) {
                Image heart = new Image(imageLife.getDrawable());
                table.add(heart).right().padRight(20);
            } else {
                Image heart2 = new Image(imageLife.getDrawable());
                table.add(heart2).right();
            }
        }*/

        stage.addActor(topTable);
        stage.addActor(table);
    }

    public static void addScore(int value){
        score += value;
        scoreLabel.setText(String.format("score: %05d", score));
    }

    public static void zeroLives(){
        lives = 0;
        livesLabel.setText(String.format("lives: %01d", lives));
    }


    public static void loseLive(){
        lives -= 1;
        livesLabel.setText(String.format("lives: %01d", lives));
    }

    public boolean isLifeZero(){
        if (lives == 0)
                return true;
        else return false;
    }

    public static void resetCollected() {
        colMetal = 0;
        colPaper = 0;
        colPlastic = 0;
        plasticLabel.setText(String.format(": %01d/%01d", colPlastic, noPlastic));
        paperLabel.setText(String.format(": %01d/%01d", colPaper, noPaper));
        metalLabel.setText(String.format(": %01d/%01d", colMetal, noMetal));
    }

    public static void addPlastic(){
        colPlastic += 1;
        plasticLabel.setText(String.format(": %01d/%01d", colPlastic, noPlastic));
    }

    public static void addPaper(){
        colPaper += 1;
        paperLabel.setText(String.format(": %01d/%01d", colPaper, noPaper));
    }

    public static void addMetal(){
        colMetal += 1;
        metalLabel.setText(String.format(": %01d/%01d", colMetal, noMetal));
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
