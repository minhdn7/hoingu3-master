package com.hoingu3.ui.presenter;

import com.hoingu3.ui.view.CheckServiceView;

/**
 * Created by MinhDN on 7/5/2018.
 */
public interface CheckServicePresenter extends Presenter<CheckServiceView> {
    void checkService(String appgamecode, Integer os, String deviceid, String auth, String country);
}
