package com.teamuxilium.auxilium;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;

public class SMS extends AppCompatActivity {

    SharedPreferences sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
        sharedPref =  this.getPreferences(Context.MODE_PRIVATE);
        Send();
    }
    public String get_contact(){
        String msg = sharedPref.getString("Message",null ); //change null to default msg
        return msg;
    }
    public String get_number(){
        long no = sharedPref.getLong("Phone_number",1);
        String temp = Long.toString(no);
        return temp;
    }

    public String sms = get_contact();
    public String no = get_number();
    public void set_loc() {
        LocationListener myLocLis = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                sms = sms + "\nLatitude" + location.getLatitude() + "\nLongitude" + location.getLongitude();

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {
            }

            @Override
            public void onProviderDisabled(String s) {
            }
        };
    }
    protected void Send() {
        SmsManager manager = SmsManager.getDefault();
        manager.sendTextMessage(no,null,sms,null,null);
    }
}

