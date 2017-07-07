/*
* Set Time of Alarm Alert
*/

package com.example.shinbolat.tabapp.setting;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.shinbolat.tabapp.appwidget.DayOfWeek;
import com.example.shinbolat.tabapp.editor.ReadTable;
import com.example.shinbolat.tabapp.utils.Prefs;

import java.util.Calendar;

/**
 * Created by shinbolat on 2/4/16.
 */
public class AlarmReceiver extends BroadcastReceiver {

    private AlarmManager alarmMgr;
    // The pending intent that is triggered when the alarm fires.
    private PendingIntent alarmIntent;

    ReadTable readTable;
    String AlarmTime;

    @Override
    public void onReceive(Context context, Intent intent) {

        if (!Prefs.getPath().isEmpty()) {
            readTable = new ReadTable(context);
            AlarmTime = readTable.getTimesOfLessons(new DayOfWeek().getIndex());

            Log.i("ALARM TIME", String.valueOf(new DayOfWeek().getIndex()) + "=" + AlarmTime);
            Toast.makeText(context, String.valueOf(new DayOfWeek().getIndex()) + "=" + AlarmTime, Toast.LENGTH_LONG);

            if (!AlarmTime.isEmpty()) {
                setAlarm(context, new TimeTablesAlarm().getClockTime(Integer.parseInt(AlarmTime)));
            }
        }
    }

    public void setAlarm(Context context, Calendar calendar) {
        alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmService.class);
        alarmIntent = PendingIntent.getService(context, 0, intent, 0);

        alarmMgr.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis()-(Prefs.getTimer()*60*1000), alarmIntent);

    }
}
