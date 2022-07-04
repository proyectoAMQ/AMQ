package com.example.amq.rest;

import com.example.amq.models.paypal.DtCreateOrder;
import com.example.amq.models.paypal.DtOrderResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

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
