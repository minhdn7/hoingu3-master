package com.hoingu3.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.android.mkit.hoingu3.R;
import com.google.android.gms.ads.AdListener;
import com.hoingu3.app.utils.AppDef;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity {

    @BindView(R.id.view_top)
    RelativeLayout viewTop;
    @BindView(R.id.btn_play_now)
    ImageView btnPlayNow;
    @BindView(R.id.btn_setting)
    ImageView btnSetting;
    @BindView(R.id.btn_rank)
    ImageView btnRank;
    @BindView(R.id.btn_moregame)
    ImageView btnMoregame;
    @BindView(R.id.btn_exit)
    ImageView btnExit;

    private MediaPlayer mPlayer;

    private SeekBar volumeSeekbar = null;
    private AudioManager audioManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        addSounds();
    }

    private void addSounds(){
         mPlayer = MediaPlayer.create(this, R.raw.trong_com);
        mPlayer.start();
    }


    @OnClick({R.id.btn_play_now, R.id.btn_setting, R.id.btn_rank, R.id.btn_moregame, R.id.btn_exit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_play_now:
                startActivity(new Intent(this, PlayActivity.class));
                mPlayer.stop();
                this.finish();
                break;
            case R.id.btn_setting:
                dilogSetting();
                break;
            case R.id.btn_rank:
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                    mInterstitialAd.setAdListener(new AdListener() {
                        @Override
                        public void onAdLoaded() {
                            // Code to be executed when an ad finishes loading.
                        }

                        @Override
                        public void onAdFailedToLoad(int errorCode) {
                            // Code to be executed when an ad request fails.
                            startActivity(new Intent(HomeActivity.this, PlayActivity.class));
                            finish();
                        }

                        @Override
                        public void onAdOpened() {
                            // Code to be executed when the ad is displayed.
                        }

                        @Override
                        public void onAdLeftApplication() {
                            // Code to be executed when the user has left the app.
                        }

                        @Override
                        public void onAdClosed() {
                            // Code to be executed when when the interstitial ad is closed.
                            startActivity(new Intent( HomeActivity.this, PlayActivity.class));
                            finish();
                        }
                    });
                } else {
                    startActivity(new Intent(this, PlayActivity.class));
                    this.finish();
                }
                break;
            case R.id.btn_moregame:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.hippoGammes.SatanChristmas")));
                break;
            case R.id.btn_exit:
                this.finish();
                break;
        }
    }

    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            if(isConnectedNetwork()){
                dialogAdExit(AppDef.IMAGE_AD, AppDef.DOWNLOAD_AD);
            }else {
                dialogExit();
            }
        }

        this.doubleBackToExitPressedOnce = true;
        showToast("Nhấn 2 lần để thoát");

        new android.os.Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    public void dilogSetting(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_setting, null);
        dialogBuilder.setView(dialogView);

        final AlertDialog b = dialogBuilder.create();
        b.setCanceledOnTouchOutside(false);
        b.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        b.show();
        volumeSeekbar = (SeekBar) b.findViewById(R.id.seekBar);
        Button btnOk = (Button) b.findViewById(R.id.btn_ok);
        final ImageView btnSound = (ImageView) b.findViewById(R.id.btn_sound);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        volumeSeekbar.setMax(audioManager
                .getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        volumeSeekbar.setProgress(audioManager
                .getStreamVolume(AudioManager.STREAM_MUSIC));


        volumeSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onStopTrackingTouch(SeekBar arg0)
            {
            }

            @Override
            public void onStartTrackingTouch(SeekBar arg0)
            {
            }

            @Override
            public void onProgressChanged(SeekBar arg0, int progress, boolean arg2)
            {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                        progress, 0);
                if(progress == 0){
                    btnSound.setImageResource(R.mipmap.btn_soundoff);
                }else {
                    btnSound.setImageResource(R.mipmap.btn_soundon);
                }

            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.dismiss();
            }
        });

    }
}
