package cz.shmoula.nawa;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

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
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Load and save all assets info
        // TODO move to background and do it on demand
        RestClient.getNxtContract().getAllAssets(new Callback<ResponseGetAllAssets>() {
            @Override
            public void success(ResponseGetAllAssets responseGetAllAssets, Response response) {
                for(Asset asset : responseGetAllAssets.getAssets())
                    asset.save();
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
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
}
