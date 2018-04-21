package com.hoingu3.ui.activity;

import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.btn_play_now, R.id.btn_setting, R.id.btn_rank, R.id.btn_moregame, R.id.btn_exit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_play_now:
                startActivity(new Intent(this, PlayActivity.class));
                break;
            case R.id.btn_setting:
                break;
            case R.id.btn_rank:
                break;
            case R.id.btn_moregame:
                break;
            case R.id.btn_exit:
                break;
        }
    }
}
