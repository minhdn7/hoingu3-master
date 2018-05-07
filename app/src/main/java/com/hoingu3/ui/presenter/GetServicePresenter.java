package com.hoingu3.ui.presenter;

import com.hoingu3.ui.view.CheckServiceView;
import com.hoingu3.ui.view.GetServiceView;

/**
 * Created by MinhDN on 7/5/2018.
 */
public interface GetServicePresenter extends Presenter<GetServiceView> {
    void getService(String country, Integer os, String deviceid, String appcode);
}
