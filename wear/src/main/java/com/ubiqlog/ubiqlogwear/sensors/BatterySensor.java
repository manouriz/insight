package com.ubiqlog.ubiqlogwear.sensors;

import android.content.IntentFilter;
import android.os.IBinder;
import android.content.Intent;
import android.app.Service;
import android.widget.Toast;
import android.os.BatteryManager;
import android.os.Handler;
import android.util.Log;

import com.ubiqlog.ubiqlogwear.core.DataAcquisitor;
import com.ubiqlog.ubiqlogwear.utils.JsonEncodeDecode;

import java.util.Date;

/**
 * Created by prajnashetty on 10/30/14.
 */

public class BatterySensor extends Service {

    private long lastUpdate;
    Intent batteryStatus;
    boolean isCharging;

    private Handler objHandler = new Handler();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private Runnable doBatteryLogging = new Runnable() {
        public void run() {
            readSensor();
            objHandler.postDelayed(doBatteryLogging, SensorConstants.BATTERY_LOG_INTERVAL);
        }
    };

    private void readSensor() {
        //Log.d("Battery-Logging", "--- Reading Battery " + (System.currentTimeMillis()/1000));
        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL;
        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        Date _currentDate = new Date();

        //if (Math.pow((lastUpdate - level),2) > 25) {
            String jsonString = JsonEncodeDecode.EncodeBattery(level, _currentDate);
            DataAcquisitor.dataBuff.add(jsonString);
            Log.i("Battery-Logging", jsonString);
            lastUpdate = level;
        //}
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("Battery-Logging", "--- onStart");
        readSensor();
        objHandler.postDelayed(doBatteryLogging, SensorConstants.BATTERY_LOG_INTERVAL);
        return START_STICKY;
    }

    @Override
    public void onCreate() {

        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        batteryStatus = getApplicationContext().registerReceiver(null, ifilter);

        Toast.makeText(getApplicationContext(), "Started Battery Logging", Toast.LENGTH_SHORT).show();
        super.onCreate();

    }


    @Override
    public void onDestroy() {
        objHandler.removeCallbacks(doBatteryLogging);
        Toast.makeText(this, "Stopped Battery Logging", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

}