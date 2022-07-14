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
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.amq.R;
import com.example.amq.alerts.Alert;
import com.example.amq.databinding.FragmentUsuarioInfoBinding;
import com.example.amq.models.DtUsuario;
import com.example.amq.rest.AMQEndpoint;
import com.example.amq.rest.IAmqApi;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.InputStream;
import java.net.ConnectException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UsuarioInfo} factory method to
 * create an instance of this fragment.
 */
public class UsuarioInfo extends Fragment {
    private FragmentUsuarioInfoBinding binding;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private View usuarioInfoView = null;
    private SharedPreferences preferences = null;
    private String jwToken = null;
    private int idUsuario;
    private TextView textViewCalificacion = null;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        UsuarioInfoViewModel usuarioInfoViewModel =
                new ViewModelProvider(this).get(UsuarioInfoViewModel.class);

        binding = FragmentUsuarioInfoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        final Button button = binding.btnCerrarSesion;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.remove("emailUsuario");
                editor.remove("idUsuario");
                editor.remove("jwToken");
                editor.apply();
                BottomNavigationView navBar = getActivity().findViewById(R.id.nav_view);
                navBar.setVisibility(View.GONE);

                Navigation.findNavController(view).navigate(R.id.login_fragment, new Bundle());
            }
        });

        jwToken = preferences.getString("jwToken", "");
        idUsuario = preferences.getInt("idUsuario", 0);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        usuarioInfoView = view;
        textViewCalificacion = view.findViewById(R.id.usuarioInfo_calificacion);


        db.collection("fotos").document("Elementos").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    new DownloadImageFromInternet((ImageView) view.findViewById(R.id.imageViewPerfil)).execute(documentSnapshot.getString("url1"));
                }
            }
        });

        Button btnEliminarCuenta = view.findViewById(R.id.usuarioInfo_btneliminar);
        btnEliminarCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminarCuenta( jwToken, idUsuario );
            }
        });

        setCalificacion( jwToken, idUsuario);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
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

    private void setCalificacion( String jwToken, int idUsr){
        IAmqApi iAmqApi = AMQEndpoint.getIAmqApi();
        Call<DtUsuario> call = iAmqApi.buscarUsuario(jwToken, idUsr );
        call.enqueue(new Callback<DtUsuario>() {
            @Override
            public void onResponse(Call<DtUsuario> call, Response<DtUsuario> response) {
                if( response.code()==403){
                    Alert.alertConfirm(
                            usuarioInfoView,
                            "Sesión inválida",
                            "La sesión ha caducado, por favor, presion OK para continuar.",
                            R.id.login_fragment
                    );
                }
                else if (response.code()==200 && response.body()!=null)
                {
                    DtUsuario dtUsuario = response.body();
                    Integer calif = dtUsuario.getCalificacion();
                    textViewCalificacion.setText(
                            calif==null || calif.compareTo(0) <=0 ? "-sin calificar-": calif.toString()
                    );
                }
                else{
                    String headerError = response.headers().get("AMQ_ERROR");
                    Alert.alertConfirm(
                            usuarioInfoView,
                            "Error de servidor",
                            headerError==null || headerError.equals("") ? "Error desconocido" : headerError,
                            -1
                    );
                }
            }

            @Override
            public void onFailure(Call<DtUsuario> call, Throwable t) {
                String mensaje = null;
                if( t instanceof ConnectException ){
                    mensaje = "Se ha producido un error al conectar con el servidor AMQ, " +
                            "presiones OK para continuar.";
                }
                else{
                    mensaje = "Error desconocido, presione OK para continuar.";
                }

                Alert.alertConfirm(
                        usuarioInfoView,
                        "Error de conectividad",
                        mensaje,
                        -1
                );
            }
        });
    }

    private void eliminarCuenta( String jwToken, int idUsr){
        new AlertDialog.Builder(usuarioInfoView.getContext())
                .setTitle("Eliminar cuenta")
                .setMessage("¿Realmente desea eliminar su cuenta de Aquí Me Quedo?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }})
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        eliminarCuentaRest(jwToken, idUsr );
                    }}).show();
    }

    private void eliminarCuentaRest( String jwToken, int idUsr ){
        IAmqApi iAmqApi = AMQEndpoint.getIAmqApi();
        Call<Object> call = iAmqApi.eliminarUsuario(jwToken, idUsr);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if( response.code()==403 ){
                    Alert.alertConfirm(
                            usuarioInfoView,
                            "Sesión inválida",
                            "La sesión ha caducado, presione OK para continuar",
                            R.id.login_fragment
                    );
                }
                else if( response.code() == 200 ){
                    SharedPreferences.Editor editor  = preferences.edit();
                    editor.clear();
                    editor.commit();

                    Alert.alertConfirm(
                            usuarioInfoView,
                            "Eliminar cuenta",
                            "Su cuenta ha sido eliminada, presion OK para continuar",
                            R.id.login_fragment
                    );
                }
                else{
                    String headerError = response.headers().get("AMQ_ERROR");
                    Alert.alertConfirm(
                        usuarioInfoView,
                        "Error de servidor",
                        headerError==null || headerError.equals("") ? "Error desconocido" : headerError,
                        -1
                    );
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                String mensaje = null;
                if( t instanceof ConnectException ){
                    mensaje = "Se ha producido un error al conectar con el servidor AMQ, " +
                            "presiones OK para continuar.";
                }
                else{
                    mensaje = "Error desconocido, presione OK para continuar.";
                }

                Alert.alertConfirm(
                        usuarioInfoView,
                        "Error de conectividad",
                        mensaje,
                        -1
                );
            }
        });
    }
}