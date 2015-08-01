package cz.shmoula.nawa;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.activeandroid.loaders.ModelLoader;

import java.util.List;

import cz.shmoula.nawa.adapter.AssetAdapter;
import cz.shmoula.nawa.api.RestClient;
import cz.shmoula.nawa.model.Asset;
import cz.shmoula.nawa.model.ResponseGetAllAssets;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Main entry point to the application
 * Created by vbalak on 01/08/15.
 */
public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Asset>> {
    private static final int ID_ASSET_LOADER = 666;

    private AssetAdapter assetAddapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Load and save all assets info
        // TODO move to background and do it on demand
        RestClient.getNxtContract().getAllAssets(new Callback<ResponseGetAllAssets>() {
            @Override
            public void success(ResponseGetAllAssets responseGetAllAssets, Response response) {
                for (Asset asset : responseGetAllAssets.getAssets())
                    asset.save();
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.mainAssetsList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        assetAddapter = new AssetAdapter();
        recyclerView.setAdapter(assetAddapter);

        getSupportLoaderManager().initLoader(ID_ASSET_LOADER, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<List<Asset>> onCreateLoader(int id, Bundle args) {
        return new ModelLoader<Asset>(getApplicationContext(), Asset.class, true);
    }

    @Override
    public void onLoadFinished(Loader<List<Asset>> loader, List<Asset> data) {
        assetAddapter.setData(data);
    }

    @Override
    public void onLoaderReset(Loader<List<Asset>> loader) {
        assetAddapter.setData(null);
    }
}
