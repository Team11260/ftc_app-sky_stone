package org.upacreekrobotics.dashboard;

import android.content.Context;
import android.content.SharedPreferences;

import org.firstinspires.ftc.robotcore.internal.system.AppUtil;

public class PreferencesHandler {

    private final SharedPreferences sharedPreferences;

    public PreferencesHandler() {
        sharedPreferences = AppUtil.getInstance().getActivity().getSharedPreferences("UP_A_CREEK_FTC_PREFERENCES", Context.MODE_PRIVATE);
    }

    public void putString(String key, String value) {
        sharedPreferences.edit().putString(key, value).apply();
    }

    public void putInt(String key, int value) {
        sharedPreferences.edit().putInt(key, value).apply();
    }

    public void putFloat(String key, float value) {
        sharedPreferences.edit().putFloat(key, value).apply();
    }

    public void putBoolean(String key, boolean value) {
        sharedPreferences.edit().putBoolean(key, value).apply();
    }

    public String getString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    public int getInt(String key, int defaultValue) {
        return sharedPreferences.getInt(key, defaultValue);
    }

    public float getFloat(String key, float defaultValue) {
        return sharedPreferences.getFloat(key, defaultValue);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }
}
