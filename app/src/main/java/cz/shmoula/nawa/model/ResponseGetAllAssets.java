package cz.shmoula.nawa.model;

import com.google.gson.annotations.SerializedName;

import java.util.Set;

/**
 * POJO with fields of getAllAssets response
 * Created by vbalak on 01/08/15.
 */
public class ResponseGetAllAssets {
    @SerializedName("assets")
    private Set<Asset> assets;

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
