package id.net.uninet.azwar_uninet.utils;

import android.content.Context;

public class Preferences {

    private static final String PREFS_NAME = "id.co.kensington.gunhrissecurity";
    Context c;

    public Preferences(Context c) {
        this.c = c;
    }

    public void savePreferences(String key, int value) {
        this.c.getSharedPreferences(PREFS_NAME, 0).edit().putInt(key, value).apply();
    }

    public void savePreferences(String key, String value) {
        this.c.getSharedPreferences(PREFS_NAME, 0).edit().putString(key, value).apply();
    }

    public void savePreferences(String key, boolean value) {
        this.c.getSharedPreferences(PREFS_NAME, 0).edit().putBoolean(key, value).apply();
    }

//    public void savePreferences(String key, double value) {
//        this.c.getSharedPreferences(PREFS_NAME, 0).edit().putd(key, value).apply();
//    }

    public int getPreferencesInt(String key) {
        return this.c.getSharedPreferences(PREFS_NAME, 0).getInt(key, Integer.MIN_VALUE);
    }

    public String getPreferencesString(String key) {
        return this.c.getSharedPreferences(PREFS_NAME, 0).getString(key, "null");
    }

    public boolean getPreferencesBoolean(String key) {
        return this.c.getSharedPreferences(PREFS_NAME, 0).getBoolean(key, false);
    }

    public void clearAllPreferences() {
        this.c.getSharedPreferences(PREFS_NAME, 0).edit().clear().apply();
    }
}
