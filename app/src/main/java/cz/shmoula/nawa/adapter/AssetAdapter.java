package cz.shmoula.nawa.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cz.shmoula.nawa.R;
import cz.shmoula.nawa.model.Asset;
import cz.shmoula.nawa.view.AssetViewHolder;

/**
 * Data holder for list of assets
 * Created by vbalak on 01/08/15.
 */
public class AssetAdapter extends RecyclerView.Adapter<AssetViewHolder> {
    private List<Asset> data;

    @Override
    public AssetViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_asset, viewGroup, false);

        return new AssetViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AssetViewHolder assetViewHolder, int i) {
        assetViewHolder.setAsset(data.get(i));
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void setData(List<Asset> data) {
        this.data = data;
        notifyDataSetChanged();
    }
}
