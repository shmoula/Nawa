package cz.shmoula.nawa.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import cz.shmoula.nawa.R;
import cz.shmoula.nawa.model.Asset;

/**
 * View holder for one row in assets list
 * Created by vbalak on 01/08/15.
 */
public class AssetViewHolder extends RecyclerView.ViewHolder {
    private TextView tvAssetName;

    public AssetViewHolder(View itemView) {
        super(itemView);

        tvAssetName = (TextView) itemView.findViewById(R.id.itemAssetName);
    }

    public void setAsset(Asset asset) {
        tvAssetName.setText(asset.getName());
    }
}
