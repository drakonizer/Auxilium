package com.teamuxilium.auxilium;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class Countdown extends AppCompatActivity {
    int time_call;
    int time_sms =0;
    TextView mTextField;
    CountDownTimer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown);
        mTextField = (TextView)findViewById(R.id.Timer);
        Intent inten = new Intent();
        int prob = inten.getIntExtra("Intensity", 0);
        switch (prob) {
            case 1:
                time_call = 480;
                time_sms = 240;
                break;
            case 2:
                time_call = 240;
                time_sms = 120;
                break;
            case 3:
                time_call = 120;
                time_sms = 0; //make object of sms class and call and run the required two methods
                break;
        }
        timer = new CountDownTimer(time_call, 1000) {
            int i = 0;

            public void onTick(long millisUntilFinished) {
                mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
                i++;
                if (i == time_sms) {
                    //make object of sms class and call and run the required two methods
                }
            }

            public void onFinish() {
                mTextField.setText("We have sent Emergency Services to your location");
                sendMsg();
            }
        }.start();
    }
    private void sendMsg()
    {
        Intent intent = new Intent(this, SMS.class);
        startActivity(intent);
    }
    private void goToBack()
    {
        Intent intt = new Intent(this, SendReceiveData.class);
        startActivity(intt);
    }
    public void Stop_Timer() {
        timer.cancel();
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        goToBack();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        Intent intt = new Intent(Intent.ACTION_MAIN);
                        intt.addCategory(Intent.CATEGORY_HOME);
                        intt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intt);
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Would you like to continue the trip?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

}

