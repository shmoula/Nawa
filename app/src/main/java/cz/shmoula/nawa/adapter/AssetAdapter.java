package cz.shmoula.nawa.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.daimajia.swipe.SwipeLayout;

import java.util.ArrayList;
import java.util.List;

import cz.shmoula.nawa.R;
import cz.shmoula.nawa.api.RestClient;
import cz.shmoula.nawa.model.Asset;
import cz.shmoula.nawa.model.ResponseEnvelope;
import cz.shmoula.nawa.model.Trade;
import cz.shmoula.nawa.service.DownloadService;
import cz.shmoula.nawa.service.WidgetProvider;
import cz.shmoula.nawa.view.AssetViewHolder;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Data holder for list of assets
 * Provides simple filtering capabilities - by asset name
 * Created by vbalak on 01/08/15.
 */
public class AssetAdapter extends RecyclerView.Adapter<AssetViewHolder> implements Filterable {
    private List<Asset> allAssets;
    private List<Asset> filteredAssets;
    private String filterString;

    private Context context;

    @Override
    public AssetViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        SwipeLayout swipeLayout = (SwipeLayout) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_asset, viewGroup, false);

        // swipe show mode
        swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);

        // add drag edge
        swipeLayout.addDrag(SwipeLayout.DragEdge.Left, swipeLayout.findViewById(R.id.bottom_wrapper));

        context = viewGroup.getContext();

        return new AssetViewHolder(swipeLayout);
    }

    @Override
    public void onBindViewHolder(AssetViewHolder assetViewHolder, int i) {
        final Asset asset = filteredAssets.get(i);
        assetViewHolder.setAsset(asset);

        assetViewHolder.onWatchClicked(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                asset.setWatched(!asset.isWatched());
                asset.save();

                // if watching was enabled, download all trades immediately
                if(asset.isWatched())
                    DownloadService.downloadTradesForAsset(context, asset.getAssetId());
            }
        });
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

        if (filteredAssets == null || TextUtils.isEmpty(filterString))
            filteredAssets = data;
        else
            getFilter().filter(filterString);

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
                if (allAssets == null || allAssets.size() < 1 || charSequence == null)
                    return null;

                filterString = charSequence.toString().toLowerCase();
                List<Asset> filteredList = new ArrayList<Asset>();

                for (Asset asset : allAssets) {
                    String assetName = asset.getName().toLowerCase();
                    if (assetName.contains(filterString))
                        filteredList.add(asset);
                }

                FilterResults results = new FilterResults();
                results.values = filteredList;
                results.count = filteredList.size();

                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                if (filterResults == null || filterResults.count < 1)
                    return;

                setFilteredAssets((List<Asset>) filterResults.values);
            }
        };
    }
}
