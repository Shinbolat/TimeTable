package com.example.shinbolat.tabapp.updateservice;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shinbolat.tabapp.R;
import com.example.shinbolat.tabapp.setting.AlarmService;
import com.example.shinbolat.tabapp.utils.Prefs;

import java.util.ArrayList;

/**
 * Created by shinbolat on 2/10/16.
 */
public class RepeatingTimeTablesCheck extends Activity {

    Button SaveState;
    CheckBox UpdateState;
    Spinner UpdateTime;
    String [] timesList;
    long times[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_layout);

        SaveState = (Button)findViewById(R.id.save_state);
        UpdateState = (CheckBox)findViewById(R.id.update_state);
        UpdateTime = (Spinner)findViewById(R.id.update_time);

        times = new long[]{AlarmManager.INTERVAL_HOUR,AlarmManager.INTERVAL_HOUR*3,AlarmManager.INTERVAL_HOUR*6,AlarmManager.INTERVAL_HALF_DAY,AlarmManager.INTERVAL_DAY};

        UpdateState.setChecked(Prefs.getUpdateCheckBoxState());
        timesList = getApplicationContext().getResources().getStringArray(R.array.update_period);

        ArrayAdapter<String> UpdatePeriod = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, timesList);
        UpdatePeriod.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        UpdateTime.setAdapter(UpdatePeriod);

        SaveState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("TEXTOFSPINNER", timesList[UpdateTime.getSelectedItemPosition()]);

                AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
                Intent intent = new Intent(getApplicationContext(), UpdateCheckService.class);
                PendingIntent alarmIntent = PendingIntent.getService(getApplicationContext(), 0, intent, 0);

                if(UpdateState.isChecked() == true){
                    Prefs.setUpdateCheckBoxState(true);

                    Log.i("CHECKBOX","ACTIVE");
                    //AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
                    alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,times[UpdateTime.getSelectedItemPosition()] , times[UpdateTime.getSelectedItemPosition()], alarmIntent);

                }
                else{
                    Prefs.setUpdateCheckBoxState(false);
                    alarmManager.cancel(alarmIntent);
                }

                Toast.makeText(getApplicationContext(),"Saved",Toast.LENGTH_SHORT).show();
                finish();

            }
        });

    }
}
