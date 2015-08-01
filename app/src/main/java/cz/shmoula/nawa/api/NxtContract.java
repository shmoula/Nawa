package cz.shmoula.nawa.api;

import cz.shmoula.nawa.model.ResponseGetAllAssets;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Query;

/**
 * Nxt api connector contract
 * Created by vbalak on 01/08/15.
 */
public interface NxtContract {
    @GET("/nxt?requestType=getAllAssets")
    @Headers("Content-Type: application/json;charset=UTF-8")
    void getAllAssets(Callback<ResponseGetAllAssets> callback);
}
