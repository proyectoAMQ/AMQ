package com.example.amq.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AMQEndpoint {
    private static final String BASE_DIR  = "http://192.168.1.9:8080/";
    private static IAmqApi amqApi;

    //Singleton
    public static IAmqApi getIAmqApi(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_DIR)
                .addConverterFactory( GsonConverterFactory.create() )
                .build();
        amqApi = retrofit.create(IAmqApi.class);
        return amqApi;
    }
}
