package com.example.shinbolat.tabapp.setting;

import android.util.Log;

import com.example.shinbolat.tabapp.utils.Prefs;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by shinbolat on 2/8/16.
 */
public class LessonsTime{

    private String time[];

    public String[] getTime(){

        time = new String[]
                {
                        Prefs.getFirstLesson().toString(),Prefs.getSecondLesson().toString(),
                        Prefs.getThirdLesson().toString(),Prefs.getFourthLesson().toString(),
                        Prefs.getFifthLesson().toString(),Prefs.getSixLesson().toString()
                };


        return time;
    }

    public void setTimeOfIndex(String time, int index){

        if(index==0)Prefs.setFirstLesson(time);
        if(index==1)Prefs.setSecondLesson(time);
        if(index==2)Prefs.setThirdLesson(time);
        if(index==3)Prefs.setFourthLesson(time);
        if(index==4)Prefs.setFifthLesson(time);
        if(index==5)Prefs.setSixLesson(time);

    }

    public Calendar getTime(String temp){

        Date date;
        Calendar calendar = Calendar.getInstance();

        try {
            date = new SimpleDateFormat("HH:mm").parse(temp);
        } catch (ParseException e) {
            //e.printStackTrace();
            date = null;
        }

        calendar.setTime(date);

        Log.i("TIME = ",temp);
        //Log.i("DATETIME = ", date.getHours()+":"+date.getMinutes());
        Log.i("CALENDAR_TIME = ", String.valueOf(calendar.get(Calendar.HOUR)) + ":" + String.valueOf(calendar.get(Calendar.MINUTE)));

        return calendar;
    }
}
