package com.example.amq.paypal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;

import com.example.amq.MainActivity;
import com.example.amq.R;
import com.example.amq.models.DtAltaReserva;
import com.example.amq.models.DtUsuario;
import com.example.amq.rest.AMQEndpoint;
import com.example.amq.rest.IAmqApi;
import com.example.amq.ui.vistas.PagoError;
import com.example.amq.ui.vistas.PagoOK;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaypalWebView extends WebViewClient {
    SharedPreferences preferences = null;

    @Override
    public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
        super.doUpdateVisitedHistory(view, url, isReload);

        preferences = PreferenceManager.getDefaultSharedPreferences(view.getContext());

        //Url cancelado
        if( url.matches("(?i).*DB8MHxwaG90by1wYWd.*") ){
            Intent intent = new Intent(view.getContext(), PagoError.class);
            view.getContext().startActivity(intent);
        }
        //Url exito
        else if( url.matches("(?i).*White.*") ) {
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

            String jwToken =  preferences.getString("jwToken" , null );

            IAmqApi iAmqApi = AMQEndpoint.getIAmqApi();
            Call<Object> call = iAmqApi.altaReserva(jwToken, dtAltaReserva);
            call.enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {
                    if( response.code()==403){
                        new AlertDialog.Builder(view.getContext())
                                .setTitle("Sesión: ")
                                .setMessage("La sesión ha caducado, presione OK para iniciar sesión nuevamente.")
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        SharedPreferences preferences = PreferenceManager
                                                .getDefaultSharedPreferences(view.getContext());
                                        SharedPreferences.Editor editor = preferences.edit();
                                        editor.remove("emailUsuario");
                                        editor.remove("idUsuario");
                                        editor.remove("jwToken");
                                        editor.apply();
                                        Navigation.findNavController(view)
                                                .navigate(R.id.login_fragment, new Bundle());
                                    }}).show();
                    }else{
                        Intent intent = new Intent(view.getContext(), PagoOK.class);
                        view.getContext().startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<Object> call, Throwable t) {
                    Intent intent = new Intent(view.getContext(), PagoError.class);
                    view.getContext().startActivity(intent);
                }
            });
        }
    }
}
