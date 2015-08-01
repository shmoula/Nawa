package cz.shmoula.nawa.service;

import android.app.IntentService;
import android.content.Intent;

import com.activeandroid.ActiveAndroid;

import cz.shmoula.nawa.api.RestClient;
import cz.shmoula.nawa.model.Asset;
import cz.shmoula.nawa.model.ResponseGetAllAssets;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Service for downloading data from web on background
 * Created by vbalak on 01/08/15.
 */
public class DownloadService extends IntentService {
    private static final String TAG = "DownloadService";

    public DownloadService() {
        super(TAG);
    }

    /**
     * Download all assets from web and save locally
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        RestClient.getNxtContract().getAllAssets(new Callback<ResponseGetAllAssets>() {
            @Override
            public void success(ResponseGetAllAssets responseGetAllAssets, Response response) {
                ActiveAndroid.beginTransaction();
                try {
                    for (Asset asset : responseGetAllAssets.getAssets())
                        asset.save();

                    ActiveAndroid.setTransactionSuccessful();
                } finally {
                    ActiveAndroid.endTransaction();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
    }
}
