package com.example.weathertrack.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.weathertrack.model.WeatherData;

import java.util.Date;
import java.util.List;

@Dao
public interface WeatherDao {
    @Insert
    void insert(WeatherData data);

    @Query("SELECT * FROM weather_table ORDER BY timestamp DESC")
    LiveData<List<WeatherData>> getAll();
    @Query("SELECT * FROM weather_table ORDER BY timestamp DESC LIMIT 1")
    LiveData<WeatherData> getLatest();


    @Query("SELECT * FROM weather_table WHERE timestamp >= :fromDate ORDER BY timestamp ASC")
    LiveData<List<WeatherData>> getWeatherSince(Date fromDate);
}
