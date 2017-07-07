/*
* to get weeks day
*/

package com.example.shinbolat.tabapp.appwidget;

import android.content.Context;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Shinbolat on 17.11.2015.
 */
public class DayOfWeek {

    int index;

    public static final String week[] = {"MON","THU","WED","TUE","FRI","SAT"};

    public DayOfWeek(){

        GregorianCalendar calendar = (GregorianCalendar) GregorianCalendar.getInstance();

        index = (calendar.get(Calendar.DAY_OF_WEEK) == 1 ? 2 : calendar.get(Calendar.DAY_OF_WEEK)) - 2;

    }

    public int getIndex(){

        return index;
    }

    public static final int getPosition(String temp){

        int position=-1;

        for (int i = 0; i < week.length; i ++){

            if(temp.equals(week[i])){

                position=i;

            }

        }

        return position;
    }

}
