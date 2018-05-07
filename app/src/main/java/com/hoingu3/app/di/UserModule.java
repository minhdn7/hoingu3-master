package com.hoingu3.app.di;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hoingu3.domain.interactor.UserInteractor;
import com.hoingu3.domain.interactor.UserInteractorImpl;
import com.hoingu3.domain.repository.api.UserApi;
import com.hoingu3.ui.presenter.CheckServicePresenter;
import com.hoingu3.ui.presenter.CheckServicePresenterImpl;
import com.hoingu3.ui.presenter.GetServicePresenter;
import com.hoingu3.ui.presenter.GetServicePresenterImpl;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by MinhDN on 5/2/2018.
 */
@Module(complete = false, library = true)
public class UserModule {
    @Provides
    UserApi provideIService(Retrofit.Builder retrofitBuilder) {
        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit =
                retrofitBuilder.addConverterFactory(GsonConverterFactory.create(gson)).build();

        return retrofit.create(UserApi.class);
    }

    @Provides
    UserInteractor provideUserInteractor(UserInteractorImpl userInteractor) {
        return userInteractor;
    }

    @Provides
    GetServicePresenter getServicePresenter(GetServicePresenterImpl getServicePresenter){
        return getServicePresenter;
    }

    @Provides
    CheckServicePresenter checkServicePresenter(CheckServicePresenterImpl checkServicePresenter){
        return checkServicePresenter;
    }


}
