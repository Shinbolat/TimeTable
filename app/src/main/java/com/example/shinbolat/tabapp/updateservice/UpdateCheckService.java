package com.example.shinbolat.tabapp.updateservice;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.shinbolat.tabapp.R;
import com.example.shinbolat.tabapp.setting.SettingApp;
import com.example.shinbolat.tabapp.utils.Prefs;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

/**
 * Created by shinbolat on 2/10/16.
 */
public class UpdateCheckService extends Service {

    private NotificationManager mNotificationManager;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("UPDATE CHECK SERVICE", "ON CREATE");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String temp = "";

        Log.i("UPDATE CHECK SERVICE", "ON START COMMAND");

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("THREAD", "IS RUNNING");
                String message = "";
                Document doc=null;

                try {

                    URL url = new URL(Prefs.getFaculty().toString());
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                        doc = Jsoup.connect(Prefs.getFaculty().toString()).get();
                        Log.i("CONNECTION", "HTTP_OK");
                        Elements menu = doc.getElementsByAttributeValue("title", "groups");
                        Elements dates = doc.getElementsByAttributeValue("title", "date");
                        Date[] data;
                        data = new Date[dates.size()];

                        //Check Date Format
                        for (int i = 0; i < dates.size(); i++) {

                            Log.i("UPDATE CHECK SERVICE", "CHECK DATA FORMAT");

                            try {
                                data[i] = new SimpleDateFormat("dd/MM/yyyy").parse(dates.get(i).text().toString().trim());
                            } catch (ParseException e) {
                                Log.i("UPDATE_ASYNC_TASK", "ERROR IN DATA");
                                //e.printStackTrace();
                            }

                        }

                        if ((menu.size() != 0) && (data.length == menu.size())) {

                            Log.i("MENU = DATA", "EQUALS");

                            for (int i = 0; i < menu.size(); i++) {

                                Element element = menu.select("a[href]").get(i);

                                String temp = element.text();

                                if (temp.equals(Prefs.getSelectedGroup())) {
                                    if (!data[i].toString().equals(Prefs.getDownloadDate())) {

                                        Prefs.setDownloadDate(data[i].toString());
                                        Log.i("CHANGED DATE", data[i].toString());
                                        message = "TimeTable has changed in " + data[i].toString();
                                        sendNotification(message);

                                    } else Log.i("UPDATE CHECK SERVICE", "IS THE SAME");
                                }

                            }

                        } else {
                            //Toast.makeText(this, "System error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    else Log.i("CONNECTION", "HTTP_BAD");

                } catch (MalformedURLException e) {

                    Log.i("THREAD", "ERROR CONNECTION");
                    //e.printStackTrace();
                } catch (IOException e) {

                    Log.i("THREAD", "ERROR CONNECTION");
                    //e.printStackTrace();
                }

            }
        }).start();

        Log.i("UPDATE CHECK SERVICE", "BEFORE STOP SELF");

        stopSelf();

        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("UPDATE SERVICE", "onDestroy");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void sendNotification(String msg) {
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, SettingApp.class), 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher_widget)
                        .setContentTitle("TimeTable")
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(msg))
                        .setContentText(msg)
                        .setSound(new RingtoneManager(this).getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(1, mBuilder.build());
    }
}
