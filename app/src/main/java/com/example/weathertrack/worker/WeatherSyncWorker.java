package com.example.weathertrack.worker;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.weathertrack.repository.WeatherRepository;

public class WeatherSyncWorker extends Worker {

    public WeatherSyncWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        WeatherRepository repository = new WeatherRepository(getApplicationContext());

        repository.fetchAndSaveWeather(() -> {
            // Error callback: if DB insert fails, you can log here
            // Can't update UI here because this is background worker
            // Just log error
            // For example:
            android.util.Log.e("WeatherSyncWorker", "Error saving weather data");
        });

        // Return success immediately (no blocking)
        return Result.success();
    }

}
