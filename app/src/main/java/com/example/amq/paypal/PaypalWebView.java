package com.example.amq.paypal;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;

import com.example.amq.models.DtAltaReserva;
import com.example.amq.models.DtUsuario;
import com.example.amq.rest.AMQEndpoint;
import com.example.amq.rest.IAmqApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaypalWebView extends WebViewClient {

    @Override
    public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
        super.doUpdateVisitedHistory(view, url, isReload);
        if( url.matches(".*OPERACION_CANCELADA.*") ){
            //REDIRECT....
        }
        else if( url.matches(".*OPERACION_OK.*") ) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(view.getContext());

            Log.i("emailUsuario", preferences.getString("emailUsuario", null));
            Log.i("fInicio", preferences.getString("fInicio", null));
            Log.i("fFin", preferences.getString("fFin", null));
            Log.i("idHab", String.valueOf(preferences.getInt("idHab", 0)));
            Log.i("idUsuario", String.valueOf(preferences.getInt("idUsuario", 0)));
            Log.i("cantDias", String.valueOf(preferences.getInt("cantDias", 0)));
            Log.i("id_order", preferences.getString("id_order", null));

            DtAltaReserva dtAltaReserva = new DtAltaReserva(
                    preferences.getInt("idUsuario", 0),
                    preferences.getInt("idHab", 0),
                    preferences.getInt("cantDias", 0),
                    preferences.getString("fInicio", null),
                    preferences.getString("fFin", null),
                    0.00,
                    preferences.getString("id_order", null)
            );

            IAmqApi iAmqApi = AMQEndpoint.getIAmqApi();
            Call<Object> call = iAmqApi.altaReserva(dtAltaReserva);
            call.enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {
                    Object o = response.body();
                }

                @Override
                public void onFailure(Call<Object> call, Throwable t) {
                    Log.e("ALTA_RESERVA", t.getMessage());
                }
            });
        }
    }
}