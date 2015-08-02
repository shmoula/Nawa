package cz.shmoula.nawa.model;

import com.google.gson.annotations.SerializedName;

import java.util.Set;

/**
 * POJO with fields of every response - envelops error handling
 * Created by vbalak on 01/08/15.
 */
public class ResponseEnvelope {
    @SerializedName("assets")
    private Set<Asset> assets;

    @SerializedName("trades")
    private Set<Trade> trades;

    @SerializedName("requestProcessingTime")
    private long requestProcessingTime;

    @SerializedName("error")
    private String error;

    public Set<Asset> getAssets() {
        return assets;
    }

    public void setAssets(Set<Asset> assets) {
        this.assets = assets;
    }

    public Set<Trade> getTrades() {
        return trades;
    }

    public void setTrades(Set<Trade> trades) {
        this.trades = trades;
    }

    public long getRequestProcessingTime() {
        return requestProcessingTime;
    }

    public void setRequestProcessingTime(long requestProcessingTime) {
        this.requestProcessingTime = requestProcessingTime;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
