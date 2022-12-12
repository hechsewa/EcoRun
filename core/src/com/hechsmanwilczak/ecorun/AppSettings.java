package com.hechsmanwilczak.ecorun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class AppSettings {
    private static final String MUSIC_VOLUME = "volume";
    private static final String PREFS_NAME = "pref";

    protected static Preferences getPrefs() {
        return Gdx.app.getPreferences(PREFS_NAME);
    }

    public static float getMusicVolume() {
        return getPrefs().getFloat(MUSIC_VOLUME, 0.5f);
    }

    public static void setMusicVolume(float volume) {
        getPrefs().putFloat(MUSIC_VOLUME, volume);
        getPrefs().flush();
    }
}
