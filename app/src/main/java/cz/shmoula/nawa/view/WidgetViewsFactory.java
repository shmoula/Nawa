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

        // Load last two trades for this asset and compute price delta and percent diff
        List<Trade> trades = Trade.findTradesByAssetId(assets.get(i).getId(), 2);
        double lastPrice = 0;
        double previousPrice = 0;
        if (trades.size() > 0)
            lastPrice = trades.get(0).getPrice();
        if(trades.size() > 1)
            previousPrice = trades.get(1).getPrice();

        double deltaPrice = lastPrice - previousPrice;

        if(deltaPrice < 0)
            row.setInt(R.id.itemWidgetRowDeltaBox, "setBackgroundResource", R.drawable.widget_delta_box_red);
        else
            row.setInt(R.id.itemWidgetRowDeltaBox, "setBackgroundResource", R.drawable.widget_delta_box_green);

        row.setTextViewText(R.id.itemWidgetRowName, assets.get(i).getName());
        row.setTextViewText(R.id.itemWidgetRowPrice, new DecimalFormat(".####").format(lastPrice));
        row.setTextViewText(R.id.itemWidgetRowDeltaPrice, new DecimalFormat("0.0000").format(deltaPrice));
        row.setTextViewText(R.id.itemWidgetRowDeltaPercent, new DecimalFormat("0.000").format(deltaPrice / (lastPrice / 100)) + "%");



        //row.setTextColor(R.id.itemWidgetRowDeltaPrice, context.getResources().getColor(R.color.delta_red));
        //row.setTextViewText(R.id.itemWidgetRowDeltaPrice, "-1.2");

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
