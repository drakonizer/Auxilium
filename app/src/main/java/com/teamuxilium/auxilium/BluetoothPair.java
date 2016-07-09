package com.teamuxilium.auxilium;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.util.Set;
import static android.widget.AdapterView.*;

public class BluetoothPair extends AppCompatActivity {
    // Debugging for LOGCAT
    // Debugging for LOGCAT
    private static final String TAG = "DeviceListActivity";
    //  private static final boolean D = true;
    // declare button for launching website and textview for connection status
    // EXTRA string to send on to mainactivity
    public static String EXTRA_DEVICE_ADDRESS = "device_address";
    // Member fields
    private BluetoothAdapter mBtAdapter;
    private ArrayAdapter<String> mPairedDevicesArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_pair);
        super.onResume();
        checkBTState();
        // Initialize array adapter for paired devices
        mPairedDevicesArrayAdapter = new ArrayAdapter<>(this, R.layout.activity_bluetooth_pair, R.id.connecting);
        // Find and set up the ListView for paired devices
        ListView pairedListView = (ListView) findViewById(R.id.paired_devices);
        pairedListView.setAdapter(mPairedDevicesArrayAdapter);
        pairedListView.setOnItemClickListener(mDeviceClickListener);
        // Get the local Bluetooth adapter
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        try {
            // Get a set of currently paired devices and append to 'pairedDevices'
            Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();
            // Add previosuly paired devices to the array
            if (pairedDevices.size() > 0) {
                findViewById(R.id.title_paired_devices).setVisibility(View.VISIBLE);//make title viewable
                for (BluetoothDevice device : pairedDevices) {
                    mPairedDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                }
            } else {
                String noDevices = "No Devices...";
                mPairedDevicesArrayAdapter.add(noDevices);
            }
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }
    }
    // Set up on-click listener for the list (nicked this - unsure)
    private OnItemClickListener mDeviceClickListener = new OnItemClickListener() {
        public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {
            // Get the device MAC address, which is the last 17 chars in the View
            Object item = av.getItemAtPosition(arg2);
            String info = item.toString();
            String address = info.substring(info.length() - 17);
            // Make an intent to start next activity while taking an extra which is the MAC address.
            Intent i = new Intent(BluetoothPair.this, SendReceiveData.class);
            i.putExtra(EXTRA_DEVICE_ADDRESS, address);
            startActivity(i);
        }
    };
    private void checkBTState() {
        // Check device has Bluetooth and that it is turned on
        mBtAdapter = BluetoothAdapter.getDefaultAdapter(); // CHECK THIS OUT! IT WORKS!!!
        if (mBtAdapter == null) {
            Toast.makeText(getBaseContext(), "Device does not support Bluetooth", Toast.LENGTH_SHORT).show();
        } else {
            if (mBtAdapter.isEnabled()) {
                Log.d(TAG, "...Bluetooth ON...");
            } else {
                //Prompt user to turn on Bluetooth
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);

            }
        }
    }
}

