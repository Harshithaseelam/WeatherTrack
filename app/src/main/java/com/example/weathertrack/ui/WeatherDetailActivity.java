package com.example.weathertrack.ui;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.weathertrack.R;

public class WeatherDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_detail);

        TextView temp = findViewById(R.id.detailTemp);
        TextView humidity = findViewById(R.id.detailHumidity);
        TextView cond = findViewById(R.id.detailCondition);
        TextView time = findViewById(R.id.detailTime);

        temp.setText("Temp: " + getIntent().getFloatExtra("temperature", 0) + " °C");
        humidity.setText("Humidity: " + getIntent().getFloatExtra("humidity", 0) + " %");
        cond.setText("Condition: " + getIntent().getStringExtra("condition"));
        time.setText("Time: " + getIntent().getStringExtra("timestamp"));
    }
}
