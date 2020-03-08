package com.zhaojin.cachingrealm_rxjava_retrofit_2;

import android.app.Application;

import com.zhaojin.cachingrealm_rxjava_retrofit_2.services.core.ApiClient;
import com.zhaojin.cachingrealm_rxjava_retrofit_2.services.core.ApiConfig;

import io.realm.Realm;



public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        createService();
        createDatabase();

    }

    private void createDatabase() {
        Realm.init(this);
    }

    private void createService() {
        ApiConfig apiConfig = new ApiConfig(this, getString(R.string.url_base));
        ApiClient.getInstance().init(apiConfig);
    }
}
