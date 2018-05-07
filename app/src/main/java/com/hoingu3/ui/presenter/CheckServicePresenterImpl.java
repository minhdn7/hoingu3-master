package com.hoingu3.ui.presenter;

import com.hoingu3.domain.interactor.UserInteractor;
import com.hoingu3.ui.view.CheckServiceView;

import javax.inject.Inject;

import retrofit2.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by MinhDN on 7/5/2018.
 */
public class CheckServicePresenterImpl implements CheckServicePresenter{

    @Inject
    UserInteractor userInteractor;

    CheckServiceView checkServiceView;

    private CompositeSubscription subscription;
    @Override
    public void setView(CheckServiceView view) {
        checkServiceView = view;
    }

    @Override
    public void onViewCreate() {
        subscription = new CompositeSubscription();
    }

    @Override
    public void onViewStart() {

    }

    @Override
    public void onViewResume() {

    }

    @Override
    public void onViewPause() {

    }

    @Override
    public void onViewStop() {

    }

    @Override
    public void onViewDestroy() {
        subscription.unsubscribe();
    }

    @Override
    public void checkService(String appgamecode, Integer os, String deviceid, String auth, String country) {
        subscription.add(userInteractor.checkService(appgamecode, os, deviceid, auth, country)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Response<String>>() {
                    @Override
                    public void call(Response<String> response) {
                        checkServiceView.onCheckServiceSuccses(response.body());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable e) {
                        Timber.e(e, e.getMessage());
                        checkServiceView.onCheckServiceError(e);
                    }
                }));
    }
}
