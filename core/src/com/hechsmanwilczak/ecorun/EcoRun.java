package com.hechsmanwilczak.ecorun;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.hechsmanwilczak.ecorun.Screens.PlayScreen;

public class EcoRun extends Game {
	public SpriteBatch batch;
	public static final int V_WIDTH = 400;
	public static final int V_HEIGHT = 208;
	public static final float PPM = 100;

	public static final short GROUND_BIT = 1;
	public static final short EARTH_BIT = 2;
	public static final short BIN_BIT = 4;
	public static final short PORTAL_BIT = 8;
	public static final short OPEN_PORTAL_BIT = 16;
	public static final short ENEMY_BIT = 32;
	public static final short ENEMY_HEAD_BIT = 64;
	//public static final short ENEMY_RIGHT_BIT = 128;
	//public static final short ENEMY_LEFT_BIT = 256;


	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {
		super.render(); //delegate render to playscreen or active screen
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
