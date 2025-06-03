package com.example.weathertrack.ui;

import static androidx.core.content.ContextCompat.startActivity;

import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.weathertrack.R;
import com.example.weathertrack.model.WeatherData;
import com.example.weathertrack.viewmodel.WeatherViewModel;

import java.util.ArrayList;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weathertrack.R;
import com.example.weathertrack.model.WeatherData;
import com.example.weathertrack.viewmodel.WeatherViewModel;

import java.util.List;

public class WeeklySummaryActivity extends AppCompatActivity implements WeatherListAdapter.OnItemClickListener {

    private WeatherViewModel viewModel;
    private WeatherListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_summary);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewWeather);
        adapter = new WeatherListAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(WeatherViewModel.class);
        viewModel.getLast7DaysWeather().observe(this, weatherData -> {
            if (weatherData != null) adapter.setWeatherList(weatherData);
        });
    }

    @Override
    public void onItemClick(WeatherData data) {
        Intent intent = new Intent(this, WeatherDetailActivity.class);
        intent.putExtra("temperature", data.getTemperature());
        intent.putExtra("humidity", data.getHumidity());
        intent.putExtra("condition", data.getCondition());
        intent.putExtra("timestamp", data.getTimestamp().toString());
        startActivity(intent);
    }
}
