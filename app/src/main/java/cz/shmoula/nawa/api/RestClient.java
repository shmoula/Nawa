package cz.shmoula.nawa.api;

import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Singleton with nxtContract instance
 * Created by vbalak on 01/08/15.
 */
public class RestClient {
    private static final String BASE_URL = "http://jnxt.org:7876/";
    private static NxtContract nxtContract;

    static {
        setupRestClient();
    }

    private RestClient() {
    }

    private static final int CONNECT_TIMEOUT_MILLIS = 10 * 1000;
    private static final int READ_TIMEOUT_MILLIS = 30 * 1000;

    private static void setupRestClient() {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(CONNECT_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
        okHttpClient.setReadTimeout(READ_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setClient(new OkClient(okHttpClient))
                //.setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(BASE_URL).build();

        nxtContract = restAdapter.create(NxtContract.class);
    }

    public static NxtContract getNxtContract() {
        return nxtContract;
    }
}