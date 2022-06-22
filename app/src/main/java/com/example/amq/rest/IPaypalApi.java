package com.example.amq.rest;

import com.example.amq.models.DtAlojHab;
import com.example.amq.models.DtAlojamiento;
import com.example.amq.models.DtFiltrosAloj;
import com.example.amq.models.DtLogin;
import com.example.amq.models.DtPais;
import com.example.amq.models.DtUsuario;
import com.example.amq.models.paypal.DtCreateOrder;
import com.example.amq.models.paypal.DtOrderResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IPaypalApi {

    @Headers(
            {
                    "Accept: */*",
                    "Content-Type: application/json",
                    "Accept-Encoding: gzip, deflate, br",
                    "Connection: keep-alive"
            }
    )

    @POST("v2/checkout/orders")
    public Call<DtOrderResponse> createOrder(
            @Header("Authorization") String access_token,
            @Body DtCreateOrder dtCreateOrder
    );

}
