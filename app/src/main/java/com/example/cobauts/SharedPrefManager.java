package com.example.cobauts;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

public class SharedPrefManager {
    public static final String SP_NEWS_APP="spNewsApp";
    public static final String SP_EMAIL = "spEmail";
//    public static final String SP_LOGIN = "spPassword";
    public static final String SP_SUDAH_LOGIN = "spSudahLogin";

    SharedPreferences sp;
    SharedPreferences.Editor spEditor;
    private static SharedPrefManager instance = null;

    public SharedPrefManager(Context context){
        sp = context.getSharedPreferences(SP_NEWS_APP, Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    public void saveSPString(String keySP, String value){
        spEditor.putString(keySP, value);
        spEditor.commit();
    }
    public void saveSPInt(String keySP, int value){
        spEditor.putInt(keySP, value);
        spEditor.commit();
    }

    public void saveSPBoolean(String keySP, boolean value){
        spEditor.putBoolean(keySP, value);
        spEditor.commit();
    }

    public void clearSharedPref(@NonNull Context context) {
        SharedPreferences pref = context.getSharedPreferences(SP_EMAIL, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.apply();
    }

    public Boolean getSPLogout(){
        return sp.getBoolean(SP_SUDAH_LOGIN, true);
    }

    public String getSpEmail(){
        return sp.getString(SP_EMAIL,"");
    }

//    public String getSpPassword(){
//        return sp.getString(SP_PASSWORD,"");
//    }

    public Boolean getSPSudahLogin(){
        return sp.getBoolean(SP_SUDAH_LOGIN, false);
    }

}
