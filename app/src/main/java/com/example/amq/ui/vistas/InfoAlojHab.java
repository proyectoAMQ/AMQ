package com.example.amq.ui.vistas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.amq.R;
import com.example.amq.models.DtAlojHab;
import com.example.amq.models.DtAlojamiento;
import com.example.amq.rest.AMQEndpoint;
import com.example.amq.rest.IAmqApi;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InfoAlojHab#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InfoAlojHab extends Fragment {
    private DtAlojHab dtAlojHab;
    private int idHab;
    private String nomAloj;
    ImageSlider slider;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public InfoAlojHab() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_info_alojHab.
     */
    // TODO: Rename and change types and number of parameters
    public static InfoAlojHab newInstance(String param1, String param2) {
        InfoAlojHab fragment = new InfoAlojHab();
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
            idHab = getArguments().getInt("idHabitacion");
            nomAloj = getArguments().getString("nomAloj");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_info_aloj_hab, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        slider = view.findViewById(R.id.image_slider);
        final List<SlideModel> imagenesFire = new ArrayList<>();
        db.collection("fotos").document(nomAloj).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    imagenesFire.add(new SlideModel(documentSnapshot.getString("url1"), ScaleTypes.FIT));
                    imagenesFire.add(new SlideModel(documentSnapshot.getString("url2"), ScaleTypes.FIT));
                    imagenesFire.add(new SlideModel(documentSnapshot.getString("url3"), ScaleTypes.FIT));
                    slider.setImageList(imagenesFire, ScaleTypes.FIT);
                }
            }
        });


        Button btnReservar = (Button) getView().findViewById(R.id.info_btnReservar);
        btnReservar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("idHabitacion", idHab ) ;

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences( getContext());
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("idHab", idHab );
                editor.apply();

                Navigation.findNavController(view).navigate(R.id.reservar_fragment, bundle);
            }
        });

        findAlojHab( idHab );
    }

    public DtAlojHab findAlojHab( int id ){
        IAmqApi amqApi = AMQEndpoint.getIAmqApi();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String jwToken = preferences.getString("jwToken", null);

        Call<DtAlojHab> call = amqApi.buscarAlojamientoHab(jwToken, id);
        call.enqueue( new Callback< DtAlojHab >(){
            @Override
            public void onResponse(Call<DtAlojHab> call, Response<DtAlojHab> response) {
                //No tiene permiso
                if( response.code()==403){
                    new AlertDialog.Builder(getContext())
                        .setTitle("Sesión: ")
                        .setMessage("La sesión ha caducado, presione OK para iniciar sesión nuevamente.")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.remove("emailUsuario");
                                editor.remove("idUsuario");
                                editor.remove("jwToken");
                                editor.apply();
                                Navigation.findNavController(getView()).navigate(R.id.login_fragment, new Bundle());
                            }}).show();
                }
                else {
                    dtAlojHab = response.body();
                    //Datos alojamiento
                    ((TextView) getView().findViewById(R.id.info_nomAloj))
                            .setText(dtAlojHab.getNombre());
                    ((TextView) getView().findViewById(R.id.info_descAloj))
                            .setText(dtAlojHab.getDescripcion());
                    ((TextView) getView().findViewById(R.id.info_pais))
                            .setText(dtAlojHab.getDirecion().getPais().getNombre());
                    ((TextView) getView().findViewById(R.id.info_ciudad))
                            .setText(dtAlojHab.getDirecion().getCiudad());
                    ((TextView) getView().findViewById(R.id.info_direccion))
                            .setText(
                                    dtAlojHab.getDirecion().getCalle() + " " +
                                            dtAlojHab.getDirecion().getNumero()
                            );
                    //Datos habitacion
                    ((TextView) getView().findViewById(R.id.info_descHab))
                            .setText(dtAlojHab.getHabitacion().getDescripcion());
                    ((TextView) getView().findViewById(R.id.info_precio))
                            .setText(dtAlojHab.getHabitacion().getPrecioNoche().toString());
                    ((TextView) getView().findViewById(R.id.info_camas))
                            .setText(String.valueOf(dtAlojHab.getHabitacion().getCamas()));
                    //Datos servicios
                    ((TextView) getView().findViewById(R.id.info_aire))
                            .setText(dtAlojHab.getHabitacion().getDtservicios().isAire() ? "SI" : "NO");
                    ((TextView) getView().findViewById(R.id.info_tvCable))
                            .setText(dtAlojHab.getHabitacion().getDtservicios().isTvCable() ? "SI" : "NO");
                    ((TextView) getView().findViewById(R.id.info_jacuzzi))
                            .setText(dtAlojHab.getHabitacion().getDtservicios().isJacuzzi() ? "SI" : "NO");
                    ((TextView) getView().findViewById(R.id.info_wifi))
                            .setText(dtAlojHab.getHabitacion().getDtservicios().isWifi() ? "SI" : "NO");
                    ((TextView) getView().findViewById(R.id.info_desayuno))
                            .setText(dtAlojHab.getHabitacion().getDtservicios().isDesayuno() ? "SI" : "NO");
                    ((TextView) getView().findViewById(R.id.info_parking))
                            .setText(dtAlojHab.getHabitacion().getDtservicios().isParking() ? "SI" : "NO");
                }
            }

            @Override
            public void onFailure(Call<DtAlojHab> call, Throwable t) {
                Log.e("ERROR findAlojHab", t.getMessage());
            }
        } );
        return null;
    }
}