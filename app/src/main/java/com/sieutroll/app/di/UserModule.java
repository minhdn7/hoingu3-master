package com.sieutroll.app.di;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.sieutroll.domain.interactor.UserInteractor;
import com.sieutroll.domain.interactor.UserInteractorImpl;
import com.sieutroll.domain.repository.api.UserApi;
import com.sieutroll.ui.presenter.CheckServicePresenter;
import com.sieutroll.ui.presenter.CheckServicePresenterImpl;
import com.sieutroll.ui.presenter.GetServicePresenter;
import com.sieutroll.ui.presenter.GetServicePresenterImpl;

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
