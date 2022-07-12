package com.example.amq.rest;

import com.example.amq.models.paypal.DtCreateOrder;
import com.example.amq.models.paypal.DtOrderResponse;
import com.example.amq.models.paypal.DtRefund;
import com.example.amq.models.paypal.DtRefundReponse;

import java.lang.reflect.Parameter;

import retrofit2.Call;
import retrofit2.http.Body;
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

    @POST("/v2/payments/captures/{capture_id}/refund")
    public Call<DtRefundReponse> refund(
            @Header("Authorization") String access_token,
            @Body DtRefund dtRefund,
            @Path("capture_id") String capture_id
    );


}
