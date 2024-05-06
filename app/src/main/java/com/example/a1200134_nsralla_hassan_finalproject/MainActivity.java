package com.example.a1200134_nsralla_hassan_finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button getStartedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get the get started button
        getStartedButton = findViewById(R.id.get_started_button);

        // add event listener to the button
        getStartedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // connect to API
                new FetchPizzaTypes(MainActivity.this).execute();
            }
        });

    }
}