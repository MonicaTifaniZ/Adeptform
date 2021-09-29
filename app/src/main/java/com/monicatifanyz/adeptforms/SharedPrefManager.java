package com.monicatifanyz.adeptforms;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SharedPrefManager {


    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "introslider";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";


    private static  String SHARED_PREF_NAME = "siskar";
    public static final String SP_NAMA = "spNama";

    public static final String SP_SUDAH_LOGIN = "spSudahLogin";

    private final static String TAG = "Shared User";


    SharedPreferences sharedPreferences;
    private SharedPreferences.Editor spEditor;
    Context context;

    public SharedPrefManager(Context context){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        spEditor = sharedPreferences.edit();

    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        spEditor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        spEditor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return sharedPreferences.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }


    public void saveUser(String sharedPrefName, String names) {
//        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
        spEditor.putString(SP_NAMA, names);
        spEditor.commit();
        Log.d(TAG, "save user " +names);
    }

    public String getSpNama(){
        String data = sharedPreferences.getString(SP_NAMA, "");
        Log.d(TAG, "getSpNama " +data);
        return data;

    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(SP_SUDAH_LOGIN, false);
    }

    public void saveSPBoolean(String keySP, boolean value) {
        spEditor.putBoolean(keySP, value);
        spEditor.commit();
    }

    public void logout() {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        spEditor = sharedPreferences.edit();
        spEditor.clear();
        spEditor.commit();
//        context.startActivity(new Intent(context, SplashScreenActivity.class));
    }



//    //Storage File
//    public static final String SHARED_PREF_NAME = "";
//
//    //Username
//    public static final String USER_NAME = "username";
//
//    public static SharedPrefManager mInstance;
//
//    public static Context mCtx;
//
//
//    public SharedPrefManager(Context context) {
//        mCtx = context;
//    }
//
//
//    public static synchronized SharedPrefManager getInstance(Context context) {
//        if (mInstance == null) {
//            mInstance = new SharedPrefManager(context);
//        }
//        return mInstance;
//    }
//
//
//    //method to store user data
//    public void storeUserName(String sharedPrefName, String names) {
//        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString(USER_NAME, names);
//        editor.commit();
//    }
//
//    //check if user is logged in
//    public boolean isLoggedIn() {
//        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
//        return sharedPreferences.getString(USER_NAME, null) != null;
//    }
//
//
//    //find logged in user
//    public String LoggedInUser() {
//        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
//        return sharedPreferences.getString(USER_NAME, null);
//
//    }
//
//
//    //Logout user
//    public void logout() {
//        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.clear();
//        editor.commit();
//        mCtx.startActivity(new Intent(mCtx, MainActivity.class));
//    }

}
