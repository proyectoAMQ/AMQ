package com.example.amq.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PaypalEndpoint {
    private static final String BASE_DIR  = "https://api-m.sandbox.paypal.com/";
    private static IPaypalApi paypalApi;

    //Singleton
    public static IPaypalApi getIPaypalApi(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_DIR)
                .addConverterFactory( GsonConverterFactory.create() )
                .build();
        paypalApi = retrofit.create(IPaypalApi.class);
        return paypalApi;
    }
}
