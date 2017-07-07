package com.example.shinbolat.tabapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.shinbolat.tabapp.appwidget.DayOfWeek;
import com.example.shinbolat.tabapp.editor.ReadTable;
import com.example.shinbolat.tabapp.editor.WriteTable;
import com.example.shinbolat.tabapp.findfile.TimeTable;
import com.example.shinbolat.tabapp.utils.Prefs;

import java.util.List;

/**
 * Created by shinbolat on 2/18/16.
 */
public class OperationsActivity extends AppCompatActivity {
/*
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    OperationsFragment operations;
*/
    ArrayAdapter<CharSequence> weekdaysAdapter;

    Spinner weekdaySpinner;
    NumberPicker lessonsNum;
    EditText lessonsName, lessonsRoom;

    Button save, exit;
    int position;

    TimeTable timeTable;
    List<TimeTable> timeTablesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.operation_dialog);

        weekdaySpinner = (Spinner)findViewById(R.id.weekdays_spinner);
        lessonsNum = (NumberPicker)findViewById(R.id.lessons_number_picker);
        lessonsName = (EditText)findViewById(R.id.lessons_name);
        lessonsRoom = (EditText)findViewById(R.id.room_num_text);

        save = (Button)findViewById(R.id.button_save_data);
        exit = (Button)findViewById(R.id.button_back);

        lessonsNum.setMinValue(1);
        lessonsNum.setMaxValue(6);
        Intent intent = getIntent();
        position = intent.getIntExtra("position",0);
        Log.i("POSITION OPERA = ", String.valueOf(position));


        timeTablesList = new ReadTable(this).getDataFromTable();
        //set Adapter
        weekdaysAdapter = ArrayAdapter.createFromResource(this,R.array.week, android.R.layout.simple_spinner_item);
        weekdaysAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weekdaySpinner.setAdapter(weekdaysAdapter);
        weekdaySpinner.setSelection(DayOfWeek.getPosition(timeTablesList.get(position).getNameOfWeek()));

        lessonsNum.setValue(Integer.valueOf(timeTablesList.get(position).getNumOfSubject()));
        lessonsName.setText(timeTablesList.get(position).getNameOfSubject());
        lessonsRoom.setText(timeTablesList.get(position).getNumOfRoom());

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                timeTable = new TimeTable(DayOfWeek.week[weekdaySpinner.getSelectedItemPosition()], String.valueOf(lessonsNum.getValue()), lessonsName.getText().toString(), lessonsRoom.getText().toString());
                Log.i("DAYYY", DayOfWeek.week[weekdaySpinner.getSelectedItemPosition()]);
                timeTablesList.set(position, timeTable);
                Boolean flag = new WriteTable().Writer(timeTablesList);
                Toast.makeText(OperationsActivity.this, flag?getResources().getString(R.string.saved):getResources().getString(R.string.failed), Toast.LENGTH_SHORT).show();

                finish();

            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
