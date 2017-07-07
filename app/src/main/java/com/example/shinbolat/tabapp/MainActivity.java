package com.example.shinbolat.tabapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.shinbolat.tabapp.about.AboutActivity;
import com.example.shinbolat.tabapp.adapters.TimeTableAdapter;
import com.example.shinbolat.tabapp.editor.ReadTable;
import com.example.shinbolat.tabapp.findfile.TimeTable;
import com.example.shinbolat.tabapp.findfile.UpdateFromMemory;
import com.example.shinbolat.tabapp.setting.SettingApp;
import com.example.shinbolat.tabapp.setting.TimerForLesson;
import com.example.shinbolat.tabapp.updateservice.RepeatingTimeTablesCheck;
import com.example.shinbolat.tabapp.utils.Prefs;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    List<TimeTable> timeTablesList;

    int flag = 0;

    ListView listView;
    TimeTableAdapter timeTableAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_list_fragment);

        Log.i("MainActivity-->>", "OnCreate()" + Prefs.getPath());



    }

    @Override
    protected void onResume() {

        super.onResume();

        Log.i("MainActivity-->>", "OnResume()");

        if(!Prefs.getPath().isEmpty()) {

            /*timeTablesList = new ReadTable(this).getDataFromTable();
            timeTableAdapter.notifyDataSetChanged();*/

            timeTablesList = new ReadTable(this).getDataFromTable();
            timeTableAdapter = new TimeTableAdapter(this, timeTablesList);

            listView = (ListView)findViewById(R.id.list);
            timeTableAdapter.notifyDataSetChanged();
            listView.setAdapter(timeTableAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent(getApplicationContext(), OperationsActivity.class);
                    intent.putExtra("position", position);
                    Log.i("POSITION MAIN = ", String.valueOf(position));
                    startActivity(intent);

                }
            });

        }
    }

    @Override
    public void onBackPressed() {

        Log.i("MAIN ACTIVITY", "ON BACK PRESSED");
        if(flag == 1) {
            //confirmFireMissiles();
        }
        else super.onBackPressed();
    }

    /*public void confirmFireMissiles() {
        DialogFragment newFragment = new FireMissilesDialogFragment(textData,this);
        newFragment.show(getFragmentManager(), "missiles");
    }
*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.update_from_memory:
                Toast.makeText(this, getResources().getString(R.string.loading), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, UpdateFromMemory.class);
                startActivity(intent);
                break;

            case R.id.action_sent:
                Toast.makeText(this, "Pressed", Toast.LENGTH_SHORT).show();
                if (!Prefs.getPath().isEmpty()) {
                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                    File file = new File(Prefs.getPath());

                    sharingIntent.setType("text/plain");
                    sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
                    startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.share_file)));
                }
                break;
            case R.id.action_about:
                Toast.makeText(this, "Pressed", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, AboutActivity.class));
                break;

            case R.id.action_settings:
                Toast.makeText(this, "Pressed ", Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, SettingApp.class));
                break;
            case R.id.action_settings_timer:
                Toast.makeText(this, "Pressed ", Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, TimerForLesson.class));
                break;
            case R.id.action_service_update:
                Toast.makeText(this, "Auto Updater",Toast.LENGTH_SHORT).show();
                //start activity check for new timetable's update
                startActivity(new Intent(this, RepeatingTimeTablesCheck.class));
        }

        return super.onOptionsItemSelected(item);
    }
}
