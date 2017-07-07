package com.example.shinbolat.tabapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.shinbolat.tabapp.R;
import com.example.shinbolat.tabapp.setting.LessonsTime;

/**
 * Created by shinbolat on 4/25/16.
 */
public class TimeDialogAdapter extends BaseAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public TimeDialogAdapter(Context context){

        this.context = context;

    }

    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public Object getItem(int position) {

        LessonsTime lessonsTime = new LessonsTime();
        String times[] = lessonsTime.getTime();

        return times[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.lessons_item, parent, false);
        }

        LessonsTime lessonsTime = new LessonsTime();
        String times[] = lessonsTime.getTime();

        ((TextView)view.findViewById(R.id.lessons_number)).setText(context.getResources().getString(R.string.lesson));
        ((TextView)view.findViewById(R.id.lessons_time)).setText(times[position]);

        return view;

    }
}
