package com.example.amq.rest;

import com.example.amq.models.DtAlojHab;
import com.example.amq.models.DtAlojamiento;
import com.example.amq.models.DtAltaReserva;
import com.example.amq.models.DtFiltrosAloj;
import com.example.amq.models.DtLogin;
import com.example.amq.models.DtPais;
import com.example.amq.models.DtRegistroHuesped;
import com.example.amq.models.DtUsuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IAmqApi {

    @POST("alojamiento/listarAlojamientos")
    public Call<List<DtAlojamiento>> listarAlojamientos(@Body DtFiltrosAloj dtFiltros);

    @GET("alojamiento/getPaises")
    public Call<List<DtPais>> getPaises();

    @GET("/alojamiento/buscarAlojamientoHab/{id}")
    public Call<DtAlojHab> buscarAlojamientoHab(@Path("id") int id );

    @POST("/usuario/login")
    public Call<DtUsuario> login(@Body DtLogin dtLogin );
    
    @POST("/reserva/alta")
    public Call<Object> altaReserva(@Body DtAltaReserva dtAltaReserva );

    @POST("/usuario/altaHuesped")
    public Call<Object> altaHuesped(@Body DtRegistroHuesped dtRegistroHuesped );
}
