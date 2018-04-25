package com.hoingu3.ui.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.mkit.hoingu3.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
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
    private String sGiaiThich = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        ButterKnife.bind(this);
        initView();
        addSounds();
        initAdsReward();
    }

    private void initAdsReward() {
//        MobileAds.initialize(this, ADS_REWARD);
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);
//        mRewardedVideoAd.loadAd(ADS_REWARD,
//                new AdRequest.Builder().build());

    }

    private void addSounds(){
        MediaPlayer mPlayer = MediaPlayer.create(this, R.raw.succe_2);
        mPlayer.start();
    }

    private void initView() {
        sGiaiThich = getIntent().getStringExtra("GIAI_THICH");
        tvNoiDung.setText(sGiaiThich);
        // set font
        Typeface face2 = Typeface.createFromAsset(getAssets(), "fonts/SFUFuturaHeavy.TTF");
        tvNoiDung.setTypeface(face2);
    }

    @OnClick({R.id.btn_add_lifesaver, R.id.btn_next, R.id.btn_moregame})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_add_lifesaver:
                mRewardedVideoAd.loadAd(ADS_REWARD, new AdRequest.Builder().build());
                if (mRewardedVideoAd.isLoaded()) {
                    mRewardedVideoAd.show();
                }
                break;
            case R.id.btn_next:
                startActivity(new Intent(AnswerActivity.this, PlayActivity.class));
                this.finish();
                break;
            case R.id.btn_moregame:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.hippoGammes.SatanChristmas")));
                break;
        }
    }

    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
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

    @Override
    public void onRewardedVideoAdLoaded() {

    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {
        startActivity(new Intent(AnswerActivity.this, PlayActivity.class));
        this.finish();
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {

    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {
        showToast("Loading video fail");
    }

    @Override
    public void onRewardedVideoCompleted() {
        if(AppDef.LifeScore < 5){
            AppDef.LifeScore += 1;
        }
        startActivity(new Intent(AnswerActivity.this, PlayActivity.class));
        this.finish();
    }
}
