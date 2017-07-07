/*
* Find file from phone's memory
*/
package com.example.shinbolat.tabapp.findfile;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.shinbolat.tabapp.R;
import com.example.shinbolat.tabapp.utils.Prefs;

import java.io.File;
import java.util.ArrayList;
import android.support.v7.app.AppCompatActivity;
/**
 * Created by Shinbolat on 07.11.2015.
 */
public class UpdateFromMemory extends AppCompatActivity {

    ArrayList<File> fileArrayList;
    ListView listView;

    String pathOfFile;

    ProgressBar progressBar;

    private File SDcard = Environment.getExternalStorageDirectory(); // Get the external storage directory
    private File DirectoryToFind;

    FileOperation fileOperation;
    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_file_layout);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        listView = (ListView)findViewById(R.id.listViewOfFoundFiles);

        fileOperation = new FileOperation();

        arrayList = new ArrayList<String>();

        fileArrayList = new ArrayList<>();

        arrayAdapter = new ArrayAdapter<String>(this, R.layout.item,R.id.text1,arrayList);

        if (isExternalStorageWritable()){
            DirectoryToFind = Environment.getExternalStorageDirectory();
        }
        else DirectoryToFind = Environment.getRootDirectory();

        if(DirectoryToFind.toString().isEmpty()){

            return;
        }
        else {
            new Thread(new Runnable() {
                @Override
                public void run() {

                    fileArrayList = fileOperation.findFileByName(".tt", DirectoryToFind.toString());

                    if (!fileArrayList.isEmpty()){

                        for (int i = 0; i < fileArrayList.size(); i++) {

                            arrayList.add(fileArrayList.get(i).toString());

                        }
                        listView.post(new Runnable() {
                            @Override
                            public void run() {

                                progressBar.setVisibility(View.GONE);

                                listView.setAdapter(arrayAdapter);

                            }
                        });
                    }

                }
            }).start();
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //return path to clicked file
                pathOfFile = new String((arrayAdapter.getItem(position)));
                Prefs.setPath(pathOfFile);

                finish();

            }
        });
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }


}
