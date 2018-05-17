package com.sieutroll.app.di;

import android.content.Context;

import com.sieutroll.app.LineApplication;
import com.sieutroll.ui.activity.AnswerActivity;
import com.sieutroll.ui.activity.BaseActivity;
import com.sieutroll.ui.activity.GameOverActivity;
import com.sieutroll.ui.activity.HomeActivity;
import com.sieutroll.ui.activity.PlayActivity;
import com.sieutroll.ui.activity.StartActivity;
import com.sieutroll.ui.fragment.BaseFragment;

import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
/**
 * Created by LiKaLi on 1/22/2018.
 */
@Module(
        includes = {

                UserModule.class,
                NetModule.class
        },
        injects = {
                //App
                LineApplication.class,

                // - view
                BaseActivity.class,
                BaseFragment.class,


                //Activity

                StartActivity.class,
                HomeActivity.class,
                PlayActivity.class,
                GameOverActivity.class,
                AnswerActivity.class,
//        //Fragment

//        ThongBaoFragment.class
        }, library = true)
public class AppModule {

    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }

//    @Provides @Singleton public Context provideApplicationContext() {
//        return this.context;
//    }
}
