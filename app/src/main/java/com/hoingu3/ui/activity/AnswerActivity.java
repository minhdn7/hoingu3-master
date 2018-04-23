package com.hoingu3.ui.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.mkit.hoingu3.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AnswerActivity extends BaseActivity {

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
    }

    private void addSounds(){
        MediaPlayer mPlayer = MediaPlayer.create(this, R.raw.succe_2);
        mPlayer.start();
    }

    private void initView() {
        sGiaiThich = getIntent().getStringExtra("GIAI_THICH");
        tvNoiDung.setText(sGiaiThich);
    }

    @OnClick({R.id.btn_add_lifesaver, R.id.btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_add_lifesaver:
                break;
            case R.id.btn_next:
                startActivity(new Intent(AnswerActivity.this, PlayActivity.class));
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
