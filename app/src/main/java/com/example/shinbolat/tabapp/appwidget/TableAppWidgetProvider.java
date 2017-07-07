package com.example.shinbolat.tabapp.appwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.shinbolat.tabapp.MainActivity;
import com.example.shinbolat.tabapp.R;

/**
 * Created by Shinbolat on 16.11.2015.
 */
public class TableAppWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {

        super.onUpdate(context, appWidgetManager, appWidgetIds);
        for (int i : appWidgetIds) {
            updateWidget(context, appWidgetManager, i);
        }
    }

    void updateWidget(Context context, AppWidgetManager appWidgetManager,
                      int appWidgetId) {
        RemoteViews rv = new RemoteViews(context.getPackageName(),
                R.layout.widget);

        Intent launchActivity = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, launchActivity, 0);
        rv.setOnClickPendingIntent(R.id.tvUpdate, pendingIntent);

        setUpdateTV(rv, context, appWidgetId);

        setList(rv, context, appWidgetId);

        setListClick(rv, context, appWidgetId);

        appWidgetManager.updateAppWidget(appWidgetId, rv);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId,
                R.id.lvList);
    }

    void setUpdateTV(RemoteViews rv, Context context, int appWidgetId) {

        String Week[] = new String[6];

        Week[0] = context.getResources().getString(R.string.monday);
        Week[1] = context.getResources().getString(R.string.tuesday);
        Week[2] = context.getResources().getString(R.string.wednesday);
        Week[3] = context.getResources().getString(R.string.thursday);
        Week[4] = context.getResources().getString(R.string.friday);
        Week[5] = context.getResources().getString(R.string.saturday);

        rv.setTextViewText(R.id.tvUpdate, Week[new DayOfWeek().getIndex()]);

        Intent updIntent = new Intent(context, TableAppWidgetProvider.class);
        updIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        updIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,
                new int[] { appWidgetId });
        PendingIntent updPIntent = PendingIntent.getBroadcast(context,
                appWidgetId, updIntent, 0);
        rv.setOnClickPendingIntent(R.id.tvUpdate, updPIntent);
    }

    void setList(RemoteViews rv, Context context, int appWidgetId) {
        Intent adapter = new Intent(context, TableWidgetService.class);
        adapter.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        rv.setRemoteAdapter(R.id.lvList, adapter);
    }

    void setListClick(RemoteViews rv, Context context, int appWidgetId) {

    }

}
