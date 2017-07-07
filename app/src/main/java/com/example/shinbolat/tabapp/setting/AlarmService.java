/*
* To notification about lesson
*/
package com.example.shinbolat.tabapp.setting;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.shinbolat.tabapp.MainActivity;
import com.example.shinbolat.tabapp.R;
import com.example.shinbolat.tabapp.appwidget.DayOfWeek;
import com.example.shinbolat.tabapp.editor.ReadTable;
import com.example.shinbolat.tabapp.utils.Prefs;

/**
 * Created by shinbolat on 2/4/16.
 */
public class AlarmService extends Service {

    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;
    ReadTable readTable;
    String Lesson;

    @Override
    public void onCreate() {
        super.onCreate();

        Log.i("SERVICE","WAS CREATED");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(!Prefs.getPath().isEmpty()) {
            readTable = new ReadTable(getApplicationContext());
            Lesson = readTable.getNameOfLesson(new DayOfWeek().getIndex());

            Log.i("SERVICE", "ON START COMMAND");

            sendNotification(Lesson);
            stopSelf();
        }
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.i("SERVICE", "WAS DESTROYED");
    }

    public void sendNotification(String msg) {
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher_widget)
                        .setContentTitle("TimeTable")
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(msg))
                        .setContentText(msg)
                        .setSound(new RingtoneManager(this).getDefaultUri(RingtoneManager.TYPE_ALARM));

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(1, mBuilder.build());

    }
}
