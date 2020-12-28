package com.example.activitudemo;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lib.Snmp1;

import java.io.IOException;

import de.nitri.gauge.Gauge;

public class GaugeActivity extends AppCompatActivity  {


    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);





        doTheAutoRefresh();
        setContentView(R.layout.activity_gauge);

        final Gauge gauge11 = (Gauge) findViewById(R.id.gauge1);
        final Gauge gauge22 = (Gauge) findViewById(R.id.gauge2);
        final TextView textView = findViewById(R.id.textView);
        final TextView textView1 = findViewById(R.id.textView3);
        final TextView ServerRoom = findViewById(R.id.ServerRoom);

        final ImageView hum = (ImageView)findViewById(R.id.humdanger);
        final ImageView temp = (ImageView)findViewById(R.id.tempdanger);

        ServerRoom.setTextColor(Color.WHITE);

        textView.setTextColor(Color.WHITE);
        new Thread() {

            public void run() {
        float a = 0;
        float b = 0;



        try {


            Snmp1 s11 = new Snmp1("udp:78.108.164.82/161");
           s11= (Snmp1) Middle.Runner();
          a=  s11.getHum11();
          b=   s11.getTemp11();

         if(b>=16 && b<=24) //Normal
         {
            textView.setTextColor(Color.WHITE);



         }

         if(b>=26 && b<=30) //Hot
         {
             textView.setTextColor(Color.RED);
             runOnUiThread(new Runnable() {
                 @Override
                 public void run() {
                     temp.setVisibility(View.VISIBLE);

                 }
             });
         }

          if(b>=32) //Very Hot
            {
                textView.setTextColor(Color.RED);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        temp.setVisibility(View.VISIBLE);

                    }
                });
            }




            if(a>=35 && a<=85) //Normal
            {
                textView1.setTextColor(Color.WHITE);
            }

            if(a<=35) //dry
            {
                textView1.setTextColor(Color.RED);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hum.setVisibility(View.VISIBLE);

                    }
                });
            }

            if(a>=85) //Humid
            {
                textView1.setTextColor(Color.RED);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hum.setVisibility(View.VISIBLE);
                        hum.setVisibility(View.VISIBLE);

                    }
                });
            }


            gauge11.moveToValue((float) a);
            gauge22.moveToValue((float) b);
        } catch (IOException e) {
            gauge11.moveToValue((30));
            gauge22.moveToValue(20);
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
        final Gauge gauge11 = (Gauge) findViewById(R.id.gauge1);
        final Gauge gauge22 = (Gauge) findViewById(R.id.gauge2);

        new Thread() {

            public void run() {
                float a = 0;
                float b = 0;



                try {


                    Snmp1 s11 = new Snmp1("udp:78.108.164.82/161");
                    s11= (Snmp1) Middle.Runner();
                    a=  s11.getHum11();
                    b=   s11.getTemp11();



                    gauge11.moveToValue((float) a);
                    gauge22.moveToValue((float) b);
                } catch (IOException e) {
                    gauge11.moveToValue((30));
                    gauge22.moveToValue(20);
                } }
        }.start();






    }



}
