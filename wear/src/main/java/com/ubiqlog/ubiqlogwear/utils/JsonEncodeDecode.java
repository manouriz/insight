package com.ubiqlog.ubiqlogwear.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by prajnashetty on 10/30/14.
 */
public class JsonEncodeDecode {

	public static final SimpleDateFormat dateformat = new SimpleDateFormat("M-d-yyyy HH:mm:ss");
	
	public static String EncodeApplication(String friendlyName, String processName, Date startTime, Date endTime) {	
		StringBuilder encodedString = new StringBuilder("");
		encodedString = encodedString.append("{\""+friendlyName+"\":{\"ProcessName\":\""
				+ processName + "\",\"Start\":\""
				+ dateformat.format(startTime) + "\",\"End\":\""
				+ dateformat.format(endTime) + "\"}}");
		return encodedString.toString();
	}


    public static String EncodeBluetooth(String deviceName, String deviceAddress, String bindState, Date timeStamp)
    {
        StringBuilder encodedString = new StringBuilder("");
        encodedString = encodedString.append("{\"Bluetooth\":{\"name\":\""
                + deviceName
                + "\",\"address\":\""
                + deviceAddress
                + "\",\"bond status\":\""
                + bindState
                + "\",\"time\":\""
                + dateformat.format(timeStamp)
                + "\"}}");

        return encodedString.toString();
    }

    public static String EncodeHeartRate(float heartRate, Date timeStamp)
    {
        StringBuilder encodedString = new StringBuilder("");
        encodedString = encodedString.append("{\"HeartRate\":{\"rate\":\""
                + heartRate
                + "\",\"time\":\""
                + dateformat.format(timeStamp)
                + "\"}}");

        return encodedString.toString();
    }

    public static String EncodeLight(float light, Date timeStamp)
    {
        StringBuilder encodedString = new StringBuilder("");
        encodedString = encodedString.append("{\"Light\":{\"level\":\""
                + light
                + "\",\"time\":\""
                + dateformat.format(timeStamp)
                + "\"}}");

        return encodedString.toString();
    }

    public static String EncodeBattery(float percent, Date timeStamp)
    {
        StringBuilder encodedString = new StringBuilder("");
        encodedString = encodedString.append("{\"Battery\":{\"percentage\":\""
                + percent
                + "\",\"time\":\""
                + dateformat.format(timeStamp)
                + "\"}}");

        return encodedString.toString();
    }

    public static String EncodeGyroscope(float x, float y, float z, Date timeStamp)
    {
        StringBuilder encodedString = new StringBuilder("");
        encodedString = encodedString.append("{\"Gyroscope\":{\"rotation x-axis\":\""
                + x
                + "\",\"rotation y-axis\":\""
                + y
                + "\",\"rotation z-axis\":\""
                + z
                + "\",\"time\":\""
                + dateformat.format(timeStamp)
                + "\"}}");

        return encodedString.toString();
    }

    public static String EncodeAccelerometer(float x, float y, float z, Date timeStamp)
    {
        StringBuilder encodedString = new StringBuilder("");
        encodedString = encodedString.append("{\"Accelerometer\":{\"acceleration x-axis\":\""
                + x
                + "\",\"acceleration y-axis\":\""
                + y
                + "\",\"acceleration z-axis\":\""
                + z
                + "\",\"time\":\""
                + dateformat.format(timeStamp)
                + "\"}}");

        return encodedString.toString();
    }

//	public static String EncodeActivity(String friendlyName, Date  timestampStart, Date timestampEnd, String intype, int inconfidence) {
//		StringBuilder encodedString = new StringBuilder("");
//		encodedString = encodedString.append("{\""+friendlyName+"\":{\"start\":\""
//				+ dateformat.format(timestampStart) + "\",\"end\":\""
//				+ dateformat.format(timestampEnd) + "\",\"type\":\""
//				+ intype+ "\",\"condfidence\":\""+inconfidence+"\"}}");
//
//		return encodedString.toString();
//	}
}

