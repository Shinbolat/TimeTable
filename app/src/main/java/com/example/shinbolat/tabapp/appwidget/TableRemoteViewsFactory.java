package com.example.shinbolat.tabapp.appwidget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService.RemoteViewsFactory;

import com.example.shinbolat.tabapp.R;
import com.example.shinbolat.tabapp.editor.ReadTable;
import com.example.shinbolat.tabapp.utils.Prefs;

import java.util.ArrayList;

/**
 * Created by Shinbolat on 17.11.2015.
 */
public class TableRemoteViewsFactory implements RemoteViewsFactory {

    ArrayList<String> data;
    Context context;
    int widgetID;

    TableRemoteViewsFactory(Context ctx, Intent intent) {
        context = ctx;

        widgetID = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
        data = new ArrayList<String>();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rView = new RemoteViews(context.getPackageName(),
                R.layout.item__list_view);
        rView.setTextViewText(R.id.tvItemText, data.get(position));
        return rView;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void onDataSetChanged() {


        ReadTable readTable = new ReadTable(context);

        ArrayList<String> arrayList = new ArrayList<String>();

        try {
            arrayList = readTable.getDataForWidget((new DayOfWeek()).getIndex());
        } catch (Exception e) {
            e.printStackTrace();
        }

        data.clear();
        for (int i = 0; i < arrayList.size(); i++) {
            data.add(arrayList.get(i));
        }

    }

    @Override
    public void onDestroy() {

    }

}
