package com.example.amq.varios;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.net.SocketAddress;

public class ConnectivityCheck {

    /**
     * Verifica si la conexión está disponible
     */
    public static boolean checkConnection(Context context) {
        final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connMgr != null) {
            NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();

            if (activeNetworkInfo != null) { // connected to the internet
                // connected to the mobile provider's data plan
                if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    // connected to wifi
                    return true;
                } else return activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
            }
        }
        return false;
    }

    public static boolean checkApiConnection(String address, int port){
        SocketAddress socketAddress = new InetSocketAddress(address, port);
        Proxy proxy = new Proxy(Proxy.Type.SOCKS, socketAddress);
        Socket socket = new Socket();
        try {
            int timeout = 2000;

            socket.connect(socketAddress, timeout);
            return true;
        }
        catch (IOException e) {
            return false;
        }
        catch(Exception e){
            Log.e("Connectivity exception", e.getMessage());
            return false;
        }
        finally {
            if (socket.isConnected()) {
                try {
                    socket.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}