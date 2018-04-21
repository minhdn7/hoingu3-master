package com.hoingu3.ui.activity;

import android.content.Intent;
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
    }

    @OnClick(R.id.btn_play_again)
    public void onViewClicked() {
        AppDef.LifeScore = 2;
        startActivity(new Intent(this, PlayActivity.class));
    }
}
