package com.hoingu3.ui.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.mkit.hoingu3.R;
import com.hoingu3.app.utils.AppDef;
import com.hoingu3.ui.adapter.MyDatabaseAdapter;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlayActivity extends BaseActivity {

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
    @BindView(R.id.btn_sound) ImageView btnSound;

    private String sCorrectValue = "";
    private String gtA = "";
    private String gtB = "";
    private String gtC = "";
    private String gtD = "";

    private Integer minId = 1;
    private Integer maxId = 353;
    private Boolean isPlay = true;
    private MediaPlayer mPlayer;
    private int random  = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        ButterKnife.bind(this);
        initView();
        do {
            random = new Random().nextInt(maxId - minId + 1) + minId;
        } while (checkIdQuestion(random));

        getData(random);
        checkVoice();
//        addSounds();
    }

    private void addSounds(){
        mPlayer = MediaPlayer.create(this, R.raw.trong_com);
        mPlayer.setLooping(true);
        mPlayer.start();
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
                checkAnswer("a", gtA);
                break;
            case R.id.bt_ans_b:
                btAnsB.setBackgroundDrawable(getResources().getDrawable(R.mipmap.btn_answer_2));
                checkAnswer("b", gtB);
                break;
            case R.id.bt_ans_c:
                btAnsC.setBackgroundDrawable(getResources().getDrawable(R.mipmap.btn_answer_2));
                checkAnswer("c", gtC);
                break;
            case R.id.bt_ans_d:
                btAnsD.setBackgroundDrawable(getResources().getDrawable(R.mipmap.btn_answer_2));
                checkAnswer("d", gtD);
                break;
            case R.id.btn_sound:
                checkVoice();
                AppDef.isVoice = !AppDef.isVoice;
                break;
            case R.id.btn_moregame:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.hippoGammes.SatanChristmas")));
                break;
        }
    }

    public void checkAnswer(String response, String giaiThich) {
        MediaPlayer mPlayer2 = MediaPlayer.create(this, R.raw.correct);
        mPlayer2.start();
        mPlayer.stop();
        if (!response.toLowerCase().equals(sCorrectValue)) {
            AppDef.LifeScore -= 1;
            tvLife.setText(String.valueOf(AppDef.LifeScore));
        }else {
            AppDef.Score += 1;
        }

        if(AppDef.LifeScore <= 0){

            startActivity(new Intent(this, GameOverActivity.class));
            this.finish();
        }else {
            Intent intent = new Intent(PlayActivity.this, AnswerActivity.class);
            intent.putExtra("GIAI_THICH", giaiThich);
            startActivity(intent);
            this.finish();

        }
        this.finish();
    }

    public boolean checkIdQuestion(Integer id){
        if(AppDef.listPlayId.size() > 0){
            for(int i = 0; i < AppDef.listPlayId.size(); i++){
                if(AppDef.listPlayId.get(i).equals(id)){
                    return false;
                }
            }
            AppDef.listPlayId.add(id);
            return true;
        }else {
            AppDef.listPlayId.add(id);
            return true;
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

    public void checkVoice(){
        if(AppDef.isVoice){
            btnSound.setImageResource(R.mipmap.btn_soundoff);
            mPlayer.stop();
        }else {
            btnSound.setImageResource(R.mipmap.btn_soundon);
            mPlayer = MediaPlayer.create(this, R.raw.trong_com);
            mPlayer.setLooping(true);
            mPlayer.start();
        }
    }

}
