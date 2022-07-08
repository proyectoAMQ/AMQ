package com.example.amq.varios;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.navigation.Navigation;

import com.example.amq.R;
import com.example.amq.rest.AMQEndpoint;
import com.example.amq.rest.IAmqApi;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.net.SocketAddress;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConnectivityCheck {

    public static boolean checkApiConnection(){
        IAmqApi amqApi = AMQEndpoint.getIAmqApi();
        Call<Object> callSync = amqApi.esValidoTokenHuesped("X");
        try{

            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    //TODO your background code
                }
            });
            Response<Object> response = callSync.execute();
            Object apiResponse = response.body();

            //API response
            Log.i("log:", apiResponse.toString());
        }
        catch(Exception e){
            Log.i("log:", e.getMessage()==null ? "OCURRIO UN PROBLEMA!" : e.getMessage());
        }
        return true;
    }
}