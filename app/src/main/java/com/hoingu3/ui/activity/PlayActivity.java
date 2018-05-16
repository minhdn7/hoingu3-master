package com.hoingu3.ui.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.mkit.hoingu3.R;
import com.hoingu3.app.utils.AppDef;
import com.hoingu3.ui.adapter.MyDatabaseAdapter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlayActivity extends BaseActivity implements Animation.AnimationListener, MediaPlayer.OnPreparedListener {

    @BindView(R.id.tv_question)
    TextView tvQuestion;
    @BindView(R.id.bt_ans_a)
    Button btAnsA;
    @BindView(R.id.bt_ans_b)
    Button btAnsB;
    @BindView(R.id.bt_ans_c)
    Button btAnsC;
    @BindView(R.id.bt_ans_d)
    Button btAnsD;
    @BindView(R.id.view_top)
    RelativeLayout viewTop;
    @BindView(R.id.tv_tittle)
    TextView tvTittle;
    @BindView(R.id.ic_show)
    ImageView icShow;
    @BindView(R.id.view_question)
    RelativeLayout viewQuestion;
    @BindView(R.id.tv_life)
    TextView tvLife;
    @BindView(R.id.btn_sound)
    ImageView btnSound;
    @BindView(R.id.ln_life)
    LinearLayout lnLife;

    private String sCorrectValue = "a";
    private String gtA = "";
    private String gtB = "";
    private String gtC = "";
    private String gtD = "";

    private Integer minId = 1;
    private Integer maxId = 353;
    private Boolean isPrepare = false;
    private MediaPlayer mPlayer;
    private int random = 1;

    Animation animFadein, animBlink, animZoomIn, animZoomOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        ButterKnife.bind(this);
        initView();
        do {
            random = new Random().nextInt(maxId - minId + 1) + minId;
        } while (!checkIdQuestion(random));

        getData(random);
        checkVoice();
        addAnim();
        addSounds();
    }

    private void addAnim() {
        // load the animation
        animFadein = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        animBlink = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink_not_repeat);
        animZoomIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);
        animZoomOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_out);
        // set animation listener
        animFadein.setAnimationListener(this);
        animBlink.setAnimationListener(this);
    }

    private void addSounds() {
        if(mPlayer == null) {
            mPlayer = MediaPlayer.create(this, R.raw.uh_oh);
            mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    isPrepare = true;
                }
            });

        }

    }

    private void initView() {
        tvLife.setText(String.valueOf(AppDef.LifeScore));
        //set fonts
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/SFUFuturaBook.TTF");
        Typeface face2 = Typeface.createFromAsset(getAssets(), "fonts/SFUFuturaHeavy.TTF");
        tvQuestion.setTypeface(face2);
        tvTittle.setTypeface(face);
        btAnsA.setTypeface(face2);
        btAnsB.setTypeface(face2);
        btAnsC.setTypeface(face2);
        btAnsD.setTypeface(face2);
    }


    public void getData(Integer id) {
        MyDatabaseAdapter myDatabase = new MyDatabaseAdapter(this);
        myDatabase.open();
        SQLiteDatabase database = myDatabase.getMyDatabase();
        if (database != null) {
//            Toast.makeText(this, "data base exist!", Toast.LENGTH_LONG).show();
            String sQuerry = "SELECT * FROM 'tb_dvhainaohoingu' WHERE _id = " + id;
            Cursor cursor = database.rawQuery(sQuerry, null);
            while (cursor.moveToNext()) {
                tvQuestion.setText(cursor.getString(1));

                btAnsA.setText(cursor.getString(2));
                btAnsB.setText(cursor.getString(3));
                btAnsC.setText(cursor.getString(4));
                btAnsD.setText(cursor.getString(5));
                sCorrectValue = cursor.getString(6).trim().toLowerCase();
                gtA = cursor.getString(7).trim();
                gtB = cursor.getString(8).trim();
                gtC = cursor.getString(9).trim();
                gtD = cursor.getString(10).trim();

                try {
                    tvTittle.setText(cursor.getString(11));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    @OnClick({R.id.bt_ans_a, R.id.bt_ans_b, R.id.bt_ans_c, R.id.bt_ans_d, R.id.btn_sound, R.id.btn_moregame})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_ans_a:
                btAnsA.setBackgroundDrawable(getResources().getDrawable(R.mipmap.btn_answer_2));
                checkAnswer("a", gtA, btAnsA);
                break;
            case R.id.bt_ans_b:
                btAnsB.setBackgroundDrawable(getResources().getDrawable(R.mipmap.btn_answer_2));
                checkAnswer("b", gtB, btAnsB);
                break;
            case R.id.bt_ans_c:
                btAnsC.setBackgroundDrawable(getResources().getDrawable(R.mipmap.btn_answer_2));
                checkAnswer("c", gtC, btAnsC);
                break;
            case R.id.bt_ans_d:
                btAnsD.setBackgroundDrawable(getResources().getDrawable(R.mipmap.btn_answer_2));
                checkAnswer("d", gtD, btAnsD);
                break;
            case R.id.btn_sound:
                AppDef.isVoice = !AppDef.isVoice;
                if (!AppDef.isVoice) {
                    btnSound.setImageResource(R.mipmap.btn_soundoff);
                } else {
                    btnSound.setImageResource(R.mipmap.btn_soundon);
                }
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
        }
    }

    public void checkAnswer(String response, String giaiThich, Button btnAnswer) {
        if (!response.toLowerCase().equals(sCorrectValue)) {
            if (AppDef.isVoice) {
                if(mPlayer != null && mPlayer.isPlaying()) {
                    mPlayer.reset();
                    mPlayer.stop();
                    mPlayer.seekTo(0);
                }
                if(isPrepare){
                    mPlayer.start();
                }else {
                    addSounds();
                    mPlayer.start();
                }

            }
            AppDef.LifeScore -= 1;
            tvLife.setText(String.valueOf(AppDef.LifeScore));
            lnLife.startAnimation(animBlink);

        } else {
            if(mPlayer != null && mPlayer.isPlaying()) {
                mPlayer.reset();
                mPlayer.stop();
                mPlayer.seekTo(0);
            }
            AppDef.Score += 1;
            Intent intent = new Intent(PlayActivity.this, AnswerActivity.class);
            intent.putExtra("GIAI_THICH", giaiThich);
            startActivity(intent);
            this.finish();

        }
        if (AppDef.LifeScore <= 0) {
            if(mPlayer != null && mPlayer.isPlaying()) {
                mPlayer.stop();
            }
            startActivity(new Intent(this, GameOverActivity.class));
            this.finish();
        }

    }

    public boolean checkIdQuestion(Integer id) {
        if (AppDef.listPlayId.size() > 0) {
            for (int i = 0; i < AppDef.listPlayId.size(); i++) {
                if (AppDef.listPlayId.get(i).equals(id)) {
                    return false;
                }
            }
            AppDef.listPlayId.add(id);
            return true;
        } else {
            AppDef.listPlayId.add(id);
            return true;
        }
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (mPlayer != null && mPlayer.isPlaying()) {
            mPlayer.stop();
        }
        if (doubleBackToExitPressedOnce) {
            if (isConnectedNetwork()) {
                dialogAdExit(AppDef.IMAGE_AD, AppDef.DOWNLOAD_AD);
            } else {
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

    public void checkVoice() {
        if (!AppDef.isVoice) {
            btnSound.setImageResource(R.mipmap.btn_soundoff);
            if (mPlayer != null && mPlayer.isPlaying()) {
                mPlayer.stop();
            }
        } else {
            btnSound.setImageResource(R.mipmap.btn_soundon);
        }
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }



    @Override
    public void onStop() {
        if (mPlayer != null && mPlayer.isPlaying()) {
            mPlayer.stop();
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPlayer.stop();
        mPlayer.release();
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {

    }
}
