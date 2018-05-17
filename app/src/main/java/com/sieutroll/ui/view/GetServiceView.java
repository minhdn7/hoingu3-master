package com.sieutroll.ui.view;

import com.sieutroll.domain.model.GetServiceResponse;
import com.sieutroll.ui.view.View;

/**
 * Created by MinhDN on 7/5/2018.
 */
public interface GetServiceView extends View {
    void onGetServiceSuccses(GetServiceResponse response);
    void onGetServiceFailed(String message);
    void onGetServiceError(Throwable e);
}
