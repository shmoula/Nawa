package cz.shmoula.nawa.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.SerializedName;

/**
 * Model of one asset
 * Created by vbalak on 01/08/15.
 */
@Table(name = "Assets")
public class Asset extends Model {
    @Column(name = "asset", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    @SerializedName("asset")
    private String assetId;

    @Column(name = "account")
    @SerializedName("account")
    private String accountId;

    @Column(name = "name")
    @SerializedName("name")
    private String name;

    @Column(name = "description")
    @SerializedName("description")
    private String description;

    @Column(name = "quantityQNT")
    @SerializedName("quantityQNT")
    private long quantityQnt;

    @Column(name = "decimals")
    @SerializedName("decimals")
    private byte decimals;

    @Column(name = "numberOfAccounts")
    @SerializedName("numberOfAccounts")
    private long numberOfAccounts;

    @Column(name = "accountRS")
    @SerializedName("accountRS")
    private String accountRs;

    @Column(name = "numberOfTransfers")
    @SerializedName("numberOfTransfers")
    private long numberOfTransfers;

    @Column(name = "numberOfTrades")
    @SerializedName("numberOfTrades")
    private long numberOfTrades;

    @Column(name = "watched")
    private boolean watched;

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getQuantityQnt() {
        return quantityQnt;
    }

    public void setQuantityQnt(long quantityQnt) {
        this.quantityQnt = quantityQnt;
    }

    public byte getDecimals() {
        return decimals;
    }

    public void setDecimals(byte decimals) {
        this.decimals = decimals;
    }

    public long getNumberOfAccounts() {
        return numberOfAccounts;
    }

    public void setNumberOfAccounts(long numberOfAccounts) {
        this.numberOfAccounts = numberOfAccounts;
    }

    public String getAccountRs() {
        return accountRs;
    }

    public void setAccountRs(String accountRs) {
        this.accountRs = accountRs;
    }

    public long getNumberOfTransfers() {
        return numberOfTransfers;
    }

    public void setNumberOfTransfers(long numberOfTransfers) {
        this.numberOfTransfers = numberOfTransfers;
    }

    public long getNumberOfTrades() {
        return numberOfTrades;
    }

    public void setNumberOfTrades(long numberOfTrades) {
        this.numberOfTrades = numberOfTrades;
    }

    public boolean isWatched() {
        return watched;
    }

    public void setWatched(boolean watched) {
        this.watched = watched;
    }
}
