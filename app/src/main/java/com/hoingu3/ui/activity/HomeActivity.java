package com.hoingu3.ui.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.mkit.hoingu3.R;

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
                break;
            case R.id.btn_rank:
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
}
