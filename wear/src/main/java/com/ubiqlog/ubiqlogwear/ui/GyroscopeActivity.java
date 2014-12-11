package com.ubiqlog.ubiqlogwear.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.ubiqlog.ubiqlogwear.R;
import com.ubiqlog.ubiqlogwear.ui.fragments.GyroscopeChartFragment;

/**
 * Created by prajnashetty on 10/30/14.
 */
public class GyroscopeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("GyroscopeActivity","in GyroscopeActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gyroscope);
        if (savedInstanceState == null) {
            Log.i("GyroscopeActivity","in GyroscopeActivity");
            getFragmentManager().beginTransaction()
                    .add(R.id.gyroscope_chart_fragment, new GyroscopeChartFragment())
                    .commit();

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
//        getFragmentManager().beginTransaction()
//                .add(R.id.gyroscope_chart_fragment, new GyroscopeChartFragment())
//                .commit();
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