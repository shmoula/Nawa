package cz.shmoula.nawa.view;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.activeandroid.query.Select;

import java.util.List;

import cz.shmoula.nawa.R;
import cz.shmoula.nawa.model.Asset;

/**
 * Factory providing data for the collection inside widget
 * Created by vbalak on 02/08/15.
 */
public class WidgetViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context context = null;

    private List<Asset> assets;

    public WidgetViewsFactory(Context context, Intent intent) {
        this.context = context;
        int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        assets = new Select().from(Asset.class).where(Asset.COLUMN_WATCHED + "=1").execute();
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return assets.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews row = new RemoteViews(context.getPackageName(), R.layout.item_widget_row);

        row.setTextViewText(android.R.id.text1, assets.get(i).getName());

        return (row);
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
