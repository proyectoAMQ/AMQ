package com.example.amq.rest;

import com.example.amq.models.Producto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IAmqApi {
    @GET("alojamiento/find/{id}")
    public Call<Producto> find(@Path("id") String id);

    @GET("alojamiento/findList/{id}")
    public Call<List<Producto>> findList(@Path("id") String id);

}
