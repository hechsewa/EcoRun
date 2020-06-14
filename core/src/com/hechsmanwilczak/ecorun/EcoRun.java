package com.hechsmanwilczak.ecorun;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.hechsmanwilczak.ecorun.Screens.MenuScreen;

public class EcoRun extends Game {
	public SpriteBatch batch;
	public static final int V_WIDTH = 400;
	public static final int V_HEIGHT = 208;
	public static final float PPM = 100;

	public static final short NOTHING_BIT = 0;
	public static final short GROUND_BIT = 1;
	public static final short EARTH_BIT = 2;
	public static final short BIN_BIT = 4;
	public static final short PORTAL_BIT = 8;
	public static final short OIL_BIT = 16;
	public static final short ENEMY_BIT = 32;
	public static final short ENEMY_HEAD_BIT = 64;
	public static final short WATER_BIT = 128;
	public static final short TOUCHED_WATER_BIT = 256;
	public static final short ITEM_BIT = 512;
	public static final short SMOG_MASK_BIT = 1024;
	public static final short PAPER_BIT = 2048;
	public static final short METAL_BIT = 4096;
	public static final short PLASTIC_BIT = 8192;
	public static final short SMOG_BIT = 16384;

	public static AssetManager assetManager;
	public static Music music;
	public static Sound leaf_sound;
	public static Sound trash_sound;
	public static Sound kick_sound;
	public static Sound portal_sound;
	public static Sound smog_sound;
	public static Sound destroy_sound;

	@Override
	public void create () {
		batch = new SpriteBatch();
		assetManager = new AssetManager();
		assetManager.load("sounds/bgmusic.mp3", Music.class);
		assetManager.load("sounds/kick.mp3", Sound.class);
		assetManager.load("sounds/leaf.mp3", Sound.class);
		assetManager.load("sounds/portal.mp3", Sound.class);
		assetManager.load("sounds/trash.wav", Sound.class);
		assetManager.load("sounds/smog.mp3", Sound.class);
		assetManager.load("sounds/destroy.mp3", Sound.class);
		assetManager.finishLoading();
		setScreen(new MenuScreen(this));
	}

	@Override
	public void render () {
		super.render(); //delegate render to playscreen or active screen
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		assetManager.dispose();
	}
}
