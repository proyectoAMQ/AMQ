package com.example.amq.rest;

import com.example.amq.models.DtAlojHab;
import com.example.amq.models.DtAlojamiento;
import com.example.amq.models.DtAltaReserva;
import com.example.amq.models.DtEnviarCalificacion;
import com.example.amq.models.DtFactura;
import com.example.amq.models.DtFiltrosAloj;
import com.example.amq.models.DtLogin;
import com.example.amq.models.DtIdValor;
import com.example.amq.models.DtPassword;
import com.example.amq.models.DtRegistroHuesped;
import com.example.amq.models.DtResHuespEstado;
import com.example.amq.models.DtReservaAlojHab;
import com.example.amq.models.DtResetEmail;
import com.example.amq.models.DtUsuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IAmqApi {
    @POST("alojamiento/listarAlojamientos")
    public Call<List<DtAlojamiento>> listarAlojamientos(@Body DtFiltrosAloj dtFiltros);

    @GET("alojamiento/getPaises")
    public Call<List<DtIdValor>> getPaises();

    @GET("/alojamiento/buscarAlojamientoHab/{id}")
    public Call<DtAlojHab> buscarAlojamientoHab(@Header("Authorization") String authHeader,
                                                @Path("id") int id );

    @POST("/usuario/login")
    public Call<DtUsuario> login(@Body DtLogin dtLogin );
    
    @POST("/reserva/alta")
    public Call<Object> altaReserva(@Header("Authorization") String authHeader,
                                    @Body DtAltaReserva dtAltaReserva );

    @POST("/usuario/altaHuesped")
    public Call<Object> altaHuesped(@Body DtRegistroHuesped dtRegistroHuesped );

    @POST("/reserva/reservasXHuespXEstado")
    public Call<List<DtReservaAlojHab>> listarReservas(@Header("Authorization") String authHeader,
                                                       @Body DtResHuespEstado dtResHuespEstado );

    @GET("/usuario/esValidoTokenHuesped")
    public Call<Object> esValidoTokenHuesped(@Header("Authorization") String authHeader);

    @POST("/reserva/calificar")
    public Call<Object> calificar(@Header("Authorization") String authHeader,
                                  @Body DtEnviarCalificacion dtEnviarCalificacion);

    @POST("/reserva/cancelarReservaAprobada/{idreserva}")
    public Call<Object> cancelarReservaAprobada(
            @Header("Authorization") String authHeader,
            @Path("idreserva") int idreserva,
            @Body DtFactura dtFactura);


    @POST("/usuario/buscar/{idUsr}")
    public Call<DtUsuario> buscarUsuario(@Header("Authorization") String authHeader,
                                                @Path("idUsr") int idUsr );

    @POST("/usuario/desactivar/{id}")
    public Call<Object> eliminarUsuario(@Header("Authorization") String authHeader,
                                         @Path("id") int idUsr );

    @POST("/usuario/resetPassword")
    public Call<Object> cambiarPassEnviarEmail(@Body DtResetEmail dtResetEmail );

    @POST("/usuario/savePassword")
    public Call<Object> cambiarPass(@Body DtPassword dtPassword );

}
