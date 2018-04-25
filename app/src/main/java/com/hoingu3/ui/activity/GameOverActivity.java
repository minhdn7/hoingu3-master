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
import com.hoingu3.app.utils.AppDef;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GameOverActivity extends BaseActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        ButterKnife.bind(this);
        addSounds();
        initView();
        initControls();
    }

    private void initControls() {
        AppDef.LifeScore = 2;
        AppDef.Score = 0;
        if (AppDef.listPlayId.size() > 0) {
            AppDef.listPlayId.clear();
        }
    }

    private void initView() {
        String sDiemCao = "Điểm cao: " + String.valueOf(AppDef.Score);
        String sDoNgu = "Độ ngu: " + String.valueOf(AppDef.Score + 1);
        tvDiemCao.setText(sDiemCao);
        tvDoNgu.setText(sDoNgu);
    }

    private void addSounds() {
        MediaPlayer mPlayer = MediaPlayer.create(this, R.raw.an_ui);
        mPlayer.start();
    }

    @OnClick(R.id.btn_play_again)
    public void onViewClicked() {


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

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @OnClick({R.id.btn_moregame, R.id.btn_play_again})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_moregame:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.hippoGammes.SatanChristmas")));
                break;
            case R.id.btn_play_again:
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
                            startActivity(new Intent(GameOverActivity.this, PlayActivity.class));
                            finish();
                        }
                    });
                } else {
                    startActivity(new Intent(this, PlayActivity.class));
                    this.finish();
                }
                break;
        }
    }
}
