package com.example.shinbolat.tabapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.shinbolat.tabapp.R;
import com.example.shinbolat.tabapp.findfile.TimeTable;

import java.util.List;

/**
 * Created by shinbolat on 4/25/16.
 */
public class TimeTableAdapter extends BaseAdapter {

    Context context;
    LayoutInflater layoutInflater;
    List<TimeTable> timeTableList;

    public TimeTableAdapter(Context context, List<TimeTable> timeTables){

        this.context=context;
        this.timeTableList=timeTables;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return timeTableList.size();
    }

    @Override
    public Object getItem(int position) {
        return timeTableList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.lessons_list_item, parent, false);
        }

        TimeTable timeTable = timeTableList.get(position);

        ((TextView)view.findViewById(R.id.lesson_num)).setText(timeTable.getNumOfSubject());
        ((TextView)view.findViewById(R.id.lesson)).setText(timeTable.getNameOfSubject());
        ((TextView)view.findViewById(R.id.weekday)).setText(getWeekday(timeTable.getNameOfWeek()));
        ((TextView)view.findViewById(R.id.room_num)).setText(timeTable.getNumOfRoom());

        return view;
    }

    private String getWeekday(String temp){

        String [] week = context.getResources().getStringArray(R.array.week);
        String []WeekDay=new String[]{"MON","TUE","WED","THU","FRI","SAT"};

        for(int i = 0; i < week.length; i ++){

            if(WeekDay[i].equals(temp.trim())){
                return week[i];
            }

        }
        return null;
    }
}
