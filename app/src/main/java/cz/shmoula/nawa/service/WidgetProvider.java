package cz.shmoula.nawa.service;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import cz.shmoula.nawa.R;

/**
 * Provider for handlig widget operation actions and binding remote views to widget
 * Created by vbalak on 02/08/15.
 */
public class WidgetProvider extends AppWidgetProvider {
    private static final String KEY_FORCE_REFRESH = "forceWidgetRefresh";

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if (intent.hasExtra(KEY_FORCE_REFRESH)) {
            ComponentName cn = new ComponentName(context, WidgetProvider.class);

            AppWidgetManager.getInstance(context).notifyAppWidgetViewDataChanged(
                    AppWidgetManager.getInstance(context).getAppWidgetIds(cn), R.id.widgetList);
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
}
