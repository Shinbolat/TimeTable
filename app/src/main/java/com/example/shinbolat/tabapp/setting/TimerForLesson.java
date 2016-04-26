package com.example.shinbolat.tabapp.setting;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.UiAutomation;
import android.app.UiModeManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.test.UiThreadTest;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.SimpleAdapter;
import android.widget.TimePicker;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.widget.Toast;

import com.example.shinbolat.tabapp.R;
import com.example.shinbolat.tabapp.utils.Prefs;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by shinbolat on 2/7/16.
 */
public class TimerForLesson extends Activity {

    CheckBox setServiceState;
    NumberPicker numberPicker;
    Button SaveButton;
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    ListView LessonsListView;
    SimpleAdapter simpleAdapter;

    LessonsTime lessonsTime;

    String temp[];
    final String Lesson = "lesson";
    final String LessonsTime = "time";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_timer);

        setServiceState = (CheckBox) findViewById(R.id.checkbox_state);
        numberPicker = (NumberPicker) findViewById(R.id.number_picker);
        LessonsListView = (ListView) findViewById(R.id.list_view_lesson);
        SaveButton = (Button) findViewById(R.id.save_button);
        numberPicker.setMaxValue(60);
        numberPicker.setMinValue(5);



    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Prefs.getCheckBoxState()) {
            setServiceState.setChecked(true);
            numberPicker.setValue(Prefs.getTimer());
        } else numberPicker.setEnabled(false);

        // Set List View
        ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>(6);
        Map<String, Object> map;

        lessonsTime = new LessonsTime();
        temp = lessonsTime.getTime();

        for (int i = 0; i < 6; i++) {
            map = new HashMap<String, Object>();
            map.put(Lesson, String.valueOf(i + 1) + " " + this.getResources().getString(R.string.lesson));
            map.put(LessonsTime, temp[i]);
            data.add(map);
        }

        String[] from = {Lesson, LessonsTime};
        int[] to = {R.id.lessons_number, R.id.lessons_time};

        simpleAdapter = new SimpleAdapter(this, data, R.layout.lessons_item, from, to);
        LessonsListView.setAdapter(simpleAdapter);
        simpleAdapter.notifyDataSetChanged();


        LessonsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                dialog(i);
                Log.i("position", String.valueOf(i));

            }
        });

        //

        setServiceState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (setServiceState.isChecked()) {
                    //Log.i("CHECKBOX", "STATE ACTIVE");
                    numberPicker.setEnabled(true);
                    Prefs.setCheckBoxState(true);
                    StartAlarm(getApplicationContext());
                } else {
                    //Log.i("CHECKBOX", "STATE DEAKTIVE");
                    numberPicker.setEnabled(false);
                    Prefs.setCheckBoxState(false);
                    if (alarmMgr != null) {
                        alarmMgr.cancel(alarmIntent);
                    }
                }
            }
        });

        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (setServiceState.isChecked())
                    Prefs.setTimeBeforeLesson(numberPicker.getValue());
                finish();
            }
        });
    }

    public void StartAlarm(Context context) {

        alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        calendar.set(Calendar.HOUR_OF_DAY, 7);
        calendar.set(Calendar.MINUTE, 20);
        calendar.set(Calendar.SECOND, 0);

        alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, AlarmManager.INTERVAL_DAY, calendar.getTimeInMillis(), alarmIntent);

    }

    public void dialog(final int position) {

        Calendar calendar = new TimeTablesAlarm().getClockTime(position + 1);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new OnTimeSetListener() {


            @Override
            public void onTimeSet(TimePicker timePicker, int hours, int minutes) {

                String temp = String.valueOf(hours) + ":";

                if(minutes<10){

                    temp+="0"+String.valueOf(minutes);

                }
                else temp += String.valueOf(minutes);

                Log.i("HH:mm", temp);
                lessonsTime.setTimeOfIndex(temp, position);


            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);

        timePickerDialog.setTitle(String.valueOf(position + 1) + " " + getApplicationContext().getResources().getString(R.string.lesson));
        timePickerDialog.show();
        simpleAdapter.notifyDataSetChanged();
    }
}
