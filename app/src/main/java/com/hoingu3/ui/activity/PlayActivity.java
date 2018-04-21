package com.hoingu3.ui.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
    private String sCorrectValue = "";
    private String gtA = "";
    private String gtB = "";
    private String gtC = "";
    private String gtD = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        ButterKnife.bind(this);
        initView();
        int random = new Random().nextInt(1) + 353;
        getData(random);
    }

    private void initView() {
        tvLife.setText(String.valueOf(AppDef.LifeScore));
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
                tvTittle.setText(cursor.getString(11));
                btAnsA.setText(cursor.getString(2));
                btAnsB.setText(cursor.getString(3));
                btAnsC.setText(cursor.getString(4));
                btAnsD.setText(cursor.getString(5));
                sCorrectValue = cursor.getString(6).trim().toLowerCase();
                gtA = cursor.getString(7).trim();
                gtB = cursor.getString(8).trim();
                gtC = cursor.getString(9).trim();
                gtD = cursor.getString(10).trim();

            }
        }
    }

    @OnClick({R.id.bt_ans_a, R.id.bt_ans_b, R.id.bt_ans_c, R.id.bt_ans_d})
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
        }
    }

    public void checkAnswer(String response, String giaiThich) {
        if (!response.toLowerCase().equals(sCorrectValue)) {
            AppDef.LifeScore -= 1;
            tvLife.setText(String.valueOf(AppDef.LifeScore));
        }

        if(AppDef.LifeScore <= 0){
            startActivity(new Intent(this, GameOverActivity.class));
        }else {
            Intent intent = new Intent(PlayActivity.this, AnswerActivity.class);
            intent.putExtra("GIAI_THICH", giaiThich);
            startActivity(intent);
        }
    }


}
