package com.example.amq.ui.vistas;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amq.GridViewAdapter.GridViewAdapterAlojamiento;
import com.example.amq.R;
import com.example.amq.models.DtAlojamiento;
import com.example.amq.models.DtFiltrosAloj;
import com.example.amq.rest.AMQEndpoint;
import com.example.amq.rest.IAmqApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListarAlojamientos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListarAlojamientos extends Fragment {

    GridView gridViewAloj;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_EST = "estrellas";
    private static final String ARG_PAIS = "pais";
    private static final String ARG_RANGO = "rango";
    TextView text;

    // TODO: Rename and change types of parameters
    private int estrellas;
    private String pais;
    private int rangoDesde;
    private int rangoHasta;

    public ListarAlojamientos() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ListarAlojamientos newInstance(String estrellas, String pais, String rango) {
        ListarAlojamientos fragment = new ListarAlojamientos();
        Bundle args = new Bundle();
        args.putString(ARG_EST, estrellas);
        args.putString(ARG_PAIS, pais);
        args.putString(ARG_RANGO, rango);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            estrellas = Integer.parseInt(getArguments().getString(ARG_EST));
            pais = getArguments().getString(ARG_PAIS);
            if (getArguments().getString(ARG_RANGO).equals("0-50 U$D")){
                rangoDesde = 0;
                rangoHasta = 50;
            } else if (getArguments().getString(ARG_RANGO).equals("51-100 U$D")){
                rangoDesde = 51;
                rangoHasta = 100;
            }else if (getArguments().getString(ARG_RANGO).equals("101-200 U$D")){
                rangoDesde = 101;
                rangoHasta = 200;
            }else if (getArguments().getString(ARG_RANGO).equals("201-300 U$D")){
                rangoDesde = 201;
                rangoHasta = 300;
            }else if (getArguments().getString(ARG_RANGO).equals("+301 U$D")){
                rangoDesde = 301;
                rangoHasta = 1000;
            }
            Log.v("estrellasList", String.valueOf(estrellas));
            Log.v("paisList", pais);
            Log.v("rangoDList", String.valueOf(rangoDesde));
            Log.v("rangoHList", String.valueOf(rangoHasta));

        }
        getParentFragmentManager().setFragmentResultListener("key", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                String resultB = result.getString("amount");
                text.setText(resultB);
                System.out.println(resultB);
            }
        });



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_listar_alojamientos, container, false);

        gridViewAloj = (GridView) view.findViewById(R.id.gridViewAloj);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DtFiltrosAloj filtros = new DtFiltrosAloj(true);
        listarAlojamientos(filtros);
    }


    private void listarAlojamientos( DtFiltrosAloj filtros ){
        IAmqApi amqApi = AMQEndpoint.getIAmqApi();

        Call<List<DtAlojamiento>> call = amqApi.listarAlojamientos(filtros);
        call.enqueue( new Callback<List<DtAlojamiento>>(){
            @Override
            public void onResponse(Call<List<DtAlojamiento>> call, Response<List<DtAlojamiento>> response) {
                List<DtAlojamiento> alojamientos = response.body();

                gridViewAloj = (GridView) getView().findViewById(R.id.gridViewAloj);
                GridViewAdapterAlojamiento customAdapter = new GridViewAdapterAlojamiento(
                        alojamientos, getActivity()
                );
                gridViewAloj.setAdapter(customAdapter);

                Log.d(">>>>>>>", "onResponse: H!");
            }

            @Override
            public void onFailure(Call<List<DtAlojamiento>> call, Throwable t) {

            }
        });
    }

}