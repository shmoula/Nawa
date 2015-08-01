package cz.shmoula.nawa;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import cz.shmoula.nawa.adapter.AssetAdapter;
import cz.shmoula.nawa.service.DownloadService;
import cz.shmoula.nawa.util.DataLoader;

/**
 * Main entry point to the application
 * Created by vbalak on 01/08/15.
 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.mainAssetsList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        AssetAdapter assetAdapter = new AssetAdapter();
        recyclerView.setAdapter(assetAdapter);

        DataLoader dataLoader = new DataLoader(this, assetAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_reload) {
            Intent downloadData = new Intent(getApplicationContext(), DownloadService.class);
            startService(downloadData);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
