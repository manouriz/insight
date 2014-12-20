package com.ubiqlog.ubiqlogwear.sensors;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

/**
 * Check this code: http://weimenglee.blogspot.com/2014/03/android-tip-notification-listener.html
 * Created by rawassizadeh on 12/19/14.
 */
class NotificationSensor extends NotificationListenerService {
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        //---show current notification---
        Log.i("", "---A notification appears---");
        Log.i("","ID :" + sbn.getId() + "\t" +
                sbn.getNotification().tickerText + "\t" +
                sbn.getPackageName());
        Log.i("","--------------------------");

        //---show all active notifications---
        Log.i("","===All Notifications===");
        for (StatusBarNotification notif :
                this.getActiveNotifications()) {
            Log.i("","ID :" + notif.getId() + "\t" +
                    notif.getNotification().tickerText + "\t" +
                    notif.getPackageName());
        }
        Log.i("","=======================");
    }

    @Override
    public void onNotificationRemoved(
            StatusBarNotification sbn) {
        Log.i("","---a notification has been removed---");
        Log.i("","ID :" + sbn.getId() + "\t" +
                sbn.getNotification().tickerText + "\t" +
                sbn.getPackageName());
        Log.i("","--------------------------");

    }
}
