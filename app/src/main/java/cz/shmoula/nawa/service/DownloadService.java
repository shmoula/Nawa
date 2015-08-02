package cz.shmoula.nawa.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;

import com.activeandroid.ActiveAndroid;

import cz.shmoula.nawa.MainActivity;
import cz.shmoula.nawa.api.RestClient;
import cz.shmoula.nawa.model.Asset;
import cz.shmoula.nawa.model.ResponseEnvelope;
import cz.shmoula.nawa.model.Trade;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Service for downloading data from web on background
 * Created by vbalak on 01/08/15.
 */
public class DownloadService extends IntentService {
    private static final String TAG = "DownloadService";

    public static final String ACTION = "cz.shmoula.nawa.START_DOWNLOAD";
    public static final String KEY_DOWNLOAD_ASSETS = "downloadAssets";
    public static final String KEY_DOWNLOAD_TRADES = "downloadTrades";
    public static final String KEY_ASSET_ID = "assetId";

    public DownloadService() {
        super(TAG);
    }

    /**
     * Download all assets from web and save locally
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent.hasExtra(ACTION)) {
            if (KEY_DOWNLOAD_ASSETS.equals(intent.getStringExtra(ACTION)))
                getAllAssets();
            else if (KEY_DOWNLOAD_TRADES.equals(intent.getStringExtra(ACTION)))
                getTradesForAsset(intent.getStringExtra(KEY_ASSET_ID));
        }
    }

    /**
     * Initiates query for getting all assets list
     */
    public void getAllAssets() {
        RestClient.getNxtContract().getAllAssets(new Callback<ResponseEnvelope>() {
            @Override
            public void success(ResponseEnvelope responseEnvelope, Response response) {
                if (responseEnvelope.getAssets() == null) {
                    if (!TextUtils.isEmpty(responseEnvelope.getError()))
                        broadcastMessage(responseEnvelope.getError());
                    else
                        broadcastMessage("Assets empty :(");

                    return;
                }

                ActiveAndroid.beginTransaction();
                try {
                    for (Asset asset : responseEnvelope.getAssets())
                        asset.save();

                    ActiveAndroid.setTransactionSuccessful();
                } finally {
                    ActiveAndroid.endTransaction();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
                broadcastMessage(error.getMessage());
            }
        });
    }

    /**
     * Downloads trades history for specified asset
     */
    public void getTradesForAsset(final String assetId) {
        RestClient.getNxtContract().getTrades(assetId, new Callback<ResponseEnvelope>() {
            @Override
            public void success(ResponseEnvelope responseEnvelope, Response response) {
                if (responseEnvelope.getTrades() == null) {
                    if (!TextUtils.isEmpty(responseEnvelope.getError()))
                        broadcastMessage(responseEnvelope.getError());
                    else
                        broadcastMessage("Trades empty :(");

                    return;
                }

                Asset asset = Asset.findAssetByAssetId(assetId);

                ActiveAndroid.beginTransaction();
                try {
                    for (Trade trade : responseEnvelope.getTrades()) {
                        trade.setAsset(asset);
                        trade.save();
                    }

                    ActiveAndroid.setTransactionSuccessful();

                    broadcastTradesDownloaded();

                } finally {
                    ActiveAndroid.endTransaction();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
                broadcastMessage(error.getMessage());
            }
        });
    }

    /**
     * Broadcasts message to catch it back in MainActivity
     */
    public void broadcastMessage(String message) {
        Intent localIntent = new Intent(MainActivity.DownloadStateReceiver.ACTION);

        localIntent.putExtra(MainActivity.DownloadStateReceiver.TYPE, MainActivity.DownloadStateReceiver.KEY_MESSAGE);
        localIntent.putExtra(MainActivity.DownloadStateReceiver.KEY_MESSAGE, message);
        LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
    }

    /**
     * Broadcasts information about finished downloading back to MainActivity
     */
    public void broadcastTradesDownloaded() {
        Intent localIntent = new Intent(MainActivity.DownloadStateReceiver.ACTION);

        localIntent.putExtra(MainActivity.DownloadStateReceiver.TYPE, MainActivity.DownloadStateReceiver.KEY_TRADES_DOWNLOADED);
        LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
    }

    /**
     * Initiates assets data downloading
     */
    public static void downloadAssets(Context context) {
        Intent downloadData = new Intent(context, DownloadService.class);
        downloadData.putExtra(DownloadService.ACTION, DownloadService.KEY_DOWNLOAD_ASSETS);
        context.startService(downloadData);
    }

    /**
     * Initiates trades list downloading for specified asset
     */
    public static void downloadTradesForAsset(Context context, String assetId) {
        Intent downloadData = new Intent(context, DownloadService.class);
        downloadData.putExtra(DownloadService.ACTION, DownloadService.KEY_DOWNLOAD_TRADES);
        downloadData.putExtra(DownloadService.KEY_ASSET_ID, assetId);
        context.startService(downloadData);
    }
}
