package com.example.shinbolat.tabapp.appwidget;

import android.content.Intent;
import android.widget.RemoteViewsService;

import com.example.shinbolat.tabapp.appwidget.TableRemoteViewsFactory;

/**
 * Created by Shinbolat on 17.11.2015.
 */
public class TableWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new TableRemoteViewsFactory(getApplicationContext(), intent);
    }

}
