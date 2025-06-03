package com.example.weathertrack.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.weathertrack.R;
import com.example.weathertrack.model.WeatherData;
import com.example.weathertrack.repository.WeatherRepository;
import com.example.weathertrack.viewmodel.WeatherViewModel;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import android.graphics.Color;


import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.LinkedHashMap;

import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private WeatherViewModel viewModel;
    private WeatherRepository weatherRepository;
    private TextView tempText, humidityText, conditionText;
    private LineChart lineChart;
    private void showAverageTemperatureGraph(List<WeatherData> weatherList) {
        // Group by date string (e.g., "2024-06-03")
        Map<String, List<Float>> dailyTemps = new LinkedHashMap<>();


        SimpleDateFormat sdf = new SimpleDateFormat("EEE", Locale.getDefault());


        for (WeatherData data : weatherList) {
            String dateKey = sdf.format(data.getTimestamp());
            dailyTemps.putIfAbsent(dateKey, new ArrayList<>());
            dailyTemps.get(dateKey).add(data.getTemperature());
        }

        List<Entry> entries = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        int index = 0;

        for (Map.Entry<String, List<Float>> entry : dailyTemps.entrySet()) {
            float avg = 0;
            for (Float t : entry.getValue()) avg += t;
            avg /= entry.getValue().size();

            entries.add(new Entry(index, avg));
            labels.add(entry.getKey());
            index++;
        }

        LineDataSet dataSet = new LineDataSet(entries, "Avg Temperature (°C)");
        dataSet.setColor(Color.BLUE);
        dataSet.setCircleColor(Color.RED);
        dataSet.setLineWidth(2f);
        dataSet.setValueTextSize(10f);
        dataSet.setDrawFilled(true);

        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));

        lineChart.getAxisRight().setEnabled(false);
        lineChart.getDescription().setEnabled(false);
        lineChart.invalidate();
    }

    private void setupTemperatureChart(List<WeatherData> weatherList) {
        LineChart chart = findViewById(R.id.lineChartTemperature);

        List<Entry> entries = new ArrayList<>();
        final List<String> labels = new ArrayList<>();

        for (int i = 0; i < weatherList.size(); i++) {
            WeatherData data = weatherList.get(i);
            entries.add(new Entry(i, data.getTemperature()));
            labels.add(new SimpleDateFormat("EEE", Locale.getDefault()).format(data.getTimestamp()));

        }

        LineDataSet dataSet = new LineDataSet(entries, "Temperature (°C)");
        dataSet.setColor(getResources().getColor(R.color.purple_700));
        dataSet.setValueTextColor(getResources().getColor(R.color.black));
        dataSet.setLineWidth(2f);
        dataSet.setCircleRadius(4f);
        dataSet.setDrawValues(false);

        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);

        // Customize X axis
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new com.github.mikephil.charting.formatter.ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                int index = (int) value;
                return index >= 0 && index < labels.size() ? labels.get(index) : "";
            }
        });


        chart.getDescription().setEnabled(false);
        chart.getLegend().setEnabled(false);
        chart.invalidate(); // Refresh the chart
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lineChart = findViewById(R.id.lineChartTemperature);

        weatherRepository = new WeatherRepository(this);
        viewModel = new ViewModelProvider(this).get(WeatherViewModel.class);

        Button refreshButton = findViewById(R.id.button_refresh);
        lineChart = findViewById(R.id.lineChartTemperature);
        tempText = findViewById(R.id.text_temperature);
        humidityText = findViewById(R.id.text_humidity);
        conditionText = findViewById(R.id.text_condition);

        refreshButton.setOnClickListener(v -> {
            viewModel.refreshWeather(() -> {
                runOnUiThread(() ->
                        Toast.makeText(this, "Failed to refresh weather", Toast.LENGTH_SHORT).show()
                );
            });
        });

        // Observe weather LiveData from ViewModel to update UI when data changes
        viewModel.getLatestWeather().observe(this, weatherData -> {
            if (weatherData != null) {
                updateUI(weatherData);
            } else {
                clearUI();
            }
        });
        Button summaryButton = findViewById(R.id.button_summary);
        summaryButton.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, WeeklySummaryActivity.class));
        });
        weatherRepository.getLast7DaysWeather().observe(this, weatherList -> {
            if (weatherList != null && !weatherList.isEmpty()) {
                showAverageTemperatureGraph(weatherList);
            }
        });


    }

    private void updateUI(WeatherData weatherData) {
        tempText.setText("Temperature: " + weatherData.getTemperature() + " °C");
        humidityText.setText("Humidity: " + weatherData.getHumidity() + " %");
        conditionText.setText("Condition: " + weatherData.getCondition());
    }

    private void clearUI() {
        tempText.setText("Temperature: --");
        humidityText.setText("Humidity: --");
        conditionText.setText("Condition: --");
    }

}
