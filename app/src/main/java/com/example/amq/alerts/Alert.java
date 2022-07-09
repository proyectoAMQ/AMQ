package com.example.amq.alerts;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.navigation.Navigation;

import com.example.amq.R;

public class Alert {
    public static void alertConfirm(View view, String titulo, String mensaje, int redirect){
        new AlertDialog.Builder(view.getContext())
                .setTitle(titulo)
                .setMessage(mensaje)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        if( redirect!=-1 ) {
                            Navigation.findNavController(view).navigate(redirect, new Bundle());
                        }
                    }}).show();
    }
}
