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
public class BatteryChartFragment extends Fragment {

    private LinearLayout chartLyt;
    private Animation fadeAnim;

    // London, UK
    private static final String CITY_ID = "2643743";

    ArrayList<Long> rateList = new ArrayList<Long>();
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

    /**
     * Called to have the fragment instantiate its user interface view.
     * This is optional, and non-graphical fragments can return null (which
     * is the default implementation).  This will be called between
     * {@link #onCreate(android.os.Bundle)} and {@link #onActivityCreated(android.os.Bundle)}.
     * <p/>
     * <p>If you return a View from here, you will later be called in
     * {@link #onDestroyView} when the view is being released.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to.  The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chart, container, false);
        chartLyt = (LinearLayout) v.findViewById(R.id.chart);
        return v;
    }

    private void getData()
    {
        try {
            Calendar cal = Calendar.getInstance();
            //cal.add(Calendar.DATE, -1);

            File dir = Environment.getExternalStorageDirectory();
            File logFile = new File(dir,"/ubiqlog/log_" + filedateformat.format(cal.getTime()) + ".txt");
            Log.i("ChartFragment", "logFile =" + logFile);
            if (logFile != null) {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(logFile));
                String read;
                StringBuilder builder = new StringBuilder("");
                while ((read = bufferedReader.readLine()) != null) {
                    JSONObject jsonObject1 = new JSONObject(read);
                    if (jsonObject1.optJSONObject("Battery") != null) {

                        JSONObject batteryObject = jsonObject1.optJSONObject("Battery");

                        Long batteryLevel = batteryObject.getLong("percentage");
                        rateList.add(batteryLevel);
                        Date date = dateformat.parse(batteryObject.getString("time"));
                        Calendar cal1 = Calendar.getInstance();
                        cal1.setTime(date);
                        int minutes = cal1.get(Calendar.MINUTE);
                        if (minutes > 30)
                            timeList.add(cal1.get(Calendar.HOUR_OF_DAY) + 1);
                        else
                            timeList.add(cal1.get(Calendar.HOUR_OF_DAY));
                        Log.i("ChartFragment", "batteryLevel =" + batteryLevel);
                        Log.i("ChartFragment", "Date =" + date);
                    }
                }
                Log.d("Output", builder.toString());
                bufferedReader.close();
            }

            Toast.makeText(getActivity(), "Data retrieved", Toast.LENGTH_LONG).show();
            chartLyt.addView(createGraph(), 0);
        } catch (Exception e) {

            Log.e("ChartFragment", "--------Failed to read file-----"+ e.getMessage() + "; Stack: " +  Log.getStackTraceString(e));

        }

    }

    private View createGraph() {
        // We start creating the XYSeries to plot the temperature
        XYSeries series = new XYSeries("Battery Percentage");

        for (int i = 0; i < timeList.size(); i++) {
            series.add(timeList.get(i), rateList.get(i));
        }


        // Now we create the renderer
        XYSeriesRenderer renderer = new XYSeriesRenderer();
        renderer.setLineWidth(2);
        renderer.setColor(Color.RED);
        // Include low and max value
        renderer.setDisplayBoundingPoints(true);
        // we add point markers
        renderer.setPointStyle(PointStyle.CIRCLE);
        renderer.setPointStrokeWidth(3);


        // Now we add our series
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        dataset.addSeries(series);

        // Finaly we create the multiple series renderer to control the graph
        XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
        mRenderer.addSeriesRenderer(renderer);
        mRenderer.setBackgroundColor(Color.WHITE);
        mRenderer.setApplyBackgroundColor(true);
        mRenderer.setXLabelsColor(Color.BLACK);
        mRenderer.setYLabelsColor(0,Color.BLACK);

        // We want to avoid black border
        mRenderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00)); // transparent margins
        mRenderer.setPanEnabled(false, false);
        mRenderer.setYAxisMax(100);
        mRenderer.setYAxisMin(0);
        mRenderer.setShowGrid(true); // we show the grid
        mRenderer.setChartTitle("BatteryData");
        mRenderer.setLabelsColor(Color.BLACK);
        GraphicalView chartView = ChartFactory.getLineChartView(getActivity(), dataset, mRenderer);


        // Enable chart click
//        mRenderer.setClickEnabled(true);
//        chartView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                applyAnim(v, createPressGraph());
//            }
//        });

        return chartView;
    }

//    private View createPressGraph() {
//        XYSeries series = new XYSeries("London Pressure hourly");
//
//        // We start filling the series
//        int hour = 0;
//
//        for (HourForecast hf : nextHourForecast) {
//            series.add(hour++, hf.weather.currentCondition.getPressure());
//
//            if (hour > 24)
//                break;
//        }
//
//        // Now we create the renderer
//        XYSeriesRenderer renderer = new XYSeriesRenderer();
//        renderer.setColor(Color.BLUE);
//        // Include low and max value
//        renderer.setDisplayBoundingPoints(true);
//
//        // Now we add our series
//        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
//        dataset.addSeries(series);
//
//        // Finaly we create the multiple series renderer to control the graph
//        XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
//        mRenderer.addSeriesRenderer(renderer);
//
//        // We want to avoid black border
//        mRenderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00)); // transparent margins
//        // Disable Pan on two axis
//        mRenderer.setPanEnabled(false, false);
//
//        mRenderer.setShowGrid(true); // we show the grid
//        mRenderer.setBarSpacing(1);
//
//        GraphicalView chartView = ChartFactory.getBarChartView(getActivity(), dataset, mRenderer, BarChart.Type.DEFAULT);
//
//        // Enable chart click
//        mRenderer.setClickEnabled(false);
//
//        return chartView;
//    }



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