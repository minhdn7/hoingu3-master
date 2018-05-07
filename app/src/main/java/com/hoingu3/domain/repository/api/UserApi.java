package com.hoingu3.domain.repository.api;

import com.hoingu3.domain.model.GetServiceResponse;
import com.hoingu3.domain.repository.ServiceUrl;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface UserApi {
    @GET(ServiceUrl.ChECK_SERVICE)
    Observable<Response<String>> checkService(@Query("appgamecode") String appgamecode,
                                              @Query("os") Integer os,
                                              @Query("deviceid") String deviceid,
                                              @Query("auth") String auth,
                                              @Query("country") String country
                                              );

    @GET(ServiceUrl.GET_SERVICE)
    Observable<Response<GetServiceResponse>> getService(@Query("country") String country,
                                                        @Query("os") Integer os,
                                                        @Query("deviceid") String deviceid,
                                                        @Query("appcode") String appcode);
}
