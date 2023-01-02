package com.example.uit_visitor;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;

import com.example.uit_visitor.Models.Asset;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.uit_visitor.databinding.FragmentAssetDetailInfoListDialogItemBinding;
import com.example.uit_visitor.databinding.FragmentAssetDetailInfoListDialogBinding;
import com.example.uit_visitor.databinding.AssetCardviewBinding;
import com.google.gson.Gson;

/**
 * <p>A fragment that shows a list of items as a modal bottom sheet.</p>
 * <p>You can show this modal bottom sheet from your activity like this:</p>
 * <pre>
 *     asset_detail_info.newInstance(30).show(getSupportFragmentManager(), "dialog");
 * </pre>
 */
public class asset_detail_info extends BottomSheetDialogFragment {

    private static Gson gson;
    // TODO: Customize parameter argument names
    private static final String ARG_ITEM_COUNT = "asset info";
    private FragmentAssetDetailInfoListDialogBinding binding;

    // TODO: Customize parameters
    public static asset_detail_info newInstance(Asset asset) {
        final asset_detail_info fragment = new asset_detail_info();
        final Bundle args = new Bundle();
        gson = new Gson();
        args.putString(ARG_ITEM_COUNT, gson.toJson(asset));
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentAssetDetailInfoListDialogBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        final RecyclerView recyclerView = (RecyclerView) view;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        Asset asset = gson.fromJson(getArguments().getString(ARG_ITEM_COUNT), Asset.class);
        recyclerView.setAdapter(new ItemAdapter(asset));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

         final TextView asset_name;
         final TextView temp;
         final TextView humidity;
         final TextView pressure;
         final TextView temp_max;
         final TextView temp_min;
         final TextView sea_level;
         final TextView feels_like;
         final TextView grnd_level;

        ViewHolder(AssetCardviewBinding binding) {
            super(binding.getRoot());
            asset_name = binding.assetName;
            temp = binding.temp;
            humidity = binding.humidity;
            pressure = binding.pressure;
            temp_max = binding.tempMax;
            temp_min =binding.tempMin;
            sea_level = binding.seaLevel;
            feels_like = binding.feelsLike;
            grnd_level = binding.grndLevel;

        }

    }

    private class ItemAdapter extends RecyclerView.Adapter<ViewHolder> {

        private final Asset asset;

        ItemAdapter(Asset asset) {
            this.asset = asset;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            return new ViewHolder(AssetCardviewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.asset_name.setText(asset.name);
            holder.temp.setText("tempurature value: "+String.valueOf(asset.attributes.get("weatherData").getAsJsonObject().get("value").getAsJsonObject().getAsJsonObject().get("main").getAsJsonObject().get("temp")));
            holder.humidity.setText("humidity value: "+String.valueOf(asset.attributes.get("weatherData").getAsJsonObject().get("value").getAsJsonObject().getAsJsonObject().get("main").getAsJsonObject().get("humidity")));
            holder.pressure.setText("pressure value: "+String.valueOf(asset.attributes.get("weatherData").getAsJsonObject().get("value").getAsJsonObject().getAsJsonObject().get("main").getAsJsonObject().get("pressure")));
            holder.temp_max.setText("tempurature max value: "+String.valueOf(asset.attributes.get("weatherData").getAsJsonObject().get("value").getAsJsonObject().getAsJsonObject().get("main").getAsJsonObject().get("temp_max")));
            holder.temp_min.setText("tempurature min value: "+String.valueOf(asset.attributes.get("weatherData").getAsJsonObject().get("value").getAsJsonObject().getAsJsonObject().get("main").getAsJsonObject().get("temp_min")));
            holder.sea_level.setText("sea lv value: "+String.valueOf(asset.attributes.get("weatherData").getAsJsonObject().get("value").getAsJsonObject().getAsJsonObject().get("main").getAsJsonObject().get("sea_level")));
            holder.feels_like.setText("feels linke value: "+String.valueOf(asset.attributes.get("weatherData").getAsJsonObject().get("value").getAsJsonObject().getAsJsonObject().get("main").getAsJsonObject().get("feels_like")));
            holder.grnd_level.setText("grnd value: "+String.valueOf(asset.attributes.get("weatherData").getAsJsonObject().get("value").getAsJsonObject().getAsJsonObject().get("main").getAsJsonObject().get("grnd_level")));

        }

        @Override
        public int getItemCount() {
            return 1;
        }

    }
}