package com.example.projetpiece;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences("appKey", 0);
        editor = sharedPreferences.edit();
        editor.apply();
    }

    public void setLogin(boolean login) {
        editor.putBoolean("KEY_LOGIN", login);
        editor.commit();
    }

    public boolean getLogin() {
        return sharedPreferences.getBoolean("KEY_LOGIN", false);
    }

    public void setCourriel(String courriel) {
        editor.putString("KEY_COURRIEL", courriel);
        editor.commit();
    }

    public String getCourriel() {
        return sharedPreferences.getString("KEY_COURRIEL", "");
    }

    public void setID(String id) {
        editor.putString("KEY_ID", id);
        editor.commit();
    }

    public String getID() {
        return sharedPreferences.getString("KEY_ID", "0");
    }

}

