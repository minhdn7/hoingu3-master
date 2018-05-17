package com.sieutroll.domain.interactor;

import com.sieutroll.domain.model.GetServiceResponse;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by MinhDN on 7/5/2018.
 */
public interface UserInteractor {
    Observable<Response<String>> checkService(String appgamecode, Integer os, String deviceid, String auth, String country);
    Observable<Response<GetServiceResponse>> getService(String country, Integer os, String deviceid, String appcode);
}
