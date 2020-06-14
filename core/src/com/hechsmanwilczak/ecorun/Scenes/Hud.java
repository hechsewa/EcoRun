package com.hechsmanwilczak.ecorun.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.hechsmanwilczak.ecorun.EcoRun;

import java.util.Timer;
import java.util.TimerTask;

public class Hud implements Disposable {
    public static Stage stage;
    private Viewport viewport;

    private static Integer score;
    private static Integer lives;

    private static Label scoreLabel;
    private static Label livesLabel;
    private static Label pauseLabel;
    private static Label plasticLabel;
    private static Label metalLabel;
    private static Label paperLabel;
    private static Label infoLabel;
    private static BitmapFont hudFont;

    private static Image heart, heart2, heart3;
    public static Window pauseWin;
    private static Label.LabelStyle labelStyle;

    private static Integer noPlastic, colPlastic;
    private static Integer noMetal, colMetal;
    private static Integer noPaper, colPaper;
    private Image imagePlastic, imageMetal, imagePaper, imageLife;

    public Hud(SpriteBatch sb, Integer plastic, Integer metal, Integer paper){
        score = 0;
        lives = 3;
        noPlastic = plastic;
        noMetal = metal;
        noPaper = paper;
        colPlastic = 0;
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

        labelStyle = new Label.LabelStyle(hudFont, Color.WHITE);

        scoreLabel = new Label(String.format("score: %05d", score), labelStyle);
        pauseLabel = new Label("Press spacebar to pause", labelStyle);
        plasticLabel = new Label(String.format(": %01d/%01d", colPlastic, noPlastic),labelStyle);
        metalLabel = new Label(String.format(": %01d/%01d", colMetal, noMetal), labelStyle);
        paperLabel = new Label(String.format(": %01d/%01d", colPaper, noPaper), labelStyle);
        livesLabel = new Label("lives: ", labelStyle);
        infoLabel = new Label(" ", new Label.LabelStyle(hudFont, Color.WHITE));
        infoLabel.setPosition(Math.round(0.3*EcoRun.V_WIDTH),
                Math.round(0.8*(EcoRun.V_HEIGHT)),
                Align.center);

        topTable.add(imagePlastic).center().padRight(5f);
        topTable.add(plasticLabel).center().padRight(20f);
        topTable.add(imageMetal).center().padRight(5f);
        topTable.add(metalLabel).center().padRight(20f);
        topTable.add(imagePaper).center().padRight(5f);
        topTable.add(paperLabel).center();

        table.add(scoreLabel).expandX().left().padLeft(20);
        table.add(pauseLabel).expandX().center();
        table.add(livesLabel).expandX().right().padRight(20);


        heart = new Image(imageLife.getDrawable());
        table.add(heart).right();
        heart2 = new Image(imageLife.getDrawable());
        table.add(heart2).right();
        heart3 = new Image(imageLife.getDrawable());
        table.add(heart3).right().padRight(20f);

        //pause window
        initPauseWindow(viewport.getScreenWidth(), viewport.getScreenHeight());

        stage.addActor(infoLabel);
        stage.addActor(topTable);
        stage.addActor(table);
    }

    public static void initPauseWindow(int w, int h){
        Pixmap winPixmap = new Pixmap(w, h, Pixmap.Format.RGBA8888);
        winPixmap.setColor(0f,0f,0f,0.5f);
        winPixmap.fill();
        Texture winBg = new Texture(winPixmap);
        Window.WindowStyle winStyle = new Window.WindowStyle(hudFont, Color.WHITE, new Image(winBg).getDrawable());
        pauseWin = new Window("PAUSED",winStyle);
        pauseWin.setHeight(w);
        pauseWin.setWidth(h);
    }

    public static void addScore(int value){
        score += value;
        scoreLabel.setText(String.format("score: %05d", score));
    }

    public static void loseScore(int value){
        score -= value;
        scoreLabel.setText(String.format("score: %05d", score));
    }

    public static void zeroLives(){
        lives = 0;
        heart3.setVisible(false);
        heart2.setVisible(false);
        heart.setVisible(false);
    }

    public static void loseLive(){
        lives -= 1;
        if (lives == 2){
            heart3.setVisible(false);
        } else if (lives == 1){
            heart2.setVisible(false);
        } else {
            heart.setVisible(false);
        }
    }

    private static String returnInfoString(Integer type, Integer colType) {
        String retStr = " ";
        if (type == 0)
            retStr = "Collected a smog mask";
        else if (type == 1) {
                String binKey;
                String binType;
                if (colType == 0) { //metal
                    binKey = "R";
                    binType = "metal";
                } else if (colType == 1) { //plastic
                    binKey = "Y";
                    binType = "plastic";
                } else {              //paper
                    binKey = "B";
                    binType = "paper";
                }
                retStr = "Press " + binKey + " to throw out " + binType;
            }
        else if (type == 2)
            retStr = "Cannot pass without a smog mask";
        else if (type == 3)
            retStr = "Cannot go in without collecting all trash";

        return retStr;
    }

    public static void showDialog(Integer type, Integer colType) {
        String infoStr = returnInfoString(type, colType);
        infoLabel.setText(infoStr);
        infoLabel.setAlignment(Align.center);

        final Timer t = new Timer();
        t.schedule(new TimerTask() {
            public void run() {
                infoLabel.setText(" ");
                t.cancel();
            }
        }, 2000);

    }

    public boolean isLifeZero(){
        if (lives == 0)
                return true;
        else return false;
    }

    public static Integer getScore() {
        return score;
    }

    public static void resetCollected(int type) {
        if (type == 0)
            colMetal = 0;
        else if (type == 1)
            colPlastic = 0;
        else if (type == 2)
            colPaper = 0;
        else {
            colMetal = 0;
            colPaper = 0;
            colPlastic = 0;
        }
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

    public static boolean areAllCollected(int type){
        if (type == 0){ //metal
            if (colMetal >= noMetal){
                EcoRun.trash_sound.play();
                resetCollected(0);
                return true;
            }
        } else if (type == 1){ //plastic
            if (colPlastic >= noPlastic){
                EcoRun.trash_sound.play();
                resetCollected(1);
                return true;
            }
        } else { //paper
            if (colPaper >= noPaper){
                EcoRun.trash_sound.play();
                resetCollected(2);
                return true;
            }
        }
        return false;
    }

    public static Boolean areTrashThrown() {
        return colPaper == 0 && colPlastic == 0 && colMetal == 0;
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
