package com.hoingu3.ui.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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

public class AnswerActivity extends BaseActivity implements RewardedVideoAdListener {

    @BindView(R.id.view_top)
    RelativeLayout viewTop;
    @BindView(R.id.tv_noi_dung)
    TextView tvNoiDung;
    @BindView(R.id.btn_add_lifesaver)
    ImageView btnAddLifesaver;
    @BindView(R.id.btn_next)
    ImageView btnNext;
    @BindView(R.id.btn_sound)
    ImageView btnSound;
    @BindView(R.id.btn_moregame)
    ImageView btnMoregame;
    @BindView(R.id.adView)
    AdView adView;
    private String sGiaiThich = "";
    private MediaPlayer mPlayer;
    private boolean isLoadReward = false;
    private Boolean isPrepare = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        ButterKnife.bind(this);
        initView();
        addSounds();
        checkVoice();
        initAdsReward();
    }

    private void initAdsReward() {

        MobileAds.initialize(this, ADS_REWARD);

        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);
        mRewardedVideoAd.loadAd(ADS_REWARD,
                new AdRequest.Builder()
                        .build());


    }


    private void addSounds() {
        mPlayer = MediaPlayer.create(this, R.raw.succe_2);
        mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                isPrepare = true;
            }
        });

    }

    private void initView() {
        sGiaiThich = getIntent().getStringExtra("GIAI_THICH");
        tvNoiDung.setText(sGiaiThich);
        // set font
        Typeface face2 = Typeface.createFromAsset(getAssets(), "fonts/SFUFuturaHeavy.TTF");
        tvNoiDung.setTypeface(face2);
    }

    @OnClick({R.id.btn_add_lifesaver, R.id.btn_next, R.id.btn_moregame, R.id.btn_sound})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_add_lifesaver:
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
                                startActivity(new Intent(AnswerActivity.this, PlayActivity.class));
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
                                startActivity(new Intent(AnswerActivity.this, PlayActivity.class));
                                finish();
                            }
                        });
                    }
                } else {
                    showDialog("Đừng nóng, phao đang trong quá trình vận chuyển, các hạ vui lòng ăn miếng bánh uống miếng nước cho hạ hỏa rồi thử lại nhé!"
                            + "\n" + "Yêu thương:X");
                }
                break;
            case R.id.btn_next:
                if(mPlayer != null && mPlayer.isPlaying()) {
                    mPlayer.stop();
                }
                startActivity(new Intent(AnswerActivity.this, PlayActivity.class));
                this.finish();
                break;
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
                checkVoice();
                AppDef.isVoice = !AppDef.isVoice;
                break;
        }
    }

    boolean doubleBackToExitPressedOnce = false;

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

    @Override
    public void onRewardedVideoAdLoaded() {
        isLoadReward = true;
    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {
        hideProgressBar();
    }

    @Override
    public void onRewardedVideoAdClosed() {
        hideProgressBar();
        if(mPlayer != null && mPlayer.isPlaying()) {
            mPlayer.stop();
        }
        startActivity(new Intent(AnswerActivity.this, PlayActivity.class));
        this.finish();
    }

    @Override
    public void onRewarded(RewardItem reward) {
        hideProgressBar();
//        Toast.makeText(this, "onRewarded! currency: " + reward.getType() + "  amount: " +
//                reward.getAmount(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {
//        showToast("RewardVideo fail");
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
        startActivity(new Intent(AnswerActivity.this, PlayActivity.class));
        this.finish();
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
    public void onStop() {
        if (mPlayer != null && mPlayer.isPlaying()) {
            mPlayer.stop();
        }
        super.onStop();
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
