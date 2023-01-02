package com.example.uit_visitor.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uit_visitor.CustomAdapter;
import com.example.uit_visitor.Models.Asset;
import com.example.uit_visitor.R;
import com.example.uit_visitor.Retrofit_Config.APIClient;
import com.example.uit_visitor.Retrofit_Config.APIMethods;
import com.example.uit_visitor.databinding.FragmentGalleryBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;

public class AssetListFragment extends Fragment {

    private FragmentGalleryBinding binding;
    private ArrayList<Asset> arrayList;
    private CustomAdapter customAdapter = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        RecyclerView recyclerView = root.findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        APIMethods apiMethods = APIClient.getRetrofitInstance().create(APIMethods.class);
        Call<ArrayList<Asset>> call = apiMethods.getAssetList();
        call.enqueue(new Callback<ArrayList<Asset>>() {
            @Override
            public void onResponse(Call<ArrayList<Asset>> call, Response<ArrayList<Asset>> response) {
                arrayList = response.body();
                customAdapter = new CustomAdapter(AssetListFragment.this.getContext(), arrayList);
                recyclerView.setAdapter(customAdapter);

            }

            @Override
            public void onFailure(Call<ArrayList<Asset>> call, Throwable t) {

            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}