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

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {

    private List<WeatherData> data;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(WeatherData item);
    }

    public WeatherAdapter(List<WeatherData> data, OnItemClickListener listener) {
        this.data = data;
        this.listener = listener;
    }

    public void updateData(List<WeatherData> newData) {
        data = newData;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tempText, dateText;

        ViewHolder(View view) {
            super(view);
            TextView date = itemView.findViewById(R.id.textDate);
            TextView temp = itemView.findViewById(R.id.textTemp);

        }

        void bind(WeatherData item, OnItemClickListener listener) {
            tempText.setText("Temp: " + item.getTemperature() + "°C");
            dateText.setText(new SimpleDateFormat("EEE, MMM d", Locale.getDefault()).format(item.getTimestamp()));
            itemView.setOnClickListener(v -> listener.onItemClick(item));
        }
    }

    @NonNull
    @Override
    public WeatherAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weather, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherAdapter.ViewHolder holder, int position) {
        holder.bind(data.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }
}
