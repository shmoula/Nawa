package cz.shmoula.nawa.view;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.activeandroid.query.Select;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import cz.shmoula.nawa.R;
import cz.shmoula.nawa.model.Asset;
import cz.shmoula.nawa.model.Trade;

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
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        assets = new Select().from(Asset.class).where(Asset.COLUMN_WATCHED + "=?", 1).execute();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return assets == null ? 0 : assets.size();

    }

    @Override
    public RemoteViews getViewAt(int i) {
        if (assets == null || assets.size() < 1)
            return null;

        RemoteViews row = new RemoteViews(context.getPackageName(), R.layout.item_widget_row);

        // Load all trades for this asset and prints last price
        List<Trade> trades = Trade.findTradesByAssetId(assets.get(i).getId());
        if (trades.size() > 0) {
            NumberFormat formatter = new DecimalFormat(".####");
            row.setTextViewText(R.id.itemWidgetRowPrice, formatter.format(trades.get(0).getPrice()));
        }

        row.setTextViewText(R.id.itemWidgetRowName, assets.get(i).getName());

        return row;
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
