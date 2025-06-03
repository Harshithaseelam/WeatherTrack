package com.example.weathertrack.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weathertrack.R;
import com.example.weathertrack.model.WeatherData;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class WeatherListAdapter extends RecyclerView.Adapter<WeatherListAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(WeatherData data);
    }

    private List<WeatherData> weatherList;
    private final OnItemClickListener listener;

    public WeatherListAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setWeatherList(List<WeatherData> list) {
        this.weatherList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WeatherListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_weather, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherListAdapter.ViewHolder holder, int position) {
        WeatherData data = weatherList.get(position);
        holder.bind(data, listener);
    }

    @Override
    public int getItemCount() {
        return weatherList != null ? weatherList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView date, temp;

        public ViewHolder(View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.textDate);
            temp = itemView.findViewById(R.id.textTemp);
        }

        public void bind(WeatherData data, OnItemClickListener listener) {
            SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d", Locale.getDefault());
            date.setText(sdf.format(data.getTimestamp()));
            temp.setText(data.getTemperature() + " °C");
            itemView.setOnClickListener(v -> listener.onItemClick(data));
        }
    }
}
