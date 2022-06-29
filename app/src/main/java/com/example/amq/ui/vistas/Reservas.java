package com.example.amq.ui.vistas;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.example.amq.GridViewAdapter.GridViewAdapterAlojamiento;
import com.example.amq.GridViewAdapter.GridViewAdapterReserva;
import com.example.amq.R;
import com.example.amq.models.DtAlojamiento;
import com.example.amq.models.DtFiltrosAloj;
import com.example.amq.models.DtResHuespEstado;
import com.example.amq.models.DtReservaAlojHab;
import com.example.amq.models.ReservaEstado;
import com.example.amq.rest.AMQEndpoint;
import com.example.amq.rest.IAmqApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Reservas#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Reservas extends Fragment {
    GridView grdReservas;
    List<DtReservaAlojHab> reservas=null;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Reservas() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Reservas.
     */
    // TODO: Rename and change types and number of parameters
    public static Reservas newInstance(String param1, String param2) {
        Reservas fragment = new Reservas();
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



        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        Integer idUsuario = preferences.getInt("idUsuario" , '0');

        if(! idUsuario.equals(0)) {
            List<ReservaEstado> estados = new ArrayList<>();
            estados.add(ReservaEstado.PENDIENTE);
            DtResHuespEstado dtResHuespEstado = new DtResHuespEstado(idUsuario , estados);
            listarReservas(dtResHuespEstado);
            Log.i("RESERVAS ", reservas != null ? reservas.toString() : "null");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reservas, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    private void listarReservas(DtResHuespEstado dtResHuespEstado){
        IAmqApi amqApi = AMQEndpoint.getIAmqApi();

        Call<List< DtReservaAlojHab> > call = amqApi.listarReservas(dtResHuespEstado);

        call.enqueue(new Callback<List<DtReservaAlojHab>>() {
            @Override
            public void onResponse(Call<List<DtReservaAlojHab>> call, Response<List<DtReservaAlojHab>> response) {
                reservas = response.body();
                if(reservas==null){
                    Log.i("Reservas", "No tiene Reservas");
                }
                else{
                    grdReservas = (GridView) getView().findViewById(R.id.reservas_grdReservas);

                    GridViewAdapterReserva customAdapter = new GridViewAdapterReserva(
                            reservas, getActivity() );
                    grdReservas.setAdapter(customAdapter);
                    Log.e("OK", response.body().toString() );
                }
            }

            @Override
            public void onFailure(Call<List<DtReservaAlojHab>> call, Throwable t) {
                Log.e("ERROR ", "" );
            }


        });


    }
}