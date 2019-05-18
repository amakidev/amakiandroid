package br.com.victorcatao.amaki.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import br.com.victorcatao.amaki.Constants;

public class PreferencesHelper {

    public static final String SHARED_PREFERENCES_NAME = Constants.PACKAGE_NAME + ".SHARED_PREFERENCES";

    public static final String PREF_SESSION_COOKIE = SHARED_PREFERENCES_NAME + ".PREF_SESSION_COOKIE";
    public static final String PREF_USER_ID = SHARED_PREFERENCES_NAME + ".PREF_USER_ID";
    public static final String PREF_BASIC_AUTH = SHARED_PREFERENCES_NAME + ".PREF_BASIC_AUTH";

    private SharedPreferences mSharedPreferences;

    private PreferencesHelper(Context context) {
        mSharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    private static PreferencesHelper sInstance;

    public static synchronized void initializeInstance(Context context) {
        if (sInstance == null) {
            sInstance = new PreferencesHelper(context);
        }
    }

    public static synchronized PreferencesHelper getInstance() {
        if (sInstance == null) {
            throw new IllegalStateException(PreferencesHelper.class.getSimpleName() +
                    " is not initialized, call initializeInstance(..) method first.");
        }
        return sInstance;
    }

    public void putSessionCookie(String sessionCookie){
        mSharedPreferences.edit().putString(PREF_SESSION_COOKIE, sessionCookie).apply();
    }

    public String getSessionCookie(){
        return mSharedPreferences.getString(PREF_SESSION_COOKIE, "");
    }

    public String getBasicAuth() {
        return mSharedPreferences.getString(PREF_BASIC_AUTH, "");
    }

    public void clearBasicAuth() {
        mSharedPreferences.edit().putString(PREF_BASIC_AUTH, "").apply();
    }

    public void putUserId(String id){
        mSharedPreferences.edit().putString(PREF_USER_ID, id).apply();
    }

    public String getUserId(){
        return mSharedPreferences.getString(PREF_USER_ID, "");
    }

    public boolean isLogged(){
        return !getSessionCookie().isEmpty();
    }

    public void clearSharedPref(){
        mSharedPreferences.edit().clear().apply();
    }
}