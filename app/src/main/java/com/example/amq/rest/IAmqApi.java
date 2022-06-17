package com.example.amq.rest;

import com.example.amq.models.DtAlojamiento;
import com.example.amq.models.DtFiltrosAlojamiento;
import com.example.amq.models.Producto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IAmqApi {
    @POST("alojamiento/listarAlojamientos")
    public Call<List<DtAlojamiento>> listarAlojamientos(@Body DtFiltrosAlojamiento filtrosAloj);

    @GET("alojamiento/findList/{id}")
    public Call<List<Producto>> findList(@Path("id") String id);

}
