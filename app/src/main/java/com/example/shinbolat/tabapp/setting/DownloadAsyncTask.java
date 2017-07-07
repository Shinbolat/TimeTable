/*
* Download timetable file <.tt> from inserted url
*/

package com.example.shinbolat.tabapp.setting;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.example.shinbolat.tabapp.R;
import com.example.shinbolat.tabapp.utils.Prefs;

import org.jsoup.Jsoup;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.content.Context.*;

/**
 * Created by Shinbolat on 16.01.2016.
 */
class DownloadAsyncTask extends AsyncTask<String, Void, String> {

    Context context;
    final String FOLDER_NAME = "TimeTable";
    ProgressBar progressBar;
    View view;
    String savedFileName;
    byte[] bytes;
    String path;

    DownloadAsyncTask(Context context){

        this.context = context;

    }

    protected void onPreExecute(){
        super.onPreExecute();

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.setting_layout, null);
        progressBar = (ProgressBar)view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    protected String doInBackground(String... params) {

        try {
            bytes = Jsoup.connect(params[0])
                    .ignoreContentType(true)
                    .execute()
                    .bodyAsBytes();
        } catch (IOException e) {
            //return null;
            e.printStackTrace();
        }

        savedFileName = params[1]+".tt";
        Log.i("SAVED_FILE_NAME",savedFileName);

        return savedFileName;
    }

    protected void onPostExecute(String str) {
        super.onPostExecute(str);

        File pathToFolder;

        if(isExternalStorageWritable()) {

            pathToFolder = new File(Environment.getExternalStorageDirectory().toString() + "/" + FOLDER_NAME);
            if (!pathToFolder.exists()) {
                pathToFolder.mkdirs();
            }

        }
        else{

            pathToFolder = new File(Environment.getRootDirectory().toString()+ "/"+ FOLDER_NAME);
            if (!pathToFolder.exists()) {
                pathToFolder.mkdirs();
            }

        }


        try {

            //File file = new File(pathToFolder.toString()+savedFileName);
            path = pathToFolder.toString() + "/" + savedFileName;
            FileOutputStream fos = new FileOutputStream(path);
            Prefs.setPath(path);
            Log.i("PATH DOWNLOADED"," FILE-->>"+Prefs.getPath());

            fos.write(bytes);
            fos.close();

        } catch (FileNotFoundException e) {
            //return;
            //e.printStackTrace();
            Log.i("ERROR", "WRITING");
            return;
        } catch (IOException e) {
            //return;
            //e.printStackTrace();
            Log.i("ERROR", "WRITING");
            return;
        }

        progressBar.setVisibility(View.INVISIBLE);
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }


}
