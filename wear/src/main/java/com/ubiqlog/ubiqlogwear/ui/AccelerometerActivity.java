package com.ubiqlog.ubiqlogwear.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.ubiqlog.ubiqlogwear.R;
import com.ubiqlog.ubiqlogwear.ui.fragments.AccelerometerChartFragment;



public class AccelerometerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("AccelerometerActivity","in AccelerometerActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery_level);
        if (savedInstanceState == null) {
            Log.i("AccelerometerActivity","in AccelerometerActivity");
            getFragmentManager().beginTransaction()
                    .add(R.id.chart_fragment, new AccelerometerChartFragment())
            .commit();

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        getFragmentManager().beginTransaction()
                .add(R.id.chart_fragment, new AccelerometerChartFragment())
                .commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}