package com.ubiqlog.ubiqlogwear.ui.fragments;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.Toast;


import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.json.JSONObject;

import com.ubiqlog.ubiqlogwear.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by prajnashetty on 10/30/14.
 */
public class GyroscopeChartFragment extends Fragment {

    private LinearLayout chartLyt;
    private Animation fadeAnim;
    private int greater;

    ArrayList<Long> xList = new ArrayList<Long>();
    ArrayList<Long> yList = new ArrayList<Long>();
    ArrayList<Long> zList = new ArrayList<Long>();
    ArrayList<Integer> timeList = new ArrayList<Integer>();

    public static final SimpleDateFormat dateformat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
    public static final SimpleDateFormat filedateformat = new SimpleDateFormat("MM-dd-yyyy");


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //fadeAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_anim);
        getData();
    }

    @Override
    public void onStart(){
        super.onStart();
        getData();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("GyroscopeChart", "in onCreateView");
        View v = inflater.inflate(R.layout.fragment_gyroscope_chart, container, false);
        chartLyt = (LinearLayout) v.findViewById(R.id.gyroscope_chart);
        return v;
    }

    private void getData()
    {

        try {
            Calendar cal = Calendar.getInstance();
            //cal.add(Calendar.DATE, -1);

            File dir = Environment.getExternalStorageDirectory();
            File logFile = new File(dir,"/ubiqlog/log_" + filedateformat.format(cal.getTime()) + ".txt");
            Log.i("GyroscopeChart", "logFile =" + logFile);
            if (logFile != null) {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(logFile));
                String read;
                StringBuilder builder = new StringBuilder("");
                while ((read = bufferedReader.readLine()) != null) {
                    JSONObject jsonObject1 = new JSONObject(read);
                    if (jsonObject1.optJSONObject("Gyroscope") != null) {

                        JSONObject batteryObject = jsonObject1.optJSONObject("Gyroscope");

                        Long x = batteryObject.getLong("rotation x-axis");
                        Long y = batteryObject.getLong("rotation y-axis");
                        Long z = batteryObject.getLong("rotation z-axis");
                        xList.add(x);
                        yList.add(y);
                        zList.add(z);
                        Date date = dateformat.parse(batteryObject.getString("time"));
                        Calendar cal1 = Calendar.getInstance();
                        cal1.setTime(date);
                        int minutes = cal1.get(Calendar.MINUTE);
                        if (minutes > 30)
                            timeList.add(cal1.get(Calendar.HOUR_OF_DAY) + 1);
                        else
                            timeList.add(cal1.get(Calendar.HOUR_OF_DAY));
                        Log.i("GyroscopeChart", "x =" + x + ", y = " + y + ", z = " + z);
                        Log.i("GyroscopeChart", "Date =" + date);
                    }
                }
                Log.d("Output", builder.toString());
                bufferedReader.close();
            }
            Toast.makeText(getActivity(), "Data retrieved", Toast.LENGTH_LONG).show();
            chartLyt.addView(createGraph(), 0);
        } catch (Exception e) {

            Log.e("GyroscopeChart", "--------Failed to read file-----"+ e.getMessage() + "; Stack: " +  Log.getStackTraceString(e));

        }

    }

    private View createGraph() {
        Log.i("GyroscopeChart", "In Create Chart");
        // We start creating the XYSeries to plot the temperature
        XYSeries series1 = new XYSeries("X");
        XYSeries series2 = new XYSeries("Y");
        XYSeries series3 = new XYSeries("Z");

        // We start filling the series
        int hour = 0;

        for (int i = 0; i < timeList.size(); i++) {
            series1.add(timeList.get(i), xList.get(i));
            series2.add(timeList.get(i), yList.get(i));
            series3.add(timeList.get(i), zList.get(i));
        }

        greater = xList.size();
        if(yList.size() > greater){
            greater = yList.size();
        }else if(zList.size() > greater){
            greater = zList.size();
        }


        XYSeriesRenderer renderer1 = new XYSeriesRenderer();
        renderer1.setLineWidth(2);
        renderer1.setColor(Color.RED);
        renderer1.setDisplayBoundingPoints(true);
        renderer1.setPointStyle(PointStyle.CIRCLE);
        renderer1.setPointStrokeWidth(3);

        XYSeriesRenderer renderer2 = new XYSeriesRenderer();
        renderer2.setLineWidth(2);
        renderer2.setColor(Color.GREEN);
        renderer2.setDisplayBoundingPoints(true);
        renderer2.setPointStyle(PointStyle.CIRCLE);
        renderer2.setPointStrokeWidth(3);

        XYSeriesRenderer renderer3 = new XYSeriesRenderer();
        renderer3.setLineWidth(2);
        renderer3.setColor(Color.BLUE);
        renderer3.setDisplayBoundingPoints(true);
        renderer3.setPointStyle(PointStyle.CIRCLE);
        renderer3.setPointStrokeWidth(3);


        // Now we add our series
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        dataset.addSeries(series1);
        dataset.addSeries(series2);
        dataset.addSeries(series3);

        // Finaly we create the multiple series renderer to control the graph
        XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
        mRenderer.addSeriesRenderer(renderer1);
        mRenderer.addSeriesRenderer(renderer2);
        mRenderer.addSeriesRenderer(renderer3);

        mRenderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00)); // transparent margins
        // Disable Pan on two axis
        mRenderer.setPanEnabled(false, false);
        mRenderer.setShowGrid(true);
        mRenderer.setBackgroundColor(Color.WHITE);
        mRenderer.setMarginsColor(Color.WHITE);
        mRenderer.setAxesColor(Color.BLACK);
        mRenderer.setXLabelsColor(Color.BLACK);
        mRenderer.setYLabelsColor(0,Color.BLACK);
        mRenderer.setApplyBackgroundColor(true);

        if(greater < 20){
            mRenderer.setYAxisMax(20);
        }else{
            mRenderer.setYAxisMin(greater-20);
            mRenderer.setYAxisMax(greater);
        }
        mRenderer.setChartTitle("GyroscopeData");
        mRenderer.setLabelsColor(Color.BLACK);
        GraphicalView chartView = ChartFactory.getLineChartView(getActivity(), dataset, mRenderer);

        Log.i("GyroscopeChart", "Finished creating Chart");
        return chartView;
    }

    private void applyAnim(final View v, final View nextView) {

        Animation.AnimationListener list = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                chartLyt.removeViewAt(0);
                chartLyt.addView(nextView,0);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        };
        fadeAnim.setAnimationListener(list);
        v.startAnimation(fadeAnim);
    }
}