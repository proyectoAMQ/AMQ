package com.example.amq.ui.vistas;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.amq.R;
import com.example.amq.models.DtAlojamiento;
import com.example.amq.models.DtLogin;
import com.example.amq.models.DtUsuario;
import com.example.amq.rest.AMQEndpoint;
import com.example.amq.rest.IAmqApi;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceIdReceiver;
import com.google.firebase.iid.internal.FirebaseInstanceIdInternal;
import com.google.firebase.messaging.FirebaseMessaging;

import java.nio.charset.StandardCharsets;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    SharedPreferences preferences;
    private String pushToken;
    IAmqApi amqApi = null;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        amqApi = AMQEndpoint.getIAmqApi();


        //Get firebase push token
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("Firebase", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        pushToken = task.getResult();
                        Log.i("Push Token", pushToken);
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        preferences = PreferenceManager.getDefaultSharedPreferences(view.getContext());
        int idUsr = preferences.getInt("idUsuario", 0 );


        //Verifica si el token guardado es válido, si no lo es elimina las variables de sesión
        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String jwToken = preferences.getString("jwToken", "aaa" );
        IAmqApi amqApi = AMQEndpoint.getIAmqApi();
        Call<Object> call = amqApi.esValidoTokenHuesped(jwToken);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if(response.code()!=403){
                    Navigation.findNavController(view).navigate(R.id.navigation_home, new Bundle());
                }
                else{
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    editor.commit();

                    Button btnLoguear = (Button) getView().findViewById(R.id.login_loguear);

                    btnLoguear.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            clickLoguear(view);
                        }
                    });

                    Button btnRegistro = (Button) getView().findViewById(R.id.login_registro);
                    btnRegistro.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Navigation.findNavController(view).navigate(R.id.registro_fragment, new Bundle());
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }

    private void clickLoguear(View view){
        EditText viewEmail = (EditText) getView().findViewById(R.id.login_email);
        String email = viewEmail.getText().toString();
        String pass = ((EditText) getView().findViewById(R.id.login_pass)).getText().toString();

        String hash = android.util.Base64.encodeToString(pass.getBytes(), Base64.DEFAULT);

        DtLogin dtLogin = new DtLogin(email, hash, pushToken);

        Call<DtUsuario> call = amqApi.login(dtLogin);
        call.enqueue(new Callback<DtUsuario>() {
            @Override
            public void onResponse(Call<DtUsuario> call, Response<DtUsuario> response) {
                DtUsuario usr = response.body();

                if (response.headers().get("amq_error") != null && !response.headers().get("amq_error").isEmpty()) {
                    String msjError = "";
                    msjError = response.headers().get("amq_error");

                    Toast.makeText(getActivity(), msjError, Toast.LENGTH_SHORT).show();
                } else if( usr==null ){
                    Toast.makeText(getActivity(), "El usaurio y/o contraseña ingresados son incorrectos"
                            , Toast.LENGTH_SHORT).show();
                }
                else if(!usr.getTipo().equals("Hu")){
                    Toast.makeText(getActivity(), "El usuario ingresado no es huésped."
                            , Toast.LENGTH_SHORT).show();
                }
                else
                {
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("emailUsuario", usr.getEmail());
                    editor.putInt("idUsuario", usr.getId());
                    editor.putString("jwToken", usr.getJwToken());
                    editor.apply();

                    Navigation.findNavController(view).navigate(R.id.navigation_home, new Bundle());

                    Log.i("Usuario logueado", usr.getEmail());
                }
            }

            @Override
            public void onFailure(Call<DtUsuario> call, Throwable t) {
                Log.i("Usuario", t.getMessage());
            }
        });
    }
}