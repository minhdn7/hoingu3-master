package com.sieutroll.ui.view;

/**
 * Created by MinhDN on 7/5/2018.
 */
public interface CheckServiceView extends View {
    void onCheckServiceSuccses(String response);
    void onCheckServiceError(Throwable e);
}
