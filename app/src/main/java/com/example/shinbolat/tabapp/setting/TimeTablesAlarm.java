/*
 * This Class to get Calendar time of lessons
*/

package com.example.shinbolat.tabapp.setting;

import android.content.Context;
import android.provider.CalendarContract;

import com.example.shinbolat.tabapp.utils.Prefs;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Shinbolat on 19.01.2016.
 */
public class TimeTablesAlarm {

    public TimeTablesAlarm(){

    }

    public Calendar getClockTime(int lesson){

        LessonsTime lessonsTime = new LessonsTime();

        String time[];
        time = new String[]
                {
                        Prefs.getFirstLesson().toString(),Prefs.getSecondLesson().toString(),
                        Prefs.getThirdLesson().toString(),Prefs.getFourthLesson().toString(),
                        Prefs.getFifthLesson().toString(),Prefs.getSixLesson().toString()
                };

        return lessonsTime.getTime(time[lesson - 1]);
    }

}
