package com.example.weathertrack.data.remote;

import android.content.Context;
import android.content.res.AssetManager;

import com.example.weathertrack.model.WeatherData;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class WeatherRemoteDataSource {
    public WeatherData fetchWeather(Context context) throws IOException {
        AssetManager assetManager = context.getAssets();
        InputStream input = assetManager.open("weather.json");
        Reader reader = new InputStreamReader(input);
        return new Gson().fromJson(reader, WeatherData.class);
    }
}
