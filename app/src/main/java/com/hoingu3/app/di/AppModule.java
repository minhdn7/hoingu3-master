package com.hoingu3.app.di;

import android.content.Context;


import com.hoingu3.app.LineApplication;
import com.hoingu3.ui.activity.AnswerActivity;
import com.hoingu3.ui.activity.BaseActivity;
import com.hoingu3.ui.activity.GameOverActivity;
import com.hoingu3.ui.activity.HomeActivity;
import com.hoingu3.ui.activity.PlayActivity;
import com.hoingu3.ui.activity.StartActivity;
import com.hoingu3.ui.fragment.BaseFragment;

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
