/*
* Activity to set Settings of Application
*/

package com.example.shinbolat.tabapp.setting;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.shinbolat.tabapp.Alarm;

import com.example.shinbolat.tabapp.MainActivity;
import com.example.shinbolat.tabapp.R;
import com.example.shinbolat.tabapp.appwidget.DayOfWeek;
import com.example.shinbolat.tabapp.utils.Prefs;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

/**
 * Created by Shinbolat on 23.11.2015.
 */
public class SettingApp extends Activity {

    Button start, accept;
    Spinner listOfUniversities,listOfFaculty , listOfGroups;
    ProgressBar progressBar;
    String downloadUrl;
    EditText urlOfSite;
    String nameOfGroup;

    SitesAsyncTask sitesAsyncTask1,sitesAsyncTask2, sitesAsyncTask3;
    DownloadAsyncTask downloadAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_layout);

//        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
//        vibrator.vibrate(1000);
        Log.i("SETTING", "ON CREATE");

        start = (Button)findViewById(R.id.start_bt);
        accept = (Button)findViewById(R.id.accept_bt);
        listOfUniversities = (Spinner)findViewById(R.id.university_id);
        listOfFaculty = (Spinner)findViewById(R.id.faculty_id);
        listOfGroups = (Spinner)findViewById(R.id.groups_id);
        progressBar = (ProgressBar)findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.INVISIBLE);
        urlOfSite = (EditText)findViewById(R.id.sites_url);
        urlOfSite.setText("localhost/timetable/");

        downloadUrl="";

        start.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                sitesAsyncTask1 = new SitesAsyncTask(getApplicationContext(), listOfUniversities,"MENU");
                sitesAsyncTask1.execute("http://"+urlOfSite.getText().toString());
            }
        });
        listOfUniversities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                ArrayList<String> list = null;

                try {
                    list = sitesAsyncTask1.get();
                } catch (InterruptedException e) {
                    return;
                    //e.printStackTrace();
                } catch (ExecutionException e) {
                    return;
                    //e.printStackTrace();
                }

                Log.i("sitesAsyncTask1.get()", list.get(position));

                if(list != null){
                    sitesAsyncTask2 = new SitesAsyncTask(getApplicationContext(), listOfFaculty, "FACULTY");
                    sitesAsyncTask2.execute(list.get(position));

                }

                list = null;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        listOfFaculty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                ArrayList<String> list = null;

                try {
                    list = sitesAsyncTask2.get();
                } catch (InterruptedException e) {
                    return;
                    //e.printStackTrace();
                } catch (ExecutionException e) {
                    return;
                    //e.printStackTrace();
                }

                Log.i("sitesAsyncTask2.get()", list.get(position));

                if(list != null){
                    sitesAsyncTask3 = new SitesAsyncTask(getApplicationContext(), listOfGroups, "GROUPS");
                    sitesAsyncTask3.execute(list.get(position));
                    Prefs.setFaculty(list.get(position));
                    Log.i("FACULTY_URL = ", Prefs.getFaculty());
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        listOfGroups.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                try {

                    downloadUrl = sitesAsyncTask3.get().get(position);

                } catch (InterruptedException e) {
                    return;
                    //e.printStackTrace();
                } catch (ExecutionException e) {
                    return;
                    //e.printStackTrace();
                }

                Log.i("DOWNLOAD URL",downloadUrl);
                Calendar calendar = Calendar.getInstance();

                nameOfGroup = ((TextView)view).getText().toString();
                Prefs.setSelectedGroup(nameOfGroup);
                String temp = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH))+"/"+String.valueOf(calendar.get(Calendar.MONTH))+"/"+String.valueOf(calendar.get(Calendar.YEAR));
                Prefs.setDownloadDate(temp);

                Log.i("NAME OF GROUP = ", nameOfGroup + "  DATE =" + temp);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!downloadUrl.equals("")) {
                    downloadAsyncTask = new DownloadAsyncTask(getApplicationContext());
                    downloadAsyncTask.execute(downloadUrl, nameOfGroup);
                }
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}