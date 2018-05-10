package com.hoingu3.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.mkit.hoingu3.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.hoingu3.app.utils.AppDef;
import com.hoingu3.domain.model.GetServiceResponse;
import com.hoingu3.ui.presenter.CheckServicePresenter;
import com.hoingu3.ui.presenter.GetServicePresenter;
import com.hoingu3.ui.view.CheckServiceView;
import com.hoingu3.ui.view.GetServiceView;

import javax.inject.Inject;

public class StartActivity extends BaseActivity implements CheckServiceView, GetServiceView{

    @Inject
    CheckServicePresenter checkServicePresenter;

    @Inject
    GetServicePresenter getServicePresenter;

    private String appgamecode = "hoingu3";
    private Integer os = 1;
    private String auth = "2V3He2oawdc=";
    private String country = "VN";
    private String deviceId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        FirebaseMessaging.getInstance().subscribeToTopic("HoiNgu3");
        deviceId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        AppDef.DEVICE_ID = deviceId;
        initView();
    }

    private void initView() {
        checkServicePresenter.setView(this);
        checkServicePresenter.onViewCreate();
        getServicePresenter.setView(this);
        getServicePresenter.onViewCreate();
        showProgressBar();
        checkServicePresenter.checkService(appgamecode, os, deviceId, auth, country);
    }

    @Override
    public void onCheckServiceSuccses(String response) {
        getServicePresenter.getService(country, os, deviceId, appgamecode);
    }

    @Override
    public void onCheckServiceError(Throwable e) {
        hideProgressBar();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final Intent mainIntent = new Intent(StartActivity.this, HomeActivity.class);
                StartActivity.this.startActivity(mainIntent);
                StartActivity.this.finish();
            }
        }, 2000);
    }

    @Override
    public void onGetServiceSuccses(final GetServiceResponse response) {
        hideProgressBar();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AppDef.IMAGE_AD = response.getData().getItem().get(0).getAvatarFull();
                AppDef.DOWNLOAD_AD = response.getData().getItem().get(0).getUrlDownload();
                final Intent mainIntent = new Intent(StartActivity.this, HomeActivity.class);
                StartActivity.this.startActivity(mainIntent);
                StartActivity.this.finish();
            }
        }, 1000);
    }

    @Override
    public void onGetServiceFailed(String message) {
        hideProgressBar();
    }

    @Override
    public void onGetServiceError(Throwable e) {
        hideProgressBar();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final Intent mainIntent = new Intent(StartActivity.this, HomeActivity.class);
                StartActivity.this.startActivity(mainIntent);
                StartActivity.this.finish();
            }
        }, 2000);
    }
}
