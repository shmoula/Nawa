package cz.shmoula.nawa.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;

import com.activeandroid.loaders.ModelLoader;
import com.activeandroid.query.Select;

import java.util.List;

import cz.shmoula.nawa.adapter.AssetAdapter;
import cz.shmoula.nawa.model.Asset;
import cz.shmoula.nawa.service.DownloadService;


/**
 * Utility for preparing data - either download them from web, or loading from db
 * Created by vbalak on 01/08/15.
 */
public class DataLoader implements android.app.LoaderManager.LoaderCallbacks<List<Asset>> {
    private static final int ID_ASSET_LOADER = 666;

    private Context context;
    private AssetAdapter assetAdapter;

    public DataLoader(Activity activity, AssetAdapter assetAdapter) {
        context = activity.getApplicationContext();
        this.assetAdapter = assetAdapter;

        // download data only if the app is started for the first time
        if (new Select().from(Asset.class).count() == 0) {
            Intent downloadData = new Intent(context, DownloadService.class);
            activity.startService(downloadData);
        }

        activity.getLoaderManager().initLoader(ID_ASSET_LOADER, null, this);
    }

    @Override
    public Loader<List<Asset>> onCreateLoader(int i, Bundle bundle) {
        return new ModelLoader<Asset>(context, Asset.class, true);
    }

    @Override
    public void onLoadFinished(Loader<List<Asset>> loader, List<Asset> assets) {
        assetAdapter.setData(assets);
    }

    @Override
    public void onLoaderReset(Loader<List<Asset>> loader) {
        assetAdapter.setData(null);
    }
}