package com.hoingu3.ui.presenter;

import com.hoingu3.domain.interactor.UserInteractor;
import com.hoingu3.domain.model.GetServiceResponse;
import com.hoingu3.ui.view.GetServiceView;

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
public class GetServicePresenterImpl implements GetServicePresenter{

    @Inject
    UserInteractor userInteractor;

    GetServiceView getServiceView;

    private CompositeSubscription subscription;

    @Override
    public void setView(GetServiceView view) {
        getServiceView = view;
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
    public void getService(String country, Integer os, String deviceid, String appcode) {
        subscription.add(userInteractor.getService(country, os, deviceid, appcode)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Response<GetServiceResponse>>() {
                    @Override
                    public void call(Response<GetServiceResponse> response) {
                        getServiceView.onGetServiceSuccses(response.body());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable e) {
                        Timber.e(e, e.getMessage());
                        getServiceView.onGetServiceError(e);
                    }
                }));
    }
}