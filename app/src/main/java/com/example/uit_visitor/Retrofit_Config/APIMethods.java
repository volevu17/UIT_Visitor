package com.example.uit_visitor.Retrofit_Config;

import com.example.uit_visitor.Models.Asset;
import com.example.uit_visitor.Models.Map;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIMethods {
    @GET("api/master/map")
    Call<Map> getMap();

    @GET("api/master/asset/{assetID}")
    Call<Asset> getAsset(@Path("assetID")String assetID);

    @GET("api/master/asset/user/current")
    Call<ArrayList<Asset>> getAssetList();
}
