package com.ubiqlog.ubiqlogwear.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ubiqlog.ubiqlogwear.R;
import com.ubiqlog.ubiqlogwear.ui.dummy.MyListAdapter;


public class wBluetooth extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barlist);

        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setText("Bluetooth");

        ImageView tvTitleLogo = (ImageView) findViewById(R.id.ivTitleLogo);
        tvTitleLogo.setImageResource(R.drawable.bluetoothicn2);

        String data[] = new String[]{"Connect", "Disconnect", "Connect", "Connect", "Disconnect", "Connect"};
        ListView list = (ListView) findViewById(R.id.listView);
        MyListAdapter adapter = new MyListAdapter(this, data, "blue");
        list.setAdapter(adapter);

    }

}