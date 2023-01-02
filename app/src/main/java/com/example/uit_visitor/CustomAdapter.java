package com.example.uit_visitor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uit_visitor.Models.Asset;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {


    private final Context context;
    private ArrayList<Asset> assetsList;

    public CustomAdapter(Context context ,ArrayList<Asset> assetsList) {
        this.context = context;
        this.assetsList = assetsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.asset_cardview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Asset asset = assetsList.get(position);
        holder.asset_name.setText(asset.name);
        holder.temp.setText(asset.id);
        holder.pressure.setText(asset.version);
        holder.temp_max.setText(asset.createdOn);
        holder.temp_min.setText(asset.accessPublicRead);
        holder.sea_level.setText(asset.realm);
        holder.feels_like.setText(asset.type);
    }

    @Override
    public int getItemCount() {
        return assetsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView asset_name;
        private final TextView temp;
        private final TextView humidity;
        private final TextView pressure;
        private final TextView temp_max;
        private final TextView temp_min;
        private final TextView sea_level;
        private final TextView feels_like;
        private final TextView grnd_level;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            asset_name = itemView.findViewById(R.id.assetName);
            temp = itemView.findViewById(R.id.temp);
            humidity = itemView.findViewById(R.id.humidity);
            pressure = itemView.findViewById(R.id.pressure);
            temp_max = itemView.findViewById(R.id.temp_max);
            temp_min = itemView.findViewById(R.id.temp_min);
            sea_level = itemView.findViewById(R.id.sea_level);
            feels_like = itemView.findViewById(R.id.feels_like);
            grnd_level = itemView.findViewById(R.id.grnd_level);
        }
    }
}
