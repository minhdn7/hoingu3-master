package com.sieutroll.ui.presenter;
import com.sieutroll.ui.view.GetServiceView;

/**
 * Created by MinhDN on 7/5/2018.
 */
public interface GetServicePresenter extends Presenter<GetServiceView> {
    void getService(String country, Integer os, String deviceid, String appcode);
}
