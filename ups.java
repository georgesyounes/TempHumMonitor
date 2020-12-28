package com.example.activitudemo;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lib.Snmp1;

import java.io.IOException;

import de.nitri.gauge.Gauge;

public class UPS extends AppCompatActivity {


    private final Handler handler = new Handler();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        doTheAutoRefresh();
        setContentView(R.layout.activity_u_p_s);


        final Gauge gauge22 = (Gauge) findViewById(R.id.gauge3);
        final ImageView Battery = findViewById(R.id.imageView1);
       final TextView Text = findViewById(R.id.textView2);

        final TextView textView1 = findViewById(R.id.textView1);
        final TextView textView2 = findViewById(R.id.textView);
        final TextView textView3 = findViewById(R.id.textView2);
        final TextView UPS = findViewById(R.id.UPSroom);
        final ImageView Bat = (ImageView)findViewById(R.id.batDanger);
        final ImageView Temp = (ImageView)findViewById(R.id.tempdanger);

        UPS.setTextColor(Color.WHITE);
        textView3.setTextColor(Color.WHITE);
        new Thread() {

            public void run() {
                float t = 0;
                float b = 0;



                try {
                    Snmp1 s11 = new Snmp1("udp:78.108.164.82/162");
                    s11= (Snmp1) Middle.Running();
                    t=  s11.getUPStemp();
                   b=   s11.getUPSBattery();
                   int x;
                   x= (int)b;
                    final String s=Integer. toString(x);


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                           Text.setText(s + " %");
                        }
                    });


                 if(b>= 90)
                 {
                     Battery.setImageDrawable(getResources().getDrawable(R.drawable.b100));
                     textView1.setTextColor(Color.WHITE);


                 }
                 else if(b<90 && b>=65)
                 {
                     Battery.setImageDrawable(getResources().getDrawable(R.drawable.b75));
                     textView1.setTextColor(Color.WHITE);


                 }

                 else if(b<65 && b>=40)
                 {
                     textView1.setTextColor(Color.RED);
                     Battery.setImageDrawable(getResources().getDrawable(R.drawable.b50));
                     runOnUiThread(new Runnable() {
                         @Override
                         public void run() {
                             Bat.setVisibility(View.VISIBLE);

                         }
                     });
                 }

                 else if(b<40 && b>=10)
                 {
                     textView1.setTextColor(Color.RED);
                     Battery.setImageDrawable(getResources().getDrawable(R.drawable.b25));
                     runOnUiThread(new Runnable() {
                         @Override
                         public void run() {
                             Bat.setVisibility(View.VISIBLE);

                         }
                     });
                 }
                 else
                 {
                     textView1.setTextColor(Color.RED);
                     Battery.setImageDrawable(getResources().getDrawable(R.drawable.b0));
                     runOnUiThread(new Runnable() {
                         @Override
                         public void run() {
                             Bat.setVisibility(View.VISIBLE);

                         }
                     });
                 }

                    if(t<=45) //Normal
                    {
                        textView2.setTextColor(Color.WHITE);

                    }

                    if( t>=50 && t<=55) //Hot
                    {
                        textView2.setTextColor(Color.RED);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Temp.setVisibility(View.VISIBLE);

                            }
                        });
                    }

                    if( t>=60) //Very Hot
                    {
                        textView2.setTextColor(Color.RED);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Temp.setVisibility(View.VISIBLE);

                            }
                        });
                    }




                    gauge22.moveToValue((float) t);
                } catch (IOException e) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Error In Connecting to Network",
                            Toast.LENGTH_SHORT);

                    toast.show();


                } }
        }.start();







    }
    private void doTheAutoRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Refresh();
                doTheAutoRefresh();
            }
        }, 10000);
    }

    public void Refresh()
    {
        final Gauge gauge22 = (Gauge) findViewById(R.id.gauge3);
        final ImageView Battery = findViewById(R.id.imageView1);
        final TextView Text = findViewById(R.id.textView2);

        new Thread() {

            public void run() {
                float t = 0;
                float b = 0;



                try {
                    Snmp1 s11 = new Snmp1("udp:78.108.164.82/162");
                    s11= (Snmp1) Middle.Running();
                    t=  s11.getUPStemp();
                    b=   s11.getUPSBattery();
                    int x;
                    x= (int)b;
                    final String s=Integer. toString(x);


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Text.setText(s + " %");
                        }
                    });




                    gauge22.moveToValue((float) t);
                } catch (IOException e) {

                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Error In Connecting to Network",
                            Toast.LENGTH_SHORT);

                    toast.show();

                } }
        }.start();



    }

}