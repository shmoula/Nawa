package cz.shmoula.nawa.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.List;

import cz.shmoula.nawa.R;
import cz.shmoula.nawa.model.Asset;
import cz.shmoula.nawa.view.AssetViewHolder;

/**
 * Data holder for list of assets
 * Provides simple filtering capabilities - by asset name
 * Created by vbalak on 01/08/15.
 */
public class AssetAdapter extends RecyclerView.Adapter<AssetViewHolder> implements Filterable {
    private List<Asset> allAssets;
    private List<Asset> filteredAssets;

    @Override
    public AssetViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_asset, viewGroup, false);

        return new AssetViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AssetViewHolder assetViewHolder, int i) {
        assetViewHolder.setAsset(filteredAssets.get(i));
    }

    @Override
    public int getItemCount() {
        return filteredAssets == null ? 0 : filteredAssets.size();
    }

    /**
     * Sets ALL data for this adapter
     */
    public void setData(List<Asset> data) {
        allAssets = data;
        filteredAssets = data;
        notifyDataSetChanged();
    }

    /**
     * Sets filtered data - everything visible in the list
     */
    private void setFilteredAssets(List<Asset> data) {
        filteredAssets = data;
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                if(allAssets == null || allAssets.size() < 1)
                    return null;

                String filterString = charSequence.toString().toLowerCase();
                List<Asset> filteredList = new ArrayList<Asset>();

                for(Asset asset : allAssets) {
                    String assetName = asset.getName().toLowerCase();
                    if(assetName.contains(filterString))
                        filteredList.add(asset);
                }

                FilterResults results = new FilterResults();
                results.values = filteredList;
                results.count = filteredList.size();

                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                if(filterResults == null || filterResults.count < 1)
                    return;

                setFilteredAssets((List<Asset>) filterResults.values);
            }
        };
    }
}
