package cz.shmoula.nawa.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * POJO with informations of single trade
 * Created by vbalak on 02/08/15.
 */
@Table(name = "Trades")
public class Trade extends Model {
    private static final String COLUMN_ASSET_ID = "fkAsset";
    private static final String COLUMN_TIMESTAMP = "timestamp";

    @Column(name = COLUMN_TIMESTAMP)
    @SerializedName("timestamp")
    private long timestamp;

    @Column(name = "quantity")
    @SerializedName("quantityQNT")
    private long quantityQnt;

    @Column(name = "price")
    @SerializedName("priceNQT")
    private long priceNqt;

    @Column(name = "decimals")
    @SerializedName("decimals")
    private int decimals;

    @Column(name = COLUMN_ASSET_ID, onUpdate = Column.ForeignKeyAction.CASCADE, onDelete = Column.ForeignKeyAction.CASCADE)
    private Asset assetItem;

    public static List<Trade> findTradesByAssetId(Long assetId, int limit) {
        return new Select().from(Trade.class).limit(limit).where(COLUMN_ASSET_ID + " = ?", assetId).orderBy(COLUMN_TIMESTAMP + " DESC").execute();
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getQuantityQnt() {
        return quantityQnt;
    }

    public void setQuantityQnt(long quantityQnt) {
        this.quantityQnt = quantityQnt;
    }

    public long getPriceNqt() {
        return priceNqt;
    }

    public void setPriceNqt(long priceNqt) {
        this.priceNqt = priceNqt;
    }

    public Asset getAsset() {
        return assetItem;
    }

    public void setAsset(Asset asset) {
        assetItem = asset;
    }

    public int getDecimals() {
        return decimals;
    }

    public void setDecimals(int decimals) {
        this.decimals = decimals;
    }

    /**
     * Returns price in NXT
     */
    public double getPrice() {
        return (priceNqt / Math.pow(10, 8 - decimals));
    }
}
