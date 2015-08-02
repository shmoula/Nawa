package cz.shmoula.nawa.service;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.Toast;

import cz.shmoula.nawa.R;

/**
 * Provider for handlig widget operation actions and binding remote views to widget
 * Created by vbalak on 02/08/15.
 */
public class WidgetProvider extends AppWidgetProvider {
    private static final String KEY_FORCE_REFRESH = "forceWidgetRefresh";
    private static final String KEY_ASSET_ID = "assetId";

    public static final String ACTION_ROW_CLICKED = "cz.shmoula.nawa.ROW_CLICKED";

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if (intent.hasExtra(KEY_FORCE_REFRESH)) {
            ComponentName cn = new ComponentName(context, WidgetProvider.class);

            AppWidgetManager.getInstance(context).notifyAppWidgetViewDataChanged(
                    AppWidgetManager.getInstance(context).getAppWidgetIds(cn), R.id.widgetList);

        } else if (ACTION_ROW_CLICKED.equals(intent.getAction())) {
            long assetId = intent.getLongExtra(KEY_ASSET_ID, -1);
            Toast.makeText(context, "To be implemented, id=" + assetId, Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            Intent intent = new Intent(context, WidgetService.class);

            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

            RemoteViews widget = new RemoteViews(context.getPackageName(), R.layout.widget);
            widget.setRemoteAdapter(appWidgetId, R.id.widgetList, intent);

            // prepare intent, which will be called when widget row is clicked
            Intent toastIntent = new Intent(context, WidgetProvider.class);
            toastIntent.setAction(ACTION_ROW_CLICKED);
            PendingIntent toastPendingIntent = PendingIntent.getBroadcast(context, 0, toastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            widget.setPendingIntentTemplate(R.id.widgetList, toastPendingIntent);

            appWidgetManager.updateAppWidget(appWidgetId, widget);
        }

        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    /**
     * Force reload widget
     */
    public static void forceReload(Context context) {
        Intent updateIntent = new Intent();

        updateIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        updateIntent.putExtra(KEY_FORCE_REFRESH, true);
        context.sendBroadcast(updateIntent);
    }

    /**
     * Widget row was clicked
     */
    public static void widgetRowClicked(RemoteViews row, Long id) {
        Bundle extras = new Bundle();
        Intent fillInIntent = new Intent();

        extras.putLong(KEY_ASSET_ID, id);
        fillInIntent.putExtras(extras);

        row.setOnClickFillInIntent(R.id.itemWidgetRow, fillInIntent);
    }
}
