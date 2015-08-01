package cz.shmoula.nawa.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;

import com.activeandroid.ActiveAndroid;

import cz.shmoula.nawa.MainActivity;
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
            public void success(ResponseGetAllAssets assets, Response response) {
                if (assets.getAssets() == null) {
                    if (!TextUtils.isEmpty(assets.getError()))
                        broadcastMessage(assets.getError());
                    else
                        broadcastMessage("Assets empty :(");

                    return;
                }

                ActiveAndroid.beginTransaction();
                try {
                    for (Asset asset : assets.getAssets())
                        asset.save();

                    ActiveAndroid.setTransactionSuccessful();
                } finally {
                    ActiveAndroid.endTransaction();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                broadcastMessage(error.getMessage());
            }
        });
    }

    /**
     * Broadcasts message to catch it back in MainActivity
     */
    public void broadcastMessage(String message) {
        Intent localIntent = new Intent(MainActivity.DownloadStateReceiver.ACTION);

        localIntent.putExtra(MainActivity.DownloadStateReceiver.KEY_MESSAGE, message);
        LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
    }
}
