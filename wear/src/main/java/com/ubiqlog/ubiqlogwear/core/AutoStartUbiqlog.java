package com.ubiqlog.ubiqlogwear.core;

import com.ubiqlog.ubiqlogwear.common.Setting;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AutoStartUbiqlog extends BroadcastReceiver{
	
	@Override
	public void onReceive(Context arg0, Intent arg1) {
		try{
			if (Setting.AUTO_START){
				Toast.makeText(arg0, "MyReceiver Started", Toast.LENGTH_SHORT).show();
				Intent i = new Intent(arg0, Engine.class);
				arg0.startService(i);
			}
		}catch(Exception e){
			e.printStackTrace();

		}

	}

}
