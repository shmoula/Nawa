package cz.shmoula.nawa.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import cz.shmoula.nawa.R;
import cz.shmoula.nawa.model.Asset;

/**
 * View holder for one row in assets list
 * Created by vbalak on 01/08/15.
 */
public class AssetViewHolder extends RecyclerView.ViewHolder {
    private TextView tvAssetName;
    private TextView tvAssetTrades;
    private ImageButton btnWatch;
    private ImageView ivWatching;

    public AssetViewHolder(View itemView) {
        super(itemView);

        tvAssetName = (TextView) itemView.findViewById(R.id.itemAssetName);
        tvAssetTrades = (TextView) itemView.findViewById(R.id.itemAssetTrades);
        btnWatch = (ImageButton) itemView.findViewById(R.id.itemAssetWatchButton);
        ivWatching = (ImageView) itemView.findViewById(R.id.itemAssetWatching);
    }

    public void setAsset(Asset asset) {
        tvAssetName.setText(asset.getName());
        tvAssetTrades.setText(Long.toString(asset.getNumberOfTrades()));

        if (asset.isWatched()) {
            btnWatch.setImageResource(R.drawable.ic_unwatch);
            ivWatching.setImageResource(R.drawable.ic_watch);
        } else {
            btnWatch.setImageResource(R.drawable.ic_watch);
            ivWatching.setImageResource(R.drawable.ic_unwatch);
        }
    }

    public void onWatchClicked(View.OnClickListener listener) {
        btnWatch.setOnClickListener(listener);
    }
}
