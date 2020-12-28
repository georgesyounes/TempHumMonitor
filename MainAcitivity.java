package com.example.activitudemo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    private FingerprintManager fingerprintManager;
 
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        View A = findViewById(R.id.button);
        View B = findViewById(R.id.button1);






        A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           openGaugeActivity();
            }



        });

        B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUPS();
            }



        });


    }
    public void openGaugeActivity()
    {

        Intent intent = new Intent(this, GaugeActivity.class);
        startActivity(intent);

    }



    public void openUPS()
    {

        Intent intent = new Intent(this, UPS.class);
        startActivity(intent);

    }




}