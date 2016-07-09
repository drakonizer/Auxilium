package com.teamuxilium.auxilium;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EmergencyContact extends AppCompatActivity {
    SharedPreferences prefs = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = this.getPreferences(Context.MODE_PRIVATE);
        setContentView(R.layout.activity_emergency_contact);
    }
    @Override
    public void onResume()
    {
        super.onResume();
        if (!prefs.getBoolean("firstrun", true)) {
            Intent intent = new Intent(this, BluetoothPair.class);
            startActivity(intent);
        }
            prefs.edit().putBoolean("firstrun", false).commit();
    }
    public void BtnClick(View  v) {
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        EditText s = (EditText)findViewById(R.id.number);
        EditText msg = (EditText)findViewById(R.id.message);
        String mesg = msg.getText().toString();
        Long str = Long.parseLong(s.getText().toString());
        editor.putLong("Phone_Number", str);
        editor.putString("Message",mesg);
        editor.putBoolean("firstrun", false);
        editor.commit();
        Intent intent = new Intent(this, BluetoothPair.class);
        startActivity(intent);
    }
}
