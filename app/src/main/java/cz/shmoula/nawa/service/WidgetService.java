package cz.shmoula.nawa.service;

import android.content.Intent;
import android.widget.RemoteViewsService;

import cz.shmoula.nawa.view.WidgetViewsFactory;

/**
 * Service providing factory for remote views
 * Created by vbalak on 02/08/15.
 */
public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return (new WidgetViewsFactory(getApplicationContext(), intent));
    }
}