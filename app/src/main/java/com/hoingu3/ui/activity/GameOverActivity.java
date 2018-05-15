package com.hoingu3.ui.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.mkit.hoingu3.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.hoingu3.app.utils.AppDef;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GameOverActivity extends BaseActivity implements RewardedVideoAdListener {

    @BindView(R.id.view_top)
    RelativeLayout viewTop;
    @BindView(R.id.ic_too_sad)
    ImageView icTooSad;
    @BindView(R.id.ic_show)
    TextView icShow;
    @BindView(R.id.tv_diem_cao)
    TextView tvDiemCao;
    @BindView(R.id.tv_do_ngu)
    TextView tvDoNgu;
    @BindView(R.id.btn_play_again)
    ImageView btnPlayAgain;
    @BindView(R.id.btn_sound)
    ImageView btnSound;
    @BindView(R.id.btn_moregame)
    ImageView btnMoregame;
    @BindView(R.id.adView)
    AdView adView;
    boolean doubleBackToExitPressedOnce = false;
    private MediaPlayer mPlayer;
    private boolean isLoadReward = false;
    private Boolean isPrepare = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        ButterKnife.bind(this);
        initView();
        initAdsReward();
        addSounds();
        checkVoice();
    }

    private void resetLife() {
        AppDef.LifeScore = 3;
        AppDef.Score = 0;
        if (AppDef.listPlayId.size() > 0) {
            AppDef.listPlayId.clear();
        }
    }

    private void initAdsReward() {

        MobileAds.initialize(this, ADS_REWARD);

        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);
        mRewardedVideoAd.loadAd(ADS_REWARD,
                new AdRequest.Builder()
                        .build());


    }


    private void initView() {
        String sDiemCao = "Điểm cao: " + String.valueOf(AppDef.Score);
        String sDoNgu = "Độ ngu: " + String.valueOf(AppDef.Score + 1);
        tvDiemCao.setText(sDiemCao);
        tvDoNgu.setText(sDoNgu);
    }

    private void addSounds() {
        mPlayer = MediaPlayer.create(this, R.raw.an_ui);
        mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                isPrepare = true;
            }
        });

    }


    private void showAdReward(){
        if (mRewardedVideoAd.isLoaded()) {
            showProgressBar();
            mRewardedVideoAd.show();
        } else if(!isLoadReward){
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
                        if(mPlayer != null && mPlayer.isPlaying()) {
                            mPlayer.stop();
                        }
                        startActivity(new Intent(GameOverActivity.this, PlayActivity.class));
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
                        if (AppDef.LifeScore < 5) {
                            AppDef.LifeScore += 1;
                        }
                        if(mPlayer != null && mPlayer.isPlaying()) {
                            mPlayer.stop();
                        }
                        startActivity(new Intent(GameOverActivity.this, PlayActivity.class));
                        finish();
                    }
                });
            }
        } else {
            showDialog("Đừng nóng, phao đang trong quá trình vận chuyển, các hạ vui lòng ăn miếng bánh uống miếng nước cho hạ hỏa rồi thử lại nhé!"
                    + "\n" + "Yêu thương:X");
        }
    }




    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            if(mPlayer != null && mPlayer.isPlaying()){
                mPlayer.stop();
            }
            if(isConnectedNetwork()){
                dialogAdExit(AppDef.IMAGE_AD, AppDef.DOWNLOAD_AD);
            }else {
                dialogExit();
            }
        }

        this.doubleBackToExitPressedOnce = true;
        showToast("Nhấn 2 lần để thoát");

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @OnClick({R.id.btn_moregame, R.id.btn_play_again, R.id.btn_sound, R.id.btn_add_lifesaver})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_moregame:
                if(mPlayer != null && mPlayer.isPlaying()) {
                    mPlayer.stop();
                }
                if(AppDef.DOWNLOAD_AD != null && !AppDef.DOWNLOAD_AD.equals("")){
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(AppDef.DOWNLOAD_AD)));
                }else {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://112.213.94.52:8186/dl/moreapplives?source=app_code")));
                }
                break;
            case R.id.btn_sound:
                AppDef.isVoice = !AppDef.isVoice;
                if(!AppDef.isVoice){
                    btnSound.setImageResource(R.mipmap.btn_soundoff);
                    if(mPlayer.isPlaying()){
                        mPlayer.stop();
                    }
                } else {
                    btnSound.setImageResource(R.mipmap.btn_soundon);
                }
                break;
            case R.id.btn_play_again:
                resetLife();
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
                            if(mPlayer != null && mPlayer.isPlaying()) {
                                mPlayer.stop();
                            }
                            startActivity(new Intent(GameOverActivity.this, PlayActivity.class));
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
                            if(mPlayer != null && mPlayer.isPlaying()) {
                                mPlayer.stop();
                            }
                            startActivity(new Intent(GameOverActivity.this, PlayActivity.class));
                            finish();
                        }
                    });
                } else {
                    if(mPlayer != null && mPlayer.isPlaying()) {
                        mPlayer.stop();
                    }
                    startActivity(new Intent(this, PlayActivity.class));
                    this.finish();
                }
                break;
            case R.id.btn_add_lifesaver:
                showAdReward();
                break;
        }
    }


    public void checkVoice() {
        if(!AppDef.isVoice){
            btnSound.setImageResource(R.mipmap.btn_soundoff);
            if(mPlayer != null && mPlayer.isPlaying()){
                mPlayer.stop();
            }
        } else {
            btnSound.setImageResource(R.mipmap.btn_soundon);
            if (mPlayer != null && mPlayer.isPlaying()) {
                mPlayer.stop();
            }
            if(isPrepare){
                mPlayer.start();
            }else {
                addSounds();
                mPlayer.start();
            }
        }
    }

    @Override
    public void onRewardedVideoAdLoaded() {
        isLoadReward = true;
    }

    @Override
    public void onRewardedVideoAdOpened() {
        hideProgressBar();
    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {
        hideProgressBar();
        if(mPlayer != null && mPlayer.isPlaying()) {
            mPlayer.stop();
        }
        startActivity(new Intent(GameOverActivity.this, PlayActivity.class));
        this.finish();
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
        hideProgressBar();
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {
        isLoadReward = false;
        hideProgressBar();
    }

    @Override
    public void onRewardedVideoCompleted() {
        if (AppDef.LifeScore < 5) {
            AppDef.LifeScore += 1;
        }
        if(mPlayer != null && mPlayer.isPlaying()) {
            mPlayer.stop();
        }
        startActivity(new Intent(GameOverActivity.this, PlayActivity.class));
        this.finish();
    }

    @Override
    public void onStop() {
        if (mPlayer != null && mPlayer.isPlaying()) {
            mPlayer.stop();
        }
        super.onStop();
    }
}
