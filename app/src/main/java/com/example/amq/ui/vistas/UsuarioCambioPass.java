package com.example.amq.ui.vistas;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.text.Layout;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.amq.R;
import com.example.amq.alerts.Alert;
import com.example.amq.models.DtPassword;
import com.example.amq.models.DtResetEmail;
import com.example.amq.rest.AMQEndpoint;
import com.example.amq.rest.IAmqApi;

import java.net.ConnectException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UsuarioCambioPass#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UsuarioCambioPass extends Fragment {
    IAmqApi iAmqApi = null;
    SharedPreferences preferences = null;

    View viewCambioPass;
    TextView txtEmail;
    Button btnEnviarCorreo;
    Button btnVerCampos;
    Button btnCambiarPass;
    TextView txtPass;
    TextView txtPassConfirm;
    TextView txtToken;
    ConstraintLayout layCampos;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UsuarioCambioPass() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PassReset.
     */
    // TODO: Rename and change types and number of parameters
    public static UsuarioCambioPass newInstance(String param1, String param2) {
        UsuarioCambioPass fragment = new UsuarioCambioPass();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        iAmqApi = AMQEndpoint.getIAmqApi();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cambio_pass, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewCambioPass = view;
        txtEmail = view.findViewById(R.id.cambioPass_email);
        btnEnviarCorreo = view.findViewById(R.id.cambioPass_btnEnviarToken);
        btnVerCampos = view.findViewById(R.id.cambioPass_btnVerCampos);
        btnCambiarPass = view.findViewById(R.id.cambioPass_btnCambiarPass);
        txtPass = view.findViewById(R.id.cambioPass_txtPass);
        txtPassConfirm = view.findViewById(R.id.cambioPass_txtConfirmPass);
        txtToken = view.findViewById(R.id.cambioPass_txtToken);
        layCampos = view.findViewById(R.id.cambioPass_layIngresarToken);


        btnEnviarCorreo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviarMail ( txtEmail.getText().toString());
            }
        });

        btnVerCampos.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                layCampos.setVisibility(View.VISIBLE);
                btnVerCampos.setEnabled(false);
            }
        } );

        btnCambiarPass.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if( ! txtPass.getText().toString().equals( txtPassConfirm.getText().toString() ) ){
                    Alert.alertConfirm(
                            viewCambioPass,
                            "Cambio de contraseña",
                            "Las contraseñas no coinciden."
                            ,-1
                    );
                }
                else if( txtPass.getText().toString().trim().equals("") ){
                    Alert.alertConfirm(
                            viewCambioPass,
                            "Cambio de contraseña",
                            "Debe ingresar el token enviado a su email.\n\n" +
                                    "Presione OK para continuar."
                            ,-1
                    );
                }
                else{
                    String pass = txtPass.getText().toString();
                    String hashPass = android.util.Base64.encodeToString(pass.getBytes(), Base64.NO_WRAP);

                    DtPassword dtPassword = new DtPassword(
                        txtToken.getText().toString().trim(),
                        hashPass
                    );
                    cambiarPass(dtPassword);
                }
            }
        });


    }

    private void enviarMail( String email ){
        DtResetEmail dtResetEmail = new DtResetEmail(
            email
        );

        Call<Object> call = iAmqApi.cambiarPassEnviarEmail( dtResetEmail );

        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if(response.code()==200){
                    Alert.alertConfirm(
                            viewCambioPass,
                            "Cambio de contraseña",
                            "Si el email ingresado corresponde a un usuario de AMQ recibirá " +
                                    "un código para cambiar la contraseña.\n\n" +
                                    "En este email encontrará los pasos a seguir.\n\n" +
                                    "Presione OK para continuar",
                            -1
                    );
                    layCampos.setVisibility(View.VISIBLE);
                    btnVerCampos.setEnabled(false);
                    Log.i("Envio mail ", "Mail correcto");
                }
                else{
                    String mensaje = response.headers().get("AMQ_ERROR");
                    mensaje = mensaje!=null && !mensaje.equals("") ?
                            mensaje :
                            "Se produjo un error inesperado al enviar el mail de reseteo" +
                                    "de contraseña";
                    Alert.alertConfirm(
                            viewCambioPass,
                            "Error de cambio de contraseña",
                            mensaje,
                            -1
                    );
                    Log.e("Envio mail ", mensaje);
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Alert.alertConfirm(
                        viewCambioPass,
                        "Conectividad",
                        "Error de conectividad, presione OK para continuar.",
                        -1
                );
                Log.e("Envio mail ", t.getMessage());
            }
        });

    }

    private void cambiarPass(DtPassword dtPassword){
        Call<Object> call = iAmqApi.cambiarPass(dtPassword);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if( response.code()==200 ){
                    Alert.alertConfirm(
                            viewCambioPass,
                            "Cambio de contraseña",
                            "La contraseña ha sido cambiada, presione OK para continuar",
                            R.id.login_fragment
                    );
                }else{
                    String mensaje = response.headers().get("AMQ_ERROR");
                    mensaje = mensaje != null && !mensaje.equals("") ? mensaje:
                            "Error desconocido al realizar el cambio de contraseña.";
                    Alert.alertConfirm(
                            viewCambioPass,
                            "Cambio de contraseña",
                            mensaje,
                            -1
                    );
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Alert.alertConfirm(
                        viewCambioPass,
                        "Conectividad",
                        "Error de conectividad, presione OK para continuar.",
                        -1
                );
                Log.e("Cambio de contraseña ", t.getMessage());
            }
        });
    }
}