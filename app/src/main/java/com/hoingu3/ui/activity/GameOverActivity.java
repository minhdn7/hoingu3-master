package com.hoingu3.ui.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.mkit.hoingu3.R;
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
    }

    private void addSounds(){
        MediaPlayer mPlayer = MediaPlayer.create(this, R.raw.an_ui);
        mPlayer.start();
    }

    @OnClick(R.id.btn_play_again)
    public void onViewClicked() {
        AppDef.LifeScore = 2;
        startActivity(new Intent(this, PlayActivity.class));
        this.finish();
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
