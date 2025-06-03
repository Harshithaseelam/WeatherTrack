package com.example.weathertrack.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.weathertrack.data.local.AppDatabase;
import com.example.weathertrack.data.local.WeatherDao;
import com.example.weathertrack.model.WeatherData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;

public class WeatherRepository {
    private WeatherDao weatherDao;
    private final Context context;



    public WeatherRepository(Context context) {
        this.context = context.getApplicationContext();
        this.weatherDao = AppDatabase.getInstance(this.context).weatherDao();
    }

    // ✅ Method 1: Fetch from mock API & save to DB


    public void fetchAndSaveWeather(Runnable onError) {
        try {
            InputStream is = context.getAssets().open("weather.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String jsonStr = new String(buffer, StandardCharsets.UTF_8);

            // Parse JSON as an array now
            JSONArray jsonArray = new JSONArray(jsonStr);

            Executors.newSingleThreadExecutor().execute(() -> {
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject obj = jsonArray.getJSONObject(i);

                        WeatherData data = new WeatherData();
                        data.setTemperature((float) obj.getDouble("temperature"));
                        data.setHumidity((float) obj.getDouble("humidity"));
                        data.setCondition(obj.getString("condition"));

                        // Assign timestamps going back one day each
                        Calendar cal = Calendar.getInstance();
                        cal.add(Calendar.DAY_OF_YEAR, -i);
                        data.setTimestamp(cal.getTime());

                        AppDatabase.getInstance(context).weatherDao().insert(data);
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (onError != null) onError.run();
                    }
                }
                System.out.println("Inserted weather data for " + jsonArray.length() + " days.");
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (onError != null) onError.run();
        }
    }



    public LiveData<List<WeatherData>> getWeatherSince(Date fromDate) {
        return weatherDao.getWeatherSince(fromDate);
    }


    public LiveData<WeatherData> getLatestWeather() {
        // Query the latest single record from DB
        return weatherDao.getLatest(); // Implement this query to get latest single WeatherData
    }
    // ✅ Method 2: Get past 7 days of weather data
    public LiveData<List<WeatherData>> getLast7DaysWeather() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -7);
        Date sevenDaysAgo = calendar.getTime();

        return weatherDao.getWeatherSince(sevenDaysAgo);
    }


}




