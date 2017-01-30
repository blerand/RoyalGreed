package com.example.blerandbahtiri.royalgreed;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * ROYAL DICE . BLERAND BAHTIRI
 * Created by blerandbahtiri on 16-02-06.
 */
public class PlayerData {
    private static String PLAYER_DATABASE = "royal_players";

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(PLAYER_DATABASE, Context.MODE_PRIVATE);
    }

    public static String getUsername(Context context, String key) {
        return getPrefs(context).getString(key, "");
    }
    public static int getScore(Context context, String key) {
        return getPrefs(context).getInt(key,0);
    }

    public static void setUsername(Context context,String key, String input) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putString(key, input);
        editor.apply();
    }
    public static void setScore(Context context,String key, int input) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putInt(key, input);
        editor.apply();
    }
}
