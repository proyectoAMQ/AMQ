package com.example.amq.ui.vistas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.amq.MainActivity;
import com.example.amq.R;

public class PagoError extends AppCompatActivity {

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pago_error);

        context = this;

        Button btn = this.findViewById(R.id.resultadoPagoError_btnVolver);
        btn.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
            }
        } );


    }
}