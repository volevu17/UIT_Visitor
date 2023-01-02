package com.example.uit_visitor.Retrofit_Config;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    private static final OkHttpClient client = UnsafeOkHttpClient.getUnsafeOkHttpClient();
    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance(){
        if(retrofit==null)
        {
            retrofit=new Retrofit.Builder()
                    .baseUrl("https://103.126.161.199/")
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
