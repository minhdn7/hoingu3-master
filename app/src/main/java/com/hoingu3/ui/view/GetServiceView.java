package com.hoingu3.ui.view;

import com.hoingu3.domain.model.GetServiceResponse;

/**
 * Created by MinhDN on 7/5/2018.
 */
public interface GetServiceView extends View{
    void onGetServiceSuccses(GetServiceResponse response);
    void onGetServiceFailed(String message);
    void onGetServiceError(Throwable e);
}
