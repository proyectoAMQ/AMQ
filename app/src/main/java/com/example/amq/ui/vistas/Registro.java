package com.example.amq.ui.vistas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amq.R;
import com.example.amq.models.DtAltaReserva;
import com.example.amq.models.DtRegistroHuesped;
import com.example.amq.rest.AMQEndpoint;
import com.example.amq.rest.IAmqApi;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.api.Endpoint;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.InputStream;

import java.util.prefs.Preferences;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Registro#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Registro extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public Registro() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Registro.
     */
    // TODO: Rename and change types and number of parameters
    public static Registro newInstance(String param1, String param2) {
        Registro fragment = new Registro();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registro, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db.collection("fotos").document("logosinfondosombreado").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    new Registro.DownloadImageFromInternet((ImageView) view.findViewById(R.id.imageViewRegistro)).execute(documentSnapshot.getString("url1"));
                }
            }
        });

        Button btnRegistro = getView().findViewById(R.id.registro_btn_registro);
        btnRegistro.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String toastMsj = "";
                TextView pass =  getView().findViewById(R.id.registro_pass);
                TextView confPass =  getView().findViewById(R.id.registro_pass_conf);
                TextView email =  getView().findViewById(R.id.registro_email);
                TextView nombre =  getView().findViewById(R.id.registro_nombre);
                TextView apellido =  getView().findViewById(R.id.registro_apellido);

                if(
                        pass.getText().equals("") ||
                        confPass.getText().equals("") ||
                        email.getText().equals("") ||
                        nombre.getText().equals("") ||
                        apellido.getText().equals("")
                ){
                    toastMsj = "Debe completar todos los campos solicitados";
                    Toast.makeText(getContext(), toastMsj , Toast.LENGTH_LONG).show();
                }
                else if( !confPass.getText().toString().equals(pass.getText().toString()) ){
                    toastMsj = "La contraseña y la confirmación no coinciden";
                    Toast.makeText(getContext(), toastMsj, Toast.LENGTH_LONG).show();
                }
                else{

                    String hash = android.util.Base64
                            .encodeToString(pass.getText().toString().getBytes(), Base64.DEFAULT);

                    DtRegistroHuesped dtAltaHuesped = new DtRegistroHuesped(
                            email.getText().toString(),
                            nombre.getText().toString(),
                            apellido.getText().toString(),
                            hash
                    );
                    IAmqApi iAmqApi = AMQEndpoint.getIAmqApi();
                    Call<Object> call = iAmqApi.altaHuesped(dtAltaHuesped);
                    call.enqueue(new Callback<Object>() {
                        @Override
                        public void onResponse(Call<Object> call, Response<Object> response) {
                            if( response.code()==403){
                                new AlertDialog.Builder(getContext())
                                        .setTitle("Sesión: ")
                                        .setMessage("La sesión ha caducado, presione OK para iniciar sesión nuevamente.")
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int whichButton) {
                                                SharedPreferences preferences = PreferenceManager
                                                        .getDefaultSharedPreferences(getContext());
                                                SharedPreferences.Editor editor = preferences.edit();
                                                editor.remove("emailUsuario");
                                                editor.remove("idUsuario");
                                                editor.remove("jwToken");
                                                editor.apply();
                                                Navigation.findNavController(getView()).navigate(R.id.login_fragment, new Bundle());
                                            }}).show();
                            }
                            new AlertDialog.Builder(getContext())
                                    .setTitle("Registro")
                                    .setMessage("El registro fué exitoso, presione OK para ir al inicio de sesión.")
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            Navigation.findNavController(view).navigate(R.id.login_fragment, new Bundle());
                                        }}).show();
                             Log.i("REGISTRO ", "onResponse: OK ");
                        }

                        @Override
                        public void onFailure(Call<Object> call, Throwable t) {
                            String toastMsj = "La contraseña y la confirmación no coinciden";
                            Toast.makeText(getContext(), toastMsj, Toast.LENGTH_LONG).show();
                        }
                    });
                }


            }
        });

    }

    private class DownloadImageFromInternet extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;
        public DownloadImageFromInternet(ImageView imageView) {
            this.imageView=imageView;
        }
        protected Bitmap doInBackground(String... urls) {
            String imageURL=urls[0];
            Bitmap bimage=null;
            try {
                InputStream in=new java.net.URL(imageURL).openStream();
                bimage= BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error Message", e.getMessage());
                e.printStackTrace();
            }
            return bimage;
        }
        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }
}