package com.example.weathertrack.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.weathertrack.model.WeatherData;
import com.example.weathertrack.repository.WeatherRepository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class WeatherViewModel extends AndroidViewModel {
    private WeatherRepository repository;
    private LiveData<WeatherData> latestWeather;

    public WeatherViewModel(@NonNull Application application) {
        super(application);
        repository = new WeatherRepository(application);
        latestWeather = repository.getLatestWeather();
    }

    public LiveData<WeatherData> getLatestWeather() {
        return latestWeather;
    }

    public void refreshWeather(Runnable onError) {
        repository.fetchAndSaveWeather(onError);
    }
    public LiveData<List<WeatherData>> getLast7DaysWeather() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, -6); // Last 7 days including today
        Date fromDate = cal.getTime();
        return repository.getWeatherSince(fromDate);
    }
    

}
